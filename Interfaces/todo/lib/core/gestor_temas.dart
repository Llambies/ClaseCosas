import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'colores_app.dart';

/// Enumeración de modos de tema disponibles.
/// 
/// Define los tres modos de visualización soportados por la aplicación.
enum ModoTema {
  /// Tema claro con colores claros
  claro,
  
  /// Tema oscuro con colores oscuros
  oscuro,
  
  /// Sigue automáticamente el modo del sistema operativo
  sistema,
}

/// Enumeración de intensidades del esquema de colores Gruvbox.
/// 
/// Permite ajustar la intensidad de los colores del tema Gruvbox
/// para personalizar la apariencia visual de la aplicación.
enum IntensidadGruvbox {
  /// Colores más suaves y apagados
  soft,
  
  /// Colores estándar del esquema Gruvbox
  normal,
  
  /// Colores más intensos y saturados
  hard,
}

/// Gestor de temas para la aplicación.
/// 
/// Gestiona la configuración de temas de la aplicación, incluyendo
/// el modo (claro/oscuro/sistema) y la intensidad del esquema de
/// colores Gruvbox. Implementa [ChangeNotifier] para notificar
/// cambios a los widgets que escuchan.
/// 
/// El gestor genera dinámicamente [ThemeData] basado en la configuración
/// actual, aplicando los colores Gruvbox correspondientes según el modo
/// y la intensidad seleccionados.
/// 
/// Ejemplo de uso:
/// ```dart
/// final gestor = GestorTemas();
/// gestor.cambiarModoTema(ModoTema.oscuro);
/// gestor.cambiarIntensidad(IntensidadGruvbox.hard);
/// final tema = gestor.obtenerTema();
/// ```
class GestorTemas extends ChangeNotifier {
  ModoTema _modoTema = ModoTema.sistema;
  IntensidadGruvbox _intensidad = IntensidadGruvbox.normal;
  bool _esModoOscuro = false;

  /// Obtiene el modo de tema actual.
  ModoTema get modoTema => _modoTema;
  
  /// Obtiene la intensidad Gruvbox actual.
  IntensidadGruvbox get intensidad => _intensidad;
  
  /// Indica si el tema actual es oscuro.
  bool get esModoOscuro => _esModoOscuro;

  /// Cambia el modo de tema de la aplicación.
  /// 
  /// Actualiza el modo de tema y ajusta automáticamente el modo oscuro
  /// según corresponda. Notifica a los listeners sobre el cambio.
  /// 
  /// [modo] el nuevo modo de tema a aplicar.
  void cambiarModoTema(ModoTema modo) {
    _modoTema = modo;
    _actualizarModoOscuro();
    notifyListeners();
  }

  /// Cambia la intensidad del esquema de colores Gruvbox.
  /// 
  /// Actualiza la intensidad de los colores y notifica a los listeners
  /// sobre el cambio. Esto afecta los colores de fondo y superficie.
  /// 
  /// [intensidad] la nueva intensidad Gruvbox a aplicar.
  void cambiarIntensidad(IntensidadGruvbox intensidad) {
    _intensidad = intensidad;
    notifyListeners();
  }

  /// Actualiza el modo oscuro basándose en el sistema operativo.
  /// 
  /// Solo tiene efecto si el modo de tema actual es [ModoTema.sistema].
  /// Se llama automáticamente cuando el sistema operativo cambia su
  /// modo claro/oscuro.
  /// 
  /// [esOscuro] `true` si el sistema está en modo oscuro, `false` si está en modo claro.
  void actualizarModoOscuroSistema(bool esOscuro) {
    if (_modoTema == ModoTema.sistema) {
      _esModoOscuro = esOscuro;
      notifyListeners();
    }
  }

  void _actualizarModoOscuro() {
    switch (_modoTema) {
      case ModoTema.claro:
        _esModoOscuro = false;
        break;
      case ModoTema.oscuro:
        _esModoOscuro = true;
        break;
      case ModoTema.sistema:
        // Se actualizará desde el widget que detecta el modo del sistema
        break;
    }
  }

  Color _obtenerColorFondo() {
    if (_esModoOscuro) {
      switch (_intensidad) {
        case IntensidadGruvbox.soft:
          return ColoresApp.darkSoft;
        case IntensidadGruvbox.normal:
          return ColoresApp.darkNormal;
        case IntensidadGruvbox.hard:
          return ColoresApp.darkHard;
      }
    } else {
      switch (_intensidad) {
        case IntensidadGruvbox.soft:
          return ColoresApp.lightSoft;
        case IntensidadGruvbox.normal:
          return ColoresApp.lightNormal;
        case IntensidadGruvbox.hard:
          return ColoresApp.lightHard;
      }
    }
  }

  Color _obtenerColorSurface() {
    return _esModoOscuro 
        ? ColoresApp.darkSurface1 
        : ColoresApp.lightSurface1;
  }

  Color _obtenerColorForeground() {
    return _esModoOscuro 
        ? ColoresApp.darkFg 
        : ColoresApp.lightFg;
  }

  Color _obtenerColorForeground0() {
    return _esModoOscuro 
        ? ColoresApp.darkFg0 
        : ColoresApp.lightFg0;
  }

  Color _obtenerColorPrincipal() {
    return _esModoOscuro 
        ? ColoresApp.blueBright 
        : ColoresApp.blue;
  }

  Color _obtenerColorSecundario() {
    return _esModoOscuro 
        ? ColoresApp.aquaBright 
        : ColoresApp.aqua;
  }

  Color _obtenerColorError() {
    return _esModoOscuro 
        ? ColoresApp.redBright 
        : ColoresApp.red;
  }

  /// Genera y retorna el [ThemeData] actual basado en la configuración.
  /// 
  /// Crea un tema completo de Material Design aplicando los colores
  /// Gruvbox según el modo (claro/oscuro) y la intensidad configurados.
  /// El tema incluye configuración para:
  /// - Esquema de colores (ColorScheme)
  /// - Colores de fondo y superficie
  /// - Estilos de texto usando Google Fonts (Inter)
  /// - Temas de AppBar, FloatingActionButton, ElevatedButton, etc.
  /// 
  /// Retorna un [ThemeData] completamente configurado con los colores Gruvbox.
  ThemeData obtenerTema() {
    final colorFondo = _obtenerColorFondo();
    final colorSurface = _obtenerColorSurface();
    final colorForeground = _obtenerColorForeground();
    final colorForeground0 = _obtenerColorForeground0();
    final colorPrincipal = _obtenerColorPrincipal();
    final colorSecundario = _obtenerColorSecundario();
    final colorError = _obtenerColorError();

    final colorScheme = _esModoOscuro
        ? ColorScheme.dark(
            primary: colorPrincipal,
            secondary: colorSecundario,
            error: colorError,
            surface: colorSurface,
            onPrimary: ColoresApp.darkHard,
            onSecondary: ColoresApp.darkHard,
            onError: ColoresApp.darkHard,
            onSurface: colorForeground,
          )
        : ColorScheme.light(
            primary: colorPrincipal,
            secondary: colorSecundario,
            error: colorError,
            surface: colorSurface,
            onPrimary: ColoresApp.lightHard,
            onSecondary: ColoresApp.lightHard,
            onError: ColoresApp.lightHard,
            onSurface: colorForeground,
          );

    return ThemeData(
      useMaterial3: false,
      primaryColor: colorPrincipal,
      scaffoldBackgroundColor: colorFondo,
      colorScheme: colorScheme,
      cardColor: colorSurface,
      dividerColor: _esModoOscuro ? ColoresApp.gray : ColoresApp.grayBright,
      textTheme: GoogleFonts.interTextTheme(
        (_esModoOscuro ? ThemeData.dark() : ThemeData.light()).textTheme.copyWith(
          bodyLarge: TextStyle(color: colorForeground0),
          bodyMedium: TextStyle(color: colorForeground),
          bodySmall: TextStyle(color: colorForeground),
          titleLarge: TextStyle(color: colorForeground0),
          titleMedium: TextStyle(color: colorForeground0),
          titleSmall: TextStyle(color: colorForeground),
        ),
      ),
      appBarTheme: AppBarTheme(
        backgroundColor: colorFondo,
        foregroundColor: colorForeground0,
        elevation: 0,
      ),
      floatingActionButtonTheme: FloatingActionButtonThemeData(
        backgroundColor: _esModoOscuro ? ColoresApp.orangeBright : ColoresApp.orange,
        foregroundColor: _esModoOscuro ? ColoresApp.darkHard : ColoresApp.lightHard,
      ),
      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: colorPrincipal,
          foregroundColor: _esModoOscuro ? ColoresApp.darkHard : ColoresApp.lightHard,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
        ),
      ),
      inputDecorationTheme: InputDecorationTheme(
        filled: true,
        fillColor: colorSurface,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: BorderSide(color: ColoresApp.gray),
        ),
        enabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: BorderSide(color: ColoresApp.gray),
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: BorderSide(color: colorPrincipal, width: 2),
        ),
      ),
      checkboxTheme: CheckboxThemeData(
        fillColor: WidgetStateProperty.resolveWith((states) {
          if (states.contains(WidgetState.selected)) {
            return _esModoOscuro ? ColoresApp.greenBright : ColoresApp.green;
          }
          return Colors.transparent;
        }),
        checkColor: WidgetStateProperty.all(
          _esModoOscuro ? ColoresApp.darkHard : ColoresApp.lightHard,
        ),
      ),
      iconTheme: IconThemeData(color: colorForeground),
      visualDensity: VisualDensity.adaptivePlatformDensity,
    );
  }
}
