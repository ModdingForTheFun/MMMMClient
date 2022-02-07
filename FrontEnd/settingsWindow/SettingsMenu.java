package settingsWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import Engine.Controller;

public class SettingsMenu {


private Controller con;
	

private JPanel Panel;
	
private String SautoConnect;
	
	public SettingsMenu(Controller Con) {
	
		con = Con;
		
		Panel = new JPanel();
		Panel.setSize(720,480);
		Panel.setLocation(0, 0);
		Panel.setLayout(null);
		Panel.setOpaque(false);
		
		String[] settings = con.fiMa.getConfig();
		
		JPanel SettingsWindow = new JPanel();
		SettingsWindow.setSize(new Dimension(655,370));
		SettingsWindow.setBackground(Color.LIGHT_GRAY);
		SettingsWindow.setLocation(25,25);
		SettingsWindow.setLayout(null);
		
		
		// Coduments folder IF someone has a wierd documents folder
		
				JTextField DocuFolderText = new JTextField();
				DocuFolderText.setSize(425,25);
				DocuFolderText.setLocation(125,50);
				DocuFolderText.setEditable(false);
				DocuFolderText.setBackground(null);
				DocuFolderText.setBorder(null);
				DocuFolderText.setText("ManicMiners Folder : (in Documents , not the game)");
				
				JTextField DocuLocation = new JTextField();
				DocuLocation.setSize(425,25);
				DocuLocation.setLocation(125,75);
				DocuLocation.setText(settings[3]);
				
				
				JButton DocuLocSelection = new JButton();
				DocuLocSelection.setSize(25,25);
				DocuLocSelection.setLocation(100,75);
				DocuLocSelection.setIcon(UIManager.getIcon("FileView.directoryIcon"));
				DocuLocSelection.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser location = new JFileChooser(DocuLocation.getText());
						
						location.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						
						int returnVal = location.showOpenDialog(null);
						
						if(returnVal == JFileChooser.APPROVE_OPTION)
				        {
							String loc = location.getSelectedFile().getAbsolutePath();
							
							if(loc.contains("ManicMiners")) {
								DocuLocation.setText(location.getSelectedFile().getAbsolutePath());
							}
							
				        }
					}
					
				});
		
		
		// user name
		
		JTextField UserNameText = new JTextField();
		UserNameText.setSize(125,25);
		UserNameText.setLocation(125,125);
		UserNameText.setEditable(false);
		UserNameText.setBackground(null);
		UserNameText.setBorder(null);
		UserNameText.setText("Username : ");
		
		JTextField UserName = new JTextField();
		UserName.setSize(125,25);
		UserName.setLocation(125,155);
		UserName.setText(settings[0]);
		
		
		// Game location
		
		JTextField GameLocationText = new JTextField();
		GameLocationText.setSize(350,25);
		GameLocationText.setLocation(125,200);
		GameLocationText.setEditable(false);
		GameLocationText.setBackground(null);
		GameLocationText.setBorder(null);
		GameLocationText.setText("GameLocation : (ManicMiners.exe)");
		
		JTextField GameLocation = new JTextField();
		GameLocation.setSize(425,25);
		GameLocation.setLocation(125,235);
		GameLocation.setText(settings[1]);
		
		
		JButton locSelection = new JButton();
		locSelection.setSize(25,25);
		locSelection.setLocation(100,235);
		locSelection.setIcon(UIManager.getIcon("FileView.directoryIcon"));
		locSelection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser location = new JFileChooser(GameLocation.getText());
				
				location.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				int returnVal = location.showOpenDialog(null);
				
				if(returnVal == JFileChooser.APPROVE_OPTION)
		        {
					
					String loc = location.getSelectedFile().getAbsolutePath();
					
					if(loc.contains("ManicMiners.exe")) {
						GameLocation.setText(location.getSelectedFile().getAbsolutePath());
					}
					
		        }
			}
			
		});
		
		
		
		// Auto connect
		SautoConnect = "false";
		SautoConnect = "" + settings[2];
		
		JCheckBox autoConnect = new JCheckBox();
		autoConnect.setSize(225,25);
		autoConnect.setText("Automaticly connect to Server");
		autoConnect.setLocation(100,265);
		autoConnect.setBackground(null);
		if(SautoConnect.equals("true")) {
			autoConnect.setSelected(true);
		}else {
			autoConnect.setSelected(false);
		}
		autoConnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!autoConnect.isSelected()) {
					SautoConnect = "false";
				}else {
					SautoConnect = "true";
				}
			}
			
		});
		
		// save settings
		
		
		JButton Confirm = new JButton();
		Confirm.setSize(125,25);
		Confirm.setLocation(500,325);
		Confirm.setText("Save");
		Confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(UserName.getText().length() > 2) {
					
					if(!con.fiMa.getConfig()[0].equals(UserName.getText())) {
						con.fiMa.resetUserKey();
						con.com.disconnect();
					}
					
					con.mainWin.warWin.DisplayWarning(new String[]{"Warning !","Dont change your username later.","You will lose acces to update your maps.","Ok"});
					con.fiMa.writeToConfig(new String[] {UserName.getText(),GameLocation.getText(),SautoConnect,DocuLocation.getText()});
				}else {
					con.mainWin.warWin.DisplayWarning(new String[]{"Faulty Username","Your Username is to Short.","Please use a Longer Username","Ok"});
				}
				
				
			}
			
		});
		
		SettingsWindow.add(DocuFolderText);
		SettingsWindow.add(DocuLocation);
		SettingsWindow.add(DocuLocSelection);
		
		SettingsWindow.add(UserNameText);
		SettingsWindow.add(UserName);
		
		SettingsWindow.add(GameLocationText);
		SettingsWindow.add(GameLocation);
		SettingsWindow.add(locSelection);
		
		SettingsWindow.add(autoConnect);
		
		SettingsWindow.add(Confirm);
		
		Panel.add(SettingsWindow);
		
		Panel.setVisible(true);
		
	}
	
	
	
	
	public JPanel getPanel() {
		return Panel;
	}
	
	
	
}
