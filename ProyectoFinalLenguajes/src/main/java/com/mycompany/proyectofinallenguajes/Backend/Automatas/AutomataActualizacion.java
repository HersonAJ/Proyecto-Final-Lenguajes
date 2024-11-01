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
public class AutomataActualizacion {
    private Estado estadoActual;
    private Set<String> datosPrimitivos;

    public AutomataActualizacion() {
        this.estadoActual = Estado.Q0;
        this.datosPrimitivos = new HashSet<>();
        inicializarDatosPrimitivos();
    }

    private void inicializarDatosPrimitivos() {
        // Añadir los datos primitivos
        datosPrimitivos.add("TRUE");
        datosPrimitivos.add("FALSE");
    }

    public boolean esDatoPrimitivo(String token) {
        return datosPrimitivos.contains(token) || token.matches("\\d+") || token.matches("\\d+\\.\\d+") || token.matches("'[^']*'") || token.matches("'\\d{4}-\\d{2}-\\d{2}'");
    }

    public Token procesarToken(Token token) {
        estadoActual = Estado.Q0;
        String[] partes = token.getValue().split("\\s+|,");
        for (String parte : partes) {
            switch (estadoActual) {
                case Q0:
                    if (parte.equalsIgnoreCase("UPDATE")) {
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
                    if (parte.equalsIgnoreCase("SET")) {
                        estadoActual = Estado.Q3;
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
                    if (parte.equalsIgnoreCase("=")) {
                        estadoActual = Estado.Q5;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q5:
                    if (esDatoPrimitivo(parte)) {
                        estadoActual = Estado.Q6;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q6:
                    if (parte.equalsIgnoreCase("WHERE")) {
                        estadoActual = Estado.Q7;
                    } else if (parte.equalsIgnoreCase(",")) {
                        estadoActual = Estado.Q3;
                    } else if (parte.equals(";")) {
                        estadoActual = Estado.QF;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q7:
                    if (parte.matches("[a-zA-Z_][a-zA-Z0-9_]*")) { // Identificador
                        estadoActual = Estado.Q8;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q8:
                    if (parte.equalsIgnoreCase("=")) {
                        estadoActual = Estado.Q9;
                    } else {
                        estadoActual = Estado.ERROR;
                    }
                    break;
                case Q9:
                    if (esDatoPrimitivo(parte)) {
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
        Q0, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, QF, ERROR
    }

}


