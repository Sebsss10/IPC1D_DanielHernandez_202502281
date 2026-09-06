package vista;

import servicios.*;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonIngresar;
    private JLabel mensaje;

    private GestorUsuarios gestorUsuarios;
    private GestorBitacora bitacora;
    private GestorAnimales gestorAnimales;
    private GestorAdoptantes gestorAdoptantes;
    private GestorSolicitudes gestorSolicitudes;
    private GestorRescates gestorRescates;
    private GestorUbicaciones gestorUbicaciones;

    public VentanaLogin(GestorUsuarios gestorUsuarios, GestorBitacora bitacora,
                         GestorAnimales gestorAnimales, GestorAdoptantes gestorAdoptantes,
                         GestorSolicitudes gestorSolicitudes, GestorRescates gestorRescates,
                         GestorUbicaciones gestorUbicaciones) {
        this.gestorUsuarios = gestorUsuarios;
        this.bitacora = bitacora;
        this.gestorAnimales = gestorAnimales;
        this.gestorAdoptantes = gestorAdoptantes;
        this.gestorSolicitudes = gestorSolicitudes;
        this.gestorRescates = gestorRescates;
        this.gestorUbicaciones = gestorUbicaciones;

        construirVentana();
    }

    private void construirVentana() {
        setTitle("Centro de Rescate Animal - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 8, 8));

        add(new JLabel("Usuario:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel("Contraseña:"));
        campoContrasena = new JPasswordField();
        add(campoContrasena);

        botonIngresar = new JButton("Ingresar");
        botonIngresar.addActionListener(this::intentarLogin);
        add(botonIngresar);

        mensaje = new JLabel("");
        mensaje.setForeground(Color.RED);
        add(mensaje);
    }

    private void intentarLogin(ActionEvent e) {
        String usuario = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        Usuario resultado = gestorUsuarios.autenticar(usuario, contrasena);

        if (resultado != null) {
            VentanaPrincipal principal = new VentanaPrincipal(resultado, bitacora,
                    gestorAnimales, gestorAdoptantes, gestorSolicitudes, gestorRescates, gestorUbicaciones);
            principal.setVisible(true);
            dispose();
        } else if (gestorUsuarios.isBloqueado()) {
            mensaje.setText("Sesión bloqueada, reinicie la aplicación");
            botonIngresar.setEnabled(false);
            campoUsuario.setEnabled(false);
            campoContrasena.setEnabled(false);
        } else {
            mensaje.setText("Usuario o contraseña incorrectos");
        }
    }
}