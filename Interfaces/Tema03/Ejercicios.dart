// Ejercicio 1
int numeroEntero = 1;
double numeroDecimal = 1.0;
num numeroEnteroODecimal = 1;

String numeroEnteroComoTexto = numeroEntero.toString();
double parsearStringADouble = double.parse("3.14");
// Ejercicio 2
String lenguajes = '''
Dart
Java
Kotlin
''';
int longitud = lenguajes.length;
String lenguajesEnUnaLinea = "${lenguajes}";
// Ejercicio 3
List<int> numeros = [1, 2, 3, 4, 5];
List<int> paresMultiplicados = numeros
    .where((numero) => numero % 2 == 0) // Filtra los números pares
    .map((numero) => numero * 10) // Multiplica por 10
    .toList(); // Convierte el iterable en una lista

// Ejercicio 4
Set<String> lenguajesUnicos = {'dart', 'flutter', 'dart', 'firebase'};
int longitudSet = lenguajesUnicos.length; // Debería ser 3

Set<String> masLenguajes = {'dart', 'grpc'};
Set<String> union = lenguajesUnicos.union(
  masLenguajes,
); // Union -> {dart, flutter, firebase, grpc}
Set<String> interseccion = lenguajesUnicos.intersection(
  masLenguajes,
); // Intersección -> {dart}
Set<String> diferencia = lenguajesUnicos.difference(
  masLenguajes,
); // Diferencia -> {flutter, firebase}

// Ejercicio 5

var usuarios = <String, Object>{'id': 0, 'nombre': "Ada", 'activo': false};

void agregarCorreo(Map<String, Object> usuarios) {
  usuarios['email'] = 'correo';
}

void cambiarEstadoActivo(Map<String, Object> usuarios) {
  usuarios['activo'] = true;
}

void mostrarParesClaveValor(Map<String, Object> usuarios) {
  for (var i = 0; i < usuarios.keys.length; i++) {
    print("${usuarios.keys.elementAt(i)}: ${usuarios.values.elementAt(i)}");
  }
}

// Ejercicio 6
void saludador(String nombre) {
  print("Hola ${nombre.length > 0 ? nombre.trim() : "anónimo"}");
}

// Ejercicio 7
const double iva = 0.21;
List<double> precios = [10.0, 20.0, 30.0];
List<String> preciosConIva = precios
    .map((precio) => precio * (1 + iva))
    .map((precio) => precio.toStringAsPrecision(3))
    .toList();

// Ejercicio 8
enum Prioridad { alta, media, baja }

void horasPorPrioridad(Prioridad prioridad) {
  switch (prioridad) {
    case Prioridad.alta:
      print("8 horas");
      break;
    case Prioridad.media:
      print("4 horas");
      break;
    case Prioridad.baja:
      print("2 horas");
      break;
  }
}