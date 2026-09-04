package servicios;

import modelo.Solicitud;
import util.validaciones;

public class GestorSolicitudes {

    private static final int MAX_SOLICITUDES = 200;

    private Solicitud[] solicitudes = new Solicitud[MAX_SOLICITUDES];
    private int total = 0;

    private GestorBitacora bitacora;
    private GestorAnimales gestorAnimales;
    private GestorAdoptantes gestorAdoptantes;

    public GestorSolicitudes(GestorBitacora bitacora, GestorAnimales gestorAnimales,
                              GestorAdoptantes gestorAdoptantes) {
        this.bitacora = bitacora;
        this.gestorAnimales = gestorAnimales;
        this.gestorAdoptantes = gestorAdoptantes;
    }

    public boolean registrar(String usuarioActual, Solicitud nueva) {
        if (total >= MAX_SOLICITUDES) {
            bitacora.registrarError(usuarioActual, "SOLICITUDES", "ALTA", "Capacidad máxima alcanzada");
            return false;
        }
        if (!validaciones.esCodigoValido("S-", nueva.getCodigo())
                || !validaciones.esFechaValida(nueva.getFecha())) {
            bitacora.registrarError(usuarioActual, "SOLICITUDES", "VALIDACION", "Datos inválidos para " + nueva.getCodigo());
            return false;
        }
        if (gestorAnimales.buscarPorCodigo(nueva.getCodigoAnimal()) == null) {
            bitacora.registrarError(usuarioActual, "SOLICITUDES", "VALIDACION", "Animal no existe: " + nueva.getCodigoAnimal());
            return false;
        }
        if (gestorAdoptantes.buscarPorCodigo(nueva.getCodigoAdoptante()) == null) {
            bitacora.registrarError(usuarioActual, "SOLICITUDES", "VALIDACION", "Adoptante no existe: " + nueva.getCodigoAdoptante());
            return false;
        }
        if (!"DISPONIBLE".equals(gestorAnimales.buscarPorCodigo(nueva.getCodigoAnimal()).getEstadoAdopcion())) {
            bitacora.registrarError(usuarioActual, "SOLICITUDES", "VALIDACION", "Animal no disponible: " + nueva.getCodigoAnimal());
            return false;
        }

        solicitudes[total] = nueva;
        total++;
        bitacora.registrarAccion(usuarioActual, "SOLICITUDES", "ALTA", "Solicitud " + nueva.getCodigo() + " registrada");
        return true;
    }

    public boolean cambiarEstado(String usuarioActual, String codigoSolicitud, String nuevoEstado) {
        Solicitud solicitud = buscarPorCodigo(codigoSolicitud);
        if (solicitud == null) {
            bitacora.registrarError(usuarioActual, "SOLICITUDES", "CAMBIO_ESTADO", "Solicitud no encontrada: " + codigoSolicitud);
            return false;
        }

        if ("APROBADA".equals(nuevoEstado)) {
            // Regla: solo una aprobada por animal
            for (int i = 0; i < total; i++) {
                if (solicitudes[i].getCodigoAnimal().equals(solicitud.getCodigoAnimal())
                        && "APROBADA".equals(solicitudes[i].getEstado())) {
                    bitacora.registrarError(usuarioActual, "SOLICITUDES", "APROBAR", "Ya existe una solicitud aprobada para " + solicitud.getCodigoAnimal());
                    return false;
                }
            }
            // Rechazar las demás pendientes del mismo animal
            for (int i = 0; i < total; i++) {
                if (solicitudes[i].getCodigoAnimal().equals(solicitud.getCodigoAnimal())
                        && "PENDIENTE".equals(solicitudes[i].getEstado())
                        && !solicitudes[i].getCodigo().equals(codigoSolicitud)) {
                    solicitudes[i].setEstado("RECHAZADA");
                }
            }
            gestorAnimales.editarEstadoAdopcion(usuarioActual, solicitud.getCodigoAnimal(), "ADOPTADO");
        }

        solicitud.setEstado(nuevoEstado);
        bitacora.registrarAccion(usuarioActual, "SOLICITUDES", "CAMBIO_ESTADO",
                "Solicitud " + codigoSolicitud + " -> " + nuevoEstado);
        return true;
    }

    public Solicitud buscarPorCodigo(String codigo) {
        for (int i = 0; i < total; i++) {
            if (solicitudes[i].getCodigo().equals(codigo)) return solicitudes[i];
        }
        return null;
    }

    public Solicitud[] listarHistorial() {
        Solicitud[] copia = new Solicitud[total];
        for (int i = 0; i < total; i++) copia[i] = solicitudes[i];
        return copia;
    }
}