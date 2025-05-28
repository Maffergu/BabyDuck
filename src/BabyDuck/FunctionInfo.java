package BabyDuck;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// ¡ASEGÚRATE DE QUE ESTA LÍNEA ESTÉ PRESENTE!
import BabyDuck.FunctionDirectory.ParameterInfo;

public class FunctionInfo {
    private String name;
    private String returnType;
    private int startQuadruple; // Cuádruplo donde empieza la función
    private VariableTable localVariables;
    private List<ParameterInfo> parameters; // Este es el campo correcto
    private int numParams; // Cantidad de parámetros
    private int localIntCount = 0;
    private int localFloatCount = 0;
    private int localBoolCount = 0;
    private int localStringCount = 0;
    private int tempIntCount = 0;
    private int tempFloatCount = 0;
    private int tempBoolCount = 0;
    private int tempStringCount = 0;

    // Constructor que acepta una lista de String (tipos de parámetros) para la creación inicial
    public FunctionInfo(String name, String returnType, int startQuadruple, List<String> parameterTypes) {
        this.name = name;
        this.returnType = returnType;
        this.startQuadruple = startQuadruple;
        this.localVariables = new VariableTable();
        this.parameters = new ArrayList<>(); // Inicializa la lista de ParameterInfo
        // CORRECCIÓN AQUÍ: Crea un nuevo ParameterInfo al añadir
        if (parameterTypes != null) {
            for (int i = 0; i < parameterTypes.size(); i++) {
                this.parameters.add(new ParameterInfo("param_" + i, parameterTypes.get(i)));
            }
        }
        this.numParams = this.parameters.size();
    }

    public String getName() { return name; }
    public String getReturnType() { return returnType; }
    public int getStartQuadruple() { return startQuadruple; }
    public VariableTable getLocalVariables() { return localVariables; }
    public void setLocalVariables(VariableTable localVariables) {
        this.localVariables = localVariables;
    }

    // ¡ESTE ES EL MÉTODO QUE TU FunctionInfo NECESITA!
    public void addParameter(String name, String type) {
        this.parameters.add(new ParameterInfo(name, type));
        this.numParams = this.parameters.size(); // Actualiza el conteo de parámetros
    }

    // Getter para la lista de ParameterInfo
    public List<ParameterInfo> getParameters() {
        return parameters;
    }

    public int getNumParams() { return numParams; }

    public void incrementLocalCount(String type) {
        switch (type) {
            case "INT": localIntCount++; break;
            case "FLOAT": localFloatCount++; break;
            case "BOOLEAN": localBoolCount++; break;
            case "STRING": localStringCount++; break;
        }
    }

    public void incrementTempCount(String type) {
        switch (type) {
            case "INT": tempIntCount++; break;
            case "FLOAT": tempFloatCount++; break;
            case "BOOLEAN": tempBoolCount++; break;
            case "STRING": tempStringCount++; break;
        }
    }

    // Getters para los contadores
    public int getLocalIntCount() { return localIntCount; }
    public int getLocalFloatCount() { return localFloatCount; }
    public int getLocalBoolCount() { return localBoolCount; }
    public int getLocalStringCount() { return localStringCount; }
    public int getTempIntCount() { return tempIntCount; }
    public int getTempFloatCount() { return tempFloatCount; }
    public int getTempBoolCount() { return tempBoolCount; }
    public int getTempStringCount() { return tempStringCount; }


    @Override
    public String toString() {
        StringBuilder paramStr = new StringBuilder();
        for (ParameterInfo p : parameters) {
            paramStr.append(p.getType()).append(" ").append(p.getName()).append(", ");
        }
        if (paramStr.length() > 0) {
            paramStr.setLength(paramStr.length() - 2); // Remover la última ", "
        }

        return "  Name: " + name + "\n" +
                "  Return Type: " + returnType + "\n" +
                "  Start Quad: " + startQuadruple + "\n" +
                "  Parameters: [" + paramStr.toString() + "]\n" +
                "  Local Variables: " + localVariables.getVariables().keySet() + "\n" +
                "  Memory Counts: [L_INT:" + localIntCount + ", L_FLOAT:" + localFloatCount + ", L_BOOL:" + localBoolCount + ", L_STR:" + localStringCount +
                ", T_INT:" + tempIntCount + ", T_FLOAT:" + tempFloatCount + ", T_BOOL:" + tempBoolCount + ", T_STR:" + tempStringCount + "]";
    }
}