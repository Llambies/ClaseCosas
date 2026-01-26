# Vamos a simular carreras de caballos donde cada caballo es un hilo de ejecución
# Cada hilo de ejecución tendrá un nombre y un tiempo de ejecución
# El tiempo de ejecución será un número aleatorio entre 1 y 10
# El hilo de ejecución se ejecutará cada 1 segundo
# El hilo de ejecución se ejecutará hasta que el caballo haya recorrido 100 metros
# El caballo que haya recorrido 100 metros primero será el ganador

import threading
import random
import time
import os

# Lock para evitar condiciones de carrera en la impresión
lock = threading.Lock()

# Diccionario para guardar el estado de cada caballo
caballos_estado = {}

# Lista para guardar el orden de llegada con tiempos
podio = []

# Diccionario para guardar los tiempos de llegada de cada caballo
caballos_terminados = {}

# Tiempo de inicio de la carrera
tiempo_inicio = 0

def limpiar_pantalla():
    """Limpia la pantalla de la terminal"""
    os.system('clear' if os.name == 'posix' else 'cls')

def mostrar_carrera():
    """Muestra el estado actual de la carrera"""
    with lock:
        limpiar_pantalla()
        # Calcular tiempo transcurrido
        tiempo_actual = time.time() - tiempo_inicio
        minutos = int(tiempo_actual // 60)
        segundos = tiempo_actual % 60
        
        print("=" * 80)
        print("🏆 CARRERA DE CABALLOS 🏆".center(80))
        print("=" * 80)
        print(f"⏱️  Tiempo: {minutos}m {segundos:.2f}s".center(80))
        print("=" * 80)
        print()
        
        # Ordenar por nombre para mostrar consistentemente
        for nombre in sorted(caballos_estado.keys()):
            distancia = caballos_estado[nombre]
            # Calcular barras (cada metro = 1 guión, máximo 40 caracteres)
            barras = int((distancia / 100) * 40)
            barra_visual = "-" * barras + "🏇"
            
            # Mostrar la barra con el caballo
            porcentaje = min(100, distancia)
            
            # Si el caballo terminó, mostrar su tiempo de llegada
            if nombre in caballos_terminados:
                tiempo_llegada = caballos_terminados[nombre]
                print(f"{nombre:12} [{barra_visual:<40}]  {porcentaje:3d}% ✅ {tiempo_llegada:.2f}s")
            else:
                print(f"{nombre:12} [{barra_visual:<40}]  {porcentaje:3d}%")
        
        print()
        print("=" * 80)

def caballo(nombre):
    """Función que ejecuta cada caballo en su hilo"""
    distancia = 0
    
    # Inicializar el caballo en el diccionario
    with lock:
        caballos_estado[nombre] = distancia
    
    while distancia < 100:
        distancia += random.randrange(0, 2)
        # No exceder 100
        if distancia > 100:
            distancia = 100
        
        # Actualizar estado
        with lock:
            caballos_estado[nombre] = distancia
        
        # Mostrar carrera actualizada
        mostrar_carrera()
        
        time.sleep(0.1)
    
    # Registrar el orden de llegada con el tiempo
    tiempo_llegada = time.time() - tiempo_inicio
    with lock:
        caballos_terminados[nombre] = tiempo_llegada
        podio.append((nombre, tiempo_llegada))

def mostrar_podio():
    """Muestra el podio final con las posiciones y tiempos"""
    limpiar_pantalla()
    print("\n" * 2)
    print("=" * 80)
    print("🏆 PODIO FINAL - CARRERA DE CABALLOS 🏆".center(80))
    print("=" * 80)
    print()
    
    medallas = ["🥇", "🥈", "🥉"]
    
    for i, (caballo_ganador, tiempo) in enumerate(podio):
        if i < 3:
            medalla = medallas[i]
            posicion = ["1º LUGAR", "2º LUGAR", "3º LUGAR"][i]
        else:
            medalla = "  "
            posicion = f"{i+1}º LUGAR"
        
        minutos = int(tiempo // 60)
        segundos = tiempo % 60
        tiempo_formateado = f"{minutos}m {segundos:.2f}s" if minutos > 0 else f"{segundos:.2f}s"
        
        print(f"  {medalla} {posicion:15} → {caballo_ganador:15} | Tiempo: {tiempo_formateado}")
    
    print()
    print("=" * 80)
    print()

if __name__ == "__main__":
    caballos_nombre = [f"Caballo {i+1}" for i in range(8)]
    
    # Registrar el tiempo de inicio
    tiempo_inicio = time.time()
    
    # Crear e iniciar los hilos
    hilos = []
    for nombre in caballos_nombre:
        hilo = threading.Thread(target=caballo, args=(nombre,), daemon=True)
        hilos.append(hilo)
        hilo.start()
    
    # Esperar a que terminen todos
    for hilo in hilos:
        hilo.join()
    
    # Mostrar el podio final
    mostrar_podio()