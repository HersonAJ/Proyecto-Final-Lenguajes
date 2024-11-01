/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend.Automatas;

import com.mycompany.proyectofinallenguajes.Backend.Token;
import com.mycompany.proyectofinallenguajes.Backend.TokenType;

/**
 *
 * @author herson
 */
public class AutomataEstructuraLlaves {
    private Estado estadoActual;

    public AutomataEstructuraLlaves() {
        estadoActual = Estado.Q0;
    }

    public Token procesarToken(Token token) {
        estadoActual = Estado.Q0;
        String[] partes = token.getValue().split("\\s+");

        for (String parte : partes) {
            switch (estadoActual) {
                case Q0:
                    if (parte.equalsIgnoreCase("CONSTRAINT")) {
                        estadoActual = Estado.Q1;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q1:
                    if (parte.matches("[a-zA-Z_][a-zA-Z0-9_]*")) { // Identificador
                        estadoActual = Estado.Q2;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q2:
                    if (parte.equalsIgnoreCase("FOREIGN")) {
                        estadoActual = Estado.Q3;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q3:
                    if (parte.equalsIgnoreCase("KEY")) {
                        estadoActual = Estado.Q4;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q4:
                    if (parte.matches("\\(.+\\)")) { // Identificador entre paréntesis
                        estadoActual = Estado.Q5;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q5:
                    if (parte.equalsIgnoreCase("REFERENCE")) {
                        estadoActual = Estado.Q6;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q6:
                    if (parte.matches("[a-zA-Z_][a-zA-Z0-9_]*")) { // Identificador
                        estadoActual = Estado.Q7;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q7:
                    if (parte.matches("\\(.+\\)")) { // Identificador entre paréntesis
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
        Q0, Q1, Q2, Q3, Q4, Q5, Q6, Q7, QF, ERROR
    }
}