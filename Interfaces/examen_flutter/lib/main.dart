import 'package:flutter/material.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'DAM - DI: 16-2-2026',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const PaginaPrincipal(),
    );
  }
}

// --- Pantallas ---

class PaginaPrincipal extends StatelessWidget {
  const PaginaPrincipal({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const SeccionImagen(urlImagen: 'assets/images/ensalada.png'),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                child: SeccionTitulo(
                  titulo: 'Ensalada de rúcula y pepino',
                  subtitulo: 'Muy saludable y fácil de preparar',
                ),
              ),
              const Padding(
                padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                child: SeccionBotones(),
              ),
              Padding(
                padding: const EdgeInsets.all(16),
                child: SeccionInformacion(
                  contenido:
                      'Una receta fresca y ligera ideal para cualquier momento. '
                      'Combina rúcula, pepino, tomates cherry y un aderezo ligero.',
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class PaginaInformacion extends StatelessWidget {
  const PaginaInformacion({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Información de la Receta'),
      ),
      body: const Center(
        child: Padding(
          padding: EdgeInsets.all(24),
          child: Text(
            'Esta es la información detallada de la receta. '
            'Se mostrará aquí al pulsar la imagen de la sección Imagen.',
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 16),
          ),
        ),
      ),
    );
  }
}

// --- Widgets de sección ---

class SeccionImagen extends StatelessWidget {
  const SeccionImagen({
    super.key,
    required this.urlImagen,
  });

  final String urlImagen;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.of(context).push(
          MaterialPageRoute<void>(
            builder: (context) => const PaginaInformacion(),
          ),
        );
      },
      child: AspectRatio(
        aspectRatio: 16 / 10,
        child: Image.asset(
          urlImagen,
          fit: BoxFit.cover,
          errorBuilder: (_, __, ___) => const ColoredBox(
            color: Colors.grey,
            child: Center(child: Icon(Icons.image_not_supported, size: 48)),
          ),
        ),
      ),
    );
  }
}

class SeccionTitulo extends StatelessWidget {
  const SeccionTitulo({
    super.key,
    required this.titulo,
    required this.subtitulo,
  });

  final String titulo;
  final String subtitulo;

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                titulo,
                style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
              ),
              const SizedBox(height: 4),
              Text(
                subtitulo,
                style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                      color: Colors.grey.shade700,
                    ),
              ),
            ],
          ),
        ),
        const WidgetFavorito(),
      ],
    );
  }
}

/// Widget que muestra un indicador de favorito con icono y contador.
///
/// Gestiona su propio estado: alterna entre [Icons.star] (favorito, color rojo)
/// y [Icons.star_border] (no favorito) al pulsar. El contador se incrementa
/// al marcar como favorito y se decrementa al desmarcar (valor inicial 16).
///
/// Forma parte de la sección Título de la página principal.
class WidgetFavorito extends StatefulWidget {
  const WidgetFavorito({super.key});

  @override
  State<WidgetFavorito> createState() => _WidgetFavoritoState();
}

class _WidgetFavoritoState extends State<WidgetFavorito> {
  bool _esFavorito = false;
  int _contador = 16;

  void _alternarFavorito() {
    setState(() {
      _esFavorito = !_esFavorito;
      _contador += _esFavorito ? 1 : -1;
    });
  }

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: _alternarFavorito,
      borderRadius: BorderRadius.circular(24),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(
              _esFavorito ? Icons.star : Icons.star_border,
              color: _esFavorito ? Colors.red : null,
              size: 28,
            ),
            const SizedBox(width: 4),
            Text(
              '$_contador',
              style: Theme.of(context).textTheme.titleMedium,
            ),
          ],
        ),
      ),
    );
  }
}

class SeccionBotones extends StatelessWidget {
  const SeccionBotones({super.key});

  static const Color _colorIconos = Colors.deepPurple;

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        BotonIconoConTexto(
          color: _colorIconos,
          icono: Icons.kitchen,
          etiqueta: '15 min',
          tooltipTexto: 'Tiempo de preparación',
        ),
        BotonIconoConTexto(
          color: _colorIconos,
          icono: Icons.timer,
          etiqueta: '5 min',
          tooltipTexto: 'Tiempo de cocción',
        ),
        BotonIconoConTexto(
          color: _colorIconos,
          icono: Icons.restaurant,
          etiqueta: '4-6',
          tooltipTexto: 'Raciones',
        ),
      ],
    );
  }
}

/// Botón que muestra un icono encima de una etiqueta de texto.
///
/// [color] aplica al icono. [icono] y [etiqueta] son obligatorios.
/// Si se proporciona [tooltipTexto], el icono se envuelve en un [Tooltip]
/// con ese mensaje; si no se pasa o está vacío, no se crea el Tooltip.
///
/// Se usa en la sección Botones (p. ej. kitchen + "15 min", timer + "5 min",
/// restaurant + "4-6").
class BotonIconoConTexto extends StatelessWidget {
  const BotonIconoConTexto({
    super.key,
    required this.color,
    required this.icono,
    required this.etiqueta,
    this.tooltipTexto,
  });

  /// Color del icono.
  final Color color;

  /// Icono a mostrar (p. ej. [Icons.kitchen], [Icons.timer], [Icons.restaurant]).
  final IconData icono;

  /// Texto que se muestra debajo del icono.
  final String etiqueta;

  /// Mensaje opcional para el [Tooltip]; si es null o vacío no se muestra Tooltip.
  final String? tooltipTexto;

  @override
  Widget build(BuildContext context) {
    Widget iconWidget = Icon(icono, color: color, size: 32);

    if (tooltipTexto != null && tooltipTexto!.isNotEmpty) {
      iconWidget = Tooltip(
        message: tooltipTexto!,
        child: iconWidget,
      );
    }

    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        iconWidget,
        const SizedBox(height: 8),
        Text(
          etiqueta,
          style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                color: Colors.grey.shade700,
              ),
        ),
      ],
    );
  }
}

class SeccionInformacion extends StatelessWidget {
  const SeccionInformacion({
    super.key,
    required this.contenido,
  });

  final String contenido;

  @override
  Widget build(BuildContext context) {
    return Text(
      contenido,
      style: Theme.of(context).textTheme.bodyMedium,
    );
  }
}
