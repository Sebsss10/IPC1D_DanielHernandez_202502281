package vista;

import servicios.*;
import modelo.Usuario;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal(Usuario usuarioActual, GestorBitacora bitacora,
                             GestorAnimales gestorAnimales, GestorAdoptantes gestorAdoptantes,
                             GestorSolicitudes gestorSolicitudes, GestorRescates gestorRescates,
                             GestorUbicaciones gestorUbicaciones) {
        setTitle("Centro de Rescate Animal - " + usuarioActual.getUsuario() + " (" + usuarioActual.getRol() + ")");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel bienvenida = new JLabel("Bienvenido, " + usuarioActual.getUsuario(), SwingConstants.CENTER);
        add(bienvenida);
    }
}