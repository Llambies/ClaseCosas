package org.adrian.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.adrian.controladores.App;
import org.adrian.entities.Columna;
import org.adrian.entities.Etiqueta;
import org.adrian.entities.Tablero;
import org.adrian.entities.Tarjeta;
import org.adrian.entities.Usuario;

public class Menu {
    private final Scanner scanner;
    private final App app;
    private Usuario usuarioAutenticado;

    public Menu(App app) {
        this.app = app;
        this.scanner = new Scanner(System.in);
        this.usuarioAutenticado = app.getUsuarioAutenticado();
    }

    public void iniciar() {
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenuPrincipal();
            
            try {
                String opcion = scanner.nextLine().trim().toUpperCase();
                
                switch (opcion) {
                    case "A" -> gestionarTableros();
                    case "B" -> gestionarColumnas();
                    case "C" -> gestionarTarjetas();
                    case "D" -> gestionarEtiquetas();
                    case "E" -> {
                        System.out.println("Cerrando sesión...");
                        app.logout();
                        continuar = false;
                    }
                    default -> {
                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                        pausa();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                pausa();
            }
        }
    }
    
    private void mostrarMenuPrincipal() {
        System.out.println("\n====================");
        System.out.println("   MENÚ PRINCIPAL");
        System.out.println("====================");
        System.out.println("A) Gestión de Tableros");
        System.out.println("B) Gestión de Columnas");
        System.out.println("C) Gestión de Tarjetas");
        System.out.println("D) Gestión de Etiquetas");
        System.out.println("E) Salir (logout)");
        System.out.print("Seleccione una opción: ");
    }

    // ========== A) GESTIÓN DE TABLEROS ==========
    private void gestionarTableros() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Gestión de Tableros ---");
            System.out.println("1. Crear tablero");
            System.out.println("2. Listar mis tableros");
            System.out.println("3. Eliminar tablero");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerInt();
            switch (opcion) {
                case 1 -> crearTablero();
                case 2 -> listarMisTableros();
                case 3 -> eliminarTablero();
                case 0 -> continuar = false;
                default -> System.out.println("Opción no válida.");
            }
            if (continuar) pausa();
        }
    }

    private void crearTablero() {
        System.out.print("Nombre del tablero: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }
        Tablero tablero = new Tablero(nombre, usuarioAutenticado, null);
        app.crearTablero(tablero);
        System.out.println("Tablero creado exitosamente con las columnas por defecto: TODO, DOING, DONE.");
    }

    private void listarMisTableros() {
        List<Tablero> tableros = app.obtenerTablerosPorUsuarioId(usuarioAutenticado.getId());
        if (tableros.isEmpty()) {
            System.out.println("No tienes tableros creados.");
        } else {
            System.out.println("\nTus tableros:");
            for (Tablero tablero : tableros) {
                System.out.println("  ID: " + tablero.getId() + " - " + tablero.getNombre());
            }
        }
    }

    private void eliminarTablero() {
        listarMisTableros();
        System.out.print("ID del tablero a eliminar: ");
        Long id = leerLong();
        if (id == null) return;
        
        Tablero tablero = app.obtenerTableroPorId(id);
        if (tablero == null || !tablero.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            System.out.println("Error: Tablero no encontrado o no tienes permiso para eliminarlo.");
            return;
        }
        
        app.eliminarTableroPorId(id);
        System.out.println("Tablero eliminado exitosamente (incluyendo columnas y tarjetas).");
    }

    // ========== B) GESTIÓN DE COLUMNAS ==========
    private void gestionarColumnas() {
        Tablero tablero = seleccionarTablero();
        if (tablero == null) return;

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Gestión de Columnas (Tablero: " + tablero.getNombre() + ") ---");
            System.out.println("1. Crear columna");
            System.out.println("2. Listar columnas del tablero");
            System.out.println("3. Renombrar columna");
            System.out.println("4. Eliminar columna");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerInt();
            switch (opcion) {
                case 1 -> crearColumna(tablero);
                case 2 -> listarColumnasDelTablero(tablero.getId());
                case 3 -> renombrarColumna(tablero.getId());
                case 4 -> eliminarColumna(tablero.getId());
                case 0 -> continuar = false;
                default -> System.out.println("Opción no válida.");
            }
            if (continuar) pausa();
        }
    }

    private Tablero seleccionarTablero() {
        List<Tablero> tableros = app.obtenerTablerosPorUsuarioId(usuarioAutenticado.getId());
        if (tableros.isEmpty()) {
            System.out.println("No tienes tableros. Crea uno primero.");
            return null;
        }
        System.out.println("\nSeleccione un tablero:");
        for (Tablero tablero : tableros) {
            System.out.println("  ID: " + tablero.getId() + " - " + tablero.getNombre());
        }
        System.out.print("ID del tablero: ");
        Long id = leerLong();
        if (id == null) return null;
        
        Tablero tablero = app.obtenerTableroPorId(id);
        if (tablero == null || !tablero.getUsuario().getId().equals(usuarioAutenticado.getId())) {
            System.out.println("Error: Tablero no encontrado.");
            return null;
        }
        return tablero;
    }

    private void crearColumna(Tablero tablero) {
        System.out.print("Nombre de la columna: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }
        Columna columna = new Columna(nombre, tablero);
        app.crearColumna(columna);
        System.out.println("Columna creada exitosamente.");
    }

    private void listarColumnasDelTablero(Long tableroId) {
        List<Columna> columnas = app.obtenerColumnasPorTableroId(tableroId);
        if (columnas.isEmpty()) {
            System.out.println("Este tablero no tiene columnas.");
        } else {
            System.out.println("\nColumnas del tablero:");
            for (Columna columna : columnas) {
                System.out.println("  ID: " + columna.getId() + " - " + columna.getNombre());
            }
        }
    }

    private void renombrarColumna(Long tableroId) {
        listarColumnasDelTablero(tableroId);
        System.out.print("ID de la columna a renombrar: ");
        Long id = leerLong();
        if (id == null) return;
        
        Columna columna = app.obtenerColumnaPorId(id);
        if (columna == null || !columna.getTablero().getId().equals(tableroId)) {
            System.out.println("Error: Columna no encontrada.");
            return;
        }
        
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine().trim();
        if (nuevoNombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }
        
        columna.setNombre(nuevoNombre);
        app.actualizarColumnaPorId(id, columna);
        System.out.println("Columna renombrada exitosamente.");
    }

    private void eliminarColumna(Long tableroId) {
        listarColumnasDelTablero(tableroId);
        System.out.print("ID de la columna a eliminar: ");
        Long id = leerLong();
        if (id == null) return;
        
        Columna columna = app.obtenerColumnaPorId(id);
        if (columna == null || !columna.getTablero().getId().equals(tableroId)) {
            System.out.println("Error: Columna no encontrada.");
            return;
        }
        
        app.eliminarColumnaPorId(id);
        System.out.println("Columna eliminada exitosamente (incluyendo tarjetas).");
    }

    // ========== C) GESTIÓN DE TARJETAS ==========
    private void gestionarTarjetas() {
        Tablero tablero = seleccionarTablero();
        if (tablero == null) return;

        Columna columna = seleccionarColumna(tablero.getId());
        if (columna == null) return;

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Gestión de Tarjetas (Columna: " + columna.getNombre() + ") ---");
            System.out.println("1. Crear tarjeta");
            System.out.println("2. Listar tarjetas por columna");
            System.out.println("3. Mover tarjeta a otra columna");
            System.out.println("4. Editar tarjeta");
            System.out.println("5. Eliminar tarjeta");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerInt();
            switch (opcion) {
                case 1 -> crearTarjeta(columna);
                case 2 -> listarTarjetasPorColumna(columna.getId());
                case 3 -> moverTarjeta(tablero.getId());
                case 4 -> editarTarjeta(tablero.getId());
                case 5 -> eliminarTarjeta(tablero.getId());
                case 0 -> continuar = false;
                default -> System.out.println("Opción no válida.");
            }
            if (continuar) pausa();
        }
    }

    private Columna seleccionarColumna(Long tableroId) {
        List<Columna> columnas = app.obtenerColumnasPorTableroId(tableroId);
        if (columnas.isEmpty()) {
            System.out.println("Este tablero no tiene columnas. Crea una primero.");
            return null;
        }
        System.out.println("\nSeleccione una columna:");
        for (Columna columna : columnas) {
            System.out.println("  ID: " + columna.getId() + " - " + columna.getNombre());
        }
        System.out.print("ID de la columna: ");
        Long id = leerLong();
        if (id == null) return null;
        
        Columna columna = app.obtenerColumnaPorId(id);
        if (columna == null || !columna.getTablero().getId().equals(tableroId)) {
            System.out.println("Error: Columna no encontrada.");
            return null;
        }
        return columna;
    }

    private void crearTarjeta(Columna columna) {
        System.out.print("Título de la tarjeta (obligatorio): ");
        String titulo = scanner.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("Error: El título es obligatorio.");
            return;
        }
        
        System.out.print("Descripción (opcional, presione Enter para omitir): ");
        String descripcion = scanner.nextLine().trim();
        if (descripcion.isEmpty()) {
            descripcion = "";
        }
        
        Tarjeta tarjeta = new Tarjeta(titulo, descripcion, columna);
        app.crearTarjeta(tarjeta);
        System.out.println("Tarjeta creada exitosamente.");
    }

    private void listarTarjetasPorColumna(Long columnaId) {
        List<Tarjeta> tarjetas = app.obtenerTarjetasPorColumnaId(columnaId);
        if (tarjetas.isEmpty()) {
            System.out.println("Esta columna no tiene tarjetas.");
        } else {
            System.out.println("\nTarjetas de la columna:");
            for (Tarjeta tarjeta : tarjetas) {
                System.out.println("  ID: " + tarjeta.getId() + " - " + tarjeta.getTitulo());
                if (!tarjeta.getDescripcion().isEmpty()) {
                    System.out.println("    Descripción: " + tarjeta.getDescripcion());
                }
            }
        }
    }

    private void moverTarjeta(Long tableroId) {
        // Listar todas las tarjetas del tablero
        List<Tarjeta> tarjetas = app.obtenerTarjetasPorTableroId(tableroId);
        if (tarjetas.isEmpty()) {
            System.out.println("Este tablero no tiene tarjetas.");
            return;
        }
        
        System.out.println("\nTarjetas del tablero:");
        for (Tarjeta tarjeta : tarjetas) {
            System.out.println("  ID: " + tarjeta.getId() + " - " + tarjeta.getTitulo() + 
                             " (Columna actual: " + tarjeta.getColumna().getNombre() + ")");
        }
        
        System.out.print("ID de la tarjeta a mover: ");
        Long tarjetaId = leerLong();
        if (tarjetaId == null) return;
        
        Tarjeta tarjeta = app.obtenerTarjetaPorId(tarjetaId);
        if (tarjeta == null || !tarjeta.getColumna().getTablero().getId().equals(tableroId)) {
            System.out.println("Error: Tarjeta no encontrada.");
            return;
        }
        
        // Listar columnas del mismo tablero
        List<Columna> columnas = app.obtenerColumnasPorTableroId(tableroId);
        System.out.println("\nColumnas disponibles:");
        for (Columna columna : columnas) {
            System.out.println("  ID: " + columna.getId() + " - " + columna.getNombre());
        }
        
        System.out.print("ID de la columna destino: ");
        Long columnaId = leerLong();
        if (columnaId == null) return;
        
        Columna nuevaColumna = app.obtenerColumnaPorId(columnaId);
        if (nuevaColumna == null || !nuevaColumna.getTablero().getId().equals(tableroId)) {
            System.out.println("Error: Columna no encontrada.");
            return;
        }
        
        tarjeta.setColumna(nuevaColumna);
        app.actualizarTarjetaPorId(tarjetaId, tarjeta);
        System.out.println("Tarjeta movida exitosamente.");
    }

    private void editarTarjeta(Long tableroId) {
        List<Tarjeta> tarjetas = app.obtenerTarjetasPorTableroId(tableroId);
        if (tarjetas.isEmpty()) {
            System.out.println("Este tablero no tiene tarjetas.");
            return;
        }
        
        System.out.println("\nTarjetas del tablero:");
        for (Tarjeta tarjeta : tarjetas) {
            System.out.println("  ID: " + tarjeta.getId() + " - " + tarjeta.getTitulo());
        }
        
        System.out.print("ID de la tarjeta a editar: ");
        Long id = leerLong();
        if (id == null) return;
        
        Tarjeta tarjeta = app.obtenerTarjetaPorId(id);
        if (tarjeta == null || !tarjeta.getColumna().getTablero().getId().equals(tableroId)) {
            System.out.println("Error: Tarjeta no encontrada.");
            return;
        }
        
        System.out.print("Nuevo título (presione Enter para mantener '" + tarjeta.getTitulo() + "'): ");
        String nuevoTitulo = scanner.nextLine().trim();
        if (!nuevoTitulo.isEmpty()) {
            tarjeta.setTitulo(nuevoTitulo);
        }
        
        System.out.print("Nueva descripción (presione Enter para mantener la actual): ");
        String nuevaDescripcion = scanner.nextLine().trim();
        if (!nuevaDescripcion.isEmpty()) {
            tarjeta.setDescripcion(nuevaDescripcion);
        }
        
        app.actualizarTarjetaPorId(id, tarjeta);
        System.out.println("Tarjeta editada exitosamente.");
    }

    private void eliminarTarjeta(Long tableroId) {
        List<Tarjeta> tarjetas = app.obtenerTarjetasPorTableroId(tableroId);
        if (tarjetas.isEmpty()) {
            System.out.println("Este tablero no tiene tarjetas.");
            return;
        }
        
        System.out.println("\nTarjetas del tablero:");
        for (Tarjeta tarjeta : tarjetas) {
            System.out.println("  ID: " + tarjeta.getId() + " - " + tarjeta.getTitulo());
        }
        
        System.out.print("ID de la tarjeta a eliminar: ");
        Long id = leerLong();
        if (id == null) return;
        
        Tarjeta tarjeta = app.obtenerTarjetaPorId(id);
        if (tarjeta == null || !tarjeta.getColumna().getTablero().getId().equals(tableroId)) {
            System.out.println("Error: Tarjeta no encontrada.");
            return;
        }
        
        app.eliminarTarjetaPorId(id);
        System.out.println("Tarjeta eliminada exitosamente.");
    }

    // ========== D) GESTIÓN DE ETIQUETAS ==========
    private void gestionarEtiquetas() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Gestión de Etiquetas ---");
            System.out.println("1. Crear etiqueta");
            System.out.println("2. Listar etiquetas");
            System.out.println("3. Asignar etiqueta a tarjeta");
            System.out.println("4. Quitar etiqueta de tarjeta");
            System.out.println("5. Listar tarjetas por etiqueta");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerInt();
            switch (opcion) {
                case 1 -> crearEtiqueta();
                case 2 -> listarEtiquetas();
                case 3 -> asignarEtiquetaATarjeta();
                case 4 -> quitarEtiquetaDeTarjeta();
                case 5 -> listarTarjetasPorEtiqueta();
                case 0 -> continuar = false;
                default -> System.out.println("Opción no válida.");
            }
            if (continuar) pausa();
        }
    }

    private void crearEtiqueta() {
        System.out.print("Nombre de la etiqueta: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }
        
        // Validar unicidad del nombre
        Etiqueta etiquetaExistente = app.obtenerEtiquetaPorNombre(nombre);
        if (etiquetaExistente != null) {
            System.out.println("Error: Ya existe una etiqueta con el nombre '" + nombre + "'. El nombre debe ser único.");
            return;
        }
        
        System.out.print("Color de la etiqueta: ");
        String color = scanner.nextLine().trim();
        if (color.isEmpty()) {
            System.out.println("Error: El color no puede estar vacío.");
            return;
        }
        
        Etiqueta etiqueta = new Etiqueta(nombre, color);
        app.crearEtiqueta(etiqueta);
        System.out.println("Etiqueta creada exitosamente.");
    }

    private void listarEtiquetas() {
        List<Etiqueta> etiquetas = app.obtenerEtiquetas();
        if (etiquetas.isEmpty()) {
            System.out.println("No hay etiquetas creadas.");
        } else {
            System.out.println("\nEtiquetas:");
            for (Etiqueta etiqueta : etiquetas) {
                System.out.println("  ID: " + etiqueta.getId() + " - " + etiqueta.getNombre() + 
                                 " (Color: " + etiqueta.getColor() + ")");
            }
        }
    }

    private void asignarEtiquetaATarjeta() {
        // Seleccionar tablero y tarjeta
        Tablero tablero = seleccionarTablero();
        if (tablero == null) return;
        
        List<Tarjeta> tarjetas = app.obtenerTarjetasPorTableroId(tablero.getId());
        if (tarjetas.isEmpty()) {
            System.out.println("Este tablero no tiene tarjetas.");
            return;
        }
        
        System.out.println("\nTarjetas del tablero:");
        for (Tarjeta tarjeta : tarjetas) {
            System.out.println("  ID: " + tarjeta.getId() + " - " + tarjeta.getTitulo());
        }
        
        System.out.print("ID de la tarjeta: ");
        Long tarjetaId = leerLong();
        if (tarjetaId == null) return;
        
        Tarjeta tarjeta = app.obtenerTarjetaPorId(tarjetaId);
        if (tarjeta == null || !tarjeta.getColumna().getTablero().getId().equals(tablero.getId())) {
            System.out.println("Error: Tarjeta no encontrada.");
            return;
        }
        
        // Seleccionar etiqueta
        List<Etiqueta> etiquetas = app.obtenerEtiquetas();
        if (etiquetas.isEmpty()) {
            System.out.println("No hay etiquetas disponibles. Crea una primero.");
            return;
        }
        
        System.out.println("\nEtiquetas disponibles:");
        for (Etiqueta etiqueta : etiquetas) {
            System.out.println("  ID: " + etiqueta.getId() + " - " + etiqueta.getNombre());
        }
        
        System.out.print("ID de la etiqueta: ");
        Long etiquetaId = leerLong();
        if (etiquetaId == null) return;
        
        Etiqueta etiqueta = app.obtenerEtiquetaPorId(etiquetaId);
        if (etiqueta == null) {
            System.out.println("Error: Etiqueta no encontrada.");
            return;
        }
        
        // Asignar etiqueta
        List<Etiqueta> etiquetasActuales = tarjeta.getEtiquetas();
        if (etiquetasActuales == null) {
            etiquetasActuales = new ArrayList<>();
        }
        if (!etiquetasActuales.contains(etiqueta)) {
            etiquetasActuales.add(etiqueta);
            tarjeta.setEtiquetas(etiquetasActuales);
            app.actualizarTarjetaPorId(tarjetaId, tarjeta);
            System.out.println("Etiqueta asignada exitosamente.");
        } else {
            System.out.println("La tarjeta ya tiene esta etiqueta asignada.");
        }
    }

    private void quitarEtiquetaDeTarjeta() {
        Tablero tablero = seleccionarTablero();
        if (tablero == null) return;
        
        List<Tarjeta> tarjetas = app.obtenerTarjetasPorTableroId(tablero.getId());
        if (tarjetas.isEmpty()) {
            System.out.println("Este tablero no tiene tarjetas.");
            return;
        }
        
        System.out.println("\nTarjetas del tablero:");
        for (Tarjeta tarjeta : tarjetas) {
            System.out.println("  ID: " + tarjeta.getId() + " - " + tarjeta.getTitulo());
        }
        
        System.out.print("ID de la tarjeta: ");
        Long tarjetaId = leerLong();
        if (tarjetaId == null) return;
        
        Tarjeta tarjeta = app.obtenerTarjetaPorId(tarjetaId);
        if (tarjeta == null || !tarjeta.getColumna().getTablero().getId().equals(tablero.getId())) {
            System.out.println("Error: Tarjeta no encontrada.");
            return;
        }
        
        List<Etiqueta> etiquetas = tarjeta.getEtiquetas();
        if (etiquetas == null || etiquetas.isEmpty()) {
            System.out.println("Esta tarjeta no tiene etiquetas asignadas.");
            return;
        }
        
        System.out.println("\nEtiquetas asignadas:");
        for (Etiqueta etiqueta : etiquetas) {
            System.out.println("  ID: " + etiqueta.getId() + " - " + etiqueta.getNombre());
        }
        
        System.out.print("ID de la etiqueta a quitar: ");
        Long etiquetaId = leerLong();
        if (etiquetaId == null) return;
        
        Etiqueta etiqueta = app.obtenerEtiquetaPorId(etiquetaId);
        if (etiqueta == null || !etiquetas.contains(etiqueta)) {
            System.out.println("Error: Etiqueta no encontrada o no está asignada a esta tarjeta.");
            return;
        }
        
        etiquetas.remove(etiqueta);
        tarjeta.setEtiquetas(etiquetas);
        app.actualizarTarjetaPorId(tarjetaId, tarjeta);
        System.out.println("Etiqueta quitada exitosamente.");
    }

    private void listarTarjetasPorEtiqueta() {
        List<Etiqueta> etiquetas = app.obtenerEtiquetas();
        if (etiquetas.isEmpty()) {
            System.out.println("No hay etiquetas creadas.");
            return;
        }
        
        System.out.println("\nEtiquetas disponibles:");
        for (Etiqueta etiqueta : etiquetas) {
            System.out.println("  ID: " + etiqueta.getId() + " - " + etiqueta.getNombre());
        }
        
        System.out.print("ID de la etiqueta: ");
        Long etiquetaId = leerLong();
        if (etiquetaId == null) return;
        
        Etiqueta etiqueta = app.obtenerEtiquetaPorId(etiquetaId);
        if (etiqueta == null) {
            System.out.println("Error: Etiqueta no encontrada.");
            return;
        }
        
        List<Tarjeta> tarjetas = app.obtenerTarjetasPorEtiquetaIdYUsuarioId(etiquetaId, usuarioAutenticado.getId());
        if (tarjetas.isEmpty()) {
            System.out.println("No tienes tarjetas con esta etiqueta.");
        } else {
            System.out.println("\nTarjetas con la etiqueta '" + etiqueta.getNombre() + "':");
            for (Tarjeta tarjeta : tarjetas) {
                System.out.println("  ID: " + tarjeta.getId() + " - " + tarjeta.getTitulo() + 
                                 " (Tablero: " + tarjeta.getColumna().getTablero().getNombre() + 
                                 ", Columna: " + tarjeta.getColumna().getNombre() + ")");
            }
        }
    }

    // ========== MÉTODOS AUXILIARES ==========
    private int leerInt() {
        try {
            int valor = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return valor;
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }

    private Long leerLong() {
        try {
            Long valor = scanner.nextLong();
            scanner.nextLine(); // Limpiar buffer
            return valor;
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return null;
        }
    }

    private void pausa() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
