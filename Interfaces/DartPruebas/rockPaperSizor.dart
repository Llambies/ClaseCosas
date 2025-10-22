import 'dart:io';
import 'dart:math';

enum Movimientos { piedra, papel, tijera, lagarto, spock }

List<List<int>> ganaA = [[2, 3], [0, 4], [1, 3], [1, 4], [0, 2]];

int victoriasJugador = 0;
int victoriasComputadora = 0;
int empates = 0;

void jugar(int intMovimientoJugador) {
  final movimientoJugador = Movimientos.values[intMovimientoJugador];

  final intMovimientoComputadora = Random().nextInt(5);
  final movimientoComputadora = Movimientos.values[intMovimientoComputadora];

  print("Jugador: $movimientoJugador");
  print("Computadora: $movimientoComputadora");

  if (ganaA[intMovimientoJugador].contains(intMovimientoComputadora)) {
    GanoJugador();
  } else if (ganaA[intMovimientoComputadora].contains(intMovimientoJugador)) {
    GanoComputadora();
  } else {
    Empate();
  }
}

void GanoJugador() {
  print("Gana el jugador");
  victoriasJugador++;
}

void GanoComputadora() {
  print("Gana la computadora");
  victoriasComputadora++;
}

void Empate() {
  print("Empate");
  empates++;
}

void main(List<String> args) {
  while (true) {
    print("Ingrese su movimiento [0: piedra, 1: papel, 2: tijera, 3: lagarto, 4: spock, 5: salir]");
    final entrada = stdin.readLineSync();

    if (entrada == null) {
      print("No se ingreso un valor");
      continue;
    } else if (entrada.isEmpty) {
      print("No se ingreso un valor");
      continue;
    } else if (int.parse(entrada) < 0 || int.parse(entrada) > 5) {
      print("No se ingreso un valor valido");
      continue;
    }

    final intMovimientoJugador = int.parse(entrada);
    if (intMovimientoJugador == 5) {
      break;
    }
    jugar(intMovimientoJugador);
    print("Victorias jugador: $victoriasJugador");
    print("Victorias computadora: $victoriasComputadora");
    print("Empates: $empates");
  }
}
