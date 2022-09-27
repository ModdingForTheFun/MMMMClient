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
		Panel.setSize((int)(720 * Controller.scale),(int)(480 * Controller.scale));
		Panel.setLocation(0, 0);
		Panel.setLayout(null);
		Panel.setOpaque(false);
		
		JPanel UploadWindow = new JPanel();
		UploadWindow.setSize(new Dimension((int)(655 * Controller.scale),(int)(370 * Controller.scale)));
		UploadWindow.setBackground(Color.LIGHT_GRAY);
		UploadWindow.setLocation((int)(25 * Controller.scale),(int)(25 * Controller.scale));
		UploadWindow.setLayout(null);
		
		//Level selection
		JTextField LevelPathText = new JTextField();
		LevelPathText.setFont(Controller.font);
		LevelPathText.setText("Level :");
		LevelPathText.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		LevelPathText.setLocation((int)(50 * Controller.scale),(int)(25 * Controller.scale));
		LevelPathText.setEditable(false);
		LevelPathText.setBackground(null);
		LevelPathText.setBorder(null);
		
		JTextField LevelPath = new JTextField();
		LevelPath.setFont(Controller.font);
		LevelPath.setSize((int)(500 * Controller.scale),(int)(25 * Controller.scale));
		LevelPath.setLocation((int)(50 * Controller.scale),(int)(50 * Controller.scale));
		
		JTextField AssetPathText = new JTextField();
		AssetPathText.setFont(Controller.font);
		AssetPathText.setText("Assets :(Documents/ManicMiners/Levels/ASSETS/Sounds/MapName) (Music/Sound .ogg) ");
		AssetPathText.setSize((int)(550 * Controller.scale),(int)(25 * Controller.scale));
		AssetPathText.setLocation((int)(50 * Controller.scale),(int)(75 * Controller.scale));
		AssetPathText.setEditable(false);
		AssetPathText.setBackground(null);
		AssetPathText.setBorder(null);
		
		JTextField AssetsPath = new JTextField();
		AssetsPath.setFont(Controller.font);
		AssetsPath.setSize((int)(500 * Controller.scale),(int)(25 * Controller.scale));
		AssetsPath.setLocation((int)(50 * Controller.scale),(int)(100 * Controller.scale));
		
		JButton browseLevel = new JButton();
		browseLevel.setFont(Controller.font);
		browseLevel.setSize((int)(25 * Controller.scale),(int)(25 * Controller.scale));
		browseLevel.setText("...");
		browseLevel.setLocation((int)(550 * Controller.scale),(int)(50 * Controller.scale));
		
		JButton browseAssets = new JButton();
		browseAssets.setFont(Controller.font);
		browseAssets.setSize((int)(25 * Controller.scale),(int)(25 * Controller.scale));
		browseAssets.setText("...");
		browseAssets.setLocation((int)(550 * Controller.scale),(int)(100 * Controller.scale));
		
		JTextField AuthorText = new JTextField();
		AuthorText.setFont(Controller.font);
		AuthorText.setText("Author :");
		AuthorText.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		AuthorText.setLocation((int)(50 * Controller.scale),(int)(125 * Controller.scale));
		AuthorText.setEditable(false);
		AuthorText.setBackground(null);
		AuthorText.setBorder(null);
		
		String[] conf = con.fiMa.getConfig();
		String author = conf[0];
		
		JTextField Author = new JTextField();
		Author.setFont(Controller.font);
		Author.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		Author.setLocation((int)(50 * Controller.scale),(int)(150 * Controller.scale));
		Author.setText(author);
		Author.setEditable(false);
		Author.setBackground(null);
		
		JTextField LevelTypeText = new JTextField();
		LevelTypeText.setFont(Controller.font);
		LevelTypeText.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		LevelTypeText.setLocation((int)(50 * Controller.scale),(int)(200 * Controller.scale));
		LevelTypeText.setEditable(false);
		LevelTypeText.setBackground(null);
		LevelTypeText.setBorder(null);
		LevelTypeText.setText("LevelType :");
		
		JComboBox<String> LevelTypeBox = new JComboBox<String>();
		LevelTypeBox.setFont(Controller.font);
		LevelTypeBox.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		LevelTypeBox.setLocation((int)(50 * Controller.scale),(int)(225 * Controller.scale));
		LevelTypeBox.setEditable(false);
		LevelTypeBox.addItem("Gimmick");
		LevelTypeBox.addItem("Puzzle");
		LevelTypeBox.addItem("Casual");
		LevelTypeBox.addItem("No Level");
		LevelTypeBox.addItem("Grind");
		LevelTypeBox.addItem("Panic");
		
		JComboBox<String> LevelTypeBox2 = new JComboBox<String>();
		LevelTypeBox2.setFont(Controller.font);
		LevelTypeBox2.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		LevelTypeBox2.setLocation((int)(50 * Controller.scale),(int)(250 * Controller.scale));
		LevelTypeBox2.setEditable(false);
		LevelTypeBox2.addItem("");
		LevelTypeBox2.addItem("Gimmick");
		LevelTypeBox2.addItem("Puzzle");
		LevelTypeBox2.addItem("Casual");
		LevelTypeBox2.addItem("No Level");
		LevelTypeBox2.addItem("Grind");
		LevelTypeBox2.addItem("Panic");
		
		JTextField LengthText = new JTextField();
		LengthText.setFont(Controller.font);
		LengthText.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		LengthText.setLocation((int)(50 * Controller.scale),(int)(275 * Controller.scale));
		LengthText.setEditable(false);
		LengthText.setBackground(null);
		LengthText.setBorder(null);
		LengthText.setText("Length :");
		
		JComboBox<String> LengthBox = new JComboBox<String>();
		LengthBox.setFont(Controller.font);
		LengthBox.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		LengthBox.setLocation((int)(50 * Controller.scale),(int)(300 * Controller.scale));
		LengthBox.setEditable(false);
		LengthBox.addItem("Short");
		LengthBox.addItem("Medium");
		LengthBox.addItem("Long");
		LengthBox.addItem("Infinite");
		
		JTextField AditionalInfoText = new JTextField();
		AditionalInfoText.setFont(Controller.font);
		AditionalInfoText.setText("Extra Info :");
		AditionalInfoText.setSize((int)(200 * Controller.scale),(int)(25 * Controller.scale));
		AditionalInfoText.setLocation((int)(200 * Controller.scale),(int)(125 * Controller.scale));
		AditionalInfoText.setEditable(false);
		AditionalInfoText.setBackground(null);
		AditionalInfoText.setBorder(null);
		
		JTextArea Aditionalinfo = new JTextArea();
		Aditionalinfo.setFont(Controller.font);
		//Aditionalinfo.setSize(350,150);
		//Aditionalinfo.setLocation(200,150);
		Aditionalinfo.setLineWrap(true);
		Aditionalinfo.setWrapStyleWord(true);
		
		JScrollPane aditionalInfoContainer = new JScrollPane(Aditionalinfo);
		aditionalInfoContainer.setBounds((int)(200 * Controller.scale), (int)(150 * Controller.scale), (int)(350 * Controller.scale), (int)(150 * Controller.scale));
		
		
		browseLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser pathCh = new JFileChooser(con.fiMa.MMFolderLoc + "/Levels" );
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

				JFileChooser pathCh = new JFileChooser(System.getProperty("user.home") + "/Documents/ManicMiners/Levels" );
				pathCh.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int returnVal = pathCh.showOpenDialog(null);
				
				if(returnVal == JFileChooser.APPROVE_OPTION)
		        {
					AssetsPath.setText(pathCh.getSelectedFile().getAbsolutePath());
		        }
				
			}
			
		});
		
		JButton Upload = new JButton();
		Upload.setFont(Controller.font);
		Upload.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		Upload.setLocation((int)(500 * Controller.scale),(int)(325 * Controller.scale));
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