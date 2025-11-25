class CSV2List {
  static List<Map<String, dynamic>> parseToMap({
    required List<String> datosCSV,
    required int numCmp,
    String sepCmp = ',',
  }) {
    List<String> cabeceras = _obtenerCampos(listaCad: datosCSV);
    List<List<String>> datosCSVLimpios = _listaCad2ListaDeListas(
      listaCad: datosCSV,
      numCmp: numCmp,
      sepCmp: sepCmp,
    ).skip(1).toList();

    return _lista2Map(lista: datosCSVLimpios, campos: cabeceras);
  }

  static List<String> limpiarLista({required List<String> listaDatos}) {
    return listaDatos.where((e) {
      return e.isNotEmpty;
    }).toList();
  }

  static List<String> _obtenerCampos({
    required List<String> listaCad,
    String sepCmp = ',',
  }) {
    return listaCad[0].split(sepCmp);
  }

  static List<List<String>> _listaCad2ListaDeListas({
    required List<String> listaCad,
    required int numCmp,
    String sepCmp = ',',
  }) {
    return listaCad
        .map((e) {
          return e.split(sepCmp);
        })
        .where((e) {
          return limpiarLista(listaDatos: e).length >= numCmp;
        })
        .toList();
  }

  static List<Map<String, dynamic>> _lista2Map({
    required List<List<String>> lista,
    required List<String> campos,
  }) {
    return lista.map((e) {
      Map<String, dynamic> obj = {};
      for (var i = 0; i < campos.length; i++) {
        obj[campos[i]] = e[i];
      }
      return obj;
    }).toList();
  }
}

class repo_datos {
  static Future<List<Map<String, dynamic>>> leerTarde({
    required List<String> datosCSV,
    required int numCmp,
    String sepCmp = ',',
  }) async {
    return Future.delayed(const Duration(seconds: 1), () {
      return CSV2List.parseToMap(
        datosCSV: datosCSV,
        numCmp: numCmp,
        sepCmp: sepCmp,
      );
    });
  }
}
