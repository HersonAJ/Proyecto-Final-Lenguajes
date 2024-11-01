/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend.Automatas;

import com.mycompany.proyectofinallenguajes.Backend.Token;
import com.mycompany.proyectofinallenguajes.Backend.TokenType;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author herson
 */
public class AutomataLectura {
    private Estado estadoActual;
    private Set<String> funcionesAgregacion;

    public AutomataLectura() {
        this.estadoActual = Estado.Q0;
        this.funcionesAgregacion = new HashSet<>();
        inicializarFuncionesAgregacion();
    }

    private void inicializarFuncionesAgregacion() {
        
        funcionesAgregacion.add("SUM");
        funcionesAgregacion.add("AVG");
        funcionesAgregacion.add("COUNT");
        funcionesAgregacion.add("MIN");
        funcionesAgregacion.add("MAX");
    }

    public boolean esFuncionAgregacion(String token) {
        return funcionesAgregacion.contains(token);
    }

    public Token procesarToken(Token token) {
        estadoActual = Estado.Q0;
        String[] partes = token.getValue().split("\\s+|,");

        for (String parte : partes) {
            switch (estadoActual) {
                case Q0:
                    if (parte.equalsIgnoreCase("SELECT")) {
                        estadoActual = Estado.Q1;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q1:
                    if (parte.equalsIgnoreCase("*") || parte.matches("[a-zA-Z_][a-zA-Z0-9_]*") || esFuncionAgregacion(parte)) {
                        estadoActual = Estado.Q2;
                    } else if (parte.matches("[a-zA-Z_][a-zA-Z0-9_]*\\.[a-zA-Z_][a-zA-Z0-9_]*")) {
                        estadoActual = Estado.Q2;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q2:
                    if (parte.equalsIgnoreCase("FROM")) {
                        estadoActual = Estado.Q3;
                    } else if (parte.equalsIgnoreCase("AS")) {
                        estadoActual = Estado.Q1;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q3:
                    if (parte.matches("[a-zA-Z_][a-zA-Z0-9_]*")) { // Identificador
                        estadoActual = Estado.Q4;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q4:
                    if (parte.equalsIgnoreCase("JOIN") || parte.equalsIgnoreCase("WHERE") || parte.equalsIgnoreCase("GROUP") || parte.equalsIgnoreCase("ORDER") || parte.equalsIgnoreCase("LIMIT") || parte.equalsIgnoreCase(";")) {
                        estadoActual = Estado.QF;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                default:
                    estadoActual = Estado.ERROR;
                    break;
            }
        }

        if (estadoActual == Estado.QF) {
            return new Token(TokenType.PALABRA_RESERVADA, token.getValue(), token.getLinea(), token.getColumna(), "naranja");
        }
        return new Token(TokenType.ERROR, token.getValue(), token.getLinea(), token.getColumna(), "rojo");
    }

    private enum Estado {
        Q0, Q1, Q2, Q3, Q4, QF, ERROR
    }

}
