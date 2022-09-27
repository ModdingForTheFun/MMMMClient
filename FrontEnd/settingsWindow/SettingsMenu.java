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
		Panel.setSize((int)(720 * Controller.scale),(int)(480 * Controller.scale));
		Panel.setLocation(0, 0);
		Panel.setLayout(null);
		Panel.setOpaque(false);
		
		String[] settings = con.fiMa.getConfig();
		
		JPanel SettingsWindow = new JPanel();
		SettingsWindow.setSize(new Dimension((int)(655 * Controller.scale),(int)(370 * Controller.scale)));
		SettingsWindow.setBackground(Color.LIGHT_GRAY);
		SettingsWindow.setLocation((int)(25 * Controller.scale),(int)(25 * Controller.scale));
		SettingsWindow.setLayout(null);
		
		
		// Coduments folder IF someone has a wierd documents folder
		
				JTextField DocuFolderText = new JTextField();
				DocuFolderText.setFont(Controller.font);
				DocuFolderText.setSize((int)(425 * Controller.scale),(int)(25 * Controller.scale));
				DocuFolderText.setLocation((int)(125 * Controller.scale),(int)(50 * Controller.scale));
				DocuFolderText.setEditable(false);
				DocuFolderText.setBackground(null);
				DocuFolderText.setBorder(null);
				DocuFolderText.setText("ManicMiners Folder : (in Documents , not the game)");
				
				JTextField DocuLocation = new JTextField();
				DocuLocation.setFont(Controller.font);
				DocuLocation.setSize((int)(425 * Controller.scale),(int)(25 * Controller.scale));
				DocuLocation.setLocation((int)(125 * Controller.scale),(int)(75 * Controller.scale));
				DocuLocation.setText(settings[3]);
				
				
				JButton DocuLocSelection = new JButton();
				DocuLocSelection.setFont(Controller.font);
				DocuLocSelection.setSize((int)(25 * Controller.scale),(int)(25 * Controller.scale));
				DocuLocSelection.setLocation((int)(100 * Controller.scale),(int)(75 * Controller.scale));
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
		UserNameText.setFont(Controller.font);
		UserNameText.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		UserNameText.setLocation((int)(125 * Controller.scale),(int)(125 * Controller.scale));
		UserNameText.setEditable(false);
		UserNameText.setBackground(null);
		UserNameText.setBorder(null);
		UserNameText.setText("Username : ");
		
		JTextField UserName = new JTextField();
		UserName.setFont(Controller.font);
		UserName.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		UserName.setLocation((int)(125 * Controller.scale),(int)(155 * Controller.scale));
		UserName.setText(settings[0]);
		
		
		// Game location
		
		JTextField GameLocationText = new JTextField();
		GameLocationText.setFont(Controller.font);
		GameLocationText.setSize((int)(350 * Controller.scale),(int)(25 * Controller.scale));
		GameLocationText.setLocation((int)(125 * Controller.scale),(int)(200 * Controller.scale));
		GameLocationText.setEditable(false);
		GameLocationText.setBackground(null);
		GameLocationText.setBorder(null);
		GameLocationText.setText("GameLocation : (ManicMiners.exe)");
		
		JTextField GameLocation = new JTextField();
		GameLocation.setFont(Controller.font);
		GameLocation.setSize((int)(425 * Controller.scale),(int)(25 * Controller.scale));
		GameLocation.setLocation((int)(125 * Controller.scale),(int)(235 * Controller.scale));
		GameLocation.setText(settings[1]);
		
		
		JButton locSelection = new JButton();
		locSelection.setFont(Controller.font);
		locSelection.setSize((int)(25 * Controller.scale),(int)(25 * Controller.scale));
		locSelection.setLocation((int)(100 * Controller.scale),(int)(235 * Controller.scale));
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
		autoConnect.setSize((int)(225 * Controller.scale),(int)(25 * Controller.scale));
		autoConnect.setFont(Controller.font);
		autoConnect.setText("Automaticly connect to Server");
		autoConnect.setLocation((int)(100 * Controller.scale),(int)(265 * Controller.scale));
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
		
		
		
		// window scale
		
		
		JTextField winScaleText = new JTextField();
		winScaleText.setFont(Controller.font);
		winScaleText.setSize((int)(350 * Controller.scale),(int)(25 * Controller.scale));
		winScaleText.setLocation((int)(125 * Controller.scale),(int)(300 * Controller.scale));
		winScaleText.setEditable(false);
		winScaleText.setBackground(null);
		winScaleText.setBorder(null);
		winScaleText.setText("Window Scale, Restart after (Use . for comma numbers)");
		
		JTextField winScale = new JTextField();
		winScale.setFont(Controller.font);
		winScale.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		winScale.setLocation((int)(125 * Controller.scale),(int)(330 * Controller.scale));
		winScale.setText(settings[4]);
		
		
		// save settings
		
		
		JButton Confirm = new JButton();
		Confirm.setFont(Controller.font);
		Confirm.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		Confirm.setLocation((int)(500 * Controller.scale),(int)(325 * Controller.scale));
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
					con.fiMa.writeToConfig(new String[] {UserName.getText(),GameLocation.getText(),SautoConnect,DocuLocation.getText(),winScale.getText()});
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
		
		SettingsWindow.add(winScaleText);
		SettingsWindow.add(winScale);
		
		SettingsWindow.add(Confirm);
		
		Panel.add(SettingsWindow);
		
		Panel.setVisible(true);
		
	}
	
	
	
	
	public JPanel getPanel() {
		return Panel;
	}
	
	
	
}
