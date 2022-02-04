package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import Engine.Controller;
import Engine.FileManager;
import mainWindow.MainWindow;

public class communicator {
	
private Controller con;
	
private Socket server;
	
private DataInputStream dataIn;
private DataOutputStream dataOut;
	
private Boolean connected = false;
private boolean tryingToConnect = false;

private PacketManager PaMa;
//private cryptingManager crMa;

private PublicKey serverKey;

private boolean firstPacket = true;

private boolean Dev;

private FileManager FiMa;
private MainWindow MWin;

private LinkedList<String> packetsToSend = new LinkedList<String>();



	public void UPDATE() {
		
		
		if(connected && packetsToSend.size() > 0) {
			
			write(packetsToSend.get(0));
			
			System.out.println("Sending Packet : " + packetsToSend.get(0));
			
			if(PaMa.filePacket) {
				readFile();
				
				System.out.println("Waiting for File");
			}
			
			if(PaMa.curID.equals("0x13")) {
				
				read();
				
			}
			
			packetsToSend.remove(0);
		}
		
	}


	
	
	public void addPacket(String packetType) {
		
		System.out.println("Adding Packet : " + packetType);
		
		packetsToSend.add(packetType);
		
		System.out.println("In Queu :" + packetsToSend.size());
	}




	public String getVersion() { 
		return MWin.getVersion();
	}


	public communicator(Controller Con ,boolean dev,boolean exe, boolean jar,FileManager fiMa) { // con dem or not / file manager for username / set the main window to online
		
		con = Con;
		
//		crMa = new cryptingManager();
		
		PaMa = new PacketManager(exe,jar,this);
		
		Dev = dev;
		
		FiMa = fiMa;
	}
	
	public void setMainWin(MainWindow mWin) {
		MWin = mWin;
		PaMa.setMainWindow(mWin);
	}
	
	//setPublicKeyOfServer
	public void setPublicKey(PublicKey sKey) {
//		System.out.println("Pup Key from Server : " + sKey);
		serverKey = sKey;
	}
	
	
	
	//connect to server
	public void connect() {
		
		if(!connected && !tryingToConnect) {
			
			tryingToConnect = true;
			
			System.out.println("Trying to connect");
			
			MWin.setConnecting();
			
			try {
				
				//Build up the connection

				if(Dev) {
					
					server = new Socket("192.168.0.125",8888);
//					server = new Socket("127.0.0.1",8888);
					
					System.out.println("Trying to connect DEV");
					
				}else {
					
					server = new Socket("mmmmserver.dynv6.net",8888);
					
				}
				
				dataOut = new DataOutputStream(server.getOutputStream());
				
				dataOut.flush();
				
				dataIn = new DataInputStream(server.getInputStream());
				
				dataOut.writeUTF("MMMM_Client_03hg983tz3pgn3uzß3toj09");
				dataOut.flush();
				
				//Create crypting Key
//				crMa.createKey();
		
				
				//1. read public key of Server
//				System.out.println("read p key of server");
//				read();
				
				
				//2. send own public key encryptet
//				System.out.println("send own p key to server");
//				write("Key");
				
				
				
				// Username of connected dude
				byte[] username = FiMa.getConfig()[0].getBytes();
//				username = crMa.encryptPacket(username,serverKey);
				dataOut.writeInt(username.length);
				dataOut.write(username);
				dataOut.flush();
				
				System.out.println("Trying to connect 2");
				
				// UserKey
				byte[] userKey = FiMa.getUserKey().getBytes();
				dataOut.writeInt(userKey.length);
				dataOut.write(userKey);
				dataOut.flush();
				
				
				System.out.println("Trying to connect 3");
				
				
				//read if user is taken
				byte[] answer = new byte[dataIn.readInt()];
				dataIn.readFully(answer);
				
				System.out.println("Trying to connect 4");
				
				String Answer = new String(answer);
				System.out.println("Answer : " + Answer);
				if(!Answer.equals("LogIn")) {
					MWin.warWin.DisplayWarning(new String[]{"Failed to Connect","Your Username is already taken","Please use a different one.","OK!"});
					
					tryingToConnect=false;
					
					return;
				}
				
				//now check for version
				System.out.println("Read Accepted ver from server");
				//read accepted Version
				read();
				
			} catch (UnknownHostException e) {
					
					FiMa.Log("connect : UnknownHostException");
					MWin.warWin.DisplayWarning(new String[]{"Failed to Connect","Unable to connect to Server","Please Contact MMMM Admin","OK!"});
				
				e.printStackTrace();
			} catch (IOException e) {
					
					FiMa.Log("connect : IOException");
					MWin.warWin.DisplayWarning(new String[]{"Failed to Connect","Unable to connect to Server","Please Contact MMMM Admin","OK!"});
				
				e.printStackTrace();
			}
			
			tryingToConnect = false;
			
		}
		
	}
	
	//set connect from PacketManager
	public void setConnected() { 
		System.out.println("conected");
		MWin.setOnline();
		connected = true;
	}
	
	//Disconnect from server
	public void disconnect() {
		
		firstPacket = true;
		
		if(connected) {
			
				try {
				
				write("Disconnect");
				
				connected = false;
				
				server.close();
				
				dataOut.close();
				dataIn.close();
				
				MWin.setOffline();
				
			} catch (IOException e) {
	
				FiMa.Log("disconnect : IOException");
				
				e.printStackTrace();
			}
				
		}
		
	}
	
	
	
	
	
	
	
	
	
	private void read() {
		
		byte[] Packet = new byte[4096];
		
		try {
			
			Packet = new byte[dataIn.readInt()];
			dataIn.readFully(Packet);
			
			if(!firstPacket) {
//				Packet = crMa.decryptPacket(Packet);
			}else {
				firstPacket = false;
			}
			
			PaMa.workPacket(Packet);
			
		} catch (IOException e) {
			
			connected = false;
			
			e.printStackTrace();
		}
		
	}
	
	public void write(String packetType) {
		
		try {
			
			PaMa.newPacket(packetType);
			
			byte[] Packet = PaMa.getPacket();
			
//			Packet = crMa.encryptPacket(Packet, serverKey);
			
			dataOut.writeInt(Packet.length);
			
			dataOut.write(Packet);
			
			dataOut.flush();
			
		} catch (IOException e) {
			
			connected = false;
			
			e.printStackTrace();
		}
		
	}
	
	
	

	
	
	
	
	
	
	//FILE Stuff
	
	public void readFile() {
		
		MWin.dWin.setVisible(true);
		
		boolean isTextureFetch = true;
		boolean LastTexture = false;
		boolean isGameDownload = false;
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		byte[] Packet = new byte[4096];
		
		try {
			
			int FileAmount = dataIn.readInt();
			int FileCount = 1;
			
			System.out.println("File Amount : " + FileAmount);
			
			//Loop for lotta files here
			
			while(FileAmount >= FileCount) {
				
				
				// Get InfoPacketSize
				Packet = new byte[dataIn.readInt()];
				
				System.out.println("InfoPacket size : " + Packet.length);
				
				//get Info Packet
				dataIn.readFully(Packet);
				
				//decrypt infopacket
//				Packet = crMa.decryptPacket(Packet);
				
				//Get File Name from packet
				int nameSize = Packet[0];
				
				System.out.println("Name Size : " + nameSize);
				
				byte[] String = new byte[nameSize];
				
				int Pos = 0;
				int readPos = 1;
				for(@SuppressWarnings("unused") byte B : String) {
					String[Pos] = Packet[readPos];
					Pos++;
					readPos++;
				}
				
				String FileName = new String(String);
				
				System.out.println("File Name : " + FileName);
				
				//Get File Type    Map , MapAssets , TextureXXX 
				// 0x32 = Client Update
				// 0x33 = Map
				// 0x34 = MapAsset (sound only)
				// 0x3C = Textures Arm
				// 0x3D = Belt
				// 0x3E = Face
				// 0x3F = LegLeft
				// 0x40 = LegRight
				// 0x41 = Torso
				
				
				//get HexId of File Type
				byte[] HexId = new byte[4];
				
				HexId[0] = Packet[readPos];
				readPos++;
				HexId[1] = Packet[readPos];
				readPos++;
				HexId[2] = Packet[readPos];
				readPos++;
				HexId[3] = Packet[readPos];
				readPos++;
				
				String ID =  new String(HexId);
				
				long FileSize = (int) dataIn.readLong();//ActualFileSize
				
				byte[] buffer = new byte[4096];
				
				File FILE = new File(FileName);
				
				
				String homeDir = System.getProperty("user.home");
				
				if(con.fiMa.MMFolderLoc.length() > 4) {
					homeDir = con.fiMa.MMFolderLoc;
				}
				
				System.out.println("File ID : " + ID);
				
				switch(ID) {
				
				case("0x32"):
					isTextureFetch = false;
					FileName += "NEW";
					FILE.createNewFile();
				break;
				
				case("0x33"):
					isTextureFetch = false;
					FILE = new File(homeDir + "\\Levels\\" + FileName);
				break;
				
				case("0x34"):
					isTextureFetch = false;
					byte[] LevelFolderName = new byte[Packet.length - readPos];
				
					int LFNcount = 0;
					while(LFNcount < LevelFolderName.length) {
						
						LevelFolderName[LFNcount] = Packet[readPos];
						
						LFNcount++;
						readPos++;
					}
					
					String LevelName = new String(LevelFolderName);
					
					FILE = new File(homeDir + "\\Levels\\ASSETS\\Sounds\\" + LevelName);
					
					if(!FILE.exists()) {
						FILE.mkdir();
					}
					
					FILE = new File(homeDir + "\\Levels\\ASSETS\\Sounds\\" + LevelName + "\\" + FileName);
				break;
				
				case("0x3C"):
					isTextureFetch = true;
					FILE = new File(homeDir + "\\tempFiles\\Arm");
					
					if(!FILE.exists()) {
						FILE.mkdir();
					}
					
					FILE = new File(homeDir + "\\tempFiles\\Arm\\" + FileName);
					FILE.deleteOnExit();
				break;
				
				case("0x3D"):
					isTextureFetch = true;
					FILE = new File(homeDir + "\\tempFiles\\Belt");
					
					if(!FILE.exists()) {
						FILE.mkdir();
					}
					
					FILE = new File(homeDir + "\\tempFiles\\Belt\\" + FileName);
					FILE.deleteOnExit();
					
				break;
				
				case("0x3E"):
					isTextureFetch = true;
					FILE = new File(homeDir + "\\tempFiles\\Face");
					
					if(!FILE.exists()) {
						FILE.mkdir();
					}
					
					FILE = new File(homeDir + "\\tempFiles\\Face\\" + FileName);
					FILE.deleteOnExit();
					
				break;
				
				case("0x3F"):
					isTextureFetch = true;
					FILE = new File(homeDir + "\\tempFiles\\LegLeft");
					
					if(!FILE.exists()) {
						FILE.mkdir();
					}
					
					FILE = new File(homeDir + "\\tempFiles\\LegLeft\\" + FileName);
					FILE.deleteOnExit();
					
				break;
				
				case("0x40"):
					isTextureFetch = true;
					FILE = new File(homeDir + "\\tempFiles\\LegRight");
					
					if(!FILE.exists()) {
						FILE.mkdir();
					}
					
					FILE = new File(homeDir + "\\tempFiles\\LegRight\\" + FileName);
					FILE.deleteOnExit();
					
				break;
				
				case("0x41"):
					isTextureFetch = true;
					LastTexture=true;
					FILE = new File(homeDir + "\\tempFiles\\Torso");
					
					if(!FILE.exists()) {
						FILE.mkdir();
					}
					
					FILE = new File(homeDir + "\\tempFiles\\Torso\\" + FileName);
					FILE.deleteOnExit();
					
				break;
				
				//game files
				
				case("0x50"):
					isTextureFetch = false;
					isGameDownload = true;
					FILE = new File(FiMa.getConfig()[1] + "\\" +  FileName);
					System.out.println("GameZipLocation");
				break;
				
				}
				
				System.out.println("File Location : " + FILE.getAbsolutePath());
				
				if(FILE.exists()) {
					FILE.delete();
					FILE.createNewFile();
				}else {
					FILE.createNewFile();
				}
				
				FileOutputStream FileWriter = new FileOutputStream(FILE);;
				
				System.out.println("Start reading");
				
				MWin.dWin.setVisible(true);
				MWin.dWin.setFileAmount(FileAmount);
				MWin.dWin.updateFileNr(FileCount);
				MWin.dWin.setFileName(FileName);
				MWin.dWin.setFileSize(FileSize);
				
				// to see the download window
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				while(FILE.length() < FileSize) {
					
					//read first MAX 4096 bytes of file
					buffer = new byte[dataIn.readInt()];
					dataIn.readFully(buffer);
					
//					buffer = crMa.decryptPacket(buffer);
					
					FileWriter.write(buffer);
					
					MWin.dWin.updateFileBar(FILE.length());
					
					try {
						
						Thread.sleep(1);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("Cur File Size : " + FILE.length() + " | Goal FileSize " + FileSize);
				}
				
				FileWriter.flush();
				
				System.out.println("Finisht Reading");
				
				if(FileWriter != null) {
					FileWriter.close();
				}
				
				System.out.println("File Size : " + FILE.length()); 
				
				//FILE = null;
				
				//TODO extract zip if game
				if(isGameDownload) {
					
					
					
				}
				
				FileCount++;
			}
			
			System.out.println("No more Files");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Open Download Window");
		
		if(!isTextureFetch) {
			MWin.dWin.setCloseAble();
			MWin.warWin.DisplayWarning(new String[] {"Download","Your download" , "Finisht !", "Close"});
		}else 
		if(LastTexture){
			MWin.dWin.setCloseAble();
			MWin.warWin.DisplayWarning(new String[] {"Textures","Finished fetching textures" , "", "Close"});
			con.mainWin.TB.UpdateTexturePanel();
		}
		
		System.out.println("End");
		
		
		
	}
	
	
	
	
	
	

	
	public void writeFile(File[] fileList,String fileType) {
		
		System.out.println("Writing Files : "  + fileList.length);
		System.out.println("File Type : " + fileType);
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			//send how many files will be send
			dataOut.writeInt(fileList.length);
			dataOut.flush();
			
			int FileCounter = 1;
			
			for(File file : fileList) {
				
				System.out.println("File Nr. : " + FileCounter);
				
				byte[] FileName = file.getName().getBytes();
				
				byte[] InfoPacket = new byte[1];
				
				InfoPacket[0] = (byte)FileName.length;
				
				byte[] HexId = new byte[4];
				
				switch(fileType) {
				
				case("ClientFiles"):
					HexId = new String("0x32").getBytes();
				break;
				
				case("Map"):
					HexId = new String("0x33").getBytes();
				break;
				
				case("MapAssets"):
					HexId = new String("0x34").getBytes();
				break;
				
				case("Arm"):
					HexId = new String("0x3C").getBytes();
				break;
				
				case("Belt"):
					HexId = new String("0x3D").getBytes();
				break;
				
				case("Face"):
					HexId = new String("0x3E").getBytes();
				break;
				
				case("LegLeft"):
					HexId = new String("0x3F").getBytes();
				break;
				
				case("LegRight"):
					HexId = new String("0x40").getBytes();
				break;
				
				case("Torso"):
					HexId = new String("0x41").getBytes();
				break;
				
				}
				
				byte[] dataBuffer = InfoPacket;
				
				InfoPacket = new byte[dataBuffer.length + FileName.length];
				
				int pos = 0;
				
				for(byte B : dataBuffer) {
					
					InfoPacket[pos] = B;
					pos++;
				
				}
				
				for(byte B : FileName) {
					
					InfoPacket[pos] = B;
					pos++;
					
				}
				
				dataBuffer = InfoPacket;
				
				InfoPacket = new byte[dataBuffer.length + HexId.length];
				
				pos = 0;
				
				for(byte B : dataBuffer) {
					
					InfoPacket[pos] = B;
					pos++;
				
				}
				
				for(byte B : HexId) {
					
//					System.out.println("ADDING ID VAL : " + B);
					
					InfoPacket[pos] = B;
					pos++;
					
				}
				
				if(fileType.equals("MapAssets")) {
					
					String AssetsPath = new String(file.getAbsolutePath());
					
					int indexOfSounds = AssetsPath.indexOf("Sounds");
					int indexOfFile = AssetsPath.indexOf(file.getName());
					
					String MapFolder = AssetsPath.substring(indexOfSounds + 7, indexOfFile -1);
					
					dataBuffer = InfoPacket;
					
					InfoPacket = new byte[dataBuffer.length + MapFolder.getBytes().length];
					
					pos = 0;
					
					for(byte B : dataBuffer) {
						
						InfoPacket[pos] = B;
						pos++;
					
					}
					
					for(byte B : MapFolder.getBytes()) {
						
						InfoPacket[pos] = B;
						pos++;
						
					}
				}
				
				//info packet ready
				
				//send packet size
				dataOut.writeInt(InfoPacket.length);
				dataOut.flush();
				
				//send info packet
				dataOut.write(InfoPacket);
				dataOut.flush();
			
				System.out.println("Does it already exits");
				
				//read if it is ok to send the file
				int SendFile = dataIn.readInt();
				
				System.out.println("Well : " + SendFile);
				
				if(SendFile == 1) {
					
					//send file size
					dataOut.writeLong(file.length());
					dataOut.flush();
					
				    long filePos = 0;
				    
				    System.out.println("File Size : " + file.length());
				    
				    byte[] FILE = new byte[(int) file.length()];
				    
				    try(FileInputStream inputStream = new FileInputStream(file)){

						FILE = inputStream.readAllBytes();
						
					}
					catch (Exception ex){
						 
						ex.printStackTrace();
					
					}
				    
					while(filePos < file.length()) {
						
						byte[] filePart = new byte[4096];
						
						if((file.length() - filePos)>= 4096) {
							filePart = new byte[4096];
						}else {
							filePart = new byte[(int) (file.length() - filePos)];
						}
						
						int count = 0;
						
						while(count < filePart.length) {
							
							filePart[count] = FILE[(int) filePos];
							
							count++;
							filePos++;
						}
						
						dataOut.writeInt(filePart.length);
						dataOut.write(filePart);
						dataOut.flush();
						
					}

					System.out.println("Finisht File : " + file.getName());
					
				}else {
					
					System.out.println("File already exists on server  " + file.getName());
					
				}
				
				FileCounter++;
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	//get data
	public String[][] getLevelData(){
		
		write("RequestMapList");
		
		int MapAmount = 0;
		
		try {
			
			MapAmount = dataIn.readInt();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[][] LevelData = new String[MapAmount][5];
		String[][] levelData = new String[MapAmount][5];
		
		byte[] Packet = new byte[2048];
		
		int row = 0;
		int col = 0;
		
		for(String[] dataAr : LevelData) {
			
			for(@SuppressWarnings("unused") String info : dataAr){
				
				try {

					Packet = new byte[dataIn.readInt()];
					dataIn.readFully(Packet);
					
					levelData[row][col] = new String(Packet);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				col++;
				
			}
			
			row++;
			col = 0;
		}
		
		return levelData;
		
	}
	
	
	
	
	
	
	
	
	
	//level stuff
	public String LevelName;
	public void downloadLevel(String levelName) {
		
		LevelName = levelName;
		write("RequestMapDownload");
		readFile();
		
	}
	
	
	
	
	public void getTextures() {
		write("RequestTextureList");
		readFile();
		readFile();
		readFile();
		readFile();
		readFile();
		readFile();
	}
	
	
	
	
	
	
	public Boolean isConnected() {
		return connected;
	}
	
}
