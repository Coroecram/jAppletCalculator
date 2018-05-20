package CalcManagement;

import java.util.ArrayList;

public class HistoricalCalculations {
	
	private ArrayList<Double[]> history;
	private int histInd;
	private boolean histCleared;

	public HistoricalCalculations() {
		history = new ArrayList<Double[]>();
		histInd = 0;
		histCleared = true;
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

	public void clearHistory() {
		// TODO Auto-generated method stub
		
	}


}
