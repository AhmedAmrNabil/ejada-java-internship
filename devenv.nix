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
    jdk.package = pkgs.jdk25;
    maven.enable = true;
  };

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
      --arg jdkPath "${pkgs.jdk25}/lib/openjdk" \
      '.["java.configuration.runtimes"] = [
        { "name": "JavaSE-25", "path": $jdkPath }
      ]
      | .["java.jdt.ls.java.home"] = $jdkPath' \
      .vscode/settings.json > .vscode/settings.json.tmp \
      && mv .vscode/settings.json.tmp .vscode/settings.json
  '';
}
