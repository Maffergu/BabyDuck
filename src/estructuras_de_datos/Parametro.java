package estructuras_de_datos;

public class Parametro {
    private String nombre;
    private String tipo;

    public Parametro(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "(" + nombre + ": " + tipo + ")";
    }
}
