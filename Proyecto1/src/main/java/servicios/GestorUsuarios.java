package servicios;

import modelo.Usuario;

public class GestorUsuarios {

    private Usuario[] usuarios;
    private int intentosFallidos = 0;
    private boolean bloqueado = false;

    private GestorBitacora bitacora;
    public GestorUsuarios(GestorBitacora bitacora) {
        this.bitacora = bitacora;
        // Usuarios cargados desde memoria (no desde archivo), según el enunciado
        usuarios = new Usuario[] {
            new Usuario("admin1", "Refugio2026", "ADMIN"),
            new Usuario("auxiliar1", "Refugio2026", "AUXILIAR")
        };
    } 
    public Usuario autenticar(String usuarioIngresado, String contrasenaIngresada) {
        if (bloqueado) {
            bitacora.registrarError(usuarioIngresado, "AUTENTICACION", "LOGIN_FALLIDO", "Sesión bloqueada, reinicie la aplicación");
            return null;
        }
 
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i].getUsuario().equals(usuarioIngresado)
                    && usuarios[i].getContrasena().equals(contrasenaIngresada)) {
                intentosFallidos = 0; // se resetea al loguear bien
                bitacora.registrarAccion(usuarioIngresado, "AUTENTICACION", "LOGIN_OK", "Inicio de sesión correcto");
                return usuarios[i];
            }
        }

        intentosFallidos++;
        bitacora.registrarError(usuarioIngresado, "AUTENTICACION", "LOGIN_FALLIDO",
                "Contraseña incorrecta (intento " + intentosFallidos + " de 3)");

        if (intentosFallidos >= 3) {
            bloqueado = true;
        }
        return null;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }
}  

