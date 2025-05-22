package BabyDuck;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase MemoryManager se encarga de asignar direcciones virtuales
 * únicas a variables, constantes y temporales en el contexto de un compilador.
 * Define rangos de memoria para diferentes tipos y ámbitos.
 */
public class MemoryManager {

    // --- Definición de Rangos de Memoria Virtual ---
    // Estos son ejemplos de bases de direcciones. En un compilador real,
    // se ajustarían según la arquitectura de la máquina virtual o el sistema.
    // Se recomienda que los rangos no se solapen.

    // Direcciones para variables Globales
    private static final int GLOBAL_INT_START = 1000;
    private static final int GLOBAL_FLOAT_START = 2000;
    private static final int GLOBAL_STRING_START = 3000;

    // Direcciones para variables Locales (dentro de funciones)
    private static final int LOCAL_INT_START = 4000;
    private static final int LOCAL_FLOAT_START = 5000;
    private static final int LOCAL_STRING_START = 6000;

    // Direcciones para variables Temporales (resultados intermedios de expresiones)
    private static final int TEMP_INT_START = 7000;
    private static final int TEMP_FLOAT_START = 8000;
    private static final int TEMP_BOOLEAN_START = 9000; // Para resultados de expresiones relacionales

    // Direcciones para Constantes
    private static final int CONST_INT_START = 10000;
    private static final int CONST_FLOAT_START = 11000;
    private static final int CONST_STRING_START = 12000;

    // --- Contadores para la Próxima Dirección Disponible ---
    // Estos contadores se incrementan cada vez que se asigna una nueva dirección.
    private int nextGlobalInt;
    private int nextGlobalFloat;
    private int nextGlobalString;

    private int nextLocalInt;
    private int nextLocalFloat;
    private int nextLocalString;

    private int nextTempInt;
    private int nextTempFloat;
    private int nextTempBoolean;

    private int nextConstInt;
    private int nextConstFloat;
    private int nextConstString;

    // --- Mapa para Almacenar Direcciones de Constantes ---
    // Esto permite reutilizar la misma dirección para constantes con el mismo valor.
    // Ejemplo: si "5" aparece varias veces, solo se le asigna una dirección.
    private Map<String, Integer> constAddresses; // Clave: valor de la constante (ej. "10", "3.14"), Valor: su dirección virtual

    /**
     * Constructor de MemoryManager. Inicializa todos los contadores
     * de dirección a sus valores de inicio.
     */
    public MemoryManager() {
        reset(); // Llama a reset para inicializar todos los contadores
    }

    /**
     * Reinicia todos los contadores de direcciones a sus valores base.
     * Útil para cuando se reinicia el proceso de compilación o para pruebas.
     */
    public void reset() {
        nextGlobalInt = GLOBAL_INT_START;
        nextGlobalFloat = GLOBAL_FLOAT_START;
        nextGlobalString = GLOBAL_STRING_START;

        nextLocalInt = LOCAL_INT_START;
        nextLocalFloat = LOCAL_FLOAT_START;
        nextLocalString = LOCAL_STRING_START;

        nextTempInt = TEMP_INT_START;
        nextTempFloat = TEMP_FLOAT_START;
        nextTempBoolean = TEMP_BOOLEAN_START;

        nextConstInt = CONST_INT_START;
        nextConstFloat = CONST_FLOAT_START;
        nextConstString = CONST_STRING_START;

        constAddresses = new HashMap<>(); // Se reinicia el mapa de constantes
    }

    /**
     * Asigna una dirección virtual para una variable (no temporal, no constante).
     * @param type El tipo de la variable (ej. "INT", "FLOAT", "STRING").
     * @param scope El ámbito de la variable (ej. "GLOBAL", "LOCAL").
     * @return La dirección virtual asignada.
     * @throws RuntimeException Si el tipo o ámbito proporcionado no es reconocido.
     */
    public int assignVariableAddress(String type, String scope) {
        switch (scope.toUpperCase()) {
            case "GLOBAL":
                switch (type.toUpperCase()) {
                    case "INT": return nextGlobalInt++;
                    case "FLOAT": return nextGlobalFloat++;
                    case "STRING": return nextGlobalString++;
                    default: throw new RuntimeException("Error en MemoryManager: Tipo de variable global desconocido: " + type);
                }
            case "LOCAL": // Para variables declaradas dentro de funciones
                switch (type.toUpperCase()) {
                    case "INT": return nextLocalInt++;
                    case "FLOAT": return nextLocalFloat++;
                    case "STRING": return nextLocalString++;
                    default: throw new RuntimeException("Error en MemoryManager: Tipo de variable local desconocido: " + type);
                }
            default: throw new RuntimeException("Error en MemoryManager: Ámbito de variable desconocido: " + scope);
        }
    }

    /**
     * Asigna una dirección virtual para una variable temporal (generada por el compilador).
     * @param type El tipo de la temporal (ej. "INT", "FLOAT", "BOOLEAN").
     * @return La dirección virtual asignada.
     * @throws RuntimeException Si el tipo temporal no es reconocido.
     */
    public int assignTempAddress(String type) {
        switch (type.toUpperCase()) {
            case "INT": return nextTempInt++;
            case "FLOAT": return nextTempFloat++;
            case "BOOLEAN": return nextTempBoolean++;
            default: throw new RuntimeException("Error en MemoryManager: Tipo de temporal desconocido: " + type);
        }
    }

    /**
     * Asigna una dirección virtual para una constante. Si el valor de la constante
     * ya ha sido asignado previamente, devuelve la dirección existente.
     * @param value El valor de la constante como String (ej. "10", "3.14", "\"hola\"").
     * @param type El tipo de la constante (ej. "INT", "FLOAT", "STRING").
     * @return La dirección virtual asignada o ya existente para esa constante.
     * @throws RuntimeException Si el tipo de constante no es reconocido.
     */
    public int assignConstantAddress(String value, String type) {
        // Primero, verifica si la constante ya tiene una dirección asignada
        if (constAddresses.containsKey(value)) {
            return constAddresses.get(value);
        }

        // Si no tiene, asigna una nueva dirección basada en su tipo
        int address;
        switch (type.toUpperCase()) {
            case "INT": address = nextConstInt++; break;
            case "FLOAT": address = nextConstFloat++; break;
            case "STRING": address = nextConstString++; break;
            default: throw new RuntimeException("Error en MemoryManager: Tipo de constante desconocido: " + type);
        }
        // Guarda la nueva dirección en el mapa para futuras referencias
        constAddresses.put(value, address);
        return address;
    }
}
