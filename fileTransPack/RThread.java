package fileTransPack;


public class RThread extends Thread {
	
	private ReceiverFrame rfrm;
	public RThread() {
		super();
	}
	
	@Override
	public void run() {
		rfrm = new ReceiverFrame();
		rfrm.setBounds(300, 300, 550, 400);
		rfrm.setVisible(true);
	}
}
