import 'dart:io';
import 'dart:math';

class CSV2List {
  String pathAlArchivo;
  late List<Map<String, dynamic>> datos;
  late List<String> cabeceras;

  CSV2List(this.pathAlArchivo) {
    datos = [];
    datos = tratarDatosEntrada(cargarArchivo(), "\n", ";");
  }

  List<Map<String, dynamic>> tratarDatosEntrada(
    String listaDatos,
    String sepLinea,
    String sepCampos,
  ) {
    List<String> cabeceras = listaDatos.split(sepLinea)[0].split(sepCampos);

    return listaDatos
        .split(sepLinea)
        .skip(1)
        .where((e) => e.length > cabeceras.length)
        .map((e) {
          return aObjeto(cabeceras, e.split(sepCampos));
        })
        .toList();
  }

  String cargarArchivo() {
    if (pathAlArchivo.isEmpty) {
      return "";
    }
    File f = File(pathAlArchivo);
    if (!f.existsSync()) {
      print("No existe el archivo: ${f.absolute}");
      return "";
    }
    return f.readAsStringSync();
  }

  Map<String, dynamic> aObjeto(List<String> cabeceras, List<String> campos) {
    Map<String, dynamic> objeto = {};
    for (var i = 0; i < cabeceras.length; i++) {
      objeto[cabeceras[i]] = campos[i];
    }
    return objeto;
  }
}

class RepoDatos {
  static Future<List<Map<String, dynamic>>> devolverDatosTarde(String path) async {
    await Future.delayed(const Duration(seconds: 2));
    CSV2List c = CSV2List(path);
    return c.datos;
  }
}
