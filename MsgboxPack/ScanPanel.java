package MsgboxPack;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class ScanPanel extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1879474541851514363L;
	
	public JTextField scantxtfield;
	public JRadioButton uprbtn;
	public JRadioButton downbtn;
	public JButton scannextBtn = new JButton("Scan next");
	

	public String searchingtxt = "";
	public boolean downsearch = true;
	public boolean listavailable = false;
	public int currentindex = -1;
	public List<Integer> indexlist = new ArrayList<Integer>();
	
	public ScanPanel() {
		setResizable(false);
		setTitle("Scan");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 491, 149);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setVgap(15);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("Scanning for:");
		panel.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		scantxtfield = new JTextField();
		panel.add(scantxtfield);
		scantxtfield.setHorizontalAlignment(SwingConstants.CENTER);
		scantxtfield.setColumns(30);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		final JRadioButton uprbtn = new JRadioButton("Up");
		panel_2.add(uprbtn);
		
		final JRadioButton downrbtn = new JRadioButton("Down");
		downrbtn.setSelected(true);
		panel_2.add(downrbtn);
		
		/*
		 * Radio button action
		 **/
		
		uprbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				uprbtn.setSelected(true);
				downrbtn.setSelected(false);
				downsearch = false;
			}
		});
		
		downrbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				downrbtn.setSelected(true);
				uprbtn.setSelected(false);
				downsearch = true;
			}
		});
		
		/*
		 * Radio button action end
		 **/
		
		scannextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchingtxt = scantxtfield.getText();
				
			}
		});
		panel.add(scannextBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(closeBtn);
		
		Box verticalBox = Box.createVerticalBox();
		panel.add(verticalBox);
	}
	
	
	
	public String showScanDialog(JFrame owner) {
		setLocationRelativeTo(owner);
		setVisible(true);
		return searchingtxt;
	}
}
