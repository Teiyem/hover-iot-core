# Hover
Hover (**H**ome Aut**o**mation Ser**ver**) is a central hub for managing and monitoring smart home devices by providing Restful APIs that are consumed by the Hover client applications.

[![CI Build Hover Core](https://github.com/Teiyem/hover-iot-core/actions/workflows/hover-iot-core-ci.yml/badge.svg)](https://github.com/Teiyem/hover-iot-core/actions/workflows/hover-iot-core-ci.yml)

## Features
* Authentication and authorization of user actions. ✅
* Managing and monitoring smart home devices. ✅
* Automations and Scenes. ✅
* Persistence of smart home device data. ✅

## Technologies Used
* Java 17.
* Spring Boot.
* Spring WebMVC.
* Spring Data JPA.
* Spring Security.
* PostgreSQL.
* Docker.
* Hashicorp Vault.

## Getting Started
To run Hover on your local machine, you will need to have Java 17, Maven and Docker installed. Follow these steps to get started:
* Clone the project to your local machine.
* Build the project using Maven.
* Run Docker-compose up -d
* Run the application.

## Usage
Hover provides a set of Restful APIs that allow for the management and monitoring of smart home devices. These APIs are consumed by the Hover client applications.

## Authors
Hover was developed by Thabang Mmakgatla.

## License
Hover is released under the MIT license. See LICENSE for more details.
