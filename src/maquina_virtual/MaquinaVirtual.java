package maquina_virtual;

import estructuras_de_datos.Cuadruplo;

import java.util.*;

public class MaquinaVirtual {
    private final List<Cuadruplo> cuadruplos;
    private final MemoriaVirtual memoria;
    private final Stack<Integer> pilaRetornos = new Stack<>();

    public MaquinaVirtual(List<Cuadruplo> cuadruplos, Map<Integer, Object> constantes) {
        this.cuadruplos = cuadruplos;
        this.memoria = new MemoriaVirtual();

        // Cargar constantes
        for (Map.Entry<Integer, Object> entry : constantes.entrySet()) {
            memoria.cargarConstante(entry.getKey(), entry.getValue());
        }
    }

    public void ejecutar() {
        memoria.setContexto();          // Activa contexto global
        memoria.setContextoTemporal();  // Activa contexto temporal global
        int ip = buscarInicioFuncion("main");

        while (ip < cuadruplos.size()) {
            Cuadruplo cuad = cuadruplos.get(ip);
            String op = cuad.getOperacion();
            String arg1 = cuad.getOperando1();
            String arg2 = cuad.getOperando2();
            String res = cuad.getResultado();

            //System.out.println("Ejecutando cuádruplo en IP=" + ip + ": " + cuad);

            switch (op) {
                case "=":
                    Object val;
                    try {
                        val = memoria.obtenerValor(Integer.parseInt(arg1));
                    } catch (NumberFormatException e) {
                        if (arg1.startsWith("param")) {
                            int indice = Integer.parseInt(arg1.substring(5));
                            val = memoria.obtenerValor(3000 + indice);
                        } else {
                            throw new RuntimeException("Dirección o parámetro desconocido: " + arg1);
                        }
                    }
                    memoria.put(Integer.parseInt(res), val);
                    break;

                case "+":
                case "-":
                case "*":
                case "/":
                    operarAritmeticamente(op, arg1, arg2, res);
                    break;

                case "<":
                case ">":
                case "!=":
                case "<=":
                case ">=":
                    operarRelacionalmente(op, arg1, arg2, res);
                    break;

                case "PRINT":
                    Object valPrint = memoria.obtenerValor(Integer.parseInt(arg1));
                    if (valPrint instanceof Number) {
                        double num = ((Number) valPrint).doubleValue();
                        if (num % 1 == 0) System.out.println((int) num);
                        else System.out.println(num);
                    } else {
                        System.out.println(valPrint);
                    }
                    break;

                case "GOTO":
                    ip = Integer.parseInt(res);
                    continue;

                case "GOTOF":
                    Object cond = memoria.obtenerValor(Integer.parseInt(arg1));
                    if (((Number) cond).intValue() == 0) {
                        ip = Integer.parseInt(res);
                        continue;
                    }
                    break;

                case "ERA":
                    memoria.setContexto();          // Prepara contexto local
                    memoria.setContextoTemporal();  // Prepara contexto temporal
                    //System.out.println("[DEBUG ERA] Nuevo contexto local/temporal creado.");
                    break;

                case "PARAM":
                    int dirOrigen = Integer.parseInt(arg1);
                    int dirDestino;
                    if (res.startsWith("param")) {
                        int paramIndex = Integer.parseInt(res.substring(5));
                        dirDestino = 3000 + paramIndex;
                    } else {
                        dirDestino = Integer.parseInt(res);
                    }

                    Object valor = memoria.obtenerValor(dirOrigen);
                    memoria.put(dirDestino, valor);
                    break;

                case "GOSUB":
                    pilaRetornos.push(ip + 1);  // Guarda el punto de retorno
                    ip = buscarInicioFuncion(res);  // Salta al inicio de la función
                    continue;

                case "ENDFUNC":
                    memoria.eliminarContexto();  // Elimina contexto local
                    if (!pilaRetornos.isEmpty()) {
                        ip = pilaRetornos.pop();  // Regresa del GOSUB
                        continue;
                    } else {
                        //System.out.println("[DEBUG ENDFUNC] No hay más IPs a los que regresar. Fin del programa.");
                        return;
                    }

                default:
                    throw new RuntimeException("Operación desconocida: " + op);
            }

            ip++;
        }
    }

    private void operarAritmeticamente(String op, String arg1, String arg2, String res) {
        double val1 = obtenerNumero(Integer.parseInt(arg1));
        double val2 = obtenerNumero(Integer.parseInt(arg2));
        double resultado;

        switch (op) {
            case "+": resultado = val1 + val2; break;
            case "-": resultado = val1 - val2; break;
            case "*": resultado = val1 * val2; break;
            case "/": resultado = val1 / val2; break;
            default: throw new RuntimeException("Operador aritmético inválido: " + op);
        }

        memoria.put(Integer.parseInt(res), resultado);
    }

    private void operarRelacionalmente(String op, String arg1, String arg2, String res) {
        double val1 = obtenerNumero(Integer.parseInt(arg1));
        double val2 = obtenerNumero(Integer.parseInt(arg2));
        boolean resultado;

        switch (op) {
            case "<": resultado = val1 < val2; break;
            case ">": resultado = val1 > val2; break;
            case "!=": resultado = val1 != val2; break;
            case "<=": resultado = val1 <= val2; break;
            case ">=": resultado = val1 >= val2; break;
            default: throw new RuntimeException("Operador relacional inválido: " + op);
        }

        memoria.put(Integer.parseInt(res), resultado ? 1 : 0);
    }

    private double obtenerNumero(int direccion) {
        Object valor = memoria.obtenerValor(direccion);
        if (valor instanceof Number) {
            return ((Number) valor).doubleValue();
        }
        throw new RuntimeException("Valor no numérico en la dirección: " + direccion);
    }

    private int buscarInicioFuncion(String nombreFuncion) {
        for (int i = 0; i < cuadruplos.size(); i++) {
            Cuadruplo cuad = cuadruplos.get(i);
            if (cuad.getOperacion().equals("BEGINFUNC") && cuad.getResultado().equals(nombreFuncion)) {
                return i + 1;
            }
        }
        throw new RuntimeException("No se encontró el inicio de la función: " + nombreFuncion);
    }

    public void cargarConstante(int direccion, Object valor) {
        memoria.cargarConstante(direccion, valor);
    }
}