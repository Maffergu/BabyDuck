// Generated from /Users/mafer/Documents/8vo/Aplicaciones Avanzadas/BBDuck/src/BabyDuck/BabyDuck.g4 by ANTLR 4.13.2
package BabyDuck;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BabyDuckParser}.
 */
public interface BabyDuckListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(BabyDuckParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(BabyDuckParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#vars}.
	 * @param ctx the parse tree
	 */
	void enterVars(BabyDuckParser.VarsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#vars}.
	 * @param ctx the parse tree
	 */
	void exitVars(BabyDuckParser.VarsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#idList}.
	 * @param ctx the parse tree
	 */
	void enterIdList(BabyDuckParser.IdListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#idList}.
	 * @param ctx the parse tree
	 */
	void exitIdList(BabyDuckParser.IdListContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(BabyDuckParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(BabyDuckParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#funcs}.
	 * @param ctx the parse tree
	 */
	void enterFuncs(BabyDuckParser.FuncsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#funcs}.
	 * @param ctx the parse tree
	 */
	void exitFuncs(BabyDuckParser.FuncsContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFunc(BabyDuckParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFunc(BabyDuckParser.FuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(BabyDuckParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(BabyDuckParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(BabyDuckParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(BabyDuckParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(BabyDuckParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(BabyDuckParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(BabyDuckParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(BabyDuckParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#cycle}.
	 * @param ctx the parse tree
	 */
	void enterCycle(BabyDuckParser.CycleContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#cycle}.
	 * @param ctx the parse tree
	 */
	void exitCycle(BabyDuckParser.CycleContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#fCall}.
	 * @param ctx the parse tree
	 */
	void enterFCall(BabyDuckParser.FCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#fCall}.
	 * @param ctx the parse tree
	 */
	void exitFCall(BabyDuckParser.FCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(BabyDuckParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(BabyDuckParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(BabyDuckParser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(BabyDuckParser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(BabyDuckParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(BabyDuckParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#termino}.
	 * @param ctx the parse tree
	 */
	void enterTermino(BabyDuckParser.TerminoContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#termino}.
	 * @param ctx the parse tree
	 */
	void exitTermino(BabyDuckParser.TerminoContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(BabyDuckParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(BabyDuckParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link BabyDuckParser#cte}.
	 * @param ctx the parse tree
	 */
	void enterCte(BabyDuckParser.CteContext ctx);
	/**
	 * Exit a parse tree produced by {@link BabyDuckParser#cte}.
	 * @param ctx the parse tree
	 */
	void exitCte(BabyDuckParser.CteContext ctx);
}