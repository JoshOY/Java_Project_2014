package MsgboxPack;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	/**
	 * Create the frame.
	 */
	public AboutPanel() {
		setTitle("About");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 279, 192);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel txtpanel = new JPanel();
		contentPane.add(txtpanel, BorderLayout.CENTER);
		
		JTextPane txtpnAboutTextEditor = new JTextPane();
		txtpnAboutTextEditor.setEditable(false);
		txtpnAboutTextEditor.setBackground(UIManager.getColor("Button.background"));
		txtpnAboutTextEditor.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 15));
		txtpnAboutTextEditor.setText("About Text Editor\r\nmade by JoshOY 2014.5\r\nThank you for using!");
		txtpanel.add(txtpnAboutTextEditor);
		
		JPanel btnpanel = new JPanel();
		contentPane.add(btnpanel, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnpanel.add(btnOk);
	}

}
