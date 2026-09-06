package com.mycompany.proyecto1;

import servicios.*;
import vista.VentanaLogin;


public class Proyecto1 {

    public static void main(String[] args) {
        GestorBitacora bitacora = new GestorBitacora();
        GestorAnimales gestorAnimales = new GestorAnimales(bitacora);
        GestorAdoptantes gestorAdoptantes = new GestorAdoptantes(bitacora);
        GestorSolicitudes gestorSolicitudes = new GestorSolicitudes(bitacora, gestorAnimales, gestorAdoptantes);
        GestorRescates gestorRescates = new GestorRescates(bitacora, gestorAnimales);
        GestorUbicaciones gestorUbicaciones = new GestorUbicaciones(bitacora);
        GestorUsuarios gestorUsuarios = new GestorUsuarios(bitacora);

        java.awt.EventQueue.invokeLater(() -> {
            VentanaLogin login = new VentanaLogin(gestorUsuarios, bitacora, gestorAnimales,
                    gestorAdoptantes, gestorSolicitudes, gestorRescates, gestorUbicaciones);
            login.setVisible(true);
        });
    }
}