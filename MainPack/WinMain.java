package MainPack;

import MsgboxPack.AboutPanel;
import MsgboxPack.MQFontChooser;
import MsgboxPack.ScanWin;
import MsgboxPack.TurnToLinePanel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JFileChooser;
import javax.swing.SpinnerNumberModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JSeparator;
import javax.swing.ImageIcon;

import java.awt.Toolkit;

import javax.swing.JScrollPane;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;


public class WinMain {

	private JFrame frmTextEditor;
	public final JTextArea txtArea = new JTextArea();
	public final JLabel LineInfoText = new JLabel("Row 1, Column 1");
	/* Using these vars to save my settings */
	private boolean BFileSaved = true;
	String strFileName = "Unnamed";
	public String searchingText = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMain window = new WinMain();
					window.frmTextEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WinMain() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTextEditor = new JFrame();
		frmTextEditor.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Documents\\\u6211\u7684\u6587\u6863\\\u56FE\u7247\u6536\u85CF\\\u5934\u50CF.png"));
		frmTextEditor.setTitle("Text Editor" + " - " + strFileName);
		frmTextEditor.setBounds(100, 100, 625, 500);
		frmTextEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JScrollPane scrollPane = new JScrollPane();
		frmTextEditor.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		txtArea.setTabSize(4);
		txtArea.setFont(new Font("宋体", Font.PLAIN, 16));
		scrollPane.setViewportView(txtArea);
		txtArea.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e) {
				BFileSaved = false;
				if(frmTextEditor.getTitle().charAt(0) != '*')
					frmTextEditor.setTitle("* " + frmTextEditor.getTitle());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				BFileSaved = false;
				if(frmTextEditor.getTitle().charAt(0) != '*')
					frmTextEditor.setTitle("* " + frmTextEditor.getTitle());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		txtArea.addCaretListener(new CaretListener() {
		       public void caretUpdate(CaretEvent e) {
		           //System.out.println("row="+getRow(e.getDot(), (JTextComponent)e.getSource()));
		           //System.out.println("col="+getColumn(e.getDot(), (JTextComponent)e.getSource()));
		    	   LineInfoText.setText("Row "+getRow(e.getDot(), (JTextComponent)e.getSource()) 
		    			   				+ ", Column "+getColumn(e.getDot(), (JTextComponent)e.getSource()));
		       }
		   });
		
		JPanel LineInfoPane = new JPanel();
		LineInfoPane.setBorder(null);
		frmTextEditor.getContentPane().add(LineInfoPane, BorderLayout.SOUTH);
		
		
		LineInfoText.setFont(new Font("Times New Roman", Font.BOLD, 9));
		LineInfoText.setVerticalAlignment(SwingConstants.BOTTOM);
		LineInfoPane.add(LineInfoText);
		
		JMenuBar menuBar = new JMenuBar();
		frmTextEditor.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(BFileSaved) {
					txtArea.setText("");
					strFileName = "Unnamed";
					frmTextEditor.setTitle("Text Editor" + " - " + strFileName);
					BFileSaved = true;
				} else {
					/*Check file saved first*/
					int selection = saveAlert(BFileSaved);
					switch (selection) {
						case JOptionPane.NO_OPTION:
							break;
						case JOptionPane.CANCEL_OPTION:
							return;
						case JOptionPane.YES_OPTION:
							if(strFileName == "Unnamed"){
								JFileChooser saveaswin = new JFileChooser();
								saveaswin.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
								FileFilter filter1 = new FileNameExtensionFilter("txt File", "txt");
								saveaswin.setFileFilter(filter1);
								//saveaswin.setSelectedFile(new File("unamed.txt"));
								
								int option1 = saveaswin.showDialog(frmTextEditor, "Save File");
								if(option1 == JFileChooser.APPROVE_OPTION) {
								File file = saveaswin.getSelectedFile();
								saveaswin.setFileSelectionMode(JFileChooser.FILES_ONLY);
								System.out.println(file.toString());
										
								String[] content = txtArea.getText().split("\n");
								String path = file.getPath();
								//Begin to write in file.
								try {
									//If there's no such a file
									FileWriter fwriter = new FileWriter(path);
									//fwriter.write(content);
									for(int i = 0; i < content.length; ++i){
										fwriter.write(content[i]);
										if(i != content.length - 1)
											fwriter.write("\r\n");
									}
									fwriter.close();
									frmTextEditor.setTitle("Text Editor - " + path);
									strFileName = path;
									BFileSaved = true;
									} catch (IOException e1) { e1.printStackTrace(); }
								break;
								} 
							} else {
								try {
									FileWriter fwriter = new FileWriter(strFileName);
									String[] content = txtArea.getText().split("\n");
									for(int i = 0; i < content.length; ++i){
										fwriter.write(content[i]);
										if(i != content.length - 1)
											fwriter.write("\r\n");
									}
									fwriter.close();
									BFileSaved = true;
								} catch (IOException e1) {e1.printStackTrace();}
								break;
							}
					}
					/*Check Compelete*/	
					txtArea.setText("");
					strFileName = "Unnamed";
					frmTextEditor.setTitle("Text Editor" + " - " + strFileName);
					BFileSaved = true;
				}
			}
		});
		mntmNew.setIcon(new ImageIcon(WinMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JFileChooser openwin = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("txt File", "txt");
				openwin.addChoosableFileFilter(filter);
				int option = openwin.showDialog(frmTextEditor, null);
				if(option == JFileChooser.APPROVE_OPTION) {
					/*Check file saved first*/
					int selection = saveAlert(BFileSaved);
					switch (selection) {
						case JOptionPane.NO_OPTION:
							break;
						case JOptionPane.CANCEL_OPTION:
							return;
						case JOptionPane.YES_OPTION:
							if(strFileName == "Unnamed"){
								JFileChooser saveaswin = new JFileChooser();
								saveaswin.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
								FileFilter filter1 = new FileNameExtensionFilter("txt File", "txt");
								saveaswin.setFileFilter(filter1);
								//saveaswin.setSelectedFile(new File("unamed.txt"));
								
								int option1 = saveaswin.showDialog(frmTextEditor, "Save File");
								if(option1 == JFileChooser.APPROVE_OPTION) {
								File file = saveaswin.getSelectedFile();
								saveaswin.setFileSelectionMode(JFileChooser.FILES_ONLY);
								System.out.println(file.toString());
										
								String[] content = txtArea.getText().split("\n");
								String path = file.getPath();
								//Begin to write in file.
								try {
									//If there's no such a file
									FileWriter fwriter = new FileWriter(path);
									//fwriter.write(content);
									for(int i = 0; i < content.length; ++i){
										fwriter.write(content[i]);
										if(i != content.length - 1)
											fwriter.write("\r\n");
									}
									fwriter.close();
									frmTextEditor.setTitle("Text Editor - " + path);
									strFileName = path;
									BFileSaved = true;
									} catch (IOException e1) { e1.printStackTrace(); }
								break;
								} 
							} else {
								try {
									FileWriter fwriter = new FileWriter(strFileName);
									String[] content = txtArea.getText().split("\n");
									for(int i = 0; i < content.length; ++i){
										fwriter.write(content[i]);
										if(i != content.length - 1)
											fwriter.write("\r\n");
									}
									fwriter.close();
									BFileSaved = true;
								} catch (IOException e1) {e1.printStackTrace();}
								break;
							}
					}
					/*Check Compelete*/
				  File file = openwin.getSelectedFile();
				  String path = file.getPath();
				  try {
					System.setIn(new FileInputStream(path));
					Scanner fs = new Scanner(System.in);
					String content = "";
					while(fs.hasNextLine()) {
						content = content + fs.nextLine();
						content = content + '\n';
					}
					txtArea.setText(content);
					frmTextEditor.setTitle("Text Editor - " + path);
					strFileName = path;
					fs.close();
					BFileSaved = true;
				  	} catch (FileNotFoundException e1) {
				  		e1.printStackTrace();
				  	}
				  
				  //the following you write the processing of the reading from the reader and
				  //writing them into the text pane.
				}
			}
		});
		mntmOpen.setIcon(new ImageIcon(WinMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(strFileName == "Unnamed"){
					JFileChooser saveaswin = new JFileChooser();
					saveaswin.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
					FileFilter filter1 = new FileNameExtensionFilter("txt File", "txt");
					saveaswin.setFileFilter(filter1);
					//saveaswin.setSelectedFile(new File("unamed.txt"));
					
					int option1 = saveaswin.showDialog(frmTextEditor, "Save File");
					if(option1 == JFileChooser.APPROVE_OPTION) {
					File file = saveaswin.getSelectedFile();
					saveaswin.setFileSelectionMode(JFileChooser.FILES_ONLY);
					System.out.println(file.toString());
							
					String[] content = txtArea.getText().split("\n");
					String path = file.getPath();
					//Begin to write in file.
					try {
						//If there's no such a file
						FileWriter fwriter = new FileWriter(path);
						//fwriter.write(content);
						for(int i = 0; i < content.length; ++i){
							fwriter.write(content[i]);
							if(i != content.length - 1)
								fwriter.write("\r\n");
						}
						fwriter.close();
						frmTextEditor.setTitle("Text Editor - " + path);
						strFileName = path;
						BFileSaved = true;
						frmTextEditor.setTitle("Text Editor - " + strFileName);
						} catch (IOException e1) { e1.printStackTrace(); }
					} 
				} else {
					try {
						FileWriter fwriter = new FileWriter(strFileName);
						String[] content = txtArea.getText().split("\n");
						for(int i = 0; i < content.length; ++i){
							fwriter.write(content[i]);
							if(i != content.length - 1)
								fwriter.write("\r\n");
						}
						fwriter.close();
						BFileSaved = true;
						frmTextEditor.setTitle("Text Editor - " + strFileName);
					} catch (IOException e1) {e1.printStackTrace();}
				}
			}
		});
		mntmSave.setIcon(new ImageIcon(WinMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JFileChooser saveaswin = new JFileChooser();
				saveaswin.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
				FileFilter filter = new FileNameExtensionFilter("txt File", "txt");
				saveaswin.setFileFilter(filter);
				//saveaswin.setSelectedFile(new File("unamed.txt"));
				
				int option = saveaswin.showDialog(frmTextEditor, "Save File");
				if(option == JFileChooser.APPROVE_OPTION) {
					File file = saveaswin.getSelectedFile();
					saveaswin.setFileSelectionMode(JFileChooser.FILES_ONLY);
					System.out.println(file.toString());
					
					String[] content = txtArea.getText().split("\n");
					String path = file.getPath();
					//Begin to write in file.
					try {
						//If there's no such a file
						FileWriter fwriter = new FileWriter(path);
						//fwriter.write(content);
						for(int i = 0; i < content.length; ++i){
							fwriter.write(content[i]);
							if(i != content.length - 1)
								fwriter.write("\r\n");
							}
						fwriter.close();
						frmTextEditor.setTitle("Text Editor - " + path);
						strFileName = path;
						BFileSaved = true;
					} catch (IOException e1) { e1.printStackTrace(); }
				}
				//endif
			}
		});
		mntmSaveAs.setIcon(new ImageIcon(WinMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setIcon(new ImageIcon(WinMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mntmClose.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(BFileSaved) {
						frmTextEditor.dispose();
						frmTextEditor.validate();
					}
					else {
						/*Check file saved first*/
						int selection = saveAlert(BFileSaved);
						switch (selection) {
							case JOptionPane.NO_OPTION:
								break;
							case JOptionPane.CANCEL_OPTION:
								return;
							case JOptionPane.YES_OPTION:
								if(strFileName == "Unnamed"){
									JFileChooser saveaswin = new JFileChooser();
									saveaswin.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
									FileFilter filter1 = new FileNameExtensionFilter("txt File", "txt");
									saveaswin.setFileFilter(filter1);
									//saveaswin.setSelectedFile(new File("unamed.txt"));
									
									int option1 = saveaswin.showDialog(frmTextEditor, "Save File");
									if(option1 == JFileChooser.APPROVE_OPTION) {
									File file = saveaswin.getSelectedFile();
									saveaswin.setFileSelectionMode(JFileChooser.FILES_ONLY);
									System.out.println(file.toString());
											
									String[] content = txtArea.getText().split("\n");
									String path = file.getPath();
									//Begin to write in file.
									try {
										//If there's no such a file
										FileWriter fwriter = new FileWriter(path);
										//fwriter.write(content);
										for(int i = 0; i < content.length; ++i){
											fwriter.write(content[i]);
											if(i != content.length - 1)
												fwriter.write("\r\n");
										}
										fwriter.close();
										frmTextEditor.setTitle("Text Editor - " + path);
										strFileName = path;
										BFileSaved = true;
										} catch (IOException e1) { e1.printStackTrace(); }
									break;
									} 
								} else {
									try {
										FileWriter fwriter = new FileWriter(strFileName);
										String[] content = txtArea.getText().split("\n");
										for(int i = 0; i < content.length; ++i){
											fwriter.write(content[i]);
											if(i != content.length - 1)
												fwriter.write("\r\n");
										}
										fwriter.close();
										BFileSaved = true;
									} catch (IOException e1) {e1.printStackTrace();}
									break;
								}
						}
						/*Check Compelete*/
						frmTextEditor.dispose();
						frmTextEditor.validate();
					}
				}
			}
		);

		frmTextEditor.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(BFileSaved) {
					frmTextEditor.dispose();
					frmTextEditor.validate();
				}
				else {
					/*Check file saved first*/
					int selection = saveAlert(BFileSaved);
					switch (selection) {
						case JOptionPane.NO_OPTION:
							frmTextEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							break;
						case JOptionPane.CANCEL_OPTION:
							frmTextEditor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  //阻止关闭窗口
							return;
						case JOptionPane.YES_OPTION:
							if(strFileName == "Unnamed"){
								JFileChooser saveaswin = new JFileChooser();
								saveaswin.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
								FileFilter filter1 = new FileNameExtensionFilter("txt File", "txt");
								saveaswin.setFileFilter(filter1);
								//saveaswin.setSelectedFile(new File("unamed.txt"));
								
								int option1 = saveaswin.showDialog(frmTextEditor, "Save File");
								if(option1 == JFileChooser.APPROVE_OPTION) {
								File file = saveaswin.getSelectedFile();
								saveaswin.setFileSelectionMode(JFileChooser.FILES_ONLY);
								System.out.println(file.toString());
										
								String[] content = txtArea.getText().split("\n");
								String path = file.getPath();
								//Begin to write in file.
								try {
									//If there's no such a file
									FileWriter fwriter = new FileWriter(path);
									//fwriter.write(content);
									for(int i = 0; i < content.length; ++i){
										fwriter.write(content[i]);
										if(i != content.length - 1)
											fwriter.write("\r\n");
									}
									fwriter.close();
									frmTextEditor.setTitle("Text Editor - " + path);
									strFileName = path;
									BFileSaved = true;
									} catch (IOException e1) { e1.printStackTrace(); }
								frmTextEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								break;
								} 
							} else {
								try {
									FileWriter fwriter = new FileWriter(strFileName);
									String[] content = txtArea.getText().split("\n");
									for(int i = 0; i < content.length; ++i){
										fwriter.write(content[i]);
										if(i != content.length - 1)
											fwriter.write("\r\n");
									}
									fwriter.close();
									BFileSaved = true;
								} catch (IOException e1) {e1.printStackTrace();}
								frmTextEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								break;
							}
					}
					/*Check Compelete*/	
				}
			}
		});
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		mnFile.add(mntmClose);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmReplace = new JMenuItem("Replace...");
		mnEdit.add(mntmReplace);
		
		JSeparator separator_1 = new JSeparator();
		mnEdit.add(separator_1);
		
		JMenuItem mntmScan = new JMenuItem("Scan...");
		mntmScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScanWin scanwin = new ScanWin();
				searchingText = scanwin.showScanDialog(frmTextEditor);
				System.out.println(searchingText);
			}
		});
		mnEdit.add(mntmScan);
		
		JMenuItem mntmScanNext = new JMenuItem("Scan next");
		mnEdit.add(mntmScanNext);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		JMenuItem mntmTurnToLine = new JMenuItem("Turn to line...");
		mntmTurnToLine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				////////////////////////////////////////////////////////////////////
				final TurnToLinePanel turntolinewin = new TurnToLinePanel();
				turntolinewin.setVisible(true);
				turntolinewin.spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(txtArea.getLineCount()), new Integer(1)));
				turntolinewin.btnTurnTo.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int line = turntolinewin.getLine();
						int position = 0;
						try {
							position = txtArea.getLineStartOffset(line);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
						txtArea.requestFocus();
						txtArea.setSelectionStart(position);
						txtArea.setSelectionEnd(position);
						turntolinewin.dispose();
						turntolinewin.validate();
					}
				});
				
				////////////////////////////////////////////////////////////////////
			}
		});
		mnEdit.add(mntmTurnToLine);
		
		JMenu mnPattern = new JMenu("Pattern");
		menuBar.add(mnPattern);
		
		final JCheckBoxMenuItem chckbxmntmAutoLineWrap = new JCheckBoxMenuItem("Auto line wrap");
		/* EVENT: Auto Line wrap selection */
		chckbxmntmAutoLineWrap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(chckbxmntmAutoLineWrap.getState() == true)
				{
					txtArea.setLineWrap(true);
				}
				else
				{
					txtArea.setLineWrap(false);
				}
			}
		});
		mnPattern.add(chckbxmntmAutoLineWrap);
		
		JMenuItem mntmFont = new JMenuItem("Font...");
		mntmFont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 构造字体选择器，参数字体为预设值  
                MQFontChooser fontChooser = new MQFontChooser(txtArea.getFont());  
                // 打开一个字体选择器窗口，参数为父级所有者窗体。返回一个整型，代表设置字体时按下了确定或是取消，可参考MQFontChooser.APPROVE_OPTION和MQFontChooser.CANCEL_OPTION  
                int returnValue = fontChooser.showFontDialog(null);  
                // 如果按下的是确定按钮  
                if (returnValue == MQFontChooser.APPROVE_OPTION) {  
                    // 获取选择的字体  
                    Font font = fontChooser.getSelectFont();  
                    // 将字体设置到JTextArea中  
                    txtArea.setFont(font);  
                }
			}
		});
		
		mnPattern.add(mntmFont);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		/* About Panel */
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				AboutPanel infowin = new AboutPanel();
				infowin.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);
	}
	
	public int saveAlert(boolean flag) {
		if(!flag)
			return JOptionPane.showConfirmDialog (null
				, "Save this file now?"
				, "Save alert"
				, JOptionPane.YES_NO_CANCEL_OPTION
				, JOptionPane.WARNING_MESSAGE);
		else
			return -1;
	}
	
	//Get Row
	public static int getRow(int pos, JTextComponent editor) {
	        int rn = (pos==0) ? 1 : 0;
	        try {
	            int offs=pos;
	            while( offs>0) {
	                offs=Utilities.getRowStart(editor, offs)-1;
	                rn++;
	            }
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
	        return rn;
	    }
	 //Get Column
	    public static int getColumn(int pos, JTextComponent editor) {
	        try {
	            return pos-Utilities.getRowStart(editor, pos)+1;
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
	        return -1;
	    }
}
