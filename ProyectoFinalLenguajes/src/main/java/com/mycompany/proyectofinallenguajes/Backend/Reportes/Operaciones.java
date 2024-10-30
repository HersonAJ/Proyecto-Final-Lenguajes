/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend.Reportes;

/**
 *
 * @author herson
 */
import java.awt.BorderLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Operaciones {
    private String tipoOperacion;
    private int conteo;

    public Operaciones(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
        this.conteo = 0;
    }

    public void incrementarConteo() {
        this.conteo++;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public int getConteo() {
        return conteo;
    }

    public static List<Operaciones> analizarOperaciones(String codigoFuente) {
        List<Operaciones> operaciones = new ArrayList<>();
        operaciones.add(new Operaciones("CREATE"));
        operaciones.add(new Operaciones("DELETE"));
        operaciones.add(new Operaciones("UPDATE"));
        operaciones.add(new Operaciones("SELECT"));
        operaciones.add(new Operaciones("ALTER"));

        for (Operaciones operacion : operaciones) {
            Pattern pattern = Pattern.compile("\\b" + operacion.getTipoOperacion() + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(codigoFuente);

            while (matcher.find()) {
                operacion.incrementarConteo();
            }
        }

        return operaciones;
    }

    public static void mostrarReporteOperaciones(List<Operaciones> operaciones) {
        JFrame frame = new JFrame("Reporte de Operaciones");
        frame.setLayout(new BorderLayout());
        DefaultTableModel modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Operación");
        modeloTabla.addColumn("Conteo");

        for (Operaciones operacion : operaciones) {
            modeloTabla.addRow(new Object[]{operacion.getTipoOperacion(), operacion.getConteo()});
        }

        JTable tablaReporte = new JTable(modeloTabla);
        JScrollPane panelScroll = new JScrollPane(tablaReporte);
        frame.add(panelScroll, BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
