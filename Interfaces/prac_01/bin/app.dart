import 'package:prac_01/tratador.dart';

void main(List<String> arguments) {
  Parseador p = Parseador("./data/datos_2024.csv");
  p.parseador().then((value) => {print(p.toString())});
}
