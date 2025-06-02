grammar BabyDuck;

// Reglas léxicas (tokens)
PROGRAM     : 'program';
VAR         : 'var';
INT         : 'int';
FLOAT       : 'float';
VOID        : 'void';
MAIN        : 'main';
END         : 'end';
IF          : 'if';
ELSE        : 'else';
WHILE       : 'while';
DO          : 'do';
PRINT       : 'print';
ASSIGN      : '=';
SEMI        : ';';
COLON       : ':';
COMMA       : ',';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACKET    : '[';
RBRACKET    : ']';
PLUS        : '+';
MINUS       : '-';
MULT        : '*';
DIV         : '/';
NEQ         : '!=';
LT          : '<';
GT          : '>';
LEQ         : '<=';
GEQ         : '>=';
CTE_INT     : [0-9]+;
CTE_FLOAT   : [0-9]+ '.' [0-9]+;
CTE_STRING  : '"' .*? '"';
ID          : [a-zA-Z_][a-zA-Z0-9_]*;
WS          : [ \t\r\n]+ -> skip;

// Reglas sintácticas (simplificadas)
programa    : PROGRAM ID SEMI vars funcs MAIN body END ;
vars        : (VAR idList COLON type SEMI)* ;
idList      : ID (COMMA ID)* ;
type        : INT | FLOAT ;
funcs       : (func)* ;

func        : VOID ID LPAREN (ID COLON type (COMMA ID COLON type)*)? RPAREN LBRACKET vars body RBRACKET SEMI ;

body        : LBRACE statement* RBRACE ;
statement   : assign
            | condition
            | cycle
            | fCall
            | print
            ;
assign      : ID ASSIGN expresion SEMI ;
condition   : IF LPAREN expresion RPAREN body (ELSE body)? ;
cycle       : WHILE LPAREN expresion RPAREN DO body SEMI ;
fCall       :ID LPAREN (expresion (COMMA expresion)*)? RPAREN SEMI ;
print       :PRINT LPAREN (expresion (COMMA expresion)*)? RPAREN SEMI ;
expresion   : exp ( (NEQ | LT | GT | LEQ | GEQ) exp )? ;
exp         : termino ( (PLUS | MINUS) termino )* ;
termino     : factor ( (MULT | DIV) factor )* ;
factor      : LPAREN expresion RPAREN
                  | ID
                  | cte
                  ;
cte         : CTE_INT | CTE_FLOAT | CTE_STRING ;


