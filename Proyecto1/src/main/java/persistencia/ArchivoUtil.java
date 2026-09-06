package persistencia;

import java.io.*;

public class ArchivoUtil {

 public static void escribirLinea(String rutaArchivo, String linea) {
    File archivo = new File(rutaArchivo);
    if (archivo.getParentFile() != null) {
        archivo.getParentFile().mkdirs(); // crea la carpeta si no existe
    }
    try (FileWriter fw = new FileWriter(archivo, true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter pw = new PrintWriter(bw)) {
        pw.println(linea);
    } catch (IOException e) {
        System.out.println("Error al escribir en " + rutaArchivo + ": " + e.getMessage());
    }
}

public static void escribirArchivoCompleto(String rutaArchivo, String contenido) {
    File archivo = new File(rutaArchivo);
    if (archivo.getParentFile() != null) {
        archivo.getParentFile().mkdirs();
    }
    try (FileWriter fw = new FileWriter(archivo, false)) {
        fw.write(contenido);
    } catch (IOException e) {
        System.out.println("Error al escribir " + rutaArchivo + ": " + e.getMessage());
    }
}
}