package MsgboxPack;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileOpenPanel {
   
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Choose File v1.0");
        final Container content = frame.getContentPane();
        content.setLayout(new GridBagLayout());
        JButton button = new JButton("Choose File...");
        content.add(button);
       
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //���ļ�ѡ��Ի���
                JFileChooser file = new JFileChooser();
                file.showOpenDialog(frame);
                //�������Ϊnull������ʾ����ʾ�����룬������ʾ��frame����
            }
        });
       
        frame.setSize(200,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}