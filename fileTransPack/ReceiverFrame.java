package fileTransPack;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ReceiverFrame extends JFrame{
	
	/**
	 * 	Server GUI Frame
	 */
	private static final long serialVersionUID = -6908010068571328476L;
	private final JTextArea textArea = new JTextArea();
	private final JPanel panel = new JPanel();
	private final Label label = new Label("Port");
	private final JTextField textField = new JTextField();
	private final JButton btnStartListening = new JButton("Start Listening");
	private final JButton btnStopListening = new JButton("Stop Listening");
	private Boolean breakflag = false;
	private ReceiverThread rt;
	private final JButton btnPaste = new JButton("Paste");
	
	public ReceiverFrame(final JTextArea txtArea) {
		textField.setText("10086");
		textField.setColumns(10);
		setTitle("Text Receiver");
		
		getContentPane().add(textArea, BorderLayout.CENTER);
		
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		panel.add(label);
		
		panel.add(textField);
		
		panel.add(btnStartListening);
		
		panel.add(btnStopListening);
		btnStopListening.setEnabled(false);
		//Add event of clicking paste button
		btnPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String text = textArea.getText();
				if(txtArea.getText().equals(""))
					txtArea.setText(text);
				else
					txtArea.append("\n" + text);
			}
		});
		
		panel.add(btnPaste);
		
		//Add event of clicking start receiver
		btnStartListening.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				breakflag = false;
				btnStartListening.setEnabled(false);
				btnStopListening.setEnabled(true);
				rt = new ReceiverThread(textArea, Integer.parseInt(textField.getText()), breakflag);
				rt.start();
			}
			
		});
		
		//Add event of clicking halt receiver
		btnStopListening.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(!rt.serversocket.isClosed()) {
					try {
						rt.serversocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breakflag = true;
				btnStopListening.setEnabled(false);
				btnStartListening.setEnabled(true);
			}
		});
	}
}

//Uses a thread to host a server, in order to receive text from the sender.
class ReceiverThread extends Thread {
	private Boolean breakflag;
	private JTextArea textField;
	private int port;
	public ServerSocket serversocket;
	private Socket client;
	
	public ReceiverThread(JTextArea textField, int port, Boolean breakflag) {
		super();
		this.textField = textField;
		this.port = port;
		this.breakflag = breakflag;
		
	}
	
	//Start receiver thread
	@Override
	public void run() {
		
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Failed to run serversocket.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		while (breakflag == false) {
			try {
				client = serversocket.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
				
				//String line = reader.readLine();
				//textField.append(line + "\r\n");
				String line = reader.readLine();
				while(line != null) {
					textField.append(line + "\r\n");
					line = reader.readLine();
				}
				
				writer.close();
				reader.close();
				client.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Server closed.", "Alert", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		breakflag = false;
		try {
			serversocket.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to stop serversocket.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		JOptionPane.showMessageDialog(null, "Break.", "Alert", JOptionPane.WARNING_MESSAGE);
		return;
	}
	
}