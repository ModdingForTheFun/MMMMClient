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

import FrontEnd.AssetLoader;


public class FileManager {

private Controller con;

private AssetLoader asLo;

	public FileManager(AssetLoader AsLo) {
		asLo = AsLo;
	}
	
	
	
	
	//ModManagerFolder
	
	public void deleteModManagerFolder() {
		
		String homeDir = System.getProperty("user.home");
		
		File configFolder = new File(homeDir + "\\Documents\\ManicMiners\\ModManager");
		
		if(configFolder.exists()) {
			delete(configFolder);
		}
		
	}
	
	
	
	
	//config file
	
	public String[] getConfig(){
		
		String[] configData = new String[3];
		
		String homeDir = System.getProperty("user.home");
		
		File configFolder = new File(homeDir + "\\Documents\\ManicMiners\\ModManager");
		
		if(!configFolder.exists()) {
			configFolder.mkdir();
		}
		
		File configFile = new File(homeDir + "\\Documents\\ManicMiners\\ModManager\\MMMM.cfg");
		
		if(!configFile.exists()) {
			
			try {
				
				configFile.createNewFile();
				
				BufferedWriter BW = new BufferedWriter(new FileWriter(configFile));
				
				BW.write("-");
				BW.newLine();
				BW.write(" ");
				BW.newLine();
				BW.write("false");
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
			configData[1] = BR.readLine(); //game location
			configData[2] = BR.readLine(); // auto connect
			
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
		
		String homeDir = System.getProperty("user.home");
		
		File configFolder = new File(homeDir + "\\Documents\\ManicMiners\\ModManager");
		
		if(!configFolder.exists()) {
			configFolder.mkdir();
		}
		
		File configFile = new File(homeDir + "\\Documents\\ManicMiners\\ModManager\\MMMM.cfg");
		
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
		
		String homeDir = System.getProperty("user.home");
		
		File keyFile = new File(homeDir + "\\Documents\\ManicMiners\\ModManager\\MMMM.key");
		
		keyFile.delete();
		
	}
	
	public String getUserKey() throws IOException {
		
		String homeDir = System.getProperty("user.home");
		
		File keyFile = new File(homeDir + "\\Documents\\ManicMiners\\ModManager\\MMMM.key");
		
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
		
		
		String homeDir = System.getProperty("user.home");
		
		//System.out.println(homeDir);
		
		File levelFolder = new File(homeDir + "\\Documents\\ManicMiners\\Levels");
		
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
			
			File LevelFile = new File(homeDir + "\\Documents\\ManicMiners\\Levels\\" + LevelName);
			
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
				
				String homeDir = System.getProperty("user.home");
				
				File LevelFile = new File(homeDir + "\\Documents\\ManicMiners\\Levels\\" + mapName + ".dat");
				
				if(LevelFile.exists()) {
					System.out.println("Gelöscht");
				}else {
					System.out.println(homeDir + "\\Documents\\ManicMiners\\Levels\\" + mapName + ".dat" + "_wurde nicht gefunden");
				}
				
				delete(LevelFile);
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
		
		String homeDir = System.getProperty("user.home");
		
		File textureMainFolder = new File(homeDir + "\\Documents\\ManicMiners\\tempFiles");
		
		if(!textureMainFolder.exists()) {
			textureMainFolder.mkdir();
		}
		
		File ArmFolder = new File(homeDir + "\\Documents\\ManicMiners\\tempFiles\\Arm");
		File BeltFolder = new File(homeDir + "\\Documents\\ManicMiners\\tempFiles\\Belt");
		File FaceFolder = new File(homeDir + "\\Documents\\ManicMiners\\tempFiles\\Face");
		File LegLeftFolder = new File(homeDir + "\\Documents\\ManicMiners\\tempFiles\\LegLeft");
		File LegRightFolder = new File(homeDir + "\\Documents\\ManicMiners\\tempFiles\\LegRight");
		File TorsoFolder = new File(homeDir + "\\Documents\\ManicMiners\\tempFiles\\Torso");
		
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
			
			String[] folderItems = new File(homeDir + "\\Documents\\ManicMiners\\tempFiles\\" + SubFolder).list();
			
			int counter = 0;
			
			Log("		Texture List : ");
			
			for(String texture : folderItems) {
				
				Log("		-" + texture + "-");
				
				if(texture.contains(".png")) {
					LoadedTextures.put(SubFolder + counter, new ImageIcon(homeDir + "\\Documents\\ManicMiners\\tempFiles\\" + SubFolder + "\\" + texture));
				
					counter++;
				}
				
			}
			
		}
		
		return LoadedTextures;
		
	}
		
	private int counter = 0;
	
	public void loadTextures(){
		
		loadedTextures = new HashMap<String, ImageIcon>();
		
		String homeDir = System.getProperty("user.home");
		
		File textureMainFolder = new File(homeDir + "\\Documents\\ManicMiners\\Textures");
		
		if(!textureMainFolder.exists()) {
			textureMainFolder.mkdir();
		}
		
		File ArmFolder = new File(homeDir + "\\Documents\\ManicMiners\\Textures\\Arm");
		File BeltFolder = new File(homeDir + "\\Documents\\ManicMiners\\Textures\\Belt");
		File FaceFolder = new File(homeDir + "\\Documents\\ManicMiners\\Textures\\Face");
		File LegLeftFolder = new File(homeDir + "\\Documents\\ManicMiners\\Textures\\LegLeft");
		File LegRightFolder = new File(homeDir + "\\Documents\\ManicMiners\\Textures\\LegRight");
		File TorsoFolder = new File(homeDir + "\\Documents\\ManicMiners\\Textures\\Torso");
		
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
			
			String[] folderItems = new File(homeDir + "\\Documents\\ManicMiners\\Textures\\" + SubFolder).list();
			
			Log("		Texture List : ");
			
			for(String texture : folderItems) {
				
				Log("		-" + texture + "-");
				
				if(texture.contains(".png")) {
					loadedTextures.put(SubFolder + counter, new ImageIcon(homeDir + "\\Documents\\ManicMiners\\Textures\\" + SubFolder + "\\" + texture));
				
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
		
		String homeDir = System.getProperty("user.home");
		
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
		
		File newFile = new File(homeDir + "\\Documents\\ManicMiners\\Textures\\" + subFolder + "\\" + fileName);
		
		try {
			Files.copy(file.toPath(),newFile.toPath());
		
			loadedTextures.put(subFolder + counter, new ImageIcon(homeDir + "\\Documents\\ManicMiners\\Textures\\" + subFolder + "\\" + fileName));
			
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
