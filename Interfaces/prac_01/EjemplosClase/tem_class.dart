void main() {
  const datos = '''

         fecha   ;    tmin;tmax; ;     
      ;;;
      ;
         2025/09/05;20.5;32;
      2025/09/06;19;31.7;
      2025/09/07;21.2;32;555;
      2025/09/08;20;29;
      2025/09/08;21;;
      2025/09/09;16.5;27;
      2025/09/10;20;33.5;
      2025/09/11;18;28;
      2025/09/12;;25;
      2025/09/13;17;30;

    ''';
    final lista = csv2Lista(datos: datos);
    print(lista);

    final campos = getFields(datos: lista);
    print(campos);

    final registros = getRegistros(datos: lista, numCmp: campos.length);
    print(registros);

    final listaMapas = aListaMapas(datos: registros, nomCampos: campos);
    print(listaMapas);

    final ordTmin = ordListMaps(lista:listaMapas, campo:'tmin');
    print(ordTmin);

    final valorMin = valorMinimo(lista:listaMapas, campo:'tmin');
    print(valorMin);
}

List<String> csv2Lista({required String datos, String sepReg = '\n'}) {
  return datos
          .split(sepReg)
          .map((e) => e.trim())
          .where((e) => e.isNotEmpty)
          .toList();
}

List<String> getFields({required List<String> datos, String sepCmp = ';'}) {
  return datos
            .removeAt(0)
            .split(sepCmp)
            .map((e) => e.trim())
            .where((e) => e.isNotEmpty)
            .toList();
}

// 2025/09/05;20.5;32; -> ["2025/09/05", "20.5", "32"]

List getRegistros({required List<String> datos, required int numCmp, String sepCmp = ';'}) {
  return datos
            .map((e) {
              return e.split(sepCmp)
                      .where((e) => e.isNotEmpty);
            })
            .where((e) => e.length == numCmp)
            .toList();
}


// ["2025/09/05", "20.5", "32"] -> [{'fecha': '2025/09/05', 'tmin': '20.5', 'tmax': '32' }]

List aListaMapas({required List datos, required List<String> nomCampos}) {
    return datos
              .map((e) {
                var inx = 0;
                final res = e.fold({}, (acc, v) {
                  acc[nomCampos[inx]] = v;
                  inx++;
                  return acc;
                });
                return res;
              }).toList();


}

List ordListMaps({ required List lista, required String campo, bool ordAsc = true}) {
  lista.sort((a, b) => a[campo].compareTo(b[campo]));
  if(!ordAsc) lista = lista.reversed.toList();
  return lista;
}

Map valorMinimo({required List lista, required String campo}) {
  return lista.reduce((a,b) => double.parse(a[campo]) <  double.parse(b[campo]) ? a : b);
}

// Buscar la primera coincidencia y devolver el Map correspondiente o null si no existe coincidencias
Map buscarPrimero({required List lista, required String campo, required String valor}) {
  return;
}


// Buscar todos los elementos coincidentes y devolver una lista con los Map correspondientes o null si no existe coincidencias
List buscarTodos({required List lista, required String campo, required String valor}) {
  return;
}
