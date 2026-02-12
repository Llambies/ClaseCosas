import 'package:categoria/core/colores.dart';
import 'package:flutter/material.dart';

class BotonColor extends StatelessWidget {
  final Colores color;
  final Function(Colores) onColorSelected;
  const BotonColor({
    super.key,
    required this.color,
    required this.onColorSelected,
  });

  @override
  Widget build(BuildContext context) {
    return PopupMenuButton(
      icon: Icon(Icons.color_lens),
      color: color.color,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
      itemBuilder: (context) {
        return List.generate(
          Colores.values.length,
          (index) => PopupMenuItem(
            child: Wrap(
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Icon(
                    Icons.opacity,
                    size: 20,
                    color: Colores.values[index].color,
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(Colores.values[index].etiqueta),
                ),
              ],
            ),
            onTap: () => onColorSelected(Colores.values[index]),
          ),
        );
      },
    );
  }
}
