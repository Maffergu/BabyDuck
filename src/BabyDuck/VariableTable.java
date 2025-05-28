package BabyDuck; // Asegúrate de que este sea el paquete correcto de tu proyecto

import java.util.HashMap;
import java.util.Map;

/**
 * La clase VariableTable gestiona las variables declaradas dentro de un ámbito específico
 * (global o local de una función).
 * Almacena información sobre cada variable, incluyendo su tipo y su dirección virtual.
 */
public class VariableTable {

    /**
     * Clase interna VariableInfo: Representa la información de una variable individual.
     * Ahora incluye la dirección virtual asignada a la variable.
     */
    public static class VariableInfo {
        private String type;    // Tipo de la variable (ej. "INT", "FLOAT", "BOOLEAN", "STRING")
        private int address;    // Dirección virtual asignada a la variable

        /**
         * Constructor para VariableInfo.
         * @param type El tipo de la variable.
         * @param address La dirección virtual asignada a la variable.
         */
        public VariableInfo(String type, int address) {
            this.type = type;
            this.address = address;
        }

        // Getters para los atributos
        public String getType() { return type; }
        public int getAddress() { return address; }

        @Override
        public String toString() {
            return "VariableInfo{type='" + type + "', address=" + address + '}';
        }
    }

    private Map<String, VariableInfo> variables; // Mapa para almacenar las variables por nombre

    /**
     * Constructor de VariableTable. Inicializa el mapa de variables.
     */
    public VariableTable() {
        this.variables = new HashMap<>();
    }

    /**
     * Agrega una nueva variable a la tabla.
     * @param name El nombre de la variable.
     * @param type El tipo de la variable.
     * @param address La dirección virtual asignada a la variable.
     */
    public void addVariable(String name, String type, int address) {
        if (variables.containsKey(name)) {
            // Error semántico: La variable ya existe en este ámbito
            System.err.println("Error semántico: La variable '" + name + "' ya ha sido declarada en este ámbito.");
        } else {
            variables.put(name, new VariableInfo(type, address));
        }
    }

    /**
     * Obtiene la información de una variable por su nombre.
     * @param name El nombre de la variable.
     * @return El objeto VariableInfo si la variable existe, o null si no se encuentra.
     */
    public VariableInfo getVariable(String name) {
        return variables.get(name);
    }

    /**
     * Verifica si una variable con el nombre dado existe en esta tabla.
     * @param name El nombre de la variable a verificar.
     * @return true si la variable existe, false en caso contrario.
     */
    public boolean variableExists(String name) {
        return variables.containsKey(name);
    }

    /**
     * Obtiene el mapa de variables de la tabla.
     * Este método es útil para depuración y pruebas.
     * @return El mapa de variables.
     */
    public Map<String, VariableInfo> getVariables() {
        return variables;
    }

    @Override
    public String toString() {
        return "VariableTable{" +
                "variables=" + variables +
                '}';
    }
    /**
     * Imprime el contenido completo de la tabla de variables para depuración.
     */
    public void printTable() {
        if (variables.isEmpty()) {
            System.out.println("  No variables declared in this table.");
            return;
        }
        for (Map.Entry<String, VariableInfo> entry : variables.entrySet()) {
            String varName = entry.getKey(); // <<--- ¡OBTIENE EL NOMBRE DE LA CLAVE DEL MAPA!
            VariableInfo varInfo = entry.getValue();
            System.out.println("  Name: " + varName + // <<--- ¡USA LA CLAVE DEL MAPA AQUÍ!
                    ", Type: " + varInfo.getType() +
                    ", Address: " + varInfo.getAddress());
        }
    }
}
