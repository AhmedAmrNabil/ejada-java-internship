{
  description = "Flake for ejada internship";
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-26.05";
    flake-parts.url = "github:hercules-ci/flake-parts";
  };

  outputs =
    { flake-parts, ... }@inputs:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = [
        "x86_64-linux"
        "aarch64-linux"
        "x86_64-darwin"
        "aarch64-darwin"
      ];

      perSystem =
        {
          pkgs,
          ...
        }:
        {
          devShells.default = pkgs.mkShell {
            name = "ejada";
            buildInputs = with pkgs; [
              jdk25
              maven
            ];
            shellHook = ''
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
          };
        };

      flake = {
        # The usual flake attributes can be defined here, including
        # system-agnostic and/or arbitrary outputs.
      };
    };
}
