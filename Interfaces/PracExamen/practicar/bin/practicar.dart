import 'package:practicar/practicar.dart' as practicar;

Future<void> main(List<String> arguments) async {
  final datos = await practicar.RepoDatos.devolverDatosTarde(
    "data/datos.txt",
  );
  // print(filtrarPorFecha(datos, "date", "2020-11-30"));
  // print(mapasConCampos(datos, ["date"]));
  // print(mapasConCampos(calcularIncidencias(datos), ["ia7d", "ia14d"]));
  imprimirDatos(datos, "ccaa", Orden.ascendente);
}

List<Map<String, dynamic>> filtrarPorFecha(
  List<Map<String, dynamic>> datos,
  String propiedad,
  String fecha,
) {
  return datos.where((dato) => dato[propiedad] == fecha).toList();
}

List<Map<String, dynamic>> mapasConCampos(
  List<Map<String, dynamic>> datos,
  List<String> campos,
) {
  return datos.map((dato) {
    Map<String, dynamic> mapa = {};
    for (int i = 0; i < campos.length; i++) {
      mapa[campos[i]] = dato[campos[i]];
    }
    return mapa;
  }).toList();
}

List<Map<String, dynamic>> calcularIncidencias(
  List<Map<String, dynamic>> datos,
) {
  return datos.map((dato) {
    String campo7d = dato["cases_PCR_7days"] == ""
        ? dato["cases_7days"]
        : dato["cases_PCR_7days"];
    double casos7d = double.tryParse(campo7d) == null
        ? 0
        : double.parse(campo7d);
    double poblacion = double.tryParse(dato["poblacion"]) == null
        ? 1
        : double.parse(dato["poblacion"]);
    dato["ia7d"] = casos7d / poblacion * 1000000;

    String campo14d = dato["cases_PCR_14days"] == ""
        ? dato["cases_14days"]
        : dato["cases_PCR_14days"];
    double casos14d = double.tryParse(campo14d) == null
        ? 0
        : double.parse(campo14d);

    dato["ia14d"] = casos14d / poblacion * 1000000;
    return dato;
  }).toList();
}

void imprimirDatos(
  List<Map<String, dynamic>> datos,
  String campo,
  Orden orden,
) {
  var l =
      mapasConCampos(calcularIncidencias(datos), [
        "ine_code",
        "ccaa",
        "ia7d",
        "ia14d",
      ]).map((dato) {
        return dato;
      }).toList();

  if (orden == Orden.descendente) {
    l.sort((a, b) => a[campo].compareTo(b[campo]));
  } else {
    l.sort((a, b) => b[campo].compareTo(a[campo]));
  }

  print(l);
}

enum Orden { ascendente, descendente }
