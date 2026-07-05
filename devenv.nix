{
  inputs,
  pkgs,
  ...
}:
let
  oracleSysPwd = "PlaceholderPassword";
  oracleAppUser = "ejadatestapp";
  oracleAppPwd = "verysecureejadapassword";
  oraclePdb = "FREEPDB1";
  sqlplus = "docker exec -i oracle26ai sqlplus -s";

  pkgsUnstable = import inputs.nixpkgs-unstable { system = pkgs.stdenv.system; };
in
{
  name = "ejada";
  languages.java = {
    enable = true;
    jdk.package = pkgs.jdk25;
    maven.enable = true;
  };

  processes.oracle = {
    exec = ''
      docker stop oracle26ai 2>/dev/null || true
      docker run --rm --name oracle26ai \
        -p 1521:1521 -p 5500:5500 \
        -e ORACLE_PWD=${oracleSysPwd} \
        -v oracle-data:/opt/oracle/oradata \
        container-registry.oracle.com/database/free:latest-lite
    '';

    process-compose = {
      shutdown.command = "docker stop oracle26ai";
    };

    # Wait for Oracle to be ready
    ready.exec = ''
      status=$(docker inspect --format='{{.State.Health.Status}}' oracle26ai 2>/dev/null || echo "starting")
      [ "$status" = "healthy" ]
    '';
  };

  tasks."ejadatestapp:init-db" = {
    exec = ''
      #bash
      #!/usr/bin/env bash
      ${sqlplus} system/${oracleSysPwd}@//localhost:1521/${oraclePdb} <<SQL
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

  packages = with pkgs; [
    just
    just-lsp # lsp for Justfiles
  ] ++( with pkgsUnstable; [
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
