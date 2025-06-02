package estructuras_de_datos;
import java.util.*;

public class DirectorioFunciones {
    private Map<String, FuncionInfo> funciones;

    public DirectorioFunciones() {
        funciones = new HashMap<>();
    }

    public void agregarFuncion(String nombre, FuncionInfo funcionInfo) {
        funciones.put(nombre, funcionInfo);
    }

    public FuncionInfo getFuncion(String nombre) {
        return funciones.get(nombre);
    }

    public boolean contieneFuncion(String nombre) {
        return funciones.containsKey(nombre);
    }

    @Override
    public String toString() {
        return funciones.toString();
    }
}