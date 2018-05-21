package CalcManagement;

import java.util.ArrayList;
import java.util.Arrays;

import DisplayManagement.DisplayManager;

public class CalcManager {
	private DisplayManager dispManager;
	private HistoricalCalculations histCalcs;
	private int histInd;
	private Double[][] workset;
	private boolean worksetCleared;
	String currentDisplay;
	private ArrayList<String> histDisp;
	
	public CalcManager(DisplayManager dispManager, HistoricalCalculations histCalcs) {
		this.dispManager = dispManager;
		this.histCalcs = histCalcs;
		this.histDisp = this.histCalcs.getHistDisp();
		this.histInd = histCalcs.getHistInd();
		currentDisplay = "0";
		workset = new Double[2][];
		worksetCleared = true;
		updateDisplays();
	}
	
	//Command to call when histCalcs or currentDisplay is ever changed
	private void updateDisplays() {
		dispManager.updateDisplays(currentDisplay, histDisp);
	}
	
	//Buttons that change display
	public void button(String string) {
		if (getDisplayingResult() || getCleared() && string != "0") {
			firstEntry(string);
		} else {
			notFirstEntry(string);
		}
		
		updateDisplays();
	}
	
	//Buttons that perform operations
	public void button(int opCode) {
		
	}

	private void firstEntry(String string) {
		if (string == ".") {
			currentDisplay = "0.";
			this.dispManager.setDecimalPoint(true);
		} else {
			currentDisplay = string;
		}
		this.dispManager.setCleared(false);
		this.dispManager.setDisplayingResult(false);
	}
	
	private void notFirstEntry(String string) {
		if (string == ".") {
			if (!getDecimalPoint()) {
				currentDisplay = currentDisplay + string;
				this.dispManager.setDecimalPoint(true);
			} // Do nothing if decimal point there.
		} else {
			currentDisplay = currentDisplay + string;
		}		
	}
	
	protected void calculate(int operator) {
		Double[] prevCalc = workset[0];
		Double[] currentCalc = workset[1];
		
		// Workset array key: { Operator Code, First Operand, Second Operand, Result, Modified(bool 1/0) };
		// Operator Code: {0: +, 1: -, 2: *, 3: /, 4: sqrt, 5: =}
		
		System.out.println("calc operator: " + operator);
		if (getCleared()) {
			return;
		// Check if there was previous calculation
		} else if (prevCalc != null) {
			System.out.println("are: " + prevCalc[0]);
			double prevOp = prevCalc[0];
			// Check for special prevOps sqrt(4) and equals(5)
			if (prevOp >= 4) {
				// Check if current and prevop were sqrt => nest operations
				if (prevOp % 4 == 0 && operator == 4) {
					decrementHistInd();
					exchangePrevCurrent();
					String squares = "4";
					squares = "" + prevCalc[0].intValue() + "4";
					currentCalc[0] = Double.parseDouble(squares);
					currentCalc[4] = 1.0;
					processCurrentCalc(operator);
					// System.out.println("127getHistory(histInd)[0] " + getHistory(histInd)[0].intValue());
				} else if (prevOp == 5) {
				// Change prevOp to currentOp if an equals operation and mark as modified (currentCalc[4])
					decrementHistInd();
					exchangePrevCurrent();
					prevCalc = null;
					currentCalc[0] = (double) operator;
					currentCalc[1] = currentCalc[3];
					currentCalc[2] = null;
					currentCalc[3] = null;
					currentCalc[4] = 1.0;
				} else if (operator == 4) {
					currentCalc[0] = (double) operator;
					currentCalc[1] = prevCalc[3];
					currentCalc[2] = prevCalc[3];
					processCurrentCalc(operator);
				} else {
				// Move result to current operation if past was 4 but current is not
					currentCalc[1] = prevCalc[3];
					currentCalc[0] = (double) operator;
				}
			} else {
				currentCalc[2] = Double.parseDouble(currentDisplay);
				processCurrentCalc(operator);
			}
		} else {
			// Make special rules for special operators
			if (operator >= 4) {
				if (operator == 5) {
					if (currentCalc[1] != null) {
						currentCalc[0] = (double) operator;
						currentCalc[1] = Double.parseDouble(currentDisplay);
					}
					// Do nothing if equals already loaded with first operand
				} else { // If sqrt operator is first
					currentCalc[0] = (double) operator;
					currentCalc[1] = Double.parseDouble(currentDisplay);
					// currentCalc[2] for sqrt operations is the root.
					currentCalc[2] = Double.parseDouble(currentDisplay);
					processCurrentCalc(operator);
				}
			} else {
			currentCalc[0] = (double) operator;
				// If first calculation then check if first operand is loaded. If it is, add second operand and move to prevCalc
				if (currentCalc[1] != null) {
					currentCalc[2] = Double.parseDouble(currentDisplay);
					processCurrentCalc(operator);
				} else {
					currentCalc[1] = Double.parseDouble(currentDisplay);
				}
			}
		}
		
		updateDisplays();
	}

	private void processCurrentCalc(int operator) {
		Double result = executeCurrentOp();
		currentDisplay = "" + result; // Change display to result
		workset[1][3] = result; // Put in result
		workset[1][4] = 1.0; // Row is modified
		workset[0] = Arrays.copyOf(workset[1], 5);
		pushWorkset(workset[0], 0);
		incrementHistInd();
		workset[1] = new Double[]{null, null, null, null, 1.0}; // New row, modified (1.0) from nothing.
		workset[1][0] = (double) operator;
		workset[1][1] = workset[0][3];
		pushWorkset(workset[1], 1);
	}

	private void pushWorkset(Double[] workset, int ind) {
		histCalcs.append(workset, histInd);
		histCalcs.addModification(histInd, ind);
		histCalcs.setModified(true);
	}

	private Double executeCurrentOp() {
		
		Double result = null;
		Double[] currentCalc = workset[1];
		
		int operator = currentCalc[0].intValue();
		
		// Remove extra 4s if nested sqrt
		while (operator > 10) {
			operator = operator % 10;
		}
		
		System.out.println("operator: " + operator);
		// Operator Code: {0: +, 1: -, 2: *, 3: /, 4: sqrt, 5: =}
		switch(operator) {
			case(0):
				System.out.println("0");
				result = currentCalc[1] + currentCalc[2];
				break;
			case(1):
				System.out.println("1");
				result = currentCalc[1] - currentCalc[2];
				break;
			case(2):
				System.out.println("2");
				result = currentCalc[1] * currentCalc[2];
				break;
			case(3):
				System.out.println("3");
				result = currentCalc[1] / currentCalc[2];
				break;
			case(4):
				//Hold the root for the nested string
				System.out.println("4");
				result = calcNestSqrtDbl(currentCalc[0].intValue(), currentCalc[2]); // currentCalc[2] being the root number of the sqrts are nested
				break;
			case(5):
				System.out.println("5");
				result = Double.parseDouble(currentDisplay);
				break;
			default:
			
		}
		
		return result;
	}
	
	private void exchangePrevCurrent() {
		workset[1] = Arrays.copyOf(workset[0], 5);
	}

	private Object[] getHistory(int ind) {
		return this.histCalcs.getHistory(ind);
	}

	private void incrementHistInd() {
		this.histCalcs.incrementHistInd();
	}
	
	private void decrementHistInd() {
		this.histCalcs.decrementHistInd();
	}

	public void clear() {
		currentDisplay = "0";
		clearHistory();
		clearDisplay();
		updateDisplays();
	}

	private void clearDisplay() {
		this.dispManager.clearDisplay();
	}

	private void clearHistory() {
		this.histCalcs.clearHistory();
	}

	public void backspace() {		
		int length = currentDisplay.length();
		if (length > 0 && !getDisplayingResult()) {
			StringBuilder sb = new StringBuilder(currentDisplay);
			sb.deleteCharAt(length-1);
			currentDisplay = sb.toString();
			updateDisplays();
		}
	}

	public void negate() {
		if (!getCleared()) {
			Double negatedVal = 0 - Double.parseDouble(currentDisplay);
			currentDisplay = "" + negatedVal;
			if (currentCalcIncomplete() && getDisplayingResult()) { // If displaying the first operand
				workset[1][1] = negatedVal;
				if (workset[1][0] == 5) {
					workset[1][4] = negatedVal; // Change the total for an = operations
				}
			}
			updateDisplays();
		}
		
	}

	private boolean currentCalcIncomplete() {
		return workset[1][2] == null;
	}

	private boolean getHistCleared() {
		return this.histCalcs.getHistCleared();
	}

	private Double calcNestSqrtDbl(int operators, Double root) {
		int numSqrt = ("" + operators).length();
		Double value = root;
		for (int i = 0; i < numSqrt; i++) {
			value = Math.sqrt(value);
		}
		return value;
	}
	
	private boolean getDisplayingResult() {
		return this.dispManager.getDisplayingResult();
	}

	private boolean getCleared() {
		return this.dispManager.getCleared();
	}
	
	private boolean getDecimalPoint() {
		return this.dispManager.getDecimalPoint();
	}

}
