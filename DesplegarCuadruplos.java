package BabyDuck;

import java.util.Queue;

public class DesplegarCuadruplos {

    /**
     * Despliega los cuádruplos generados por el compilador de Baby Duck.
     * Este método recibe una fila de cuádruplos y los imprime en la consola.
     * @param filaCuadruplos La fila que contiene los cuádruplos a desplegar.
     */
    public static void desplegarCuadruplos(Queue<EstructurasDeDatos.Cuadruplo> filaCuadruplos) {
        if (filaCuadruplos == null || filaCuadruplos.isEmpty()) {
            System.out.println("No hay cuádruplos para desplegar.");
            return;
        }

        System.out.println("-----------------------------------");
        System.out.println("Cuádruplos Generados:");
        System.out.println("-----------------------------------");
        for (EstructurasDeDatos.Cuadruplo cuadruplo : filaCuadruplos) {
            System.out.println(cuadruplo);
        }
        System.out.println("-----------------------------------");
    }

    public static void main(String[] args) {
        // Ejemplo de uso:

        // *** Test Case 1: Cuádruplos Generados ***
        System.out.println("\n--- Test Case 1: Cuádruplos Generados ---");
        // 1. Crear una instancia de EstructurasDeDatos (o tu clase donde generas los cuádruplos).
        EstructurasDeDatos estructuras = new EstructurasDeDatos();

        // 2. Generar algunos cuádruplos de prueba (simulando la compilación de un programa de Baby Duck).
        estructuras.agregarCuadruplo("+", "a", "b", "temp1");
        estructuras.agregarCuadruplo("*", "temp1", "c", "temp2");
        estructuras.agregarCuadruplo("=", "temp2", null, "x");
        estructuras.agregarCuadruplo(">", "a", "b", "temp3");
        estructuras.agregarCuadruplo("=", "temp3", null, "y");

        // 3. Obtener la fila de cuádruplos generados.
        Queue<EstructurasDeDatos.Cuadruplo> cuadruplosGenerados = estructuras.getCuadruplos();

        // 4. Desplegar los cuádruplos usando el método de la clase DesplegarCuadruplos.
        DesplegarCuadruplos.desplegarCuadruplos(cuadruplosGenerados);


        // *** Test Case 2: No hay cuádruplos ***
        System.out.println("\n--- Test Case 2: No hay cuádruplos ---");
        EstructurasDeDatos estructuras2 = new EstructurasDeDatos();
        Queue<EstructurasDeDatos.Cuadruplo> cuadruplosVacios = estructuras2.getCuadruplos();
        DesplegarCuadruplos.desplegarCuadruplos(cuadruplosVacios);
    }
}
