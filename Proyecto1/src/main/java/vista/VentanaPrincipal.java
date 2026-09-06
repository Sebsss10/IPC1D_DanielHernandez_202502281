package vista;

import servicios.*;
import modelo.Animal;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

    private Usuario usuarioActual;
    private GestorBitacora bitacora;
    private GestorAnimales gestorAnimales;
    private GestorAdoptantes gestorAdoptantes;
    private GestorSolicitudes gestorSolicitudes;
    private GestorRescates gestorRescates;
    private GestorUbicaciones gestorUbicaciones;

    private DefaultTableModel modeloTablaAnimales;
    private JTable tablaAnimales;

    public VentanaPrincipal(Usuario usuarioActual, GestorBitacora bitacora,
                             GestorAnimales gestorAnimales, GestorAdoptantes gestorAdoptantes,
                             GestorSolicitudes gestorSolicitudes, GestorRescates gestorRescates,
                             GestorUbicaciones gestorUbicaciones) {
        this.usuarioActual = usuarioActual;
        this.bitacora = bitacora;
        this.gestorAnimales = gestorAnimales;
        this.gestorAdoptantes = gestorAdoptantes;
        this.gestorSolicitudes = gestorSolicitudes;
        this.gestorRescates = gestorRescates;
        this.gestorUbicaciones = gestorUbicaciones;

        construirVentana();
    }

    private void construirVentana() {
        setTitle("Centro de Rescate Animal - " + usuarioActual.getUsuario() + " (" + usuarioActual.getRol() + ")");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Animales", construirPanelAnimales());
        // más adelante: pestañas.addTab("Adoptantes", construirPanelAdoptantes());
        // pestañas.addTab("Solicitudes", ...); etc.

        add(pestañas);
    }

    private JPanel construirPanelAnimales() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        // --- Formulario superior ---
        JPanel formulario = new JPanel(new GridLayout(2, 4, 5, 5));
        JTextField campoCodigo = new JTextField();
        JTextField campoEspecie = new JTextField();
        JTextField campoEdad = new JTextField();
        JTextField campoEstadoClinico = new JTextField();

        formulario.add(new JLabel("Código (A-XXX):"));
        formulario.add(campoCodigo);
        formulario.add(new JLabel("Especie:"));
        formulario.add(campoEspecie);
        formulario.add(new JLabel("Edad:"));
        formulario.add(campoEdad);
        formulario.add(new JLabel("Estado clínico:"));
        formulario.add(campoEstadoClinico);

        JButton botonRegistrar = new JButton("Registrar animal");

        // --- Tabla ---
        modeloTablaAnimales = new DefaultTableModel(
                new Object[]{"Código", "Especie", "Edad", "Estado clínico", "Estado adopción"}, 0);
        tablaAnimales = new JTable(modeloTablaAnimales);
        JScrollPane scroll = new JScrollPane(tablaAnimales);

        botonRegistrar.addActionListener((ActionEvent e) -> {
            String codigo = campoCodigo.getText().trim();
            String especie = campoEspecie.getText().trim();
            String estadoClinico = campoEstadoClinico.getText().trim().toUpperCase();
            int edad;

            try {
                edad = Integer.parseInt(campoEdad.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un número entero.");
                return;
            }

            Animal nuevo = new Animal(codigo, especie, edad, estadoClinico, "DISPONIBLE");
            boolean exito = gestorAnimales.registrar(usuarioActual.getUsuario(), nuevo);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Animal registrado correctamente.");
                campoCodigo.setText("");
                campoEspecie.setText("");
                campoEdad.setText("");
                campoEstadoClinico.setText("");
                actualizarTablaAnimales();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar. Revisa los datos (código único con prefijo A-, especie Perro/Gato, edad 0-25, estado clínico EN_OBSERVACION/EN_TRATAMIENTO/APTO).");
            }
        });

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(formulario, BorderLayout.CENTER);
        panelSuperior.add(botonRegistrar, BorderLayout.SOUTH);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        actualizarTablaAnimales();
        return panel;
    }

    private void actualizarTablaAnimales() {
        modeloTablaAnimales.setRowCount(0); // limpia la tabla
        Animal[] animales = gestorAnimales.listarActivos();
        for (int i = 0; i < animales.length; i++) {
            Animal a = animales[i];
            modeloTablaAnimales.addRow(new Object[]{
                a.getCodigo(), a.getEspecie(), a.getEdadEstimada(),
                a.getEstadoClinico(), a.getEstadoAdopcion()
            });
        }
    }
}