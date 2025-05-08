import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionDirectory {

    public static class FunctionInfo {
        private String returnType; // Tipo de retorno de la función (INT, FLOAT, VOID)
        private List<ParameterInfo> parameters; // Lista de parámetros de la función
        private VariableTable localVariables; // Tabla de variables locales de la función
        private int address; // Dirección de memoria donde inicia la función (para la generación de código)

        public FunctionInfo(String returnType, List<ParameterInfo> parameters) {
            this.returnType = returnType;
            this.parameters = parameters;
            this.localVariables = new VariableTable();
            this.address = -1; // Inicialmente no asignada
        }

        // Getters y setters para los atributos
        public String getReturnType() {
            return returnType;
        }

        public List<ParameterInfo> getParameters() {
            return parameters;
        }

        public VariableTable getLocalVariables() {
            return localVariables;
        }

        public int getAddress() {
            return address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "FunctionInfo{" +
                    "returnType='" + returnType + '\'' +
                    ", parameters=" + parameters +
                    ", localVariables=" + localVariables +
                    ", address=" + address +
                    '}';
        }
    }

    public static class ParameterInfo {
        private String name;
        private String type;

        public ParameterInfo(String name, String type) {
            this.name = name;
            this.type = type;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "ParameterInfo{" +
                    "name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    private Map<String, FunctionInfo> functions;

    public FunctionDirectory() {
        this.functions = new HashMap<>();
        // Añadir la función principal (main) al directorio
        this.functions.put("main", new FunctionInfo("VOID", new java.util.ArrayList<>()));
    }

    public void addFunction(String name, String returnType, List<ParameterInfo> parameters) {
        if (functions.containsKey(name)) {
            // Error: La función ya existe
            System.err.println("Error semántico: La función '" + name + "' ya ha sido declarada.");
        } else {
            functions.put(name, new FunctionInfo(returnType, parameters));
        }
    }

    public FunctionInfo getFunction(String name) {
        return functions.get(name);
    }

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