package estructuras_de_datos;

public class Cuadruplo {
    private final String operacion;
    private final String operando1;
    private final String operando2;
    private String resultado;

    public Cuadruplo(String operacion, String operando1, String operando2, String resultado) {
        this.operacion = operacion;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
    }

    public String getOperacion() {
        return operacion;
    }

    public String getOperando1() {
        return operando1;
    }

    public String getOperando2() {
        return operando2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "(" + operacion + ", " + operando1 + ", " + operando2 + ", " + resultado + ")";
    }
}