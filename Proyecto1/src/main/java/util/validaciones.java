package util;

public class validaciones {

// Valida códigos con prefijo: "A-014", "AD-007", "S-021", "R-009"
    public static boolean esCodigoValido(String prefijo, String codigo) {
        if (codigo == null || !codigo.startsWith(prefijo)) return false;
        String numero = codigo.substring(prefijo.length());
        if (numero.isEmpty()) return false;
        for (int i = 0; i < numero.length(); i++) {
            if (!Character.isDigit(numero.charAt(i))) return false;
        }
        return true;
    }

    // Solo "Perro" o "Gato" (comparación exacta, sensible a mayúsculas)
    public static boolean esEspecieValida(String especie) {
        return "Perro".equals(especie) || "Gato".equals(especie);
    }

    // Entero entre 0 y 25 inclusive
    public static boolean esEdadValida(int edad) {
        return edad >= 0 && edad <= 25;
    }

    public static boolean esEstadoClinicoValido(String estado) {
        return "EN_OBSERVACION".equals(estado)
            || "EN_TRATAMIENTO".equals(estado)
            || "APTO".equals(estado);
    }

    public static boolean esEstadoAdopcionValido(String estado) {
        return "DISPONIBLE".equals(estado)
            || "ADOPTADO".equals(estado)
            || "ELIMINADO".equals(estado);
    }

    // Solo letras (incluye tildes y ñ) y espacios, no vacío
    public static boolean esNombreValido(String nombre) {
        if (esCampoVacio(nombre)) return false;
        for (int i = 0; i < nombre.length(); i++) {
            char c = nombre.charAt(i);
            if (!Character.isLetter(c) && c != ' ') return false;
        }
        return true;
    }

    // Exactamente 13 dígitos
    public static boolean esDpiValido(String dpi) {
        if (dpi == null || dpi.length() != 13) return false;
        for (int i = 0; i < dpi.length(); i++) {
            if (!Character.isDigit(dpi.charAt(i))) return false;
        }
        return true;
    }

    // Exactamente 8 dígitos
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.length() != 8) return false;
        for (int i = 0; i < telefono.length(); i++) {
            if (!Character.isDigit(telefono.charAt(i))) return false;
        }
        return true;
    }

    // Formato dd/mm/aaaa y que la fecha exista de verdad (ej. 31/02/2026 -> inválida)
    public static boolean esFechaValida(String fecha) {
        if (fecha == null) return false;
        String[] partes = fecha.split("/");
        if (partes.length != 3) return false;

        try {
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);
            java.time.LocalDate.of(anio, mes, dia); // lanza excepción si no existe
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean esPrioridadValida(String prioridad) {
        return "ALTA".equals(prioridad)
            || "MEDIA".equals(prioridad)
            || "BAJA".equals(prioridad);
    }

    public static boolean esCampoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}