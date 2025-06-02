package BabyDuck;

import estructuras_de_datos.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class BabyDuckSemanticoVisitor extends BabyDuckBaseVisitor<Object> {
    private final DirectorioFunciones directorio = new DirectorioFunciones();
    private FuncionInfo funcionActual;
    private final Direccionador direccionador = new Direccionador();
    private final CuboSemantico cubo = new CuboSemantico();
    private final Stack<String> pilaOperandos = new Stack<>();
    private final Stack<String> pilaOperadores = new Stack<>();
    private final Stack<String> pilaTipos = new Stack<>();
    private final List<Cuadruplo> cuadruplos = new ArrayList<>();
    private final Stack<Integer> pilaSaltos = new Stack<>();

    @Override
    public Object visitPrograma(BabyDuckParser.ProgramaContext ctx) {
        // Crear función 'global' para variables globales
        FuncionInfo globalFunc = new FuncionInfo("void", -1);
        globalFunc.setVariableTable(new VariableTable());
        directorio.agregarFuncion("global", globalFunc);
        funcionActual = globalFunc;

        visit(ctx.vars());

        // Fase 1: Registrar funciones
        for (BabyDuckParser.FuncContext funcCtx : ctx.funcs().func()) {
            String nombreFuncion = funcCtx.ID(0).getText();
            if (directorio.contieneFuncion(nombreFuncion)) {
                throw new SemanticException("La función '" + nombreFuncion + "' ya fue declarada.");
            }
            FuncionInfo f = new FuncionInfo("void", -1);
            f.setVariableTable(new VariableTable());
            directorio.agregarFuncion(nombreFuncion, f);
        }

        // Visitar funciones declaradas
        visit(ctx.funcs());

        // Preparar función main
        FuncionInfo mainFunc = new FuncionInfo("void", -1);
        mainFunc.setVariableTable(new VariableTable());
        directorio.agregarFuncion("main", mainFunc);
        funcionActual = mainFunc;

        // 🔧 Cuádruplos para main
        cuadruplos.add(new Cuadruplo("BEGINFUNC", "-", "-", "main"));
        visit(ctx.body());
        cuadruplos.add(new Cuadruplo("ENDFUNC", "-", "-", "main"));

        // Mostrar cuádruplos
        System.out.println("\n--- Cuádruplos generados ---");
        for (int i = 0; i < cuadruplos.size(); i++) {
            System.out.println(i + ": " + cuadruplos.get(i));
        }

        return null;
    }

    @Override
    public Object visitVars(BabyDuckParser.VarsContext ctx) {
        for (BabyDuckParser.IdListContext idListCtx : ctx.idList()) {
            String tipo = ctx.type(ctx.idList().indexOf(idListCtx)).getText();
            for (TerminalNode idNode : idListCtx.ID()) {
                String nombre = idNode.getText();
                String ambito = funcionActual == directorio.getFuncion("global") ? "global" : "local";
                funcionActual.getVariableTable().agregarVariable(nombre, tipo, direccionador, ambito);
                //System.out.println("[DEBUG VAR] Declarada " + nombre + " con tipo " + tipo + " en ámbito " + ambito);
            }
        }
        return null;
    }
    @Override
    public Object visitBody(BabyDuckParser.BodyContext ctx) {
        for (BabyDuckParser.StatementContext stmt : ctx.statement()) {
            visit(stmt);
        }
        return null;
    }

    @Override
    public Object visitFunc(BabyDuckParser.FuncContext ctx) {
        String nombreFuncion = ctx.ID(0).getText();
        FuncionInfo nuevaFuncion = directorio.getFuncion(nombreFuncion);
        nuevaFuncion.setVariableTable(new VariableTable());
        funcionActual = nuevaFuncion;

        // Marca inicio de la función
        cuadruplos.add(new Cuadruplo("BEGINFUNC", "-", "-", nombreFuncion));

        int numParametros = ctx.ID().size() - 1;

        for (int i = 0; i < numParametros; i++) {
            String paramName = ctx.ID(i + 1).getText();
            String paramType = ctx.type(i).getText();

            nuevaFuncion.agregarParametro(new ParameterInfo(paramName, paramType));


            int direccionParametro = direccionador.direccionParametro(paramType);
            nuevaFuncion.getVariableTable().agregarVariable(paramName, paramType, direccionParametro);

            // Cuádruplo para mover param{i} → dirección asignada
            cuadruplos.add(new Cuadruplo("=", "param" + i, "-", String.valueOf(direccionParametro)));
        }

        visit(ctx.vars());  // Variables locales
        visit(ctx.body());  // Cuerpo de la función

        // Marca fin de la función
        cuadruplos.add(new Cuadruplo("ENDFUNC", "-", "-", nombreFuncion));
        return null;
    }
    @Override
    public Object visitAssign(BabyDuckParser.AssignContext ctx) {
        String varName = ctx.ID().getText();

        VariableInfo varInfo = funcionActual.getVariableTable().getVariable(varName);
        if (varInfo == null) {
            FuncionInfo globalFunc = directorio.getFuncion("global");
            if (globalFunc != null) {
                varInfo = globalFunc.getVariableTable().getVariable(varName);
            }
        }

        if (varInfo == null) {
            throw new SemanticException("Variable '" + varName + "' no declarada.");
        }

        visit(ctx.expresion());

        String valor = pilaOperandos.pop();
        String tipoValor = pilaTipos.pop();
        String tipoDestino = varInfo.getTipo();

        if (!cubo.esCompatible(tipoDestino, tipoValor, "=")) {
            throw new SemanticException("Asignación incompatible a variable '" + varName + "'.");
        }

        cuadruplos.add(new Cuadruplo("=", valor, "-", String.valueOf(varInfo.getDireccion())));
        return null;
    }

    @Override
    public Object visitExpresion(BabyDuckParser.ExpresionContext ctx) {
        // Visitar el primer operando (expresión a la izquierda del operador)
        visit(ctx.exp(0));

        if (ctx.exp().size() == 2) {
            // Visitar el segundo operando (expresión a la derecha del operador relacional)
            visit(ctx.exp(1));

            // Obtener el operador relacional (como '<', '>', etc.)
            String operador = ctx.getChild(1).getText();
            pilaOperadores.push(operador);



            // Generar el cuádruplo para la expresión relacional
            generarCuadruplo();


        }

        return null;
    }

    @Override
    public Object visitCondition(BabyDuckParser.ConditionContext ctx) {
        visit(ctx.expresion());

        String resultado = pilaOperandos.pop();
        String tipo = pilaTipos.pop();

        if (!tipo.equals("int")) {
            throw new SemanticException("La condición del IF debe ser de tipo entero (booleano implícito).");
        }

        Cuadruplo gotof = new Cuadruplo("GOTOF", resultado, "-", "pendiente");
        cuadruplos.add(gotof);
        int indexGotoF = cuadruplos.size() - 1;

        visit(ctx.body(0));

        if (ctx.ELSE() != null) {
            Cuadruplo gotoFinal = new Cuadruplo("GOTO", "-", "-", "pendiente");
            cuadruplos.add(gotoFinal);
            int indexGoto = cuadruplos.size() - 1;

            gotof.setResultado(String.valueOf(cuadruplos.size()));
            visit(ctx.body(1));
            gotoFinal.setResultado(String.valueOf(cuadruplos.size()));
        } else {
            gotof.setResultado(String.valueOf(cuadruplos.size()));
        }

        return null;
    }

    private void generarCuadruplo() {
        String operador = pilaOperadores.pop();
        String der = pilaOperandos.pop();
        String izq = pilaOperandos.pop();
        String tipoDer = pilaTipos.pop();
        String tipoIzq = pilaTipos.pop();

        if (!cubo.esCompatible(tipoIzq, tipoDer, operador)) {
            throw new SemanticException("Operación no válida: " + tipoIzq + " " + operador + " " + tipoDer);
        }

        String tipoResultado = tipoIzq.equals("float") || tipoDer.equals("float") ? "float" : "int";
        int temporalDir = direccionador.nuevaTemporal(tipoResultado);
        cuadruplos.add(new Cuadruplo(operador, izq, der, String.valueOf(temporalDir)));
        pilaOperandos.push(String.valueOf(temporalDir));
        pilaTipos.push(tipoResultado);
    }

    @Override
    public Object visitExp(BabyDuckParser.ExpContext ctx) {
        visit(ctx.termino(0));
        for (int i = 1; i < ctx.termino().size(); i++) {
            visit(ctx.termino(i));
            String operador = ctx.getChild(2 * i - 1).getText();
            pilaOperadores.push(operador);
            generarCuadruplo();
        }
        return null;
    }

    @Override
    public Object visitTermino(BabyDuckParser.TerminoContext ctx) {
        visit(ctx.factor(0));
        for (int i = 1; i < ctx.factor().size(); i++) {
            visit(ctx.factor(i));
            String operador = ctx.getChild(2 * i - 1).getText();
            pilaOperadores.push(operador);
            generarCuadruplo();
        }
        return null;
    }

    @Override
    public Object visitFactor(BabyDuckParser.FactorContext ctx) {
        if (ctx.LPAREN() != null) {
            visit(ctx.expresion());
        } else if (ctx.ID() != null) {
            String nombre = ctx.ID().getText();
            VariableInfo var = funcionActual.getVariableTable().getVariable(nombre);

            // Si no está en variables locales, busca en globales
            if (var == null) {
                FuncionInfo globalFunc = directorio.getFuncion("global");
                if (globalFunc != null) {
                    var = globalFunc.getVariableTable().getVariable(nombre);
                }
            }

            // Si aún no está, busca en los parámetros de la función
            if (var == null) {
                for (ParameterInfo param : funcionActual.getParametros()) {
                    if (param.getNombre().equals(nombre)) {
                        int index = funcionActual.getParametros().indexOf(param);
                        int direccion = direccionador.direccionParametro(index);
                        var = new VariableInfo(param.getTipo(), direccion);
                        break;
                    }
                }
            }

            if (var == null) {
                throw new SemanticException("Variable o parámetro '" + nombre + "' no declarada.");
            }

            pilaOperandos.push(String.valueOf(var.getDireccion()));
            pilaTipos.push(var.getTipo());

        } else if (ctx.cte() != null) {
            String valor;
            String tipo;

            if (ctx.cte().CTE_INT() != null) {
                valor = ctx.cte().CTE_INT().getText();
                tipo = "int";
            } else if (ctx.cte().CTE_FLOAT() != null) {
                valor = ctx.cte().CTE_FLOAT().getText();
                tipo = "float";
            } else {
                valor = ctx.cte().CTE_STRING().getText();
                tipo = "string";
            }

            int dirConst = direccionador.direccionConstante(valor, tipo);
            pilaOperandos.push(String.valueOf(dirConst));
            pilaTipos.push(tipo);
        }

        return null;
    }

    @Override
    public Object visitCycle(BabyDuckParser.CycleContext ctx) {
        int inicioCiclo = cuadruplos.size();
        visit(ctx.expresion());
        String cond = pilaOperandos.pop();
        String tipoCond = pilaTipos.pop();

        if (!tipoCond.equals("int")) {
            throw new SemanticException("La condición del WHILE debe ser de tipo int.");
        }

        Cuadruplo gotof = new Cuadruplo("GOTOF", cond, "-", "pendiente");
        cuadruplos.add(gotof);
        int indexGotof = cuadruplos.size() - 1;

        visit(ctx.body());
        cuadruplos.add(new Cuadruplo("GOTO", "-", "-", String.valueOf(inicioCiclo)));
        gotof.setResultado(String.valueOf(cuadruplos.size()));
        return null;
    }

    @Override
    public Object visitFCall(BabyDuckParser.FCallContext ctx) {
        String nombreFuncion = ctx.ID().getText();

        if (!directorio.contieneFuncion(nombreFuncion)) {
            throw new SemanticException("La función '" + nombreFuncion + "' no ha sido declarada.");
        }

        FuncionInfo info = directorio.getFuncion(nombreFuncion);
        cuadruplos.add(new Cuadruplo("ERA", "-", "-", nombreFuncion));

        List<BabyDuckParser.ExpresionContext> args = ctx.expresion();
        List<ParameterInfo> parametros = info.getParametros();

        if (args.size() != parametros.size()) {
            throw new SemanticException("La función '" + nombreFuncion + "' esperaba " +
                    parametros.size() + " argumentos, pero se recibieron " + args.size() + ".");
        }

        for (int i = 0; i < args.size(); i++) {
            visit(args.get(i));
            String dirArg = pilaOperandos.pop();
            String tipoArg = pilaTipos.pop();
            String tipoParam = parametros.get(i).getTipo();

            if (!tipoArg.equals(tipoParam)) {
                throw new SemanticException("Tipo incompatible en parámetro " + (i + 1) +
                        " de la función '" + nombreFuncion + "'");
            }

            // Este cuádruplo envía el valor como paramX
            cuadruplos.add(new Cuadruplo("PARAM", dirArg, "-", "param" + i));
        }

        // Esta instrucción invoca la función
        cuadruplos.add(new Cuadruplo("GOSUB", "-", "-", nombreFuncion));


        return null;
    }

    @Override
    public Object visitPrint(BabyDuckParser.PrintContext ctx) {
        if (ctx.expresion() != null) {
            for (BabyDuckParser.ExpresionContext exprCtx : ctx.expresion()) {
                visit(exprCtx);
                String valor = pilaOperandos.pop();
                pilaTipos.pop();
                cuadruplos.add(new Cuadruplo("PRINT", valor, "-", "-"));
            }
        }
        return null;
    }

    public List<Cuadruplo> getCuadruplos() {
        return cuadruplos;
    }
    public Direccionador getDireccionador() {
        return direccionador;
    }
}