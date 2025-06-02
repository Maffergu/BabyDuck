package estructuras_de_datos;

import java.util.HashMap;
import java.util.Map;

public class CuboSemantico {
    private final Map<String, Map<String, Map<String, String>>> cubo;

    public CuboSemantico() {
        cubo = new HashMap<>();

        // Operaciones aritméticas
        agregar("int", "int", "+", "int");
        agregar("int", "int", "-", "int");
        agregar("int", "int", "*", "int");
        agregar("int", "int", "/", "int");

        agregar("int", "float", "+", "float");
        agregar("float", "int", "+", "float");
        agregar("float", "float", "+", "float");

        agregar("int", "float", "-", "float");
        agregar("float", "int", "-", "float");
        agregar("float", "float", "-", "float");

        agregar("int", "float", "*", "float");
        agregar("float", "int", "*", "float");
        agregar("float", "float", "*", "float");

        agregar("int", "float", "/", "float");
        agregar("float", "int", "/", "float");
        agregar("float", "float", "/", "float");

        // Operaciones relacionales → resultado será tipo int (booleano simulado)
        agregar("int", "int", "<", "int");
        agregar("int", "int", ">", "int");
        agregar("int", "int", "!=", "int");

        agregar("int", "float", "<", "int");
        agregar("float", "int", ">", "int");
        agregar("float", "float", "!=", "int");
        agregar("float", "float", "<", "int");
        agregar("float", "float", ">", "int");
        agregar("int", "float", "!=", "int");
        agregar("float", "int", "!=", "int");

        // Comparación de strings
        agregar("string", "string", "!=", "int");

        // Asignación
        agregar("int", "int", "=", "int");
        agregar("float", "int", "=", "float");
        agregar("float", "float", "=", "float");
        agregar("string", "string", "=", "string");
        // Operaciones relacionales adicionales
        agregar("int", "int", "<=", "int");
        agregar("int", "int", ">=", "int");

        agregar("int", "float", "<=", "int");
        agregar("int", "float", ">=", "int");

        agregar("float", "int", "<=", "int");
        agregar("float", "int", ">=", "int");

        agregar("float", "float", "<=", "int");
        agregar("float", "float", ">=", "int");
    }

    private void agregar(String tipo1, String tipo2, String operador, String resultado) {
        cubo.computeIfAbsent(tipo1, k -> new HashMap<>())
                .computeIfAbsent(tipo2, k -> new HashMap<>())
                .put(operador, resultado);
    }

    public boolean esCompatible(String tipo1, String tipo2, String operador) {
        return resultado(tipo1, tipo2, operador) != null;
    }

    public String resultado(String tipo1, String tipo2, String operador) {
        if (cubo.containsKey(tipo1) &&
                cubo.get(tipo1).containsKey(tipo2) &&
                cubo.get(tipo1).get(tipo2).containsKey(operador)) {
            return cubo.get(tipo1).get(tipo2).get(operador);
        }
        return null;
    }
}
