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

	public DisplayManager(JTextField mainText, JTextArea histView) {
		this.mainText = mainText;
		this.histView = histView;
		this.mainDisplay = "0";
		decimalPoint = false;
		displayingResult = false;
		cleared = true;
	}
	
	public void updateDisplays(String currentDisplay, String histDisp) {
		String toDisplay = currentDisplay.endsWith(".0") ? currentDisplay.substring(0, currentDisplay.length()-2): currentDisplay;
		mainText.setText(toDisplay);
		histView.setText(histDisp);
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
