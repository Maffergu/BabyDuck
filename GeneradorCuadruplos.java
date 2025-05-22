package BabyDuck;

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.List; // Necesario para ArrayList en imprimirCuadruplos
import java.util.ArrayList; // Necesario para ArrayList en imprimirCuadruplos

// Clase GeneradorCuadruplos: Responsable de la generación de código intermedio (cuádruplos)
// y la gestión de direcciones virtuales.
public class GeneradorCuadruplos {

    // Pilas para operadores, operandos (ahora direcciones virtuales) y tipos
    private Stack<String> pilaOperadores;
    private Stack<Integer> pilaOperandosDirecciones; // Almacena direcciones virtuales (Integer)
    private Stack<String> pilaTipos;
    private Queue<EstructurasDeDatos.Cuadruplo> filaCuadruplos; // Usa la clase Cuadruplo de EstructurasDeDatos

    // Componentes para la gestión de memoria y tabla de símbolos
    private MemoryManager memoryManager;
    private FunctionDirectory functionDirectory;
    private VariableTable currentLocalTable; // Tabla de variables locales del ámbito actual
    private VariableTable globalTable;       // Tabla de variables globales

    // --- Atributos para el Control de Flujo ---
    private int quadCount; // Contador para el número de cuádruplo actual (dirección de instrucción)
    private Stack<Integer> jumpStack; // Pila para almacenar los números de cuádruplo que necesitan ser rellenados (GOTOF, GOTO)

    // Cubo semántico (simplificado para este ejemplo, debería ser una clase separada)
    private static final Map<String, Map<String, Map<String, String>>> cuboSemantico = new HashMap<>();

    static {
        // Definición de reglas de tipos para operadores aritméticos y relacionales
        Map<String, Map<String, String>> intFloatOps = new HashMap<>();
        intFloatOps.put("INT", new HashMap<String, String>() {{
            put("INT", "INT");
            put("FLOAT", "FLOAT"); // INT op FLOAT -> FLOAT (promoción)
        }});
        intFloatOps.put("FLOAT", new HashMap<String, String>() {{
            put("INT", "FLOAT"); // FLOAT op INT -> FLOAT (promoción)
            put("FLOAT", "FLOAT");
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
        cuboSemantico.put(">", intFloatOps); // Relacionales también usan intFloatOps para tipos de operandos
        cuboSemantico.put("<", intFloatOps);
        cuboSemantico.put("==", intFloatOps);
        cuboSemantico.put("!=", intFloatOps);
        cuboSemantico.put("&", boolOps); // Operadores lógicos para BOOLEAN
        cuboSemantico.put("|", boolOps);
        // Se pueden agregar más operadores según la gramática de BabyDuck
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
        this.filaCuadruplos = new LinkedList<>();

        this.memoryManager = new MemoryManager();
        this.functionDirectory = new FunctionDirectory();
        this.globalTable = new VariableTable(); // Se inicializa la tabla de variables globales
        this.currentLocalTable = globalTable;   // El ámbito actual inicia como global

        this.quadCount = 0; // Inicia el contador de cuádruplos en 0
        this.jumpStack = new Stack<>(); // Inicializa la pila de saltos

        // Simula la declaración de la función principal 'main' y su tabla de variables local.
        // En un compilador real, esto lo haría el Visitor/Listener al procesar la regla 'main_func'.
        functionDirectory.addFunction("main", "VOID", 0); // 'main' se asume que inicia en dirección 0
        currentLocalTable = functionDirectory.getFunction("main").getLocalVariables();
    }

    /**
     * Obtiene la fila de cuádruplos generados.
     * @return La Queue de objetos Cuadruplo.
     */
    public Queue<EstructurasDeDatos.Cuadruplo> getCuadruplos() {
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
     * Simula la declaración de una variable y asigna su dirección virtual.
     * Este método sería invocado por el analizador semántico (Visitor/Listener)
     * al encontrar una declaración de variable en el código fuente.
     * @param name El nombre de la variable.
     * @param type El tipo de la variable (ej. "INT", "FLOAT", "BOOLEAN").
     * @param scope El ámbito de la variable ("GLOBAL" o "LOCAL").
     */
    public void declararVariable(String name, String type, String scope) {
        int address = memoryManager.assignVariableAddress(type, scope);
        if ("GLOBAL".equalsIgnoreCase(scope)) {
            globalTable.addVariable(name, type, address);
        } else { // Asumimos "LOCAL" para cualquier otro scope
            currentLocalTable.addVariable(name, type, address);
        }
        System.out.println("DEBUG: Variable '" + name + "' (" + type + ", " + scope + ") asignada a dirección: " + address);
    }

    /**
     * Simula el procesamiento de un operando (variable o constante).
     * Busca su dirección virtual o le asigna una si es una constante nueva,
     * y luego empuja la dirección y el tipo a las pilas correspondientes.
     * Este método sería invocado por el analizador semántico al encontrar un operando.
     * @param value El nombre de la variable o el valor literal de la constante (como String).
     * @param isConstant Indica si el 'value' es una constante literal (true) o un identificador de variable (false).
     * @param type Si es una constante, su tipo (ej. "INT", "FLOAT", "STRING"). Si es variable, puede ser null.
     */
    public void procesarOperando(String value, boolean isConstant, String type) {
        if (isConstant) {
            // Asigna dirección a la constante (reutilizando si ya existe)
            int address = memoryManager.assignConstantAddress(value, type);
            pilaOperandosDirecciones.push(address);
            pilaTipos.push(type);
            System.out.println("DEBUG: Constante '" + value + "' (" + type + ") asignada a dirección: " + address);
        } else { // Es un identificador de variable
            // Busca la variable en la tabla de símbolos local y luego global
            VariableTable.VariableInfo varInfo = currentLocalTable.getVariable(value);
            if (varInfo == null) {
                varInfo = globalTable.getVariable(value);
            }
            if (varInfo == null) {
                // Si la variable no se encuentra, es un error semántico
                throw new RuntimeException("Error semántico: Variable '" + value + "' no declarada.");
            }
            // Empuja la dirección y el tipo de la variable a las pilas
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
     * Extrae operandos y tipos de las pilas, verifica compatibilidad, asigna
     * una dirección temporal para el resultado y añade el cuádruplo a la fila.
     * Este método sería invocado por el analizador semántico al finalizar una expresión.
     * @param operador El operador de la expresión (ej. "+", "*", ">").
     * @throws RuntimeException Si hay un error de tipos.
     */
    public void generarCuadruploExpresion(String operador) {
        String tipoDerecho = pilaTipos.pop();
        String tipoIzquierdo = pilaTipos.pop();
        Integer dirDerecha = pilaOperandosDirecciones.pop();
        Integer dirIzquierda = pilaOperandosDirecciones.pop();

        String tipoResultante = getTipoResultante(tipoIzquierdo, tipoDerecho, operador);
        if (tipoResultante == null) {
            throw new RuntimeException("Error de tipos: Operación no válida '" + operador + "' con tipos " + tipoIzquierdo + " y " + tipoDerecho);
        }

        // Asigna una dirección temporal para el resultado de esta operación
        int dirResultado = memoryManager.assignTempAddress(tipoResultante);
        // Crea el cuádruplo con las direcciones virtuales (convertidas a String)
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo(operador, String.valueOf(dirIzquierda), String.valueOf(dirDerecha), String.valueOf(dirResultado)));
        // Empuja la dirección y el tipo del resultado a las pilas para futuras operaciones
        pilaOperandosDirecciones.push(dirResultado);
        pilaTipos.push(tipoResultante);
        quadCount++; // Incrementa el contador de cuádruplos
        System.out.println("DEBUG: Cuádruplo generado: " + filaCuadruplos.peek());
    }

    /**
     * Genera un cuádruplo para una asignación.
     * Extrae el valor (dirección) de la pila de operandos y busca la dirección
     * de la variable de destino en la tabla de símbolos.
     * Este método sería invocado por el analizador semántico al procesar una asignación.
     * @param variableName El nombre de la variable a la que se asigna el valor.
     * @throws RuntimeException Si la variable de asignación no está declarada.
     */
    public void generarCuadruploAsignacion(String variableName) {
        String tipoValor = pilaTipos.pop(); // Tipo del valor a asignar
        Integer dirValor = pilaOperandosDirecciones.pop(); // Dirección del valor a asignar

        // Busca la dirección de la variable de destino en la tabla de símbolos
        VariableTable.VariableInfo varInfo = currentLocalTable.getVariable(variableName);
        if (varInfo == null) {
            varInfo = globalTable.getVariable(variableName);
        }
        if (varInfo == null) {
            throw new RuntimeException("Error semántico: Variable de asignación '" + variableName + "' no declarada.");
        }

        // Aquí se debería realizar la verificación de compatibilidad de tipos para la asignación
        // (ej. INT = FLOAT podría requerir un cast o ser un error, dependiendo de BabyDuck)
        // Por simplicidad en este ejemplo, asumimos compatibilidad o que ya se verificó.

        // Crea el cuádruplo de asignación
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("=", String.valueOf(dirValor), null, String.valueOf(varInfo.getAddress())));
        quadCount++; // Incrementa el contador de cuádruplos
        System.out.println("DEBUG: Cuádruplo generado: " + filaCuadruplos.peek());
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
        // Para imprimir los cuádruplos en orden de generación (como una lista)
        // Necesitamos convertirlos a una lista para acceder por índice y rellenar saltos.
        // En un compilador real, la filaCuadruplos se convertiría a List<Cuadruplo>
        // o se usaría una estructura que permita acceso por índice para rellenar los GOTO/GOTOF.
        List<EstructurasDeDatos.Cuadruplo> listaCuadruplos = new ArrayList<>(filaCuadruplos);
        for (int i = 0; i < listaCuadruplos.size(); i++) {
            System.out.println( (i + 1) + ": " + listaCuadruplos.get(i).toString());
        }
        System.out.println("--------------------------------------------------");
    }

    // ======================================================================
    // === NUEVOS MÉTODOS PARA EL CONTROL DE FLUJO (IF-ELSE, WHILE) ===
    // ======================================================================

    /**
     * Punto neurálgico para el inicio de un IF.
     * Genera un GOTOF y empuja el número de cuádruplo actual a la pila de saltos.
     * Este método se llamaría después de procesar la condición del IF.
     */
    public void inicioIf() {
        // La condición ya fue procesada y su resultado (dirección temporal booleana)
        // está en el tope de la pila de operandos.
        String tipoCondicion = pilaTipos.pop();
        Integer dirCondicion = pilaOperandosDirecciones.pop();

        if (!"BOOLEAN".equalsIgnoreCase(tipoCondicion)) {
            throw new RuntimeException("Error de tipos: La condición del IF debe ser de tipo BOOLEAN.");
        }

        // Genera un GOTOF con destino pendiente (null)
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTOF", String.valueOf(dirCondicion), null, null));
        jumpStack.push(quadCount); // Guarda el número de cuádruplo del GOTOF
        quadCount++; // Incrementa el contador de cuádruplos para el siguiente cuádruplo
        System.out.println("DEBUG: GOTOF generado (pendiente): " + filaCuadruplos.peek());
    }

    /**
     * Punto neurálgico para el ELSE de un IF.
     * Rellena el GOTOF del IF y genera un GOTO para saltar el bloque ELSE.
     * Este método se llamaría al encontrar la palabra clave 'else'.
     */
    public void inicioElse() {
        // Genera un GOTO con destino pendiente para saltar el bloque ELSE
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTO", null, null, null));
        Integer gotoQuadNum = quadCount; // Guarda el número de cuádruplo del GOTO
        quadCount++;

        // Rellena el GOTOF del IF (que está en el tope de la pila de saltos)
        // El GOTOF debe saltar a la posición actual (inicio del bloque ELSE)
        Integer gotofQuadNum = jumpStack.pop(); // Obtiene el número de cuádruplo del GOTOF
        // Necesitamos acceder al cuádruplo en la fila y modificarlo.
        // Para simular, asumimos que podemos acceder y modificar el cuádruplo por su índice.
        // En una implementación real, la filaCuadruplos sería un ArrayList<Cuadruplo>.
        // Para este ejemplo, simplemente lo imprimimos para mostrar el efecto.
        System.out.println("DEBUG: Rellenado GOTOF en " + gotofQuadNum + " a " + quadCount);


        jumpStack.push(gotoQuadNum); // Empuja el número de cuádruplo del GOTO a la pila de saltos
        System.out.println("DEBUG: GOTO generado (pendiente): " + filaCuadruplos.peek());
    }

    /**
     * Punto neurálgico para el fin de un IF-ELSE o el fin de un IF simple.
     * Rellena el último salto pendiente (ya sea un GOTOF o un GOTO).
     * Este método se llamaría al encontrar la palabra clave 'end' del IF.
     */
    public void finIf() {
        Integer saltoPendienteQuadNum = jumpStack.pop(); // Obtiene el número de cuádruplo del salto pendiente
        // Rellena el cuádruplo de salto con la posición actual (fin del bloque IF/ELSE)
        // Similar al inicioElse, esto implicaría modificar un cuádruplo existente en la fila.
        // Para el ejemplo, solo lo imprimimos para mostrar el efecto.
        System.out.println("DEBUG: Rellenado salto pendiente en " + saltoPendienteQuadNum + " a " + quadCount);
    }

    /**
     * Punto neurálgico para el inicio de un WHILE.
     * Guarda el número de cuádruplo actual (punto de retorno al inicio del ciclo).
     * Este método se llamaría antes de procesar la condición del WHILE.
     */
    public void inicioWhile() {
        jumpStack.push(quadCount); // Guarda el número de cuádruplo del inicio del WHILE
        System.out.println("DEBUG: Inicio WHILE guardado en " + jumpStack.peek());
    }

    /**
     * Punto neurálgico para la condición de un WHILE.
     * Genera un GOTOF para salir del ciclo si la condición es falsa.
     * Este método se llamaría después de procesar la condición del WHILE.
     */
    public void condicionWhile() {
        String tipoCondicion = pilaTipos.pop();
        Integer dirCondicion = pilaOperandosDirecciones.pop();

        if (!"BOOLEAN".equalsIgnoreCase(tipoCondicion)) {
            throw new RuntimeException("Error de tipos: La condición del WHILE debe ser de tipo BOOLEAN.");
        }

        // Genera un GOTOF con destino pendiente (null) para salir del ciclo
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTOF", String.valueOf(dirCondicion), null, null));
        jumpStack.push(quadCount); // Guarda el número de cuádruplo del GOTOF
        quadCount++;
        System.out.println("DEBUG: GOTOF WHILE generado (pendiente): " + filaCuadruplos.peek());
    }

    /**
     * Punto neurálgico para el fin de un WHILE.
     * Genera un GOTO para regresar al inicio del ciclo y rellena el GOTOF pendiente.
     * Este método se llamaría al encontrar la palabra clave 'end' del WHILE.
     */
    public void finWhile() {
        // 1. Rellenar el GOTOF de la condición del WHILE
        Integer gotofWhileQuadNum = jumpStack.pop(); // Obtiene el número de cuádruplo del GOTOF
        // Rellenar con la posición actual (fin del bloque WHILE)
        System.out.println("DEBUG: Rellenado GOTOF WHILE en " + gotofWhileQuadNum + " a " + quadCount);

        // 2. Generar un GOTO para regresar al inicio del ciclo
        Integer inicioWhileQuadNum = jumpStack.pop(); // Obtiene el número de cuádruplo del inicio del WHILE
        filaCuadruplos.add(new EstructurasDeDatos.Cuadruplo("GOTO", null, null, String.valueOf(inicioWhileQuadNum)));
        quadCount++;
        System.out.println("DEBUG: GOTO para regresar al WHILE: " + filaCuadruplos.peek());
    }


}
