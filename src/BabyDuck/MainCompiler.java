package BabyDuck;

import estructuras_de_datos.Direccionador;
import maquina_virtual.MaquinaVirtual;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.file.Files;
import java.nio.file.Paths;

public class MainCompiler {
    public static void main(String[] args) throws Exception {
        String inputFile = "/Users/mafer/Documents/8vo/Aplicaciones Avanzadas/BBDuck/src/BabyDuck/finalTests/factorial_main.bdk";

        // Verificación y lectura de archivo
        System.out.println("Leyendo archivo desde: " + inputFile);
        if (!Files.exists(Paths.get(inputFile))) {
            System.err.println("ERROR: Archivo no encontrado.");
            return;
        }

        String content = new String(Files.readAllBytes(Paths.get(inputFile)));
        System.out.println("Contenido del archivo:\n" + content);

        // Preparar parser
        CharStream input = CharStreams.fromFileName(inputFile);
        BabyDuckLexer lexer = new BabyDuckLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BabyDuckParser parser = new BabyDuckParser(tokens);

        // Mostrar errores de sintaxis si ocurren
        parser.removeErrorListeners();
        parser.addErrorListener(new DiagnosticErrorListener());
        parser.addErrorListener(new ConsoleErrorListener());

        // Parsear el programa
        ParseTree tree = parser.programa();
        BabyDuckSemanticoVisitor visitor = new BabyDuckSemanticoVisitor();
        visitor.visit(tree);

        // Obtener cuadruplos y constantes
        var cuadruplos = visitor.getCuadruplos();
        var direccionador = visitor.getDireccionador();

        System.out.println("\n--- Cuádruplos generados ---");
        for (int i = 0; i < cuadruplos.size(); i++) {
            System.out.println(i + ": " + cuadruplos.get(i));
        }

        // Ejecutar con máquina virtual si hay algo que ejecutar
        if (!cuadruplos.isEmpty()) {
            System.out.println("\n--- Ejecución del programa ---");
            MaquinaVirtual maquina = new MaquinaVirtual(cuadruplos, direccionador.getConstantes());
            maquina.ejecutar();
        } else {
            System.out.println("\nNo se generaron cuádruplos.");
        }
    }

}