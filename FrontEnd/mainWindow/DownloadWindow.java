package mainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import Engine.Controller;
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
		window.setSize((int)(300 * Controller.scale),(int)(250 * Controller.scale));
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setIconImage(asLo.IconOff.getImage());
		window.setTitle("Downloading");
		
		JPanel content = new JPanel();
		content.setSize((int)(300 * Controller.scale),(int)(250 * Controller.scale));
		content.setLayout(null);
		
		fileAmount = new JTextField();
		fileAmount.setFont(Controller.font);
		fileAmount.setFont(Controller.font);
		fileAmount.setSize((int)(200 * Controller.scale),(int)(25 * Controller.scale));
		fileAmount.setLocation((int)(50 * Controller.scale), (int)(25 * Controller.scale));
		fileAmount.setEditable(false);
		fileAmount.setBackground(null);
		fileAmount.setBorder(null);
		
		downloadProgress = new JProgressBar();
		downloadProgress.setSize((int)(200 * Controller.scale),(int)(25 * Controller.scale));
		downloadProgress.setLocation((int)(50 * Controller.scale),(int)(50 * Controller.scale));
		downloadProgress.setMinimum(0);
		
		fileName = new JTextField();
		fileName.setFont(Controller.font);
		fileName.setFont(Controller.font);
		fileName.setSize((int)(200 * Controller.scale),(int)(25 * Controller.scale));
		fileName.setLocation((int)(50 * Controller.scale), (int)(100 * Controller.scale));
		fileName.setEditable(false);
		fileName.setBackground(null);
		fileName.setBorder(null);
		
		fileProgress = new JProgressBar();
		fileProgress.setSize((int)(200 * Controller.scale),(int)(25 * Controller.scale));
		fileProgress.setLocation((int)(50 * Controller.scale),(int)(125 * Controller.scale));
		fileProgress.setMaximum(100);
		fileProgress.setMinimum(0);
		
		close = new JButton();
		close.setFont(Controller.font);
		close.setFont(Controller.font);
		close.setText("Close");
		close.setVisible(false);
		close.setSize((int)(100 * Controller.scale),(int)(25 * Controller.scale));
		close.setLocation((int)(100 * Controller.scale), (int)(175 * Controller.scale));
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
