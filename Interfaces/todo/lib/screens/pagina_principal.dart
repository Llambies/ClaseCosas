import 'package:flutter/material.dart';
import 'package:flutter_application_2/core/colores_app.dart';
import 'package:flutter_application_2/models/tarea.dart';
import 'package:flutter_application_2/routes/rutas.dart';
import 'package:flutter_application_2/screens/pagina_agregar_tarea.dart';
import 'package:flutter_application_2/widgets/logo.dart';
import 'package:flutter_application_2/widgets/selector_tema.dart';
import 'package:flutter_application_2/widgets/sin_tareas.dart';
import 'package:flutter_application_2/widgets/tarjeta_tarea.dart';

/// Pantalla principal de la aplicación de gestión de tareas.
/// 
/// Muestra la lista de tareas con funcionalidades de:
/// - Agregar nuevas tareas
/// - Editar tareas existentes
/// - Eliminar tareas con opción de deshacer
/// - Filtrar por categoría y estado (pendiente/terminada)
/// - Reordenar tareas mediante arrastre
/// - Cambiar el estado de completado de las tareas
/// 
/// Cuando no hay tareas, muestra el widget [SinTareas].
/// Cuando hay tareas, muestra una lista reordenable con filtros.
class PaginaPrincipal extends StatefulWidget {
  /// Crea una instancia de [PaginaPrincipal].
  const PaginaPrincipal({super.key});

  @override
  State<PaginaPrincipal> createState() => _PaginaPrincipalState();
}

/// Estado de la pantalla principal.
/// 
/// Gestiona la lista de tareas y los filtros aplicados.
class _PaginaPrincipalState extends State<PaginaPrincipal> {
  /// Filtro de categoría aplicado. `null` significa todas las categorías.
  Categoria? _filtroCategoria;
  
  /// Filtro de estado aplicado.
  /// - `null`: todas las tareas
  /// - `true`: solo tareas terminadas
  /// - `false`: solo tareas pendientes
  bool? _filtroEstado;

  /// Lista de todas las tareas almacenadas.
  final List<Tarea> _tareas = [];

  /// Obtiene la lista de tareas filtradas según los filtros aplicados.
  /// 
  /// Aplica los filtros de categoría y estado a la lista completa de tareas.
  /// Retorna una nueva lista con las tareas que cumplen los criterios de filtrado.
  List<Tarea> get _tareasFiltradas {
    return _tareas.where((tarea) {
      if (_filtroCategoria != null && tarea.categoria != _filtroCategoria) {
        return false;
      }
      if (_filtroEstado != null && tarea.estaTerminada != _filtroEstado) {
        return false;
      }
      return true;
    }).toList();
  }

  /// Agrega una nueva tarea a la lista.
  /// 
  /// [tarea] la tarea a agregar.
  void _agregarTarea(Tarea tarea) {
    setState(() {
      _tareas.add(tarea);
    });
  }

  /// Actualiza una tarea existente en la lista.
  /// 
  /// [index] el índice de la tarea a actualizar.
  /// [tareaEditada] la tarea con los datos actualizados.
  void _actualizarTarea(int index, Tarea tareaEditada) {
    if (index != -1 && index < _tareas.length) {
      setState(() {
        _tareas[index] = tareaEditada;
      });
    }
  }

  /// Elimina una tarea de la lista y muestra un SnackBar con opción de deshacer.
  /// 
  /// Guarda el índice de la tarea eliminada para poder restaurarla
  /// en la misma posición si el usuario presiona "Deshacer".
  /// 
  /// [tarea] la tarea a eliminar.
  void _eliminarTarea(Tarea tarea) {
    // Guardamos el índice para poder restaurarla en la misma posición
    final int indice = _tareas.indexOf(tarea);
    final esModoOscuro = Theme.of(context).brightness == Brightness.dark;

    setState(() {
      _tareas.remove(tarea);
    });

    // Limpiamos SnackBars anteriores y mostramos el nuevo con la opción Deshacer
    ScaffoldMessenger.of(context).clearSnackBars();
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Row(
          children: [
            Icon(
              Icons.delete_outline,
              color: esModoOscuro ? ColoresApp.darkFg : ColoresApp.lightHard,
            ),
            const SizedBox(width: 12),
            const Expanded(child: Text('Tarea eliminada')),
          ],
        ),
        backgroundColor: esModoOscuro ? ColoresApp.darkSurface1 : ColoresApp.lightFg,
        behavior: SnackBarBehavior.floating,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
        duration: const Duration(seconds: 4),
        action: SnackBarAction(
          label: 'Deshacer',
          textColor: esModoOscuro ? ColoresApp.yellowBright : ColoresApp.yellow,
          onPressed: () {
            setState(() {
              // Si el índice es válido insertamos, si no, añadimos al final
              if (indice >= 0 && indice <= _tareas.length) {
                _tareas.insert(indice, tarea);
              } else {
                _tareas.add(tarea);
              }
            });
          },
        ),
      ),
    );
  }

  /// Cambia el estado de completado de una tarea.
  /// 
  /// Alterna el estado entre completada y pendiente.
  /// 
  /// [index] el índice de la tarea cuyo estado se desea cambiar.
  void _cambiarEstadoTarea(int index) {
    if (index != -1 && index < _tareas.length) {
      setState(() {
        _tareas[index] = _tareas[index].copyWith(
          estaTerminada: !_tareas[index].estaTerminada,
        );
      });
    }
  }

  /// Construye el widget de filtros para categoría y estado.
  /// 
  /// Crea una fila con dos dropdowns: uno para filtrar por categoría
  /// y otro para filtrar por estado (pendiente/terminada).
  /// 
  /// [context] el contexto de construcción.
  /// 
  /// Retorna un [Widget] con los controles de filtrado.
  Widget _construirFiltros(BuildContext context) {
    final theme = Theme.of(context);
    return Container(
      padding: const EdgeInsets.all(12.0),
      color: theme.scaffoldBackgroundColor.withValues(alpha: 0.3), // Un fondo sutil
      child: Row(
        children: [
          // Filtro de Categoría
          Expanded(
            child: DropdownButtonFormField<Categoria?>(
              value: _filtroCategoria,
              decoration: const InputDecoration(
                labelText: 'Categoría',
                contentPadding: EdgeInsets.symmetric(horizontal: 10),
                border: OutlineInputBorder(),
              ),
              items: const [
                DropdownMenuItem(value: null, child: Text('Todas')),
                DropdownMenuItem(
                  value: Categoria.personal,
                  child: Text('Personal'),
                ),
                DropdownMenuItem(
                  value: Categoria.trabajo,
                  child: Text('Trabajo'),
                ),
                DropdownMenuItem(value: Categoria.otro, child: Text('Otro')),
              ],
              onChanged: (valor) {
                setState(() => _filtroCategoria = valor);
              },
            ),
          ),
          const SizedBox(width: 12),
          // Filtro de Estado
          Expanded(
            child: DropdownButtonFormField<bool?>(
              value: _filtroEstado,
              decoration: const InputDecoration(
                labelText: 'Estado',
                contentPadding: EdgeInsets.symmetric(horizontal: 10),
                border: OutlineInputBorder(),
              ),
              items: const [
                DropdownMenuItem(value: null, child: Text('Todos')),
                DropdownMenuItem(value: false, child: Text('Pendientes')),
                DropdownMenuItem(value: true, child: Text('Terminadas')),
              ],
              onChanged: (valor) {
                setState(() => _filtroEstado = valor);
              },
            ),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Logo(),
        centerTitle: true,
        backgroundColor: Theme.of(context).colorScheme.primary,
        elevation: 0,
        actions: const [
          SelectorTema(),
          SizedBox(width: 8),
        ],
      ),
      body: _tareas.isEmpty
          ? const SinTareas()
          : Column(
              children: [
                _construirFiltros(context), // Aquí ponemos los filtros
                Expanded(
                  child: _tareasFiltradas.isEmpty
                      ? const Center(
                          child: Text("No hay tareas con estos filtros"),
                        )
                      : ReorderableListView.builder(
                          padding: const EdgeInsets.only(left: 0, right: 8, top: 8, bottom: 8),
                          buildDefaultDragHandles: false,
                          itemCount: _tareasFiltradas.length,
                          onReorder: (oldIndex, newIndex) {
                            setState(() {
                              if (newIndex > oldIndex) {
                                newIndex -= 1;
                              }
                              // Trabajamos con la lista completa de tareas
                              final tareaMovida = _tareasFiltradas[oldIndex];
                              final indiceOriginal = _tareas.indexOf(tareaMovida);
                              
                              if (indiceOriginal != -1) {
                                _tareas.removeAt(indiceOriginal);
                                
                                // Calculamos el nuevo índice en la lista completa
                                int nuevoIndice = indiceOriginal;
                                if (newIndex < _tareasFiltradas.length - 1) {
                                  final tareaReferencia = _tareasFiltradas[newIndex];
                                  final indiceReferencia = _tareas.indexOf(tareaReferencia);
                                  if (indiceReferencia != -1) {
                                    nuevoIndice = indiceReferencia;
                                    if (nuevoIndice > indiceOriginal) {
                                      nuevoIndice -= 1;
                                    }
                                  }
                                } else {
                                  nuevoIndice = _tareas.length;
                                }
                                
                                _tareas.insert(nuevoIndice, tareaMovida);
                              }
                            });
                          },
                          itemBuilder: (context, index) {
                            final tarea = _tareasFiltradas[index];
                            final esModoOscuro = Theme.of(context).brightness == Brightness.dark;
                            return Dismissible(
                              // Usamos ValueKey con el ID único de la tarea
                              key: ValueKey(tarea.id),
                              direction: DismissDirection.endToStart, // Derecha a izquierda
                              background: Container(
                                margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
                                decoration: BoxDecoration(
                                  color: esModoOscuro ? ColoresApp.redBright : ColoresApp.red,
                                  borderRadius: BorderRadius.circular(10),
                                ),
                                alignment: Alignment.centerRight,
                                padding: const EdgeInsets.only(right: 20.0),
                                child: Icon(
                                  Icons.delete_outline,
                                  color: esModoOscuro ? ColoresApp.darkHard : ColoresApp.lightHard,
                                  size: 28,
                                ),
                              ),
                              onDismissed: (direction) => _eliminarTarea(tarea),
                              child: TarjetaTarea(
                                key: ValueKey(tarea.id),
                                tarea: tarea,
                                indiceReordenar: index,
                                onCambiarEstado: () {
                                  final indiceReal = _tareas.indexOf(tarea);
                                  _cambiarEstadoTarea(indiceReal);
                                },
                                onEliminar: () => _eliminarTarea(tarea),
                                onEditar: () async {
                                  final indiceReal = _tareas.indexOf(tarea);
                                  // Navegamos a la pantalla de agregar, pero pasándole la tarea actual
                                  final tareaEditada = await Navigator.push<Tarea>(
                                    context,
                                    MaterialPageRoute(
                                      builder: (context) => PaginaAgregarTarea(
                                        tareaParaEditar: tarea,
                                      ),
                                    ),
                                  );
                                  if (tareaEditada != null) {
                                    _actualizarTarea(indiceReal, tareaEditada);
                                  }
                                },
                              ),
                            );
                          },
                        ),
                ),
              ],
            ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          final nuevaTarea = await Navigator.pushNamed(
            context,
            Rutas.pagAgregarTarea,
          );
          if (nuevaTarea != null) {
            _agregarTarea(nuevaTarea as Tarea);
          }
        },
        backgroundColor: Theme.of(context).colorScheme.secondary,
        child: const Icon(Icons.add_circle),
      ),
    );
  }
}
