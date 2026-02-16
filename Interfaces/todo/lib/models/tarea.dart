/// Enumeración de categorías disponibles para las tareas.
/// 
/// Permite clasificar las tareas en diferentes categorías para
/// facilitar su organización y filtrado.
enum Categoria {
  /// Tareas de carácter personal
  personal,
  
  /// Tareas relacionadas con el trabajo
  trabajo,
  
  /// Otras tareas que no encajan en las categorías anteriores
  otro,
}

/// Modelo de datos inmutables para una tarea.
/// 
/// Representa una tarea con su información básica: título, descripción,
/// estado de completado y categoría. Incluye un ID único generado
/// automáticamente para identificación en listas y operaciones de estado.
/// 
/// El ID se genera automáticamente usando el timestamp actual en milisegundos
/// si no se proporciona uno explícitamente.
/// 
/// Ejemplo de uso:
/// ```dart
/// final tarea = Tarea(
///   titulo: 'Comprar leche',
///   descripcion: 'Comprar leche en el supermercado',
///   estaTerminada: false,
///   categoria: Categoria.personal,
/// );
/// ```
class Tarea {
  /// Crea una nueva instancia de [Tarea].
  /// 
  /// [id] es opcional. Si no se proporciona, se genera automáticamente
  /// usando el timestamp actual en milisegundos.
  /// 
  /// [titulo] es el título de la tarea (requerido).
  /// [descripcion] es la descripción detallada de la tarea (requerido).
  /// [estaTerminada] indica si la tarea está completada (requerido).
  /// [categoria] es la categoría de la tarea (requerido).
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
  /// 
  /// Retorna una nueva instancia de [Tarea] con los mismos valores que la
  /// instancia actual, excepto por los campos que se proporcionen explícitamente.
  /// El ID siempre se mantiene igual para preservar la identidad de la tarea.
  /// 
  /// Ejemplo de uso:
  /// ```dart
  /// final tareaCompletada = tarea.copyWith(estaTerminada: true);
  /// final tareaEditada = tarea.copyWith(
  ///   titulo: 'Nuevo título',
  ///   categoria: Categoria.trabajo,
  /// );
  /// ```
  /// 
  /// [titulo] nuevo título para la tarea (opcional).
  /// [descripcion] nueva descripción para la tarea (opcional).
  /// [categoria] nueva categoría para la tarea (opcional).
  /// [estaTerminada] nuevo estado de completado para la tarea (opcional).
  /// 
  /// Retorna una nueva instancia de [Tarea] con los valores modificados.
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

  /// Compara dos tareas por igualdad basándose en su ID.
  /// 
  /// Dos tareas se consideran iguales si tienen el mismo ID, independientemente
  /// de los demás campos. Esto permite identificar la misma tarea incluso si
  /// ha sido modificada.
  /// 
  /// Retorna `true` si las tareas tienen el mismo ID, `false` en caso contrario.
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Tarea && runtimeType == other.runtimeType && id == other.id;

  /// Retorna el hash code de la tarea basado en su ID.
  /// 
  /// Esto asegura que tareas con el mismo ID tengan el mismo hash code,
  /// lo cual es necesario para usar tareas como claves en mapas y sets.
  /// 
  /// Retorna el hash code del ID de la tarea.
  @override
  int get hashCode => id.hashCode;
}