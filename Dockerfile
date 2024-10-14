# Indica que imagen base de Java se usará para construir la imagen Docker.
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor donde se ejecutarán las siguientes instrucciones.
WORKDIR /app

# Define una variable de construcción (ARG) llamada JAR_FILE, que se usará para especificar la ubicación del archivo JAR.
ARG JAR_FILE=build/libs/LaFabricaApp-0.0.1-SNAPSHOT.jar

# Copia el archivo JAR desde la máquina host al directorio de trabajo en el contenedor Docker y lo nombra 'app.jar'.
COPY ${JAR_FILE} app.jar

# Define el punto de entrada del contenedor que, en este caso, es ejecutar el comando 'java -jar app.jar'.
ENTRYPOINT ["java", "-jar", "app.jar"]
