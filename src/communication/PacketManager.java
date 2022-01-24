package communication;

import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import Engine.Controller;
import mainWindow.MainWindow;

public class PacketManager {
	
//private cryptingManager crMa;
private communicator com;
private MainWindow mWin;

private String version;

private int readPos = 0;


private byte[] data = new byte[4096];


private boolean exe;
private boolean jar;

public boolean filePacket = false;

public String curID = "0";

	public PacketManager(boolean Exe,boolean Jar,communicator Com) {
		com = Com;
		exe = Exe;
		jar = Jar;
	}
	
	public void setMainWindow(MainWindow MWin) {
		mWin = MWin;
	}
	
	
	//returns the current Packet
	public byte[] getPacket() {
		return data;
	}
	
	//creates a new Packet for sending
	public void newPacket(String packetType) {
		
		data = new byte[0];
		filePacket = false;
		
		String HEXid = "0x00";
		
		//Request MapList 0x0F
		//Request MapUpload 0x10
		//Request MapDownload 0x11
		//Request MapAssetsUpload 0x12
		
		//Request TextureList 0x14		
		//Request TextureUpload 0x15
		//Request TextureDownload x16
				
		//--------------
				
		//public Key Packet 0xFF
		//Version Packet 0x0B
		//Request UpdateDownload Packet 0x0C
		//Disconnect Packet 0x0A
		
		switch(packetType) {
		
			case("RequestMapList"):
				HEXid = "0x0F";
				addToPacket(HEXid.getBytes());
			break;
			
			case("RequestMapUpload"):
				HEXid = "0x10";
				addToPacket(HEXid.getBytes());
			break;
			
			case("RequestMapDownload"):
				HEXid = "0x11";
				addToPacket(HEXid.getBytes());
				addToPacket(com.LevelName.getBytes());
			break;
			
			case("RequestAssetsUpload"):
				HEXid = "0x12";
				addToPacket(HEXid.getBytes());
			break;
			
			case("MapInfo"):
				HEXid = "0x13";
				addToPacket(HEXid.getBytes());
				addToPacket(com.LevelName.getBytes());
			break;
			
			case("RequestTextureList"):
				HEXid = "0x14";
				addToPacket(HEXid.getBytes());
			break;
			
			case("RequestTextureUpload"):
				HEXid = "0x15";
				addToPacket(HEXid.getBytes());
			break;
			
			case("RequestTextureDownload"):
				HEXid = "0x16";
				addToPacket(HEXid.getBytes());
			break;
		
			//-------------------------------
		
			case("Key"):
				HEXid = "0xFF";
				addToPacket(HEXid.getBytes());
//				addToPacket(crMa.getPublicKey().getEncoded());
			break;
		
			case("Version"):
				HEXid = "0x0B";
				addToPacket(HEXid.getBytes());
			break;
		
			case("RequestUpdate"):
				HEXid = "0x0C";
				addToPacket(HEXid.getBytes());//add jar or exe identeifer
				if(exe) {
					addToPacket("exe".getBytes());
				}
				if(jar) {
					addToPacket("jar".getBytes());
				}
			break;
			
			case("Disconnect"):
				HEXid = "0x0A";
				addToPacket(HEXid.getBytes());
			break;
			
			// big files
			
			//50
				
			case("LatestStable"):
				HEXid = "0x32";
				addToPacket(HEXid.getBytes());
				filePacket = true;
				//get current version
			break;
				
			case("LatestExperimental"):
				HEXid = "0x33";
				addToPacket(HEXid.getBytes());
			break;
			
			
			
			
			case("UpVoteMap"):
				HEXid = "0x20";
				addToPacket(HEXid.getBytes());
				addToPacket(com.LevelName.getBytes());
			break;
			
			case("DownVoteMap"):
				HEXid = "0x21";
				addToPacket(HEXid.getBytes());
				addToPacket(com.LevelName.getBytes());
			break;
		}
		
		curID = HEXid;
	}
	
	//private function to add Info to packet
	private void addToPacket(byte[] Data) {
		
		byte[] dataBuffer = data;
		
		data = new byte[dataBuffer.length + Data.length];
		
		int pos = 0;
		
		for(byte B : dataBuffer) {
			
			data[pos] = B;
			pos++;
		
		}
		
		for(byte B : Data) {
			
			data[pos] = B;
			pos++;
			
		}
		
	}
	
	
	
	
	//set the current packet for reading from it
	public void workPacket(byte[] Packet) {
		
		data = Packet;
		readPos = 0;
		
		String PID = getPacketType();
		
		System.out.print("Got packet : " + PID);
		
		switch(PID) {
		
		case("0x0F"): //MapList
			System.out.println(" - MapList ");
		break;
		
		case("0x10"): //MapUpload
			System.out.println(" - MapUpload ");
		break;
		
		case("0x11"): //MapDownload
			System.out.println(" - MapDownload ");
		break;
		
		case("0x13"):
			System.out.println(" - MapInfo");
			String LevelInfo = getString();
			mWin.MB.setInfoText(LevelInfo);
		break;
		
		case("0x14"): //TextureList
			System.out.println(" - TextureList ");
		break;
		
		case("0x15"): //TextureUpload
			System.out.println(" - TextureUpload ");
		break;
		
		case("0x16"): //TextureDownload
			System.out.println(" - TextureDownload ");
		break;
		
		//-----------------------------------
		
		case("0xFF"): //Key Packet
			System.out.println(" - Key Packet ");
			com.setPublicKey(getKey());
		break;
		
		case("0x0B"): //Version Packet
			System.out.println(" - Version Packet ");
			//if update needed popup box , yes = RequestUpdate Packet | no = disconect packet
			
			String ver = getString();
			
//			System.out.println("Ver : " + ver);
		
			
			
			if(!ver.equals(com.getVersion())) {
				mWin.warWin.DisplayWarning(new String[]{"Wrong Version","You have : " + com.getVersion(),"You need : " + ver,"Download"});
				
				boolean answer = mWin.warWin.chooise;
				
				if(answer) {
					com.setConnected();
					com.addPacket("RequestUpdate");
						
					com.readFile();
					com.disconnect();
					
					String curDir = new File("").getAbsolutePath();
					
					ProcessBuilder processBuilder=new ProcessBuilder("java","-jar",curDir + "/" + "MMMMUpdater.jar");

					try {
						
						Process process=processBuilder.start();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					System.exit(0);
					
				}else {
					System.out.println("Disconnect");
					com.setConnected();
					com.disconnect();
				}
				
			}else {
				com.setConnected();
			}
		
			
			
		break;
		
		case("0x0C"): //UpdateDownload Packet
			System.out.println(" - Update Download Packet ");
			
			com.readFile();
			
			//after finish disconnect and run Cleaner and close program 
			
		break;
		
		case("0x0A"): //Disconnect Packet
			System.out.println(" - Disconect Packet ");
		break;
		
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//returns the Type of the Packet
	private String getPacketType() {
		
//		System.out.println("Length:" + data.length);
		
		byte[] HexId = new byte[4];
		
		HexId[readPos] = data[readPos];
		readPos++;
		HexId[readPos] = data[readPos];
		readPos++;
		HexId[readPos] = data[readPos];
		readPos++;
		HexId[readPos] = data[readPos];
		readPos++;
		
		return new String(HexId);
	}
	
	//Returns the public Key from a Key packet
	private PublicKey getKey() {
		
		byte[] Key = new byte[data.length - readPos];
		
		int Pos = 0;
		for(@SuppressWarnings("unused") byte B : Key) {
			Key[Pos] = data[readPos];
			Pos++;
			readPos++;
		}
		
		PublicKey publicKey = null;
		
		try {
			
			publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Key));
			
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return publicKey;
	}
	
	
	private String getString() {
		
		byte[] String = new byte[data.length - readPos];
		
		int Pos = 0;
		for(@SuppressWarnings("unused") byte B : String) {
			String[Pos] = data[readPos];
			Pos++;
			readPos++;
		}
		
		return new String(String);
		
	}
	
	
}
