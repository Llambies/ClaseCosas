import 'dart:io';
import 'dart:collection';
import 'package:examen/examen.dart' as examen;

Future<void> main(List<String> arguments) async {
  List<String> datosCSV = File("./data/datos.txt").readAsLinesSync();
  //print(examen.CSV2List.parseToMap(datosCSV: datosCSV, numCmp: 6));
  //print(await examen.repo_datos.leerTarde(datosCSV: datosCSV, numCmp: 6));

  print(
    filtrarPorCampo(
      lista: examen.CSV2List.parseToMap(datosCSV: datosCSV, numCmp: 6),
      campo: "Name",
      valor: "buick skylark 320"
    ),
  );

  print(
    ordenarPorCampo(
      lista: examen.CSV2List.parseToMap(datosCSV: datosCSV, numCmp: 6),
      campo: "Name",
    ),
  );
}

List filtrarPorCampo({
  required List lista,
  required String campo,
  required dynamic valor,
}) {
  return lista.where((e) {
    return e[campo] == valor;
  }).toList();
}

/// Aqui no me carga el compareNatural,
/// no se a que se debera
List ordenarPorCampo({
  required List lista,
  required String campo,
  bool ascendente = true,
}) {
  lista.sort((a, b) {
    return Comparable.compare(a[campo], b[campo]);
  });

  return lista;
}
