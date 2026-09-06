package reportes;

import modelo.Animal;
import modelo.EntradaBitacora;
import persistencia.ArchivoUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteHTML {

    private static final DateTimeFormatter FORMATO_ARCHIVO =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");

    public static String generarReporteAnimales(Animal[] animales) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset='UTF-8'>");
        html.append("<style>table{border-collapse:collapse;width:100%;}");
        html.append("th,td{border:1px solid #999;padding:6px;text-align:left;}");
        html.append("th{background:#2c5f8a;color:white;}</style></head><body>");
        html.append("<h2>Reporte de Animales</h2>");
        html.append("<table><tr><th>Código</th><th>Especie</th><th>Edad</th>");
        html.append("<th>Estado clínico</th><th>Estado adopción</th></tr>");

        for (int i = 0; i < animales.length; i++) {
            Animal a = animales[i];
            html.append("<tr><td>").append(a.getCodigo()).append("</td>");
            html.append("<td>").append(a.getEspecie()).append("</td>");
            html.append("<td>").append(a.getEdadEstimada()).append("</td>");
            html.append("<td>").append(a.getEstadoClinico()).append("</td>");
            html.append("<td>").append(a.getEstadoAdopcion()).append("</td></tr>");
        }

        html.append("</table></body></html>");

        String nombreArchivo = "reportes/reporte_animales_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".html";
        ArchivoUtil.escribirArchivoCompleto(nombreArchivo, html.toString());
        return nombreArchivo;
    }

    public static String generarReporteOcupacion(String[][] matriz) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset='UTF-8'>");
        html.append("<style>table{border-collapse:collapse;}");
        html.append("td{border:1px solid #999;padding:10px;text-align:center;width:40px;}");
        html.append(".ocupada{background:#e57373;} .libre{background:#a5d6a7;}</style>");
        html.append("</head><body><h2>Ocupación del Refugio</h2><table>");

        String[] nombresZona = {"Zona Perros", "Zona Gatos"}; // ajusta si cambias tus filas

        for (int i = 0; i < matriz.length; i++) {
            html.append("<tr><td><b>")
                .append(i < nombresZona.length ? nombresZona[i] : "Fila " + i)
                .append("</b></td>");
            for (int j = 0; j < matriz[i].length; j++) {
                boolean libre = matriz[i][j].isEmpty();
                html.append("<td class='").append(libre ? "libre" : "ocupada").append("'>");
                html.append(libre ? "-" : matriz[i][j]);
                html.append("</td>");
            }
            html.append("</tr>");
        }

        html.append("</table></body></html>");

        String nombreArchivo = "reportes/reporte_ocupacion_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".html";
        ArchivoUtil.escribirArchivoCompleto(nombreArchivo, html.toString());
        return nombreArchivo;
    }

    public static String generarReporteBitacora(EntradaBitacora[] acciones, EntradaBitacora[] errores) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset='UTF-8'>");
        html.append("<style>table{border-collapse:collapse;width:100%;margin-bottom:20px;}");
        html.append("th,td{border:1px solid #999;padding:6px;}");
        html.append("th{background:#2c5f8a;color:white;}</style></head><body>");

        html.append("<h2>Bitácora de Acciones</h2><table>");
        html.append("<tr><th>Fecha</th><th>Usuario</th><th>Módulo</th><th>Evento</th><th>Descripción</th></tr>");
        for (int i = 0; i < acciones.length; i++) {
            html.append("<tr><td>").append(acciones[i].getFecha()).append("</td>");
            html.append("<td>").append(acciones[i].getUsuario()).append("</td>");
            html.append("<td>").append(acciones[i].getModulo()).append("</td>");
            html.append("<td>").append(acciones[i].getEvento()).append("</td>");
            html.append("<td>").append(acciones[i].getDetalle()).append("</td></tr>");
        }
        html.append("</table>");

        html.append("<h2>Bitácora de Errores</h2><table>");
        html.append("<tr><th>Fecha</th><th>Usuario</th><th>Módulo</th><th>Evento</th><th>Motivo</th></tr>");
        for (int i = 0; i < errores.length; i++) {
            html.append("<tr><td>").append(errores[i].getFecha()).append("</td>");
            html.append("<td>").append(errores[i].getUsuario()).append("</td>");
            html.append("<td>").append(errores[i].getModulo()).append("</td>");
            html.append("<td>").append(errores[i].getEvento()).append("</td>");
            html.append("<td>").append(errores[i].getDetalle()).append("</td></tr>");
        }
        html.append("</table></body></html>");

        String nombreArchivo = "reportes/reporte_bitacora_" + LocalDateTime.now().format(FORMATO_ARCHIVO) + ".html";
        ArchivoUtil.escribirArchivoCompleto(nombreArchivo, html.toString());
        return nombreArchivo;
    }
}