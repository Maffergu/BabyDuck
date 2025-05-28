package BabyDuck;

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Clase EstructurasDeDatos: Proporciona las estructuras de datos básicas
 * (pilas y fila) utilizadas en el proceso de compilación para BabyDuck,
 * así como la definición del Cuádruplo.
 *
 * NOTA: La lógica de generación de cuádruplos y el manejo de temporales
 * ha sido movida a la clase GeneradorCuadruplos.
 */
public class EstructurasDeDatos {

    // Pilas para operadores, operandos (que ahora serán direcciones virtuales como String) y tipos
    private Stack<String> operadores;
    private Stack<String> operandos; // Almacenará direcciones virtuales (como String)
    private Stack<String> tipos;

    // Fila para cuádruplos
    private Queue<Cuadruplo> cuadruplos;

    /**
     * Clase interna Cuadruplo: Representa una instrucción de código intermedio.
     * Los operandos y el resultado ahora almacenan direcciones virtuales
     * (representadas como Strings para flexibilidad).
     */
    public static class Cuadruplo {
        private String operador;
        private String operandoIzquierdo; // Dirección virtual (como String)
        private String operandoDerecho;  // Dirección virtual (como String)
        private String resultado;        // Dirección virtual (como String)

        public Cuadruplo(String operador, String operandoIzquierdo, String operandoDerecho, String resultado) {
            this.operador = operador;
            this.operandoIzquierdo = operandoIzquierdo;
            this.operandoDerecho = operandoDerecho;
            this.resultado = resultado;
        }

        // Getters para los atributos
        public String getOperador() {
            return operador;
        }

        public String getOperandoIzquierdo() {
            return operandoIzquierdo;
        }

        public String getOperandoDerecho() {
            return operandoDerecho;
        }

        public String getResultado() {
            return resultado;
        }

        // Setter para el campo 'resultado', útil para backpatching
        public void setResultado(String resultado) {
            this.resultado = resultado;
        }

        @Override
        public String toString() {
            // Asegura que "null" se imprima como tal para operandos vacíos
            String left = (operandoIzquierdo == null) ? "null" : operandoIzquierdo;
            String right = (operandoDerecho == null) ? "null" : operandoDerecho;
            String res = (resultado == null) ? "null" : resultado;
            return "(" + operador + ", " + left + ", " + right + ", " + res + ")";
        }
    }

    /**
     * Constructor de la clase EstructurasDeDatos.
     * Inicializa las pilas y la fila de cuádruplos.
     */
    public EstructurasDeDatos() {
        this.operadores = new Stack<>();
        this.operandos = new Stack<>();
        this.tipos = new Stack<>();
        this.cuadruplos = new LinkedList<>();
    }

    // --- Métodos para la pila de operadores ---
    public void pushOperador(String operador) {
        this.operadores.push(operador);
    }

    public String popOperador() {
        if (!this.operadores.isEmpty()) {
            return this.operadores.pop();
        } else {
            System.err.println("Advertencia: Intentando pop en pila de operadores vacía.");
            return null;
        }
    }

    public String peekOperador() {
        if (!this.operadores.isEmpty()) {
            return this.operadores.peek();
        } else {
            return null;
        }
    }

    public boolean estaVaciaPilaOperadores() {
        return this.operadores.isEmpty();
    }


    // --- Métodos para la pila de operandos (ahora direcciones virtuales como String) ---
    public void pushOperando(String operandoDireccion) { // Renombrado para claridad
        this.operandos.push(operandoDireccion);
    }

    public String popOperando() { // Devuelve la dirección como String
        if (!this.operandos.isEmpty()) {
            return this.operandos.pop();
        } else {
            System.err.println("Advertencia: Intentando pop en pila de operandos vacía.");
            return null;
        }
    }

    public String peekOperando() { // Devuelve la dirección como String
        if (!this.operandos.isEmpty()) {
            return this.operandos.peek();
        } else {
            return null;
        }
    }

    public boolean estaVaciaPilaOperandos() {
        return this.operandos.isEmpty();
    }

    // --- Métodos para la pila de tipos ---
    public void pushTipo(String tipo) {
        this.tipos.push(tipo);
    }

    public String popTipo() {
        if (!this.tipos.isEmpty()) {
            return this.tipos.pop();
        } else {
            System.err.println("Advertencia: Intentando pop en pila de tipos vacía.");
            return null;
        }
    }

    public String peekTipo() {
        if (!this.tipos.isEmpty()) {
            return this.tipos.peek();
        } else {
            return null;
        }
    }

    public boolean estaVaciaPilaTipos() {
        return this.tipos.isEmpty();
    }

    // --- Métodos para la fila de cuádruplos ---
    public void agregarCuadruplo(String operador, String operandoIzquierdo, String operandoDerecho, String resultado) {
        this.cuadruplos.add(new Cuadruplo(operador, operandoIzquierdo, operandoDerecho, resultado));
    }

    public Cuadruplo obtenerCuadruplo() {
        if (!this.cuadruplos.isEmpty()) {
            return this.cuadruplos.poll();
        } else {
            return null;
        }
    }

    public boolean estaVaciaFilaCuadruplos() {
        return this.cuadruplos.isEmpty();
    }

    public Queue<Cuadruplo> getCuadruplos() {
        return this.cuadruplos;
    }

    public void imprimirCuadruplos() {
        if (this.cuadruplos.isEmpty()) {
            System.out.println("No hay cuádruplos para desplegar.");
            return;
        }
        System.out.println("Cuádruplos:");
        for (Cuadruplo cuadruplo : this.cuadruplos) {
            System.out.println(cuadruplo);
        }
    }

    // --- El método main para pruebas se elimina o se deja muy básico ---
    // La lógica principal de generación de cuádruplos y asignación de direcciones
    // ahora reside en GeneradorCuadruplos.java.
    public static void main(String[] args) {
        System.out.println("Clase EstructurasDeDatos: Solo para la gestión de pilas y la fila de cuádruplos.");
        System.out.println("La lógica de generación de cuádruplos y direcciones virtuales reside en GeneradorCuadruplos.java.");

        // Ejemplo muy básico para probar las pilas individualmente si se desea
        EstructurasDeDatos eds = new EstructurasDeDatos();
        eds.pushOperador("+");
        eds.pushOperando("1000"); // Simula una dirección virtual
        eds.pushTipo("INT");
        System.out.println("Operador peek: " + eds.peekOperador());
        System.out.println("Operando peek: " + eds.peekOperando());
        System.out.println("Tipo peek: " + eds.peekTipo());

        eds.agregarCuadruplo("=", "1000", null, "4000"); // Simula un cuádruplo
        eds.imprimirCuadruplos();
    }
}
