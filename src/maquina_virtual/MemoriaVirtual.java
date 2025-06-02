package maquina_virtual;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MemoriaVirtual {
    private final Map<Integer, Object> memoriaGlobal = new HashMap<>();
    private final Map<Integer, Object> memoriaConstantes = new HashMap<>();
    private final Stack<Map<Integer, Object>> pilaContextosLocales = new Stack<>();
    private final Stack<Map<Integer, Object>> pilaContextosTemporales = new Stack<>();

    // Crear nuevo contexto local con direcciones 3000–3099 inicializadas
    public void setContexto() {
        Map<Integer, Object> nuevoContexto = new HashMap<>();
        for (int i = 3000; i < 3100; i++) {
            nuevoContexto.put(i, 0);
        }
        pilaContextosLocales.push(nuevoContexto);
        //System.out.println("[DEBUG] Nuevo contexto local creado (3000–3099 inicializado).");
    }

    // Crear nuevo contexto temporal con direcciones 5000–5099 inicializadas
    public void setContextoTemporal() {
        Map<Integer, Object> nuevoContexto = new HashMap<>();
        for (int i = 5000; i < 5100; i++) {
            nuevoContexto.put(i, 0);
        }
        pilaContextosTemporales.push(nuevoContexto);
        //System.out.println("[DEBUG] Nuevo contexto temporal creado (5000–5099 inicializado).");
    }

    // Eliminar ambos contextos al terminar función
    public void eliminarContexto() {
        if (!pilaContextosLocales.isEmpty()) {
            pilaContextosLocales.pop();
        }
        if (!pilaContextosTemporales.isEmpty()) {
            pilaContextosTemporales.pop();
        }
    }

    // Asignar valor a una dirección
    public void put(int direccion, Object valor) {
        if (esDireccionLocal(direccion)) {
            if (pilaContextosLocales.isEmpty()) {
                throw new RuntimeException("No hay contexto local activo.");
            }
            pilaContextosLocales.peek().put(direccion, valor);
        } else if (esDireccionTemporal(direccion)) {
            if (pilaContextosTemporales.isEmpty()) {
                throw new RuntimeException("No hay contexto temporal activo.");
            }
            pilaContextosTemporales.peek().put(direccion, valor);
        } else if (esDireccionGlobal(direccion)) {
            memoriaGlobal.put(direccion, valor);
        } else {
            throw new RuntimeException("Dirección no válida para asignación: " + direccion);
        }
    }

    // Obtener valor de una dirección
    public Object obtenerValor(int direccion) {
        if (esDireccionLocal(direccion)) {
            if (pilaContextosLocales.isEmpty()) {
                throw new RuntimeException("No hay contexto local activo.");
            }
            Map<Integer, Object> contexto = pilaContextosLocales.peek();
            if (!contexto.containsKey(direccion)) {
                throw new RuntimeException("Dirección local no inicializada: " + direccion);
            }
            return contexto.get(direccion);
        } else if (esDireccionTemporal(direccion)) {
            if (pilaContextosTemporales.isEmpty()) {
                throw new RuntimeException("No hay contexto temporal activo.");
            }
            Map<Integer, Object> contexto = pilaContextosTemporales.peek();
            if (!contexto.containsKey(direccion)) {
                throw new RuntimeException("Dirección temporal no inicializada: " + direccion);
            }
            return contexto.get(direccion);
        } else if (esDireccionGlobal(direccion)) {
            if (!memoriaGlobal.containsKey(direccion)) {
                throw new RuntimeException("Dirección global no inicializada: " + direccion);
            }
            return memoriaGlobal.get(direccion);
        } else if (esDireccionConstante(direccion)) {
            if (!memoriaConstantes.containsKey(direccion)) {
                throw new RuntimeException("Constante no inicializada: " + direccion);
            }
            return memoriaConstantes.get(direccion);
        }

        throw new RuntimeException("Dirección desconocida: " + direccion);
    }

    // Cargar constante (solo se hace una vez al inicio)
    public void cargarConstante(int direccion, Object valor) {
        memoriaConstantes.put(direccion, valor);
    }

    public Object obtenerValorTemporal(String nombreParam) {
        if (nombreParam.startsWith("param")) {
            int indice = Integer.parseInt(nombreParam.substring(5));
            int direccion = 3000 + indice;
            return obtenerValor(direccion);
        }
        throw new RuntimeException("Parámetro no reconocido: " + nombreParam);
    }

    // Métodos auxiliares para verificar el tipo de dirección
    private boolean esDireccionLocal(int direccion) {
        return direccion >= 3000 && direccion < 5000;
    }

    private boolean esDireccionTemporal(int direccion) {
        return direccion >= 5000 && direccion < 7000;
    }

    private boolean esDireccionGlobal(int direccion) {
        return direccion >= 1000 && direccion < 3000;
    }

    private boolean esDireccionConstante(int direccion) {
        return direccion >= 9000;
    }

    // Mostrar estado de la memoria (útil para depurar)
    public void printMemoria() {
        System.out.println("Memoria Global: " + memoriaGlobal);
        System.out.println("Memoria Constantes: " + memoriaConstantes);
        System.out.println("Memoria Local Actual: " + (pilaContextosLocales.isEmpty() ? "{}" : pilaContextosLocales.peek()));
        System.out.println("Memoria Temporal Actual: " + (pilaContextosTemporales.isEmpty() ? "{}" : pilaContextosTemporales.peek()));
    }
}