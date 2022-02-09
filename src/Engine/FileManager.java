package Engine;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import FrontEnd.AssetLoader;


public class FileManager {

private Controller con;

private AssetLoader asLo;


public String MMFolderLoc;	


	public FileManager(Controller Con,AssetLoader AsLo) {
		con = Con;
		asLo = AsLo;
	}
	
	
	//ModManagerFolder
	
	public void deleteModManagerFolder() { //TODO
		
		File configFolder = new File(MMFolderLoc +  "\\ModManager");
		
		if(configFolder.exists()) {
			delete(configFolder);
		}
		
		File tempFolder = new File(MMFolderLoc +  "\\tempFiles");
		
		if(tempFolder.exists()) {
			delete(tempFolder);
		}
		
	}
	
	
	
	
	//config file
	
	private boolean firstBoot = true;
	
	public String[] getConfig(){
		
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		
		String MMMMdir = jarDir.getAbsolutePath().toString();
		
		
		String[] configData = new String[4];
		
		
		
		if(firstBoot) {
			
			System.out.println("Find Documents folder test : " + FileSystemView.getFileSystemView().getDefaultDirectory().getPath() );
			
			
			// Well some code that creates the folders neccesary because some people dont have them for some reason . . .
			
			File MMDocFolder = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners\\Levels");
			if(!MMDocFolder.exists()) {
				MMDocFolder.mkdirs();
			}
			
			
			
			if(!new File(MMMMdir + "\\ModManager").exists()) {
				new File(MMMMdir + "\\ModManager").mkdirs();
			}
			
			//move key if needed
			
			if(new File(System.getProperty("user.home") + "\\Documents\\ManicMiners" + "\\ModManager\\MMMM.key").exists()) {
				try {
					Files.move(Paths.get(System.getProperty("user.home") + "\\Documents\\ManicMiners" + "\\ModManager\\MMMM.key"), 
							Paths.get(MMMMdir + "\\ModManager\\MMMM.key"));
					delete(new File(System.getProperty("user.home") + "\\Documents\\ManicMiners" + "\\ModManager\\MMMM.key"));
				} catch (IOException e) {
					System.out.println("Couldnt move key file !");
					e.printStackTrace();
				}
			}
			
			//move config
			
			if(new File(System.getProperty("user.home") + "\\Documents\\ManicMiners" + "\\ModManager\\MMMM.cfg").exists()) {
				try {
					Files.move(Paths.get(System.getProperty("user.home") + "\\Documents\\ManicMiners" + "\\ModManager\\MMMM.cfg"), 
							Paths.get(MMMMdir + "\\ModManager\\MMMM.cfg"));
					delete(new File(System.getProperty("user.home") + "\\Documents\\ManicMiners" + "\\ModManager\\MMMM.cfg"));
				} catch (IOException e) {
					System.out.println("Couldnt move cfg file !");
					e.printStackTrace();
				}
			}
		
			//move key if needed new method !
			
			if(new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners" + "\\ModManager\\MMMM.key").exists()) {
				try {
					Files.move(Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners" + "\\ModManager\\MMMM.key"), 
							Paths.get(MMMMdir + "\\ModManager\\MMMM.key"));
					delete(new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners" + "\\ModManager\\MMMM.key"));
				} catch (IOException e) {
					System.out.println("Couldnt move key file !");
					e.printStackTrace();
				}
			}
			
			//move config
			
			if(new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners" + "\\ModManager\\MMMM.cfg").exists()) {
				try {
					Files.move(Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners" + "\\ModManager\\MMMM.cfg"), 
							Paths.get(MMMMdir + "\\ModManager\\MMMM.cfg"));
					delete(new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners" + "\\ModManager\\MMMM.cfg"));
				} catch (IOException e) {
					System.out.println("Couldnt move cfg file !");
					e.printStackTrace();
				}
			}
			
			firstBoot = false;
		}
		
		
		
		File configFolder = new File(MMMMdir + "\\ModManager");
		
		if(!configFolder.exists()) {
			configFolder.mkdirs();
		}
		
		File configFile = new File(MMMMdir + "\\ModManager\\MMMM.cfg");
		
		if(!configFile.exists()) {
			
			try {
				
				configFile.createNewFile();
				
				BufferedWriter BW = new BufferedWriter(new FileWriter(configFile));
				
				BW.write("");
				BW.newLine();
				BW.write("");
				BW.newLine();
				BW.write("false");
				BW.flush();
				BW.write("" + FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners");
				BW.flush();
				
				BW.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		try {
			
			
			FileReader FR = new FileReader(configFile);
			BufferedReader BR = new BufferedReader(FR);
			
			String UserName = BR.readLine();
			
			configData[0] = UserName;
			configData[1] = BR.readLine(); // game location
			configData[2] = BR.readLine(); // auto connect
			configData[3] = BR.readLine(); // MMfolder
			
			if(configData[3] == null || (!configData[3].contains("ManicMiners"))) {
				MMFolderLoc = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners";
				configData[3] = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\ManicMiners";
			}else
			{
				MMFolderLoc = configData[3];
			}
			
			BR.close();
			FR.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return configData;
		
	}
	
	
	public void writeToConfig(String what[]) {
		
		
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		
		String MMMMdir = jarDir.getAbsolutePath().toString();
		
		File configFolder = new File(MMMMdir + "\\ModManager");
		
		if(!configFolder.exists()) {
			configFolder.mkdir();
		}
		
		File configFile = new File(MMMMdir + "\\ModManager\\MMMM.cfg");
		
		if(!configFile.exists()) {
			
			try {
				
				configFile.createNewFile();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			
			
			FileWriter FW = new FileWriter(configFile);
			BufferedWriter BW = new BufferedWriter(FW);
			
			for(String s : what) {
				BW.write(s);
				BW.newLine();
			}
			
			
			BW.flush();
			
			BW.close();
			FW.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	
	
	// User Key
	
	public void resetUserKey() {
		
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		
		String MMMMdir = jarDir.getAbsolutePath().toString();
		
		File keyFile = new File(MMMMdir + "\\ModManager\\MMMM.key");
		
		keyFile.delete();
		
	}
	
	public String getUserKey() throws IOException {
		
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		
		String MMMMdir = jarDir.getAbsolutePath().toString();
		
		File keyFile = new File(MMMMdir + "\\ModManager\\MMMM.key");
		
		if(!keyFile.exists()) {
			
			try {
				
				keyFile.createNewFile();
				
				FileWriter FW = new FileWriter(keyFile);
				BufferedWriter BW = new BufferedWriter(FW);
				
				String Key = "";
				
				Key += getConfig()[0].hashCode();
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();  
				
				Key += dtf.format(now).hashCode();
				
				BW.write(Key);
				BW.flush();
				
				BW.close();
				
				return Key;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			
			BufferedReader BR = new BufferedReader(new FileReader(keyFile));
			
			String Key = BR.readLine();
			
			BR.close();
			
			if(Key.length() < 5) {
				con.mainWin.warWin.DisplayWarning(new String[]{"Warning !","There seams to be a problem.","Did you edit your Key ?.","HUUUH ?"});
			
				throw new IOException("Dam short key problems");
			}
			
			return Key;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		throw new IOException("Dam key problems");
	}
	
	
	
	
	//Map files
	
	public String[][] getLevelData(){
		
		File levelFolder = new File(MMFolderLoc + "\\Levels");
		
		String[] levels = levelFolder.list();
		
		if(levels == null || levels.length < 1) { 
			return new String[][] {{"NoLevel!",".",".","."}};
		}
		
		List<String> LevelListList = Arrays.asList(levelFolder.list());
		
		LinkedList<String> LevelList = new LinkedList<String>();
		
		for(String item : LevelListList) {
			if(item.contains(".dat")) {
				LevelList.add(item);
			}
		}
		
		
		String[][] levelData = new String[LevelList.size()][4];		
		
		int count = 0;
		for(String LevelName : LevelList) {
			
			File LevelFile = new File(MMFolderLoc + "\\Levels\\" + LevelName);
			
			LinkedList<String> commentList = new LinkedList<String>();
			
			FileReader FR;
			
			try {
				
				FR = new FileReader(LevelFile);
				
				BufferedReader BR = new BufferedReader(FR);
				
				
				
				boolean commentEnd = false;
				
				while(!commentEnd) {
					
					try {
						
						String line = BR.readLine();
						
						commentList.add(line);
						
						if(line.contains("}")) {
							commentEnd = true;
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
				BR.close();
				FR.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String Author = "";
			String LevelType1 = "";
			String LevelType2 = "";
			String Length = "";
			int stringStart;
			
			for(String comment : commentList) {
				
				if(comment.contains("Author")) {
					
					stringStart = comment.indexOf(':') + 1;
					
					Author = comment.substring(stringStart,comment.length());
				}
				
				if(comment.contains("LevelType1")) {
					
					stringStart = comment.indexOf(':') + 1;
					
					LevelType1 = comment.substring(stringStart,comment.length());
				}
				
				if(comment.contains("LevelType2")) {	
					
					stringStart = comment.indexOf(':') + 1;
					
					LevelType2 = comment.substring(stringStart,comment.length());
				}
				
				if(comment.contains("Length")) {
					
					stringStart = comment.indexOf(':') + 1;
					
					Length = comment.substring(stringStart,comment.length());
				}
			}
			
			
			
			levelData[count][0] = LevelName.substring(0,LevelName.length() - 4);
			levelData[count][1] = Author;
			levelData[count][2] = LevelType1 + "," + LevelType2;
			levelData[count][3] = Length;
			
			count++;
		}
		
		
		
		return levelData;
		
	}
	
	
	
	public Boolean deleteMap(String mapName) {
		
		JFrame popUp = new JFrame();
		popUp.setSize(250,150);
		popUp.setLocationRelativeTo(null);
		popUp.setTitle("Warning !");
		if(con.com.isConnected()) {
			popUp.setIconImage(asLo.IconOn.getImage());
		}else {
			popUp.setIconImage(asLo.IconOff.getImage());
		}
		
		popUp.setLayout(null);
		
		JPanel content = new JPanel();
		content.setSize(300, 150);
		content.setLocation(0, 0);
		content.setLayout(null);
		
		JTextField ReallyText = new JTextField();
		ReallyText.setSize(125,25);
		ReallyText.setLocation(50,10);
		ReallyText.setEditable(false);
		ReallyText.setBackground(null);
		ReallyText.setBorder(null);
		ReallyText.setText("Delete Level : ");
		
		JTextField mapNameText = new JTextField();
		mapNameText.setSize(200,25);
		mapNameText.setLocation(50,40);
		mapNameText.setEditable(false);
		mapNameText.setBackground(null);
		mapNameText.setBorder(null);
		mapNameText.setText(mapName);
		
		JButton yes = new JButton();
		yes.setSize(125,25);
		yes.setLocation(50,75);
		yes.setText("Delet Map !");
		yes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				popUp.dispose();
				
				File LevelFile = new File(MMFolderLoc + "\\Levels\\" + mapName + ".dat");
				
				if(LevelFile.exists()) {
					System.out.println("Gelöscht");
				}else {
					System.out.println(MMFolderLoc + "\\Levels\\" + mapName + ".dat" + "_wurde nicht gefunden");
				}
				
				if(new File(MMFolderLoc + "\\Levels\\ASSETS\\Sounds\\" + mapName).exists()) {
					delete(new File(MMFolderLoc + "\\Levels\\ASSETS\\Sounds\\" + mapName));
				}
				
				delete(LevelFile);
				
				File assetsFolder = new File(MMFolderLoc + "\\Levels\\ASSETS\\Sounds\\" + mapName);
				
				if(assetsFolder.exists()) {
					delete(assetsFolder);
				}
			}
			
		});
		
		content.add(ReallyText);
		content.add(mapNameText);
		content.add(yes);
		
		popUp.setContentPane(content);
		popUp.setVisible(true);
		
		return true;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	public String[] getMapInfo(File map) {
		
		LinkedList<String> commentList = new LinkedList<String>();
		
		FileReader FR;
		
		try {
			
			FR = new FileReader(map);
			
			BufferedReader BR = new BufferedReader(FR);
			
			
			
			boolean commentEnd = false;
			
			while(!commentEnd) {
				
				try {
					
					String line = BR.readLine();
					
					if(line.contains("}")) {
						commentEnd = true;
					}else {
						commentList.add(line);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			BR.close();
			FR.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String Author = "";
		String LevelType1 = "";
		String LevelType2 = "";
		String Length = "";
		String AssetPath = "";
		String Info = "";
		int stringStart;
		
		boolean infoText = false;
		
		for(String comment : commentList) {
			
//			System.out.println(comment);
			
			if(comment.contains("Author")) {
				
				stringStart = comment.indexOf(':') + 1;
				
				Author = comment.substring(stringStart,comment.length());
			}
			
			if(comment.contains("LevelType1")) {
				
				stringStart = comment.indexOf(':') + 1;
				
				LevelType1 = comment.substring(stringStart,comment.length());
				
			}
			
			if(comment.contains("LevelType2")) {
				
				stringStart = comment.indexOf(':') + 1;
				
				LevelType2 = comment.substring(stringStart,comment.length());
				
			}
			
			if(comment.contains("Length")) {
				
				stringStart = comment.indexOf(':') + 1;
				
				Length = comment.substring(stringStart,comment.length());
			}
			
			if(infoText) {
				Info = Info + "\n" + comment;
			}
			
			if(comment.contains("Info")) {
				
				stringStart = comment.indexOf(':') + 1;
				
				Info = comment.substring(stringStart,comment.length());
				
				infoText = true;
			}
			
			if(comment.contains("Asset")) {
				
				stringStart = comment.indexOf(':') + 1;
				
				AssetPath = comment.substring(stringStart,comment.length());
				
			}
			
		}
		
		return new String[] {Author,LevelType1,LevelType2,Length,Info,AssetPath};
		
	}
	
	
	
	
	//-----------Texture
	
	HashMap<String, ImageIcon> loadedTextures;
	
	public HashMap<String, ImageIcon> getTextures(){
		
		return loadedTextures;
		
	}
	
	public HashMap<String,ImageIcon> getTempTextures(){
		
		HashMap<String,ImageIcon> LoadedTextures = new HashMap<String, ImageIcon>();
		
		File textureMainFolder = new File(MMFolderLoc + "\\tempFiles");
		
		if(!textureMainFolder.exists()) {
			textureMainFolder.mkdir();
		}
		
		File ArmFolder = new File(MMFolderLoc + "\\tempFiles\\Arm");
		File BeltFolder = new File(MMFolderLoc + "\\tempFiles\\Belt");
		File FaceFolder = new File(MMFolderLoc + "\\tempFiles\\Face");
		File LegLeftFolder = new File(MMFolderLoc + "\\tempFiles\\LegLeft");
		File LegRightFolder = new File(MMFolderLoc + "\\tempFiles\\LegRight");
		File TorsoFolder = new File(MMFolderLoc + "\\tempFiles\\Torso");
		
		if(!ArmFolder.exists()) {
			ArmFolder.mkdir();
		}
		
		if(!BeltFolder.exists()) {
			BeltFolder.mkdir();
		}
		
		if(!FaceFolder.exists()) {
			FaceFolder.mkdir();
		}
		
		if(!LegLeftFolder.exists()) {
			LegLeftFolder.mkdir();
		}
		
		if(!LegRightFolder.exists()) {
			LegRightFolder.mkdir();
		}
		
		if(!TorsoFolder.exists()) {
			TorsoFolder.mkdir();
		}
		
		String[] textureMainFolderList = textureMainFolder.list();
		
		Log(" FM ");
		Log("Folder List : ");
		
		for(String SubFolder : textureMainFolderList) {
			
			Log(" ");
			Log("	-" + SubFolder + "-");
			
			String[] folderItems = new File(MMFolderLoc + "\\tempFiles\\" + SubFolder).list();
			
			int counter = 0;
			
			Log("		Texture List : ");
			
			for(String texture : folderItems) {
				
				Log("		-" + texture + "-");
				
				if(texture.contains(".png")) {
					LoadedTextures.put(SubFolder + counter, new ImageIcon(MMFolderLoc + "\\tempFiles\\" + SubFolder + "\\" + texture));
				
					counter++;
				}
				
			}
			
		}
		
		return LoadedTextures;
		
	}
		
	private int counter = 0;
	
	public void loadTextures(){
		
		loadedTextures = new HashMap<String, ImageIcon>();
		
		File textureMainFolder = new File(MMFolderLoc + "\\Textures");
		
		if(!textureMainFolder.exists()) {
			textureMainFolder.mkdir();
		}
		
		File ArmFolder = new File(MMFolderLoc + "\\Textures\\Arm");
		File BeltFolder = new File(MMFolderLoc + "\\Textures\\Belt");
		File FaceFolder = new File(MMFolderLoc + "\\Textures\\Face");
		File LegLeftFolder = new File(MMFolderLoc + "\\Textures\\LegLeft");
		File LegRightFolder = new File(MMFolderLoc + "\\Textures\\LegRight");
		File TorsoFolder = new File(MMFolderLoc + "\\Textures\\Torso");
		
		if(!ArmFolder.exists()) {
			ArmFolder.mkdir();
		}
		
		if(!BeltFolder.exists()) {
			BeltFolder.mkdir();
		}
		
		if(!FaceFolder.exists()) {
			FaceFolder.mkdir();
		}
		
		if(!LegLeftFolder.exists()) {
			LegLeftFolder.mkdir();
		}
		
		if(!LegRightFolder.exists()) {
			LegRightFolder.mkdir();
		}
		
		if(!TorsoFolder.exists()) {
			TorsoFolder.mkdir();
		}
		
		String[] textureMainFolderList = textureMainFolder.list();
		
		Log(" FM ");
		Log("Folder List : ");
		
		for(String SubFolder : textureMainFolderList) {
			
			Log(" ");
			Log("	-" + SubFolder + "-");
			
			String[] folderItems = new File(MMFolderLoc + "\\Textures\\" + SubFolder).list();
			
			Log("		Texture List : ");
			
			for(String texture : folderItems) {
				
				Log("		-" + texture + "-");
				
				if(texture.contains(".png")) {
					loadedTextures.put(SubFolder + counter, new ImageIcon(MMFolderLoc + "\\Textures\\" + SubFolder + "\\" + texture));
				
					counter++;
				}
				
			}
			
		}
		
	}
	
	
	
	public void deleteTextures(LinkedList<String> textureList) {
		
		
		JFrame popUp = new JFrame();
		popUp.setSize(250,150);
		popUp.setLocationRelativeTo(null);
		popUp.setTitle("Warning !");
		if(con.com.isConnected()) {
			popUp.setIconImage(asLo.IconOn.getImage());
		}else {
			popUp.setIconImage(asLo.IconOff.getImage());
		}
		popUp.setLayout(null);
		
		JPanel content = new JPanel();
		content.setSize(300, 150);
		content.setLocation(0, 0);
		content.setLayout(null);
		
		JTextField ReallyText = new JTextField();
		ReallyText.setSize(125,25);
		ReallyText.setLocation(50,10);
		ReallyText.setEditable(false);
		ReallyText.setBackground(null);
		ReallyText.setBorder(null);
		ReallyText.setText("Do you wanna delete,");
		
		JTextField mapNameText = new JTextField();
		mapNameText.setSize(200,25);
		mapNameText.setLocation(50,40);
		mapNameText.setEditable(false);
		mapNameText.setBackground(null);
		mapNameText.setBorder(null);
		mapNameText.setText("selected Textures ?");
		
		JButton yes = new JButton();
		yes.setSize(125,25);
		yes.setLocation(50,75);
		yes.setText("Yes !");
		yes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				popUp.dispose();
				
				for(String texturePath : textureList) {
					
					File texture = new File(texturePath);
					
					delete(texture);
					
				}
			}
			
		});
		
		content.add(ReallyText);
		content.add(mapNameText);
		content.add(yes);
		
		loadTextures();
		
		popUp.setContentPane(content);
		popUp.setVisible(true);
		
	}
	
	
	
	
	public void installTextures(LinkedList<String> textureList) {
		
		
		JFrame popUp = new JFrame();
		popUp.setSize(250,150);
		popUp.setLocationRelativeTo(null);
		popUp.setTitle("Warning !");
		if(con.com.isConnected()) {
			popUp.setIconImage(asLo.IconOn.getImage());
		}else {
			popUp.setIconImage(asLo.IconOff.getImage());
		}
		popUp.setLayout(null);
		
		JPanel content = new JPanel();
		content.setSize(300, 150);
		content.setLocation(0, 0);
		content.setLayout(null);
		
		JTextField ReallyText = new JTextField();
		ReallyText.setSize(125,25);
		ReallyText.setLocation(50,10);
		ReallyText.setEditable(false);
		ReallyText.setBackground(null);
		ReallyText.setBorder(null);
		ReallyText.setText("Do you wanna install,");
		
		JTextField mapNameText = new JTextField();
		mapNameText.setSize(200,25);
		mapNameText.setLocation(50,40);
		mapNameText.setEditable(false);
		mapNameText.setBackground(null);
		mapNameText.setBorder(null);
		mapNameText.setText("selected Textures ?");
		
		JButton yes = new JButton();
		yes.setSize(125,25);
		yes.setLocation(50,75);
		yes.setText("Yes !");
		yes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				popUp.dispose();
				
				for(String texturePath : textureList) {
					
					File texture = new File(texturePath);
					
					install(texture);
					
				}
			}
			
		});
		
		content.add(ReallyText);
		content.add(mapNameText);
		content.add(yes);
		
		loadTextures();
		
		popUp.setContentPane(content);
		popUp.setVisible(true);
		
	}
	
	
	
	private void install(File file) {
		
		String filePath = file.getAbsolutePath();
		
		String subFolder = "Arm";
		
		if(filePath.contains("Arm")) {
			subFolder = "Arm";
		}
		
		if(filePath.contains("Belt")) {
			subFolder = "Belt";
		}
		
		if(filePath.contains("Face")) {
			subFolder = "Face";
		}
		
		if(filePath.contains("LegLeft")) {
			subFolder = "LegLeft";
		}
		
		if(filePath.contains("LegRight")) {
			subFolder = "LegRight";
		}
		
		if(filePath.contains("Torso")) {
			subFolder = "Torso";
		}
		
		String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
		
		System.out.println("FileName texture : " + fileName);
		
		File newFile = new File(MMFolderLoc + "\\Textures\\" + subFolder + "\\" + fileName);
		
		try {
			Files.copy(file.toPath(),newFile.toPath());
		
			loadedTextures.put(subFolder + counter, new ImageIcon(MMFolderLoc + "\\Textures\\" + subFolder + "\\" + fileName));
			
			counter++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	private void delete(File file) {
		
		Desktop.getDesktop().moveToTrash(file);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//Log
	
	private Boolean firstLog=true;
	
	public void Log(String toLog) {
		
		//File logFile = new File();
		
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		
		
		String dir = jarDir.getAbsolutePath().toString();
		
		//File LogFile = new File(dir + "\\MMMM.Log");
		
		File LogFile = new File("MMMM.Log");
		
		if(!dir.contains("Java")) {
			
			if(firstLog) {
				
				System.out.println(jarDir.getAbsolutePath());
				
				if(LogFile.exists()) {
					LogFile.delete();
				}
				
				try {
					
					LogFile.createNewFile();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				firstLog = false;
			}
			
			
			try {
				
				FileWriter FW = new FileWriter(LogFile,true);
				BufferedWriter BW = new BufferedWriter(FW);
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now(); 
				
				String LogString = dtf.format(now) + " : " + toLog;
				
				BW.write(LogString);
				BW.newLine();
				BW.flush();
				
				BW.close();
				FW.close();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}



	public void addInfoToFile(File levelFile, String[] levelData) {
		
		try {
			File newFile = new File(levelFile.getAbsoluteFile() + ".temp");
			newFile.createNewFile();
			
			FileReader FR = new FileReader(levelFile);
			BufferedReader BR = new BufferedReader(FR);
			
			FileWriter FW = new FileWriter(newFile);
			BufferedWriter BW = new BufferedWriter(FW);
			
			String Line = "";
			
			boolean commentEnd = false;
			
			boolean auth = false;
			boolean levT1 = false;
			boolean levT2 = false;
			boolean leng = false;
			boolean inf = false;
			boolean AsPa = false;
			
			while((Line = BR.readLine()) != null) {
				
				if(Line.contains("}") && !commentEnd) {
					
					System.out.println("line:" + Line);
					
					System.out.println("auth:" + auth);
					if(!auth) {
						auth = true;
						BW.write("Author:" + levelData[0]);
						BW.newLine();
						BW.flush();
					}
					
					if(!levT1) {
						levT1 = true;
						BW.write("LevelType1:" + levelData[1]);
						BW.newLine();
						BW.flush();
					}
					
					if(!levT2) {
						levT2 = true;
						BW.write("LevelType2:" + levelData[2]);
						BW.newLine();
						BW.flush();
					}
					
					if(!leng) {
						leng = true;
						BW.write("Length:" + levelData[3]);
						BW.newLine();
						BW.flush();
					}
					
					if(!AsPa) {
						AsPa = true;
						BW.write("Asset:" + levelData[5]);
						BW.newLine();
						BW.flush();
					}
					
					if(!inf) {
						inf = true;
						BW.write("Info:" + levelData[4]);
						BW.newLine();
						BW.flush();
					}
					
					if(Line.contains("}")) {
						BW.write(Line);
						BW.newLine();
						BW.flush();
					}
					
					commentEnd = true;
				}else
				
				if(commentEnd) {
					
					BW.write(Line);
					BW.newLine();
					BW.flush();
					
				}else {
					
					if(Line.contains("Author")) {
						auth = true;
						BW.write("Author:" + levelData[0]);
						BW.newLine();
						BW.flush();
					}else
					
					if(Line.contains("LevelType1")) {
						levT1 = true;
						BW.write("LevelType1:" + levelData[1]);
						BW.newLine();
						BW.flush();
					}else
					
					if(Line.contains("LevelType2")) {
						levT2 = true;
						BW.write("LevelType2:" + levelData[2]);
						BW.newLine();
						BW.flush();
					}else
						
					if(Line.contains("Length")) {
						leng = true;
						BW.write("Length:" + levelData[3]);
						BW.newLine();
						BW.flush();
					}else
					
					if(Line.contains("Asset")) {
						AsPa = true;
						BW.write("Asset:" + levelData[5]);
						BW.newLine();
						BW.flush();
					}else
					
					if(Line.contains("Info")) {
						inf = true;
						BW.write("Info:" + levelData[4]);
						BW.newLine();
						BW.flush();
					}else 
					if(Line.contains("comments{")){
					BW.write(Line);
					BW.newLine();
					BW.flush();
					}
				}
				
				
				
			}
			
			BW.close();
			FW.close();
			
			BR.close();
			FR.close();
			
			String fileName1 = newFile.getAbsolutePath().replace(newFile.getName(), "");
			String fileName2 = levelFile.getName();
			
			levelFile.delete();
			
			File tempF = new File(fileName1 + fileName2);
			
			newFile.renameTo(tempF);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
