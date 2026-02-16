import 'package:flutter/material.dart';
import 'package:flutter_application_2/core/colores_app.dart';
import 'package:flutter_application_2/models/tarea.dart';

/// Widget que representa una tarjeta visual para una tarea.
/// 
/// Muestra la información de una tarea en formato de tarjeta con:
/// - Checkbox para marcar/desmarcar como completada
/// - Título y descripción de la tarea
/// - Badge con la categoría
/// - Botones para editar y eliminar
/// - Handle de arrastre para reordenamiento (si se proporciona índice)
/// 
/// El botón de editar solo se muestra si la tarea no está completada.
/// Los colores se adaptan automáticamente al tema claro/oscuro.
class TarjetaTarea extends StatelessWidget {
  /// Crea una instancia de [TarjetaTarea].
  /// 
  /// [tarea] la tarea a mostrar en la tarjeta.
  /// [onEliminar] callback que se ejecuta cuando se presiona el botón eliminar.
  /// [onCambiarEstado] callback que se ejecuta cuando se cambia el estado de completado.
  /// [onEditar] callback que se ejecuta cuando se presiona el botón editar.
  /// [indiceReordenar] índice de la tarea en la lista para habilitar reordenamiento.
  const TarjetaTarea({
    super.key,
    required this.tarea,
    required this.onEliminar,
    required this.onCambiarEstado,
    required this.onEditar,
    this.indiceReordenar,
  });

  /// La tarea que se muestra en esta tarjeta.
  final Tarea tarea;
  
  /// Callback que se ejecuta cuando el usuario presiona el botón eliminar.
  final VoidCallback onEliminar;
  
  /// Callback que se ejecuta cuando el usuario cambia el estado de completado.
  final VoidCallback onCambiarEstado;
  
  /// Callback que se ejecuta cuando el usuario presiona el botón editar.
  final VoidCallback onEditar;
  
  /// Índice de la tarea en la lista para habilitar el reordenamiento.
  /// Si es `null`, el handle de arrastre no será funcional.
  final int? indiceReordenar;

  /// Construye el widget del handle de arrastre para reordenamiento.
  /// 
  /// Si [indiceReordenar] no es `null`, el handle será funcional y permitirá
  /// arrastrar la tarjeta para reordenarla. Si es `null`, solo se muestra
  /// visualmente pero no es funcional.
  /// 
  /// [esModoOscuro] indica si el tema actual es oscuro para aplicar los colores correctos.
  /// 
  /// Retorna un [Widget] con el handle de arrastre.
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
