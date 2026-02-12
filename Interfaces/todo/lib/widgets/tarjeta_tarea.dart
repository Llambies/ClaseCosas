import 'package:flutter/material.dart';
import 'package:flutter_application_2/core/colores_app.dart';
import 'package:flutter_application_2/models/tarea.dart';

class TarjetaTarea extends StatelessWidget {
  const TarjetaTarea({
    super.key,
    required this.tarea,
    required this.onEliminar,
    required this.onCambiarEstado,
    required this.onEditar,
    this.indiceReordenar,
  });

  final Tarea tarea;
  final VoidCallback onEliminar;
  final VoidCallback onCambiarEstado;
  final VoidCallback onEditar;
  final int? indiceReordenar;

  Widget _construirHandleArrastre(bool esModoOscuro) {
    final handleWidget = Container(
      margin: const EdgeInsets.only(right: 12, top: 2),
      width: 28,
      height: 28,
      decoration: BoxDecoration(
        color: (esModoOscuro ? ColoresApp.orangeBright : ColoresApp.orange).withValues(alpha: 0.15),
        borderRadius: BorderRadius.circular(6),
      ),
      child: Icon(
        Icons.drag_handle,
        size: 18,
        color: esModoOscuro ? ColoresApp.orangeBright : ColoresApp.orange,
      ),
    );

    if (indiceReordenar != null) {
      return ReorderableDragStartListener(
        index: indiceReordenar!,
        child: handleWidget,
      );
    }
    return handleWidget;
  }

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final colorScheme = theme.colorScheme;
    final esModoOscuro = theme.brightness == Brightness.dark;
    
    // Colores Gruvbox
    final completedColor = esModoOscuro
        ? ColoresApp.gray
        : ColoresApp.grayBright;
    
    // Color de fondo de la tarjeta
    final cardColor = esModoOscuro
        ? ColoresApp.darkSurface1
        : ColoresApp.lightSurface1;
    
    // Color del borde
    final borderColor = esModoOscuro
        ? ColoresApp.darkSurface2
        : ColoresApp.lightSurface2;
    
    // Color del checkbox no marcado
    final checkboxBorderColor = esModoOscuro
        ? ColoresApp.gray
        : ColoresApp.grayBright;
    
    return Container(
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
      decoration: BoxDecoration(
        color: cardColor,
        borderRadius: BorderRadius.circular(10),
        border: Border.all(
          color: borderColor,
          width: 1,
        ),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 14),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            // Handle de reordenamiento - solo este icono activa el arrastre
            _construirHandleArrastre(esModoOscuro),
            // Checkbox minimalista
            Padding(
              padding: const EdgeInsets.only(top: 2),
              child: AnimatedSwitcher(
                duration: const Duration(milliseconds: 200),
                child: GestureDetector(
                  key: ValueKey<bool>(tarea.estaTerminada),
                  onTap: () => onCambiarEstado(),
                  child: Container(
                    width: 22,
                    height: 22,
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      border: Border.all(
                        color: tarea.estaTerminada
                            ? (esModoOscuro ? ColoresApp.greenBright : ColoresApp.green)
                            : checkboxBorderColor,
                        width: 2,
                      ),
                      color: tarea.estaTerminada
                          ? (esModoOscuro ? ColoresApp.greenBright : ColoresApp.green)
                          : Colors.transparent,
                    ),
                    child: tarea.estaTerminada
                        ? Icon(
                            Icons.check,
                            size: 14,
                            color: esModoOscuro ? ColoresApp.darkHard : ColoresApp.lightHard,
                          )
                        : null,
                  ),
                ),
              ),
            ),
            const SizedBox(width: 14),
            // Título y detalles
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisSize: MainAxisSize.min,
                children: [
                  // Título principal
                  Text(
                    tarea.titulo,
                    style: TextStyle(
                      decoration: tarea.estaTerminada
                          ? TextDecoration.lineThrough
                          : null,
                      color: tarea.estaTerminada
                          ? completedColor
                          : colorScheme.onSurface,
                      fontSize: 15,
                      fontWeight: FontWeight.w400,
                      letterSpacing: -0.1,
                      height: 1.4,
                    ),
                  ),
                  // Descripción (solo si hay descripción)
                  if (tarea.descripcion.isNotEmpty) ...[
                    const SizedBox(height: 6),
                    Text(
                      tarea.descripcion,
                      style: TextStyle(
                        color: completedColor.withValues(alpha: 0.8),
                        fontSize: 13,
                        letterSpacing: -0.1,
                        height: 1.3,
                      ),
                      maxLines: 2,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ],
                  // Categoría como badge pequeño
                  if (tarea.descripcion.isEmpty) ...[
                    const SizedBox(height: 6),
                  ] else ...[
                    const SizedBox(height: 8),
                  ],
                  Container(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 7,
                      vertical: 3,
                    ),
                    decoration: BoxDecoration(
                      color: (esModoOscuro ? ColoresApp.purpleBright : ColoresApp.purple).withValues(alpha: 0.15),
                      borderRadius: BorderRadius.circular(5),
                    ),
                    child: Text(
                      '${tarea.categoria.name[0].toUpperCase()}${tarea.categoria.name.substring(1)}',
                      style: TextStyle(
                        color: esModoOscuro ? ColoresApp.purpleBright : ColoresApp.purple,
                        fontSize: 10,
                        fontWeight: FontWeight.w600,
                        letterSpacing: 0.3,
                      ),
                    ),
                  ),
                ],
              ),
            ),
            // Iconos de acción
            const SizedBox(width: 8),
            Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                // Icono de editar (solo si no está terminada)
                if (!tarea.estaTerminada)
                  IconButton(
                    onPressed: onEditar,
                    icon: Icon(
                      Icons.edit_outlined,
                      color: esModoOscuro ? ColoresApp.aquaBright : ColoresApp.aqua,
                      size: 20,
                    ),
                    constraints: const BoxConstraints(
                      minWidth: 36,
                      minHeight: 36,
                    ),
                    padding: EdgeInsets.zero,
                    tooltip: 'Editar',
                  ),
                // Icono de eliminar
                IconButton(
                  onPressed: onEliminar,
                  icon: Icon(
                    Icons.delete_outline,
                    color: esModoOscuro ? ColoresApp.redBright : ColoresApp.red,
                    size: 20,
                  ),
                  constraints: const BoxConstraints(
                    minWidth: 36,
                    minHeight: 36,
                  ),
                  padding: EdgeInsets.zero,
                  tooltip: 'Eliminar',
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
