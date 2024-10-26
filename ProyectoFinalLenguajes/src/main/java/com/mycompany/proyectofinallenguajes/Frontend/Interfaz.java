/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Frontend;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyectofinallenguajes.Backend.Grafica;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import  com.mycompany.proyectofinallenguajes.Backend.Token;
import com.mycompany.proyectofinallenguajes.Lexer.Lexer;
import java.io.StringReader;

/**
 *
 * @author herson
 */

public class Interfaz extends JFrame {
    private JTextPane textPane1;
    private JLabel posLabel;
    private JLabel errorLabel; // Para mostrar el mensaje de error

    private JButton boton;

    public Interfaz() {
        // Aplicar el Look and Feel de FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setTitle("Analizador Gráfico");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem abrirItem = new JMenuItem("Abrir");
        archivoMenu.add(abrirItem);

        JMenu generarGraficoMenu = new JMenu("Generar Gráfico");
        JMenuItem generarGrafico = new JMenuItem("Crear Grafico");
        generarGraficoMenu.add(generarGrafico);
        
        
        
        JMenu reportesMenu = new JMenu("Reportes");
        JMenuItem reporteErrores = new JMenuItem("Reporte de errores");
        reportesMenu.add(reporteErrores);

        menuBar.add(archivoMenu);
        menuBar.add(generarGraficoMenu);
        menuBar.add(reportesMenu);
        setJMenuBar(menuBar);

        // Crear el JTextPane con números de línea
        textPane1 = new JTextPane();
        LineNumberingTextArea lineNumberingTextPane = new LineNumberingTextArea(textPane1);
        lineNumberingTextPane.setEditable(false);

        // Crear un JScrollPane que contenga ambos JTextPanes
        JScrollPane scrollPane = new JScrollPane(textPane1);
        scrollPane.setRowHeaderView(lineNumberingTextPane);

        // Crear la etiqueta para la posición del cursor
        posLabel = new JLabel("Fila 1, Palabra 1");

        // Crear la etiqueta para el mensaje de error
        errorLabel = new JLabel(" "); // Inicialmente vacío
        errorLabel.setForeground(Color.RED);

        // Actualizar la posición del cursor basado en palabras
        textPane1.addCaretListener(e -> {
            int caretPosition = textPane1.getCaretPosition();
            int line = 0, wordCount = 0;
            try {
                line = textPane1.getDocument().getDefaultRootElement().getElementIndex(caretPosition) + 1;
                int start = textPane1.getDocument().getDefaultRootElement().getElement(line - 1).getStartOffset();
                int end = caretPosition;

                // Obtener el texto de la línea actual
                String currentLine = textPane1.getText(start, end - start);

                // Separar la línea en palabras y contar las palabras hasta la posición del cursor
                String[] words = currentLine.trim().split("\\s+"); // Dividir en palabras ignorando espacios
                wordCount = words.length;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            posLabel.setText("Fila " + line + ", Columna " + wordCount);
        });

        // Configurar el layout del JFrame usando GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar scrollPane
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Crear el botón "Analizar"
        boton = new JButton("Analizar");
        // Configurar el botón de Analizar
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;  // Ubicado en la parte inferior izquierda
        add(boton, gbc);

        // Añadir el JLabel para la posición del cursor
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;  // Ubicado en la parte inferior derecha
        add(posLabel, gbc);

        // Añadir el JLabel para el mensaje de error
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;  // Ubicado en la parte inferior izquierda
        add(errorLabel, gbc);

        // Añadir acción al menú "Abrir"
        abrirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        textPane1.setText("");
                        String line;
                        while ((line = br.readLine()) != null) {
                            textPane1.getDocument().insertString(textPane1.getDocument().getLength(), line + "\n", null);
                        }
                    } catch (IOException | BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        
boton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String codigoFuente = textPane1.getText();
        Lexer lexer = new Lexer(new StringReader(codigoFuente));
        int token;
        try {
            while ((token = lexer.yylex()) != -1) {
                System.out.println(token);
            }
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.util.List<Token> listaTokens = lexer.getLista();
        System.out.println("Lista de tokens: " + listaTokens);
        System.out.println("Lista de errores: " + lexer.getListaErrores());



        colorearTokens(listaTokens);
    }
});


//listener para crear archivo 


// Añadir acción al ítem "Crear Grafico"
generarGrafico.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener el texto de entrada del JTextPane
        String codigoFuente = textPane1.getText();

        // Abrir JFileChooser para elegir la ruta de guardado
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar ubicación para guardar la imagen");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            String filePath = archivo.getAbsolutePath();

            // Asegurarse de que el archivo tenga la extensión .png
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }

            try {
                // Analizar el código fuente y generar la imagen
                Grafica.generarGraphviz(codigoFuente, filePath);
                System.out.println("Gráfico generado y guardado en: " + filePath);
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
});



    }

    // Método para resaltar el texto con errores
    private void resaltarError(int linea, int columna) {
        int start = textPane1.getDocument().getDefaultRootElement().getElement(linea).getStartOffset() + columna;
        int end = start + 1; // Resaltar un carácter
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setBackground(attributeSet, Color.RED);
        textPane1.getStyledDocument().setCharacterAttributes(start, end - start, attributeSet, false);
    }
    
    public void colorearTokens(java.util.List<Token> tokens) {
        StyledDocument doc = textPane1.getStyledDocument();

        for (Token token : tokens) {
            SimpleAttributeSet attributeSet = new SimpleAttributeSet();
            Color color = getColorByName(token.getColor()); 
            StyleConstants.setForeground(attributeSet, color);

            int start = calcularPosicionInicio(token.getLinea(), token.getColumna(), textPane1);
            int length = token.getValue().length(); // Usando 'value' para determinar la longitud del token

            try {
                doc.setCharacterAttributes(start, length, attributeSet, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int calcularPosicionInicio(int linea, int columna, JTextPane textPane) {
        Element root = textPane.getDocument().getDefaultRootElement();
        Element lineElement = root.getElement(linea);
        return lineElement.getStartOffset() + columna;
    }

    private Color getColorByName(String colorName) {
        switch (colorName.toLowerCase()) {
            case "azul":
                return Color.BLUE;
            case "fucsia":
                return Color.MAGENTA;
            case "negro":
                return Color.BLACK;
            case "naranja":
                return  Color.ORANGE;
            case "morado":
                return  new Color(128, 0, 128);
            case "amarillo":
                return Color.YELLOW;
            case "verde":
                return Color.GREEN;
            case "gris":
                return Color.GRAY;
            default:
                return Color.BLACK; // Color por defecto
        }
    }
}