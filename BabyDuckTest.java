import org.antlr.v4.runtime.*;
import java.io.IOException;

public class BabyDuckTest {
    public static void main(String[] args) throws IOException {
        String codigoFuente = "program";
        ANTLRInputStream input = new ANTLRInputStream(codigoFuente);
        BabyDuckLexer lexer = new BabyDuckLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BabyDuckParser parser = new BabyDuckParser(tokens);

        // Imprimir tokens generados
        for (Token token : tokens.getTokens()) {
            if (token.getType() != Token.EOF) {
                System.out.println("Token: " + parser.getVocabulary().getDisplayName(token.getType()) + ", Texto: '" + token.getText() + "'");
            }
        }

        BabyDuckParser.ProgramaContext tree = parser.programa(); // Invoca la regla de inicio
        System.out.println("\nÁrbol de Sintaxis Abstracta (AST): " + tree.toStringTree(parser));

        // Manejar errores sintácticos
        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.err.println("\nSe encontraron errores de sintaxis.");
        } else {
            System.out.println("\nAnálisis sintáctico completado sin errores.");
        }
    }
}