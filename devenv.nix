{
  config,
  inputs,
  pkgs,
  ...
}:
let
  oracleSysPwd = config.env.DB_SYSTEM_PASSWORD;
  oracleAppUser = config.env.DB_USERNAME;
  oracleAppPwd = config.env.DB_PASSWORD;
  oraclePdb = config.env.DB_SERVICE_NAME;
  oracleHost = config.env.DB_HOST;
  oraclePort = config.env.DB_PORT;
  oracleDBContainerName = config.env.DB_CONTAINER_NAME;

  pkgsUnstable = import inputs.nixpkgs-unstable { system = pkgs.stdenv.system; };
in
{
  name = "ejada";
  dotenv.enable = true;
  languages.java = {
    enable = true;
    jdk.package = pkgs.jdk17;
    maven.enable = true;
  };

  languages.javascript = {
    enable = true;
    pnpm.enable = true;
  };
  languages.typescript.enable = true;

  processes.oracle = {
    exec = ''
      docker compose -f docker-compose.yml up db
    '';

    process-compose = {
      shutdown.command = "docker compose -f docker-compose.yml down db";
    };
  };

  packages =
    with pkgs;
    [
      just
      just-lsp # lsp for Justfiles
      (pkgs.writeShellScriptBin "sqlplus" ''
        #bash
        exec docker exec -it ${oracleDBContainerName} sqlplus ${oracleAppUser}/${oracleAppPwd}@//${oracleHost}:${oraclePort}/${oraclePdb} "$@"
      '')
      (pkgs.writeShellScriptBin "sqlplus-system" ''
        #bash
        exec docker exec -it ${oracleDBContainerName} sqlplus system/${oracleSysPwd}@//${oracleHost}:${oraclePort}/${oraclePdb} "$@"
      '')
    ]
    ++ (with pkgsUnstable; [
      vscode-fhs
    ]);

  enterShell = ''
    #bash
    mkdir -p .vscode
    if [ ! -f .vscode/settings.json ]; then echo '{}' > .vscode/settings.json; fi

    ${pkgs.jq}/bin/jq \
      --arg jdkPath "${pkgs.jdk17}/lib/openjdk" \
      --arg jdk25Path "${pkgs.jdk25}/lib/openjdk" \
      '.["java.configuration.runtimes"] = [
        { "name": "JavaSE-17", "path": $jdkPath },
        { "name": "JavaSE-25", "path": $jdk25Path }
      ]
      | .["java.jdt.ls.java.home"] = $jdk25Path
      | .["sonarlint.pathToNodeExecutable"] = "${pkgs.nodejs}/bin/node"
      | .["sonarlint.ls.javaHome"] = $jdk25Path
      | .["sonarlint.ls.vmargs"] = [
          "-XX:+UseParallelGC",
          "-XX:GCTimeRatio=4",
          "-XX:AdaptiveSizePolicyWeight=90",
          "-Dsun.zip.disableMemoryMapping=true",
          "-Xmx8G",
          "-Xms100m",
          "-Xlog:disable",
          "-XX:ActiveProcessorCount=1"
        ]
      | .["spring-boot.ls.java.home"] = $jdk25Path
      | .["spring-boot.ls.java.vmargs"] = [
          "-XX:+UseParallelGC",
          "-XX:GCTimeRatio=4",
          "-XX:AdaptiveSizePolicyWeight=90",
          "-Dsun.zip.disableMemoryMapping=true",
          "-Xmx8G",
          "-Xms100m",
          "-Xlog:disable",
          "-XX:ActiveProcessorCount=1"
        ]' \
      .vscode/settings.json > .vscode/settings.json.tmp \
      && mv .vscode/settings.json.tmp .vscode/settings.json
  '';
}
