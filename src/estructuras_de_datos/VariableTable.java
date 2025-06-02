package estructuras_de_datos;

import java.util.HashMap;
import java.util.Map;

public class VariableTable {

    private final Map<String, VariableInfo> variables;

    public VariableTable() {
        this.variables = new HashMap<>();
    }

    public void agregarVariable(String nombre, String tipo, Direccionador direccionador, String scope) {
        if (variables.containsKey(nombre)) {
            throw new RuntimeException("Variable doblemente declarada: " + nombre);
        }
        int direccion = direccionador.asignarDireccion(tipo, scope);
        variables.put(nombre, new VariableInfo(tipo, direccion));
    }

    public void agregarVariable(String nombre, String tipo, int direccion) {
        if (variables.containsKey(nombre)) {
            throw new RuntimeException("Variable doblemente declarada: " + nombre);
        }
        variables.put(nombre, new VariableInfo(tipo, direccion));
    }

    public VariableInfo getVariable(String nombre) {
        return variables.get(nombre);
    }

    public boolean contieneVariable(String nombre) {
        return variables.containsKey(nombre);
    }

    @Override
    public String toString() {
        return variables.toString();
    }
}