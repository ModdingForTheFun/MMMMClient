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
		
		public About(Controller Con) {
		
			con = Con;
			
			Panel = new JPanel();
			Panel.setSize((int)(720 * Controller.scale),(int)(480 * Controller.scale));
			Panel.setLocation(0, 0);
			Panel.setLayout(null);
			Panel.setOpaque(false);
			
			JPanel Window = new JPanel();
			Window.setSize(new Dimension((int)(655 * Controller.scale),(int)(370 * Controller.scale)));
			Window.setBackground(Color.BLACK);
			Window.setLocation((int)(25 * Controller.scale),(int)(25 * Controller.scale));
			Window.setLayout(null);
			
			JTextArea AT = new JTextArea(); //About Text
			AT.setFont(Controller.font);
			AT.setSize((int)(645 * Controller.scale),(int)(360 * Controller.scale));
			AT.setBorder(null);
			AT.setLocation((int)(5 * Controller.scale), (int)(5 * Controller.scale));
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
			
			System.out.println("Font : " + AT.getFont());
			
		}
		
		
		
		
		public JPanel getPanel() {
			return Panel;
		}
		
		
	
	
}
