package CalcManagement;

import java.util.ArrayList;

public class HistoricalCalculations {
	
	private ArrayList<Double[]> history;
	private ArrayList<String> histDisp;
	private boolean histCleared;
	private final String[] OPERATORS = new String[]{" + ", " - ", " x ", " \u00F7 ", " \u221A ", " = "};

	public HistoricalCalculations() {
		clearHistory();
	}
	
	private void setDisplay(int step) {
		StringBuilder historicalText = new StringBuilder();
		Double[] calculation = getHistory(history.size()-1);
		int operator = calculation[0].intValue();
		System.out.println("operator : " + operator);
		double firstOperand = calculation[1];
	
		if (operator % 4 == 0 && operator != 0) {
			String sqrtNestString = calcSqrtNestString(operator, calculation[2]);
			historicalText.append(sqrtNestString);
		} else if (step == 5) { //Second Operand sqrt special
			historicalText.append("" + firstOperand);
			historicalText.append(" " + OPERATORS[operator]);
			double secondOperand = calculation[2];
			historicalText.append("\u221A" + "(" + Math.pow(secondOperand, 2) + ")");
		} else { 
			historicalText.append("" + firstOperand);
			historicalText.append(" " + OPERATORS[operator]);
			if (step > 1) {
				double secondOperand = calculation[2];
				historicalText.append(" " + secondOperand);
			}
		}
		histDisp.add("" + historicalText.toString());
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
		return getHistory(history.size() - 1);
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
	
	public void clearHistory() {
		histDisp = new ArrayList<String>();
		history = new ArrayList<Double[]>();
		histCleared = true;		
	}

	public String getHistDisp() {		
		StringBuilder calcHistory = new StringBuilder();

		for (String calc : histDisp) {
			calcHistory.append(calc + "\n");
		}
		
		return calcHistory.toString();
	}

	public void append(Double[] calc, int step) {
		if (step > 1) {
			if (step > 4) { // nested sqrt is special case
				history.remove(history.size() - 1);
				histDisp.remove(histDisp.size() - 1);
			} else if (step < 4) {
				history.remove(history.size() - 1);
				histDisp.remove(histDisp.size() - 1);
			} // note we do nothing if first sqrt
		}
		history.add(calc);
		setDisplay(step);
	}
}
