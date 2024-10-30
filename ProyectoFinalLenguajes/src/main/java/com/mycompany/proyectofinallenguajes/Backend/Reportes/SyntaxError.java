/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend.Reportes;

import com.mycompany.proyectofinallenguajes.Backend.Token;

/**
 *
 * @author herson
 */
public class SyntaxError {
    private Token token;
    private String descripcion;

    public SyntaxError(Token token, String descripcion) {
        this.token = token;
        this.descripcion = descripcion;
    }

    public Token getToken() {
        return token;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

