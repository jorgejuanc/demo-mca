FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 5000

# Copiar los archivos de la aplicación
COPY . /app
WORKDIR /app

# Construir la aplicación con Gradle
RUN ./gradlew build

# Copiar el archivo JAR generado por Gradle
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Configurar el archivo application.yml
COPY /src/main/resources/application.yml /src/main/resources/application.yml

# Comando de inicio de la aplicación
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.config.location=file:/app/src/main/resources/application.yml"]
