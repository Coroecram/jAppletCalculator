package CalcManagement;

import java.util.ArrayList;

public class HistoricalCalculations {
	
	private ArrayList<Double[]> history;
	private ArrayList<String> histDisp;
	private int histInd;
	private boolean histCleared;
	private boolean modified;

	public HistoricalCalculations() {
		histDisp = new ArrayList<String>();
		history = new ArrayList<Double[]>();
		histInd = 0;
		histCleared = true;
		modified = false;
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
		if (this.modified) {};
		return histDisp;
	}

	public void append(Double[] calc, int histInd) {
		if (history.size() >= histInd) {
			history.remove(histInd);
		}
		history.add(histInd, calc);
	}


}
