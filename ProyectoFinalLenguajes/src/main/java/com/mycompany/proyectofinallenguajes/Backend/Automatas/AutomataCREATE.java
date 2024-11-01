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
public class AutomataCREATE {
    private Estado estadoActual;

    public AutomataCREATE() {
        estadoActual = Estado.Q0;
    }

    public Token procesarToken(Token token) {
        estadoActual = Estado.Q0;
        String[] partes = token.getValue().split("\\s+|,");

        for (String parte : partes) {
            switch (estadoActual) {
                case Q0:
                    if (parte.equalsIgnoreCase("CREATE")) {
                        estadoActual = Estado.Q1;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q1:
                    if (parte.equalsIgnoreCase("TABLE")) {
                        estadoActual = Estado.Q2;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q2:
                    if (parte.matches("[a-zA-Z_][a-zA-Z0-9_]*")) { // Identificador
                        estadoActual = Estado.Q3;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q3:
                    if (parte.equals("(")) {
                        estadoActual = Estado.Q4;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q4:
                    
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
