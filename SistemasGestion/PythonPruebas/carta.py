import json

class Regalo:
    def __init__(self, cantidad, nombre):
        self.cantidad = cantidad
        self.nombre = nombre

    def __str__(self):
        return f"{self.cantidad}x {self.nombre}"

class Carta:
    def __init__(self, nombre, regalos):
        self.nombre = nombre
        self.regalos = regalos

    def __str__(self):
        return f"{self.nombre} {'\n'.join([regalo.__str__() for regalo in self.regalos])}"

def cargar_carta(nombre_archivo):
    with open(nombre_archivo, 'r') as archivo:
        contenido = json.load(archivo)
    return Carta(contenido["nombre"], [Regalo(regalo["cantidad"], regalo["nombre"]) for regalo in contenido["regalos"]])

if __name__ == "__main__":
    nombre_archivo = input("Ingrese el nombre del archivo: ")
    print(cargar_carta(nombre_archivo).__str__())