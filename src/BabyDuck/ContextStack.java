package BabyDuck;

import java.util.Stack;

public class ContextStack {
    private Stack<String> contextNames; // Pila de nombres de funciones/ámbitos (ej. "global", "main", "myFunc")

    public ContextStack() {
        contextNames = new Stack<>();
        contextNames.push("global"); // El ámbito global es el primero por defecto
    }

    public void pushContext(String contextName) {
        contextNames.push(contextName);
    }

    public String popContext() {
        if (contextNames.size() > 1) { // Siempre debe quedar al menos el ámbito global
            return contextNames.pop();
        }
        throw new RuntimeException("Error: Intentando salir del ámbito global de la pila de contextos.");
    }

    public String getCurrentContext() {
        return contextNames.peek();
    }

    public boolean isGlobalContext() {
        return contextNames.peek().equals("global");
    }
}