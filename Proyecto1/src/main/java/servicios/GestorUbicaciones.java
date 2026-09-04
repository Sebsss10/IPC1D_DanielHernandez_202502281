package servicios;

public class GestorUbicaciones {

    // Fila 0 = Zona Perros, Fila 1 = Zona Gatos (documenta esto en tu manual técnico)
    private static final int FILAS = 2;
    private static final int COLUMNAS = 10; // capacidad máxima por zona

    private String[][] matriz = new String[FILAS][COLUMNAS];

    private GestorBitacora bitacora;

    public GestorUbicaciones(GestorBitacora bitacora) {
        this.bitacora = bitacora;
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                matriz[i][j] = "";
            }
        }
    }

    public boolean hayEspacioDisponible(int fila) {
        if (fila < 0 || fila >= FILAS) return false;
        for (int j = 0; j < COLUMNAS; j++) {
            if (matriz[fila][j].isEmpty()) return true;
        }
        return false;
    }

    public boolean asignar(String usuarioActual, int fila, int columna, String codigoAnimal) {
        if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) {
            bitacora.registrarError(usuarioActual, "UBICACIONES", "ASIGNAR", "Índice fuera de rango [" + fila + "][" + columna + "]");
            return false;
        }
        if (!matriz[fila][columna].isEmpty()) {
            bitacora.registrarError(usuarioActual, "UBICACIONES", "CAPACIDAD", "Celda [" + fila + "][" + columna + "] ya ocupada por " + matriz[fila][columna]);
            return false;
        }
        matriz[fila][columna] = codigoAnimal;
        bitacora.registrarAccion(usuarioActual, "UBICACIONES", "ASIGNAR", codigoAnimal + " asignado a [" + fila + "][" + columna + "]");
        return true;
    }

    public boolean liberar(String usuarioActual, int fila, int columna) {
        if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) return false;
        String codigoAnimal = matriz[fila][columna];
        matriz[fila][columna] = "";
        bitacora.registrarAccion(usuarioActual, "UBICACIONES", "LIBERAR", "Celda [" + fila + "][" + columna + "] liberada (" + codigoAnimal + ")");
        return true;
    }

    public boolean liberarPorCodigoAnimal(String usuarioActual, String codigoAnimal) {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (matriz[i][j].equals(codigoAnimal)) {
                    return liberar(usuarioActual, i, j);
                }
            }
        }
        return false; // el animal no estaba asignado a ninguna celda
    }

    public String[][] obtenerMatriz() {
        return matriz;
    }
}