package msgboxPack;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

public class ReplacePanel extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1879474541851514364L;
	
	public JTextField scantxtfield;
	public JRadioButton uprbtn;
	public JRadioButton downbtn;
	public JButton scannextBtn = new JButton("Replace next");
	private String searchingText = "";
	private JRadioButton radioButton_1;
	private JRadioButton radioButton;
	
	public String searchingtxt = "";
	public boolean downsearch = true;
	public boolean listavailable = false;
	public int currentindex = -1;
	public List<Integer> indexlist = new ArrayList<Integer>();
	private JTextField replacetxtfield;
	
	
	public ReplacePanel(final JTextArea txtArea) {
		setResizable(false);
		setTitle("Replace");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 468, 193);
		
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
		
		JLabel lblReplaceBy = new JLabel("Replace By: ");
		panel.add(lblReplaceBy);
		
		replacetxtfield = new JTextField();
		replacetxtfield.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(replacetxtfield);
		replacetxtfield.setColumns(31);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		radioButton = new JRadioButton("Up");
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downsearch = false;
				radioButton.setSelected(true);
				radioButton_1.setSelected(false);
			}
		});
		panel_1.add(radioButton);
		
		radioButton_1 = new JRadioButton("Down");
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downsearch = true;
				radioButton_1.setSelected(true);
				radioButton.setSelected(false);
			}
		});
		radioButton_1.setSelected(true);
		panel_1.add(radioButton_1);
		
		scannextBtn.addActionListener(new ActionListener() {
			//现在开始搜索部分
			@Override
			public void actionPerformed(ActionEvent e) {
			if((listavailable == true) && (searchingText.equals(scantxtfield.getText()) ) ) {
				//case:向下替换
				if(downsearch == true) {
					int order = indexlist.indexOf(currentindex);
					if(order == indexlist.size() - 1)
						JOptionPane.showMessageDialog(null, "This is the last string.", "Alert", JOptionPane.WARNING_MESSAGE);
					else {
						currentindex = indexlist.get(order + 1);
						
						txtArea.replaceRange(replacetxtfield.getText(), currentindex, currentindex + searchingText.length());
						
						txtArea.requestFocus();
						txtArea.setSelectionStart(currentindex);
						txtArea.setSelectionEnd(currentindex + replacetxtfield.getText().length());
					}
				}
				//case:向上替换
				else {
					int order = indexlist.indexOf(currentindex);
					if(order == 0)
						JOptionPane.showMessageDialog(null, "This is the first string.", "Alert", JOptionPane.WARNING_MESSAGE);
					else {
						currentindex = indexlist.get(order - 1);
						
						txtArea.replaceRange(replacetxtfield.getText(), currentindex, currentindex + searchingText.length());
						
						txtArea.requestFocus();
						txtArea.setSelectionStart(currentindex);
						txtArea.setSelectionEnd(currentindex + replacetxtfield.getText().length());
					}
				}
			}
			else {
				searchingText = scantxtfield.getText();
				listavailable = false;
				if(searchingText.equals(""))
					JOptionPane.showMessageDialog(null, "String can't be null.", "Alert", JOptionPane.WARNING_MESSAGE);
				else {
					currentindex = txtArea.getText().indexOf(searchingText);
					if(currentindex == -1)
						JOptionPane.showMessageDialog(null, "Cannot find string: " + searchingText, "Alert", JOptionPane.WARNING_MESSAGE);
					
					else {
						int lastindex = currentindex;
						indexlist.add(currentindex);
						while(true) {
							String str = txtArea.getText().substring(lastindex + searchingText.length(), txtArea.getText().length());
							System.out.println(str);
							currentindex = str.indexOf(searchingText);
							if(currentindex == -1)
								break;
							else {
								indexlist.add((lastindex + searchingText.length()) + currentindex);
								lastindex += currentindex + searchingText.length();
								System.out.println(indexlist.toString());
							}
						}
						currentindex = indexlist.get(0);
						
						txtArea.replaceRange(replacetxtfield.getText(), currentindex, currentindex + searchingText.length());
						
						txtArea.requestFocus();
						txtArea.setSelectionStart(currentindex);
						txtArea.setSelectionEnd(currentindex + replacetxtfield.getText().length());
						
						listavailable = true;
					}
				}
			}
						
			}
			//替换部分结束
		});
		panel.add(scannextBtn);
		
		JButton btnReplaceAll = new JButton("Replace All");
		btnReplaceAll.addActionListener(new ActionListener() {
			//现在开始搜索部分
			@Override
			public void actionPerformed(ActionEvent e) {
			if((listavailable == true) && (searchingText.equals(scantxtfield.getText()) ) ) {
				//case:向下替换
				if(downsearch == true) {
					int order = indexlist.indexOf(currentindex);
					if(order == indexlist.size() - 1)
						JOptionPane.showMessageDialog(null, "This is the last string.", "Alert", JOptionPane.WARNING_MESSAGE);
					else {
						currentindex = indexlist.get(order + 1);
						
						txtArea.replaceRange(replacetxtfield.getText(), currentindex, currentindex + searchingText.length());
						
						txtArea.requestFocus();
						txtArea.setSelectionStart(currentindex);
						txtArea.setSelectionEnd(currentindex + replacetxtfield.getText().length());
					}
				}
				//case:向上替换
				else {
					int order = indexlist.indexOf(currentindex);
					if(order == 0)
						JOptionPane.showMessageDialog(null, "This is the first string.", "Alert", JOptionPane.WARNING_MESSAGE);
					else {
						currentindex = indexlist.get(order - 1);
						
						txtArea.replaceRange(replacetxtfield.getText(), currentindex, currentindex + searchingText.length());
						
						txtArea.requestFocus();
						txtArea.setSelectionStart(currentindex);
						txtArea.setSelectionEnd(currentindex + replacetxtfield.getText().length());
					}
				}
			}
			else {
				searchingText = scantxtfield.getText();
				listavailable = false;
				if(searchingText.equals(""))
					JOptionPane.showMessageDialog(null, "String can't be null.", "Alert", JOptionPane.WARNING_MESSAGE);
				else {
					currentindex = txtArea.getText().indexOf(searchingText);
					if(currentindex == -1)
						JOptionPane.showMessageDialog(null, "Cannot find string: " + searchingText, "Alert", JOptionPane.WARNING_MESSAGE);
					
					else {
						int lastindex = currentindex;
						indexlist.add(currentindex);
						while(true) {
							String str = txtArea.getText().substring(lastindex + searchingText.length(), txtArea.getText().length());
							System.out.println(str);
							currentindex = str.indexOf(searchingText);
							if(currentindex == -1)
								break;
							else {
								indexlist.add((lastindex + searchingText.length()) + currentindex);
								lastindex += currentindex + searchingText.length();
								System.out.println(indexlist.toString());
							}
						}
						currentindex = indexlist.get(0);
						
						txtArea.replaceRange(replacetxtfield.getText(), currentindex, currentindex + searchingText.length());
						
						txtArea.requestFocus();
						txtArea.setSelectionStart(currentindex);
						txtArea.setSelectionEnd(currentindex + replacetxtfield.getText().length());
						
						listavailable = true;
					}
				}
			}
						
			}
			//替换部分结束
		});
		panel.add(btnReplaceAll);
		
		Box verticalBox = Box.createVerticalBox();
		panel.add(verticalBox);
		
		setVisible(true);
	}
	
	
	
	public String showScanDialog(JFrame owner) {
		setLocationRelativeTo(owner);
		setVisible(true);
		return searchingtxt;
	}
}
