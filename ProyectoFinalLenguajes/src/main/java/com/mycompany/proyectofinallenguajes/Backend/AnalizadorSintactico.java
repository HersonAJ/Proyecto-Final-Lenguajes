/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend;

import java.util.List;

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
                parseSentencia();
                avanzarAlSiguienteToken(); // Avanzar para la próxima sentencia
            } catch (ParserException e) {
                errores.add(e.getMessage());
                avanzarAlSiguienteToken(); // Avanza el índice para continuar con el siguiente token después de un error
            }
        }
        
        if (!errores.isEmpty()) {
            for (String error : errores) {
                System.err.println("Error sintáctico: " + error);
            }
        } else {
            System.out.println("Análisis sintáctico completado con éxito.");
        }
    }

    private void parseSentencia() throws ParserException {
        match(TokenType.PALABRA_RESERVADA, "CREATE");
        Token nextToken = tokens.get(currentTokenIndex);
        if (nextToken.getType() == TokenType.PALABRA_RESERVADA && nextToken.getValue().equalsIgnoreCase("DATABASE")) {
            parseCrearBD();
        } else if (nextToken.getType() == TokenType.PALABRA_RESERVADA && nextToken.getValue().equalsIgnoreCase("TABLE")) {
            parseCrearTabla();
        } else {
            throw new ParserException("Se esperaba DATABASE o TABLE después de CREATE", nextToken.getLinea(), nextToken.getColumna());
        }
    }

    private void parseCrearBD() throws ParserException {
        match(TokenType.PALABRA_RESERVADA, "DATABASE");
        match(TokenType.IDENTIFICADOR);
    }

    private void parseCrearTabla() throws ParserException {
        match(TokenType.PALABRA_RESERVADA, "TABLE");
        match(TokenType.IDENTIFICADOR);
    }

    private void match(TokenType expectedType, String expectedValue) throws ParserException {
        if (currentTokenIndex >= tokens.size()) {
            throw new ParserException("Se esperaba " + expectedType + " con valor " + expectedValue,
                    currentTokenIndex == 0 ? 1 : tokens.get(currentTokenIndex - 1).getLinea(),
                    currentTokenIndex == 0 ? 1 : tokens.get(currentTokenIndex - 1).getColumna());
        }

        Token tokenActual = tokens.get(currentTokenIndex);
        if (tokenActual.getType() != expectedType || !tokenActual.getValue().equalsIgnoreCase(expectedValue)) {
            throw new ParserException("Se esperaba " + expectedType + " con valor " + expectedValue + ", pero se encontró " + tokenActual.getType() + " con valor " + tokenActual.getValue(),
                    tokenActual.getLinea(), tokenActual.getColumna());
        }

        avanzarAlSiguienteToken();
    }

    private void match(TokenType expectedType) throws ParserException {
        if (tokens.get(currentTokenIndex).getType() != expectedType) {
            throw new ParserException("Se esperaba " + expectedType,
                    tokens.get(currentTokenIndex).getLinea(), tokens.get(currentTokenIndex).getColumna());
        }
        avanzarAlSiguienteToken();
    }

    private void avanzarAlSiguienteToken() {
        if (currentTokenIndex < tokens.size()) {
            currentTokenIndex++;
        }
    }
}








