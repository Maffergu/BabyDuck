package estructuras_de_datos;

import java.util.LinkedList;
import java.util.List;

public class Cuadruplos {
    private final List<Cuadruplo> lista;
    private int tempCounter = 0;

    public Cuadruplos() {
        this.lista = new LinkedList<>();
    }

    public void agregar(String operador, String operandoIzq, String operandoDer, String resultado) {
        lista.add(new Cuadruplo(operador, operandoIzq, operandoDer, resultado));
    }

    public List<Cuadruplo> obtenerLista() {
        return lista;
    }

    public void imprimir() {
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + ": " + lista.get(i));
        }
    }

    public String nuevaTemporal() {
        return "t" + (tempCounter++);
    }
}
