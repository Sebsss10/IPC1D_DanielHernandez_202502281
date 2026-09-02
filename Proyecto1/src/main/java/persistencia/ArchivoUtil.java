package persistencia;

import java.io.*;

public class ArchivoUtil {

    public static void escribirLinea(String rutaArchivo, String linea) {
        try (FileWriter fw = new FileWriter(rutaArchivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(linea);
        } catch (IOException e) {
            System.out.println("Error al escribir en " + rutaArchivo + ": " + e.getMessage());
        }
    }

    public static String[] leerLineas(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return new String[0];

        String[] lineasTemp = new String[1000]; // límite razonable de líneas
        int total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null && total < lineasTemp.length) {
                lineasTemp[total] = linea;
                total++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + rutaArchivo + ": " + e.getMessage());
        }

        String[] resultado = new String[total];
        for (int i = 0; i < total; i++) {
            resultado[i] = lineasTemp[i];
        }
        return resultado;
    }
}