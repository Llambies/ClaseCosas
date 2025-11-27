import 'dart:io';

class Parseador {
  static String path = "";
  List<String> cabeceras = [];
  List<Map<String, dynamic>> datos = [];
  Map<String, double> horasPorEtiqueta = {};
  double totalHoras = 0;

  Parseador(String pathAlArchivo) {
    path = pathAlArchivo;
  }

  Future<void> parseador() async {
    var c = await lectorArchivos(path);
    datos = parsearCSVaListaMapas(c);
    horasPorEtiqueta = contadorHorasPorEtiqueta();
    totalHoras = contadorHorasTotales();
  }

  double contadorHorasTotales() {
    return datos.fold<double>(
      0,
      (prev, e) => prev + (double.tryParse(e["Duracion"] ?? "0.0") ?? 0),
    );
  }

  Map<String, double> contadorHorasPorEtiqueta() {
    Map<String, double> mapa = {};
    for (var dato in datos) {
      mapa.update(
        dato["Etiquetas"] ?? "Null",
        (value) => value += double.tryParse(dato["Duracion"] ?? "0.0") ?? 0,
        ifAbsent: () => double.tryParse(dato["Duracion"] ?? "0.0") ?? 0,
      );
    }
    return mapa;
  }

  List<Map<String, dynamic>> parsearCSVaListaMapas(String contenido) {
    final data = contenido.replaceAll("\"", "").split("\n");
    final datos = data.map((e) => e.split(",")).toList();
    cabeceras = datos[0];
    return datos.skip(1).map((e) => parsearObjeto(cabeceras, e)).toList();
  }

  Map<String, dynamic> parsearObjeto(
    List<String> cabeceras,
    List<String> datos,
  ) {
    Map<String, dynamic> obj = {};
    for (var i = 0; i < cabeceras.length; i++) {
      obj[cabeceras[i]] = datos[i];
    }
    return obj;
  }

  Future<String> lectorArchivos(String rutaAlArchivo) async {
    final file = File(rutaAlArchivo);
    final content = await file.readAsString();
    return content;
  }

  @override
  String toString() {
    String salida = "";
    for (var i = 0; i < horasPorEtiqueta.length; i++) {
      salida +=
          "${horasPorEtiqueta.keys.toList()[i] == "" ? "Sin asignar" : horasPorEtiqueta.keys.toList()[i]}: ${horasPorEtiqueta.values.toList()[i].toStringAsFixed(1)}h \n";
    }
    salida += "Horas totales: ${totalHoras.toStringAsFixed(1)}h";
    return salida;
  }
}
