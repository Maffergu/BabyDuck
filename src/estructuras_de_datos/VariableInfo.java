package estructuras_de_datos;
public class VariableInfo {
    private final String tipo;
    private final int direccion;

    public VariableInfo(String tipo, int direccion) {
        this.tipo = tipo;
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public int getDireccion() {
        return direccion;
    }
    @Override
    public String toString() {
        return "(" + tipo + ", " + direccion + ")";
    }
}

