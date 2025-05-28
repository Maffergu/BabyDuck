package BabyDuck;
// Generated from BabyDuck.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class BabyDuckParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PROGRAM=1, MAIN=2, END=3, VAR=4, PRINT=5, INT=6, FLOAT=7, VOID=8, IF=9, 
		ELSE=10, WHILE=11, DO=12, ID=13, CTE_INT=14, CTE_FLOAT=15, CTE_STRING=16, 
		SUMA=17, RESTA=18, MULT=19, DIV=20, MAYOR=21, MENOR=22, NOT_IGUAL=23, 
		IGUAL=24, PUNTO_COMA=25, COMA=26, PAREN_IZQ=27, PAREN_DER=28, LLAVE_IZQ=29, 
		LLAVE_DER=30, DOS_PUNTOS=31, WS=32;
	public static final int
		RULE_programa = 0, RULE_funcs = 1, RULE_main_func = 2, RULE_vars = 3, 
		RULE_type = 4, RULE_body = 5, RULE_statement = 6, RULE_assign = 7, RULE_expresion = 8, 
		RULE_exp = 9, RULE_termino = 10, RULE_factor = 11, RULE_condition = 12, 
		RULE_cycle = 13, RULE_fCall = 14, RULE_print_stmt = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"programa", "funcs", "main_func", "vars", "type", "body", "statement", 
			"assign", "expresion", "exp", "termino", "factor", "condition", "cycle", 
			"fCall", "print_stmt"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'program'", "'main'", "'end'", "'var'", "'print'", "'int'", "'float'", 
			"'void'", "'if'", "'else'", "'while'", "'do'", null, null, null, null, 
			"'+'", "'-'", "'*'", "'/'", "'>'", "'<'", "'!='", "'='", "';'", "','", 
			"'('", "')'", "'{'", "'}'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PROGRAM", "MAIN", "END", "VAR", "PRINT", "INT", "FLOAT", "VOID", 
			"IF", "ELSE", "WHILE", "DO", "ID", "CTE_INT", "CTE_FLOAT", "CTE_STRING", 
			"SUMA", "RESTA", "MULT", "DIV", "MAYOR", "MENOR", "NOT_IGUAL", "IGUAL", 
			"PUNTO_COMA", "COMA", "PAREN_IZQ", "PAREN_DER", "LLAVE_IZQ", "LLAVE_DER", 
			"DOS_PUNTOS", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "BabyDuck.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BabyDuckParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramaContext extends ParserRuleContext {
		public TerminalNode PROGRAM() { return getToken(BabyDuckParser.PROGRAM, 0); }
		public TerminalNode ID() { return getToken(BabyDuckParser.ID, 0); }
		public TerminalNode PUNTO_COMA() { return getToken(BabyDuckParser.PUNTO_COMA, 0); }
		public FuncsContext funcs() {
			return getRuleContext(FuncsContext.class,0);
		}
		public Main_funcContext main_func() {
			return getRuleContext(Main_funcContext.class,0);
		}
		public TerminalNode END() { return getToken(BabyDuckParser.END, 0); }
		public ProgramaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programa; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterPrograma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitPrograma(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitPrograma(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramaContext programa() throws RecognitionException {
		ProgramaContext _localctx = new ProgramaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_programa);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(PROGRAM);
			setState(33);
			match(ID);
			setState(34);
			match(PUNTO_COMA);
			setState(35);
			funcs();
			setState(36);
			main_func();
			setState(37);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncsContext extends ParserRuleContext {
		public List<TerminalNode> VOID() { return getTokens(BabyDuckParser.VOID); }
		public TerminalNode VOID(int i) {
			return getToken(BabyDuckParser.VOID, i);
		}
		public List<TerminalNode> ID() { return getTokens(BabyDuckParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(BabyDuckParser.ID, i);
		}
		public List<TerminalNode> PAREN_IZQ() { return getTokens(BabyDuckParser.PAREN_IZQ); }
		public TerminalNode PAREN_IZQ(int i) {
			return getToken(BabyDuckParser.PAREN_IZQ, i);
		}
		public List<ExpresionContext> expresion() {
			return getRuleContexts(ExpresionContext.class);
		}
		public ExpresionContext expresion(int i) {
			return getRuleContext(ExpresionContext.class,i);
		}
		public List<TerminalNode> PAREN_DER() { return getTokens(BabyDuckParser.PAREN_DER); }
		public TerminalNode PAREN_DER(int i) {
			return getToken(BabyDuckParser.PAREN_DER, i);
		}
		public List<VarsContext> vars() {
			return getRuleContexts(VarsContext.class);
		}
		public VarsContext vars(int i) {
			return getRuleContext(VarsContext.class,i);
		}
		public List<TerminalNode> PUNTO_COMA() { return getTokens(BabyDuckParser.PUNTO_COMA); }
		public TerminalNode PUNTO_COMA(int i) {
			return getToken(BabyDuckParser.PUNTO_COMA, i);
		}
		public FuncsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterFuncs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitFuncs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitFuncs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncsContext funcs() throws RecognitionException {
		FuncsContext _localctx = new FuncsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_funcs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VOID) {
				{
				{
				setState(39);
				match(VOID);
				setState(40);
				match(ID);
				setState(41);
				match(ID);
				setState(42);
				match(PAREN_IZQ);
				setState(43);
				expresion();
				setState(44);
				match(PAREN_DER);
				setState(45);
				vars();
				setState(46);
				match(PUNTO_COMA);
				}
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Main_funcContext extends ParserRuleContext {
		public TerminalNode MAIN() { return getToken(BabyDuckParser.MAIN, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public Main_funcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main_func; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterMain_func(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitMain_func(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitMain_func(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Main_funcContext main_func() throws RecognitionException {
		Main_funcContext _localctx = new Main_funcContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_main_func);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(MAIN);
			setState(54);
			body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarsContext extends ParserRuleContext {
		public List<TerminalNode> VAR() { return getTokens(BabyDuckParser.VAR); }
		public TerminalNode VAR(int i) {
			return getToken(BabyDuckParser.VAR, i);
		}
		public List<TerminalNode> ID() { return getTokens(BabyDuckParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(BabyDuckParser.ID, i);
		}
		public List<TerminalNode> DOS_PUNTOS() { return getTokens(BabyDuckParser.DOS_PUNTOS); }
		public TerminalNode DOS_PUNTOS(int i) {
			return getToken(BabyDuckParser.DOS_PUNTOS, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> PUNTO_COMA() { return getTokens(BabyDuckParser.PUNTO_COMA); }
		public TerminalNode PUNTO_COMA(int i) {
			return getToken(BabyDuckParser.PUNTO_COMA, i);
		}
		public VarsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vars; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterVars(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitVars(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitVars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarsContext vars() throws RecognitionException {
		VarsContext _localctx = new VarsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VAR) {
				{
				{
				setState(56);
				match(VAR);
				setState(57);
				match(ID);
				setState(58);
				match(DOS_PUNTOS);
				setState(59);
				type();
				setState(60);
				match(PUNTO_COMA);
				}
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(BabyDuckParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(BabyDuckParser.FLOAT, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==FLOAT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BodyContext extends ParserRuleContext {
		public TerminalNode LLAVE_IZQ() { return getToken(BabyDuckParser.LLAVE_IZQ, 0); }
		public TerminalNode LLAVE_DER() { return getToken(BabyDuckParser.LLAVE_DER, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_body);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(LLAVE_IZQ);
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 14880L) != 0)) {
				{
				{
				setState(70);
				statement();
				}
				}
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(76);
			match(LLAVE_DER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public TerminalNode PUNTO_COMA() { return getToken(BabyDuckParser.PUNTO_COMA, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public CycleContext cycle() {
			return getRuleContext(CycleContext.class,0);
		}
		public FCallContext fCall() {
			return getRuleContext(FCallContext.class,0);
		}
		public Print_stmtContext print_stmt() {
			return getRuleContext(Print_stmtContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		try {
			setState(89);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				assign();
				setState(79);
				match(PUNTO_COMA);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				condition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				cycle();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				fCall();
				setState(84);
				match(PUNTO_COMA);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(86);
				print_stmt();
				setState(87);
				match(PUNTO_COMA);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(BabyDuckParser.ID, 0); }
		public TerminalNode IGUAL() { return getToken(BabyDuckParser.IGUAL, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(ID);
			setState(92);
			match(IGUAL);
			setState(93);
			expresion();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpresionContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode MAYOR() { return getToken(BabyDuckParser.MAYOR, 0); }
		public TerminalNode MENOR() { return getToken(BabyDuckParser.MENOR, 0); }
		public TerminalNode NOT_IGUAL() { return getToken(BabyDuckParser.NOT_IGUAL, 0); }
		public ExpresionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expresion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterExpresion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitExpresion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitExpresion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpresionContext expresion() throws RecognitionException {
		ExpresionContext _localctx = new ExpresionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_expresion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			exp();
			setState(102);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MAYOR:
				{
				setState(96);
				match(MAYOR);
				setState(97);
				exp();
				}
				break;
			case MENOR:
				{
				setState(98);
				match(MENOR);
				setState(99);
				exp();
				}
				break;
			case NOT_IGUAL:
				{
				setState(100);
				match(NOT_IGUAL);
				setState(101);
				exp();
				}
				break;
			case PUNTO_COMA:
			case PAREN_DER:
				break;
			default:
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpContext extends ParserRuleContext {
		public List<TerminoContext> termino() {
			return getRuleContexts(TerminoContext.class);
		}
		public TerminoContext termino(int i) {
			return getRuleContext(TerminoContext.class,i);
		}
		public List<TerminalNode> SUMA() { return getTokens(BabyDuckParser.SUMA); }
		public TerminalNode SUMA(int i) {
			return getToken(BabyDuckParser.SUMA, i);
		}
		public List<TerminalNode> RESTA() { return getTokens(BabyDuckParser.RESTA); }
		public TerminalNode RESTA(int i) {
			return getToken(BabyDuckParser.RESTA, i);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_exp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			termino();
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SUMA || _la==RESTA) {
				{
				setState(109);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SUMA:
					{
					setState(105);
					match(SUMA);
					setState(106);
					termino();
					}
					break;
				case RESTA:
					{
					setState(107);
					match(RESTA);
					setState(108);
					termino();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TerminoContext extends ParserRuleContext {
		public List<FactorContext> factor() {
			return getRuleContexts(FactorContext.class);
		}
		public FactorContext factor(int i) {
			return getRuleContext(FactorContext.class,i);
		}
		public List<TerminalNode> MULT() { return getTokens(BabyDuckParser.MULT); }
		public TerminalNode MULT(int i) {
			return getToken(BabyDuckParser.MULT, i);
		}
		public List<TerminalNode> DIV() { return getTokens(BabyDuckParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(BabyDuckParser.DIV, i);
		}
		public TerminoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termino; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterTermino(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitTermino(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitTermino(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminoContext termino() throws RecognitionException {
		TerminoContext _localctx = new TerminoContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_termino);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			factor();
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULT || _la==DIV) {
				{
				setState(119);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case MULT:
					{
					setState(115);
					match(MULT);
					setState(116);
					factor();
					}
					break;
				case DIV:
					{
					setState(117);
					match(DIV);
					setState(118);
					factor();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ParserRuleContext {
		public TerminalNode PAREN_IZQ() { return getToken(BabyDuckParser.PAREN_IZQ, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode PAREN_DER() { return getToken(BabyDuckParser.PAREN_DER, 0); }
		public TerminalNode ID() { return getToken(BabyDuckParser.ID, 0); }
		public TerminalNode CTE_INT() { return getToken(BabyDuckParser.CTE_INT, 0); }
		public TerminalNode CTE_FLOAT() { return getToken(BabyDuckParser.CTE_FLOAT, 0); }
		public TerminalNode CTE_STRING() { return getToken(BabyDuckParser.CTE_STRING, 0); }
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitFactor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_factor);
		try {
			setState(132);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAREN_IZQ:
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				match(PAREN_IZQ);
				setState(125);
				expresion();
				setState(126);
				match(PAREN_DER);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				match(ID);
				}
				break;
			case CTE_INT:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				match(CTE_INT);
				}
				break;
			case CTE_FLOAT:
				enterOuterAlt(_localctx, 4);
				{
				setState(130);
				match(CTE_FLOAT);
				}
				break;
			case CTE_STRING:
				enterOuterAlt(_localctx, 5);
				{
				setState(131);
				match(CTE_STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(BabyDuckParser.IF, 0); }
		public TerminalNode PAREN_IZQ() { return getToken(BabyDuckParser.PAREN_IZQ, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode PAREN_DER() { return getToken(BabyDuckParser.PAREN_DER, 0); }
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(BabyDuckParser.ELSE, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(IF);
			setState(135);
			match(PAREN_IZQ);
			setState(136);
			expresion();
			setState(137);
			match(PAREN_DER);
			setState(138);
			body();
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(139);
				match(ELSE);
				setState(140);
				body();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CycleContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(BabyDuckParser.WHILE, 0); }
		public TerminalNode PAREN_IZQ() { return getToken(BabyDuckParser.PAREN_IZQ, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode PAREN_DER() { return getToken(BabyDuckParser.PAREN_DER, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public TerminalNode DO() { return getToken(BabyDuckParser.DO, 0); }
		public TerminalNode PUNTO_COMA() { return getToken(BabyDuckParser.PUNTO_COMA, 0); }
		public CycleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cycle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterCycle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitCycle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitCycle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CycleContext cycle() throws RecognitionException {
		CycleContext _localctx = new CycleContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_cycle);
		try {
			setState(157);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHILE:
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				match(WHILE);
				setState(144);
				match(PAREN_IZQ);
				setState(145);
				expresion();
				setState(146);
				match(PAREN_DER);
				setState(147);
				body();
				}
				break;
			case DO:
				enterOuterAlt(_localctx, 2);
				{
				setState(149);
				match(DO);
				setState(150);
				body();
				setState(151);
				match(WHILE);
				setState(152);
				match(PAREN_IZQ);
				setState(153);
				expresion();
				setState(154);
				match(PAREN_DER);
				setState(155);
				match(PUNTO_COMA);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FCallContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(BabyDuckParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(BabyDuckParser.ID, i);
		}
		public TerminalNode PAREN_IZQ() { return getToken(BabyDuckParser.PAREN_IZQ, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode PAREN_DER() { return getToken(BabyDuckParser.PAREN_DER, 0); }
		public FCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterFCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitFCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitFCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FCallContext fCall() throws RecognitionException {
		FCallContext _localctx = new FCallContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_fCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			match(ID);
			setState(160);
			match(ID);
			setState(161);
			match(PAREN_IZQ);
			setState(162);
			expresion();
			setState(163);
			match(PAREN_DER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Print_stmtContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(BabyDuckParser.PRINT, 0); }
		public TerminalNode PAREN_IZQ() { return getToken(BabyDuckParser.PAREN_IZQ, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode PAREN_DER() { return getToken(BabyDuckParser.PAREN_DER, 0); }
		public Print_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).enterPrint_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BabyDuckListener ) ((BabyDuckListener)listener).exitPrint_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BabyDuckVisitor ) return ((BabyDuckVisitor<? extends T>)visitor).visitPrint_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Print_stmtContext print_stmt() throws RecognitionException {
		Print_stmtContext _localctx = new Print_stmtContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_print_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(PRINT);
			setState(166);
			match(PAREN_IZQ);
			setState(167);
			expresion();
			setState(168);
			match(PAREN_DER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001 \u00ab\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u00011\b\u0001"+
		"\n\u0001\f\u00014\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003"+
		"?\b\u0003\n\u0003\f\u0003B\t\u0003\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0005\u0005H\b\u0005\n\u0005\f\u0005K\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0003\u0006Z\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bg\b\b"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0005\tn\b\t\n\t\f\tq\t\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0005\nx\b\n\n\n\f\n{\t\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0003\u000b\u0085\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0003\f\u008e\b\f\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0003\r\u009e\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0000\u0000\u0010\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0000\u0001"+
		"\u0001\u0000\u0006\u0007\u00ae\u0000 \u0001\u0000\u0000\u0000\u00022\u0001"+
		"\u0000\u0000\u0000\u00045\u0001\u0000\u0000\u0000\u0006@\u0001\u0000\u0000"+
		"\u0000\bC\u0001\u0000\u0000\u0000\nE\u0001\u0000\u0000\u0000\fY\u0001"+
		"\u0000\u0000\u0000\u000e[\u0001\u0000\u0000\u0000\u0010_\u0001\u0000\u0000"+
		"\u0000\u0012h\u0001\u0000\u0000\u0000\u0014r\u0001\u0000\u0000\u0000\u0016"+
		"\u0084\u0001\u0000\u0000\u0000\u0018\u0086\u0001\u0000\u0000\u0000\u001a"+
		"\u009d\u0001\u0000\u0000\u0000\u001c\u009f\u0001\u0000\u0000\u0000\u001e"+
		"\u00a5\u0001\u0000\u0000\u0000 !\u0005\u0001\u0000\u0000!\"\u0005\r\u0000"+
		"\u0000\"#\u0005\u0019\u0000\u0000#$\u0003\u0002\u0001\u0000$%\u0003\u0004"+
		"\u0002\u0000%&\u0005\u0003\u0000\u0000&\u0001\u0001\u0000\u0000\u0000"+
		"\'(\u0005\b\u0000\u0000()\u0005\r\u0000\u0000)*\u0005\r\u0000\u0000*+"+
		"\u0005\u001b\u0000\u0000+,\u0003\u0010\b\u0000,-\u0005\u001c\u0000\u0000"+
		"-.\u0003\u0006\u0003\u0000./\u0005\u0019\u0000\u0000/1\u0001\u0000\u0000"+
		"\u00000\'\u0001\u0000\u0000\u000014\u0001\u0000\u0000\u000020\u0001\u0000"+
		"\u0000\u000023\u0001\u0000\u0000\u00003\u0003\u0001\u0000\u0000\u0000"+
		"42\u0001\u0000\u0000\u000056\u0005\u0002\u0000\u000067\u0003\n\u0005\u0000"+
		"7\u0005\u0001\u0000\u0000\u000089\u0005\u0004\u0000\u00009:\u0005\r\u0000"+
		"\u0000:;\u0005\u001f\u0000\u0000;<\u0003\b\u0004\u0000<=\u0005\u0019\u0000"+
		"\u0000=?\u0001\u0000\u0000\u0000>8\u0001\u0000\u0000\u0000?B\u0001\u0000"+
		"\u0000\u0000@>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000A\u0007"+
		"\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000CD\u0007\u0000\u0000"+
		"\u0000D\t\u0001\u0000\u0000\u0000EI\u0005\u001d\u0000\u0000FH\u0003\f"+
		"\u0006\u0000GF\u0001\u0000\u0000\u0000HK\u0001\u0000\u0000\u0000IG\u0001"+
		"\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000JL\u0001\u0000\u0000\u0000"+
		"KI\u0001\u0000\u0000\u0000LM\u0005\u001e\u0000\u0000M\u000b\u0001\u0000"+
		"\u0000\u0000NO\u0003\u000e\u0007\u0000OP\u0005\u0019\u0000\u0000PZ\u0001"+
		"\u0000\u0000\u0000QZ\u0003\u0018\f\u0000RZ\u0003\u001a\r\u0000ST\u0003"+
		"\u001c\u000e\u0000TU\u0005\u0019\u0000\u0000UZ\u0001\u0000\u0000\u0000"+
		"VW\u0003\u001e\u000f\u0000WX\u0005\u0019\u0000\u0000XZ\u0001\u0000\u0000"+
		"\u0000YN\u0001\u0000\u0000\u0000YQ\u0001\u0000\u0000\u0000YR\u0001\u0000"+
		"\u0000\u0000YS\u0001\u0000\u0000\u0000YV\u0001\u0000\u0000\u0000Z\r\u0001"+
		"\u0000\u0000\u0000[\\\u0005\r\u0000\u0000\\]\u0005\u0018\u0000\u0000]"+
		"^\u0003\u0010\b\u0000^\u000f\u0001\u0000\u0000\u0000_f\u0003\u0012\t\u0000"+
		"`a\u0005\u0015\u0000\u0000ag\u0003\u0012\t\u0000bc\u0005\u0016\u0000\u0000"+
		"cg\u0003\u0012\t\u0000de\u0005\u0017\u0000\u0000eg\u0003\u0012\t\u0000"+
		"f`\u0001\u0000\u0000\u0000fb\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000"+
		"\u0000fg\u0001\u0000\u0000\u0000g\u0011\u0001\u0000\u0000\u0000ho\u0003"+
		"\u0014\n\u0000ij\u0005\u0011\u0000\u0000jn\u0003\u0014\n\u0000kl\u0005"+
		"\u0012\u0000\u0000ln\u0003\u0014\n\u0000mi\u0001\u0000\u0000\u0000mk\u0001"+
		"\u0000\u0000\u0000nq\u0001\u0000\u0000\u0000om\u0001\u0000\u0000\u0000"+
		"op\u0001\u0000\u0000\u0000p\u0013\u0001\u0000\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000ry\u0003\u0016\u000b\u0000st\u0005\u0013\u0000\u0000tx\u0003"+
		"\u0016\u000b\u0000uv\u0005\u0014\u0000\u0000vx\u0003\u0016\u000b\u0000"+
		"ws\u0001\u0000\u0000\u0000wu\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000"+
		"\u0000yw\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z\u0015\u0001"+
		"\u0000\u0000\u0000{y\u0001\u0000\u0000\u0000|}\u0005\u001b\u0000\u0000"+
		"}~\u0003\u0010\b\u0000~\u007f\u0005\u001c\u0000\u0000\u007f\u0085\u0001"+
		"\u0000\u0000\u0000\u0080\u0085\u0005\r\u0000\u0000\u0081\u0085\u0005\u000e"+
		"\u0000\u0000\u0082\u0085\u0005\u000f\u0000\u0000\u0083\u0085\u0005\u0010"+
		"\u0000\u0000\u0084|\u0001\u0000\u0000\u0000\u0084\u0080\u0001\u0000\u0000"+
		"\u0000\u0084\u0081\u0001\u0000\u0000\u0000\u0084\u0082\u0001\u0000\u0000"+
		"\u0000\u0084\u0083\u0001\u0000\u0000\u0000\u0085\u0017\u0001\u0000\u0000"+
		"\u0000\u0086\u0087\u0005\t\u0000\u0000\u0087\u0088\u0005\u001b\u0000\u0000"+
		"\u0088\u0089\u0003\u0010\b\u0000\u0089\u008a\u0005\u001c\u0000\u0000\u008a"+
		"\u008d\u0003\n\u0005\u0000\u008b\u008c\u0005\n\u0000\u0000\u008c\u008e"+
		"\u0003\n\u0005\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001"+
		"\u0000\u0000\u0000\u008e\u0019\u0001\u0000\u0000\u0000\u008f\u0090\u0005"+
		"\u000b\u0000\u0000\u0090\u0091\u0005\u001b\u0000\u0000\u0091\u0092\u0003"+
		"\u0010\b\u0000\u0092\u0093\u0005\u001c\u0000\u0000\u0093\u0094\u0003\n"+
		"\u0005\u0000\u0094\u009e\u0001\u0000\u0000\u0000\u0095\u0096\u0005\f\u0000"+
		"\u0000\u0096\u0097\u0003\n\u0005\u0000\u0097\u0098\u0005\u000b\u0000\u0000"+
		"\u0098\u0099\u0005\u001b\u0000\u0000\u0099\u009a\u0003\u0010\b\u0000\u009a"+
		"\u009b\u0005\u001c\u0000\u0000\u009b\u009c\u0005\u0019\u0000\u0000\u009c"+
		"\u009e\u0001\u0000\u0000\u0000\u009d\u008f\u0001\u0000\u0000\u0000\u009d"+
		"\u0095\u0001\u0000\u0000\u0000\u009e\u001b\u0001\u0000\u0000\u0000\u009f"+
		"\u00a0\u0005\r\u0000\u0000\u00a0\u00a1\u0005\r\u0000\u0000\u00a1\u00a2"+
		"\u0005\u001b\u0000\u0000\u00a2\u00a3\u0003\u0010\b\u0000\u00a3\u00a4\u0005"+
		"\u001c\u0000\u0000\u00a4\u001d\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005"+
		"\u0005\u0000\u0000\u00a6\u00a7\u0005\u001b\u0000\u0000\u00a7\u00a8\u0003"+
		"\u0010\b\u0000\u00a8\u00a9\u0005\u001c\u0000\u0000\u00a9\u001f\u0001\u0000"+
		"\u0000\u0000\f2@IYfmowy\u0084\u008d\u009d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}