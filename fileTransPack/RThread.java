package fileTransPack;

import javax.swing.JTextArea;


public class RThread extends Thread {
	
	private final JTextArea txtArea;
	
	private ReceiverFrame rfrm;
	public RThread(final JTextArea txtArea) {
		super();
		this.txtArea = txtArea;
	}
	
	@Override
	public void run() {
		
		rfrm = new ReceiverFrame(txtArea);
		rfrm.setBounds(300, 300, 550, 400);
		rfrm.setVisible(true);
	}
}
