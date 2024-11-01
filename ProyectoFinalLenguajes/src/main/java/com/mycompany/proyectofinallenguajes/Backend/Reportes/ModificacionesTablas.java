/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend.Reportes;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author herson
 */
public class ModificacionesTablas {
    private String nombreTabla;
    private List<Modificacion> modificaciones;

    public ModificacionesTablas (String nombreTabla) {
        this.nombreTabla = nombreTabla;
        this.modificaciones = new ArrayList<>();
    }

    public void agregarModificacion(String modificacion, int fila, int columna) {
        this.modificaciones.add(new Modificacion(modificacion, fila, columna));
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public List<Modificacion> getModificaciones() {
        return modificaciones;
    }

    public static List<ModificacionesTablas> analizarModificacionesTablas(String codigoFuente) {
        List<ModificacionesTablas> modificacionesTablas = new ArrayList<>();
        String[] lineas = codigoFuente.split("\n");
        Pattern pattern = Pattern.compile("ALTER TABLE (\\w+) (.+);", Pattern.CASE_INSENSITIVE);

        for (int i = 0; i < lineas.length; i++) {
            Matcher matcher = pattern.matcher(lineas[i]);

            if (matcher.find()) {
                String nombreTabla = matcher.group(1);
                String modificacion = matcher.group(2);
                int fila = i + 1; 
                int columna = matcher.start() + 1; 

                ModificacionesTablas modificacionTabla = new ModificacionesTablas(nombreTabla);
                modificacionTabla.agregarModificacion(modificacion, fila, columna);

                modificacionesTablas.add(modificacionTabla);
            }
        }

        return modificacionesTablas;
    }

    public static void mostrarReporteModificacionesTablas(List<ModificacionesTablas> modificacionesTablas) {
        JFrame frame = new JFrame("Reporte de Modificaciones de Tablas");
        frame.setLayout(new BorderLayout());
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Tabla");
        modeloTabla.addColumn("Modificación");
        modeloTabla.addColumn("Fila");
        modeloTabla.addColumn("Columna");

        for (ModificacionesTablas modificacionTabla : modificacionesTablas) {
            for (Modificacion modificacion : modificacionTabla.getModificaciones()) {
                modeloTabla.addRow(new Object[]{
                    modificacionTabla.getNombreTabla(),
                    modificacion.getModificacion(),
                    modificacion.getFila(),
                    modificacion.getColumna()
                });
            }
        }

        JTable tablaReporte = new JTable(modeloTabla);
        JScrollPane panelScroll = new JScrollPane(tablaReporte);
        frame.add(panelScroll, BorderLayout.CENTER);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static class Modificacion {
        private String modificacion;
        private int fila;
        private int columna;

        public Modificacion(String modificacion, int fila, int columna) {
            this.modificacion = modificacion;
            this.fila = fila;
            this.columna = columna;
        }

        public String getModificacion() {
            return modificacion;
        }

        public int getFila() {
            return fila;
        }

        public int getColumna() {
            return columna;
        }
    }
}
