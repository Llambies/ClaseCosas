import 'dart:io';


void main(List<String> argumentos) {
  if(argumentos.isEmpty) {
    print("Uso: dart tem_class_fic.dart fichero.txt");
    exit(1);
  }

    final fichero = argumentos.first;
    final datos = File(fichero).readAsLinesSync();

    final lista = csv2Lista(datos: datos);
    print(lista);

    final campos = getFields(datos: lista);
    print(campos);

    final registros = getRegistros(datos: lista, numCmp: campos.length);
    print(registros);

    final listaMapas = aListaMapas(datos: registros, nomCampos: campos);
    print(listaMapas);
}

List<String> csv2Lista({required List<String> datos, String sepReg = '\n'}) {
  return datos
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