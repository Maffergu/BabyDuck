package estructuras_de_datos;

import java.util.HashMap;
import java.util.Map;

public class Direccionador {

    private int contadorGlobalInt = 1000;
    private int contadorGlobalFloat = 2000;
    private int contadorLocalInt = 3000;         // variables locales
    private int contadorParametro = 3500;     // parámetros
    private int contadorLocalFloat = 4000;
    private int contadorTemporalInt = 5000;
    private int contadorTemporalFloat = 6000;
    private int contadorConstantesInt = 9000;
    private int contadorConstantesFloat = 9100;
    private int contadorConstantesString = 9200;

    private final Map<Integer, Object> constantes = new HashMap<>();

    public int asignarDireccion(String tipo, String ambito) {
        if (ambito.equals("global")) {
            return asignarGlobal(tipo);
        } else if (ambito.equals("local")) {
            return asignarLocal(tipo);
        } else {
            throw new RuntimeException("Ámbito no reconocido: " + ambito);
        }
    }

    private int asignarGlobal(String tipo) {
        switch (tipo) {
            case "int":
                return contadorGlobalInt++;
            case "float":
                return contadorGlobalFloat++;
            default:
                throw new RuntimeException("Tipo no reconocido para global: " + tipo);
        }
    }

    private int asignarLocal(String tipo) {
        switch (tipo) {
            case "int":
                return contadorLocalInt++;
            case "float":
                return contadorLocalFloat++;
            default:
                throw new RuntimeException("Tipo no reconocido para local: " + tipo);
        }
    }

    public int nuevaTemporal(String tipo) {
        switch (tipo) {
            case "int":
                return contadorTemporalInt++;
            case "float":
                return contadorTemporalFloat++;
            default:
                throw new RuntimeException("Tipo no reconocido para temporal: " + tipo);
        }
    }

    public int direccionConstante(String valor, String tipo) {
        Object constante;
        int direccion;

        switch (tipo) {
            case "int":
                constante = Integer.parseInt(valor);
                direccion = contadorConstantesInt++;
                break;
            case "float":
                constante = Float.parseFloat(valor);
                direccion = contadorConstantesFloat++;
                break;
            case "string":
                constante = valor.substring(1, valor.length() - 1); // quita las comillas
                direccion = contadorConstantesString++;
                break;
            default:
                throw new RuntimeException("Tipo de constante no reconocido: " + tipo);
        }

        constantes.put(direccion, constante);
        return direccion;
    }

    public Map<Integer, Object> getConstantes() {
        return constantes;
    }

    public int direccionParametro(int index) {
        return 3000 + index;  // Esto alinea con la lógica en MaquinaVirtual y MemoriaVirtual
    }
    public int direccionParametro(String tipo) {
        int direccion = contadorParametro;
        contadorParametro++;
        return direccion;
    }
}