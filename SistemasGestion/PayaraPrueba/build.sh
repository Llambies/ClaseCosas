#!/bin/bash

# Script de construcción para Payara Hello World
# Autor: Asistente Cascade
# Fecha: $(date)

set -e  # Salir si hay errores

echo "🐠 Payara Hello World - Script de Construcción"
echo "=============================================="

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para imprimir mensajes con color
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Verificar Java
print_info "Verificando versión de Java..."
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    print_success "Java encontrado: $JAVA_VERSION"
else
    print_error "Java no encontrado. Por favor instala Java 21."
    exit 1
fi

# Verificar Maven
print_info "Verificando Maven..."
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version | head -n 1)
    print_success "Maven encontrado: $MVN_VERSION"
    USE_MAVEN=true
else
    print_warning "Maven no encontrado. Usando compilación manual."
    USE_MAVEN=false
fi

# Limpiar construcción anterior
print_info "Limpiando construcción anterior..."
rm -rf target/
mkdir -p target

if [ "$USE_MAVEN" = true ]; then
    # Construcción con Maven
    print_info "Construyendo con Maven..."
    mvn clean package -q
    
    if [ $? -eq 0 ]; then
        print_success "Construcción con Maven completada exitosamente!"
        print_info "Archivo WAR generado: target/payara-hello-world.war"
    else
        print_error "Error en la construcción con Maven"
        exit 1
    fi
else
    # Construcción manual
    print_info "Construyendo manualmente..."
    
    # Crear directorios
    mkdir -p target/classes
    mkdir -p target/webapp/WEB-INF/classes
    mkdir -p target/webapp/WEB-INF/lib
    
    # Compilar Java (necesitarías las librerías de Jakarta EE)
    print_warning "Compilación manual requiere librerías de Jakarta EE en el classpath"
    print_info "Para una construcción completa, instala Maven con:"
    print_info "  sudo apt update && sudo apt install maven"
    
    # Copiar recursos web
    cp -r src/main/webapp/* target/webapp/ 2>/dev/null || true
    
    print_success "Estructura básica creada en target/webapp/"
fi

# Mostrar información de ejecución
echo ""
print_info "🚀 Para ejecutar la aplicación:"
echo ""

if [ "$USE_MAVEN" = true ]; then
    echo "  Con Payara Micro:"
    echo "    mvn payara-micro:start"
    echo ""
    echo "  Con Payara Server:"
    echo "    cp target/payara-hello-world.war \$PAYARA_HOME/glassfish/domains/domain1/autodeploy/"
else
    echo "  1. Instala Maven: sudo apt install maven"
    echo "  2. Ejecuta: mvn payara-micro:start"
fi

echo ""
print_info "📱 La aplicación estará disponible en:"
echo "    http://localhost:8080/payara-hello-world"
echo ""

print_success "¡Construcción completada! 🎉"
