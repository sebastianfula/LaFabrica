# La Fabrica

Proyecto Java Web, Spring Boot, MySQL y Docker

>![Versión](https://img.shields.io/badge/version-1.0.0-green.svg)
>![Java](https://img.shields.io/badge/Java-17-blue)
>![Gradle](https://img.shields.io/badge/Gradle-7.x-green)

## Descripción del Proyecto
Este proyecto consiste en una aplicación para gestionar una fábrica. El objetivo principal es permitir operaciones CRUD para cada entidad, así como su aplicación mediante un entorno Docker que conecta la aplicación Java con un servicio de MySQL.

## Tabla de Contenidos
1. [Requisitos del Proyecto](#requisitos-del-proyecto)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Instalación y Configuración](#instalación-y-configuración)
4. [Uso del Aplicativo](#uso-del-aplicativo)
5. [Despliegue con Docker](#despliegue-con-docker)
6. [Arquitectura de la Base de Datos](#arquitectura-de-la-base-de-datos)

## Requisitos del Proyecto

> [!NOTE]
> El desarrollo del modelo de base de datos se encuentra alojado en el directorio de recursos.

## Tecnologías Utilizadas
- **Java 17**
- **Spring Boot**
- **MySQL**
- **Docker** (para la aplicación y el servicio de base de datos)
- **Gradle** como administrador de dependencias

## Instalación y Configuración

### Requisitos Previos
> [!IMPORTANT]
> Asegúrate de tener instalado Docker en tu entorno local.

1. Clona el repositorio:
    ```bash
    git clone https://github.com/tuusuario/la-fabrica.git
    cd la-fabrica
    ```

2. Compila el proyecto con Gradle:
    ```bash
    ./gradlew build
    ```

3. Configura las variables de entorno necesarias para conectar la aplicación a la base de datos MySQL.

## Uso del Aplicativo

> [!TIP]
> Para ejecutar el proyecto localmente, usa el siguiente comando:

```bash
./gradlew bootRun
```
Accede a la aplicación en http://localhost:8081.

Para ver las peticiones disponibles, accede a http://localhost:8081/swagger-ui/index.html#/

## Despliegue con Docker
El proyecto está configurado para ejecutarse dentro de un contenedor Docker. Para desplegar la aplicación junto con MySQL, utiliza el siguiente comando:

```bash
docker-compose up --build
```
Nota: El archivo docker-compose.yml configura tanto la aplicación como la base de datos, asegurando una fácil orquestación entre los servicios.

## Arquitectura de la Base de Datos
El esquema de base de datos se encuentra dentro del desarrollo del proyecto.

> [!TIP]
>Nota: Puedes encontrar el diagrama de la base de datos en el directorio resources/database.