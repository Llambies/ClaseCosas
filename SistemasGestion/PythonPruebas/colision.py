import threading
import time
import random
import os

# Configuración de la pantalla
ANCHO_PANTALLA = 80
TIEMPO_REFRESCO = 0.02  # segundos entre actualizaciones

# Variables compartidas entre hilos
posicion_izquierda = 0
posicion_derecha = ANCHO_PANTALLA - 1
colision = False
lock = threading.Lock()
punto_colision = None


def limpiar_pantalla():
    """Limpia la terminal"""
    os.system('clear' if os.name == 'posix' else 'cls')


def dibujar_pantalla():
    """Dibuja el estado actual de los proyectiles"""
    global posicion_izquierda, posicion_derecha
    
    linea = ['-'] * (ANCHO_PANTALLA - 2)
    
    with lock:
        pos_izq = int(posicion_izquierda)
        pos_der = int(posicion_derecha)
    
    # Limitar posiciones al rango válido
    pos_izq = max(0, min(pos_izq, ANCHO_PANTALLA - 1))
    pos_der = max(0, min(pos_der, ANCHO_PANTALLA - 1))
    
    if pos_izq == pos_der:
        linea[pos_izq] = 'X'  # Colisión
    else:
        linea[pos_izq] = '>'  # Proyectil izquierdo
        linea[pos_der] = '<'  # Proyectil derecho
    
    return ''.join(linea)


def proyectil_izquierdo(velocidad):
    """Hilo del proyectil que viene desde la izquierda"""
    global posicion_izquierda, colision, punto_colision
    
    while not colision:
        with lock:
            posicion_izquierda += velocidad
            
            # Verificar colisión
            if posicion_izquierda >= posicion_derecha:
                colision = True
                punto_colision = (posicion_izquierda + posicion_derecha) / 2
        
        time.sleep(TIEMPO_REFRESCO)


def proyectil_derecho(velocidad):
    """Hilo del proyectil que viene desde la derecha"""
    global posicion_derecha, colision, punto_colision
    
    while not colision:
        with lock:
            posicion_derecha -= velocidad
            
            # Verificar colisión
            if posicion_izquierda >= posicion_derecha:
                colision = True
                punto_colision = (posicion_izquierda + posicion_derecha) / 2
        
        time.sleep(TIEMPO_REFRESCO)


def mostrar_animacion():
    """Hilo que muestra la animación en pantalla"""
    while not colision:
        limpiar_pantalla()
        print("=" * ANCHO_PANTALLA)
        print("  SIMULACIÓN DE COLISIÓN DE PROYECTILES")
        print("=" * ANCHO_PANTALLA)
        print()
        print(f"  Proyectil izquierdo (>): posición {posicion_izquierda:.1f}")
        print(f"  Proyectil derecho   (<): posición {posicion_derecha:.1f}")
        print()
        print("[" + dibujar_pantalla() + "]")
        print()
        time.sleep(TIEMPO_REFRESCO)


def main():
    global posicion_izquierda, posicion_derecha, colision, punto_colision
    
    # Reiniciar variables
    posicion_izquierda = 0
    posicion_derecha = ANCHO_PANTALLA - 1
    colision = False
    punto_colision = None
    
    # Velocidades aleatorias entre 0.5 y 2.0 unidades por frame
    velocidad_izq = random.uniform(0.5, 2.0)
    velocidad_der = random.uniform(0.5, 2.0)
    
    limpiar_pantalla()
    print("=" * ANCHO_PANTALLA)
    print("  SIMULACIÓN DE COLISIÓN DE PROYECTILES")
    print("=" * ANCHO_PANTALLA)
    print()
    print(f"  Velocidad proyectil izquierdo: {velocidad_izq:.2f}")
    print(f"  Velocidad proyectil derecho:   {velocidad_der:.2f}")
    print()
    print("  Iniciando simulación en 2 segundos...")
    time.sleep(2)
    
    # Crear hilos
    hilo_izq = threading.Thread(target=proyectil_izquierdo, args=(velocidad_izq,))
    hilo_der = threading.Thread(target=proyectil_derecho, args=(velocidad_der,))
    hilo_pantalla = threading.Thread(target=mostrar_animacion)
    
    # Iniciar hilos
    hilo_izq.start()
    hilo_der.start()
    hilo_pantalla.start()
    
    # Esperar a que terminen
    hilo_izq.join()
    hilo_der.join()
    hilo_pantalla.join()
    
    # Mostrar resultado final
    limpiar_pantalla()
    print("=" * ANCHO_PANTALLA)
    print("  ¡COLISIÓN DETECTADA!")
    print("=" * ANCHO_PANTALLA)
    print()
    print(f"  Velocidad proyectil izquierdo: {velocidad_izq:.2f}")
    print(f"  Velocidad proyectil derecho:   {velocidad_der:.2f}")
    print()
    print(f"  Punto de colisión: {punto_colision:.1f} de {ANCHO_PANTALLA}")
    print(f"  Porcentaje desde la izquierda: {(punto_colision/ANCHO_PANTALLA)*100:.1f}%")
    print()
    
    # Dibujar posición final
    linea_final = ['-'] * (ANCHO_PANTALLA - 2)
    pos_colision = int(punto_colision)
    pos_colision = max(0, min(pos_colision, ANCHO_PANTALLA - 1))
    linea_final[pos_colision] = 'X'
    print("[" + ''.join(linea_final) + "]")
    print()
    
    # Análisis del resultado
    if punto_colision < ANCHO_PANTALLA / 2:
        print("  El proyectil derecho era más rápido (colisión en la mitad izquierda)")
    elif punto_colision > ANCHO_PANTALLA / 2:
        print("  El proyectil izquierdo era más rápido (colisión en la mitad derecha)")
    else:
        print("  ¡Ambos proyectiles tenían la misma velocidad efectiva!")


if __name__ == "__main__":
    ANCHO_PANTALLA = os.get_terminal_size().columns
    main()
