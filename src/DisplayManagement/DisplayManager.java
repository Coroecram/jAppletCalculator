package DisplayManagement;

import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import CalcManagement.HistoricalCalculations;

public class DisplayManager {
	
	private JTextField mainText;
	private JTextArea histView;
	private String mainDisplay;
	private boolean decimalPoint,displayingResult,cleared;
	private final String[] OPERATORS = new String[]{" + ", " - ", " x ", " \u00F7 ", " \u221A ", " = "};

	public DisplayManager(JTextField mainText, JTextArea histView) {
		this.mainText = mainText;
		this.histView = histView;
		this.mainDisplay = "0";
		decimalPoint = false;
		displayingResult = false;
		cleared = true;
	}
	
	public void updateDisplays(String currentDisplay, ArrayList<String> histDisp) {
		mainText.setText(currentDisplay);
		histView.setText(parseHist(histDisp));
	}
	
	private String parseHist(ArrayList<String> histDisp) {
		StringBuilder histToDisplay = new StringBuilder();
		for (String hist : histDisp) {
			histToDisplay.append(hist + "\n");
		}
		histToDisplay.delete(-2, -1);
		
		return histToDisplay.toString();
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