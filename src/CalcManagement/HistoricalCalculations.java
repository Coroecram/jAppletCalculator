package CalcManagement;

import java.util.ArrayList;

public class HistoricalCalculations {
	
	private ArrayList<Double[]> history;
	private ArrayList<String> histDisp;
	private int histInd;
	private boolean histCleared;
	private boolean modified;
	private int[] modifiedRows;
	private final String[] OPERATORS = new String[]{" + ", " - ", " x ", " \u00F7 ", " \u221A ", " = "};

	public HistoricalCalculations() {
		histDisp = new ArrayList<String>();
		history = new ArrayList<Double[]>();
		histInd = 0;
		histCleared = true;
		modified = false;
		modifiedRows = new int[2];
	}
	
	private void setDisplays() {
		StringBuilder historicalText = new StringBuilder();
		int operator;
		double firstOperand;
		
		for (int i : modifiedRows) {
			if (getHistory(i)[4] == 0) { // Check if row actually modified
				break;
			}
			operator = getHistory(i)[0].intValue();
			if (operator >= 4) {
				if (operator % 4 == 0) {
					String sqrtNestString = calcSqrtNestString(getHistory(i)[0].intValue(), getHistory(i)[2]);
					historicalText.append(sqrtNestString);
				} else if (operator == 5) {
					histDisp.remove(i);
					histDisp.add(i, "" + getHistory(i)[3]);
					break;
				}
			} else { 
				firstOperand = getHistory(i)[1];
				historicalText.append(firstOperand);
				historicalText.append(OPERATORS[operator]);
			}

			histDisp.remove(i);
			histDisp.add(i, "" + historicalText.toString());
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
	
	public Double[] getCurrentHistory() {
		return this.history.get(histInd);
	}
	
	public Double[] getHistory(int i) {
		return this.history.get(i);
	}
	
	public boolean getHistCleared() {
		return histCleared;
	}
	
	public void setHistCleared(boolean b) {
		histCleared = b;
	}
	
	public int getHistInd() {
		return this.histInd;
	}
	
	public void incrementHistInd() {
		this.histInd = histInd + 1;
	}
	
	public void decrementHistInd() {
		this.histInd = histInd - 1;
	}
	
	public void clearHistory() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<String> getHistDisp() {
		if (this.modified) {
			setDisplays();
			setModified(false);
		};
		
		return histDisp;
	}

	public void append(Double[] calc, int histInd) {
		if (history.size() > histInd) {
			history.remove(histInd);
		}
		history.add(histInd, calc);
	}

	public int[] getModifiedRows() {
		return modifiedRows;
	}

	public void setModifiedRows(int[] modifiedRows) {
		this.modifiedRows = modifiedRows;
	}

	public void addModification(int histInd, int ind) {
		getModifiedRows()[ind] = histInd;		
	}

	public void setModified(boolean b) {
		this.modified = b;		
	}
}
