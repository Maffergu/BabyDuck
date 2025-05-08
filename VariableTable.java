import java.util.HashMap;
import java.util.Map;

public class VariableTable {

    public static class VariableInfo {
        private String type; // Tipo de la variable (INT, FLOAT, STRING)
        private int address; // Dirección de memoria asignada a la variable

        public VariableInfo(String type) {
            this.type = type;
            this.address = -1; // Inicialmente no asignada
        }

        // Getters y setters
        public String getType() {
            return type;
        }

        public int getAddress() {
            return address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "VariableInfo{" +
                    "type='" + type + '\'' +
                    ", address=" + address +
                    '}';
        }
    }

    private Map<String, VariableInfo> variables;

    public VariableTable() {
        this.variables = new HashMap<>();
    }

    public void addVariable(String name, String type) {
        if (variables.containsKey(name)) {
            // Error: La variable ya existe en este ámbito
            System.err.println("Error semántico: La variable '" + name + "' ya ha sido declarada en este ámbito.");
        } else {
            variables.put(name, new VariableInfo(type));
        }
    }

    public VariableInfo getVariable(String name) {
        return variables.get(name);
    }

    public boolean variableExists(String name) {
        return variables.containsKey(name);
    }

    @Override
    public String toString() {
        return "VariableTable{" +
                "variables=" + variables +
                '}';
    }
}