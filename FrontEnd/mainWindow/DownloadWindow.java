package mainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import FrontEnd.AssetLoader;

public class DownloadWindow {

	
private JFrame window;
private JTextField fileAmount;
private JTextField fileName;
private JProgressBar downloadProgress;
private JProgressBar fileProgress;
private JButton close;

private int fAmount = 0;
private long fSize = 0;	
private int curFileNr = 1;

	public DownloadWindow(AssetLoader asLo) {
		
		window = new JFrame();
		window.setSize(300,250);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setIconImage(asLo.IconOff.getImage());
		window.setTitle("Downloading");
		
		JPanel content = new JPanel();
		content.setSize(300,250);
		content.setLayout(null);
		
		fileAmount = new JTextField();
		fileAmount.setSize(200,25);
		fileAmount.setLocation(50, 25);
		fileAmount.setEditable(false);
		fileAmount.setBackground(null);
		fileAmount.setBorder(null);
		
		downloadProgress = new JProgressBar();
		downloadProgress.setSize(200,25);
		downloadProgress.setLocation(50, 50);
		downloadProgress.setMinimum(0);
		
		fileName = new JTextField();
		fileName.setSize(200,25);
		fileName.setLocation(50, 100);
		fileName.setEditable(false);
		fileName.setBackground(null);
		fileName.setBorder(null);
		
		fileProgress = new JProgressBar();
		fileProgress.setSize(200,25);
		fileProgress.setLocation(50,125);
		fileProgress.setMaximum(100);
		fileProgress.setMinimum(0);
		
		close = new JButton();
		close.setText("Close");
		close.setVisible(false);
		close.setSize(100,25);
		close.setLocation(100, 175);
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.setVisible(false);
			}
			
		});
		
		content.add(fileAmount);
		content.add(downloadProgress);
		content.add(fileName);
		content.add(fileProgress);
		content.add(close);
		
		window.setContentPane(content);
	}
	
	
	public void setVisible(boolean bool) {
		window.setVisible(bool);
	}
	
	public void setFileAmount(int amount) {
		fileAmount.setText(curFileNr + " : " + amount);
		fAmount = amount;
		downloadProgress.setMaximum(amount);
	}
	
	public void setFileName(String fname) {
		fileName.setText(fname);
	}

	public void setFileSize(long size) {
		fSize = size;
		fileProgress.setMaximum((int) fSize / 1000);
	}
	
	public void updateFileBar(long fileSize) {
		
		fileProgress.setValue((int)(fileSize / 1000));
		fileProgress.validate();
		fileProgress.revalidate();
		fileProgress.repaint();
	}
	
	public void updateFileNr(int nr) {
		curFileNr = nr;
		downloadProgress.setValue((int)(100 / fAmount  * (curFileNr- 1)));
	}
	
	public void setCloseAble() {
		close.setVisible(true);
		fileAmount.setText("Finished!");
		window.setTitle("Download Finished");
	}
	
}
