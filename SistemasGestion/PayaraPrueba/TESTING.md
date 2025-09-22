# 🧪 Guía de Pruebas - Payara Hello World

## 🚀 Estado Actual
✅ **Aplicación ejecutándose exitosamente**
- **Servidor:** Payara Micro 6.2024.2
- **Puerto:** 8080
- **URL Base:** http://localhost:8080/payara-hello-world

## 📱 URLs para Probar

### 1. Página Principal
```
http://localhost:8080/payara-hello-world/
```
- Muestra la página de bienvenida con interfaz moderna
- Incluye información del sistema y enlaces a los servlets

### 2. Servlet Hello World (Básico)
```
http://localhost:8080/payara-hello-world/hello
```
- Servlet básico que muestra "¡Hola, Mundo!"
- Información del servidor y versión de Java

### 3. Servlet con Parámetro Personalizado
```
http://localhost:8080/payara-hello-world/hello?nombre=TuNombre
```
- Saludo personalizado usando el parámetro `nombre`
- Ejemplo: `?nombre=Desarrollador`

### 4. Alias en Español
```
http://localhost:8080/payara-hello-world/hola
```
- Mismo servlet accesible con URL en español

## 🔍 Qué Verificar

### ✅ Funcionalidades Implementadas
- [x] Página de inicio responsive
- [x] Servlet Hello World funcional
- [x] Parámetros dinámicos
- [x] Información del sistema
- [x] Configuración Jakarta EE 10
- [x] Compatibilidad con Java 21

### 📊 Información del Sistema Mostrada
- Versión del servidor (Payara Micro)
- Versión de Java (21.0.8)
- Versión de Servlet API (6.0)
- Fecha y hora actual
- Nombre de la instancia

## 🛠️ Comandos Útiles

### Detener el Servidor
```bash
# Presiona Ctrl+C en la terminal donde se ejecuta
# O encuentra el proceso:
ps aux | grep payara
kill <PID>
```

### Reiniciar la Aplicación
```bash
# Detener el servidor actual y ejecutar:
mvn payara-micro:start
```

### Ver Logs en Tiempo Real
```bash
# Los logs se muestran en la terminal donde ejecutaste mvn payara-micro:start
```

### Construir Solo el WAR (sin ejecutar)
```bash
mvn clean package
# El archivo WAR estará en: target/payara-hello-world.war
```

## 🐛 Solución de Problemas

### Puerto 8080 Ocupado
```bash
# Verificar qué proceso usa el puerto
lsof -i :8080

# Usar un puerto diferente
mvn payara-micro:start -Dpayara.micro.port=9090
```

### Error de Memoria
```bash
# Aumentar memoria disponible
export MAVEN_OPTS="-Xmx1024m"
mvn payara-micro:start
```

### Problemas de Compilación
```bash
# Limpiar y recompilar
mvn clean compile package
```

## 📈 Próximos Pasos Sugeridos

1. **Agregar más servlets** con diferentes funcionalidades
2. **Implementar JSP** para vistas más complejas
3. **Agregar base de datos** con JPA
4. **Implementar REST API** con JAX-RS
5. **Agregar seguridad** con Jakarta Security
6. **Configurar logging** personalizado
7. **Implementar pruebas unitarias** con JUnit

## 🎯 Características Técnicas

- **Framework:** Jakarta EE 10
- **Servidor:** Payara Micro 6.2024.2
- **Java:** OpenJDK 21
- **Build Tool:** Maven 3.9.11
- **Packaging:** WAR
- **Deployment:** Auto-deploy con Payara Micro

---

**¡Tu aplicación Hello World está funcionando perfectamente!** 🎉

Para cualquier problema o mejora, consulta el `README.md` o revisa los logs del servidor.
