{ pkgs, ... }: {
  name = "ejada";
  languages.java = {
    enable = true;
    jdk.package = pkgs.jdk25;
    maven.enable = true;
  };

  services.mysql = {
    enable = true;
    ensureUsers = [
      {
        name = "devenv";
        ensurePermissions = {
          "devenv.*" = "ALL PRIVILEGES";
        };
      }
    ];
  };

  packages = with pkgs; [
    just
    just-lsp # lsp for Justfiles
  ];

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
