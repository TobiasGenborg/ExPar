package Parser;

/**
 * @author Tobias Genborg
 */
public class Tester{
	
	private static int ShuntingYardCounter = 1;
	
	public static void main(String[] args){
		System.out.println("Testing the implentation of the Shunting-yard algorithm" + '\n');
		ShuntingYardTest("3+4", "3.0 4.0 + "); //Should return [3, 4, +]
		ShuntingYardTest("3+4*2/(1-5)^2^3", "3.0 4.0 2.0 * 1.0 5.0 - 2.0 3.0 ^ ^ / + "); //Should return [3 4 2 * 1 5 - 2 3 ^ ^ / +]
		ShuntingYardTest("(((1 + 2) * 3) + 6) / (2 + 3)", "1.0 2.0 + 3.0 * 6.0 + 2.0 3.0 + / "); //Should return [1 2 + 3 * 6 + 2 3 + /]
	}
	
	private static void DisplayResult(String testDescription, String expected, String res){
		if(expected.equals(res)){
			System.out.println(testDescription + ": OK");
		}else{
			System.out.println(testDescription + ": NOT OK");
			System.out.println('\t' + "Got: " + res);
			System.out.println('\t' + "Expected: " + expected);
		}
	}
	
	/**Test the shunting-yard algorithm by parsing a couple test-cases.	Returns True if successful*/
	private static void ShuntingYardTest(String expression, String expectedResult) {
		ExpressionParser parser = new ExpressionParser();
		String[] array = parser.postFixParse(expression);
		
		String res = "";
		for(String s : array){
			res = res.concat(s + " ");
		}
		
		DisplayResult("Shunting-yard test " + ShuntingYardCounter++, expectedResult, res);
	}
	
	

}
