# Usamos una imagen de Java como base
FROM openjdk:17-jdk

# Directorio de trabajo
WORKDIR /app

# Copiamos el archivo WAR del proyecto
COPY target/Despensa-0.0.1-SNAPSHOT.war app.war

# Puerto de exposición del contenedor
EXPOSE 8081

# Comando de inicio de la aplicación
CMD ["java", "-jar", "app.war"]
