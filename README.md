# PhoenixSpigot

A Spigot implementation of the Phoenix API.

 [![TeamCity](https://img.shields.io/teamcity/http/ci.lss233.com/s/PhoenixFramework_PhoenixSpigot.svg)](https://ci.lss233.com/viewType.html?buildTypeId=PhoenixFramework_PhoenixSpigot&guest=1)
[![Telegram](https://img.shields.io/badge/chat-Telegram-blue.svg)](https://t.me/PhoenixMCDev)
[![LICENSE](https://img.shields.io/github/license/ThePhoenixMC/PhoenixSpigot.svg)](LICENSE)



# Requirements

-  Java 1.8 or higher

# Cloning
The following steps will ensure your project is cloned properly.

 ```
git clone  --recursive https://github.com/ThePhoenixMC/PhoenixSpigot.git
```

## Updating 
The following steps will update your clone with the official repo.

```
git pull
git submodule update --recursive
```
# Building

1.  Cloning project
2. Building:
```
./mvnw clean install
```
You can find the compiled JAR files in `PhoenixForSpigot/target` directory.

# Running

After building the project, drop `PhoenixForSpigot-x.x.x-x-x.x-x.jar' to your server's plugin directory, and then restart your server.

-  You can put your Phoenix Module to 'modules' directory.
- The configuration files of the modules is under 'config' directory.


# Contributing

See [CONTRIBUTING](CONTRIBUTING.md).

# License

This project is licensed under the [MIT License](LICENSE).
