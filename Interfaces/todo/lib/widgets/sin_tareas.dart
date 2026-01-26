import 'package:flutter/material.dart';

/// Widget que se muestra cuando no hay tareas en la lista.
class SinTareas extends StatelessWidget {
  const SinTareas({super.key});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final colorScheme = theme.colorScheme;

    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            Icons.task_alt,
            size: 48,
            color: colorScheme.secondary,
          ),
          const SizedBox(height: 16),
          Text(
            'No hay tareas',
            style: theme.textTheme.bodyLarge?.copyWith(
              color: colorScheme.onSurface,
              fontSize: 18,
            ),
          ),
        ],
      ),
    );
  }
}