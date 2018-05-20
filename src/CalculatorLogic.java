import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CalculatorLogic {

	private JTextField mainText;
	private JTextArea prevCalc;
	private ArrayList<Double[]> history = new ArrayList<Double[]>();
	private final String[] OPERATORS = new String[]{" + ", " - ", " x ", " \u00F7 ", " \u221A ", " = "};
	private int histInd;
	private int prevOp = -1;
	private double lastCalc;
	private boolean decimalPoint,displayingResult,cleared,histCleared;
	
	public CalculatorLogic(JTextField mainText, JTextArea prevCalc) {
		this.mainText = mainText;
		this.prevCalc = prevCalc;
		decimalPoint = false;
		displayingResult = false;
		cleared = true;
		clearHistory();
		//mainText.setText("" + getHistory(histInd)[3]);
	}
	
	private void clearHistory() {
		for (int i = 0; i < 999; i ++) {
			history.add(new Double[]{(double) -1, (double) 0, (double) 0 , (double) 0});
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
	
	protected Double[] getCurrentHistory() {
		return this.history.get(histInd);
	}
	
	protected Double[] getHistory(int i) {
		return this.history.get(i);
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
		this.histInd = histInd + 1;
	}
	
	protected void decrementHistInd() {
		this.histInd = histInd - 1;
	}
	
	protected boolean fullHistory() {
		return history.size()-1 == histInd;
	}
	
	
	protected void calculate(int operator) {
		// History array key: { Operator Code, First Operand, Second Operand, Result };
		// Operator Code: {0: +, 1: -, 2: *, 3: /, 4: sqrt, 5: =}
		System.out.println("calc operator: " + operator);
		if (getCleared()) {
			return;
		} else if (prevOp == -1) {
			System.out.println("are: " + prevOp);
			//Set operator
			getHistory(histInd)[0] = (double) operator;
			// Set first operand
			getHistory(histInd)[1] = Double.parseDouble(mainText.getText());
			calcRows();
			prevOp = operator;
		} else if (sqrtOperand((double) operator)) {
			System.out.println("119 histInd: " + histInd);
			String squares = "4";
			if (prevOp == 4) {
				squares = "" + getHistory(histInd)[0].intValue() + "4";
			}
			getHistory(histInd)[0] = Double.parseDouble(squares);
			System.out.println("127getHistory(histInd)[0] " + getHistory(histInd)[0].intValue());
			getHistory(histInd)[1] = Double.parseDouble(mainText.getText());
			calcRows();
			setHistCleared(false);
			prevOp = 4;
		} else {
			System.out.println("else");
			if(prevOp != 4) {
				getHistory(histInd)[2] = Double.parseDouble(mainText.getText());
			}
			calcRows();
			incrementHistInd();
			prevOp = operator;
			getHistory(histInd)[0] = (double) operator;
			getHistory(histInd)[1] = getHistory(histInd-1)[3];
		}
		
		setDecimalPoint(false);
		setDisplays();
	}

	private void calcRows() {
		for (int i = 0; i < history.size(); i++) {
			int operator = getHistory(i)[0].intValue();
			if (operator == -1) {
				break;
			}
			//Remove extra 4s from nested sqrt
			while (operator > 10) {
				operator = operator % 10;
			}
			System.out.println("operator: " + operator);
			switch(operator) {
				case(0):
					System.out.println("0");
					lastCalc = getHistory(i)[1] + getHistory(i)[2];
					getHistory(i)[3] = lastCalc;
					setHistCleared(false);
					continue;
				case(1):
					System.out.println("1");
					lastCalc = getHistory(i)[1] - getHistory(i)[2];
					getHistory(i)[3] = lastCalc;
					setHistCleared(false);
					continue;
				case(2):
					System.out.println("2");
					lastCalc = getHistory(i)[1] * getHistory(i)[2];
					getHistory(i)[3] = lastCalc;
					setHistCleared(false);
					continue;
				case(3):
					System.out.println("3");
					lastCalc = getHistory(i)[1] / getHistory(i)[2];
					getHistory(i)[3] = lastCalc;
					setHistCleared(false);
					continue;
				case(4):
					//Hold the root for the nested string
					System.out.println("4");
					if (getHistory(i)[2] == 0) {
						getHistory(i)[2] = getHistory(i)[1];
					}
					Double root = getHistory(i)[2];
					lastCalc = calcNestSqrtDbl(getHistory(i)[0].intValue(), root);
					System.out.println("176 lastCalc" + lastCalc);
					getHistory(i)[3] = lastCalc;
					setHistCleared(false);
					continue;
				case(5):
					System.out.println("5");
					getHistory(i)[3] = lastCalc;
					setHistCleared(true);
					continue;
				default:
				
			}
		}
	}

	private Double calcNestSqrtDbl(int operators, Double root) {
		int numSqrt = ("" + operators).length();
		Double value = root;
		for (int i = 0; i < numSqrt; i++) {
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
		
		for (int i = 0; i < history.size(); i++) {
			if (getHistory(i)[0] == -1) {
				break;
			}
			operator = getHistory(i)[0].intValue();
			if (operator >= 4) {
				if (operator % 4 == 0) {
					String sqrtNestString = calcSqrtNestString(getHistory(i)[0].intValue(), getHistory(i)[2]);
					historicalText.append(sqrtNestString);
					mainDisplay = "" + getHistory(i)[3];
					setDisplayingResult(true);
				} else if (operator == 5) {
					calcRows();
					mainDisplay = "" + getHistory(i)[3];
				}
				mainDisplay = "" + getHistory(i)[3];
			} else { 
				setDisplayingResult(true);
				firstOperand = getHistory(i)[1];
				mainDisplay = "" + getHistory(i)[1];
				historicalText.append(firstOperand);
				historicalText.append(OPERATORS[operator]);
			}
			if (!sqrtOperand(getHistory(i)[0]) && i + 1 < history.size() && getHistory(i+1)[0] != -1) {
				secondOperand = getHistory(i)[2];
				historicalText.append(secondOperand);
			}
			if (i < history.size() -1) {
				historicalText.append("\n");
			}
		}
		
		prevCalc.setText(historicalText.toString());
		mainText.setText(mainDisplay);
	}

	private boolean sqrtOperand(Double operand) {
		if (operand == 0) {
			return false;
		} else {
			return operand % 4 == 0;
		}
	}

	private String calcSqrtNestString(int operators, Double base) {
		int numSqrt = ("" + operators).length();
		String nested = "" + base;
		for (int i = 1; i <= numSqrt; i++) {
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
			getHistory(histInd)[2] = Double.parseDouble(mainText.getText());
			incrementHistInd();
			getHistory(histInd)[0] = (double) 5;
			calcRows();
			setDisplays();
		}
	}

	public void clear() {
		mainText.setText("0");
		prevCalc.setText("");
		prevOp = -1;
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

	public void negate() {
		if (!cleared) {
			Double negatedVal = 0 - Double.parseDouble(mainText.getText());
			mainText.setText("" + negatedVal);
			if (!getHistCleared()) {
				getHistory(histInd)[3] = negatedVal;
			}
		}
		
	}
		
}
