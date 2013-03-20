package interfaceJGAL;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.codehaus.jparsec.functors.*;
import org.codehaus.jparsec.*;
import org.codehaus.jparsec.Tokens.Fragment;
import JGAL.GAL_Chromosome;
import JGAL.GAL_Population;

public class CopyOfGAL_InterpreterParser {

	private Random rand;
	private LinkedList<Object> variables;
	private LinkedList<String> variablesNames;
	private String[] constantNames;
	private Object[] constantValues;
	//private int interpreter;
	
	//Operadores binarios con Doubles
	enum BinaryOperator implements Binary<Double> {
		plus {
			public Double map(Double a, Double b) {
				return a + b;
			}
		},
		minus {
			public Double map(Double a, Double b) {
				return a - b;
			}
		},
		multiply {
			public Double map(Double a, Double b) {
				return a * b;
			}
		},
		divide {
			public Double map(Double a, Double b) {
				return a / b;
			}
		},
		mod {
			public Double map(Double a, Double b) {
				return a % b;
			}
		},
		power {
			public Double map(Double a, Double b){
				return Math.pow(a, b);
			}
		}
	}
	
	//Operadores Unarios con Doubles
	enum UnaryOperator implements Unary<Double> {
		negative {
			public Double map(Double n) {
				return -n;
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
							"$", ":=", //Variables
							"'","\"",",","(", ")","{","}",";" //Caracteres del Lenguaje
							);
	
	//Constantes
	private Parser<Boolean> False= term("False").map(new Map<Object,Boolean>(){
		public Boolean map(Object a){
			return false;
		}
	});
	
	private Parser<Double> PI= term("PI").map(new Map<Object,Double>(){
		public Double map(Object a){
			return Math.PI;
		}
	});

	private Parser<Double> E= term("E").map(new Map<Object,Double>(){
		public Double map(Object a){
			return Math.E;
		}
	});
	
	//Parser para los Numeros
	private Parser<Double> NUMBER = Terminals.DecimalLiteral.PARSER.map(new Map<String, Double>() {
		public Double map(String s) {
			return Double.valueOf(s);
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
	private Parser<Object> IDENTIFIER= Terminals.Identifier.PARSER.map(new Map<String,Object>(){
		public Object map(String arg){
			for(int i=0;i<constantNames.length;i++){
				if(constantNames[i].equals(arg)){
					return constantValues[i];
				}
			}
			//System.out.println("Identifier: " + variablesNames.toString());
			for(int i=0;i<variablesNames.size();i++){
				if(variablesNames.get(i).equals(arg)){
					return variables.get(i);
				}
			}
			throw new RuntimeException("");
		}
	});
	
	//Verdadero parser para identifiers
	private Parser<Object> geneIdentifier(){
		return term("$").next(IDENTIFIER);
	}
	
	private Parser<Object> numberIdentifier(Parser<Double> calc){
		return term("$").next(Parsers.between(term("{"), calc,term("}"))).map(new Map<Double,Object>(){
			public Object map(Double a){
				if (a.doubleValue() - a.intValue() > 0.000000001)
					throw new RuntimeException("");
				int aux= a.intValue();
				if(aux < 0){
					aux+= constantValues.length+variables.size();
					if(aux<0)
						throw new RuntimeException("");
				}
				if(aux >= constantValues.length){
					aux-= constantValues.length;
					if(aux >= variables.size())
						throw new RuntimeException("");
					return variables.get(aux);
				}
				return constantValues[aux];
			}
		});
	}
	
	Parser<Object> identifier(Parser<Double> calc){
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
	private Parser<Double> calculator(Parser<Double> atom) {
		Parser.Reference<Double> ref = Parser.newReference();
		Parser<Double> unit = ref.lazy().between(term("("), term(")")).or(atom);
		Parser<Double> parser = new OperatorTable<Double>()
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
	private Parser<Double> Abs(Parser<Double> calc){
		return term("Abs").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.abs(arg);
			}
		});
	}
	
	private Parser<Double> Max(Parser<Double> calc){
		return term("Max").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<Double,Double>, Double>(){
			public Double map(Pair<Double,Double> arg){
				return Math.max(arg.a,arg.b);
			}
		});
	}

	private Parser<Double> Min(Parser<Double> calc){
		return term("Min").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<Double,Double>, Double>(){
			public Double map(Pair<Double,Double> arg){
				return Math.min(arg.a,arg.b);
			}
		});
	}
	
	private Parser<Double> Root(Parser<Double> calc){
		return term("Root").next(Parsers.between(term("("), Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<Double,Double>, Double>(){
			public Double map(Pair<Double,Double> arg){
				return Math.pow(arg.a,1.0/arg.b);
			}
		});
	}
	
	private Parser<Double> Log(Parser<Double> calc){
		return term("Log").next(Parsers.between(term("("),  Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<Double,Double>, Double>(){
			public Double map(Pair<Double,Double> arg){
				return Math.log(arg.a)/Math.log(arg.b);
			}
		});
	}
	
	private Parser<Double> Sin(Parser<Double> calc){
		return term("Sin").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.sin(arg);
			}
		});
	}
	
	private Parser<Double> Cos(Parser<Double> calc){
		return term("Cos").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.cos(arg);
			}
		});
	}
	
	private Parser<Double> Tan(Parser<Double> calc){
		return term("Tan").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.tan(arg);
			}
		});
	}
	
	private Parser<Double> Asin(Parser<Double> calc){
		return term("Asin").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.asin(arg);
			}
		});
	}
	
	private Parser<Double> Acos(Parser<Double> calc){
		return term("Acos").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.acos(arg);
			}
		});
	}
	
	private Parser<Double> Atan(Parser<Double> calc){
		return term("Atan").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.atan(arg);
			}
		});
	}
	
	private Parser<Double> Hypot(Parser<Double> calc){
		return term("Hypot").next(Parsers.between(term("("),  Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<Double,Double>, Double>(){
			public Double map(Pair<Double,Double> arg){
				return Math.hypot(arg.a, arg.b);
			}
		});
	}
	
	private Parser<Double> Grad(Parser<Double> calc){
		return term("toDegrees").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.toDegrees(arg);
			}
		});
	}
	
	private Parser<Double> Rad(Parser<Double> calc){
		return term("toRadians").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.toRadians(arg);
			}
		});
	}
	
	private Parser<Double> Round(Parser<Double> calc){
		return term("Round").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return (double) Math.round(arg);
			}
		});
	}
	
	private Parser<Double> Ceil(Parser<Double> calc){
		return term("Ceil").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.ceil(arg);
			}
		});
	}

	private Parser<Double> Floor(Parser<Double> calc){
		return term("Floor").next(Parsers.between(term("("), calc, term(")"))).map(new Map<Double,Double>(){
			public Double map(Double arg){
				return Math.floor(arg);
			}
		});
	}
	
	private Parser<Double> RandI(Parser<Double> calc){
		return term("RandI").next(Parsers.between(term("("),  Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<Double,Double>, Double>(){
			public Double map(Pair<Double,Double> arg){
				return (double) (rand.nextInt(arg.b.intValue()-arg.a.intValue()) + arg.a.intValue());
			}
		});
	}

	private Parser<Double> RandD(Parser<Double> calc){
		return term("RandD").next(Parsers.between(term("("),  Parsers.tuple(calc,term(",").next(calc)), term(")"))).map(new Map< Pair<Double,Double>, Double>(){
			public Double map(Pair<Double,Double> arg){
				return rand.nextDouble()*(arg.b-arg.a) + arg.a;
			}
		});
	}

	private Parser<Double> RandB(){
		return term("RandB").map(new Map< Object, Double>(){
			public Double map(Object arg){
				return rand.nextDouble()>0.5?1.0:0.0;
			}
		});
	}
	
	private Parser<Character> RandC(){
		return term("RandC").next(Parsers.between(term("("),  Parsers.tuple(CHAR,term(",").next(CHAR)), term(")"))).map(new Map< Pair<Character,Character>, Character>(){
			public Character map(Pair<Character,Character> arg){
				return (char)(rand.nextInt((int)arg.b-(int)arg.a) + (int)arg.a);
			}
		});
	}
	
	private Parser<Double> complexOperators(Parser<Double> calc){
		Parser<Double> complex= Parsers.or(Abs(calc),Max(calc),Min(calc),Root(calc),Log(calc));
		Parser<Double> trig= Parsers.or(Sin(calc),Cos(calc),Tan(calc),Asin(calc),Acos(calc),Atan(calc),Rad(calc),Grad(calc),Hypot(calc));
		Parser<Double> otros= Parsers.or(Round(calc),Ceil(calc),Floor(calc),RandI(calc),RandD(calc),RandB());
		return Parsers.or(complex,trig,otros);
	}

	private Parser<Object> identifier;
	
	private Parser<Double> allAritmeticOperators(){
		Parser.Reference<Double> complex_ref= Parser.newReference();
		Parser.Reference<Double> simple_ref= Parser.newReference();
		Parser<Double> op= Parsers.or(simple_ref.lazy(),complex_ref.lazy());
		Parser<Double> complex= complexOperators(op);
		identifier= identifier(op);
		Parser<Double> numericIdentifier= identifier.map(new Map<Object, Double> (){
			public Double map(Object arg){
				if(arg instanceof Double)
					return (Double) arg;
				if(arg instanceof Integer)
					return ((Integer) arg).doubleValue();
				if(arg instanceof Short)
					return ((Short) arg).doubleValue();
				return null;
			}
		});
		Parser<Double> simpleCalc= calculator(Parsers.or(numericIdentifier,NUMBER,complex));
		simple_ref.set(simpleCalc);
		complex_ref.set(complex);
		
		return op;
	}
	
	Parser<Double> CALCULATOR= allAritmeticOperators();
	
	//Parser ara chars
	Parser<Character> ch= Parsers.token(new TokenMap<Character>(){
		public Character map(Token aux){
			return aux.toString().charAt(0);
		}
	});
	
	Parser<Character> CHAR= Parsers.between(term("'"), ch, term("'")).or(RandC());
	
	//Parser para string
	Parser<String> st= Parsers.token(new TokenMap<String>(){
		public String map(Token aux){
			return aux.toString();
		}
	});
	
	Parser<String> STRING= Parsers.between(term("\""), st, term("\""));
	
	private Parser<Object> COMPARABLES = Parsers.or(identifier,CHAR,STRING,CALCULATOR);
	
	//Operadores de comparacion
	private Parser<Boolean> lessThan(){
		return Parsers.tuple(COMPARABLES,term("<").next(COMPARABLES)).map(new Map< Pair<Object,Object>, Boolean>(){
			public Boolean map(Pair<Object,Object> arg){
				try{
					if(arg.a instanceof Double){
						if(arg.b instanceof Double)
							return ((Double) arg.a).compareTo((Double) arg.b) < 0;
						if(arg.b instanceof Integer)
							return ((Double) arg.a).compareTo(((Integer) arg.b).doubleValue()) < 0;
						if(arg.b instanceof Short)
							return ((Double) arg.a).compareTo(((Short) arg.b).doubleValue()) < 0;
					}
					if(arg.a instanceof Integer){
						if(arg.b instanceof Integer)
							return ((Integer) arg.a).compareTo((Integer) arg.b) < 0;
						if(arg.b instanceof Double)
							return ((Integer) arg.a).compareTo(((Double) arg.b).intValue()) < 0;
						if(arg.b instanceof Short)
							return ((Integer) arg.a).compareTo(((Short) arg.b).intValue()) < 0;
					}
					if(arg.a instanceof Short){
						if(arg.b instanceof Short)
							return ((Short) arg.a).compareTo((Short) arg.b) < 0;
						if(arg.b instanceof Double)
							return ((Short) arg.a).compareTo(((Double) arg.b).shortValue()) < 0;
						if(arg.b instanceof Integer)
							return ((Short) arg.a).compareTo(((Integer) arg.b).shortValue()) < 0;
					}
					if(arg.a instanceof Character)
						return ((Character) arg.a).compareTo((Character) arg.b) < 0;
					if(arg.a instanceof String)
						return ((String) arg.a).compareTo((String) arg.b) < 0;
				}catch(Exception e){
					throw new RuntimeException("");
				}
				return false;
			}
		});
	}
	
	private Parser<Boolean> moreThan(){
		return Parsers.tuple(COMPARABLES,term(">").next(COMPARABLES)).map(new Map< Pair<Object,Object>, Boolean>(){
			public Boolean map(Pair<Object,Object> arg){
				try{
					if(arg.a instanceof Double){
						if(arg.b instanceof Double)
							return ((Double) arg.a).compareTo((Double) arg.b) > 0;
						if(arg.b instanceof Integer)
							return ((Double) arg.a).compareTo(((Integer) arg.b).doubleValue()) > 0;
						if(arg.b instanceof Short)
							return ((Double) arg.a).compareTo(((Short) arg.b).doubleValue()) > 0;
					}
					if(arg.a instanceof Integer){
						if(arg.b instanceof Integer)
							return ((Integer) arg.a).compareTo((Integer) arg.b) > 0;
						if(arg.b instanceof Double)
							return ((Integer) arg.a).compareTo(((Double) arg.b).intValue()) > 0;
						if(arg.b instanceof Short)
							return ((Integer) arg.a).compareTo(((Short) arg.b).intValue()) > 0;
					}
					if(arg.a instanceof Short){
						if(arg.b instanceof Short)
							return ((Short) arg.a).compareTo((Short) arg.b) > 0;
						if(arg.b instanceof Double)
							return ((Short) arg.a).compareTo(((Double) arg.b).shortValue()) > 0;
						if(arg.b instanceof Integer)
							return ((Short) arg.a).compareTo(((Integer) arg.b).shortValue()) > 0;
					}
					if(arg.a instanceof Character)
						return ((Character) arg.a).compareTo((Character) arg.b) > 0;
					if(arg.a instanceof String)
						return ((String) arg.a).compareTo((String) arg.b) > 0;
				}catch(Exception e){
					throw new RuntimeException("");
				}
				return false;
			}
		});
	}
	
	private Parser<Boolean> equalThan(){
		return Parsers.tuple(COMPARABLES,term("=").next(COMPARABLES)).map(new Map< Pair<Object,Object>, Boolean>(){
			public Boolean map(Pair<Object,Object> arg){
				try{
					if(arg.a instanceof Double){
						if(arg.b instanceof Double)
							return ((Double) arg.a).compareTo((Double) arg.b) == 0;
						if(arg.b instanceof Integer)
							return ((Double) arg.a).compareTo(((Integer) arg.b).doubleValue()) == 0;
						if(arg.b instanceof Short)
							return ((Double) arg.a).compareTo(((Short) arg.b).doubleValue()) == 0;
					}
					if(arg.a instanceof Integer){
						if(arg.b instanceof Integer)
							return ((Integer) arg.a).compareTo((Integer) arg.b) == 0;
						if(arg.b instanceof Double)
							return ((Integer) arg.a).compareTo(((Double) arg.b).intValue()) == 0;
						if(arg.b instanceof Short)
							return ((Integer) arg.a).compareTo(((Short) arg.b).intValue()) == 0;
					}
					if(arg.a instanceof Short){
						if(arg.b instanceof Short)
							return ((Short) arg.a).compareTo((Short) arg.b) == 0;
						if(arg.b instanceof Double)
							return ((Short) arg.a).compareTo(((Double) arg.b).shortValue()) == 0;
						if(arg.b instanceof Integer)
							return ((Short) arg.a).compareTo(((Integer) arg.b).shortValue()) == 0;
					}
					if(arg.a instanceof Character)
						return ((Character) arg.a).compareTo((Character) arg.b) == 0;
					if(arg.a instanceof String)
						return ((String) arg.a).compareTo((String) arg.b) == 0;
				}catch(Exception e){
					throw new RuntimeException("");
				}
				return false;
			}
		});
	}
	
	private Parser<Boolean> moreOrEqualThan(){
		return Parsers.tuple(COMPARABLES,term(">=").next(COMPARABLES)).map(new Map< Pair<Object,Object>, Boolean>(){
			public Boolean map(Pair<Object,Object> arg){
				try{
					if(arg.a instanceof Double){
						if(arg.b instanceof Double)
							return ((Double) arg.a).compareTo((Double) arg.b) >= 0;
						if(arg.b instanceof Integer)
							return ((Double) arg.a).compareTo(((Integer) arg.b).doubleValue()) >= 0;
						if(arg.b instanceof Short)
							return ((Double) arg.a).compareTo(((Short) arg.b).doubleValue()) >= 0;
					}
					if(arg.a instanceof Integer){
						if(arg.b instanceof Integer)
							return ((Integer) arg.a).compareTo((Integer) arg.b) >= 0;
						if(arg.b instanceof Double)
							return ((Integer) arg.a).compareTo(((Double) arg.b).intValue()) >= 0;
						if(arg.b instanceof Short)
							return ((Integer) arg.a).compareTo(((Short) arg.b).intValue()) >= 0;
					}
					if(arg.a instanceof Short){
						if(arg.b instanceof Short)
							return ((Short) arg.a).compareTo((Short) arg.b) >= 0;
						if(arg.b instanceof Double)
							return ((Short) arg.a).compareTo(((Double) arg.b).shortValue()) >= 0;
						if(arg.b instanceof Integer)
							return ((Short) arg.a).compareTo(((Integer) arg.b).shortValue()) >= 0;
					}
					if(arg.a instanceof Character)
						return ((Character) arg.a).compareTo((Character) arg.b) >= 0;
					if(arg.a instanceof String)
						return ((String) arg.a).compareTo((String) arg.b) >= 0;
				}catch(Exception e){
					throw new RuntimeException("");
				}
				return false;
			}
		});
	}
	
	private Parser<Boolean> lessOrEqualThan(){
		return Parsers.tuple(COMPARABLES,term("<=").next(COMPARABLES)).map(new Map< Pair<Object,Object>, Boolean>(){
			public Boolean map(Pair<Object,Object> arg){
				try{
					if(arg.a instanceof Double){
						if(arg.b instanceof Double)
							return ((Double) arg.a).compareTo((Double) arg.b) <= 0;
						if(arg.b instanceof Integer)
							return ((Double) arg.a).compareTo(((Integer) arg.b).doubleValue()) <= 0;
						if(arg.b instanceof Short)
							return ((Double) arg.a).compareTo(((Short) arg.b).doubleValue()) <= 0;
					}
					if(arg.a instanceof Integer){
						if(arg.b instanceof Integer)
							return ((Integer) arg.a).compareTo((Integer) arg.b) <= 0;
						if(arg.b instanceof Double)
							return ((Integer) arg.a).compareTo(((Double) arg.b).intValue()) <= 0;
						if(arg.b instanceof Short)
							return ((Integer) arg.a).compareTo(((Short) arg.b).intValue()) <= 0;
					}
					if(arg.a instanceof Short){
						if(arg.b instanceof Short)
							return ((Short) arg.a).compareTo((Short) arg.b) <= 0;
						if(arg.b instanceof Double)
							return ((Short) arg.a).compareTo(((Double) arg.b).shortValue()) <= 0;
						if(arg.b instanceof Integer)
							return ((Short) arg.a).compareTo(((Integer) arg.b).shortValue()) <= 0;
					}
					if(arg.a instanceof Character)
						return ((Character) arg.a).compareTo((Character) arg.b) <= 0;
					if(arg.a instanceof String)
						return ((String) arg.a).compareTo((String) arg.b) <= 0;
				}catch(Exception e){
					throw new RuntimeException("");
				}
				return false;
			}
		});
	}
	
	//Parser para todas las comparaciones
	private Parser<Boolean> comparer(){
		return Parsers.or(lessThan(),moreThan(),equalThan(),lessOrEqualThan(),moreOrEqualThan());
	}

	//Parser estatico de comparaciones
	private Parser<Boolean> COMPARER = comparer();
	
	//Operadores binarios con Boolean
	enum binaryLogicalOperator implements Binary<Boolean> {
		and {
			public Boolean map(Boolean a, Boolean b) {
				return a && b;
			}
		},
		or {
			public Boolean map(Boolean a, Boolean b) {
				return a || b;
			}
		},
		xor {
			public Boolean map(Boolean a, Boolean b) {
				return a ^ b;
			}
		},
	}
	
	enum unaryLogicalOperator implements Unary<Boolean>{
		not{
			public Boolean map(Boolean a) {
				return !a;
			}
		}
	}
	
	private Parser<Boolean> True= term("True").map(new Map<Object,Boolean>(){
		public Boolean map(Object a){
			return true;
		}
	});
	
	//Parser para calculos logicos simples
	private Parser<Boolean> logical(Parser<Boolean> atom) {
		Parser.Reference<Boolean> ref = Parser.newReference();
		Parser<Boolean> unit = ref.lazy().between(term("("), term(")")).or(atom);
		Parser<Boolean> parser = new OperatorTable<Boolean>()
				.infixl(op("&&", binaryLogicalOperator.and), 10)
				.infixl(op("||", binaryLogicalOperator.or), 10)
				.infixl(op("^", binaryLogicalOperator.xor),10)
				.prefix(op("not", unaryLogicalOperator.not), 40)
				.build(unit);
		ref.set(parser);
		return parser;
	}
	
	private Parser<Boolean> logicalIdentifier= identifier.map(new Map<Object, Boolean> (){
		public Boolean map(Object arg){
			if(arg instanceof Boolean)
				return (Boolean) arg;
			return null;
		}
	});
	
	//Parser estatico de operadores logicos
	private Parser<Boolean> LOGICAL = logical(Parsers.or(COMPARER,True,False,logicalIdentifier));
	
	//Parser para if_then_else
	private Parser<Object> if_then_else(final Parser<Object> exec, Parser<Object> f_exec){
		Parser<Object> IFTHENELSE= Parsers.between(term("If"),Parsers.tuple(LOGICAL, term("Then").next(f_exec.source()), term("Else").next(f_exec.source())),term("End")).
			map(new Map< Tuple3<Boolean,String,String>, Object>(){
				public Object map(Tuple3<Boolean,String,String> arg){
					if(arg.a)
						return exec.from(TOKENIZER,IGNORED).parse(arg.b);
					return exec.from(TOKENIZER,IGNORED).parse(arg.c);
				}
			});
		Parser<Object> IFTHEN= Parsers.between(term("If"), Parsers.tuple(LOGICAL, term("Then").next(f_exec.source())), term("End")).
			map(new Map< Pair<Boolean,String>, Object>(){
				public Object map(Pair<Boolean,String> arg){
					if(arg.a)
						return exec.from(TOKENIZER,IGNORED).parse(arg.b);
					return null;
				}
			});
		return Parsers.or(IFTHENELSE, IFTHEN);
	}
	
	//Parser para el While
	private Parser<Object> while_cond(final Parser<Object> exec, Parser<Object> f_exec){
		return Parsers.between(term("While"), Parsers.tuple(LOGICAL.source(),term("Do").next(f_exec.source())), term("End")).
			map(new Map<Pair<String,String> , Object>(){
				public Object map(Pair<String,String> arg){
					Object ret=null;
					while((boolean) LOGICAL.from(TOKENIZER,IGNORED).parse(arg.a))
						ret= exec.from(TOKENIZER,IGNORED).parse(arg.b);
					return ret;
				}
			});
	}
	
	//un parser que dado un identificador, consigue la posicion en el arreglo de variables, usado para la asignacion
	private Parser<Pair<Integer,String>> findIdentifierPosition(){
		Parser<Pair<Integer,String>> id= Terminals.Identifier.PARSER.map(new Map<String,Pair<Integer,String>>(){
			public Pair<Integer,String> map(String arg){
				int i;
				for(i=0;i<constantNames.length;i++)
					if(constantNames[i].equals(arg))
						throw new RuntimeException("");
				for(i=0;i<variablesNames.size();i++)
					if(variablesNames.get(i).equals(arg))
						return new Pair<Integer,String>(i,arg);
				return new Pair<Integer,String>(-1,arg);
			}
		});
		Parser<Pair<Integer,String>> calculator= Parsers.between(term("{"),CALCULATOR,term("}")).map(new Map<Double,Pair<Integer,String>>(){
			public Pair<Integer,String> map(Double arg){
				if (arg.doubleValue() - arg.intValue() > 0.000000001)
					throw new RuntimeException("");
				int aux= arg.intValue();
				if(aux<0){
					aux= variables.size() + constantNames.length + aux;
					if(aux<0)
						throw new RuntimeException("");
				}
				if(aux<constantNames.length)
					throw new RuntimeException("");
				aux-= constantNames.length;
				if(aux >= variables.size())
					throw new RuntimeException("");
				return new Pair<Integer,String>(aux,"");
			}
		});
		return term("$").next(Parsers.or(id,calculator));
	}
	
	//Asignacion de variables
	private Parser<Object> asignToVariable(Parser<Object> exec){
		return Parsers.tuple(findIdentifierPosition(), term(":=").next(exec))
		.map(new Map< Pair<Pair<Integer,String>,Object>, Object>(){
			public Object map(Pair<Pair<Integer,String>, Object> arg){
				if(arg.a.a==-1){
					variables.add(arg.b);
					variablesNames.add(arg.a.b);
				}else{
					variables.set(arg.a.a, arg.b);
				}
				//System.out.println("Asignacion: " + variablesNames.toString());
				return arg.b;
			}
		});
	}
	
	//Utilizado para que los ciclos y condicionales no hagan operaciones
	private Parser<Object> fakeAssign(Parser<Object> f_exec){
		return Parsers.tuple(findIdentifierPosition(), term(":=").next(f_exec))
		.map(new Map< Pair<Pair<Integer,String>,Object>, Object>(){
			public Object map(Pair<Pair<Integer,String>, Object> arg){
				if(arg.a.a==-1){
					variables.add(arg.b);
					variablesNames.add(arg.a.b);
				}
				//System.out.println("Asignacion: " + variablesNames.toString());
				return arg.b;
			}
		});
	}
	
	//Creacion del executer con If
	private Parser<Object> executer(){
		Parser.Reference<Object> while_ref= Parser.newReference();
		Parser.Reference<Object> if_ref= Parser.newReference();
		Parser.Reference<Object> assign_ref= Parser.newReference();
		Parser.Reference<Object> fakeAssign_ref= Parser.newReference();
		//El map obliga a que lo ultimo que se evalue sea el retorno
		Parser<Object> exec= Parsers.or(Parsers.tuple(while_ref.lazy(),term(";")),Parsers.tuple(if_ref.lazy(),term(";")),assign_ref.lazy(),Parsers.tuple(identifier,term(";")),
			Parsers.tuple(LOGICAL,term(";")),Parsers.tuple(CALCULATOR,term(";"))).many1().map(new Map< List<?>, Object>(){
			@SuppressWarnings("unchecked")
			public Object map(List<?> arg){
				if(arg.get(arg.size()-1) instanceof Pair<?,?>)
					return ((Pair<Object,Object>)arg.get(arg.size()-1)).a;
				return arg.get(arg.size()-1);
			}
		});
		Parser<Object> f_exec= Parsers.or(Parsers.tuple(while_ref.lazy(),term(";")),Parsers.tuple(if_ref.lazy(),term(";")),fakeAssign_ref.lazy(),Parsers.tuple(identifier,term(";")),
			Parsers.tuple(LOGICAL,term(";")),Parsers.tuple(CALCULATOR,term(";"))).many1().map(new Map< List<?>, Object>(){
			@SuppressWarnings("unchecked")
			public Object map(List<?> arg){
				if(arg.get(arg.size()-1) instanceof Pair<?,?>)
					return ((Pair<Object,Object>)arg.get(arg.size()-1)).a;
				return arg.get(arg.size()-1);
			}
		});
		
		Parser<Object> While= while_cond(exec,f_exec);
		while_ref.set(While);
		Parser<Object> If= if_then_else(exec,f_exec);
		if_ref.set(If);
		Parser<Object> assign= asignToVariable(Parsers.or(Parsers.tuple(LOGICAL,term(";")),Parsers.tuple(CALCULATOR,term(";")),Parsers.tuple(STRING,term(";")),Parsers.tuple(CHAR,term(";")),Parsers.tuple(identifier,term(";")))
			.map(new Map< Pair<?,?>, Object>(){
				public Object map(Pair<?,?> arg){
					return arg.a;
				}
			}));
		assign_ref.set(assign);
		Parser<Object> f_assign= fakeAssign(Parsers.or(Parsers.tuple(LOGICAL,term(";")),Parsers.tuple(CALCULATOR,term(";")),Parsers.tuple(STRING,term(";")),Parsers.tuple(CHAR,term(";")),Parsers.tuple(identifier,term(";")))
			.map(new Map< Pair<?,?>, Object>(){
				public Object map(Pair<?,?> arg){
					return arg.a;
				}
			}));
		fakeAssign_ref.set(f_assign);
		return exec;
	}

	//Parser que se llamara para ejecutar los calculos
	private Parser<?> EXECUTER= executer().from(TOKENIZER,IGNORED);
	
	public CopyOfGAL_InterpreterParser(String[] constantNames) {
		this.constantNames= constantNames;
		constantValues= new Object[0];
		variables= new LinkedList<Object>();
		variablesNames= new LinkedList<String>();
		rand= new Random();
	}
	
	public double parseFitness(String source, GAL_Chromosome chromosome)throws ClassCastException{
		constantValues= new Object[chromosome.size()];
		for(int i=0;i<constantValues.length;i++)
			constantValues[i]= chromosome.getTrait(i);
		
		//System.out.println(TOKENIZER.sepEndBy(IGNORED).parse(source).toString());
		//interpreter= 0;
		return (Double) EXECUTER.parse(source);
	}
	
	public boolean parseTermination(String source, GAL_Population[] window)throws ClassCastException{
		constantValues= new Object[window.length*2+1];
		int i;
		constantValues[0]= window[0].size();
		for(i=1;i<constantValues.length;i+=2){
			constantValues[i]= window[i/2].getBestChromosome().getFitness();
			constantValues[i+1]= window[i/2].getTotalFitness();
		}
		//interpreter= 1;
		return (Boolean) EXECUTER.parse(source);
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
	
	/*public GAL_InterpreterNode getTree(){
		return new GAL_InterpreterNode(0,50,interpreter);
	}*/
}
