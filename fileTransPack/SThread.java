package fileTransPack;

import javax.swing.JTextArea;

public class SThread extends Thread {
	private SenderFrame sfrm;
	private JTextArea txtArea;
	
	public SThread(JTextArea txtArea) {
		super();
		this.txtArea = txtArea;
	}
	
	@Override
	public void run() {
		sfrm = new SenderFrame(txtArea);
		sfrm.setBounds(300, 300, 550, 400);
		sfrm.setVisible(true);
	}
}
