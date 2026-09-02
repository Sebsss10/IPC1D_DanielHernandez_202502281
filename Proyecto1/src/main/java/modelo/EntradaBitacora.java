package modelo;

public class EntradaBitacora {

    private String fecha;
    private String usuario;
    private String modulo;
    private String evento;
    private String detalle;

    public EntradaBitacora(String fecha, String usuario, String modulo,
                            String evento, String detalle) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.modulo = modulo;
        this.evento = evento;
        this.detalle = detalle;
    }

    public String aLineaArchivo() {
        return fecha + "|" + usuario + "|" + modulo + "|" + evento + "|" + detalle;
    }

    public String getFecha() { return fecha; }
    public String getUsuario() { return usuario; }
    public String getModulo() { return modulo; }
    public String getEvento() { return evento; }
    public String getDetalle() { return detalle; }
}