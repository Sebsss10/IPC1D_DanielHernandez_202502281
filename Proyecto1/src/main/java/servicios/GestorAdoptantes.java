package servicios;

import modelo.Adoptante;
import util.validaciones;

public class GestorAdoptantes {

    private static final int MAX_ADOPTANTES = 100;

    private Adoptante[] adoptantes = new Adoptante[MAX_ADOPTANTES];
    private int total = 0;

    private GestorBitacora bitacora;

    public GestorAdoptantes(GestorBitacora bitacora) {
        this.bitacora = bitacora;
    }

    public boolean registrar(String usuarioActual, Adoptante nuevo) {
        if (total >= MAX_ADOPTANTES) {
            bitacora.registrarError(usuarioActual, "ADOPTANTES", "ALTA", "Capacidad máxima alcanzada");
            return false;
        }
        if (buscarPorDpi(nuevo.getDpi()) != null) {
            bitacora.registrarError(usuarioActual, "ADOPTANTES", "DUPLICADO", "DPI " + nuevo.getDpi() + " ya existe");
            return false;
        }
        if (!validaciones.esCodigoValido("AD-", nuevo.getCodigo())
                || !validaciones.esNombreValido(nuevo.getNombre())
                || !validaciones.esDpiValido(nuevo.getDpi())
                || !validaciones.esTelefonoValido(nuevo.getTelefono())) {
            bitacora.registrarError(usuarioActual, "ADOPTANTES", "VALIDACION", "Datos inválidos para " + nuevo.getCodigo());
            return false;
        }

        adoptantes[total] = nuevo;
        total++;
        bitacora.registrarAccion(usuarioActual, "ADOPTANTES", "ALTA", "Adoptante " + nuevo.getCodigo() + " registrado");
        return true;
    }

    public Adoptante buscarPorCodigo(String codigo) {
        for (int i = 0; i < total; i++) {
            if (adoptantes[i].getCodigo().equals(codigo)) return adoptantes[i];
        }
        return null;
    }

    public Adoptante buscarPorDpi(String dpi) {
        for (int i = 0; i < total; i++) {
            if (adoptantes[i].getDpi().equals(dpi)) return adoptantes[i];
        }
        return null;
    }

    public boolean editar(String usuarioActual, String codigo, String nuevoTelefono) {
        Adoptante adoptante = buscarPorCodigo(codigo);
        if (adoptante == null) {
            bitacora.registrarError(usuarioActual, "ADOPTANTES", "EDITAR", "Adoptante no encontrado: " + codigo);
            return false;
        }
        if (!validaciones.esTelefonoValido(nuevoTelefono)) {
            bitacora.registrarError(usuarioActual, "ADOPTANTES", "VALIDACION", "Teléfono inválido: " + nuevoTelefono);
            return false;
        }
        adoptante.setTelefono(nuevoTelefono);
        bitacora.registrarAccion(usuarioActual, "ADOPTANTES", "EDITAR", "Adoptante " + codigo + " actualizado");
        return true;
    }

    public Adoptante[] listar() {
        Adoptante[] copia = new Adoptante[total];
        for (int i = 0; i < total; i++) copia[i] = adoptantes[i];
        return copia;
    }
}