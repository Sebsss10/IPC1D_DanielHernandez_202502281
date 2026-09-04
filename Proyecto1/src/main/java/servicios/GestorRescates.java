package servicios;

import modelo.Animal;
import modelo.Rescate;
import util.validaciones;

public class GestorRescates {

    private static final int MAX_RESCATES = 100;

    private Rescate[] rescates = new Rescate[MAX_RESCATES];
    private int total = 0;

    private GestorBitacora bitacora;
    private GestorAnimales gestorAnimales;

    public GestorRescates(GestorBitacora bitacora, GestorAnimales gestorAnimales) {
        this.bitacora = bitacora;
        this.gestorAnimales = gestorAnimales;
    }

    public boolean registrar(String usuarioActual, Rescate nuevo) {
        if (total >= MAX_RESCATES) {
            bitacora.registrarError(usuarioActual, "RESCATES", "ALTA", "Capacidad máxima alcanzada");
            return false;
        }
        if (!validaciones.esCodigoValido("R-", nuevo.getCodigo())
                || !validaciones.esPrioridadValida(nuevo.getPrioridad())
                || !validaciones.esFechaValida(nuevo.getFechaReporte())) {
            bitacora.registrarError(usuarioActual, "RESCATES", "VALIDACION", "Datos inválidos para " + nuevo.getCodigo());
            return false;
        }

        rescates[total] = nuevo;
        total++;
        bitacora.registrarAccion(usuarioActual, "RESCATES", "ALTA", "Rescate " + nuevo.getCodigo() + " registrado");
        return true;
    }

    public boolean atender(String usuarioActual, String codigoRescate, String codigoAnimalExistente) {
        Rescate rescate = buscarPorCodigo(codigoRescate);
        if (rescate == null) {
            bitacora.registrarError(usuarioActual, "RESCATES", "ATENDER", "Rescate no encontrado: " + codigoRescate);
            return false;
        }

        if (codigoAnimalExistente != null && !codigoAnimalExistente.isEmpty()) {
            // Ya existe un animal para este caso: solo lo vinculamos
            if (gestorAnimales.buscarPorCodigo(codigoAnimalExistente) == null) {
                bitacora.registrarError(usuarioActual, "RESCATES", "ATENDER", "Animal no existe: " + codigoAnimalExistente);
                return false;
            }
            rescate.setCodigoAnimalVinculado(codigoAnimalExistente);
        } else {
            // No existe animal: se crea uno nuevo reutilizando el consecutivo
            // ej: rescate "R-009" -> animal "A-009"
            String numero = rescate.getCodigo().substring("R-".length());
            String codigoNuevoAnimal = "A-" + numero;

            Animal animalNuevo = new Animal(codigoNuevoAnimal, "Perro", 0, "EN_TRATAMIENTO", "DISPONIBLE");
            // Nota: "Perro" y edad 0 son valores por defecto de ejemplo.
            // Lo ideal es que tu UI le pida especie/edad real al usuario
            // antes de llamar a este método, y los reciba como parámetros.
            boolean creado = gestorAnimales.registrar(usuarioActual, animalNuevo);
            if (!creado) {
                bitacora.registrarError(usuarioActual, "RESCATES", "ATENDER", "No se pudo crear animal vinculado: " + codigoNuevoAnimal);
                return false;
            }
            rescate.setCodigoAnimalVinculado(codigoNuevoAnimal);
        }

        rescate.setEstado("ATENDIDO");
        bitacora.registrarAccion(usuarioActual, "RESCATES", "ATENDER", "Rescate " + codigoRescate + " atendido, animal " + rescate.getCodigoAnimalVinculado());
        return true;
    }

    public Rescate buscarPorCodigo(String codigo) {
        for (int i = 0; i < total; i++) {
            if (rescates[i].getCodigo().equals(codigo)) return rescates[i];
        }
        return null;
    }

    public Rescate[] listarActivosOrdenadosPorPrioridad() {
        Rescate[] copia = new Rescate[total];
        for (int i = 0; i < total; i++) copia[i] = rescates[i];

        // Ordenamiento simple tipo "burbuja": ALTA primero, luego MEDIA, luego BAJA
        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - 1 - i; j++) {
                if (prioridadValor(copia[j].getPrioridad()) > prioridadValor(copia[j + 1].getPrioridad())) {
                    Rescate temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }

    private int prioridadValor(String prioridad) {
        if ("ALTA".equals(prioridad)) return 0;
        if ("MEDIA".equals(prioridad)) return 1;
        return 2; // BAJA
    }
}