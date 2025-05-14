package BabyDuck;

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class EstructurasDeDatos {

    // Pilas para operadores, operandos y tipos
    private Stack<String> operadores;
    private Stack<String> operandos;
    private Stack<String> tipos;

    // Fila para cuádruplos
    private Queue<Cuadruplo> cuadruplos;
    private int tempCount; // Contador para variables temporales

    // Clase interna para representar un cuádruplo
    public static class Cuadruplo {
        private String operador;
        private String operandoIzquierdo;
        private String operandoDerecho;
        private String resultado;

        public Cuadruplo(String operador, String operandoIzquierdo, String operandoDerecho, String resultado) {
            this.operador = operador;
            this.operandoIzquierdo = operandoIzquierdo;
            this.operandoDerecho = operandoDerecho;
            this.resultado = resultado;
        }

        // Getters para los atributos del cuádruplo
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

        @Override
        public String toString() {
            return "(" + operador + ", " + operandoIzquierdo + ", " + operandoDerecho + ", " + resultado + ")";
        }
    }

    // Constructor de la clase EstructurasDeDatos
    public EstructurasDeDatos() {
        this.operadores = new Stack<>();
        this.operandos = new Stack<>();
        this.tipos = new Stack<>();
        this.cuadruplos = new LinkedList<>();
        this.tempCount = 0;
    }

    // Métodos para la pila de operadores
    public void pushOperador(String operador) {
        this.operadores.push(operador);
    }

    public String popOperador() {
        if (!this.operadores.isEmpty()) {
            return this.operadores.pop();
        } else {
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


    // Métodos para la pila de operandos
    public void pushOperando(String operando) {
        this.operandos.push(operando);
    }

    public String popOperando() {
        if (!this.operandos.isEmpty()) {
            return this.operandos.pop();
        } else {
            return null;
        }
    }

    public String peekOperando() {
        if (!this.operandos.isEmpty()) {
            return this.operandos.peek();
        } else {
            return null;
        }
    }

    public boolean estaVaciaPilaOperandos() {
        return this.operandos.isEmpty();
    }

    // Métodos para la pila de tipos
    public void pushTipo(String tipo) {
        this.tipos.push(tipo);
    }

    public String popTipo() {
        if (!this.tipos.isEmpty()) {
            return this.tipos.pop();
        } else {
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

    // Métodos para la fila de cuádruplos
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
        System.out.println("Cuádruplos:");
        for (Cuadruplo cuadruplo : this.cuadruplos) {
            System.out.println(cuadruplo);
        }
    }

    // Método para generar una variable temporal
    private String generarTemp() {
        tempCount++;
        return "temp" + tempCount;
    }

    // Métodos para generar cuádruplos para expresiones aritméticas
    public void generarCuadruploAritmetico() {
        String operador = popOperador();
        String operandoDerecho = popOperando();
        String operandoIzquierdo = popOperando();
        popTipo(); // Ignoramos el tipo por ahora, pero debería usarse para validación
        popTipo(); // Ignoramos el tipo por ahora, pero debería usarse para validación
        String resultado = generarTemp();
        agregarCuadruplo(operador, operandoIzquierdo, operandoDerecho, resultado);
        pushOperando(resultado);
        pushTipo("INT"); // Suponemos que el resultado es INT, ¡pero esto es importante validarlo!
    }

    // Métodos para generar cuádruplos para expresiones relacionales
    public void generarCuadruploRelacional() {
        String operador = popOperador();
        String operandoDerecho = popOperando();
        String operandoIzquierdo = popOperando();
        popTipo(); // Ignoramos el tipo por ahora, pero debería usarse para validación
        popTipo(); // Ignoramos el tipo por ahora, pero debería usarse para validación
        String resultado = generarTemp();
        agregarCuadruplo(operador, operandoIzquierdo, operandoDerecho, resultado);
        pushOperando(resultado);
        pushTipo("BOOLEAN"); // El resultado de una comparación es BOOLEAN
    }

    // Método para generar cuádruplo de asignación
    public void generarCuadruploAsignacion(String variable, String valor) {
        agregarCuadruplo("=", valor, null, variable);
    }

    public static void main(String[] args) {
        // Ejemplo de uso para Baby Duck
        EstructurasDeDatos estructuras = new EstructurasDeDatos();

        // Expresión aritmética: x = a + b * c
        estructuras.pushOperando("a");
        estructuras.pushTipo("INT");
        estructuras.pushOperando("b");
        estructuras.pushTipo("INT");
        estructuras.pushOperador("*");
        estructuras.generarCuadruploAritmetico(); // temp1 = b * c
        estructuras.pushOperando("c");
        estructuras.pushTipo("INT");
        estructuras.pushOperador("+");
        estructuras.generarCuadruploAritmetico(); // temp2 = a + temp1
        estructuras.generarCuadruploAsignacion("x", estructuras.popOperando()); // x = temp2

        // Expresión relacional: y = a > b
        estructuras.pushOperando("a");
        estructuras.pushTipo("INT");
        estructuras.pushOperando("b");
        estructuras.pushTipo("INT");
        estructuras.pushOperador(">");
        estructuras.generarCuadruploRelacional(); // temp3 = a > b
        estructuras.generarCuadruploAsignacion("y", estructuras.popOperando()); // y = temp3

        estructuras.imprimirCuadruplos();
    }
}

