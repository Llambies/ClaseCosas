import 'package:preparar_esamen/preparar_esamen.dart' as preparar_esamen;

void main(List<String> arguments) {
  print(
    preparar_esamen.CV2List.parsearStringContenidoArchivo(
      preparar_esamen.CV2List.leerArchivo("./data/datos_2024.csv"),
      "\n",
      ";",
    ),
  );
}
