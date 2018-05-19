import java.awt.Label;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CalculatorLogic {

	private JTextField mainText;
	private JTextArea prevCalc;
	private int preOperator;
	private final String[] OPERATORS = {"+", "-", "x", "\u00F7"};
	private Double[][] history = new Double[5][];
	private int histInd;
	private double current,result,runningTotal;
	private String prevCalcs = "";
	private boolean decimalPoint,displayingResult,cleared;
	
	public CalculatorLogic(JTextField mainText, JTextArea prevCalc) {
		this.mainText = mainText;
		this.prevCalc = prevCalc;
		decimalPoint = false;
		displayingResult = false;
		cleared = true;
		histInd = 0;
	}
	
	protected void setCleared(boolean b) {
		this.cleared = b;
	}
	
	protected boolean getCleared(boolean b) {
		return this.cleared;
	}
	
	protected void setDisplayingResult(boolean b) {
		this.displayingResult = b;
	}
	
	protected boolean getDisplayingResult(boolean b) {
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
		return history.length-1 == histInd;
	}
	
	protected void rolloverHistory() {
		for (int i = 1; i < history.length; i++) {
			history[i-1] = history[i];
		}
	}
	
	private String replaceLastOperator(String prevCalcs, int operator) {
		int length = prevCalcs.length();
		
		StringBuilder sb = new StringBuilder(mainText.getText());
		
		if (length > 0) {
			sb.deleteCharAt(length-2);
			sb.append(OPERATORS[operator] + " ");
			return sb.toString();
		} else {
			return "";
		}
				
	}
	
	protected double calculate(int operator) {
		current = Double.parseDouble(mainText.getText());
		if (preOperator == -1) {
			preOperator = operator;
			runningTotal = Double.parseDouble(mainText.getText());
			prevCalc.setText("" + runningTotal + OPERATORS[operator] + " ");
			prevCalcs = prevCalc.getText();
		} else if (preOperator == 5) {
			if (operator == 5) {
				runningTotal = Math.sqrt(runningTotal);
				mainText.setText("" + runningTotal);
				prevCalc.setText("\u221A" + "(" + prevCalc.getText());
				this.setCleared(true);
			} else {
				
			}
		} else {
		}
			switch (preOperator) {
				case 0:
					if (cleared) {
						preOperator = operator;
						if (prevCalc.getText().length() > 0) {
							replaceLastOperator(prevCalcs, operator);
							//prevCalc.setText(remainder + runningTotal + OPERATORS[operator] + " ");
							this.setCleared(true);
						}
					} else {
						if (preOperator != -1) {
							preOperator = 1;
						}
					}
				case 2:
					result = runningTotal - current;
					runningTotal = result;
					prevCalcs = prevCalcs + current + " - ";
					prevCalc.setText(prevCalcs);
				case 3:
					result = runningTotal * current;
					prevCalc.setText(prevCalcs + " x");
				case 4:
					result = runningTotal / current;
					prevCalc.setText(prevCalcs + " ÷");
				case 5:
					double result = Math.sqrt(Double.parseDouble(mainText.getText()));
				default:
			
			}
			
		mainText.setText("" + result);
		return result;
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
		history = new Double[5][];
		histInd = 0;
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
