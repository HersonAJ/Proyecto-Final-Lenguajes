/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend;

/**
 *
 * @author herson
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class Grafica {
    private String nombre;
    private List<String> columnas;

    public Grafica(String nombre) {
        this.nombre = nombre;
        this.columnas = new ArrayList<>();
    }

    public void agregarColumna(String nombreColumna) {
        columnas.add(nombreColumna);
    }

    public static void generarGraphviz(String codigoFuente, String outputFilePath) throws IOException, InterruptedException {
        Pattern pattern = Pattern.compile("CREATE TABLE (\\w+) \\(([^;]+)\\);", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(codigoFuente);

        StringBuilder graphvizContent = new StringBuilder();
        graphvizContent.append("digraph G {\nnode [shape=record];\n");

        while (matcher.find()) {
            String nombreTabla = matcher.group(1);
            String columnasDefinicion = matcher.group(2);

            Grafica tabla = new Grafica(nombreTabla);
            String[] columnas = columnasDefinicion.split(",");
            for (String columna : columnas) {
                tabla.agregarColumna(columna.trim().split(" ")[0]);  // Asume que el nombre de la columna es la primera palabra
            }

            graphvizContent.append(nombreTabla)
                .append(" [label=\"{")
                .append(nombreTabla)
                .append("|")
                .append(String.join("|", tabla.columnas))
                .append("}\"];\n");
        }

        graphvizContent.append("}");

        File file = new File(outputFilePath.replace(".png", ".dot"));
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(graphvizContent.toString());
        }

        // Comando para generar la imagen a partir del archivo .dot
        String cmd = "dot -Tpng " + outputFilePath.replace(".png", ".dot") + " -o " + outputFilePath;
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
    }
}
