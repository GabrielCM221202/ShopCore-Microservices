# ==========================================
# Etapa 1: Construcción (Builder)
# ==========================================
# Usamos una imagen base ligera de Alpine Linux que ya trae Java 21 JDK.
# El JDK incluye todas las herramientas necesarias para compilar código Java.
# Le damos el alias "builder" para referirnos a esta etapa más adelante.
FROM eclipse-temurin:21-jdk-alpine AS builder

# Definimos el directorio de trabajo dentro del contenedor.
# Todo lo que hagamos a partir de aquí ocurrirá dentro de /app.
WORKDIR /app

# Copiamos primero los archivos relacionados con Maven (.mvn y pom.xml).
# Esto aprovecha el sistema de caché de Docker: si el pom.xml no cambia,
# no será necesario volver a descargar todas las dependencias.
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Damos permisos de ejecución al wrapper de Maven (mvnw),
# para poder usarlo dentro del contenedor.
RUN chmod +x ./mvnw

# Descargamos todas las dependencias definidas en el pom.xml.
# Esto prepara el entorno de compilación y acelera futuras builds.
RUN ./mvnw dependency:go-offline

# Ahora sí copiamos el código fuente completo (src).
COPY src ./src

# Compilamos el proyecto y generamos el archivo .jar dentro de /app/target.
# Usamos -DskipTests porque las pruebas se ejecutarán en Jenkins antes.
RUN ./mvnw clean package -DskipTests

# ==========================================
# Etapa 2: Producción (Runner)
# ==========================================
# Iniciamos una nueva imagen base, esta vez con solo el JRE (Runtime).
# El JRE es mucho más ligero que el JDK y suficiente para ejecutar la app.
FROM eclipse-temurin:21-jre-alpine

# Definimos el directorio de trabajo en esta nueva etapa.
WORKDIR /app

# Creamos un usuario sin privilegios llamado "spring".
# Esto evita que la aplicación se ejecute como root,
# reduciendo riesgos de seguridad en caso de vulnerabilidades.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiamos el archivo .jar generado en la etapa "builder"
# hacia esta nueva imagen limpia, renombrándolo como app.jar.
COPY --from=builder /app/target/*.jar app.jar

# Documentamos que la aplicación escuchará en el puerto 8080.
# Esto no abre el puerto, solo sirve como referencia para otros servicios.
EXPOSE 8080

# Definimos el comando que se ejecutará al iniciar el contenedor.
# Usamos "sh -c" para permitir la expansión de variables de entorno ($JAVA_OPTS).
# En producción, podremos inyectar parámetros como límites de memoria (-Xms, -Xmx).
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
