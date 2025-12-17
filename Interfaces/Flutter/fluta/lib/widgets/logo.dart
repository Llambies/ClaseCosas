import 'package:flutter/material.dart';

class Logo extends StatelessWidget {
  final String titulo;
  final String descripcion;
  const Logo({super.key, required this.titulo, required this.descripcion});

  @override
  Widget build(BuildContext context) {
    return Center(child: Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Image.asset('assets/images/logo.png', width: 100, height: 100),
        Text(titulo, style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
        Text(descripcion, style: const TextStyle(fontSize: 16)),
      ],
    ));
  }
}