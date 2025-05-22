package BabyDuck; // Asegúrate de que este sea el paquete correcto de tu proyecto

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La clase FunctionDirectory gestiona la información de todas las funciones
 * declaradas en el programa BabyDuck.
 */
public class FunctionDirectory {

    /**
     * Clase interna FunctionInfo: Contiene los detalles de una función.
     * Ahora incluye la dirección de inicio del código de la función.
     */
    public static class FunctionInfo {
        private String returnType; // Tipo de retorno de la función (ej. "INT", "FLOAT", "VOID")
        private List<ParameterInfo> parameters; // Lista de parámetros de la función (opcional, si los manejas aquí)
        private VariableTable localVariables; // Tabla de variables locales de la función
        private int startAddress; // Dirección de memoria virtual donde inicia el código de la función

        /**
         * Constructor para FunctionInfo.
         * @param returnType El tipo de retorno de la función.
         * @param startAddress La dirección de inicio del código de la función.
         */
        public FunctionInfo(String returnType, int startAddress) {
            this.returnType = returnType;
            this.startAddress = startAddress;
            this.localVariables = new VariableTable(); // Cada función tiene su propia tabla de variables locales
            this.parameters = new java.util.ArrayList<>(); // Inicializa la lista de parámetros
        }

        // Getters para los atributos
        public String getReturnType() { return returnType; }
        public List<ParameterInfo> getParameters() { return parameters; }
        public VariableTable getLocalVariables() { return localVariables; }
        public int getStartAddress() { return startAddress; }

        // Puedes añadir un método para añadir parámetros si los procesas aquí
        public void addParameter(String name, String type) {
            this.parameters.add(new ParameterInfo(name, type));
        }

        @Override
        public String toString() {
            return "FunctionInfo{" +
                    "returnType='" + returnType + '\'' +
                    ", startAddress=" + startAddress +
                    ", localVariables=" + localVariables + // Puede ser muy verboso si la tabla es grande
                    ", parameters=" + parameters +
                    '}';
        }
    }

    /**
     * Clase interna ParameterInfo: Representa la información de un parámetro de función.
     */
    public static class ParameterInfo {
        private String name;
        private String type;

        public ParameterInfo(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() { return name; }
        public String getType() { return type; }

        @Override
        public String toString() {
            return "ParameterInfo{name='" + name + "', type='" + type + "'}";
        }
    }

    private Map<String, FunctionInfo> functions; // Mapa para almacenar las funciones por nombre

    /**
     * Constructor de FunctionDirectory. Inicializa el mapa de funciones.
     */
    public FunctionDirectory() {
        this.functions = new HashMap<>();
    }

    /**
     * Agrega una nueva función al directorio.
     * @param name El nombre de la función.
     * @param returnType El tipo de retorno de la función.
     * @param startAddress La dirección de inicio del código de la función.
     */
    public void addFunction(String name, String returnType, int startAddress) {
        if (functions.containsKey(name)) {
            // Error semántico: La función ya existe
            System.err.println("Error semántico: La función '" + name + "' ya ha sido declarada.");
        } else {
            functions.put(name, new FunctionInfo(returnType, startAddress));
        }
    }

    /**
     * Obtiene la información de una función por su nombre.
     * @param name El nombre de la función.
     * @return El objeto FunctionInfo si la función existe, o null si no se encuentra.
     */
    public FunctionInfo getFunction(String name) {
        return functions.get(name);
    }

    /**
     * Verifica si una función con el nombre dado existe en este directorio.
     * @param name El nombre de la función a verificar.
     * @return true si la función existe, false en caso contrario.
     */
    public boolean functionExists(String name) {
        return functions.containsKey(name);
    }

    @Override
    public String toString() {
        return "FunctionDirectory{" +
                "functions=" + functions +
                '}';
    }
}