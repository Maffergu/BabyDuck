// Generated from /Users/mafer/Documents/8vo/Aplicaciones Avanzadas/BBDuck/src/BabyDuck/BabyDuck.g4 by ANTLR 4.13.2
package BabyDuck;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BabyDuckParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BabyDuckVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(BabyDuckParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#vars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVars(BabyDuckParser.VarsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#idList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdList(BabyDuckParser.IdListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(BabyDuckParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#funcs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncs(BabyDuckParser.FuncsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc(BabyDuckParser.FuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(BabyDuckParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(BabyDuckParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(BabyDuckParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(BabyDuckParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#cycle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCycle(BabyDuckParser.CycleContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#fCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFCall(BabyDuckParser.FCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(BabyDuckParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion(BabyDuckParser.ExpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(BabyDuckParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#termino}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermino(BabyDuckParser.TerminoContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(BabyDuckParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link BabyDuckParser#cte}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCte(BabyDuckParser.CteContext ctx);
}