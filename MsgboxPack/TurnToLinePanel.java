package msgboxPack;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

public class TurnToLinePanel extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6190958888109239194L;
	private JPanel contentPane = new JPanel();
	public JButton btnTurnTo = new JButton("Turn to");
	public JSpinner spinner = new JSpinner();
	
	public TurnToLinePanel() {
		setTitle("Turn to line");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 291, 98);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		setContentPane(contentPane);
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		contentPane.add(spinner, BorderLayout.CENTER);
		contentPane.add(btnTurnTo, BorderLayout.EAST);
	}
	
	public int getLine() {
		return ((int)spinner.getValue() - 1);
	} 

}
