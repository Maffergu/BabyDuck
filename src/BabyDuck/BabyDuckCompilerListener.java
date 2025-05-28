package BabyDuck;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack; // Asegúrate de que esta importación esté presente

public class BabyDuckCompilerListener extends BabyDuckBaseListener {

    private GeneradorCuadruplos generador;
    // Pila de operadores para manejo de precedencia
    private Stack<String> pilaOperadores;
    // Pila de tipos para verificar compatibilidad
    private Stack<String> pilaTipos;
    // Pila de direcciones para operandos
    private Stack<String> pilaOperandosDirecciones;
    // Pila de saltos para backpatching en estructuras de control
    private Stack<Integer> pilaSaltos;


    public BabyDuckCompilerListener(GeneradorCuadruplos generador) {
        this.generador = generador;
        this.pilaOperadores = generador.getPilaOperadores(); // Reusar las pilas del generador
        this.pilaTipos = generador.getPilaTipos();
        this.pilaOperandosDirecciones = generador.getPilaOperandosDirecciones();
        this.pilaSaltos = generador.getPilaSaltos();
    }

    // --- Reglas Semánticas (Hooks para el GeneradorCuadruplos) ---

    // General: Entrar y salir de programa
    @Override
    public void enterPrograma(BabyDuckParser.ProgramaContext ctx) {
        System.out.println("SEMANTICO: Entrando al programa.");
        generador.startProgram();
    }

    @Override
    public void exitPrograma(BabyDuckParser.ProgramaContext ctx) {
        System.out.println("SEMANTICO: Saliendo del programa.");
        generador.endProgram();
    }

    // --- Declaración de Variables (Globales y Locales) ---
    @Override
    public void enterVars(BabyDuckParser.VarsContext ctx) {
        System.out.println("SEMANTICO: Iniciando declaración de variables.");
    }

    @Override
    public void exitVars(BabyDuckParser.VarsContext ctx) {
        // La gramática vars: (VAR ID DOS_PUNTOS type PUNTO_COMA)*
        // Aquí necesitamos procesar cada declaración de variable individualmente.
        // Asume que ctx.ID() y ctx.type() devuelven listas de los tokens encontrados.
        // Podrías necesitar un parser rule específico para una sola declaración de variable si tu gramática lo soporta.
        // Por ahora, si ctx.ID() y ctx.type() son listas paralelas:
        for (int i = 0; i < ctx.ID().size(); i++) {
            String varName = ctx.ID(i).getText();
            String varType = ctx.type(i).getText();
            try {
                generador.declararVariable(varName, varType);
                System.out.println("SEMANTICO: Declarada variable '" + varName + "' de tipo '" + varType + "'");
            } catch (RuntimeException e) {
                System.err.println("Error semántico al declarar variable: " + e.getMessage());
                // Puedes decidir salir o continuar
            }
        }
        System.out.println("SEMANTICO: Terminando declaración de variables.");
    }

    // --- Funciones ---
    @Override
    public void enterMain_func(BabyDuckParser.Main_funcContext ctx) {
        System.out.println("SEMANTICO: Entrando a la función principal 'main'.");
        generador.startFunctionDeclaration("main", "void", new ArrayList<>()); // 'main' siempre es void y sin parámetros
        generador.setMainStartAddress(); // Rellena el GOTO inicial, cambia ámbito a 'main'
    }

    @Override
    public void exitMain_func(BabyDuckParser.Main_funcContext ctx) {
        System.out.println("SEMANTICO: Saliendo de la función principal 'main'.");
        generador.endFunctionDeclaration(); // Simula el fin de la función 'main'
    }

    @Override
    public void enterFuncs(BabyDuckParser.FuncsContext ctx) {
        // La gramática 'funcs' es: (VOID ID ID PAREN_IZQ expresion PAREN_DER vars PUNTO_COMA)*
        // Esto parece representar una declaración de función.
        // El primer ID sería el nombre de la función, el segundo ID es extraño en ese contexto,
        // y 'expresion' es inusual para una lista de parámetros.
        // Si tu gramática es literalmente la que me diste, esto podría ser un problema sintáctico/semántico.
        // Asumiendo que el primer ID es el nombre de la función y el tipo de retorno es VOID.
        // Y asumiendo que no hay parámetros definidos explícitamente en la declaración de función, o que la 'expresion' es un placeholder.
        if (ctx.VOID() != null && ctx.ID().size() >= 1) { // Asegura que es una declaración de función
            String funcName = ctx.ID(0).getText(); // El primer ID es el nombre de la función
            String returnType = "void"; // Siempre VOID en esta gramática
            List<String> paramTypes = new ArrayList<>(); // Vacío por ahora, ajustar si la gramática tiene parámetros

            System.out.println("SEMANTICO: Iniciando declaración de función: " + funcName);
            generador.startFunctionDeclaration(funcName, returnType, paramTypes);
        }
    }

    @Override
    public void exitFuncs(BabyDuckParser.FuncsContext ctx) {
        // Este `exit` se llama después de que todo el bloque `funcs` haya sido procesado.
        // La finalización de cada función individual debería manejarse en el `exit` de la regla de la función.
        // Si tu gramática `funcs` encapsula varias declaraciones, esta `exit` no es el lugar para `endFunctionDeclaration`.
        // Necesitas un `exit` específico para una única declaración de función.
        // Ya que tu gramática de `funcs` es un Kleene Star `()*`, esto se llama al salir del conjunto completo.
        // La lógica de `endFunctionDeclaration` se ejecutará al final del `body` o al salir de la regla de la función.
        // Por ahora, la lógica de `endFunctionDeclaration` está asociada a `exitBody` o al final del ámbito.
        // El generador ya tiene `endFunctionDeclaration` en `exitMain_func`.
        // Para funciones definidas por el usuario, necesitarías un `exit` en la regla específica de función,
        // no en el contenedor `funcs`.
        System.out.println("SEMANTICO: Terminando el bloque de funciones.");
    }


    // --- Expresiones y Operadores ---
    @Override
    public void enterFactor(BabyDuckParser.FactorContext ctx) {
        if (ctx.ID() != null) {
            // Es un ID
            generador.procesarOperando(ctx.ID().getText(), false, null); // null para tipo, ya se determinará
        } else if (ctx.CTE_INT() != null) {
            // Es una constante entera
            generador.procesarOperando(ctx.CTE_INT().getText(), true, "INT");
        } else if (ctx.CTE_FLOAT() != null) {
            // Es una constante flotante
            generador.procesarOperando(ctx.CTE_FLOAT().getText(), true, "FLOAT");
        } else if (ctx.CTE_STRING() != null) {
            // Es una constante de cadena
            generador.procesarOperando(ctx.CTE_STRING().getText(), true, "STRING");
        }
        // Si es PAREN_IZQ expresion PAREN_DER, se manejará cuando se evalúe la expresión interna
    }

    // Operadores aritméticos
    @Override public void exitExp(BabyDuckParser.ExpContext ctx) { generador.generarCuadruploExpresion("+"); generador.generarCuadruploExpresion("-"); }
    @Override public void exitTermino(BabyDuckParser.TerminoContext ctx) { generador.generarCuadruploExpresion("*"); generador.generarCuadruploExpresion("/"); }

    // Operadores lógicos y relacionales (se empujan a la pila al encontrarlos)
    @Override public void enterMayor(BabyDuckParser.MayorContext ctx) { generador.pushOperador(">"); }
    @Override public void enterMenor(BabyDuckParser.MenorContext ctx) { generador.pushOperador("<"); }
    @Override public void enterNot_igual(BabyDuckParser.Not_igualContext ctx) { generador.pushOperador("!="); }


    // --- Asignaciones ---
    @Override
    public void exitAssign(BabyDuckParser.AssignContext ctx) {
        String varName = ctx.ID().getText();
        System.out.println("SEMANTICO: Generando asignación para '" + varName + "'");
        generador.generarCuadruploAsignacion(varName); // El operando de la expresión ya debe estar en pila
    }

    // --- Print ---
    @Override
    public void exitPrint_stmt(BabyDuckParser.Print_stmtContext ctx) {
        System.out.println("SEMANTICO: Generando cuádruplo PRINT.");
        // La gramática de print_stmt es PRINT PAREN_IZQ expresion PAREN_DER
        // o PRINT PAREN_IZQ CTE_STRING PAREN_DER si ajustamos la gramática.
        // Si el `factor` de la `expresion` ya empujó la constante o ID,
        // el último elemento en la pila de operandos es lo que se debe imprimir.
        String operandoDir = pilaOperandosDirecciones.pop(); // Asegúrate de usar la pila del generador
        generador.addCustomQuadruple("PRINT", operandoDir, null, null);
    }

    // --- Llamadas a Función ---
    @Override
    public void enterFCall(BabyDuckParser.FCallContext ctx) {
        String funcName = ctx.ID(0).getText(); // Nombre de la función
        System.out.println("SEMANTICO: Iniciando llamada a función: " + funcName);
        generador.startFunctionCall(funcName);
    }

    @Override
    public void exitFCall(BabyDuckParser.FCallContext ctx) {
        String funcName = ctx.ID(0).getText();
        System.out.println("SEMANTICO: Terminando llamada a función: " + funcName);
        // Aquí asumirías que los argumentos ya fueron procesados y están en la pila de operandos
        // o en una pila auxiliar de parámetros.
        // generador.processArgumentsForCall(); // Este método procesaría los PARAMS
        generador.endFunctionCall(funcName);
    }

    // --- Estructuras de Control (IF-ELSE, WHILE) ---

    // IF
    @Override
    public void exitExpresion(BabyDuckParser.ExpresionContext ctx) {
        // En este punto, la expresión completa ha sido evaluada.
        // Si hay un operador relacional pendiente, se genera el cuádruplo.
        // Para IF/WHILE, la expresión de la condición es crucial.
        if (!pilaOperadores.isEmpty() && (pilaOperadores.peek().equals(">") ||
                pilaOperadores.peek().equals("<") ||
                pilaOperadores.peek().equals("!="))) {
            generador.generarCuadruploExpresion(pilaOperadores.pop());
        }
    }

    @Override
    public void enterCondition(BabyDuckParser.ConditionContext ctx) {
        System.out.println("SEMANTICO: Iniciando bloque IF.");
        // Después de que la expresión de la condición sea evaluada (en exitExpresion),
        // su resultado booleano estará en la pila de operandos.
        // Inmediatamente después de `PAREN_DER` en `if (expresion)`, se genera el GOTOF.
        // Si la gramática es `condition: IF PAREN_IZQ expresion PAREN_DER body (ELSE body)?`
        // Entonces, el `enterBody` del IF, o un método intermedio si necesitas un punto de inserción.
        // En `GeneradorCuadruplos` tenemos `inicioIf` que se llamaría después de `expresion`
        // y antes de `body`.
        // Para implementar esto, necesitarías acciones semánticas más finas o un Visitor en lugar de Listener.
        // Pero intentaremos simularlo con la estructura actual del Listener.
        // Para un listener, el `exitExpresion` es clave, y luego se necesita el `if` en el árbol.
        // Aquí, simplemente llamamos al método.
        generador.inicioIf(); // Esto usará el último operando de la pila como la condición
    }

    @Override
    public void enterElse(BabyDuckParser.ElseContext ctx) {
        System.out.println("SEMANTICO: Entrando a bloque ELSE.");
        generador.inicioElse(); // Prepara el salto GOTO y rellena el GOTOF del IF
    }

    @Override
    public void exitCondition(BabyDuckParser.ConditionContext ctx) {
        System.out.println("SEMANTICO: Terminando bloque IF/ELSE.");
        generador.finIf(); // Rellena el GOTO del ELSE (si existe) o el GOTOF del IF (si no hay ELSE)
    }

    // WHILE
    @Override
    public void enterWhile(BabyDuckParser.WhileContext ctx) {
        System.out.println("SEMANTICO: Iniciando bloque WHILE.");
        generador.inicioWhile(); // Marca el cuádruplo de retorno
    }

    @Override
    public void exitWhile(BabyDuckParser.WhileContext ctx) {
        System.out.println("SEMANTICO: Terminando bloque WHILE.");
        generador.finWhile(); // Genera GOTO al inicio del while y rellena GOTOF
    }

    // DO-WHILE
    @Override
    public void enterDo(BabyDuckParser.DoContext ctx) {
        System.out.println("SEMANTICO: Iniciando bloque DO-WHILE.");
        generador.inicioDoWhile(); // Marca el cuádruplo de inicio del DO
    }

    @Override
    public void exitDo(BabyDuckParser.DoContext ctx) {
        System.out.println("SEMANTICO: Terminando bloque DO-WHILE.");
        // La condición `while (expresion)` se procesa, y luego se genera el GOTOF.
        // En `exitWhile` para `DO body WHILE (expresion) PUNTO_COMA` se procesaría.
        // Tu gramática `cycle` incluye `DO body WHILE PAREN_IZQ expresion PAREN_DER PUNTO_COMA`.
        // Entonces, el `exitCycle` de esa regla específica debería llamar a `finDoWhile`.
        // Aquí no hay un `exitDo` separado. Podrías necesitar un método `exitCycle` específico
        // o manejarlo en `finWhile` si el contexto lo permite.
        // Para simplificar, la lógica de `finWhile` ya manejará el `GOTOF` y el `GOTO`
        // Asumiendo que `expresion` en DO-WHILE ya empujó el resultado a la pila.
        // La lógica de `finWhile` debería cerrar el ciclo.
        // GeneradorCuadruplos.finWhile() ya maneja el backpatching de un loop.
        // Si el `cycle` es `DO...WHILE`, entonces el `exitCycle` necesita la lógica de `finDoWhile`.
        // Por ahora, asumiremos que `finWhile` en `GeneradorCuadruplos` se encarga de todo.
    }


    // --- Manejo de terminales para empujar operadores ---
    @Override
    public void visitTerminal(TerminalNode node) {
        String symbol = node.getText();
        switch (symbol) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "=": // Para asignación
            case ">":
            case "<":
            case "!=":
                generador.pushOperador(symbol);
                break;
        }
    }


    // Otros métodos (p. ej., visitErrorNode) si necesitas un manejo específico de errores del árbol
    @Override
    public void visitErrorNode(ErrorNode node) {
        System.err.println("ERROR: Nodo de error en el árbol de análisis: " + node.getText());
        // Puedes agregar lógica para manejar errores de sintaxis
        // System.exit(1); // O salir para errores críticos
    }
}