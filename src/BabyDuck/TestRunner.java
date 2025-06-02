package BabyDuck;

import estructuras_de_datos.DirectorioFunciones;
import estructuras_de_datos.FuncionInfo;
import estructuras_de_datos.VariableInfo;
import org.antlr.v4.runtime.*;

import java.io.IOException;

import java.io.File;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) throws IOException {
        File testFolder = new File("src/BabyDuck/Tests");

        if (!testFolder.exists() || !testFolder.isDirectory()) {
            System.err.println("Carpeta 'tests' no encontrada.");
            return;
        }

        File[] testFiles = testFolder.listFiles((dir, name) -> name.endsWith(".patito"));
        if (testFiles == null || testFiles.length == 0) {
            System.out.println("No se encontraron archivos .patito en la carpeta.");
            return;
        }

        int passed = 0;
        int failed = 0;

        for (File file : testFiles) {
            System.out.println("Probando archivo: " + file.getName());
            try {
                CharStream input = CharStreams.fromFileName(file.getAbsolutePath());
                BabyDuckLexer lexer = new BabyDuckLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                BabyDuckParser parser = new BabyDuckParser(tokens);

                parser.removeErrorListeners();
                parser.setErrorHandler(new BailErrorStrategy()); // Detiene el parser al primer error

                parser.addErrorListener(new BaseErrorListener() {
                    @Override
                    public void syntaxError(Recognizer<?, ?> recognizer,
                                            Object offendingSymbol,
                                            int line, int charPositionInLine,
                                            String msg,
                                            RecognitionException e) {
                        throw new RuntimeException("Línea " + line + ":" + charPositionInLine + " " + msg);
                    }
                });


                parser.programa(); // punto de entrada

                System.out.println("Válido: " + file.getName() + "\n");
                passed++;
            } catch (Exception e) {
                System.err.println("Error: " + file.getName());
                System.err.println(e.getMessage() + "\n");
                failed++;
            }
        }

        System.out.println("RESULTADO FINAL:");
        System.out.println("Archivos válidos: " + passed);
        System.out.println("Archivos con error: " + failed);
    }
}