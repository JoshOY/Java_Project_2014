package fileTransPack;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;


public class SenderFrame extends JFrame{
	/**
	 *  Client GUI Frame
	 */
	private static final long serialVersionUID = 4370740194011232752L;
	//public
	public final JTextField txtPort;
	public final JTextField txtIP;
	public final JButton btnConnect;
	public JTextArea txtArea;
	
	public SenderFrame(JTextArea sendingtxtarea) {
		txtArea = sendingtxtarea;
		setResizable(false);
		setTitle("Text Sender");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		Label labelPort = new Label("Receiver Port");
		getContentPane().add(labelPort);
		
		Label labelHostip = new Label("Receiver IP");
		springLayout.putConstraint(SpringLayout.SOUTH, labelPort, -6, SpringLayout.NORTH, labelHostip);
		springLayout.putConstraint(SpringLayout.NORTH, labelHostip, 71, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, labelHostip, 0, SpringLayout.WEST, labelPort);
		getContentPane().add(labelHostip);
		
		txtPort = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, labelPort, -21, SpringLayout.WEST, txtPort);
		txtPort.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.WEST, txtPort, 151, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtPort, 0, SpringLayout.SOUTH, labelPort);
		txtPort.setText("10086");
		getContentPane().add(txtPort);
		txtPort.setColumns(10);
		
		txtIP = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, txtIP, 151, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtIP, -57, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtPort, 0, SpringLayout.EAST, txtIP);
		springLayout.putConstraint(SpringLayout.EAST, labelHostip, -24, SpringLayout.WEST, txtIP);
		txtIP.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, txtIP, 72, SpringLayout.NORTH, getContentPane());
		txtIP.setText("127.0.0.1");
		getContentPane().add(txtIP);
		txtIP.setColumns(10);
		
		//When connect
		btnConnect = new JButton("Connect");
		springLayout.putConstraint(SpringLayout.NORTH, btnConnect, 33, SpringLayout.SOUTH, labelHostip);
		springLayout.putConstraint(SpringLayout.WEST, btnConnect, 0, SpringLayout.WEST, labelPort);
		springLayout.putConstraint(SpringLayout.SOUTH, btnConnect, -42, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnConnect, 0, SpringLayout.EAST, txtPort);
		getContentPane().add(btnConnect);
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SendThread(txtArea, txtIP.getText(), Integer.parseInt(txtPort.getText())).start();
			}
		});
	}
}

class SendThread extends Thread {
	private Socket socket;
	private JTextArea txtArea;
	private String hostip;
	private int port;
	
	public SendThread(JTextArea txtarea, String hostip, int port) {
		super();
		this.txtArea = txtarea;
		this.hostip = hostip;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(hostip, port);
			// 由Socket对象得到输出流，并构造PrintWriter对象
			PrintWriter os = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//将输字符串输出到Receiver
			String sendingtext = txtArea.getText();
			os.print(sendingtext);
			os.flush();
			
			is.close();
			os.close();
			socket.close();	
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "UnknownHostException.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Exception.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

/*
final ClientFrame cf = new ClientFrame();
cf.btnConnect.addMouseListener(new MouseAdapter() {
	@Override
	public void mouseClicked(MouseEvent e) {
		//首先获取端口和服务端IP
		int port = Integer.parseInt(cf.txtPort.getText());
		String hostip = cf.txtIP.getText();
		//然后
		try {
			socket = new Socket(hostip, port);
			// 由Socket对象得到输出流，并构造PrintWriter对象
			PrintWriter os = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // 将输字符串hello world输出到Server
            os.println("JoshOY@" + socket.getLocalAddress().toString());
            // 刷新输出流，使Server马上收到该字符串
            os.flush();
            
            is.close();
            os.close();
            socket.close();	            
		}
		catch (Exception error) {
			System.out.println("Exception:" + error.toString());
		}
	}
});

cf.setBounds(200, 200, 570, 300);
cf.setVisible(true);*/
