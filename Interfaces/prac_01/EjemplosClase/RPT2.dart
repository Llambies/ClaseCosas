import 'dart:io';
import 'dart:math';

enum Movimientos { roca, papel, tijeras}

void main() {
  final aleatorio = Random();
  int jugador= 0;
  int IA = 0;
  int empate=0;
  while (true) {
    stdout.write('Roca, papel, tijeras o salir (r/p/t/s) ');
    final entrada = stdin.readLineSync()?.toLowerCase();
    final valor = aleatorio.nextInt(3);
    final movIA = Movimientos.values[valor];

    switch((entrada, movIA)) {
      case ('s', _):
        print ("Resultado final: Tu = ${jugador}/IA = ${IA}/Empate = ${empate}");
        return;
      
      case ('r', Movimientos.tijeras):
      case ('p', Movimientos.roca):
      case ('t', Movimientos.papel):
        jugador++;
        print("Ganas Tú: ${jugador}/IA: ${IA}/Empate = ${empate}");

      case ('r', Movimientos.papel):
      case ('p', Movimientos.tijeras):
      case ('t', Movimientos.roca):
        IA++;
        print("Pierdes Tú: ${jugador}/IA: ${IA}/Empate = ${empate}");

      case ('r', Movimientos.roca):
      case ('p', Movimientos.papel):
      case ('t', Movimientos.tijeras):
        empate++;
        IA++;
        print("Empate Tú: ${jugador}/IA: ${IA}/Empate = ${empate}");

      case(_):
        print ("Opción no válida");
        continue;  
    }
    print('Tu jugastes: ${entrada}');
    print('La IA jugó: ${movIA.name}');
  }
}