import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_application_2/models/tarea.dart';

/// Pruebas unitarias para el modelo [Tarea].
/// 
/// Estas pruebas verifican la funcionalidad básica del modelo de datos,
/// incluyendo creación, modificación, igualdad y hash code.
void main() {
  group('Tarea', () {
    /// Prueba la creación de una tarea con todos los campos requeridos.
    test('debe crear una tarea con todos los campos', () {
      final tarea = Tarea(
        titulo: 'Tarea de prueba',
        descripcion: 'Descripción de prueba',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      expect(tarea.titulo, 'Tarea de prueba');
      expect(tarea.descripcion, 'Descripción de prueba');
      expect(tarea.estaTerminada, false);
      expect(tarea.categoria, Categoria.personal);
      expect(tarea.id, isNotEmpty);
    });

    /// Prueba la creación de una tarea con un ID personalizado.
    test('debe crear una tarea con ID personalizado', () {
      const idPersonalizado = 'id-123';
      final tarea = Tarea(
        id: idPersonalizado,
        titulo: 'Tarea con ID',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.trabajo,
      );

      expect(tarea.id, idPersonalizado);
    });

    /// Prueba que se genere un ID automáticamente cuando no se proporciona.
    test('debe generar un ID automáticamente cuando no se proporciona', () {
      final tarea1 = Tarea(
        titulo: 'Tarea 1',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.otro,
      );

      // Esperar un poco para asegurar que el timestamp sea diferente
      // o verificar que el ID es un número válido
      expect(tarea1.id, isNotEmpty);
      expect(int.tryParse(tarea1.id), isNotNull, reason: 'El ID debe ser un número válido');
      
      final tarea2 = Tarea(
        titulo: 'Tarea 2',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.otro,
      );

      expect(tarea2.id, isNotEmpty);
      expect(int.tryParse(tarea2.id), isNotNull, reason: 'El ID debe ser un número válido');
      
      // Los IDs pueden ser iguales si se crean en el mismo milisegundo,
      // pero ambos deben ser números válidos
      expect(tarea1.id, isA<String>());
      expect(tarea2.id, isA<String>());
    });

    /// Prueba el método copyWith para modificar campos individuales.
    test('copyWith debe crear una copia con campos modificados', () {
      final tareaOriginal = Tarea(
        id: 'id-original',
        titulo: 'Título original',
        descripcion: 'Descripción original',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      final tareaModificada = tareaOriginal.copyWith(
        titulo: 'Título modificado',
        estaTerminada: true,
        categoria: Categoria.trabajo,
      );

      expect(tareaModificada.id, 'id-original');
      expect(tareaModificada.titulo, 'Título modificado');
      expect(tareaModificada.descripcion, 'Descripción original');
      expect(tareaModificada.estaTerminada, true);
      expect(tareaModificada.categoria, Categoria.trabajo);
    });

    /// Prueba que copyWith mantiene los valores originales cuando no se especifican cambios.
    test('copyWith debe mantener valores originales cuando no se especifican', () {
      final tareaOriginal = Tarea(
        id: 'id-original',
        titulo: 'Título original',
        descripcion: 'Descripción original',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      final tareaCopia = tareaOriginal.copyWith();

      expect(tareaCopia.id, tareaOriginal.id);
      expect(tareaCopia.titulo, tareaOriginal.titulo);
      expect(tareaCopia.descripcion, tareaOriginal.descripcion);
      expect(tareaCopia.estaTerminada, tareaOriginal.estaTerminada);
      expect(tareaCopia.categoria, tareaOriginal.categoria);
    });

    /// Prueba la igualdad de tareas basada en el ID.
    test('dos tareas con el mismo ID deben ser iguales', () {
      final tarea1 = Tarea(
        id: 'id-123',
        titulo: 'Tarea 1',
        descripcion: 'Descripción 1',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      final tarea2 = Tarea(
        id: 'id-123',
        titulo: 'Tarea 2',
        descripcion: 'Descripción 2',
        estaTerminada: true,
        categoria: Categoria.trabajo,
      );

      expect(tarea1, equals(tarea2));
      expect(tarea1 == tarea2, true);
    });

    /// Prueba que tareas con IDs diferentes no son iguales.
    test('dos tareas con IDs diferentes no deben ser iguales', () {
      final tarea1 = Tarea(
        id: 'id-123',
        titulo: 'Tarea',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      final tarea2 = Tarea(
        id: 'id-456',
        titulo: 'Tarea',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      expect(tarea1, isNot(equals(tarea2)));
      expect(tarea1 == tarea2, false);
    });

    /// Prueba el hash code de las tareas.
    test('hashCode debe ser igual para tareas con el mismo ID', () {
      final tarea1 = Tarea(
        id: 'id-123',
        titulo: 'Tarea',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      final tarea2 = Tarea(
        id: 'id-123',
        titulo: 'Otra tarea',
        descripcion: 'Otra descripción',
        estaTerminada: true,
        categoria: Categoria.trabajo,
      );

      expect(tarea1.hashCode, equals(tarea2.hashCode));
    });

    /// Prueba todas las categorías disponibles.
    test('debe soportar todas las categorías', () {
      final tareaPersonal = Tarea(
        titulo: 'Personal',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      final tareaTrabajo = Tarea(
        titulo: 'Trabajo',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.trabajo,
      );

      final tareaOtro = Tarea(
        titulo: 'Otro',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.otro,
      );

      expect(tareaPersonal.categoria, Categoria.personal);
      expect(tareaTrabajo.categoria, Categoria.trabajo);
      expect(tareaOtro.categoria, Categoria.otro);
    });
  });
}
