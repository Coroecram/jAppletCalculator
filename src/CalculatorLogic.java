import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CalculatorLogic {

	private JTextField mainText;
	private JTextArea prevCalc;
	private Double[][] history = new Double[5][];
	private final String[] OPERATORS = new String[]{" + ", " - ", " x ", "\u00F7", "\u221A", " = "};
	private int histInd;
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
			history[i] = new Double[]{(double) 0, (double) 0, (double) 0 , (double) 0};
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
		Double.parseDouble(mainText.getText());
		// History array key: { Operator Code, First Operand, Second Operand, Result };
		// Operator Code: {0: +, 1: -, 2: *, 3: /, 4: sqrt, 5: =}		
		if (getCleared()) {
			return;
		} else if (!getCleared() && getHistCleared()) {
			//Set operator
			history[histInd][0] = (double) operator;
			// Set first operand
			history[histInd][1] = Double.parseDouble(mainText.getText());
			setHistCleared(false);
		} else if (history[histInd][0] % 4 == 0) {
				if (operator == 4) {
					String squares = "" + history[histInd][0] + "4";
					history[histInd][0] = Double.parseDouble(squares);
					calcRow();
				} else if (getDisplayingResult()) {
					incrementHistInd();
					history[histInd][0] = (double) operator;
					history[histInd][1] = Double.parseDouble(mainText.getText());
				}
		} else {
			history[histInd][2] = Double.parseDouble(mainText.getText());
			calcRow();
			incrementHistInd();
			history[histInd][0] = (double) operator;
			history[histInd][1] = history[histInd-1][3];
		}
		
		setDisplays();
	}

	private void calcRow() {
		int operator = history[histInd][0].intValue();
		
		switch(operator) {
		case(0):
			history[histInd][3] = history[histInd][1] + history[histInd][2];
		case(1):
			history[histInd][3] = history[histInd][1] - history[histInd][2];
		case(2):
			history[histInd][3] = history[histInd][1] * history[histInd][2];
		case(3):
			history[histInd][3] = history[histInd][1] / history[histInd][2];
		case(4):
			history[histInd][3] = calcNestSqrtDbl(history[histInd][0], history[histInd][1]);
		case(5):
			history[histInd][3] = history[histInd][1];
		default:
			
		}
	}

	private Double calcNestSqrtDbl(Double operators, Double value) {
		int numSqrt = ("" + operators).length();
		for (int i = 0; i <= numSqrt; i++) {
			value = Math.sqrt(value);
		}
		
		return value;
	}

	private void setDisplays() {
		StringBuilder historicalText = new StringBuilder();
		int operator = -1;
		double firstOperand;
		double secondOperand;
		double result = 0;
		for (int i = 0; i < history.length; i++) {
			if (operator == 0) {
				break;
			}
			operator = history[i][0].intValue();
			firstOperand = history[i][1].intValue();
			secondOperand = history[i][2].intValue();
			result = history[i][3].intValue();
			
			historicalText.append(firstOperand);
			historicalText.append(OPERATORS[operator]);
			historicalText.append(secondOperand);
			if (operator > 4) {
				if (operator == 4) {
					String sqrtNestString = calcSqrtNestString(history[i][0], history[i][1]);
					historicalText.append(sqrtNestString);
					result = history[i][3];
				} else if (operator == 5) {
					result = history[i][3];
				}
			}
			if (i < history.length -1) {
				historicalText.append("\n");
			}
		}
		
		if (operator >= 4) {
			setDisplayingResult(true);
		}
		
		prevCalc.setText(historicalText.toString());
		mainText.setText("" + result);	
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
		if (cleared && string != "0") {
			if (string == ".") {
				mainText.setText("0.0");
				setDecimalPoint(true);
				
			} else {
				mainText.setText(string);
			}
			setCleared(false);
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
		if (length > 0) {
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
