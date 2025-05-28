package BabyDuck;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList; // Cambiado de Queue a List (ArrayList)
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

// Clase GeneradorCuadruplos: Responsable de la generación de código intermedio (cuádruplos)
// y la gestión de direcciones virtuales.
public class GeneradorCuadruplos {

    // Pilas para operadores, operandos (ahora direcciones virtuales) y tipos
    private Stack<String> pilaOperadores;
    private Stack<Integer> pilaOperandosDirecciones; // Almacena direcciones virtuales (Integer)
    private Stack<String> pilaTipos;
    private List<EstructurasDeDatos.Cuadruplo> filaCuadruplos; // Cambiado a ArrayList para backpatching

    // Componentes para la gestión de memoria y tabla de símbolos
    private MemoryManager memoryManager;
    private FunctionDirectory functionDirectory;
    private VariableTable globalTable;       // Tabla de variables globales
    private Stack<VariableTable> scopeStack; // Pila para gestionar el ámbito de variables (global/local)
    private Stack<String> currentFunctionDeclarationStack; // Pila para saber qué función se está declarando

    // --- Atributos para el Control de Flujo y Funciones ---
    private int quadCount; // Contador para el número de cuádruplo actual (dirección de instrucción)
    private Stack<Integer> jumpStack; // Pila para almacenar los números de cuádruplo que necesitan ser rellenados (GOTOF, GOTO)
    private Stack<String> callStack; // Pila para rastrear el nombre de la función actual durante la invocación (para PARAMS)
    private Stack<List<FunctionDirectory.ParameterInfo>> paramCheckStack; // Para verificar parámetros durante la llamada

    // Cubo semántico (simplificado para este ejemplo, debería ser una clase separada)
    private static final Map<String, Map<String, Map<String, String>>> cuboSemantico = new HashMap<>();

    static {
        // Definición de reglas de tipos para operadores aritméticos y relacionales
        Map<String, Map<String, String>> intFloatOps = new HashMap<>();
        intFloatOps.put("INT", new HashMap<String, String>() {{
            put("INT", "INT");
            put("FLOAT", "FLOAT");
        }});
        intFloatOps.put("FLOAT", new HashMap<String, String>() {{
            put("INT", "FLOAT");
            put("FLOAT", "FLOAT");
        }});

        // Reglas para operadores relacionales (siempre devuelven BOOLEAN)
        Map<String, Map<String, String>> relationalOps = new HashMap<>();
        relationalOps.put("INT", new HashMap<String, String>() {{
            put("INT", "BOOLEAN");
            put("FLOAT", "BOOLEAN");
        }});
        relationalOps.put("FLOAT", new HashMap<String, String>() {{
            put("INT", "BOOLEAN");
            put("FLOAT", "BOOLEAN");
        }});
        relationalOps.put("BOOLEAN", new HashMap<String, String>() {{
            put("BOOLEAN", "BOOLEAN");
        }});
        relationalOps.put("STRING", new HashMap<String, String>() {{
            put("STRING", "BOOLEAN");
        }});


        // Definición de reglas de tipos para operadores lógicos
        Map<String, Map<String, String>> boolOps = new HashMap<>();
        boolOps.put("BOOLEAN", new HashMap<String, String>() {{
            put("BOOLEAN", "BOOLEAN");
        }});

        // Asignación de operadores a sus reglas de tipo
        cuboSemantico.put("+", intFloatOps);
        cuboSemantico.put("-", intFloatOps);
        cuboSemantico.put("*", intFloatOps);
        cuboSemantico.put("/", intFloatOps);
        cuboSemantico.put(">", relationalOps);
        cuboSemantico.put("<", relationalOps);
        cuboSemantico.put("==", relationalOps);
        cuboSemantico.put("!=", relationalOps);
        cuboSemantico.put("&", boolOps); // AND
        cuboSemantico.put("|", boolOps); // OR

        // Regla para asignación: el tipo del lado derecho debe ser compatible con el izquierdo
        Map<String, Map<String, String>> assignOps = new HashMap<>();
        assignOps.put("INT", new HashMap<String, String>() {{ put("INT", "INT"); }});
        assignOps.put("FLOAT", new HashMap<String, String>() {{ put("FLOAT", "FLOAT"); put("INT", "FLOAT"); }}); // INT se puede asignar a FLOAT
        assignOps.put("BOOLEAN", new HashMap<String, String>() {{ put("BOOLEAN", "BOOLEAN"); }});
        assignOps.put("STRING", new HashMap<String, String>() {{ put("STRING", "STRING"); }});
        cuboSemantico.put("=", assignOps);
    }

    /**
     * Constructor de la clase GeneradorCuadruplos.
     * Inicializa todas las pilas, la fila de cuádruplos y las instancias
     * de los gestores de memoria y tablas de símbolos.
     */
    public GeneradorCuadruplos() {
        this.pilaOperadores = new Stack<>();
        this.pilaOperandosDirecciones = new Stack<>();
        this.pilaTipos = new Stack<>();
        this.filaCuadruplos = new ArrayList<>(); // Inicializado como ArrayList

        this.memoryManager = new MemoryManager();
        this.functionDirectory = new FunctionDirectory();
        this.globalTable = new VariableTable();
        this.scopeStack = new Stack<>();
        this.scopeStack.push(globalTable); // El ámbito inicial es el global
        this.currentFunctionDeclarationStack = new Stack<>(); // Pila para el nombre de la función que se declara

        this.quadCount = 0;
        this.jumpStack = new Stack<>();
        this.callStack = new Stack<>(); // Pila para llamadas a funciones (para el ERA/PARAM/GOSUB)
        this.paramCheckStack = new Stack<>();

        // *** IMPORTANTE: Configuración inicial del salto sobre las funciones al inicio del programa ***
        // Este GOTO saltará a la primera instrucción ejecutable del programa (usualmente 'main').
        // Su destino se rellenará en 'setMainStartAddress()'.
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTO", null, null, null));
        jumpStack.push(quadCount); // Guarda el quadNum de este GOTO para rellenar después
        quadCount++; // Incrementa para el siguiente cuádruplo
        System.out.println("DEBUG: GeneradorCuadruplos inicializado. GOTO inicial generado (quad " + (quadCount - 1) + ").");
    }

    /**
     * Helper para rellenar el campo 'resultado' de un cuádruplo previamente generado.
     * @param quadIndex El índice (número de cuádruplo) del cuádruplo a rellenar.
     * @param targetAddress La dirección de destino (como String) a rellenar.
     */
    private void fillQuadruple(int quadIndex, String targetAddress) {
        if (quadIndex >= 0 && quadIndex < filaCuadruplos.size()) {
            filaCuadruplos.get(quadIndex).setResultado(targetAddress);
            System.out.println("GC-DEBUG: Backpatched quad " + quadIndex + " con destino " + targetAddress);
        } else {
            System.err.println("GC-ERROR: Intento de backpatching en índice de cuádruplo inválido: " + quadIndex + " (tamaño actual de cuádruplos: " + filaCuadruplos.size() + ").");
            throw new RuntimeException("Error interno de backpatching.");
        }
    }

    /**
     * Define el punto de entrada principal del programa ('main').
     * Rellena el GOTO inicial que salta sobre las definiciones de funciones.
     * Este método se llamaría al inicio de la función principal 'main'.
     */
    public void setMainStartAddress() {
        if (!jumpStack.isEmpty()) {
            Integer initialGotoQuadNum = jumpStack.pop();
            fillQuadruple(initialGotoQuadNum, String.valueOf(quadCount));
            System.out.println("DEBUG: Backpatching: Rellenado GOTO inicial en quad " + initialGotoQuadNum + " a " + quadCount);
        }

        // Registra 'main' en el directorio de funciones con su dirección de inicio real
        // Asumimos que 'main' no tiene parámetros y retorna VOID
        functionDirectory.addFunction("main", "VOID", quadCount);

        // Establece el ámbito local a la tabla de variables de 'main'
        VariableTable mainLocalTable = new VariableTable();
        functionDirectory.getFunction("main").setLocalVariables(mainLocalTable);
        scopeStack.push(mainLocalTable);

        // La pila de declaración de funciones puede tener 'main' para consistency
        currentFunctionDeclarationStack.push("main");

        memoryManager.resetLocalAndTempCounters(); // Reinicia contadores para 'main'
        System.out.println("DEBUG: Se ha establecido el punto de entrada de 'main' en quad " + quadCount);
    }

    /**
     * Obtiene la fila de cuádruplos generados.
     * @return La List de objetos Cuadruplo.
     */
    public List<EstructurasDeDatos.Cuadruplo> getCuadruplos() {
        return filaCuadruplos;
    }

    /**
     * Obtiene el directorio de funciones.
     * @return La instancia de FunctionDirectory.
     */
    public FunctionDirectory getFunctionDirectory() {
        return functionDirectory;
    }

    /**
     * Obtiene la tabla de variables globales.
     * @return La instancia de VariableTable para el ámbito global.
     */
    public VariableTable getGlobalTable() {
        return globalTable;
    }

    /**
     * Obtiene la pila de direcciones de operandos.
     * Este método es principalmente para propósitos de depuración y prueba.
     * @return La Stack de direcciones virtuales de operandos.
     */
    public Stack<Integer> getPilaOperandosDirecciones() {
        return pilaOperandosDirecciones;
    }

    /**
     * Simula la declaración de una variable y asigna su dirección virtual.
     * Este método sería invocado por el analizador semántico (Visitor/Listener)
     * al encontrar una declaración de variable en el código fuente.
     * @param name El nombre de la variable.
     * @param type El tipo de la variable (ej. "INT", "FLOAT", "BOOLEAN", "STRING").
     */
    public void declararVariable(String name, String type) {
        // El ámbito se determina por el tamaño de la scopeStack
        String scope = (scopeStack.peek() == globalTable) ? "GLOBAL" : "LOCAL";
        VariableTable currentTable = scopeStack.peek(); // La tabla actual es el tope de la pila

        // Antes de asignar, verificar si ya existe en el ámbito actual
        if (currentTable.getVariable(name) != null) {
            throw new RuntimeException("Error semántico: Variable '" + name + "' ya declarada en el ámbito actual.");
        }

        // Permite shadowing: una variable local puede tener el mismo nombre que una global.
        // Si BabyDuck no permite shadowing, se debería lanzar un error aquí si globalTable.getVariable(name) != null.

        int address = memoryManager.assignVariableAddress(type, scope);
        currentTable.addVariable(name, type, address);
        System.out.println("DEBUG: Variable '" + name + "' (" + type + ", " + scope + ") asignada a dirección: " + address + " en ámbito: " + currentTable);
    }

    /**
     * Simula el procesamiento de un operando (variable o constante).
     * Busca su dirección virtual o le asigna una si es una constante nueva,
     * y luego empuja la dirección y el tipo a las pilas correspondientes.
     * Este método sería invocado por el analizador semántico al encontrar un operando.
     * @param value El nombre de la variable o el valor literal de la constante (como String).
     * @param isConstant Indica si el 'value' es una constante literal (true) o un identificador de variable (false).
     * @param type Si es una constante, su tipo (ej. "INT", "FLOAT", "STRING", "BOOLEAN"). Si es variable, puede ser null.
     */
    public void procesarOperando(String value, boolean isConstant, String type) {
        if (isConstant) {
            int address = memoryManager.assignConstantAddress(value, type);
            pilaOperandosDirecciones.push(address);
            pilaTipos.push(type);
            System.out.println("DEBUG: Constante '" + value + "' (" + type + ") asignada a dirección: " + address);
        } else { // Es un identificador de variable
            VariableTable.VariableInfo varInfo = scopeStack.peek().getVariable(value); // Busca en el ámbito actual
            if (varInfo == null) {
                varInfo = globalTable.getVariable(value); // Si no está local, busca en la tabla global
            }
            if (varInfo == null) {
                throw new RuntimeException("Error semántico: Variable '" + value + "' no declarada en el ámbito actual ni global.");
            }
            pilaOperandosDirecciones.push(varInfo.getAddress());
            pilaTipos.push(varInfo.getType());
            System.out.println("DEBUG: Variable '" + value + "' (" + varInfo.getType() + ") en dirección: " + varInfo.getAddress());
        }
    }

    /**
     * Determina el tipo resultante de una operación binaria utilizando el cubo semántico.
     * @param tipoIzquierdo Tipo del operando izquierdo.
     * @param tipoDerecho Tipo del operando derecho.
     * @param operador El operador de la operación.
     * @return El tipo de dato resultante de la operación, o null si los tipos son incompatibles.
     */
    private String getTipoResultante(String tipoIzquierdo, String tipoDerecho, String operador) {
        if (cuboSemantico.containsKey(operador) && cuboSemantico.get(operador).containsKey(tipoIzquierdo)
                && cuboSemantico.get(operador).get(tipoIzquierdo).containsKey(tipoDerecho)) {
            return cuboSemantico.get(operador).get(tipoIzquierdo).get(tipoDerecho);
        }
        return null; // Tipos incompatibles o operador no definido para esos tipos
    }

    /**
     * Genera un cuádruplo para una operación binaria (aritmética o relacional).
     * @param operador El operador de la expresión (ej. "+", "*", ">").
     * @throws RuntimeException Si hay un error de tipos o faltan operandos.
     */
    public void generarCuadruploExpresion(String operador) {
        if (pilaOperandosDirecciones.size() < 2 || pilaTipos.size() < 2) {
            throw new RuntimeException("Error interno: Insuficientes operandos o tipos en las pilas para la operación '" + operador + "'.");
        }
        String tipoDerecho = pilaTipos.pop();
        String tipoIzquierdo = pilaTipos.pop();
        Integer dirDerecha = pilaOperandosDirecciones.pop();
        Integer dirIzquierda = pilaOperandosDirecciones.pop();

        String tipoResultante = getTipoResultante(tipoIzquierdo, tipoDerecho, operador);
        if (tipoResultante == null) {
            throw new RuntimeException("Error de tipos: Operación no válida '" + operador + "' con tipos " + tipoIzquierdo + " y " + tipoDerecho);
        }

        int dirResultado = memoryManager.assignTempAddress(tipoResultante);
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo(operador, String.valueOf(dirIzquierda), String.valueOf(dirDerecha), String.valueOf(dirResultado)));
        pilaOperandosDirecciones.push(dirResultado);
        pilaTipos.push(tipoResultante);
        quadCount++;
        System.out.println("DEBUG: Cuádruplo generado: " + filaCuadruplos.get(filaCuadruplos.size()-1));
    }

    /**
     * Genera un cuádruplo para una asignación.
     * @param variableName El nombre de la variable a la que se asigna el valor.
     * @throws RuntimeException Si la variable de asignación no está declarada o hay incompatibilidad de tipos.
     */
    public void generarCuadruploAsignacion(String variableName) {
        if (pilaOperandosDirecciones.isEmpty() || pilaTipos.isEmpty()) {
            throw new RuntimeException("Error interno: No hay valor en la pila para asignación a '" + variableName + "'.");
        }
        String tipoValor = pilaTipos.pop();
        Integer dirValor = pilaOperandosDirecciones.pop();

        VariableTable.VariableInfo varInfo = scopeStack.peek().getVariable(variableName); // Busca en el ámbito actual
        if (varInfo == null) {
            varInfo = globalTable.getVariable(variableName); // Si no está local, busca en la tabla global
        }
        if (varInfo == null) {
            throw new RuntimeException("Error semántico: Variable de asignación '" + variableName + "' no declarada.");
        }

        // Realiza la verificación de compatibilidad de tipos para la asignación
        String tipoDestino = varInfo.getType();
        String tipoAsignacionResultante = getTipoResultante(tipoDestino, tipoValor, "=");

        if (tipoAsignacionResultante == null || !tipoAsignacionResultante.equals(tipoDestino)) {
            throw new RuntimeException("Error de tipos: Incompatibilidad de tipos en asignación a '" + variableName + "'. Esperado: " + varInfo.getType() + ", Obtenido: " + tipoValor);
        }

        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("=", String.valueOf(dirValor), null, String.valueOf(varInfo.getAddress())));
        quadCount++;
        System.out.println("DEBUG: Cuádruplo generado: " + filaCuadruplos.get(filaCuadruplos.size()-1));
    }

    // --- Métodos para manipular la pila de operadores ---
    public void pushOperador(String operador) { pilaOperadores.push(operador); }
    public String popOperador() { return pilaOperadores.pop(); }
    public String peekOperador() { return pilaOperadores.peek(); }
    public boolean pilaOperadoresEstaVacia() { return pilaOperadores.isEmpty(); }

    // --- Métodos para manipular la pila de tipos ---
    public void pushTipo(String tipo) { pilaTipos.push(tipo); }
    public String popTipo() { return pilaTipos.pop(); }
    public String peekTipo() { return pilaTipos.peek(); }
    public boolean pilaTiposEstaVacia() { return pilaTipos.isEmpty(); }

    /**
     * Imprime todos los cuádruplos generados en la fila.
     */
    public void imprimirCuadruplos() {
        System.out.println("\n--- Cuádruplos generados (con direcciones virtuales) ---");
        if (filaCuadruplos.isEmpty()) {
            System.out.println("No hay cuádruplos para desplegar.");
            return;
        }
        for (int i = 0; i < filaCuadruplos.size(); i++) {
            System.out.println( (i) + ": " + filaCuadruplos.get(i).toString()); // Los cuádruplos son 0-indexed
        }
        System.out.println("--------------------------------------------------");
    }

    // ======================================================================
    // === MÉTODOS PARA EL CONTROL DE FLUJO (IF-ELSE, WHILE, DO-WHILE) ===
    // ======================================================================

    /**
     * Punto neurálgico para el inicio de un IF.
     * Genera un GOTOF con destino pendiente.
     */
    public void inicioIf() {
        if (pilaTipos.isEmpty() || pilaOperandosDirecciones.isEmpty()) {
            throw new RuntimeException("Error interno: La pila de tipos/operandos está vacía para la condición del IF.");
        }
        String tipoCondicion = pilaTipos.pop();
        Integer dirCondicion = pilaOperandosDirecciones.pop();

        if (!"BOOLEAN".equalsIgnoreCase(tipoCondicion)) {
            throw new RuntimeException("Error de tipos: La condición del IF debe ser de tipo BOOLEAN.");
        }

        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTOF", String.valueOf(dirCondicion), null, null));
        jumpStack.push(quadCount); // Guarda el quadNum de este GOTOF
        quadCount++;
        System.out.println("DEBUG: GOTOF generado (pendiente): " + filaCuadruplos.get(filaCuadruplos.size()-1));
    }

    /**
     * Punto neurálgico para el ELSE de un IF.
     * Genera un GOTO con destino pendiente y rellena el GOTOF del IF anterior.
     */
    public void inicioElse() {
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTO", null, null, null));
        Integer gotoQuadNum = quadCount; // Guarda el quadNum de este nuevo GOTO
        quadCount++;

        Integer gotofQuadNum = jumpStack.pop(); // QuadNum del GOTOF del IF que se va a rellenar
        fillQuadruple(gotofQuadNum, String.valueOf(quadCount)); // Rellenar el GOTOF del IF
        jumpStack.push(gotoQuadNum); // Empuja el GOTO recién creado para rellenarlo después
        System.out.println("DEBUG: GOTO generado (pendiente): " + filaCuadruplos.get(filaCuadruplos.size()-1));
    }

    /**
     * Punto neurálgico para el fin de un IF-ELSE o el fin de un IF simple.
     * Rellena el último salto pendiente.
     */
    public void finIf() {
        if (jumpStack.isEmpty()) {
            System.err.println("GC-WARNING: jumpStack vacía al intentar finIf. Posible error lógico o IF anidado incorrecto.");
            return;
        }
        Integer saltoPendienteQuadNum = jumpStack.pop(); // QuadNum del GOTO del ELSE o del GOTOF del IF simple
        fillQuadruple(saltoPendienteQuadNum, String.valueOf(quadCount)); // Rellenar el salto pendiente
    }

    /**
     * Punto neurálgico para el inicio de un WHILE (antes de la condición).
     * Guarda el quadCount de inicio del ciclo (donde se vuelve a la condición).
     */
    public void inicioWhile() {
        jumpStack.push(quadCount); // Guarda el quadCount que apunta al inicio de la condición del while
        System.out.println("DEBUG: Inicio WHILE guardado en jumpStack: " + jumpStack.peek());
    }

    /**
     * Punto neurálgico para la condición de un WHILE.
     * Genera un GOTOF con destino pendiente.
     */
    public void condicionWhile() {
        if (pilaTipos.isEmpty() || pilaOperandosDirecciones.isEmpty()) {
            throw new RuntimeException("Error interno: La pila de tipos/operandos está vacía para la condición del WHILE.");
        }
        String tipoCondicion = pilaTipos.pop();
        Integer dirCondicion = pilaOperandosDirecciones.pop();

        if (!"BOOLEAN".equalsIgnoreCase(tipoCondicion)) {
            throw new RuntimeException("Error de tipos: La condición del WHILE debe ser de tipo BOOLEAN.");
        }

        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTOF", String.valueOf(dirCondicion), null, null));
        jumpStack.push(quadCount); // Guarda el quadCount de este GOTOF
        quadCount++;
        System.out.println("DEBUG: GOTOF WHILE generado (pendiente): " + filaCuadruplos.get(filaCuadruplos.size()-1));
    }

    /**
     * Punto neurálgico para el fin de un WHILE.
     * Rellena el GOTOF de la condición y genera un GOTO para regresar al inicio.
     */
    public void finWhile() {
        if (jumpStack.size() < 2) {
            System.err.println("GC-WARNING: jumpStack insuficiente al intentar finWhile. Posible error lógico o WHILE anidado incorrecto.");
            return;
        }
        Integer gotofWhileQuadNum = jumpStack.pop(); // QuadNum del GOTOF de la condición
        Integer inicioWhileQuadNum = jumpStack.pop(); // QuadNum del inicio del WHILE (donde se guardó el GOTO a la condición)

        // Rellenar el GOTOF para que salte *después* del ciclo
        fillQuadruple(gotofWhileQuadNum, String.valueOf(quadCount));

        // Generar un GOTO para regresar al inicio del ciclo
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTO", null, null, String.valueOf(inicioWhileQuadNum)));
        quadCount++;
        System.out.println("DEBUG: GOTO para regresar al WHILE: " + filaCuadruplos.get(filaCuadruplos.size()-1));
    }

    /**
     * Punto neurálgico para el inicio de un DO-WHILE (al principio del cuerpo 'DO').
     * Guarda el quadCount que marca el inicio del cuerpo del bucle.
     */
    public void inicioDoWhile() {
        jumpStack.push(quadCount); // Guarda el quadCount del inicio del cuerpo del DO
        System.out.println("DEBUG: Inicio DO-WHILE (cuerpo) guardado en jumpStack: " + jumpStack.peek());
    }

    /**
     * Punto neurálgico para la condición del DO-WHILE.
     * Genera un GOTOF con destino pendiente y luego el GOTO para regresar al inicio del cuerpo.
     */
    public void condicionDoWhile() {
        if (pilaTipos.isEmpty() || pilaOperandosDirecciones.isEmpty()) {
            throw new RuntimeException("Error interno: La pila de tipos/operandos está vacía para la condición del DO-WHILE.");
        }
        String tipoCondicion = pilaTipos.pop();
        Integer dirCondicion = pilaOperandosDirecciones.pop();

        if (!"BOOLEAN".equalsIgnoreCase(tipoCondicion)) {
            throw new RuntimeException("Error de tipos: La condición del DO-WHILE debe ser de tipo BOOLEAN.");
        }

        // 1. Genera el GOTOF de la condición (salta si la condición es falsa)
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTOF", String.valueOf(dirCondicion), null, null));
        jumpStack.push(quadCount); // Guarda el quadNum de este GOTOF
        quadCount++;
        System.out.println("DEBUG: GOTOF DO-WHILE generado (pendiente): " + filaCuadruplos.get(filaCuadruplos.size()-1));

        // 2. Genera el GOTO para regresar al inicio del cuerpo DO
        Integer inicioDoBodyQuadNum = jumpStack.pop(); // Obtiene el quadCount del inicio del cuerpo DO
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTO", null, null, String.valueOf(inicioDoBodyQuadNum)));
        quadCount++;
        System.out.println("DEBUG: GOTO para regresar al cuerpo DO-WHILE: " + filaCuadruplos.get(filaCuadruplos.size()-1));
    }

    /**
     * Punto neurálgico para el fin de un DO-WHILE.
     * Rellena el GOTOF de la condición.
     * Este método se llama después de la condición (y el GOTO) en el DO-WHILE.
     */
    public void finDoWhile() {
        if (jumpStack.isEmpty()) {
            System.err.println("GC-WARNING: jumpStack vacía al intentar finDoWhile. Posible error lógico o DO-WHILE anidado incorrecto.");
            return;
        }
        Integer gotofDoWhileQuadNum = jumpStack.pop(); // QuadNum del GOTOF de la condición del DO-WHILE
        fillQuadruple(gotofDoWhileQuadNum, String.valueOf(quadCount)); // Rellenar para salir del bucle
        System.out.println("DEBUG: Fin DO-WHILE. Backpatched GOTOF en " + gotofDoWhileQuadNum + " a " + quadCount);
    }


    // ======================================================================
    // === MÉTODOS PARA DECLARACIÓN E INVOCACIÓN DE FUNCIONES ===
    // ======================================================================

    /**
     * Punto neurálgico para el inicio de la declaración de una función.
     * Registra la función en el directorio y cambia el ámbito actual a local.
     * @param functionName El nombre de la función.
     * @param returnType El tipo de retorno de la función.
     * @param parameterTypes Lista de tipos de los parámetros en orden de declaración.
     */
    public void startFunctionDeclaration(String functionName, String returnType, List<String> parameterTypes) {
        if (functionDirectory.functionExists(functionName)) {
            throw new RuntimeException("Error semántico: La función '" + functionName + "' ya ha sido declarada.");
        }

        // 1. Registra la función en el directorio con su tipo de retorno y dirección de inicio actual.
        // La dirección de inicio del código de la función es el quadCount actual.
        functionDirectory.addFunction(functionName, returnType, quadCount);

        // 2. Crea una nueva VariableTable para las variables locales de esta función
        VariableTable functionLocalTable = new VariableTable();
        functionDirectory.getFunction(functionName).setLocalVariables(functionLocalTable);

        // 3. Añade los parámetros a la FunctionInfo y los declara como variables locales
        FunctionDirectory.FunctionInfo funcInfo = functionDirectory.getFunction(functionName);
        for (int i = 0; i < parameterTypes.size(); i++) {
            String type = parameterTypes.get(i);
            String paramName = "param_" + i; // Nombre ficticio para el parámetro interno (si no se usa ID para params)

            // Añadir el parámetro a la lista de parámetros de la función (para verificación de llamadas)
            funcInfo.addParameter(paramName, type);

            // Asignar dirección para el parámetro (se tratan como variables locales al inicio de la función)
            int paramAddress = memoryManager.assignVariableAddress(type, "LOCAL");
            functionLocalTable.addVariable(paramName, type, paramAddress); // Declara el parámetro en la tabla local de la función
            System.out.println("DEBUG: Parámetro '" + paramName + "' (" + type + ") asignado a dirección: " + paramAddress + " (local a " + functionName + ")");
        }

        // 4. Cambia el ámbito actual a la tabla de variables locales de la función.
        scopeStack.push(functionLocalTable);

        // 5. Guarda el nombre de la función que se está declarando actualmente
        currentFunctionDeclarationStack.push(functionName);

        // 6. Reinicia los contadores de memoria local y temporal para esta nueva función.
        memoryManager.resetLocalAndTempCounters();

        System.out.println("DEBUG: Inicio de declaración de función '" + functionName + "' en quad " + quadCount);
    }

    /**
     * Punto neurálgico para el final de la declaración de una función.
     * Genera un cuádruplo ENDFUNC y restaura el ámbito anterior.
     */
    public void endFunctionDeclaration() {
        // Asegúrate de que haya una función en la pila de declaración
        if (currentFunctionDeclarationStack.isEmpty()) {
            System.err.println("GC-WARNING: endFunctionDeclaration llamado sin una función en la pila de declaración.");
            return;
        }
        String declaredFunctionName = currentFunctionDeclarationStack.pop();

        // 1. Genera el cuádruplo ENDFUNC
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("ENDFUNC", null, null, null));
        quadCount++;
        System.out.println("DEBUG: ENDFUNC generado para la función '" + declaredFunctionName + "'.");

        // 2. Restaura el ámbito anterior (pop de la pila de ámbitos)
        scopeStack.pop();

        // 3. Reinicia los contadores de memoria local y temporal del MemoryManager.
        // Esto es importante para que la próxima función (o el main) empiece con contadores limpios.
        memoryManager.resetLocalAndTempCounters();
    }

    /**
     * Punto neurálgico para el inicio de la invocación de una función.
     * Genera un cuádruplo ERA y prepara la pila para la verificación de parámetros.
     * @param functionName El nombre de la función que se va a invocar.
     */
    public void startFunctionCall(String functionName) {
        FunctionDirectory.FunctionInfo funcInfo = functionDirectory.getFunction(functionName);
        if (funcInfo == null) {
            throw new RuntimeException("Error semántico: Función '" + functionName + "' no declarada.");
        }

        // 1. Genera el cuádruplo ERA (reserva el espacio de memoria para la función llamada)
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("ERA", functionName, null, null));
        quadCount++;
        System.out.println("DEBUG: ERA generado para '" + functionName + "'.");

        // 2. Prepara la verificación de parámetros
        // Guardamos una copia mutable de la lista de parámetros esperados para ir removiendo a medida que se procesan.
        paramCheckStack.push(new ArrayList<>(funcInfo.getParameters()));
        callStack.push(functionName); // Guarda el nombre de la función que se está llamando
    }

    /**
     * Punto neurálgico para el procesamiento de un argumento de una llamada a función.
     * Genera un cuádruplo PARAM y realiza la verificación de tipo.
     */
    public void processArgument() {
        if (pilaOperandosDirecciones.isEmpty() || pilaTipos.isEmpty()) {
            throw new RuntimeException("Error interno: No hay argumento en las pilas para processArgument().");
        }
        String argType = pilaTipos.pop();
        Integer argAddress = pilaOperandosDirecciones.pop();

        if (paramCheckStack.isEmpty() || callStack.isEmpty()) {
            throw new RuntimeException("Error interno: Pila de verificación de parámetros o de llamadas vacía.");
        }

        List<FunctionDirectory.ParameterInfo> expectedParams = paramCheckStack.peek();
        String currentFunctionName = callStack.peek();

        if (expectedParams.isEmpty()) {
            throw new RuntimeException("Error semántico: Demasiados argumentos para la función '" + currentFunctionName + "'.");
        }

        // Obtiene el próximo parámetro esperado y lo remueve de la lista
        FunctionDirectory.ParameterInfo expectedParam = expectedParams.remove(0);

        // 1. Verificación de tipos de argumento vs. parámetro esperado
        // Usamos el cubo semántico para la asignación para verificar la compatibilidad
        String assignCompatibility = getTipoResultante(expectedParam.getType(), argType, "=");
        if (assignCompatibility == null || !assignCompatibility.equals(expectedParam.getType())) {
            throw new RuntimeException("Error de tipos: Argumento incompatible para el parámetro '" + expectedParam.getName() + "' en la función '" + currentFunctionName + "'. Esperado: " + expectedParam.getType() + ", Obtenido: " + argType);
        }

        // 2. Genera el cuádruplo PARAM
        // El operando derecho es el número de parámetro (0-indexed).
        // Calculamos el índice basándonos en la cantidad de parámetros ya procesados.
        FunctionDirectory.FunctionInfo funcInfo = functionDirectory.getFunction(currentFunctionName);
        int paramIndex = funcInfo.getParameters().size() - expectedParams.size() - 1; // Calcula el índice original

        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("PARAM", String.valueOf(argAddress), String.valueOf(paramIndex), null));
        quadCount++;
        System.out.println("DEBUG: PARAM generado para argumento: " + argAddress + " (param " + paramIndex + ").");
    }

    /**
     * Punto neurálgico para el final de la invocación de una función.
     * Genera un cuádruplo GOSUB y maneja el valor de retorno si lo hay.
     * @param functionName El nombre de la función invocada.
     */
    public void endFunctionCall(String functionName) {
        if (paramCheckStack.isEmpty() || callStack.isEmpty()) {
            System.err.println("GC-WARNING: Pila de verificación de parámetros o de llamadas vacía al finalizar llamada a función.");
            return;
        }

        List<FunctionDirectory.ParameterInfo> remainingParams = paramCheckStack.pop();
        String invokedFunctionName = callStack.pop();

        if (!remainingParams.isEmpty()) {
            throw new RuntimeException("Error semántico: Faltan argumentos para la función '" + invokedFunctionName + "'. Se esperaban " + remainingParams.size() + " más.");
        }

        FunctionDirectory.FunctionInfo funcInfo = functionDirectory.getFunction(functionName);
        if(funcInfo == null) {
            throw new RuntimeException("Error interno: Función '" + functionName + "' no encontrada en el directorio al finalizar llamada.");
        }

        // 1. Genera el cuádruplo GOSUB (salto a la subrutina)
        // El resultado es la dirección de inicio de la función.
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOSUB", functionName, null, String.valueOf(funcInfo.getStartAddress())));
        quadCount++;
        System.out.println("DEBUG: GOSUB generado para '" + functionName + "'.");

        // 2. Manejo del valor de retorno (si la función no es VOID)
        if (!"VOID".equalsIgnoreCase(funcInfo.getReturnType())) {
            // Asigna una dirección temporal para el valor de retorno en el ámbito del llamador.
            // La MV sabrá que después de GOSUB, el valor de retorno de la función llamada
            // se encontrará en una ubicación específica (ej. un registro) y debe moverlo a este temporal.
            int tempReturnAddress = memoryManager.assignTempAddress(funcInfo.getReturnType());
            // Genera un cuádruplo para que la MV sepa dónde colocar el valor de retorno.
            // "RETURN_ASSIGN" es un operador ficticio para indicar que el valor retornado por la función
            // debe ser asignado a este temporal.
            filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("RETURN_ASSIGN", functionName, null, String.valueOf(tempReturnAddress)));
            quadCount++;
            pilaOperandosDirecciones.push(tempReturnAddress); // Empuja el temporal para que la expresión lo use
            pilaTipos.push(funcInfo.getReturnType());
            System.out.println("DEBUG: Valor de retorno de '" + functionName + "' asignado a temp: " + tempReturnAddress);
        }
    }

    /**
     * Genera un cuádruplo RETURN.
     * Se llama al final de una función (o en una sentencia RETURN explícita).
     * @param hasReturnValue Indica si la función devuelve un valor (true) o es VOID (false).
     */
    public void generarCuadruploReturn(boolean hasReturnValue) {
        if (currentFunctionDeclarationStack.isEmpty()) {
            throw new RuntimeException("Error interno: RETURN llamado fuera de una declaración de función.");
        }
        String currentFunctionName = currentFunctionDeclarationStack.peek();
        FunctionDirectory.FunctionInfo currentFunctionInfo = functionDirectory.getFunction(currentFunctionName);

        if (currentFunctionInfo == null) {
            throw new RuntimeException("Error interno: No se pudo determinar la función actual para RETURN.");
        }

        if (hasReturnValue) {
            if (pilaTipos.isEmpty() || pilaOperandosDirecciones.isEmpty()) {
                throw new RuntimeException("Error semántico: Se esperaba un valor de retorno para la función '" + currentFunctionName + "' pero la pila de operandos está vacía.");
            }
            String actualReturnType = pilaTipos.pop();
            Integer actualReturnValueAddress = pilaOperandosDirecciones.pop();

            String expectedReturnType = currentFunctionInfo.getReturnType();

            // 1. Validar que el tipo de retorno actual coincida con el tipo de retorno declarado de la función
            String compatibilityCheck = getTipoResultante(expectedReturnType, actualReturnType, "=");

            if (compatibilityCheck == null || !compatibilityCheck.equals(expectedReturnType)) {
                throw new RuntimeException("Error semántico: Tipo de retorno incompatible para la función '" + currentFunctionName + "'. Esperado: " + expectedReturnType + ", Obtenido: " + actualReturnType);
            }

            // 2. Generar el cuádruplo RETURN con la dirección del valor retornado
            filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("RETURN", String.valueOf(actualReturnValueAddress), null, null));
            quadCount++;
            System.out.println("DEBUG: Cuádruplo RETURN generado con valor: " + actualReturnValueAddress);

        } else { // Si la función es VOID
            if (!"VOID".equalsIgnoreCase(currentFunctionInfo.getReturnType())) {
                throw new RuntimeException("Error semántico: La función '" + currentFunctionName + "' no es VOID y requiere una sentencia RETURN con un valor.");
            }
            filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("RETURN", null, null, null)); // RETURN sin valor
            quadCount++;
            System.out.println("DEBUG: Cuádruplo RETURN VOID generado.");
        }
    }

    /**
     * Genera un cuádruplo para la sentencia PRINT.
     * Asume que la expresión a imprimir ya ha sido procesada y su dirección
     * y tipo están en la cima de las pilas.
     */
    public void generarCuadruploPrint() {
        if (pilaOperandosDirecciones.isEmpty() || pilaTipos.isEmpty()) {
            throw new RuntimeException("Error semántico: No hay expresión para imprimir (pila de operandos vacía).");
        }
        Integer dirOperando = pilaOperandosDirecciones.pop();
        String tipoOperando = pilaTipos.pop(); // Puedes usar el tipo para validación o depuración

        // El operador es "PRINT", el resultado es la dirección del operando a imprimir.
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("PRINT", null, null, String.valueOf(dirOperando)));
        quadCount++;
        System.out.println("DEBUG: Cuádruplo PRINT generado para dirección: " + dirOperando);
    }
}