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
public class AutomataEstructuraDeclaracion {
    private Estado estadoActual;
    private Set<String> tiposDeDato;

    public AutomataEstructuraDeclaracion() {
        inicializarTiposDeDato();
        estadoActual = Estado.Q0;
    }

    private void inicializarTiposDeDato() {
        tiposDeDato = new HashSet<>();
      
        tiposDeDato.add("SERIAL");
        tiposDeDato.add("INTEGER");
        tiposDeDato.add("BIGINT");
        tiposDeDato.add("VARCHAR");
        tiposDeDato.add("DECIMAL");
        tiposDeDato.add("NUMERIC");
        tiposDeDato.add("DATE");
        tiposDeDato.add("TEXT");
        tiposDeDato.add("BOOLEAN");
    }

    public boolean esTipoDeDato(String token) {
        return tiposDeDato.contains(token);
    }

    public Token procesarToken(Token token) {
        estadoActual = Estado.Q0;
        String[] partes = token.getValue().split("\\s+");

        for (String parte : partes) {
            switch (estadoActual) {
                case Q0:
                    if (parte.matches("[a-zA-Z_][a-zA-Z0-9_]*")) { // Identificador
                        estadoActual = Estado.Q1;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q1:
                    if (esTipoDeDato(parte)) {
                        estadoActual = Estado.Q2;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q2:
                    if (parte.equals("PRIMARY") || parte.equals("NOT") || parte.equals("UNIQUE")) {
                        estadoActual = Estado.Q3;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q3:
                    if (parte.equals("KEY") || parte.equals("NULL")) {
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
        Q0, Q1, Q2, Q3, QF, ERROR
    }
}

