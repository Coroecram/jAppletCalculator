package ButtonProcessing;

import CalcManagement.CalcManager;

public class ButtonProcessor {
	
	private CalcManager calcManager;

	public ButtonProcessor(CalcManager calcManager) {
		this.calcManager = calcManager;
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


	public void button(String string) {
		// TODO Auto-generated method stub
		
	}

}
