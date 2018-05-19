import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CalculatorLogic {

	private JTextField mainText;
	private JTextArea prevCalc;
	private Double[][] history = new Double[5][];
	private final String[] OPERATORS = new String[]{" + ", " - ", " x ", " \u00F7 ", " \u221A ", " = "};
	private int histInd;
	private double lastCalc;
	private boolean decimalPoint,displayingResult,cleared,histCleared;
	
	public CalculatorLogic(JTextField mainText, JTextArea prevCalc) {
		this.mainText = mainText;
		this.prevCalc = prevCalc;
		decimalPoint = false;
		displayingResult = false;
		cleared = true;
		clearHistory();
		//mainText.setText("" + history[histInd][3]);
	}
	
	private void clearHistory() {
		for (int i = 0; i < history.length; i ++) {
			history[i] = new Double[]{(double) -1, (double) 0, (double) 0 , (double) 0};
		}
		histCleared = true;
		histInd = 0;
	}

	protected void setCleared(boolean b) {
		this.cleared = b;
	}
	
	protected boolean getCleared() {
		return this.cleared;
	}
	
	protected void setDisplayingResult(boolean b) {
		this.displayingResult = b;
	}
	
	protected boolean getDisplayingResult() {
		return this.displayingResult;
	}

	protected void setDecimalPoint(boolean b) {
		this.decimalPoint = b;
	}
	
	protected boolean getDecimalPoint() {
		return this.decimalPoint;
	}
	
	protected Double[] getHistory() {
		return this.history[histInd];
	}
	
	protected Double[] getHistory(int i) {
		return this.history[i];
	}
	
	protected void setHistory(Double[] histArray) {
		this.history[histInd] = histArray;
	}
	
	private boolean getHistCleared() {
		return histCleared;
	}
	
	private void setHistCleared(boolean b) {
		histCleared = b;
	}
	
	protected int getHistInd() {
		return this.histInd;
	}
	
	protected void incrementHistInd() {
		if (fullHistory()) {
			rolloverHistory();
		} else {
			this.histInd = histInd + 1;
		}
	}
	
	protected void decrementHistInd() {
		this.histInd = histInd - 1;
	}
	
	protected boolean fullHistory() {
		return history.length-1 == histInd;
	}
	
	protected void rolloverHistory() {
		for (int i = 1; i < history.length; i++) {
			history[i-1] = history[i];
		}
	}
	
	protected void calculate(int operator) {
		// History array key: { Operator Code, First Operand, Second Operand, Result };
		// Operator Code: {0: +, 1: -, 2: *, 3: /, 4: sqrt, 5: =}		
		if (getCleared()) {
			return;
		} else if (!getCleared()  && getHistCleared()) {
			//Set operator
			history[histInd][0] = (double) operator;
			// Set first operand
			history[histInd][1] = Double.parseDouble(mainText.getText());
			calcRows();
			setHistCleared(false);
		} else if (history[histInd][0] > 0 && history[histInd][0] % 4 == 0) {
			String squares = "" + history[histInd][0].intValue() + "4";
			history[histInd][0] = Double.parseDouble(squares);
			history[histInd][1] = Double.parseDouble(mainText.getText());
			calcRows();
		} else {
			history[histInd][2] = Double.parseDouble(mainText.getText());
			calcRows();
			incrementHistInd();
			history[histInd][0] = (double) operator;
			history[histInd][1] = history[histInd-1][3];
		}
		
		setDecimalPoint(false);
		setDisplays();
	}

	private void calcRows() {
		for (int i = 0; i < history.length; i++) {
			int operator = history[i][0].intValue();
			//Remove extra 4s from nested sqrt
			while (operator > 10) {
				operator = operator / 10;
			}
			System.out.println("operator: " + operator);
			switch(operator) {
				case(-1):
					break;
				case(0):
					System.out.println("0");
					lastCalc = history[i][1] + history[i][2];
					history[i][3] = lastCalc;
					setHistCleared(false);
					break;
				case(1):
					System.out.println("1");
					lastCalc = history[i][1] - history[i][2];
					history[i][3] = lastCalc;
					setHistCleared(false);
					break;
				case(2):
					System.out.println("2");
					lastCalc = history[i][1] * history[i][2];
					System.out.println("lastCalc: " + history[i][1] * history[i][2]);
					history[i][3] = lastCalc;
					setHistCleared(false);
					break;
				case(3):
					System.out.println("3");
					lastCalc = history[i][1] / history[i][2];
					history[i][3] = lastCalc;
					setHistCleared(false);
					break;
				case(4):
					lastCalc = calcNestSqrtDbl(history[i][0].intValue(), history[i][1]);
					System.out.println("square calc: " + lastCalc);
					System.out.println("operator: " + history[i][0]);
					history[i][3] = lastCalc;
					setHistCleared(false);
					break;
				case(5):
					System.out.println("5");
					history[i][3] = lastCalc;
					setHistCleared(true);
					break;
				default:
				
			}
		}
	}

	private Double calcNestSqrtDbl(int operators, Double value) {
		System.out.println("nested sqrt: " + operators);
		int numSqrt = ("" + operators).length();
		
		for (int i = 0; i <= numSqrt; i++) {
			value = Math.sqrt(value);
		}
		
		return value;
	}

	private void setDisplays() {
		StringBuilder historicalText = new StringBuilder();
		String mainDisplay = "0";
		int operator;
		double firstOperand;
		double secondOperand;
		
		for (int i = 0; i < history.length; i++) {
			if (history[i][0] == -1) {
				break;
			}
			operator = history[i][0].intValue();
			if (operator > 4) {
				if (operator == 4) {
					String sqrtNestString = calcSqrtNestString(history[i][0], history[i][1]);
					historicalText.append(sqrtNestString);
					mainDisplay = "" + history[i][3];
				} else if (operator == 5) {
					calcRows();
					System.out.println("i: " + i + "total: " + history[i][3]);
					mainDisplay = "" + history[i][3];
				}
				mainDisplay = "" + history[i][3];
			} else { 
				setDisplayingResult(true);
				firstOperand = history[i][1];
				mainDisplay = "" + history[i][1];
				historicalText.append(firstOperand);
				historicalText.append(OPERATORS[operator]);
			}
			if (i + 1 < history.length && history[i+1][0] != -1) {
				secondOperand = history[i][2];
				historicalText.append(secondOperand);
			}
			if (i < history.length -1) {
				historicalText.append("\n");
			}
		}
		
		prevCalc.setText(historicalText.toString());
		mainText.setText(mainDisplay);
	}

	private String calcSqrtNestString(Double operators, Double operand) {
		int numSqrt = ("" + operators).length();
		String nested = "" + operand;
		for (int i = 0; i <= numSqrt; i++) {
			nested = "\u221A" + "(" + nested + ")";
		}
		
		return nested;
	}

	public void button(String string) {
		if (getDisplayingResult() || cleared && string != "0") {
			if (string == ".") {
				mainText.setText("0.");
				setDecimalPoint(true);
				
			} else {
				mainText.setText(string);
			}
			setCleared(false);
			setDisplayingResult(false);
		} else {
				if (string == ".") {
					if (!getDecimalPoint()) {
						String num = mainText.getText() + string;
						mainText.setText(num);
						setDecimalPoint(true);
					} // Do nothing if decimal point there.
				} else {
					String num = mainText.getText() + string;
					mainText.setText(num);
				}
		}
	}
	
	public void equals() {
		if (getDisplayingResult()) {
			return;
		} else {
			history[histInd][2] = Double.parseDouble(mainText.getText());
			incrementHistInd();
			history[histInd][0] = (double) 5;
			calcRows();
			setDisplays();
		}
	}

	public void clear() {
		mainText.setText("0");
		prevCalc.setText("");
		clearHistory();
		setDisplayingResult(false);
		setDecimalPoint(false);
		setCleared(true);		
	}

	public void backspace() {		
		int length = mainText.getText().length();
		if (length > 0 && !getDisplayingResult()) {
			StringBuilder sb = new StringBuilder(mainText.getText());
			sb.deleteCharAt(length-1);
			mainText.setText(sb.toString());
		}
	}

	public void negative() {
		if (!cleared) {
			
		}
		
	}
		
}
