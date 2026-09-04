package modelo;

public class Animal {

    private String codigo;
    private String especie;
    private int edadEstimada;
    private String estadoClinico;
    private String estadoAdopcion;

    public Animal(String codigo, String especie, int edadEstimada,
                  String estadoClinico, String estadoAdopcion) {
        this.codigo = codigo;
        this.especie = especie;
        this.edadEstimada = edadEstimada;
        this.estadoClinico = estadoClinico;
        this.estadoAdopcion = estadoAdopcion;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public int getEdadEstimada() { return edadEstimada; }
    public void setEdadEstimada(int edadEstimada) { this.edadEstimada = edadEstimada; }

    public String getEstadoClinico() { return estadoClinico; }
    public void setEstadoClinico(String estadoClinico) { this.estadoClinico = estadoClinico; }

    public String getEstadoAdopcion() { return estadoAdopcion; }
    public void setEstadoAdopcion(String estadoAdopcion) { this.estadoAdopcion = estadoAdopcion; }

    @Override
    public String toString() {
        return codigo + " | " + especie + " | " + estadoClinico + " | " + estadoAdopcion;
    }
}