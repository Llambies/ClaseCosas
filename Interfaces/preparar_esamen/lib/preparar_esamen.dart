import 'dart:io';

class CV2List {
  static String leerArchivo(String path) {
    File f = File(path);
    return f.readAsStringSync().replaceAll("\"", "");
  }

  static List<Map<String, dynamic>> parsearStringContenidoArchivo(
    String contenido,
    String separadorLineas,
    String separadorCampos,
  ) {
    List<String> cabeceras = contenido
        .split(separadorLineas)[0]
        .split(separadorCampos);

    return contenido
        .split(separadorLineas)
        .skip(1)
        .map((e) => aObjeto(cabeceras, e.split(separadorCampos)))
        .toList();
  }

  static Map<String, dynamic> aObjeto(
    List<String> cabeceras,
    List<String> campos,
  ) {
    Map<String, dynamic> objeto = {};

    for (var i = 0; i < cabeceras.length; i++) {
      objeto[cabeceras[i]] = campos[i];
    }

    return objeto;
  }
}
