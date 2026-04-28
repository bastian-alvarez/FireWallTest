# Usar la imagen oficial de Java 21
FROM eclipse-temurin:21-jdk-alpine

# Crear un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo .jar que acabamos de compilar
# (Revisa en tu carpeta 'target' que el nombre coincida, si no, cámbialo aquí)
COPY target/ms-reportes-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de Spring Boot
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]