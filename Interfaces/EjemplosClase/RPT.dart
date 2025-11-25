import 'dart:io';
import 'dart:math';

enum Movimientos { roca, papel, tijeras}

void main() {
  final aleatorio = Random();
  int jugador= 0;
  int IA = 0;
  int empate=0;

  while (true) {
    stdout.write('Roca, papel, tijeras o salir (r/p/t/s)');
    final entrada = stdin.readLineSync()?.toLowerCase();
    final valor = aleatorio.nextInt(3);
    final movIA = Movimientos.values[valor];
    var movJ=null;

    switch(entrada) {
      case ('s'):
        print ("Resultado final: Tu = ${jugador}/IA = ${IA}");
        return;
      
      case ('r'):
        movJ = Movimientos.roca;

      case ('p'):
        movJ = Movimientos.papel;

      case ('t'):
        movJ = Movimientos.tijeras;

      case(_):
        print ("Opción no válida");
        break;  
    }
    
    if (movJ == movIA) {
      empate++;
      print("Empate");
    } else if (movJ == Movimientos.roca && movIA == Movimientos.tijeras ||
      movJ == Movimientos.papel && movIA == Movimientos.roca ||
      movJ == Movimientos.tijeras && movIA == Movimientos.papel) {
      jugador++;
      print("Ganas (Tú: ${jugador}/IA: ${IA})");
    } else {
      IA++;
      print("Pierdes (Tú: ${jugador}/IA: ${IA})");
    }
  
  }
}