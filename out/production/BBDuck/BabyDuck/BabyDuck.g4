grammar BabyDuck;

// Palabras reservadas
PROGRAM: 'program';
MAIN: 'main';
END: 'end';
VAR: 'var';
PRINT: 'print';
INT: 'int';
FLOAT: 'float';
VOID: 'void';
IF: 'if';
ELSE: 'else';
WHILE: 'while';
DO: 'do';

// Identificadores
ID: [a-zA-Z_] [a-zA-Z0-9_]*;

// Constantes
CTE_INT: [0-9]+;
CTE_FLOAT: [0-9]+ '.' [0-9]+;
CTE_STRING: '"' (~'"')* '"';

// Operadores
SUMA: '+';
RESTA: '-';
MULT: '*';
DIV: '/';
MAYOR: '>';
MENOR: '<';
NOT_IGUAL: '!=';
IGUAL: '=';

// Puntuación
PUNTO_COMA: ';';
COMA: ',';
PAREN_IZQ: '(';
PAREN_DER: ')';
LLAVE_IZQ: '{';
LLAVE_DER: '}';
DOS_PUNTOS: ':';

// Whitespace (se ignora)
WS: [ \t\r\n]+ -> skip;

programa: PROGRAM ID PUNTO_COMA funcs main_func END
         ;

funcs: (VOID ID ID PAREN_IZQ expresion PAREN_DER vars PUNTO_COMA)*
     ;

main_func: MAIN body
           ;

vars: (VAR ID DOS_PUNTOS type PUNTO_COMA)*
    ;

type: INT
    | FLOAT
    ;

body: LLAVE_IZQ statement* LLAVE_DER
    ;

statement: assign PUNTO_COMA
         | condition
         | cycle
         | fCall PUNTO_COMA
         | print_stmt PUNTO_COMA
         ;

assign: ID IGUAL expresion
      ;

expresion: exp (MAYOR exp | MENOR exp | NOT_IGUAL exp)?
         ;

exp: termino (SUMA termino | RESTA termino)*
   ;

termino: factor (MULT factor | DIV factor)*
       ;

factor: PAREN_IZQ expresion PAREN_DER
      | ID
      | CTE_INT
      | CTE_FLOAT
      | CTE_STRING
      ;

condition: IF PAREN_IZQ expresion PAREN_DER body (ELSE body)?
         ;

cycle: WHILE PAREN_IZQ expresion PAREN_DER body
     | DO body WHILE PAREN_IZQ expresion PAREN_DER PUNTO_COMA
     ;

fCall: ID ID PAREN_IZQ expresion PAREN_DER
     ;

print_stmt: PRINT PAREN_IZQ expresion PAREN_DER
          ;