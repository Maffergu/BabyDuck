package BabyDuck;
// Generated from BabyDuck.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class BabyDuckLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PROGRAM", "MAIN", "END", "VAR", "PRINT", "INT", "FLOAT", "VOID", "IF", 
			"ELSE", "WHILE", "DO", "ID", "CTE_INT", "CTE_FLOAT", "CTE_STRING", "SUMA", 
			"RESTA", "MULT", "DIV", "MAYOR", "MENOR", "NOT_IGUAL", "IGUAL", "PUNTO_COMA", 
			"COMA", "PAREN_IZQ", "PAREN_DER", "LLAVE_IZQ", "LLAVE_DER", "DOS_PUNTOS", 
			"WS"
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


	public BabyDuckLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BabyDuck.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000 \u00c2\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0005\f"+
		"\u007f\b\f\n\f\f\f\u0082\t\f\u0001\r\u0004\r\u0085\b\r\u000b\r\f\r\u0086"+
		"\u0001\u000e\u0004\u000e\u008a\b\u000e\u000b\u000e\f\u000e\u008b\u0001"+
		"\u000e\u0001\u000e\u0004\u000e\u0090\b\u000e\u000b\u000e\f\u000e\u0091"+
		"\u0001\u000f\u0001\u000f\u0005\u000f\u0096\b\u000f\n\u000f\f\u000f\u0099"+
		"\t\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001"+
		"\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001"+
		"\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0004\u001f\u00bd"+
		"\b\u001f\u000b\u001f\f\u001f\u00be\u0001\u001f\u0001\u001f\u0000\u0000"+
		" \u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006"+
		"\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e"+
		"\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017"+
		"/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f? \u0001\u0000"+
		"\u0005\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001\u000009\u0001\u0000"+
		"\"\"\u0003\u0000\t\n\r\r  \u00c7\u0000\u0001\u0001\u0000\u0000\u0000\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000"+
		"\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b"+
		"\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001"+
		"\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001"+
		"\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001"+
		"\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001"+
		"\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001"+
		"\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000"+
		"\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000"+
		"\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-"+
		"\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000"+
		"\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000"+
		"\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;"+
		"\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000"+
		"\u0000\u0000\u0001A\u0001\u0000\u0000\u0000\u0003I\u0001\u0000\u0000\u0000"+
		"\u0005N\u0001\u0000\u0000\u0000\u0007R\u0001\u0000\u0000\u0000\tV\u0001"+
		"\u0000\u0000\u0000\u000b\\\u0001\u0000\u0000\u0000\r`\u0001\u0000\u0000"+
		"\u0000\u000ff\u0001\u0000\u0000\u0000\u0011k\u0001\u0000\u0000\u0000\u0013"+
		"n\u0001\u0000\u0000\u0000\u0015s\u0001\u0000\u0000\u0000\u0017y\u0001"+
		"\u0000\u0000\u0000\u0019|\u0001\u0000\u0000\u0000\u001b\u0084\u0001\u0000"+
		"\u0000\u0000\u001d\u0089\u0001\u0000\u0000\u0000\u001f\u0093\u0001\u0000"+
		"\u0000\u0000!\u009c\u0001\u0000\u0000\u0000#\u009e\u0001\u0000\u0000\u0000"+
		"%\u00a0\u0001\u0000\u0000\u0000\'\u00a2\u0001\u0000\u0000\u0000)\u00a4"+
		"\u0001\u0000\u0000\u0000+\u00a6\u0001\u0000\u0000\u0000-\u00a8\u0001\u0000"+
		"\u0000\u0000/\u00ab\u0001\u0000\u0000\u00001\u00ad\u0001\u0000\u0000\u0000"+
		"3\u00af\u0001\u0000\u0000\u00005\u00b1\u0001\u0000\u0000\u00007\u00b3"+
		"\u0001\u0000\u0000\u00009\u00b5\u0001\u0000\u0000\u0000;\u00b7\u0001\u0000"+
		"\u0000\u0000=\u00b9\u0001\u0000\u0000\u0000?\u00bc\u0001\u0000\u0000\u0000"+
		"AB\u0005p\u0000\u0000BC\u0005r\u0000\u0000CD\u0005o\u0000\u0000DE\u0005"+
		"g\u0000\u0000EF\u0005r\u0000\u0000FG\u0005a\u0000\u0000GH\u0005m\u0000"+
		"\u0000H\u0002\u0001\u0000\u0000\u0000IJ\u0005m\u0000\u0000JK\u0005a\u0000"+
		"\u0000KL\u0005i\u0000\u0000LM\u0005n\u0000\u0000M\u0004\u0001\u0000\u0000"+
		"\u0000NO\u0005e\u0000\u0000OP\u0005n\u0000\u0000PQ\u0005d\u0000\u0000"+
		"Q\u0006\u0001\u0000\u0000\u0000RS\u0005v\u0000\u0000ST\u0005a\u0000\u0000"+
		"TU\u0005r\u0000\u0000U\b\u0001\u0000\u0000\u0000VW\u0005p\u0000\u0000"+
		"WX\u0005r\u0000\u0000XY\u0005i\u0000\u0000YZ\u0005n\u0000\u0000Z[\u0005"+
		"t\u0000\u0000[\n\u0001\u0000\u0000\u0000\\]\u0005i\u0000\u0000]^\u0005"+
		"n\u0000\u0000^_\u0005t\u0000\u0000_\f\u0001\u0000\u0000\u0000`a\u0005"+
		"f\u0000\u0000ab\u0005l\u0000\u0000bc\u0005o\u0000\u0000cd\u0005a\u0000"+
		"\u0000de\u0005t\u0000\u0000e\u000e\u0001\u0000\u0000\u0000fg\u0005v\u0000"+
		"\u0000gh\u0005o\u0000\u0000hi\u0005i\u0000\u0000ij\u0005d\u0000\u0000"+
		"j\u0010\u0001\u0000\u0000\u0000kl\u0005i\u0000\u0000lm\u0005f\u0000\u0000"+
		"m\u0012\u0001\u0000\u0000\u0000no\u0005e\u0000\u0000op\u0005l\u0000\u0000"+
		"pq\u0005s\u0000\u0000qr\u0005e\u0000\u0000r\u0014\u0001\u0000\u0000\u0000"+
		"st\u0005w\u0000\u0000tu\u0005h\u0000\u0000uv\u0005i\u0000\u0000vw\u0005"+
		"l\u0000\u0000wx\u0005e\u0000\u0000x\u0016\u0001\u0000\u0000\u0000yz\u0005"+
		"d\u0000\u0000z{\u0005o\u0000\u0000{\u0018\u0001\u0000\u0000\u0000|\u0080"+
		"\u0007\u0000\u0000\u0000}\u007f\u0007\u0001\u0000\u0000~}\u0001\u0000"+
		"\u0000\u0000\u007f\u0082\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000"+
		"\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081\u001a\u0001\u0000\u0000"+
		"\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0083\u0085\u0007\u0002\u0000"+
		"\u0000\u0084\u0083\u0001\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000"+
		"\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000\u0000"+
		"\u0000\u0087\u001c\u0001\u0000\u0000\u0000\u0088\u008a\u0007\u0002\u0000"+
		"\u0000\u0089\u0088\u0001\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000"+
		"\u0000\u008b\u0089\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000"+
		"\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u008f\u0005.\u0000\u0000"+
		"\u008e\u0090\u0007\u0002\u0000\u0000\u008f\u008e\u0001\u0000\u0000\u0000"+
		"\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000"+
		"\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u001e\u0001\u0000\u0000\u0000"+
		"\u0093\u0097\u0005\"\u0000\u0000\u0094\u0096\b\u0003\u0000\u0000\u0095"+
		"\u0094\u0001\u0000\u0000\u0000\u0096\u0099\u0001\u0000\u0000\u0000\u0097"+
		"\u0095\u0001\u0000\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000\u0098"+
		"\u009a\u0001\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u009a"+
		"\u009b\u0005\"\u0000\u0000\u009b \u0001\u0000\u0000\u0000\u009c\u009d"+
		"\u0005+\u0000\u0000\u009d\"\u0001\u0000\u0000\u0000\u009e\u009f\u0005"+
		"-\u0000\u0000\u009f$\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005*\u0000"+
		"\u0000\u00a1&\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005/\u0000\u0000\u00a3"+
		"(\u0001\u0000\u0000\u0000\u00a4\u00a5\u0005>\u0000\u0000\u00a5*\u0001"+
		"\u0000\u0000\u0000\u00a6\u00a7\u0005<\u0000\u0000\u00a7,\u0001\u0000\u0000"+
		"\u0000\u00a8\u00a9\u0005!\u0000\u0000\u00a9\u00aa\u0005=\u0000\u0000\u00aa"+
		".\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005=\u0000\u0000\u00ac0\u0001"+
		"\u0000\u0000\u0000\u00ad\u00ae\u0005;\u0000\u0000\u00ae2\u0001\u0000\u0000"+
		"\u0000\u00af\u00b0\u0005,\u0000\u0000\u00b04\u0001\u0000\u0000\u0000\u00b1"+
		"\u00b2\u0005(\u0000\u0000\u00b26\u0001\u0000\u0000\u0000\u00b3\u00b4\u0005"+
		")\u0000\u0000\u00b48\u0001\u0000\u0000\u0000\u00b5\u00b6\u0005{\u0000"+
		"\u0000\u00b6:\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005}\u0000\u0000\u00b8"+
		"<\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005:\u0000\u0000\u00ba>\u0001"+
		"\u0000\u0000\u0000\u00bb\u00bd\u0007\u0004\u0000\u0000\u00bc\u00bb\u0001"+
		"\u0000\u0000\u0000\u00bd\u00be\u0001\u0000\u0000\u0000\u00be\u00bc\u0001"+
		"\u0000\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf\u00c0\u0001"+
		"\u0000\u0000\u0000\u00c0\u00c1\u0006\u001f\u0000\u0000\u00c1@\u0001\u0000"+
		"\u0000\u0000\u0007\u0000\u0080\u0086\u008b\u0091\u0097\u00be\u0001\u0006"+
		"\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}