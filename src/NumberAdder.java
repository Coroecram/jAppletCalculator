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

public class NumberAdder {

	private JFrame frmCalculator;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalculator = new JFrame();
		frmCalculator.setTitle("Calculator");
		frmCalculator.setBounds(100, 100, 481, 519);
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculator.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("434px"),},
			new RowSpec[] {
				RowSpec.decode("45px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
	}

}
