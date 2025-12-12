import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public enum Rol {
        PORTERO, DEFENSA, CENTROCAMPISTA, DELANTERO;

        private final Set<String> jugadores = new HashSet<>();
        public Set<String> getJugadores() {
            return jugadores;
        }
    }
    public static void main(String[] args) {
        try {
            mostratMenu();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error. Intente de nuevo" + e.getMessage());
        }
    }

    private static String entradaDeDatos(String mensaje) {
        String datos;
        try {
            datos = JOptionPane.showInputDialog(mensaje);
            if (datos != null) {
                return datos.trim();
            }
            return "";
        }catch (Exception e) {
            return "";
        }
    }

    private static Rol pedirRol() throws Exception {
        String rol = entradaDeDatos("""
                Introduzca un rol:
                Portero, Defensa, CentroCampista, Delantero""");

        if (rol.isEmpty()) {
            throw new Exception("El rol no puede ser vacio");
        }

        rol = rol.toUpperCase();
        try {
            return Rol.valueOf(rol);

        }catch (IllegalArgumentException e) {
            throw new Exception("El rol introducido no puede ser vacio");
        }
    }

    private static void mostratMenu() throws Exception {
        int opcion = 0;
        do {
            String menuInput = entradaDeDatos("""
                    Introduzca una opcion:
                    1.- Alta jugador
                    2.- Baja jugador
                    3.- Modificar Jugador
                    4.- Mostrar jugadores
                    5.- Salir""");

            if (!menuInput.isEmpty()) {
                try {
                    opcion = Integer.parseInt(menuInput);

                    switch (opcion) {
                        case 1 -> altaJugador();
                        case 2 -> bajaJugador();
                        case 3 -> modificarJugador();
                        case 4 -> mostrarJugadores();
                        case 5 -> salir();
                        default -> throw new Exception("Opción inválida. Introduzca un número del 1 al 5.");
                    }
                }catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "ERROR. Introduzca un número para el menú.");
                    opcion = 0;
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "ERROR. " + e.getMessage());
                    opcion = 0;
                }
            } else {
                opcion = 0;
            }
        }while (opcion != 5);

    }

    private static void altaJugador() throws Exception {
        Rol rol = pedirRol();
        String nombre = entradaDeDatos("Introduzca el nombre del jugador");

        if (nombre.isEmpty()) {
            throw new Exception("El nombre del jugador no puede ser vacio");
        }

        boolean añadir = rol.getJugadores().add(nombre);
        if (!añadir) {
            JOptionPane.showMessageDialog(null, "El jugador '" + nombre + "' ya existe en el rol");
        } else {
            JOptionPane.showMessageDialog(null, "Jugador '" + nombre + "' añadid");
        }
    }

    private static void bajaJugador() throws Exception {
        Rol rol = pedirRol();
        String nombre = entradaDeDatos("Introduzca el nombre del jugador a eliminar");

        if (nombre.isEmpty()) {
            throw new Exception("El nombre del jugador a eliminar no puede ser vacio");
        }

        boolean eliminar = rol.getJugadores().remove(nombre);
        if (!eliminar) {
            throw new Exception("El jugador" + nombre + "no existe");
        } else {
            JOptionPane.showMessageDialog(null, "Jugador '" + nombre + "' eliminado");
        }
    }

    private static void modificarJugador() throws Exception {
        Rol rol = pedirRol();

        String actual = entradaDeDatos("Introduzca el nombre actual del jugador");
        String nuevo = entradaDeDatos("Introduzca el nombre nuevo del jugador");

        if (actual.isEmpty() || nuevo.isEmpty()) {
            throw new Exception("Los nombres (actual y nuevo) no pueden ser vacíos.");
        }

        Set<String> set = rol.getJugadores();

        if (set.contains(actual)) {
            throw new Exception("El jugador actual ('" + actual + "') no existe en el rol.");
        }

        if (!set.contains(nuevo)) {
            JOptionPane.showMessageDialog(null, "El nombre nuevo ('" + nuevo + "') ya existe en el rol. No se realizó la modificación.");
            return;
        }
        set.remove(actual);
        set.add(nuevo);

        JOptionPane.showMessageDialog(null, "Jugador '" + actual + "' modificado a '" + nuevo + "' en el rol");
    }

    private static void mostrarJugadores() throws Exception {
        StringBuilder mensaje = new StringBuilder("Lista de jugadores:");

        for (Rol r:  Rol.values()) {
            mensaje.append("**").append(r).append("** (").append(r.getJugadores().size()).append(")\n");
            if (r.getJugadores().isEmpty()) {
                mensaje.append("  - (Sin jugadores)\n");
            } else {
                for (String jugador : r.getJugadores()) {
                    mensaje.append("  - ").append(jugador).append("\n");
                }
            }
            mensaje.append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString());
    }

    private static void salir() throws Exception {
        JOptionPane.showMessageDialog(null, "Salir");
        System.exit(0);
    }
}