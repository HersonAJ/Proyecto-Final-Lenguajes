/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend.Reportes;

/**
 *
 * @author herson
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteErroresSintacticos extends JFrame {
    private JTable tablaErrores;
    private DefaultTableModel modeloTabla;

    public ReporteErroresSintacticos(List<SyntaxError> listaErrores) {
        super("Reporte de Errores Sintácticos");
        setLayout(new BorderLayout());
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Token");
        modeloTabla.addColumn("Tipo Token");
        modeloTabla.addColumn("Línea");
        modeloTabla.addColumn("Columna");
        modeloTabla.addColumn("Descripción");
        tablaErrores = new JTable(modeloTabla);
        agregarErroresTabla(listaErrores);
        JScrollPane panelScroll = new JScrollPane(tablaErrores);
        add(panelScroll, BorderLayout.CENTER);
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void agregarErroresTabla(List<SyntaxError> listaErrores) {
        for (SyntaxError error : listaErrores) {
            Object[] fila = new Object[] {
                error.getToken().getLexema(),
                error.getToken().getType(),
                error.getToken().getLinea(),
                error.getToken().getColumna(),
                error.getDescripcion()
            };
            modeloTabla.addRow(fila);
        }
    }

}






