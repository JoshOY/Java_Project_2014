package sqlPack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;

public class SQLExportFrame extends JFrame {
	/**
	 *   SQL Export Frame
	 */
	private static final long serialVersionUID = -2043318843864400177L;
	private JTextField tfPort;
	private JTextField tfUser;
	private JTextField tfURL;
	private JPasswordField tfPassword;
	private JTextField tfDB;
	private JTextField tfTable;
	private final JButton btnExport;
	public SQLExportFrame(final JTextArea txtArea) {
		setTitle("Export to MySQL");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblUsername = new JLabel("User");
		getContentPane().add(lblUsername);
		
		JLabel lblPort = new JLabel("Port");
		springLayout.putConstraint(SpringLayout.WEST, lblUsername, 0, SpringLayout.WEST, lblPort);
		springLayout.putConstraint(SpringLayout.NORTH, lblPort, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPort, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblPort);
		
		JLabel lblPassword = new JLabel("Password");
		springLayout.putConstraint(SpringLayout.NORTH, lblUsername, 0, SpringLayout.NORTH, lblPassword);
		getContentPane().add(lblPassword);
		
		JLabel lblUrl = new JLabel("URL");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 25, SpringLayout.SOUTH, lblUrl);
		springLayout.putConstraint(SpringLayout.WEST, lblUrl, 155, SpringLayout.EAST, lblPort);
		springLayout.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, lblUrl);
		springLayout.putConstraint(SpringLayout.NORTH, lblUrl, 0, SpringLayout.NORTH, lblPort);
		getContentPane().add(lblUrl);
		
		tfPort = new JTextField();
		tfPort.setHorizontalAlignment(SwingConstants.CENTER);
		tfPort.setText("3306");
		springLayout.putConstraint(SpringLayout.NORTH, tfPort, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tfPort, 6, SpringLayout.EAST, lblPort);
		springLayout.putConstraint(SpringLayout.EAST, tfPort, 113, SpringLayout.EAST, lblPort);
		getContentPane().add(tfPort);
		tfPort.setColumns(10);
		
		tfUser = new JTextField();
		tfUser.setText("root");
		tfUser.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, tfUser, 16, SpringLayout.SOUTH, tfPort);
		springLayout.putConstraint(SpringLayout.WEST, tfUser, 6, SpringLayout.EAST, lblUsername);
		springLayout.putConstraint(SpringLayout.EAST, tfUser, 113, SpringLayout.EAST, lblUsername);
		getContentPane().add(tfUser);
		tfUser.setColumns(10);
		
		tfURL = new JTextField();
		tfURL.setHorizontalAlignment(SwingConstants.CENTER);
		tfURL.setText("127.0.0.1");
		springLayout.putConstraint(SpringLayout.NORTH, tfURL, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tfURL, 48, SpringLayout.EAST, lblUrl);
		springLayout.putConstraint(SpringLayout.EAST, tfURL, -19, SpringLayout.EAST, getContentPane());
		getContentPane().add(tfURL);
		tfURL.setColumns(10);
		
		tfPassword = new JPasswordField();
		tfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, tfPassword, -3, SpringLayout.NORTH, lblUsername);
		springLayout.putConstraint(SpringLayout.WEST, tfPassword, 6, SpringLayout.EAST, lblPassword);
		springLayout.putConstraint(SpringLayout.EAST, tfPassword, 0, SpringLayout.EAST, tfURL);
		getContentPane().add(tfPassword);
		
		btnExport = new JButton("Export");
		springLayout.putConstraint(SpringLayout.WEST, btnExport, 89, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnExport, -90, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnExport);
		
		JLabel lblImportIntoDatabase = new JLabel("Export to database :");
		springLayout.putConstraint(SpringLayout.NORTH, lblImportIntoDatabase, 33, SpringLayout.SOUTH, tfUser);
		springLayout.putConstraint(SpringLayout.WEST, lblImportIntoDatabase, 0, SpringLayout.WEST, lblUsername);
		getContentPane().add(lblImportIntoDatabase);
		
		tfDB = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, tfDB, 30, SpringLayout.SOUTH, tfPassword);
		springLayout.putConstraint(SpringLayout.WEST, tfDB, 11, SpringLayout.EAST, lblImportIntoDatabase);
		springLayout.putConstraint(SpringLayout.EAST, tfDB, -19, SpringLayout.EAST, getContentPane());
		tfDB.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(tfDB);
		tfDB.setColumns(10);
		
		JLabel lblUseCurrentTable = new JLabel("Export table:");
		springLayout.putConstraint(SpringLayout.WEST, lblUseCurrentTable, 0, SpringLayout.WEST, tfPort);
		getContentPane().add(lblUseCurrentTable);
		
		tfTable = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, btnExport, 35, SpringLayout.SOUTH, tfTable);
		springLayout.putConstraint(SpringLayout.NORTH, lblUseCurrentTable, 3, SpringLayout.NORTH, tfTable);
		springLayout.putConstraint(SpringLayout.NORTH, tfTable, 11, SpringLayout.SOUTH, tfDB);
		springLayout.putConstraint(SpringLayout.WEST, tfTable, 0, SpringLayout.WEST, lblPassword);
		springLayout.putConstraint(SpringLayout.EAST, tfTable, 0, SpringLayout.EAST, tfURL);
		tfTable.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(tfTable);
		tfTable.setColumns(10);
		
		setBounds(300, 300, 455, 288);
		setVisible(true);
		
		btnExport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://" + tfURL.getText() + ":" + tfPort.getText() + "/" + tfDB.getText();
				String user = tfUser.getText();
				String password = new String(tfPassword.getPassword());
				String table = tfTable.getText();
				String[] output = txtArea.getText().split("\n");
				
				// 加载驱动程序
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}	
				
				// 连接数据库
				Connection conn;
				try {
					conn = DriverManager.getConnection(url, user, password);
					if(!conn.isClosed()) 
						System.out.println("Succeeded connecting to the Database!");
					Statement statement = conn.createStatement();
					
					//执行的sql语句
					String sql = "";
					
					for(int i = 0; i < output.length; i++) {
						
						//防止空行影响输出
						if(output[i].equals(""))
							continue;
						
						//把各列元素分开
						String[] element = output[i].split(",");
						sql = "insert into " + table + " values(";
						for(int j = 0; j < element.length; j++){
							sql += ("'" + element[j] + "'");
							if(j != element.length - 1)
								sql += ",";
						}
						
						sql += ");";
						//System.out.println(sql);
						//执行……
						statement.executeUpdate(sql);
					}
					
					
					//永远别忘记.close()
		            conn.close();
		            
		            //成功导出时记得提示成功
		            JOptionPane.showMessageDialog(null, "Export Successfully.", "Done", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
				
		}});
	}
	
	
}
