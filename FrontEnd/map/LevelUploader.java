package map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Engine.Controller;

public class LevelUploader {

private Controller con;
	
private JPanel Panel;
	
private File LevelFile = new File("");

	public LevelUploader(Controller Con) {
		
		con = Con;
		
		Panel = new JPanel();
		Panel.setSize(720,480);
		Panel.setLocation(0, 0);
		Panel.setLayout(null);
		Panel.setOpaque(false);
		
		JPanel UploadWindow = new JPanel();
		UploadWindow.setSize(new Dimension(655,370));
		UploadWindow.setBackground(Color.LIGHT_GRAY);
		UploadWindow.setLocation(25,25);
		UploadWindow.setLayout(null);
		
		//Level selection
		JTextField LevelPathText = new JTextField();
		LevelPathText.setText("Level :");
		LevelPathText.setSize(125,25);
		LevelPathText.setLocation(50,25);
		LevelPathText.setEditable(false);
		LevelPathText.setBackground(null);
		LevelPathText.setBorder(null);
		
		JTextField LevelPath = new JTextField();
		LevelPath.setSize(500,25);
		LevelPath.setLocation(50,50);
		
		JTextField AssetPathText = new JTextField();
		AssetPathText.setText("Assets :(Documents/ManicMiners/Levels/ASSETS/Sounds/MapName) (Music/Sound .ogg) ");
		AssetPathText.setSize(550,25);
		AssetPathText.setLocation(50,75);
		AssetPathText.setEditable(false);
		AssetPathText.setBackground(null);
		AssetPathText.setBorder(null);
		
		JTextField AssetsPath = new JTextField();
		AssetsPath.setSize(500,25);
		AssetsPath.setLocation(50,100);
		
		JButton browseLevel = new JButton();
		browseLevel.setSize(25,25);
		browseLevel.setText("...");
		browseLevel.setLocation(550,50);
		
		JButton browseAssets = new JButton();
		browseAssets.setSize(25,25);
		browseAssets.setText("...");
		browseAssets.setLocation(550,100);
		
		JTextField AuthorText = new JTextField();
		AuthorText.setText("Author :");
		AuthorText.setSize(125,25);
		AuthorText.setLocation(50,125);
		AuthorText.setEditable(false);
		AuthorText.setBackground(null);
		AuthorText.setBorder(null);
		
		String[] conf = con.fiMa.getConfig();
		String author = conf[0];
		
		JTextField Author = new JTextField();
		Author.setSize(125,25);
		Author.setLocation(50,150);
		Author.setText(author);
		Author.setEditable(false);
		Author.setBackground(null);
		
		JTextField LevelTypeText = new JTextField();
		LevelTypeText.setSize(125,25);
		LevelTypeText.setLocation(50,200);
		LevelTypeText.setEditable(false);
		LevelTypeText.setBackground(null);
		LevelTypeText.setBorder(null);
		LevelTypeText.setText("LevelType :");
		
		JComboBox<String> LevelTypeBox = new JComboBox<String>();
		LevelTypeBox.setSize(125,25);
		LevelTypeBox.setLocation(50,225);
		LevelTypeBox.setEditable(false);
		LevelTypeBox.addItem("Gimmick");
		LevelTypeBox.addItem("Puzzle");
		LevelTypeBox.addItem("Casual");
		LevelTypeBox.addItem("No Level");
		LevelTypeBox.addItem("Grind");
		LevelTypeBox.addItem("Panic");
		
		JComboBox<String> LevelTypeBox2 = new JComboBox<String>();
		LevelTypeBox2.setSize(125,25);
		LevelTypeBox2.setLocation(50,250);
		LevelTypeBox2.setEditable(false);
		LevelTypeBox2.addItem("");
		LevelTypeBox2.addItem("Gimmick");
		LevelTypeBox2.addItem("Puzzle");
		LevelTypeBox2.addItem("Casual");
		LevelTypeBox2.addItem("No Level");
		LevelTypeBox2.addItem("Grind");
		LevelTypeBox2.addItem("Panic");
		
		JTextField LengthText = new JTextField();
		LengthText.setSize(125,25);
		LengthText.setLocation(50,275);
		LengthText.setEditable(false);
		LengthText.setBackground(null);
		LengthText.setBorder(null);
		LengthText.setText("Length :");
		
		JComboBox<String> LengthBox = new JComboBox<String>();
		LengthBox.setSize(125,25);
		LengthBox.setLocation(50,300);
		LengthBox.setEditable(false);
		LengthBox.addItem("Short");
		LengthBox.addItem("Medium");
		LengthBox.addItem("Long");
		LengthBox.addItem("Infinite");
		
		JTextField AditionalInfoText = new JTextField();
		AditionalInfoText.setText("Extra Info :");
		AditionalInfoText.setSize(200,25);
		AditionalInfoText.setLocation(200,125);
		AditionalInfoText.setEditable(false);
		AditionalInfoText.setBackground(null);
		AditionalInfoText.setBorder(null);
		
		JTextArea Aditionalinfo = new JTextArea();
		//Aditionalinfo.setSize(350,150);
		//Aditionalinfo.setLocation(200,150);
		Aditionalinfo.setLineWrap(true);
		Aditionalinfo.setWrapStyleWord(true);
		
		JScrollPane aditionalInfoContainer = new JScrollPane(Aditionalinfo);
		aditionalInfoContainer.setBounds(200, 150, 350, 150);
		
		
		browseLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser pathCh = new JFileChooser(con.fiMa.MMFolderLoc + "\\Levels" );
				pathCh.setFileFilter(new FileNameExtensionFilter("Manic Miners Map File", "dat"));
				
				int returnVal = pathCh.showOpenDialog(null);
				
				if(returnVal == JFileChooser.APPROVE_OPTION)
		        {
					LevelPath.setText(pathCh.getSelectedFile().getAbsolutePath());
					
					LevelFile = pathCh.getSelectedFile();
					
					String[] MapInfo = con.fiMa.getMapInfo(LevelFile);
					
					Author.setText(con.fiMa.getConfig()[0]);
					LevelTypeBox.setSelectedItem(MapInfo[1]);
					LevelTypeBox2.setSelectedItem(MapInfo[2]);
					LengthBox.setSelectedItem(MapInfo[3]);
					Aditionalinfo.setText(MapInfo[4]);
					AssetsPath.setText(MapInfo[5]);
		        }
				
			}
			
		});
		
		browseAssets.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser pathCh = new JFileChooser(System.getProperty("user.home") + "\\Documents\\ManicMiners\\Levels" );
				pathCh.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int returnVal = pathCh.showOpenDialog(null);
				
				if(returnVal == JFileChooser.APPROVE_OPTION)
		        {
					AssetsPath.setText(pathCh.getSelectedFile().getAbsolutePath());
					//TODO
		        }
				
			}
			
		});
		
		JButton Upload = new JButton();
		Upload.setSize(125,25);
		Upload.setLocation(500,325);
		Upload.setText("Upload");
		Upload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Write info to file !
				//If there are assets add them to the Level
				
				/*
				String Author = "";
				String LevelType1 = "";
				String LevelType2 = "";
				String Length = "";
				String AssetPath = "";
				String Info = "";
				*/
				
				if(LevelPath.getText().length() > 5) {
					con.fiMa.addInfoToFile(LevelFile,new String[] {	Author.getText(),LevelTypeBox.getSelectedItem().toString(),
							LevelTypeBox2.getSelectedItem().toString(),
							LengthBox.getSelectedItem().toString(),
							Aditionalinfo.getText(),AssetsPath.getText()});

					
					con.com.addPacket("RequestMapUpload");
					con.com.writeFile(new File[] {LevelFile},"Map");
					
					if(AssetsPath.getText().length() > 5) {
					
					File[] assets = new File(AssetsPath.getText()).listFiles();
					
					con.com.addPacket("RequestAssetsUpload");
					con.com.writeFile(assets,"MapAssets");
					
					}
					
					LevelPath.setText("");
					AssetsPath.setText("");
					Aditionalinfo.setText("");
					//TODO open window (Finisht uploading) cough cough
				}
				
			}
			
		});
		
		UploadWindow.add(LevelPathText);
		UploadWindow.add(LevelPath);
		UploadWindow.add(AssetPathText);
		UploadWindow.add(AssetsPath);
		UploadWindow.add(browseLevel);
		UploadWindow.add(browseAssets);
		UploadWindow.add(AuthorText);
		UploadWindow.add(Author);
		UploadWindow.add(LevelTypeText);
		UploadWindow.add(LevelTypeBox);
		UploadWindow.add(LevelTypeBox2);
		UploadWindow.add(LengthText);
		UploadWindow.add(LengthBox);
		UploadWindow.add(AditionalInfoText);
		UploadWindow.add(aditionalInfoContainer);
		UploadWindow.add(Upload);
		
		Panel.add(UploadWindow);
		
		Panel.setVisible(true);
		
	}
	
	
	
	
	public JPanel getPanel() {
		return Panel;
	}

}







/*
JFileChooser pathCh = new JFileChooser();
pathCh.setMultiSelectionEnabled(true);

int returnVal = pathCh.showOpenDialog(null);

if(returnVal == JFileChooser.APPROVE_OPTION)
{
	File[] files = pathCh.getSelectedFiles();
	
	for(File f : files) {
		System.out.println("" + f.getAbsolutePath());
	}
	
	//LevelPath.setText(pathCh.getSelectedFile().getAbsolutePath());
}
*/