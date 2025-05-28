package BabyDuck;

import java.util.HashMap;
import java.util.Map;

public class MemoryManager {
    // Rangos de direcciones virtuales
    // Puedes ajustar estos rangos y sus límites según la escala esperada de tu programa BabyDuck
    // Asegúrate de que los rangos no se solapen
    private final int GLOBAL_INT_START = 1000;
    private final int GLOBAL_INT_END = 1999;
    private final int GLOBAL_FLOAT_START = 2000;
    private final int GLOBAL_FLOAT_END = 2999;
    private final int GLOBAL_STRING_START = 3000;
    private final int GLOBAL_STRING_END = 3999;
    private final int GLOBAL_BOOLEAN_START = 4000;
    private final int GLOBAL_BOOLEAN_END = 4999;

    private final int LOCAL_INT_START = 5000;
    private final int LOCAL_INT_END = 5999;
    private final int LOCAL_FLOAT_START = 6000;
    private final int LOCAL_FLOAT_END = 6999;
    private final int LOCAL_STRING_START = 7000;
    private final int LOCAL_STRING_END = 7999;
    private final int LOCAL_BOOLEAN_START = 8000;
    private final int LOCAL_BOOLEAN_END = 8999;

    private final int TEMP_INT_START = 9000;
    private final int TEMP_INT_END = 9999;
    private final int TEMP_FLOAT_START = 10000;
    private final int TEMP_FLOAT_END = 10999;
    private final int TEMP_STRING_START = 11000;
    private final int TEMP_STRING_END = 11999;
    private final int TEMP_BOOLEAN_START = 12000;
    private final int TEMP_BOOLEAN_END = 12999;

    private final int CONST_INT_START = 13000;
    private final int CONST_INT_END = 13999;
    private final int CONST_FLOAT_START = 14000;
    private final int CONST_FLOAT_END = 14999;
    private final int CONST_STRING_START = 15000;
    private final int CONST_STRING_END = 15999;
    private final int CONST_BOOLEAN_START = 16000;
    private final int CONST_BOOLEAN_END = 16999;

    // Contadores para la siguiente dirección disponible en cada segmento
    private int nextGlobalInt;
    private int nextGlobalFloat;
    private int nextGlobalString;
    private int nextGlobalBoolean;

    private int nextLocalInt;
    private int nextLocalFloat;
    private int nextLocalString;
    private int nextLocalBoolean;

    private int nextTempInt;
    private int nextTempFloat;
    private int nextTempString;
    private int nextTempBoolean;

    private int nextConstInt;
    private int nextConstFloat;
    private int nextConstString;
    private int nextConstBoolean;

    // Tabla para almacenar las constantes y su dirección (para evitar duplicados)
    private Map<Object, Integer> constantAddresses;

    public MemoryManager() {
        // Inicializar contadores con las direcciones de inicio
        nextGlobalInt = GLOBAL_INT_START;
        nextGlobalFloat = GLOBAL_FLOAT_START;
        nextGlobalString = GLOBAL_STRING_START;
        nextGlobalBoolean = GLOBAL_BOOLEAN_START;

        nextLocalInt = LOCAL_INT_START;
        nextLocalFloat = LOCAL_FLOAT_START;
        nextLocalString = LOCAL_STRING_START;
        nextLocalBoolean = LOCAL_BOOLEAN_START;

        nextTempInt = TEMP_INT_START;
        nextTempFloat = TEMP_FLOAT_START;
        nextTempString = TEMP_STRING_START;
        nextTempBoolean = TEMP_BOOLEAN_START;

        nextConstInt = CONST_INT_START;
        nextConstFloat = CONST_FLOAT_START;
        nextConstString = CONST_STRING_START;
        nextConstBoolean = CONST_BOOLEAN_START;

        constantAddresses = new HashMap<>();
        System.out.println("MM-DEBUG: MemoryManager inicializado.");
    }

    /**
     * Asigna una dirección para una variable (global o local).
     * @param type El tipo de la variable.
     * @param scope El ámbito de la variable ("GLOBAL" o "LOCAL").
     * @return La dirección virtual asignada.
     */
    public int assignVariableAddress(String type, String scope) {
        switch (scope.toUpperCase()) {
            case "GLOBAL":
                return assignGlobalAddress(type);
            case "LOCAL":
                return assignLocalAddress(type);
            default:
                throw new IllegalArgumentException("Ámbito desconocido para asignación de variable: " + scope);
        }
    }

    /**
     * Asigna una dirección para una variable global.
     * @param type El tipo de la variable.
     * @return La dirección virtual asignada.
     */
    public int assignGlobalAddress(String type) {
        int address;
        switch (type.toUpperCase()) {
            case "INT":
                if (nextGlobalInt > GLOBAL_INT_END) throw new RuntimeException("MemoryManager Error: Global INT memory exhausted.");
                address = nextGlobalInt++;
                break;
            case "FLOAT":
                if (nextGlobalFloat > GLOBAL_FLOAT_END) throw new RuntimeException("MemoryManager Error: Global FLOAT memory exhausted.");
                address = nextGlobalFloat++;
                break;
            case "STRING":
                if (nextGlobalString > GLOBAL_STRING_END) throw new RuntimeException("MemoryManager Error: Global STRING memory exhausted.");
                address = nextGlobalString++;
                break;
            case "BOOLEAN":
                if (nextGlobalBoolean > GLOBAL_BOOLEAN_END) throw new RuntimeException("MemoryManager Error: Global BOOLEAN memory exhausted.");
                address = nextGlobalBoolean++;
                break;
            default:
                throw new RuntimeException("MemoryManager Error: Tipo de dato no soportado para variables globales: " + type);
        }
        System.out.println("MM-DEBUG: Asignada global '" + type + "' en " + address);
        return address;
    }

    /**
     * Asigna una dirección para una variable local.
     * @param type El tipo de la variable.
     * @return La dirección virtual asignada.
     */
    public int assignLocalAddress(String type) {
        int address;
        switch (type.toUpperCase()) {
            case "INT":
                if (nextLocalInt > LOCAL_INT_END) throw new RuntimeException("MemoryManager Error: Local INT memory exhausted.");
                address = nextLocalInt++;
                break;
            case "FLOAT":
                if (nextLocalFloat > LOCAL_FLOAT_END) throw new RuntimeException("MemoryManager Error: Local FLOAT memory exhausted.");
                address = nextLocalFloat++;
                break;
            case "STRING":
                if (nextLocalString > LOCAL_STRING_END) throw new RuntimeException("MemoryManager Error: Local STRING memory exhausted.");
                address = nextLocalString++;
                break;
            case "BOOLEAN":
                if (nextLocalBoolean > LOCAL_BOOLEAN_END) throw new RuntimeException("MemoryManager Error: Local BOOLEAN memory exhausted.");
                address = nextLocalBoolean++;
                break;
            default:
                throw new RuntimeException("MemoryManager Error: Tipo de dato no soportado para variables locales: " + type);
        }
        System.out.println("MM-DEBUG: Asignada local '" + type + "' en " + address);
        return address;
    }

    /**
     * Asigna una dirección para una variable temporal (resultado de expresión).
     * @param type El tipo de la variable temporal.
     * @return La dirección virtual asignada.
     */
    public int assignTempAddress(String type) {
        int address;
        switch (type.toUpperCase()) {
            case "INT":
                if (nextTempInt > TEMP_INT_END) throw new RuntimeException("MemoryManager Error: Temp INT memory exhausted.");
                address = nextTempInt++;
                break;
            case "FLOAT":
                if (nextTempFloat > TEMP_FLOAT_END) throw new RuntimeException("MemoryManager Error: Temp FLOAT memory exhausted.");
                address = nextTempFloat++;
                break;
            case "STRING":
                if (nextTempString > TEMP_STRING_END) throw new RuntimeException("MemoryManager Error: Temp STRING memory exhausted.");
                address = nextTempString++;
                break;
            case "BOOLEAN":
                if (nextTempBoolean > TEMP_BOOLEAN_END) throw new RuntimeException("MemoryManager Error: Temp BOOLEAN memory exhausted.");
                address = nextTempBoolean++;
                break;
            default:
                throw new RuntimeException("MemoryManager Error: Tipo de dato no soportado para temporales: " + type);
        }
        System.out.println("MM-DEBUG: Asignada temporal '" + type + "' en " + address);
        return address;
    }

    /**
     * Asigna una dirección para una constante.
     * Si la constante ya ha sido asignada, devuelve su dirección existente.
     * @param value El valor de la constante (como Object para ser genérico).
     * @param type El tipo de la constante (ej. "INT", "FLOAT", "STRING", "BOOLEAN").
     * @return La dirección virtual asignada o existente.
     */
    public int assignConstantAddress(String value, String type) {
        // Usa el valor y tipo combinados como clave para evitar colisiones (ej. 5 como INT vs "5" como STRING)
        String constantKey = type.toUpperCase() + "_" + value;
        if (constantAddresses.containsKey(constantKey)) {
            return constantAddresses.get(constantKey);
        }

        int address;
        switch (type.toUpperCase()) {
            case "INT":
                if (nextConstInt > CONST_INT_END) throw new RuntimeException("MemoryManager Error: Constant INT memory exhausted.");
                address = nextConstInt++;
                break;
            case "FLOAT":
                if (nextConstFloat > CONST_FLOAT_END) throw new RuntimeException("MemoryManager Error: Constant FLOAT memory exhausted.");
                address = nextConstFloat++;
                break;
            case "STRING":
                if (nextConstString > CONST_STRING_END) throw new RuntimeException("MemoryManager Error: Constant STRING memory exhausted.");
                address = nextConstString++;
                break;
            case "BOOLEAN":
                if (nextConstBoolean > CONST_BOOLEAN_END) throw new RuntimeException("MemoryManager Error: Constant BOOLEAN memory exhausted.");
                address = nextConstBoolean++;
                break;
            default:
                throw new RuntimeException("MemoryManager Error: Tipo de dato no soportado para constantes: " + type);
        }
        constantAddresses.put(constantKey, address);
        System.out.println("MM-DEBUG: Asignada constante '" + value + "' (" + type + ") en " + address);
        return address;
    }

    /**
     * Reinicia los contadores de direcciones locales y temporales
     * al entrar en una nueva función. Esto permite reutilizar el espacio de memoria
     * para variables locales y temporales de diferentes funciones.
     */
    public void resetLocalAndTempCounters() {
        nextLocalInt = LOCAL_INT_START;
        nextLocalFloat = LOCAL_FLOAT_START;
        nextLocalString = LOCAL_STRING_START;
        nextLocalBoolean = LOCAL_BOOLEAN_START;

        nextTempInt = TEMP_INT_START;
        nextTempFloat = TEMP_FLOAT_START;
        nextTempString = TEMP_STRING_START;
        nextTempBoolean = TEMP_BOOLEAN_START;
        System.out.println("MM-DEBUG: Contadores locales y temporales reiniciados.");
    }
}