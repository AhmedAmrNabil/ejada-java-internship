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
  oracleDBContainerName = "oracle26ai";
  sqlplus = "docker exec -i ${oracleDBContainerName} sqlplus -s";

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
      #bash
      docker stop ${oracleDBContainerName} 2>/dev/null || true
      docker run --rm --name ${oracleDBContainerName} \
        -p 1521:1521 -p 5500:5500 \
        -e ORACLE_PWD=${oracleSysPwd} \
        -v oracle-data:/opt/oracle/oradata \
        container-registry.oracle.com/database/free:latest-lite
    '';

    process-compose = {
      shutdown.command = "docker stop ${oracleDBContainerName}";
    };

    # Wait for Oracle to be ready
    ready.exec = ''
      #bash
      status=$(docker inspect --format='{{.State.Health.Status}}' ${oracleDBContainerName} 2>/dev/null || echo "starting")
      [ "$status" = "healthy" ]
    '';
  };

  tasks."ejadatestapp:init-db" = {
    exec = ''
      #bash
      #!/usr/bin/env bash
      ${sqlplus} system/${oracleSysPwd}@//${oracleHost}:${oraclePort}/${oraclePdb} <<SQL
        WHENEVER SQLERROR EXIT SQL.SQLCODE;
        DECLARE
          v_count NUMBER;
        BEGIN
          SELECT COUNT(*) INTO v_count FROM dba_users WHERE username = UPPER('${oracleAppUser}');
          IF v_count = 0 THEN
            EXECUTE IMMEDIATE 'CREATE USER ${oracleAppUser} IDENTIFIED BY "${oracleAppPwd}"';
            EXECUTE IMMEDIATE 'GRANT CONNECT, RESOURCE, UNLIMITED TABLESPACE TO ${oracleAppUser}';
          END IF;
        END;
        /
        EXIT;
      SQL
    '';
    after = [ "devenv:processes:oracle@ready" ];
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
