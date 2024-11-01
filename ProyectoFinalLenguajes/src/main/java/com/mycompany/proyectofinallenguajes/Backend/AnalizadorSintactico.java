/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend;

/**
 *
 * @author herson
 */
import java.util.ArrayList;
import java.util.List;

public class AnalizadorSintactico {
    private List<Token> tokens;
    private int currentTokenIndex;
    public List<String> errores;

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        this.errores = new ArrayList<>();
    }

    public void parse() {
        while (currentTokenIndex < tokens.size()) {
            try {
                Token token = tokens.get(currentTokenIndex);

                // Identifica el tipo de sentencia en función del primer token
                if (token.getValue().equalsIgnoreCase("CREATE")) {
                    parseSentencia();
                } else {
                    throw new ParserException("Sentencia no reconocida: " + token.getValue(), token.getLinea(), token.getColumna());
                }
            } catch (ParserException e) {
                errores.add(e.getMessage());
                avanzarAlFinalDeSentencia();  // Avanza hasta el siguiente ';' para evitar ciclos infinitos
            }
        }

        // Imprime los errores al final, si los hay
        if (!errores.isEmpty()) {
            for (String error : errores) {
                System.err.println("Error sintáctico: " + error);
            }
        } else {
            System.out.println("Análisis sintáctico completado con éxito.");
        }
    }

    private void avanzarAlFinalDeSentencia() {
        while (currentTokenIndex < tokens.size()) {
            if (tokens.get(currentTokenIndex).getType() == TokenType.SIGNO 
                && tokens.get(currentTokenIndex).getValue().equals(";")) {
                currentTokenIndex++;
                break;
            }
            currentTokenIndex++;
        }
    }

    private void parseSentencia() throws ParserException {
        String estado = "Q0";
        boolean avanzar = true;

        while (currentTokenIndex < tokens.size() && avanzar) {
            Token token = tokens.get(currentTokenIndex);

            switch (estado) {
                case "Q0":
                    if (token.getValue().equalsIgnoreCase("CREATE")) {
                        estado = "Q1";
                    } else {
                        throw new ParserException("Se esperaba 'CREATE'", token.getLinea(), token.getColumna());
                    }
                    break;

                case "Q1":
                    if (token.getValue().equalsIgnoreCase("TABLE")) {
                        estado = "Q2";
                    } else {
                        throw new ParserException("Se esperaba 'TABLE' después de 'CREATE'", token.getLinea(), token.getColumna());
                    }
                    break;

                case "Q2":
                    parseEstructuraDeTabla();  // Llama a parseEstructuraDeTabla directamente
                    return; // Termina aquí si la estructura de tabla es válida

                default:
                    throw new ParserException("Estado desconocido: " + estado, token.getLinea(), token.getColumna());
            }

            currentTokenIndex++;
        }
    }

    private void parseEstructuraDeTabla() throws ParserException {
        String estado = "T0";
        boolean avanzar = true;

        while (currentTokenIndex < tokens.size() && avanzar) {
            Token token = tokens.get(currentTokenIndex);

            switch (estado) {
                case "T0":
                    if (token.getType() == TokenType.IDENTIFICADOR) {
                        estado = "T1";
                    } else {
                        throw new ParserException("Se esperaba un identificador para el nombre de la tabla", token.getLinea(), token.getColumna());
                    }
                    break;

                case "T1":
                    if (token.getType() == TokenType.SIGNO && token.getValue().equals("(")) {
                        estado = "T2";
                    } else {
                        throw new ParserException("Se esperaba '(' después del nombre de la tabla", token.getLinea(), token.getColumna());
                    }
                    break;

                case "T2":
                    parseEstructuraDeDeclaracion();
                    estado = "T3"; // Cambia al estado final si la declaración es válida
                    break;

                case "T3":
                    if (token.getType() == TokenType.SIGNO && token.getValue().equals(";")) {
                        return;  // Sentencia completa y válida
                    } else {
                        throw new ParserException("Se esperaba ';' al final de la sentencia", token.getLinea(), token.getColumna());
                    }

                default:
                    throw new ParserException("Estado desconocido en la estructura de tabla: " + estado, token.getLinea(), token.getColumna());
            }

            currentTokenIndex++;
        }
    }

    private void parseEstructuraDeDeclaracion() throws ParserException {
        String estado = "D0";

        while (currentTokenIndex < tokens.size()) {
            Token token = tokens.get(currentTokenIndex);

            // Mostrar el estado y el token actual
            System.out.println("Estado: " + estado + ", Token: " + token.getValue());

            switch (estado) {
                case "D0":
                    if (token.getType() == TokenType.IDENTIFICADOR) {
                        estado = "D1";
                    } else {
                        throw new ParserException("Se esperaba un identificador para el nombre de la columna", token.getLinea(), token.getColumna());
                    }
                    break;

                case "D1":
                    if (esTipoDeDatoValido(token)) {
                        estado = "D2";
                    } else {
                        throw new ParserException("Se esperaba un tipo de dato válido para la columna", token.getLinea(), token.getColumna());
                    }
                    break;

                case "D2":
                    // Maneja restricciones opcionales
                    if (token.getType() == TokenType.SIGNO && token.getValue().equals(",")) {
                        estado = "D0"; // Permite una nueva declaración de columna
                    } else if (token.getType() == TokenType.SIGNO && token.getValue().equals(")")) {
                        return; // Fin de declaraciones de columnas
                    } else if (token.getValue().equalsIgnoreCase("PRIMARY") && verificarTokenSiguiente("KEY")) {
                        estado = "D3";
                    } else if (token.getValue().equalsIgnoreCase("NOT") && verificarTokenSiguiente("NULL")) {
                        estado = "D3";
                    } else if (token.getValue().equalsIgnoreCase("UNIQUE")) {
                        estado = "D3";
                    } else {
                        throw new ParserException("Se esperaba una restricción opcional, ',' o ')'", token.getLinea(), token.getColumna());
                    }
                    break;

                case "D3":
                    // Después de una restricción, se espera ',' o ')'
                    if (token.getType() == TokenType.SIGNO && token.getValue().equals(",")) {
                        estado = "D0";
                    } else if (token.getType() == TokenType.SIGNO && token.getValue().equals(")")) {
                        return; // Fin de declaración de columna
                    } else {
                        throw new ParserException("Se esperaba ',' o ')' después de la restricción", token.getLinea(), token.getColumna());
                    }
                    break;

                default:
                    throw new ParserException("Estado desconocido en la estructura de declaración: " + estado, token.getLinea(), token.getColumna());
            }

            currentTokenIndex++;
        }

        // Si se sale del ciclo sin retornar, la declaración es incompleta
        throw new ParserException("Declaración de columna incompleta", tokens.get(currentTokenIndex - 1).getLinea(), tokens.get(currentTokenIndex - 1).getColumna());
    }


    // Métodos auxiliares
    private boolean esTipoDeDatoValido(Token token) {
        String[] tiposDeDato = {"SERIAL", "INTEGER", "BIGINT", "VARCHAR", "DECIMAL", "DATE", "TEXT", "BOOLEAN"};
        for (String tipo : tiposDeDato) {
            if (token.getValue().equalsIgnoreCase(tipo)) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarTokenSiguiente(String valorEsperado) {
        if (currentTokenIndex + 1 < tokens.size()) {
            Token siguienteToken = tokens.get(currentTokenIndex + 1);
            if (siguienteToken.getValue().equalsIgnoreCase(valorEsperado)) {
                currentTokenIndex++; // Avanza para consumir el token
                return true;
            }
        }
        return false;
    }

}