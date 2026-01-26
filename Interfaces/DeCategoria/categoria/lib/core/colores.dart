import 'package:flutter/material.dart';

enum Colores {
  purpuraOscuro('Púrpura Oscuro', Colors.deepPurpleAccent),
  purpuraClaro('Púrpura Claro', Colors.purpleAccent),
  grisOscuro('Gris Oscuro', Colors.blueGrey),
  grisClaro('Gris Claro', Colors.grey),
  blanco('Blanco', Colors.white),
  negro('Negro', Colors.black),
  rojo('Rojo', Colors.red),
  verde('Verde', Colors.green),
  azul('Azul', Colors.blue),
  amarillo('Amarillo', Colors.yellow),
  naranja('Naranja', Colors.orange),
  morado('Morado', Colors.purple),
  rosa('Rosa', Colors.pink),
  lilac('Lilac', Colors.deepPurple),
  indigo('Indigo', Colors.indigo);

  const Colores(this.etiqueta, this.color);
  final String etiqueta;
  final Color color;
}