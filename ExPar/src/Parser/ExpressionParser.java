package Parser;

import java.util.ArrayDeque;

import Parser.SupportedOperators;


public class ExpressionParser {

	//Variables

	//Constructors
	/**Constructor	 */
	public ExpressionParser(){

	}


	/*
	 * Methods
	 */

	//Public Methods

	/**
	 * Parses the expression using the Shunting-Yard algorithm to recreate the 
	 * expression in postfix notation.
	 * 
	 * @param expression A string representing a mathematical expression in infix
	 * notation.
	 * @return A string representing the expresson in postfix representation.
	 */
	public String[] postFixParse(String expression){
		ArrayDeque<String> outputQueue = new ArrayDeque<>();
		ArrayDeque<String> operatorStack = new ArrayDeque<>();
		StringBuffer exp = new StringBuffer(expression);
		String token;
		ArrayDeque<Double> numStack = new ArrayDeque<>();
		Boolean numFlag = false;
		Boolean decimalFlag = false;
		Double integerPart = 0.0;

		for(int i = 0; i < exp.length(); i++){
			
			//Read a token			
			Character ch = exp.charAt(i);
			token = ch.toString();

			if(isNumeric(token) /* or is variable */){
				Double num = Double.parseDouble(token);
				if(i == exp.length()-1){
					outputQueue.add(num.toString());
				}else{
					numFlag = true;
					numStack.addLast(num);
				}
			}else if(token.equals(".")){ //Parsing a number with decimals
				integerPart = 0.0; //Reset the integerPart from before
				double base = 1.0;
				while(!numStack.isEmpty()){
					integerPart += numStack.removeLast() * base;
					base = base*10.0;
				}
				decimalFlag = true;	
				
			}else if(numFlag){ //Parsing a number >9
				Double d = 0.0;
				double base = 1.0;
				while(!numStack.isEmpty()){
					if(decimalFlag == false){ //Parsing a number >9
						d += numStack.removeLast() * base;
						base = base*10.0;
					}else{ //Parsing a number <1
						d += numStack.removeFirst() * base / 10.0;
						base = base/10.0;
					}

				}
				d = integerPart + d;
				outputQueue.add(d.toString());
				numFlag = false;
				decimalFlag = false;
			}else{
				decimalFlag = false;
			}
			
			
			
			//if(/*isFunction */){

			if(isArgumentSeperator(token)){
				//while(){
				//					
				//}
			}else if(isOperator(token)){
				while(true){
					String top = operatorStack.peekFirst();
					if(top == null){
						break;
					}
					
					Boolean associativityFlag = SupportedOperators.isLeftAssociative(token)
							&& (0 == SupportedOperators.CompareOperators(token, top));
					Boolean precedenceFlag = SupportedOperators.CompareOperators(token, top) == -1;	
					
					if(isOperator(top) && (associativityFlag || precedenceFlag)){
						String operator = operatorStack.pop();
						outputQueue.add(operator);
					}else{
						break;
					}

				}
				operatorStack.push(token);			
			}else if(token.equals("(")){
				operatorStack.push(token);
			}else if(token.equals(")")){
				String top = operatorStack.peekFirst();
				
				while(!top.equals("(")){
					String operator = operatorStack.pop(); //TODO the stack may be empty
					outputQueue.add(operator);
					 top = operatorStack.peekFirst();
				}
				
				if(top.equals("(")){
					operatorStack.pop();
				}
				
				//if(top is function){ //TODO
				//pop operatorstack
				//}
			}
			
		}
		
		//We have traversed the whole expression
		while(!operatorStack.isEmpty()){
			String operator = operatorStack.pop();
			if(operator == "(" || operator == "("){
				System.out.println("Paran error");
				//TODO Check if this works
			}
			outputQueue.add(operator);
		}

		String[] s = new String[outputQueue.size()];
		return outputQueue.toArray(s);

	}

	/**
	 * Checks if the string is one of the supported operators
	 * @param s String to be checked
	 * @return Boolean. True if the string is an operator, false if not.
	 */
	public static boolean isOperator(String s) {
		if(s.length() == 1){
			Character ch = s.charAt(0);
			return isOperator(ch);
		}
		return false;
	}


	/**
	 * Determines if the string is a argument seperator (e.g a bracket).
	 * @param s String to be evaluated
	 * @return Boolean. True if the string is a seperator, false if not.
	 */
	private boolean isArgumentSeperator(String s) {
		//TODO Write a proper method
		return false;
	}


	/*
	 * Private Methods
	 */

	/**
	 * Checks if an character is one of the supported operators.
	 * @return True if the character is a supported operator.
	 */
	private static Boolean isOperator(Character ch){
		return SupportedOperators.isOperator(ch);
	}

	/**
	 * Checks if the character can is a number.
	 * @return True if the character is a number
	 */
	public static Boolean isNumeric(String str){
		try{
			Double.parseDouble(str);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
}
