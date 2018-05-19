import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;

public class NumberAdder {

	private JFrame frmCalculator;
	private JTextField mainText;
	private JTextArea prevCalc = new JTextArea("");
	private JScrollPane scroll;
	private CalculatorLogic logic;
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
		logic = new CalculatorLogic(mainText, prevCalc);
	}

	/**
	 * Initialize the contents of the frame.
	 */	
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
		mainText.setBounds(5, 66, 315, 64);
		frmCalculator.getContentPane().add(mainText);
		mainText.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(5, 11, 315, 44);
		frmCalculator.getContentPane().add(scrollPane);
		prevCalc.setBackground(SystemColor.inactiveCaptionBorder);
		prevCalc.setRows(5);
		scrollPane.setViewportView(prevCalc);
		prevCalc.setEditable(false);
		
		//Plus-Minus Button
		JButton negative = new JButton("\u00B1");
		negative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logic.negative();
			}
		});
		negative.setFont(new Font("Arial", Font.BOLD, 14));
		negative.setBounds(5, 355, 75, 50);
		frmCalculator.getContentPane().add(negative);
		
		JButton zero = new JButton("0");
		zero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("0");
			}
		});
		zero.setFont(new Font("Arial", Font.BOLD, 14));
		zero.setBounds(85, 355, 75, 50);
		frmCalculator.getContentPane().add(zero);
		
		JButton decimal = new JButton(".");
		decimal.setFont(new Font("Arial", Font.BOLD, 14));
		decimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button(".");
			}
		});
		decimal.setBounds(165, 355, 75, 50);
		frmCalculator.getContentPane().add(decimal);
		
		JButton equals = new JButton("=");
		equals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logic.equals();
			}
		});
		equals.setFont(new Font("Arial", Font.BOLD, 14));
		equals.setBounds(245, 355, 75, 50);
		frmCalculator.getContentPane().add(equals);
		
		JButton one = new JButton("1");
		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("1");
			}
		});
		one.setFont(new Font("Arial", Font.BOLD, 14));
		one.setBounds(5, 300, 75, 50);
		frmCalculator.getContentPane().add(one);
		
		JButton two = new JButton("2");
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("2");
			}
		});
		two.setFont(new Font("Arial", Font.BOLD, 14));
		two.setBounds(85, 300, 75, 50);
		frmCalculator.getContentPane().add(two);
		
		JButton three = new JButton("3");
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("3");
			}
		});
		three.setFont(new Font("Arial", Font.BOLD, 14));
		three.setBounds(165, 300, 75, 50);
		frmCalculator.getContentPane().add(three);
		
		JButton subtract = new JButton("-");
		subtract.setFont(new Font("Arial", Font.BOLD, 14));
		subtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logic.calculate(1);
			}
		});
		subtract.setBounds(245, 245, 75, 50);
		frmCalculator.getContentPane().add(subtract);
		
		JButton four = new JButton("4");
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("4");
			}
		});
		four.setFont(new Font("Arial", Font.BOLD, 14));
		four.setBounds(5, 245, 75, 50);
		frmCalculator.getContentPane().add(four);
		
		JButton five = new JButton("5");
		five.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("5");
			}
		});
		five.setFont(new Font("Arial", Font.BOLD, 14));
		five.setBounds(85, 245, 75, 50);
		frmCalculator.getContentPane().add(five);
		
		JButton six = new JButton("6");
		six.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("6");
			}
		});
		six.setFont(new Font("Arial", Font.BOLD, 14));
		six.setBounds(165, 245, 75, 50);
		frmCalculator.getContentPane().add(six);
		
		JButton addition = new JButton("+");
		addition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.calculate(0);
			}
		});
		addition.setFont(new Font("Arial", Font.BOLD, 14));
		addition.setBounds(245, 300, 75, 50);
		frmCalculator.getContentPane().add(addition);
		
		JButton seven = new JButton("7");
		seven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("7");
			}
		});
		seven.setFont(new Font("Arial", Font.BOLD, 14));
		seven.setBounds(5, 190, 75, 50);
		frmCalculator.getContentPane().add(seven);
		
		JButton eight = new JButton("8");
		eight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("8");
			}
		});
		eight.setFont(new Font("Arial", Font.BOLD, 14));
		eight.setBounds(85, 190, 75, 50);
		frmCalculator.getContentPane().add(eight);
		
		JButton nine = new JButton("9");
		nine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.button("9");
			}
		});
		nine.setFont(new Font("Arial", Font.BOLD, 14));
		nine.setBounds(165, 190, 75, 50);
		frmCalculator.getContentPane().add(nine);
		
		JButton multiply = new JButton("x");
		multiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.calculate(2);
			}
		});
		multiply.setFont(new Font("Arial", Font.BOLD, 14));
		multiply.setBounds(245, 190, 75, 50);
		frmCalculator.getContentPane().add(multiply);
		
		//SquareRoot Button
		JButton squareroot = new JButton("\u221A");
		squareroot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.calculate(4);
			}
		});
		squareroot.setFont(new Font("Arial", Font.BOLD, 14));
		squareroot.setBounds(5, 135, 75, 50);
		frmCalculator.getContentPane().add(squareroot);
		
		JButton clear = new JButton("C");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.clear();
			}
		});
		clear.setFont(new Font("Arial", Font.BOLD, 14));
		clear.setBounds(85, 135, 75, 50);
		frmCalculator.getContentPane().add(clear);
		
		JButton backspace = new JButton("del");
		backspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.backspace();				
			}
		});
		backspace.setFont(new Font("Arial", Font.BOLD, 14));
		backspace.setBounds(165, 135, 75, 50);
		frmCalculator.getContentPane().add(backspace);
		
		JButton divide = new JButton("\u00F7");
		divide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.calculate(3);
			}
		});
		divide.setFont(new Font("Arial", Font.BOLD, 14));
		divide.setBounds(245, 135, 75, 50);
		frmCalculator.getContentPane().add(divide);
	}
}
