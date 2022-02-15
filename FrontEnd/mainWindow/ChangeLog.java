package mainWindow;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Engine.Controller;

public class ChangeLog {

	private Controller con;
	
	private JPanel Panel;
	
		public ChangeLog(Controller Con) {
		
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
			
			JTextArea CLT = new JTextArea(); //About Text
			CLT.setSize(645,360);
			CLT.setBorder(null);
			CLT.setLocation(5, 5);
			CLT.setEditable(false);
			CLT.setText("			---Change Log--- \n"
					+ "\n"
					+ " - Patch 0.7_5 - \n"
					+ "\n"
					+ " - Level Uploader lets you see your whole info text now.\n"
					+ " - Map Browser lets you see the whole info text now AND is resizeable \n"
					+ " - Donwloading files with sound assets now correctly stores the sounds \n"
					+ "\n"
					+ "\n"
					+ "	- Patch 0.7_4 - \n"
					+ "\n"
					+ " - Hopefully fixt bug with changing from old to new Folder System,\n"
					+ "   that caused people to lose acces to there Username. \n"
					+ " - Deleting a Map also deletes the Map Assets of it now. \n"
					+ " - Fixt a Problem that made downloading Map Assets impossible. \n"
					+ " - Added Change Log. \n"
					+ "\n"
					+ "");
			
			Window.add(CLT);
			
			Panel.add(Window);
			
			Panel.setVisible(true);
			
		}
		
		
		
		
		public JPanel getPanel() {
			return Panel;
		}
	
	
	
	
}
