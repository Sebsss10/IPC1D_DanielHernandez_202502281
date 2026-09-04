package modelo;

public class Solicitud {

    private String codigo;
    private String codigoAnimal;
    private String codigoAdoptante;
    private String fecha;
    private String estado;

    public Solicitud(String codigo, String codigoAnimal, String codigoAdoptante,
                      String fecha, String estado) {
        this.codigo = codigo;
        this.codigoAnimal = codigoAnimal;
        this.codigoAdoptante = codigoAdoptante;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getCodigo() { return codigo; }
    public String getCodigoAnimal() { return codigoAnimal; }
    public String getCodigoAdoptante() { return codigoAdoptante; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return codigo + " | " + codigoAnimal + " | " + codigoAdoptante + " | " + estado;
    }
}