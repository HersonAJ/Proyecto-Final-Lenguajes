/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend.Automatas;

import com.mycompany.proyectofinallenguajes.Backend.Token;
import com.mycompany.proyectofinallenguajes.Backend.TokenType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herson
 */
public class AnalizadorSintactico2 {
    private AutomataCREATE automataCREATE;
    private AutomataTable automataTable;
    private AutomataModificadores automataModificadores;
    private AutomataInsercion automataInsercion;
    private AutomataLectura automataLectura;
    private AutomataActualizacion automataActualizacion;
    private AutomataEliminacion automataEliminacion;
    public List<String> errores;

    public AnalizadorSintactico2() {
        this.automataCREATE = new AutomataCREATE();
        this.automataTable = new AutomataTable();
        this.automataModificadores = new AutomataModificadores();
        this.automataInsercion = new AutomataInsercion();
        this.automataLectura = new AutomataLectura();
        this.automataActualizacion = new AutomataActualizacion();
        this.automataEliminacion = new AutomataEliminacion();
        this.errores = new ArrayList<>();
    }

public List<Token> analizarTokens(List<Token> tokens) {
    List<Token> tokensValidados = new ArrayList<>();
    for (Token token : tokens) {
        Token nuevoToken = null;

        System.out.println("Procesando token: " + token.getValue());

        if (token.getValue().equalsIgnoreCase("CREATE")) {
            nuevoToken = automataCREATE.procesarToken(token);
            System.out.println("Token procesado por automataCREATE: " + nuevoToken);
        } else if (token.getValue().equalsIgnoreCase("INSERT")) {
            nuevoToken = automataInsercion.procesarToken(token);
            System.out.println("Token procesado por automataInsercion: " + nuevoToken);
        } else if (token.getValue().equalsIgnoreCase("SELECT")) {
            nuevoToken = automataLectura.procesarToken(token);
            System.out.println("Token procesado por automataLectura: " + nuevoToken);
        } else if (token.getValue().equalsIgnoreCase("UPDATE")) {
            nuevoToken = automataActualizacion.procesarToken(token);
            System.out.println("Token procesado por automataActualizacion: " + nuevoToken);
        } else if (token.getValue().equalsIgnoreCase("DELETE")) {
            nuevoToken = automataEliminacion.procesarToken(token);
            System.out.println("Token procesado por automataEliminacion: " + nuevoToken);
        } else if (token.getValue().equalsIgnoreCase("ALTER") || token.getValue().equalsIgnoreCase("DROP")) {
            nuevoToken = automataModificadores.procesarToken(token);
            System.out.println("Token procesado por automataModificadores: " + nuevoToken);
        } else {
            nuevoToken = new Token(TokenType.ERROR, token.getValue(), token.getLinea(), token.getColumna(), "rojo");
            errores.add("Error: Token no reconocido - " + token.getValue() + " en línea: " + token.getLinea() + ", columna: " + token.getColumna());
        }

        if (nuevoToken != null) {
            tokensValidados.add(nuevoToken);
        }
    }
    return tokensValidados;
}


    public void parse(List<Token> tokens) {
        List<Token> tokensValidados = analizarTokens(tokens);
        if (errores.isEmpty()) {
            System.out.println("Análisis sintáctico completado con éxito.");
        } else {
            for (String error : errores) {
                System.err.println("Error sintáctico: " + error);
            }
        }
    }

    public List<String> getErrores() {
        return errores;
    }


}
