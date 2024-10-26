package codigo;

// Sección de imports
import java.util.ArrayList;
import java.util.List;

%%

%{
    // Código Java

private List<Token> lista = new ArrayList<>();
private List<Token> listaErrores = new ArrayList<>();

public void addList(Token token) {
    lista.add(token);
}

public void addListaErrores(Token token) {
    listaErrores.add(token);
}

public List<Token> getLista() {
return lista;
}


public List<Token> getListaErrores() {
    return listaErrores;
}
%}

// Configuración
%public
%class Lexer
%unicode
%line
%column
%standalone

// Palabras reservadas
PALABRAS_RESERVADAS = "BETWEEN" | "USE" | "CREATE" | "DATABASE" | "TABLE" | "KEY" | "NULL" | "PRIMARY" | "UNIQUE" | "FOREIGN" | "REFERENCES" | "ALTER" | "ADD" | "COLUMN" | "TYPE" | "DROP" | "CONSTRAINT" | "IF" | "EXIST" | "CASCADE" | "ON" | "DELETE" | "SET" | "UPDATE" | "INSERT" | "INTO" | "VALUES" | "SELECT" | "FROM" | "WHERE" | "AS" | "GROUP" | "ORDER" | "BY" | "ASC" | "DESC" | "LIMIT" | "JOIN"

// Tipos de datos
TIPO_DATO = "INT" | "INTEGER" | "BIGINT" | "VARCHAR" | "DECIMAL" | "DATE" | "TEXT" | "BOOLEAN" | "SERIAL"

// Expresiones Regulares
ENTERO = [0-9]+
DECIMAL = {ENTERO}("."{ENTERO}+)
IDENTIFICADOR = [a-z]+([a-z0-9_]*)
ESPACIOS = [" "\r\t\b\n]
FECHA = "'"{ENTERO}{4}"-"{ENTERO}{2}"-"{ENTERO}{2}"'"
CADENA = "'"[^']*"'" 
BOOLEANO = "TRUE" | "FALSE"
FUNCION_AGRUPACION = "SUM" | "AVG" | "COUNT" | "MAX" | "MIN"
SIGNOS = "(" | ")" | "," | ";" | "." | "="
ARITMETICOS = "+" | "-" | "*" | "/"
RELACIONALES = "<" | ">" | "<=" | ">=" | "="
LOGICOS = "AND" | "OR" | "NOT"

COMENTARIO_LINEA = "--"[^\n]*

%%

// Reglas de Escaneo de Expresiones

{PALABRAS_RESERVADAS} {
Token token = new Token(TokenType.PALABRA_RESERVADA, yytext(), yyline, yycolumn, "naranja");
System.out.println(token);
addList(token);
}

{TIPO_DATO} { 
    Token token = new Token(TokenType.TIPO_DATO, yytext(), yyline, yycolumn, "morado");
    System.out.println(token); 
    addList(token); 
}


{IDENTIFICADOR} { 
    Token token = new Token(TokenType.IDENTIFICADOR, yytext(), yyline, yycolumn, "fucsia");
    System.out.println(token); 
    addList(token); 
}

{ENTERO} {
Token token = new Token(TokenType.NUMERO_ENTERO, yytext(), yyline, yycolumn, "azul");
System.out.println(token);
addList(token);
}

{DECIMAL} {
Token token = new Token(TokenType.NUMERO_DECIMAL, yytext(), yyline, yycolumn, "azul");
System.out.println(token);
addList(token);
}

{FECHA} {
Token token = new Token(TokenType.LITERAL_FECHA, yytext(), yyline, yycolumn, "amarillo");
System.out.println(token);
addList(token);
}

{CADENA} {
Token token = new Token(TokenType.LITERAL_CADENA, yytext(), yyline, yycolumn, "verde");
System.out.println(token);
addList(token);
}

{BOOLEANO} {
Token token = new Token(TokenType.LITERAL_BOOLEANO, yytext(), yyline, yycolumn, "azul");
System.out.println(token);
addList(token);
}

{FUNCION_AGRUPACION} {
Token token = new Token(TokenType.FUNCION_AGRUPACION, yytext(), yyline, yycolumn, "azul");
System.out.println(token);
addList(token);
}

{SIGNOS} {
Token token = new Token(TokenType.SIGNO, yytext(), yyline, yycolumn, "negro");
System.out.println(token);
addList(token);
}

{ARITMETICOS} {
Token token = new Token(TokenType.OPERADOR_ARITMETICO, yytext(), yyline, yycolumn, "negro");
System.out.println(token);
addList(token);
}

{RELACIONALES} {
Token token = new Token(TokenType.OPERADOR_RELACIONAL, yytext(), yyline, yycolumn, "negro");
System.out.println(token);
addList(token);
}

{LOGICOS} {
Token token = new Token(TokenType.OPERADOR_LOGICO, yytext(), yyline, yycolumn, "naranja");
System.out.println(token);
addList(token);
}

{ESPACIOS} {
/* Ignorar */
}

{COMENTARIO_LINEA} {
Token token = new Token(TokenType.COMENTARIO_LINEA, yytext(), yyline, yycolumn, "gris");
System.out.println(token);
addList(token);
}

. {
System.out.println("Error: " + yytext());
addListaErrores("ERROR>> Línea: " + yyline + ", Columna: " + yycolumn + ", Token -> " + yytext());
}