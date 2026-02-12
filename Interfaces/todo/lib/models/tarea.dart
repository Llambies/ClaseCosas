/// Enumeración de categorías disponibles para las tareas.
enum Categoria {
  personal,
  trabajo,
  otro,
}

/// Modelo de datos para una tarea.
/// 
/// Incluye un ID único generado automáticamente para identificación
/// en listas y operaciones de estado.
class Tarea {
  Tarea({
    String? id,
    required this.titulo,
    required this.descripcion,
    required this.estaTerminada,
    required this.categoria,
  }) : id = id ?? DateTime.now().millisecondsSinceEpoch.toString();

  /// Identificador único de la tarea
  final String id;
  
  /// Título de la tarea
  final String titulo;
  
  /// Descripción detallada de la tarea
  final String descripcion;
  
  /// Categoría de la tarea
  final Categoria categoria;
  
  /// Estado de completado de la tarea
  final bool estaTerminada;

  /// Crea una copia de la tarea con los campos especificados modificados.
  Tarea copyWith({
    String? titulo,
    String? descripcion,
    Categoria? categoria,
    bool? estaTerminada,
  }) {
    return Tarea(
      id: id, // Mantiene el mismo ID
      titulo: titulo ?? this.titulo,
      descripcion: descripcion ?? this.descripcion,
      categoria: categoria ?? this.categoria,
      estaTerminada: estaTerminada ?? this.estaTerminada,
    );
  }

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Tarea && runtimeType == other.runtimeType && id == other.id;

  @override
  int get hashCode => id.hashCode;
}