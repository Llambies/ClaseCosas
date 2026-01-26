package org.adrian.view;

import java.util.Scanner;

import org.adrian.controladores.App;
import org.adrian.entities.Usuario;
import org.adrian.util.PasswordUtil;

public class MenuAutenticacion {
    private final Scanner scanner;
    private final App app;

    public MenuAutenticacion(App app) {
        this.app = app;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú de autenticación y maneja la lógica de inicio de sesión y registro.
     * @return El usuario autenticado, o null si el usuario decide salir
     */
    public Usuario mostrarMenu() {
        while (true) {
            mostrarOpciones();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> {
                    Usuario usuario = iniciarSesion();
                    if (usuario != null) {
                        return usuario;
                    }
                }
                case 2 -> {
                    registrarUsuario();
                }
                case 0 -> {
                    System.out.println("Saliendo...");
                    return null;
                }
                default -> System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }

    private void mostrarOpciones() {
        System.out.println("******************");
        System.out.println("*** KANBAN ***");
        System.out.println("******************");
        System.out.println("1) Iniciar sesión");
        System.out.println("2) Registrarse");
        System.out.println("0) Salir");
        System.out.print("Seleccione una opción: ");
    }

    private int leerOpcion() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar el buffer
            return -1;
        }
    }

    /**
     * Permite al usuario iniciar sesión.
     * Reintenta si las credenciales son incorrectas.
     * @return El usuario autenticado, o null si el usuario cancela
     */
    private Usuario iniciarSesion() {
        scanner.nextLine(); // Limpiar el buffer después de leer el int
        
        while (true) {
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            Usuario usuario = app.obtenerUsuarioPorEmail(email);
            
            if (usuario == null) {
                System.out.println("Error: Usuario o contraseña incorrectos.");
                System.out.print("¿Desea intentar de nuevo? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                if (!respuesta.equals("s") && !respuesta.equals("si")) {
                    return null;
                }
                continue;
            }

            // Verificar la contraseña
            if (!PasswordUtil.verifyPassword(password, usuario.getContraseña())) {
                System.out.println("Error: Usuario o contraseña incorrectos.");
                System.out.print("¿Desea intentar de nuevo? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                if (!respuesta.equals("s") && !respuesta.equals("si")) {
                    return null;
                }
                continue;
            }

            System.out.println("¡Bienvenido, " + usuario.getNombre() + "!");
            return usuario;
        }
    }

    /**
     * Permite registrar un nuevo usuario.
     * Valida que el email no esté duplicado.
     */
    private void registrarUsuario() {
        scanner.nextLine(); // Limpiar el buffer después de leer el int
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }

        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim().toLowerCase();
            
            if (email.isEmpty()) {
                System.out.println("Error: El email no puede estar vacío.");
                continue;
            }

            // Verificar que el email no esté ya registrado
            Usuario usuarioExistente = app.obtenerUsuarioPorEmail(email);
            if (usuarioExistente != null) {
                System.out.println("Error: Este email ya está registrado. Por favor, use otro email.");
                continue;
            }
            
            break;
        }

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (password.isEmpty()) {
            System.out.println("Error: La contraseña no puede estar vacía.");
            return;
        }

        // Generar hash de la contraseña
        String passwordHash = PasswordUtil.hashPassword(password);

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario(nombre, email, passwordHash, null);
        
        try {
            app.crearUsuario(nuevoUsuario);
            System.out.println("Usuario registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }
}
