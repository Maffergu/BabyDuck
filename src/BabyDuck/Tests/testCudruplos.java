package BabyDuck.Tests;

import maquina_virtual.MaquinaVirtual;
import estructuras_de_datos.Cuadruplo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class testCudruplos {
    public static void main(String[] args) {
        List<Cuadruplo> cuadruplos = new ArrayList<>();
        MaquinaVirtual vm = new MaquinaVirtual(cuadruplos, new HashMap<>());

        // Direcciones simuladas
        int dir3 = 5000;
        int dir5 = 5001;
        int a = 3000;
        int b = 3001;
        int res = 3002;

        // Cargar constantes
        vm.cargarConstante(dir3, 3);
        vm.cargarConstante(dir5, 5);

        // main
        cuadruplos.add(new Cuadruplo("ERA", "", "", ""));
        cuadruplos.add(new Cuadruplo("PARAM", String.valueOf(dir3), "", String.valueOf(a)));
        cuadruplos.add(new Cuadruplo("PARAM", String.valueOf(dir5), "", String.valueOf(b)));
        cuadruplos.add(new Cuadruplo("GOSUB", "", "", "5"));
        cuadruplos.add(new Cuadruplo("GOTO", "", "", "9"));

        // función suma inicia en línea 5
        cuadruplos.add(new Cuadruplo("+", String.valueOf(a), String.valueOf(b), String.valueOf(res))); // 5
        cuadruplos.add(new Cuadruplo("PRINT", String.valueOf(res), "", "")); // 6
        cuadruplos.add(new Cuadruplo("ENDFUNC", "", "", "")); // 7

        // dummy (final)
        cuadruplos.add(new Cuadruplo("GOTO", "", "", "9")); // 8
        cuadruplos.add(new Cuadruplo("GOTO", "", "", "9")); // 9

        vm.ejecutar();
    }
}