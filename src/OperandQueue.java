import java.util.ArrayList;

public class OperandQueue extends ArrayList<Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double total;
	
	public OperandQueue() {
		total = 0;
	}
	
	public void setTotal(double amt) {
		total = amt;
	}
	
	public double getTotal() {
		return total;
	}
}
