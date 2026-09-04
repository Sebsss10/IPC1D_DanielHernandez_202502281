package servicios;

import modelo.Animal;
import util.validaciones;

public class GestorAnimales {

    private static final int MAX_ANIMALES = 100;

    private Animal[] animales = new Animal[MAX_ANIMALES];
    private int total = 0;

    private GestorBitacora bitacora;

    public GestorAnimales(GestorBitacora bitacora) {
        this.bitacora = bitacora;
    }

    public boolean registrar(String usuarioActual, Animal nuevo) {
        if (total >= MAX_ANIMALES) {
            bitacora.registrarError(usuarioActual, "ANIMALES", "ALTA", "Capacidad máxima alcanzada");
            return false;
        }
        if (buscarPorCodigo(nuevo.getCodigo()) != null) {
            bitacora.registrarError(usuarioActual, "ANIMALES", "ALTA", "Código duplicado: " + nuevo.getCodigo());
            return false;
        }
        if (!validaciones.esCodigoValido("A-", nuevo.getCodigo())
                || !validaciones.esEspecieValida(nuevo.getEspecie())
                || !validaciones.esEdadValida(nuevo.getEdadEstimada())
                || !validaciones.esEstadoClinicoValido(nuevo.getEstadoClinico())
                || !validaciones.esEstadoAdopcionValido(nuevo.getEstadoAdopcion())) {
            bitacora.registrarError(usuarioActual, "ANIMALES", "VALIDACION", "Datos inválidos para " + nuevo.getCodigo());
            return false;
        }

        animales[total] = nuevo;
        total++;
        bitacora.registrarAccion(usuarioActual, "ANIMALES", "ALTA", "Animal " + nuevo.getCodigo() + " registrado");
        return true;
    }

    public Animal buscarPorCodigo(String codigo) {
        for (int i = 0; i < total; i++) {
            if (animales[i].getCodigo().equals(codigo)) {
                return animales[i];
            }
        }
        return null;
    }

    public boolean editarEstado(String usuarioActual, String codigo, String nuevoEstadoClinico) {
        Animal animal = buscarPorCodigo(codigo);
        if (animal == null) {
            bitacora.registrarError(usuarioActual, "ANIMALES", "EDITAR", "Animal no encontrado: " + codigo);
            return false;
        }
        if (!validaciones.esEstadoClinicoValido(nuevoEstadoClinico)) {
            bitacora.registrarError(usuarioActual, "ANIMALES", "VALIDACION", "Estado clínico inválido: " + nuevoEstadoClinico);
            return false;
        }
        animal.setEstadoClinico(nuevoEstadoClinico);
        bitacora.registrarAccion(usuarioActual, "ANIMALES", "EDITAR", "Animal " + codigo + " -> " + nuevoEstadoClinico);
        return true;
    }
    
    public boolean editarEstadoAdopcion(String usuarioActual, String codigo, String nuevoEstadoAdopcion) {
    Animal animal = buscarPorCodigo(codigo);
    if (animal == null || !validaciones.esEstadoAdopcionValido(nuevoEstadoAdopcion)) {
        bitacora.registrarError(usuarioActual, "ANIMALES", "VALIDACION", "No se pudo cambiar estado de adopción: " + codigo);
        return false;
    }
    animal.setEstadoAdopcion(nuevoEstadoAdopcion);
    bitacora.registrarAccion(usuarioActual, "ANIMALES", "EDITAR", "Animal " + codigo + " -> " + nuevoEstadoAdopcion);
    return true;
}

    public boolean eliminarLogico(String usuarioActual, String codigo) {
        Animal animal = buscarPorCodigo(codigo);
        if (animal == null || "ELIMINADO".equals(animal.getEstadoAdopcion())) {
            bitacora.registrarError(usuarioActual, "ANIMALES", "BAJA", "No se puede eliminar: " + codigo);
            return false;
        }
        animal.setEstadoAdopcion("ELIMINADO");
        bitacora.registrarAccion(usuarioActual, "ANIMALES", "BAJA", "Animal " + codigo + " eliminado (lógico)");
        return true;
    }

    public Animal[] listarActivos() {
        int cantidad = 0;
        for (int i = 0; i < total; i++) {
            if (!"ELIMINADO".equals(animales[i].getEstadoAdopcion())) cantidad++;
        }
        Animal[] activos = new Animal[cantidad];
        int j = 0;
        for (int i = 0; i < total; i++) {
            if (!"ELIMINADO".equals(animales[i].getEstadoAdopcion())) {
                activos[j] = animales[i];
                j++;
            }
        }
        return activos;
    }
}