package Parser;

/**
 * Class that wrap the supported operators
 * @author Tobias Genborg
 *
 */
public final class SupportedOperators {

	static final Character[] supported = {'+','-','*','/','^'};
	
	//Methods
	
	public static Character[] GetSupported(){
		return supported;
	}
	
	/**
	 * Checks if an character is one of the supported operators.
	 * @return True if the character is a supported operator.
	 */
	public static Boolean isOperator(Character ch){
		for(Character op : supported){
			if(0 == ch.compareTo(op)){
				return true;
			}
		}
		return false;
	}

	public static boolean isLeftAssociative(String operator) {
		
		if(operator.equals("^")){
			return false;
		}
		return true;
	}
	
	private static int getPrecedence(String operator){
		switch (operator) {
		case "+":
			return 2;
		case "-":
			return 2;
		case "*":
			return 3;
		case "/":
			return 3;
		case "^":
			return 4;
		default:
			return 0;
		}
	}
	/**
	 * Compares the precedence of two operators. 
	 * @param o1 first operator
	 * @param o2 second operator
	 * @return Returns 1 if o1 have precedence over o2, -1 if o2 have precedence
	 * over o1 and 0 if they have the same precedence.
	 */
	public static int CompareOperators(String o1, String o2) {
		
		int p1 = getPrecedence(o1);
		int p2 = getPrecedence(o2);
		
		if(p1 == p2){
			return p1 - p2;
		}else{
			return (p1 - p2)/Math.abs(p1 - p2);
		}
	}
	
}
