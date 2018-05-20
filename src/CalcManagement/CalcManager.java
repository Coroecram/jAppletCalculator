package CalcManagement;

import DisplayManagement.DisplayManager;

public class CalcManager {
	private DisplayManager dispManager;
	private HistoricalCalculations histCalcs;
	private int histInd;
	String currentDisplay;
	
	public CalcManager(DisplayManager dispManager, HistoricalCalculations histCalcs) {
		this.dispManager = dispManager;
		this.histCalcs = histCalcs;
		this.histInd = histCalcs.getHistInd();
		currentDisplay = "0";
		updateDisplays();
	}
	
	//Command to call when histCalcs or currentDisplay is ever changed
	private void updateDisplays() {
		dispManager.updateDisplays(this.histCalcs, this.currentDisplay);
	}
	
	//Buttons that change display
	public void button(String string) {
		if (getDisplayingResult() || getCleared() && string != "0") {
			firstEntry(string);
		} else {
			notFirstEntry(string);
		}
	}
	
	//Buttons that perform operations
	public void button(int opCode) {
		
	}

	private void firstEntry(String string) {
		if (string == ".") {
			currentDisplay = "0.";
			this.dispManager.setDecimalPoint(true);
			dispManager.updateDisplays(histCalcs, currentDisplay);
		} else {
			currentDisplay = string;
			dispManager.updateDisplays(histCalcs, currentDisplay);
		}
		this.dispManager.setCleared(false);
		this.dispManager.setDisplayingResult(false);
	}
	
	private void notFirstEntry(String string) {
		if (string == ".") {
			if (!getDecimalPoint()) {
				currentDisplay = currentDisplay + string;
				updateDisplays();
				this.dispManager.setDecimalPoint(true);
			} // Do nothing if decimal point there.
		} else {
			currentDisplay = currentDisplay + string;
			updateDisplays();
		}		
	}
	
	public void equals() {
		if (getDisplayingResult()) {
			return;
		} else {
			getHistory(histInd)[2] = Double.parseDouble(currentDisplay);
			incrementHistInd();
			getHistory(histInd)[0] = (double) 5;
			updateDisplays();
		}
	}

	private Object[] getHistory(int ind) {
		return this.histCalcs.getHistory(ind);
	}

	private void incrementHistInd() {
		this.histCalcs.incrementHistInd();
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
		if (length > 0 && !this.dispManager.getDisplayingResult()) {
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
			updateDisplays();
//			if (!getHistCleared()) {
//				getHistory(histInd)[3] = negatedVal;
//			}
		}
		
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
	
	private boolean sqrtOperand(Double operand) {
		if (operand == 0) {
			return false;
		} else {
			return operand % 4 == 0;
		}
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
