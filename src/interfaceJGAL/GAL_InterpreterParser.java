package interfaceJGAL;

import java.util.LinkedList;
import java.util.List;
import org.codehaus.jparsec.functors.*;
import org.codehaus.jparsec.*;
import org.codehaus.jparsec.Tokens.Fragment;
import JGAL.GAL_Chromosome;
import JGAL.GAL_Population;

public class GAL_InterpreterParser {

	private LinkedList<Object> variables;
	private LinkedList<String> variablesNames;
	private String[] constantNames;
	private Object[] constantValues;
	private static int interpreter;
	
	//Operadores binarios con Doubles
	enum BinaryOperator implements Binary<GAL_InterpreterNode> {
		plus {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},0,interpreter);
			}
		},
		minus {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},1,interpreter);
			}
		},
		multiply {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},2,interpreter);
			}
		},
		divide {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},3,interpreter);
			}
		},
		mod {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},5,interpreter);
			}
		},
		power {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},4,interpreter);
			}
		}
	}
	
	//Operadores Unarios con Doubles
	enum UnaryOperator implements Unary<GAL_InterpreterNode> {
		negative {
			public GAL_InterpreterNode map(GAL_InterpreterNode n) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{n},13,interpreter);
			}
		}
	}

	//Parser para los operadores aritmeticos simples
	private Terminals OPERATORS =
		Terminals.operators("+", "-", "*", "/", "**","%", //Operadores Aritmeticos Simples
							"Abs","Max","Min","Root","Log", //Operadores Aritmeticos Complejos
							"Sin","Cos","Tan","Asin","Acos","Atan","Hypot","toRadians","toDegrees", //Operadores Trigonometricos
							"Round","Ceil","Floor","RandI","RandD","RandB","RandC",//Operadores Aritmeticos Otros
							"<",">","=",">=","<=","<>", //Operadores de Comparacion
							"&&","||","^","not", //Operadores Logicos
							"True","False","PI","E", //Constantes 
							"If","Then","Else","End", //Condicional
							"While","Do", //Ciclos
							"$", ":=", "Array", "Pos", //Variables
							"'","\"",",","(", ")","{","}",";", //Caracteres del Lenguaje
							"Bin2Int"
							);
	
	//Constantes
	private Parser<GAL_InterpreterNode> False= term("False").map(new Map<Object,GAL_InterpreterNode>(){
		public GAL_InterpreterNode map(Object a){
			return new GAL_InterpreterLeaf(45, interpreter);
		}
	});

	private Parser<GAL_InterpreterNode> True= term("True").map(new Map<Object,GAL_InterpreterNode>(){
		public GAL_InterpreterNode map(Object a){
			return new GAL_InterpreterLeaf(44, interpreter);
		}
	});
	
	private Parser<GAL_InterpreterNode> PI= term("PI").map(new Map<Object,GAL_InterpreterNode>(){
		public GAL_InterpreterNode map(Object a){
			return new GAL_InterpreterLeaf(42, interpreter);
		}
	});

	private Parser<GAL_InterpreterNode> E= term("E").map(new Map<Object,GAL_InterpreterNode>(){
		public GAL_InterpreterNode map(Object a){
			return new GAL_InterpreterLeaf(43, interpreter);
		}
	});
	
	//Parser para los Numeros
	private Parser<GAL_InterpreterNode> NUMBER = Terminals.DecimalLiteral.PARSER.map(new Map<String, GAL_InterpreterNode>() {
		public GAL_InterpreterNode map(String s) {
			return new GAL_InterpreterLeaf(Double.valueOf(s),39,interpreter);
		}
	}).or(PI).or(E);
	
	//Parser para simbolos que se ignoran
	private Parser<Void> IGNORED =
			Parsers.or(Scanners.JAVA_LINE_COMMENT, Scanners.JAVA_BLOCK_COMMENT, Scanners.WHITESPACES).skipMany();
	
	//Parser para operadores, numeros e identificadores
	@SuppressWarnings("unchecked")
	private Parser<?> TOKENIZER = Parsers.or
			(Terminals.DecimalLiteral.TOKENIZER,
			(Parser<Fragment>) OPERATORS.tokenizer(),
			Terminals.Identifier.TOKENIZER,
			Terminals.CharLiteral.PARSER,
			Terminals.StringLiteral.PARSER);
	
	//Parser para conseguir el identifier
	private Parser<GAL_InterpreterNode> IDENTIFIER= Terminals.Identifier.PARSER.map(new Map<String,GAL_InterpreterNode>(){
		public GAL_InterpreterNode map(String arg){
			for(int i=0;i<constantNames.length;i++){
				if(constantNames[i].equals(arg)){
					return new GAL_InterpreterNode(new GAL_InterpreterNode[]{new GAL_InterpreterLeaf((double)i,39,interpreter)},48,interpreter);
				}
			}
			for(int i=0;i<variablesNames.size();i++){
				if(variablesNames.get(i).equals(arg)){
					return new GAL_InterpreterNode(new GAL_InterpreterNode[]{new GAL_InterpreterLeaf((double)(i+constantNames.length),39,interpreter)},48,interpreter);
				}
			}
			throw new RuntimeException("");
		}
	});
	
	//Verdadero parser para identifiers
	private Parser<GAL_InterpreterNode> geneIdentifier(){
		return term("$").next(IDENTIFIER);
	}
	
	private Parser<GAL_InterpreterNode> numberIdentifier(Parser<GAL_InterpreterNode> calc){
		return term("$").next(Parsers.between(term("{"), calc,term("}"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode a){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a},48,interpreter);
			}
		});
	}
	
	Parser<GAL_InterpreterNode> identifier(Parser<GAL_InterpreterNode> calc){
		return Parsers.or(geneIdentifier(),numberIdentifier(calc));
	}
	
	//Parser para convertir en token los operadores
	private Parser<?> term(String... names) {
		return OPERATORS.token(names);
	}
	
	//Parser para definir las operaciones
	private <T> Parser<T> op(String name, T value) {
		return term(name).retn(value);
	}
	
	//Parser para calculos aritmeticos simples
	private Parser<GAL_InterpreterNode> calculator(Parser<GAL_InterpreterNode> atom) {
		Parser.Reference<GAL_InterpreterNode> ref = Parser.newReference();
		Parser<GAL_InterpreterNode> unit = ref.lazy().between(term("("), term(")")).or(atom);
		Parser<GAL_InterpreterNode> parser = new OperatorTable<GAL_InterpreterNode>()
				.infixl(op("+", BinaryOperator.plus), 10)
				.infixl(op("-", BinaryOperator.minus), 10)
				.infixl(op("**", BinaryOperator.power),30)
				.infixl(op("*", BinaryOperator.multiply), 20)
				.infixl(op("/", BinaryOperator.divide), 20)
				.infixl(op("%", BinaryOperator.mod), 20)
				.prefix(op("-", UnaryOperator.negative), 40)
				.build(unit);
		ref.set(parser);
		return parser;
	}

	//Operadores aritmeticos complejos
	private Parser<GAL_InterpreterNode> Abs(Parser<GAL_InterpreterNode> calc){
		return term("Abs").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},14,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Max(Parser<GAL_InterpreterNode> calc){
		return term("Max").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},6,interpreter);
			}
		});
	}

	private Parser<GAL_InterpreterNode> Min(Parser<GAL_InterpreterNode> calc){
		return term("Min").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},7,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Root(Parser<GAL_InterpreterNode> calc){
		return term("Root").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},8,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Log(Parser<GAL_InterpreterNode> calc){
		return term("Log").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},9,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Sin(Parser<GAL_InterpreterNode> calc){
		return term("Sin").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},15,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Cos(Parser<GAL_InterpreterNode> calc){
		return term("Cos").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},16,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Tan(Parser<GAL_InterpreterNode> calc){
		return term("Tan").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},17,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Asin(Parser<GAL_InterpreterNode> calc){
		return term("Asin").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},18,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Acos(Parser<GAL_InterpreterNode> calc){
		return term("Acos").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},19,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Atan(Parser<GAL_InterpreterNode> calc){
		return term("Atan").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},20,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Hypot(Parser<GAL_InterpreterNode> calc){
		return term("Hypot").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},10,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Grad(Parser<GAL_InterpreterNode> calc){
		return term("toDegrees").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},21,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Rad(Parser<GAL_InterpreterNode> calc){
		return term("toRadians").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},22,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Round(Parser<GAL_InterpreterNode> calc){
		return term("Round").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},23,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Ceil(Parser<GAL_InterpreterNode> calc){
		return term("Ceil").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},24,interpreter);
			}
		});
	}

	private Parser<GAL_InterpreterNode> Floor(Parser<GAL_InterpreterNode> calc){
		return term("Floor").next(Parsers.between(term("("), calc, term(")"))).map(new Map<GAL_InterpreterNode,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},25,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> RandI(Parser<GAL_InterpreterNode> calc){
		return term("RandI").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},11,interpreter);
			}
		});
	}

	private Parser<GAL_InterpreterNode> RandD(Parser<GAL_InterpreterNode> calc){
		return term("RandD").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},12,interpreter);
			}
		});
	}

	private Parser<GAL_InterpreterNode> RandB(){
		return term("RandB").map(new Map< Object, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Object arg){
				return new GAL_InterpreterLeaf(46, interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> RandC(){
		return term("RandC").next(Parsers.between(term("("),  Parsers.tuple(CHAR,term(",").next(CHAR)), term(")"))).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},47,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Bin2Int(Parser<GAL_InterpreterNode> calc){
		Parser<List<GAL_InterpreterNode>> calc2= Parsers.tuple(calc,term(",").next(calc).many()).map(new Map<Pair<GAL_InterpreterNode,List<GAL_InterpreterNode>>,List<GAL_InterpreterNode>>(){
			public List<GAL_InterpreterNode> map(Pair<GAL_InterpreterNode,List<GAL_InterpreterNode>> arg){
				arg.b.add(0, arg.a);
				return arg.b;
			}
		});
		return term("Bin2Int").next(Parsers.between(term("("), calc2, term(")"))).map(new Map<List<GAL_InterpreterNode>,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(List<GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(arg.toArray(new GAL_InterpreterNode[0]),52,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> Pos(Parser<GAL_InterpreterNode> identifier){
		return term("Pos").next(Parsers.between(term("("), identifier, term(")"))).map(new Map<GAL_InterpreterNode, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(GAL_InterpreterNode arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg},53,interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> complexOperators(Parser<GAL_InterpreterNode> calc){
		Parser<GAL_InterpreterNode> complex= Parsers.or(Abs(calc),Max(calc),Min(calc),Root(calc),Log(calc));
		Parser<GAL_InterpreterNode> trig= Parsers.or(Sin(calc),Cos(calc),Tan(calc),Asin(calc),Acos(calc),Atan(calc),Rad(calc),Grad(calc),Hypot(calc));
		Parser<GAL_InterpreterNode> otros= Parsers.or(Round(calc),Ceil(calc),Floor(calc),RandI(calc),RandD(calc),RandB(),Bin2Int(calc));
		return Parsers.or(complex,trig,otros);
	}

	private Parser<GAL_InterpreterNode> identifier;
	
	private Parser<GAL_InterpreterNode> allAritmeticOperators(){
		Parser.Reference<GAL_InterpreterNode> complex_ref= Parser.newReference();
		Parser.Reference<GAL_InterpreterNode> simple_ref= Parser.newReference();
		Parser.Reference<GAL_InterpreterNode> pos_ref= Parser.newReference();
		Parser<GAL_InterpreterNode> op= Parsers.or(simple_ref.lazy(),complex_ref.lazy(),pos_ref.lazy());
		Parser<GAL_InterpreterNode> complex= complexOperators(op);
		identifier= identifier(op);
		Parser<GAL_InterpreterNode> pos= Pos(identifier);
		pos_ref.set(pos);
		Parser<GAL_InterpreterNode> simpleCalc= calculator(Parsers.or(identifier,NUMBER,complex,pos));
		simple_ref.set(simpleCalc);
		complex_ref.set(complex);
		return op;
	}
	
	Parser<GAL_InterpreterNode> CALCULATOR= allAritmeticOperators();
	
	//Parser ara chars
	Parser<GAL_InterpreterNode> ch= Parsers.token(new TokenMap<GAL_InterpreterNode>(){
		public GAL_InterpreterNode map(Token aux){
			return new GAL_InterpreterLeaf(aux.toString().charAt(0),40, interpreter);
		}
	});
	
	Parser<GAL_InterpreterNode> CHAR= Parsers.between(term("'"), ch, term("'")).or(RandC());
	
	//Parser para string
	Parser<GAL_InterpreterNode> st= Parsers.token(new TokenMap<GAL_InterpreterNode>(){
		public GAL_InterpreterNode map(Token aux){
			return new GAL_InterpreterLeaf(aux.toString(),41, interpreter);
		}
	});
	
	Parser<GAL_InterpreterNode> STRING= Parsers.between(term("\""), st, term("\""));
	
	private Parser<GAL_InterpreterNode> COMPARABLES = Parsers.or(CHAR,STRING,CALCULATOR,identifier);
	
	//Operadores de comparacion
	private Parser<GAL_InterpreterNode> lessThan(){
		return Parsers.tuple(COMPARABLES,term("<").next(COMPARABLES)).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b}, 27, interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> moreThan(){
		return Parsers.tuple(COMPARABLES,term(">").next(COMPARABLES)).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b}, 26, interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> equalThan(){
		return Parsers.tuple(COMPARABLES,term("=").next(COMPARABLES)).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b}, 28, interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> moreOrEqualThan(){
		return Parsers.tuple(COMPARABLES,term(">=").next(COMPARABLES)).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b}, 29, interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> lessOrEqualThan(){
		return Parsers.tuple(COMPARABLES,term("<=").next(COMPARABLES)).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b}, 30, interpreter);
			}
		});
	}
	
	private Parser<GAL_InterpreterNode> notEqualThan(){
		return Parsers.tuple(COMPARABLES,term("<>").next(COMPARABLES)).map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b}, 31, interpreter);
			}
		});
	}
	
	//Parser para todas las comparaciones
	private Parser<GAL_InterpreterNode> comparer(){
		return Parsers.or(lessThan(),moreThan(),equalThan(),lessOrEqualThan(),moreOrEqualThan(),notEqualThan());
	}

	//Parser estatico de comparaciones
	private Parser<GAL_InterpreterNode> COMPARER = comparer();
	
	//Operadores binarios con Boolean
	enum binaryLogicalOperator implements Binary<GAL_InterpreterNode> {
		and {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},32,interpreter);
			}
		},
		or {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},33,interpreter);
			}
		},
		xor {
			public GAL_InterpreterNode map(GAL_InterpreterNode a, GAL_InterpreterNode b) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{a,b},34,interpreter);
			}
		},
	}
	
	enum unaryLogicalOperator implements Unary<GAL_InterpreterNode>{
		not{
			public GAL_InterpreterNode map(GAL_InterpreterNode n) {
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{n},35,interpreter);
			}
		}
	}
	
	//Parser para calculos logicos simples
	private Parser<GAL_InterpreterNode> logical(Parser<GAL_InterpreterNode> atom) {
		Parser.Reference<GAL_InterpreterNode> ref = Parser.newReference();
		Parser<GAL_InterpreterNode> unit = ref.lazy().between(term("("), term(")")).or(atom);
		Parser<GAL_InterpreterNode> parser = new OperatorTable<GAL_InterpreterNode>()
				.infixl(op("&&", binaryLogicalOperator.and), 10)
				.infixl(op("||", binaryLogicalOperator.or), 10)
				.infixl(op("^", binaryLogicalOperator.xor),10)
				.prefix(op("not", unaryLogicalOperator.not), 40)
				.build(unit);
		ref.set(parser);
		return parser;
	}

	//Parser estatico de operadores logicos
	private Parser<GAL_InterpreterNode> LOGICAL = logical(Parsers.or(COMPARER,True,False,identifier));
	
	//Parser para if_then_else
	private Parser<GAL_InterpreterNode> if_then_else(final Parser<GAL_InterpreterNode> exec){
		Parser<GAL_InterpreterNode> IFTHENELSE= Parsers.between(term("If"),Parsers.tuple(LOGICAL, term("Then").next(exec), term("Else").next(exec)),term("End")).
			map(new Map< Tuple3<GAL_InterpreterNode,GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
				public GAL_InterpreterNode map(Tuple3<GAL_InterpreterNode,GAL_InterpreterNode,GAL_InterpreterNode> arg){
					return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b,arg.c},37,interpreter);
				}
			});
		Parser<GAL_InterpreterNode> IFTHEN= Parsers.between(term("If"), Parsers.tuple(LOGICAL, term("Then").next(exec)), term("End")).
			map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
				public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
					return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},36,interpreter);
				}
			});
		return Parsers.or(IFTHENELSE, IFTHEN);
	}
	
	//Parser para el While
	private Parser<GAL_InterpreterNode> while_cond(final Parser<GAL_InterpreterNode> exec){
		return Parsers.between(term("While"), Parsers.tuple(LOGICAL,term("Do").next(exec)), term("End")).
			map(new Map<Pair<GAL_InterpreterNode,GAL_InterpreterNode> , GAL_InterpreterNode>(){
				public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
					return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},38,interpreter);
				}
			});
	}
	
	private Parser<GAL_InterpreterNode> Factory(Parser<GAL_InterpreterNode> exec){
		Parser<String> factoryIdentifier= term("$").next(Terminals.Identifier.PARSER);
		return term("Array").next(Parsers.between(term("("), Parsers.tuple(factoryIdentifier, term(",").next(Parsers.between(term("{"), exec.many1(), term("}")))), term(")"))).
			map(new Map< Pair<String,List<GAL_InterpreterNode>>, GAL_InterpreterNode>(){
				public GAL_InterpreterNode map(Pair<String,List<GAL_InterpreterNode>> arg){
					int i, j, size= arg.b.size();
					GAL_InterpreterNode[] aux= new GAL_InterpreterNode[size];
					for(i=0;i<size;i++){
						aux[i]=null;
						for(j=0;j<constantNames.length;j++)
							if(constantNames[j].equals(arg.a+"_"+i)){
								aux[i]= new GAL_InterpreterNode(new GAL_InterpreterNode[]{
										new GAL_InterpreterLeaf((double)j, 39, interpreter),arg.b.get(i)},49,interpreter);
								break;
							}
						for(j=0;j<variablesNames.size();j++)
							if(variablesNames.get(j).equals(arg.a+"_"+i)){
								aux[i]= new GAL_InterpreterNode(new GAL_InterpreterNode[]{
										new GAL_InterpreterLeaf((double)(j+constantNames.length), 39, interpreter),arg.b.get(i)},49,interpreter);
								break;
							}
						if(aux[i]==null){
							variablesNames.add(arg.a+"_"+i);
							variables.add(null);
							aux[i]= new GAL_InterpreterNode(new GAL_InterpreterNode[]{
									new GAL_InterpreterLeaf((double)(j+constantNames.length), 39, interpreter),arg.b.get(i)},49,interpreter);
						}
					}
					return new GAL_InterpreterNode(aux, 51, interpreter);
				}
			});
	}
	
	//un parser que dado un identificador, consigue la posicion en el arreglo de variables, usado para la asignacion
	private Parser<GAL_InterpreterNode> assignIdentifier(){
		Parser<GAL_InterpreterNode> id= Terminals.Identifier.PARSER.map(new Map<String,GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(String arg){
				int i;
				for(i=0;i<constantNames.length;i++)
					if(constantNames[i].equals(arg))
						return new GAL_InterpreterLeaf((double)i, 39, interpreter);
				for(i=0;i<variablesNames.size();i++)
					if(variablesNames.get(i).equals(arg))
						return new GAL_InterpreterLeaf((double)(i+constantNames.length), 39, interpreter);
				variablesNames.add(arg);
				variables.add(null);
				return new GAL_InterpreterLeaf((double)(i+constantNames.length), 39, interpreter);
			}
		});
		Parser<GAL_InterpreterNode> calculator= Parsers.between(term("{"),CALCULATOR,term("}"));
		return term("$").next(Parsers.or(id,calculator));
	}
	
	//Asignacion de variables
	private Parser<GAL_InterpreterNode> asignToVariable(Parser<GAL_InterpreterNode> exec){
		return Parsers.tuple(assignIdentifier(), term(":=").next(exec))
		.map(new Map< Pair<GAL_InterpreterNode,GAL_InterpreterNode>, GAL_InterpreterNode>(){
			public GAL_InterpreterNode map(Pair<GAL_InterpreterNode,GAL_InterpreterNode> arg){
				return new GAL_InterpreterNode(new GAL_InterpreterNode[]{arg.a,arg.b},49,interpreter);
			}
		});
	}
	
	//Creacion del executer con If
	private Parser<GAL_InterpreterNode> executer(){
		Parser.Reference<GAL_InterpreterNode> while_ref= Parser.newReference();
		Parser.Reference<GAL_InterpreterNode> if_ref= Parser.newReference();
		Parser.Reference<GAL_InterpreterNode> assign_ref= Parser.newReference();
		Parser.Reference<GAL_InterpreterNode> factory_ref= Parser.newReference();
		//El map obliga a que lo ultimo que se evalue sea el retorno
		Parser<GAL_InterpreterNode> exec= Parsers.or(Parsers.tuple(while_ref.lazy(),term(";")),Parsers.tuple(if_ref.lazy(),term(";")),assign_ref.lazy(),Parsers.tuple(factory_ref.lazy(),term(";")),
			Parsers.tuple(LOGICAL,term(";")),Parsers.tuple(CALCULATOR,term(";")),Parsers.tuple(identifier,term(";"))).many1().map(new Map< List<?>, GAL_InterpreterNode>(){
			@SuppressWarnings("unchecked")
			public GAL_InterpreterNode map(List<?> arg){
				if(arg.size()==1){
					if(arg.get(0) instanceof Pair<?, ?>)
						return ((Pair<GAL_InterpreterNode,Object>)arg.get(0)).a;
					else
						return (GAL_InterpreterNode)arg.get(0);
				}
				GAL_InterpreterNode ret= new GAL_InterpreterNode(50, interpreter);
				GAL_InterpreterNode aux= ret;
				int i;
				for(i=0;i<arg.size()-2;i++){
					if(arg.get(i) instanceof Pair<?, ?>){
						aux.setHijos(new GAL_InterpreterNode[]{((Pair<GAL_InterpreterNode,Object>)arg.get(i)).a,new GAL_InterpreterNode(50,interpreter)});
					}else{
						aux.setHijos(new GAL_InterpreterNode[]{(GAL_InterpreterNode)arg.get(i),new GAL_InterpreterNode(50,interpreter)});
					}
					aux= aux.getHijo(1);
				}
				if(arg.get(i) instanceof Pair<?,?>){
					if(arg.get(i+1) instanceof Pair<?,?>){
						aux.setHijos(new GAL_InterpreterNode[]{((Pair<GAL_InterpreterNode,Object>)arg.get(i)).a,((Pair<GAL_InterpreterNode,Object>)arg.get(i+1)).a});
					}else{
						aux.setHijos(new GAL_InterpreterNode[]{((Pair<GAL_InterpreterNode,Object>)arg.get(i)).a,(GAL_InterpreterNode)arg.get(i+1)});
					}
				}else{
					if(arg.get(i+1) instanceof Pair<?,?>){
						aux.setHijos(new GAL_InterpreterNode[]{(GAL_InterpreterNode)arg.get(i),((Pair<GAL_InterpreterNode,Object>)arg.get(i+1)).a});
					}else{
						aux.setHijos(new GAL_InterpreterNode[]{(GAL_InterpreterNode)arg.get(i),(GAL_InterpreterNode)arg.get(i+1)});
					}
				}
				return ret;
			}
		});
		
		Parser<GAL_InterpreterNode> While= while_cond(exec);
		while_ref.set(While);
		
		Parser<GAL_InterpreterNode> If= if_then_else(exec);
		if_ref.set(If);
		
		Parser<GAL_InterpreterNode> Factory= Factory(Parsers.or(Parsers.tuple(LOGICAL,term(";")),Parsers.tuple(CALCULATOR,term(";")),Parsers.tuple(STRING,term(";")),Parsers.tuple(CHAR,term(";")),Parsers.tuple(identifier,term(";")))
			.map(new Map< Pair<?,?>, GAL_InterpreterNode>(){
				public GAL_InterpreterNode map(Pair<?,?> arg){
					return (GAL_InterpreterNode) arg.a;
				}
			}));
		factory_ref.set(Factory);
		
		Parser<GAL_InterpreterNode> assign= asignToVariable(Parsers.or(Parsers.tuple(LOGICAL,term(";")),Parsers.tuple(CALCULATOR,term(";")),Parsers.tuple(STRING,term(";")),Parsers.tuple(CHAR,term(";")),Parsers.tuple(identifier,term(";")))
			.map(new Map< Pair<?,?>, GAL_InterpreterNode>(){
				public GAL_InterpreterNode map(Pair<?,?> arg){
					return (GAL_InterpreterNode) arg.a;
				}
			}));
		assign_ref.set(assign);
		
		return exec;
	}

	//Parser que se llamara para ejecutar los calculos
	private Parser<GAL_InterpreterNode> EXECUTER= executer().from(TOKENIZER,IGNORED);
	
	public GAL_InterpreterParser(String[] constantNames) {
		this.constantNames= constantNames;
		constantValues= new Object[0];
		variables= new LinkedList<Object>();
		variablesNames= new LinkedList<String>();
	}
	
	public GAL_InterpreterNode parseFitness(String source, GAL_Chromosome chromosome)throws ClassCastException{
		constantValues= new Object[chromosome.size()];
		for(int i=0;i<constantValues.length;i++)
			constantValues[i]= chromosome.getTrait(i);
		
		//System.out.println(TOKENIZER.sepEndBy(IGNORED).parse(source).toString());
		interpreter= 0;
		return EXECUTER.parse(source);
	}
	
	public GAL_InterpreterNode parseTermination(String source, GAL_Population[] window)throws ClassCastException{
		constantValues= new Object[window.length*2+1];
		int i;
		constantValues[0]= window[0].size();
		for(i=1;i<constantValues.length;i+=2){
			constantValues[i]= window[i/2].getBestChromosome().getFitness();
			constantValues[i+1]= window[i/2].getTotalFitness();
		}
		interpreter= 1;
		return EXECUTER.parse(source);
	}
	
	public Object[] getAllVariables(){
		Object[] ret= new Object[constantValues.length+variables.size()];
		int i;
		for(i=0;i<constantValues.length;i++)
			ret[i]= constantValues[i];
		for(i=0;i<variables.size();i++)
			ret[i+constantValues.length]= variables.get(i);
		return ret;
	}
}
