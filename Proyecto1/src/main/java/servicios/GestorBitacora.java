package servicios;

import modelo.EntradaBitacora;
import persistencia.ArchivoUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorBitacora {

    private static final String ARCHIVO_ACCIONES = "archivos/bitacora_acciones.txt";
    private static final String ARCHIVO_ERRORES = "archivos/bitacora_errores.txt";
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final int MAX_ENTRADAS = 500;

    private EntradaBitacora[] acciones = new EntradaBitacora[MAX_ENTRADAS];
    private int totalAcciones = 0;

    private EntradaBitacora[] errores = new EntradaBitacora[MAX_ENTRADAS];
    private int totalErrores = 0;

    public void registrarAccion(String usuario, String modulo, String evento, String descripcion) {
        String fecha = LocalDateTime.now().format(FORMATO_FECHA);
        EntradaBitacora entrada = new EntradaBitacora(fecha, usuario, modulo, evento, descripcion);

        if (totalAcciones < MAX_ENTRADAS) {
            acciones[totalAcciones] = entrada;
            totalAcciones++;
        }
        ArchivoUtil.escribirLinea(ARCHIVO_ACCIONES, entrada.aLineaArchivo());
    }

    public void registrarError(String usuario, String modulo, String evento, String motivo) {
        String fecha = LocalDateTime.now().format(FORMATO_FECHA);
        EntradaBitacora entrada = new EntradaBitacora(fecha, usuario, modulo, evento, motivo);

        if (totalErrores < MAX_ENTRADAS) {
            errores[totalErrores] = entrada;
            totalErrores++;
        }
        ArchivoUtil.escribirLinea(ARCHIVO_ERRORES, entrada.aLineaArchivo());
    }

    public EntradaBitacora[] obtenerAcciones() {
        EntradaBitacora[] copia = new EntradaBitacora[totalAcciones];
        for (int i = 0; i < totalAcciones; i++) copia[i] = acciones[i];
        return copia;
    }

    public EntradaBitacora[] obtenerErrores() {
        EntradaBitacora[] copia = new EntradaBitacora[totalErrores];
        for (int i = 0; i < totalErrores; i++) copia[i] = errores[i];
        return copia;
    }
}