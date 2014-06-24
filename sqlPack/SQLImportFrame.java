package sqlPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class SQLImportFrame extends JFrame {
	/**
	 * SQL Import Panel.
	 */
	private static final long serialVersionUID = -2435289349824361413L;
	private JTextField tfPort;
	private JTextField tfUrl;
	private JTextField tfUser;
	private JPasswordField tfPassword;
	private JTextField tfColumn;
	private JTextField tfTable;
	private JTextField tfOrderby;
	private JTextArea tfWhere;
	private JCheckBox chckbxWhere;
	private JCheckBox chckbxOrderBy;
	private JButton btnOk;
	private JTextArea txtArea;
	private JLabel lblDatabase;
	private JTextField tfDB;
	
	private boolean whereFlag;
	private boolean orderbyFlag;
	
	public SQLImportFrame(JTextArea txtArea) {
		this.txtArea = txtArea;
		whereFlag = false;
		orderbyFlag = false;
		
		setResizable(false);
		setTitle("Import from MySQL");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblPort = new JLabel("Port");
		springLayout.putConstraint(SpringLayout.NORTH, lblPort, 21, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPort, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblPort);
		
		tfPort = new JTextField();
		tfPort.setHorizontalAlignment(SwingConstants.CENTER);
		tfPort.setText("3306");
		springLayout.putConstraint(SpringLayout.NORTH, tfPort, -3, SpringLayout.NORTH, lblPort);
		springLayout.putConstraint(SpringLayout.WEST, tfPort, 6, SpringLayout.EAST, lblPort);
		getContentPane().add(tfPort);
		tfPort.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL");
		springLayout.putConstraint(SpringLayout.NORTH, lblUrl, 0, SpringLayout.NORTH, lblPort);
		getContentPane().add(lblUrl);
		
		tfUrl = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, lblUrl, -6, SpringLayout.WEST, tfUrl);
		springLayout.putConstraint(SpringLayout.WEST, tfUrl, 215, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tfUrl, -10, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, tfUrl, -3, SpringLayout.NORTH, lblPort);
		tfUrl.setHorizontalAlignment(SwingConstants.CENTER);
		tfUrl.setText("127.0.0.1");
		getContentPane().add(tfUrl);
		tfUrl.setColumns(10);
		
		JLabel lblUser = new JLabel("User");
		springLayout.putConstraint(SpringLayout.NORTH, lblUser, 21, SpringLayout.SOUTH, tfPort);
		springLayout.putConstraint(SpringLayout.WEST, lblUser, 0, SpringLayout.WEST, lblPort);
		getContentPane().add(lblUser);
		
		tfUser = new JTextField();
		tfUser.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, tfUser, -3, SpringLayout.NORTH, lblUser);
		springLayout.putConstraint(SpringLayout.WEST, tfUser, 0, SpringLayout.WEST, tfPort);
		tfUser.setText("root");
		getContentPane().add(tfUser);
		tfUser.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 0, SpringLayout.NORTH, lblUser);
		springLayout.putConstraint(SpringLayout.EAST, lblPassword, 0, SpringLayout.EAST, lblUrl);
		getContentPane().add(lblPassword);
		
		tfPassword = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, tfPassword, -3, SpringLayout.NORTH, lblUser);
		springLayout.putConstraint(SpringLayout.WEST, tfPassword, 0, SpringLayout.WEST, tfUrl);
		springLayout.putConstraint(SpringLayout.EAST, tfPassword, 0, SpringLayout.EAST, tfUrl);
		tfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(tfPassword);
		
		JLabel lblCommand = new JLabel("Command");
		springLayout.putConstraint(SpringLayout.WEST, lblCommand, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblCommand);
		
		JLabel lblSelect = new JLabel("SELECT");
		springLayout.putConstraint(SpringLayout.NORTH, lblSelect, 15, SpringLayout.SOUTH, lblCommand);
		springLayout.putConstraint(SpringLayout.WEST, lblSelect, 0, SpringLayout.WEST, lblPort);
		getContentPane().add(lblSelect);
		
		tfColumn = new JTextField();
		tfColumn.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, tfColumn, 165, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblCommand, -12, SpringLayout.NORTH, tfColumn);
		springLayout.putConstraint(SpringLayout.WEST, tfColumn, 6, SpringLayout.EAST, lblSelect);
		tfColumn.setText("*");
		getContentPane().add(tfColumn);
		tfColumn.setColumns(10);
		
		JLabel lblFrom = new JLabel("FROM");
		springLayout.putConstraint(SpringLayout.EAST, tfColumn, -6, SpringLayout.WEST, lblFrom);
		springLayout.putConstraint(SpringLayout.NORTH, lblFrom, 0, SpringLayout.NORTH, lblSelect);
		springLayout.putConstraint(SpringLayout.EAST, lblFrom, 0, SpringLayout.EAST, lblUrl);
		getContentPane().add(lblFrom);
		
		tfTable = new JTextField();
		tfTable.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, tfTable, -3, SpringLayout.NORTH, lblSelect);
		springLayout.putConstraint(SpringLayout.WEST, tfTable, 6, SpringLayout.EAST, lblFrom);
		springLayout.putConstraint(SpringLayout.EAST, tfTable, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(tfTable);
		tfTable.setColumns(10);
		
		chckbxWhere = new JCheckBox("WHERE");
		springLayout.putConstraint(SpringLayout.WEST, chckbxWhere, 0, SpringLayout.WEST, lblPort);
		getContentPane().add(chckbxWhere);
		
		tfWhere = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, tfWhere, 228, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tfWhere, 20, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tfWhere, -21, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, chckbxWhere, -6, SpringLayout.NORTH, tfWhere);
		tfWhere.setEnabled(false);
		tfWhere.setEditable(false);
		getContentPane().add(tfWhere);
		
		chckbxOrderBy = new JCheckBox("ORDER BY");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxOrderBy, 297, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tfWhere, -8, SpringLayout.NORTH, chckbxOrderBy);
		springLayout.putConstraint(SpringLayout.WEST, chckbxOrderBy, 0, SpringLayout.WEST, lblPort);
		getContentPane().add(chckbxOrderBy);
		
		tfOrderby = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, tfOrderby, 1, SpringLayout.NORTH, chckbxOrderBy);
		springLayout.putConstraint(SpringLayout.WEST, tfOrderby, 109, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tfOrderby, 0, SpringLayout.EAST, tfUrl);
		tfOrderby.setHorizontalAlignment(SwingConstants.CENTER);
		tfOrderby.setEditable(false);
		tfOrderby.setEnabled(false);
		getContentPane().add(tfOrderby);
		tfOrderby.setColumns(10);
		
		btnOk = new JButton("OK");
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, -63, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 126, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnOk, -23, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnOk, 99, SpringLayout.EAST, tfPort);
		getContentPane().add(btnOk);
		
		lblDatabase = new JLabel("Import from DataBase");
		springLayout.putConstraint(SpringLayout.NORTH, lblDatabase, 19, SpringLayout.SOUTH, tfUser);
		springLayout.putConstraint(SpringLayout.WEST, lblDatabase, 0, SpringLayout.WEST, lblPort);
		getContentPane().add(lblDatabase);
		
		tfDB = new JTextField();
		tfDB.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, tfDB, 16, SpringLayout.SOUTH, tfPassword);
		springLayout.putConstraint(SpringLayout.WEST, tfDB, 0, SpringLayout.WEST, lblFrom);
		springLayout.putConstraint(SpringLayout.EAST, tfDB, 0, SpringLayout.EAST, tfUrl);
		getContentPane().add(tfDB);
		tfDB.setColumns(10);
		
		this.addEvents();
		this.init();
	}
	
	public JTextField getTfPort() {
		return tfPort;
	}
	public JTextField getTfUrl() {
		return tfUrl;
	}
	public JTextField getTfUser() {
		return tfUser;
	}
	public JPasswordField getTfPassword() {
		return tfPassword;
	}
	public JTextField getTfColumn() {
		return tfColumn;
	}
	public JTextField getTfTable() {
		return tfTable;
	}
	public JTextArea getTfWhere() {
		return tfWhere;
	}
	public JCheckBox getChckbxWhere() {
		return chckbxWhere;
	}
	public JTextField getTfOrderby() {
		return tfOrderby;
	}
	public JCheckBox getChckbxOrderBy() {
		return chckbxOrderBy;
	}
	public JButton getBtnOk() {
		return btnOk;
	}
	public JTextField getTfDB() {
		return tfDB;
	}

	public void init() {
		this.setBounds(300, 300, 384, 448);
		this.setVisible(true);
	}

	//添加按钮事件
	private void addEvents() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://"+ getTfUrl().getText() +":" + getTfPort().getText() + "/" + getTfDB().getText();
				String user = getTfUser().getText();
				String password = new String(getTfPassword().getPassword());
				String output = "";
				
				// 加载驱动程序
				try {
					Class.forName(driver);	
				
					// 连接数据库
					Connection conn = DriverManager.getConnection(url, user, password);
					if(!conn.isClosed()) 
		                System.out.println("Succeeded connecting to the Database!");
					Statement statement = conn.createStatement();
					
					//执行的sql语句
					String sql = "SELECT " + getTfColumn().getText() + " FROM " + getTfTable().getText();
					
					//是否有where和order by
					if(whereFlag)
						sql = sql + " WHERE " + getTfWhere().getText();
					if(orderbyFlag)
						sql = sql + " ORDER BY " + getTfOrderby().getText();
					
					// 结果集
		            ResultSet rs = statement.executeQuery(sql);
		            
		            int colnum = rs.getMetaData().getColumnCount();
		            
		            //第一行是列的名称
		            for(int i = 1; i <= colnum; i++) {
		            	output = output + rs.getMetaData().getColumnName(i);
		            	if( i != colnum)
	                		output = output + ",";
		            }
		            output = output + "\n";
		            
		            //接下来输出各行
		            while(rs.next()) {
		            	
		                for(int i = 1; i <= colnum; i++) {
		                	output = output + rs.getString(i);
		                	if( i != colnum)
		                		output = output + ",";
		                }
		                output = output + "\n";
		                
		            }
		            
		            //永远别忘记.close()
		            rs.close();
		            conn.close();
		            
		            //成功导入时记得提示成功
		            JOptionPane.showMessageDialog(null, "Import Successfully.", "Done", JOptionPane.INFORMATION_MESSAGE);
		            
				} catch (SQLException | ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
	            
				if(txtArea.getText().equals(""))
					txtArea.setText(output);
				else
					txtArea.append(output);
			}
		});

		chckbxWhere.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!whereFlag) {
					chckbxWhere.setSelected(true);
					tfWhere.setEditable(true);
					tfWhere.setEnabled(true);
					whereFlag = true;
				}
				else {
					chckbxWhere.setSelected(false);
					tfWhere.setEditable(false);
					tfWhere.setEnabled(false);
					whereFlag = false;
				}
				
			}
			
		});
		
		chckbxOrderBy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
				if(!orderbyFlag) {
					chckbxOrderBy.setSelected(true);
					tfOrderby.setEditable(true);
					tfOrderby.setEnabled(true);
					orderbyFlag = true;
				}
				else {
					chckbxOrderBy.setSelected(false);
					tfOrderby.setEditable(false);
					tfOrderby.setEnabled(false);
					orderbyFlag = false;
				}
				
			}
		});
	}
	
	
	
}
