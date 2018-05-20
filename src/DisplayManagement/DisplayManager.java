package DisplayManagement;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import CalcManagement.HistoricalCalculations;

public class DisplayManager {
	
	private JTextField mainText;
	private JTextArea histView;
	private HistoricalCalculations histCalcs;
	private String mainDisplay;
	private boolean decimalPoint,displayingResult,cleared;
	private final String[] OPERATORS = new String[]{" + ", " - ", " x ", " \u00F7 ", " \u221A ", " = "};

	public DisplayManager(JTextField mainText, JTextArea histView, HistoricalCalculations histCalcs) {
		this.mainText = mainText;
		this.histView = histView;
		this.histCalcs = histCalcs;
		this.mainDisplay = "0";
		decimalPoint = false;
		displayingResult = false;
		cleared = true;
	}
	
	public void updateDisplays(HistoricalCalculations histCalcs2, String currentDisplay) {
		// TODO Auto-generated method stub
		
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

	private String calcSqrtNestString(int operators, Double base) {
		int numSqrt = ("" + operators).length();
		String nested = "" + base;
		for (int i = 1; i <= numSqrt; i++) {
			nested = "\u221A" + "(" + nested + ")";
		}
		
		return nested;
	}

	public void setCleared(boolean b) {
		this.cleared = b;
	}
	
	public boolean getCleared() {
		return this.cleared;
	}
	
	public void setDisplayingResult(boolean b) {
		this.displayingResult = b;
	}
	
	public boolean getDisplayingResult() {
		return this.displayingResult;
	}

	public void setDecimalPoint(boolean b) {
		this.decimalPoint = b;
	}
	
	public boolean getDecimalPoint() {
		return this.decimalPoint;
	}

	public void clearDisplay() {
		this.setCleared(true);
		this.setDecimalPoint(false);
		this.setDisplayingResult(false);
	}
}
