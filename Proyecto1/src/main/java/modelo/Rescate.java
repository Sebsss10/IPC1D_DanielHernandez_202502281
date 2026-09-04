package modelo;

public class Rescate {

    private String codigo;
    private String prioridad;
    private String estado;
    private String fechaReporte;
    private String codigoAnimalVinculado;

    public Rescate(String codigo, String prioridad, String estado, String fechaReporte) {
        this.codigo = codigo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fechaReporte = fechaReporte;
        this.codigoAnimalVinculado = "";
    }

    public String getCodigo() { return codigo; }
    public String getPrioridad() { return prioridad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFechaReporte() { return fechaReporte; }
    public String getCodigoAnimalVinculado() { return codigoAnimalVinculado; }
    public void setCodigoAnimalVinculado(String codigoAnimalVinculado) { this.codigoAnimalVinculado = codigoAnimalVinculado; }

    @Override
    public String toString() {
        return codigo + " | " + prioridad + " | " + estado + " | " + codigoAnimalVinculado;
    }
}