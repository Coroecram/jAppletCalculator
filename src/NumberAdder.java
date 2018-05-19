import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NumberAdder {

	private JFrame frmCalculator;
	private JTextField mainText;
	private Label prevCalc = new Label("");
	private int preOperator;
	private final String[] OPERATORS = {"+", "-", "x", "\u00F7"};
	private double current,result,runningTotal;
	private String prevCalcs = "";
	private boolean decimalPoint,displayingResult,cleared;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NumberAdder window = new NumberAdder();
					window.frmCalculator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NumberAdder() {
		initialize();
		decimalPoint = false;
		displayingResult = false;
		cleared = true;
		preOperator = -1;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private double preCalculate(int operator) {
		current = Double.parseDouble(mainText.getText());
		if (preOperator == -1) {
			preOperator = operator;
			runningTotal = Double.parseDouble(mainText.getText());
			prevCalc.setText("" + runningTotal + OPERATORS[operator] + " ");
			prevCalcs = prevCalc.getText();
		} else if (preOperator == 5) {
			if (operator == 5) {
				runningTotal = Math.sqrt(runningTotal);
				mainText.setText("" + runningTotal);
				prevCalc.setText("\u221A" + "(" + prevCalc.getText());
				setCleared(true);
			} else {
				
			}
		} else {
		}
			switch (preOperator) {
				case 0:
					if (cleared) {
						preOperator = operator;
						if (prevCalc.getText().length() > 0) {
						replaceLastOperator(prevCalcs, operator);
						prevCalc.setText(remainder + runningTotal + OPERATORS[operator] + " ");
						setCleared(true);
					} else {
						if (preOperator != -1) {
							preOperator = 1;
						}
					}
				case 2:
					result = runningTotal - current;
					runningTotal = result;
					prevCalcs = prevCalcs + current + " - ";
					prevCalc.setText(prevCalcs);
				case 3:
					result = runningTotal * current;
					prevCalc.setText(prevCalcs + " x");
				case 4:
					result = runningTotal / current;
					prevCalc.setText(prevCalcs + " ÷");
				case 5:
					double result = Math.sqrt(Double.parseDouble(mainText.getText()));
				default:
			
			}
			
		mainText.setText("" + result);
		return result;
	}
	}
	
	private String replaceLastOperator(String prevCalcs, int operator) {
		int length = prevCalcs.length();
		
		StringBuilder sb = new StringBuilder(mainText.getText());
		
		if (length > 0) {
			sb.deleteCharAt(length-2);
			sb.append(OPERATORS[operator] + " ");
			return sb.toString();
		} else {
			return "";
		}
				
	}

	private boolean getDecimalPoint() {
		return this.decimalPoint;
	}
	
	private void setDecimalPoint(boolean value) {
		this.decimalPoint = value;
	}
	
	private boolean getDisplayingResult() {
		return this.displayingResult;
	}
	
	private void setDisplayingResult(boolean value) {
		this.displayingResult = value;
	}
	
	private void initialize() {
		frmCalculator = new JFrame();
		frmCalculator.getContentPane().setBackground(Color.WHITE);
		frmCalculator.setTitle("Calculator");
		frmCalculator.setBounds(100, 100, 343, 450);
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculator.getContentPane().setLayout(null);
		
		mainText = new JTextField();
		mainText.setEditable(false);
		mainText.setText("0");
		mainText.setHorizontalAlignment(SwingConstants.RIGHT);
		mainText.setFont(new Font("Verdana", Font.PLAIN, 24));
		mainText.setBounds(10, 37, 310, 92);
		frmCalculator.getContentPane().add(mainText);
		mainText.setColumns(10);
		
		prevCalc.setAlignment(Label.RIGHT);
		prevCalc.setBounds(10, 10, 310, 21);
		frmCalculator.getContentPane().add(prevCalc);
		
		JButton negative = new JButton("\u00B1");
		negative.setFont(new Font("Arial", Font.BOLD, 14));
		negative.setBounds(5, 355, 75, 50);
		frmCalculator.getContentPane().add(negative);
		
		JButton zero = new JButton("0");
		zero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cleared) {
					String num = mainText.getText() + "0";
					mainText.setText(num);
				}
			}
		});
		zero.setFont(new Font("Arial", Font.BOLD, 14));
		zero.setBounds(85, 355, 75, 50);
		frmCalculator.getContentPane().add(zero);
		
		JButton decimal = new JButton(".");
		decimal.setFont(new Font("Arial", Font.BOLD, 14));
		decimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!getDecimalPoint()) {
					if (cleared) {
						mainText.setText("0.0");
						setCleared(false);
					} else {
						String num = mainText.getText() + ".";
						mainText.setText(num);
					}
					setDecimalPoint(true);
				}
			}
		});
		decimal.setBounds(165, 355, 75, 50);
		frmCalculator.getContentPane().add(decimal);
		
		JButton equals = new JButton("=");
		equals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		equals.setFont(new Font("Arial", Font.BOLD, 14));
		equals.setBounds(245, 355, 75, 50);
		frmCalculator.getContentPane().add(equals);
		
		JButton one = new JButton("1");
		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("1");
					setCleared(false);
				} else {
					String num = mainText.getText() + "1";
					mainText.setText(num);
				}
			}
		});
		one.setFont(new Font("Arial", Font.BOLD, 14));
		one.setBounds(5, 300, 75, 50);
		frmCalculator.getContentPane().add(one);
		
		JButton two = new JButton("2");
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("2");
					setCleared(false);
				} else {
					String num = mainText.getText() + "2";
					mainText.setText(num);
				}
			}
		});
		two.setFont(new Font("Arial", Font.BOLD, 14));
		two.setBounds(85, 300, 75, 50);
		frmCalculator.getContentPane().add(two);
		
		JButton three = new JButton("3");
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("3");
					setCleared(false);
				} else {
					String num = mainText.getText() + "3";
					mainText.setText(num);
				}
			}
		});
		three.setFont(new Font("Arial", Font.BOLD, 14));
		three.setBounds(165, 300, 75, 50);
		frmCalculator.getContentPane().add(three);
		
		JButton subtract = new JButton("-");
		subtract.setFont(new Font("Arial", Font.BOLD, 14));
		subtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calculate(2);
			}
		});
		subtract.setBounds(245, 245, 75, 50);
		frmCalculator.getContentPane().add(subtract);
		
		JButton four = new JButton("4");
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("4");
					setCleared(false);
				} else {
					String num = mainText.getText() + "4";
					mainText.setText(num);
				}
			}
		});
		four.setFont(new Font("Arial", Font.BOLD, 14));
		four.setBounds(5, 245, 75, 50);
		frmCalculator.getContentPane().add(four);
		
		JButton five = new JButton("5");
		five.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("5");
					setCleared(false);
				} else {
					String num = mainText.getText() + "5";
					mainText.setText(num);
				}
			}
		});
		five.setFont(new Font("Arial", Font.BOLD, 14));
		five.setBounds(85, 245, 75, 50);
		frmCalculator.getContentPane().add(five);
		
		JButton six = new JButton("6");
		six.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("6");
					setCleared(false);
				} else {
					String num = mainText.getText() + "6";
					mainText.setText(num);
				}
			}
		});
		six.setFont(new Font("Arial", Font.BOLD, 14));
		six.setBounds(165, 245, 75, 50);
		frmCalculator.getContentPane().add(six);
		
		JButton addition = new JButton("+");
		addition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!cleared) {
					operator = 1;
					calculate();
				}
			}
		});
		addition.setFont(new Font("Arial", Font.BOLD, 14));
		addition.setBounds(245, 300, 75, 50);
		frmCalculator.getContentPane().add(addition);
		
		JButton seven = new JButton("7");
		seven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("7");
					setCleared(false);
				} else {
					String num = mainText.getText() + "7";
					mainText.setText(num);
				}
			}
		});
		seven.setFont(new Font("Arial", Font.BOLD, 14));
		seven.setBounds(5, 190, 75, 50);
		frmCalculator.getContentPane().add(seven);
		
		JButton eight = new JButton("8");
		eight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("8");
					setCleared(false);
				} else {
					String num = mainText.getText() + "8";
					mainText.setText(num);
				}
			}
		});
		eight.setFont(new Font("Arial", Font.BOLD, 14));
		eight.setBounds(85, 190, 75, 50);
		frmCalculator.getContentPane().add(eight);
		
		JButton nine = new JButton("9");
		nine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cleared) {
					mainText.setText("9");
					setCleared(false);
				} else {
					String num = mainText.getText() + "9";
					mainText.setText(num);
				}
			}
		});
		nine.setFont(new Font("Arial", Font.BOLD, 14));
		nine.setBounds(165, 190, 75, 50);
		frmCalculator.getContentPane().add(nine);
		
		JButton multiply = new JButton("x");
		multiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!cleared) {
					operator = 3;
					calculate();
				}
			}
		});
		multiply.setFont(new Font("Arial", Font.BOLD, 14));
		multiply.setBounds(245, 190, 75, 50);
		frmCalculator.getContentPane().add(multiply);
		
		JButton squareroot = new JButton("\u221A");
		squareroot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculate(5);
			}
		});
		squareroot.setFont(new Font("Arial", Font.BOLD, 14));
		squareroot.setBounds(5, 135, 75, 50);
		frmCalculator.getContentPane().add(squareroot);
		
		JButton clear = new JButton("C");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainText.setText("0");
				prevCalc.setText("");
				operator = 0;
				setCleared(true);
				mainText.setEditable(true);
			}
		});
		clear.setFont(new Font("Arial", Font.BOLD, 14));
		clear.setBounds(85, 135, 75, 50);
		frmCalculator.getContentPane().add(clear);
		
		JButton backspace = new JButton("del");
		backspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int length = mainText.getText().length();
				String remainder;
				
				StringBuilder sb = new StringBuilder(mainText.getText());
				
				if (length > 0) {
					sb.deleteCharAt(length-1);
					remainder = sb.toString();
					mainText.setText(remainder);
				}
				
			}
		});
		backspace.setFont(new Font("Arial", Font.BOLD, 14));
		backspace.setBounds(165, 135, 75, 50);
		frmCalculator.getContentPane().add(backspace);
		
		JButton divide = new JButton("\u00F7");
		divide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!cleared) {
					operator = 4;
					calculate();
				}
			}
		});
		divide.setFont(new Font("Arial", Font.BOLD, 14));
		divide.setBounds(245, 135, 75, 50);
		frmCalculator.getContentPane().add(divide);
	}

	protected void setCleared(boolean b) {
		this.cleared = b;
	}
}
