/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend;

/**
 *
 * @author herson
 */
public class Token {
    private TokenType type;
    private String value;
    private int linea;
    private int columna;
    private String color;
    private String lexema;
    private String tipo;
    
    
    
    public Token(String lexema, int linea, int columna, String tipo, String color) {
    this.lexema = lexema;
    this.linea = linea;
    this.columna = columna;
    this.tipo = tipo;
    this.color = color;
}

    public Token(TokenType type, String value, int linea, int columna, String color) {
        this.type = type;
        this.value = value;
        this.linea = linea;
        this.columna = columna;
        this.color = color;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("Token[type=%s, value=%s, linea=%d, columna=%d, color=%s]", type, value, linea, columna, color);
    }
    
    
        public String getLexema() {
        return lexema;
    }
    
}

