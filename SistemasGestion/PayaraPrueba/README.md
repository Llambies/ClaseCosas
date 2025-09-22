# Payara Server Hello World 🐠

Una aplicación web "Hello World" simple usando Jakarta EE 10 y Payara Server 6.

## 📋 Requisitos Previos

- **Java 21** (OpenJDK recomendado)
- **Maven 3.6+** (para compilación)
- **Payara Server 6.2024.2** o **Payara Micro** (para ejecución)

## 🏗️ Estructura del Proyecto

```
payara-hello-world/
├── pom.xml                                 # Configuración Maven
├── README.md                               # Este archivo
└── src/
    └── main/
        ├── java/
        │   └── com/example/servlet/
        │       └── HelloWorldServlet.java  # Servlet principal
        ├── resources/                      # Recursos adicionales
        └── webapp/
            ├── index.html                  # Página de inicio
            └── WEB-INF/
                └── web.xml                 # Configuración web
```

## 🚀 Cómo Ejecutar

### Opción 1: Con Payara Micro (Recomendado para desarrollo)

```bash
# Compilar el proyecto
mvn clean package

# Ejecutar con Payara Micro
mvn payara-micro:start
```

La aplicación estará disponible en: `http://localhost:8080/payara-hello-world`

### Opción 2: Con Payara Server

1. **Compilar el proyecto:**
   ```bash
   mvn clean package
   ```

2. **Desplegar en Payara Server:**
   ```bash
   # Copiar el WAR al directorio de autodeploy
   cp target/payara-hello-world.war $PAYARA_HOME/glassfish/domains/domain1/autodeploy/
   
   # O usar el comando asadmin
   $PAYARA_HOME/bin/asadmin deploy target/payara-hello-world.war
   ```

3. **Acceder a la aplicación:**
   - Página principal: `http://localhost:8080/payara-hello-world`
   - Servlet Hello: `http://localhost:8080/payara-hello-world/hello`
   - Saludo personalizado: `http://localhost:8080/payara-hello-world/hello?nombre=TuNombre`

### Opción 3: Solo compilar (sin Maven instalado)

Si no tienes Maven instalado, puedes compilar manualmente:

```bash
# Crear directorio de clases
mkdir -p target/classes

# Compilar (necesitas las librerías de Jakarta EE en el classpath)
javac -cp "lib/*" -d target/classes src/main/java/com/example/servlet/*.java

# Crear estructura WAR
mkdir -p target/webapp/WEB-INF/classes
cp -r target/classes/* target/webapp/WEB-INF/classes/
cp -r src/main/webapp/* target/webapp/

# Crear WAR
cd target/webapp && jar -cvf ../payara-hello-world.war *
```

## 🌟 Características

- **Servlet moderno** usando anotaciones Jakarta EE
- **Interfaz responsive** con CSS moderno
- **Parámetros dinámicos** para personalizar el saludo
- **Información del sistema** (versión Java, servidor, etc.)
- **Configuración completa** con web.xml
- **Compatible** con Java 21 y Jakarta EE 10

## 📝 Endpoints Disponibles

| URL | Descripción |
|-----|-------------|
| `/` | Página de inicio con interfaz moderna |
| `/hello` | Servlet Hello World básico |
| `/hello?nombre=X` | Saludo personalizado |
| `/hola` | Alias en español para el servlet |

## 🔧 Configuración

### Modificar el Puerto (Payara Micro)

```bash
mvn payara-micro:start -Dpayara.micro.port=9090
```

### Variables de Entorno

- `PAYARA_HOME`: Ruta de instalación de Payara Server
- `JAVA_HOME`: Ruta de instalación de Java

## 🐛 Solución de Problemas

### Error: "java.lang.UnsupportedClassVersionError"
- **Causa:** Versión incorrecta de Java
- **Solución:** Asegúrate de usar Java 21

### Error: "Command 'mvn' not found"
- **Causa:** Maven no está instalado
- **Solución:** Instala Maven o usa la compilación manual

### Error: Puerto 8080 en uso
- **Solución:** Cambia el puerto o detén el proceso que lo está usando:
  ```bash
  # Ver qué proceso usa el puerto
  lsof -i :8080
  
  # Usar otro puerto
  mvn payara-micro:start -Dpayara.micro.port=9090
  ```

## 📚 Recursos Adicionales

- [Documentación de Payara Server](https://docs.payara.fish/)
- [Jakarta EE Tutorial](https://jakarta.ee/learn/)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature
3. Commit tus cambios
4. Push a la rama
5. Abre un Pull Request

---

**¡Disfruta desarrollando con Payara Server!** 🐠✨
