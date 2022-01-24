package mainWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Engine.Controller;

public class About {

	
	private Controller con;
	

	private JPanel Panel;
		
	private String SautoConnect;
		
		public About(Controller Con) {
		
			con = Con;
			
			Panel = new JPanel();
			Panel.setSize(720,480);
			Panel.setLocation(0, 0);
			Panel.setLayout(null);
			Panel.setOpaque(false);
			
			JPanel Window = new JPanel();
			Window.setSize(new Dimension(655,370));
			Window.setBackground(Color.BLACK);
			Window.setLocation(25,25);
			Window.setLayout(null);
			
			JTextArea AT = new JTextArea(); //About Text
			AT.setSize(645,360);
			AT.setBorder(null);
			AT.setLocation(5, 5);
			AT.setEditable(false);
			AT.setText("	About Manic Miners Mod Manager :\n"
					+ "\n"
					+ "  This is a little java Program that helps to manage,\n"
					+ "   upload and download maps and textures. \n"
					+ "\n"
					+ "  Programmed for Manic Miners , by Baraklava. \n"
					+ "  Download : https://baraklava.itch.io/manic-miners \n"
					+ "\n"
					+ "\n"
					+ "\n"
					+ "\n"
					+ "  There may be some bugs or missing Features.\n"
					+ "  Contact ICsleep ( the Script Maniac ) on the MM Discord Server, \n"
					+ "  if you run into Problems or suggested additions. \n"
					+ "\n"
					+ "");
			
			Window.add(AT);
			
			Panel.add(Window);
			
			Panel.setVisible(true);
			
		}
		
		
		
		
		public JPanel getPanel() {
			return Panel;
		}
		
		
	
	
}
