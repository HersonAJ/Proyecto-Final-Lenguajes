/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend;

/**
 *
 * @author herson
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteErrores extends JFrame {

    private JTable tablaErrores;
    private DefaultTableModel modeloTabla;

    public ReporteErrores(List<Token> listaErrores) {
        super("Reporte de Errores");
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Token");
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

    private void agregarErroresTabla(List<Token> listaErrores) {
        for (Token token : listaErrores) {
            Object[] fila = new Object[] {
                token.getLexema(),
                token.getLinea(),
                token.getColumna(),
                "Error en token"
            };
            modeloTabla.addRow(fila);
        }
    }
}