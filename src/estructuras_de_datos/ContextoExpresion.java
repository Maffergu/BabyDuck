package estructuras_de_datos;

import java.util.Stack;

public class ContextoExpresion {
    private final Stack<String> pilaOperandos;
    private final Stack<String> pilaOperadores;
    private final Stack<String> pilaTipos;

    public ContextoExpresion() {
        pilaOperandos = new Stack<>();
        pilaOperadores = new Stack<>();
        pilaTipos = new Stack<>();
    }

    // Operandos
    public void pushOperando(String operando) {
        pilaOperandos.push(operando);
    }

    public String popOperando() {
        return pilaOperandos.pop();
    }

    public String peekOperando() {
        return pilaOperandos.peek();
    }

    public boolean isEmptyOperandos() {
        return pilaOperandos.isEmpty();
    }

    // Operadores
    public void pushOperador(String operador) {
        pilaOperadores.push(operador);
    }

    public String popOperador() {
        return pilaOperadores.pop();
    }

    public String peekOperador() {
        return pilaOperadores.peek();
    }

    public boolean isEmptyOperadores() {
        return pilaOperadores.isEmpty();
    }

    // Tipos
    public void pushTipo(String tipo) {
        pilaTipos.push(tipo);
    }

    public String popTipo() {
        return pilaTipos.pop();
    }

    public String peekTipo() {
        return pilaTipos.peek();
    }

    public boolean isEmptyTipos() {
        return pilaTipos.isEmpty();
    }

    // Limpieza general (por si cambia de contexto)
    public void reset() {
        pilaOperandos.clear();
        pilaOperadores.clear();
        pilaTipos.clear();
    }
}
