/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.proyectofinallenguajes.Backend;

/**
 *
 * @author herson
 */
public enum TokenType {
    // Palabras reservadas
    PALABRA_RESERVADA,

    // Tipos de datos
    TIPO_DATO,

    // Identificadores
    IDENTIFICADOR,

    // Números
    NUMERO_ENTERO,
    NUMERO_DECIMAL,

    // Literales
    LITERAL_CADENA,
    LITERAL_FECHA,
    LITERAL_BOOLEANO,

    
    FUNCION_AGRUPACION,
    
    SIGNO,
    
    OPERADOR_ARITMETICO,
    
    OPERADOR_RELACIONAL,
    
    OPERADOR_LOGICO,
    
    
    
    // Operadores
    OPERADOR_ARITMETICO_SUMA,
    OPERADOR_ARITMETICO_RESTA,
    OPERADOR_ARITMETICO_MULTIPLICACION,
    OPERADOR_ARITMETICO_DIVISION,
    OPERADOR_RELACIONAL_IGUAL,
    OPERADOR_RELACIONAL_DIFERENTE,
    OPERADOR_RELACIONAL_MENOR,
    OPERADOR_RELACIONAL_MENOR_IGUAL,
    OPERADOR_RELACIONAL_MAYOR,
    OPERADOR_RELACIONAL_MAYOR_IGUAL,
    OPERADOR_LOGICO_AND,
    OPERADOR_LOGICO_OR,
    OPERADOR_LOGICO_NOT,

    // Signos
    SIGNO_PARENTESIS_IZQUIERDO,
    SIGNO_PARENTESIS_DERECHO,
    SIGNO_COMA,
    SIGNO_PUNTO,
    SIGNO_PUNTO_Y_COMA,

    // Comentarios
    COMENTARIO_LINEA,

    // Espacios
    ESPACIO,
    
    ERROR,

    // Fin de archivo
    YYEOF
    
 
}