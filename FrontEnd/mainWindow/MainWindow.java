package mainWindow;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Engine.Controller;
import Engine.FileManager;
import FrontEnd.AssetLoader;
import FrontEnd.WarningWin;
import communication.communicator;
import map.MapManager;
import map.LevelUploader;
import map.MapBrowser;
import textures.TextureBrowser;
import textures.TextureManager;
import textures.TextureUploader;
import settingsWindow.SettingsMenu;

public class MainWindow {

	
private JFrame window;

private JPanel currentWindow;

private String title = "";


//extra windows
public WarningWin warWin;
public DownloadWindow dWin;

// classes
private AssetLoader AsLo;
private communicator Com;
private FileManager FiMa;

public Controller con;

// version
private String version;

	public MainWindow(String version, AssetLoader asLo, communicator com,FileManager fiMa, Controller Con) {
		
		con = Con;
		
		AsLo = asLo;
		Com = com;
		FiMa = fiMa;
		
		this.version = version;
		
		warWin = new WarningWin(AsLo);
		dWin = new DownloadWindow(AsLo);
		
		currentWindow = new JPanel();
		currentWindow.setLayout(null);
		currentWindow.setLocation(0, 0);
		currentWindow.setOpaque(false);
		currentWindow.setSize((int)(720 * Controller.scale),(int)(480 * Controller.scale));
		
		window = new JFrame();
		window.setSize((int)(720 * Controller.scale),(int)(480 * Controller.scale));
		window.setResizable(false);
		window.setTitle("Manic Miners Mod Manager (Unofficial) " + version);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) 
			{
				
				Com.disconnect();
				window.dispose();
				System.exit(0);
				
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
		});
		
		title = "Manic Miners Mod Manager (Unofficial)  Ver. " + version;
		
		setOffline();
		
		window.setJMenuBar(MenuBar());
		
		window.setIconImage(AsLo.IconOff.getImage());
		
		window.setContentPane(Content());
		
	}
	

	public void setOnline() {
		window.setTitle(title + "   (Online)");
		window.setIconImage(AsLo.IconOn.getImage());
	}
	
	public void setOffline() {
		window.setTitle(title + "   (Offline)");
		window.setIconImage(AsLo.IconOff.getImage());
	}
	
	public void setConnecting() {
		window.setTitle(title + "   (Connecting)");
	}
	
	public void setVisible() {
		window.setVisible(true);
	}
	
	
	
	private JPanel Content() {
		
		JPanel content = new JPanel();
		content.setSize((int)(720 * Controller.scale),(int)(480 * Controller.scale));
		content.setLocation(0,0);
		content.setLayout(null);
		
		content.add(currentWindow);
		content.add(Background());
		
		return content;
		
	}
	
	
	private JLabel Background() {
		
		JLabel background = new JLabel();
		background.setSize((int)(720 * Controller.scale),(int)(480 * Controller.scale));
		background.setLocation(0, 0);
		
		Image bgImage = AsLo.Background.getImage().getScaledInstance((int)(720 * Controller.scale),(int)(480 * Controller.scale), Image.SCALE_SMOOTH);
		
		background.setIcon(new ImageIcon(bgImage));
		
		return background;
		
	}
	
	 private JMenuBar MenuBar() {
	        JMenuBar menuBar = new JMenuBar();
	        menuBar.add(SettingsMenu());
	        menuBar.add(MapMenu());
	        menuBar.add(TextureMenu());
	        menuBar.add(ManicMiners());
	        return menuBar;
	    }
	
	 
	 private JMenu ManicMiners() {
		 
		 JMenu mm = new JMenu("ManicMiners");
		 mm.setFont(Controller.font);
		 
		 JMenuItem playMM = new JMenuItem("Start Game");
		 playMM.setFont(Controller.font);
		 playMM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					String path[] = FiMa.getConfig();
					
					if(path[1].contains("ManicMiners.exe")) {
						new ProcessBuilder(path[1]).start();
					}else {
						con.mainWin.warWin.DisplayWarning(new String[] {"Faulty MM Path","Your MM exe Path is faulty." , "Please eddit it in your settings.", "OK"});
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			 
		 });
		 
		 mm.add(playMM);
		 
		 JMenuItem installLatestStable = new JMenuItem("Install Latest Stable");
		 installLatestStable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!Com.isConnected()) {
					warWin.DisplayWarning(new String[]{"No Internet","You are Not connected","Please connect first","OK!"});
				}else {
					
					currentWindow.removeAll();
					currentWindow.repaint();
					currentWindow.revalidate();
					
					Com.addPacket("LatestStable");
					
				}
				
			}
			 
		 });
		 
//		 mm.add(installLatestStable);
		 
		 JMenuItem installLatestExperimental = new JMenuItem("Install Latest Experimental");
		 installLatestExperimental.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			 
		 });
		 
//		 mm.add(installLatestExperimental);
		 
		 JMenuItem openDocuments = new JMenuItem("Open Documents");
		 openDocuments.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			 
		 });
		 
		 JMenuItem openAppData = new JMenuItem("Open AppData");
		 openAppData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			 
		 });
		 
		 JMenuItem deleteManicMiners = new JMenuItem("Delete ManicMiners");
		 deleteManicMiners.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			 
		 });
		 
		 
		 
		 
		 
		 return mm;
	 }
	 
	 
	 
	 boolean remove = false;
	 
	private JMenu SettingsMenu() {
		JMenu settingsMenu = new JMenu("Options");
		settingsMenu.setFont(Controller.font);
		
		JMenuItem connectITEM = new JMenuItem("Connect");
		connectITEM.setFont(Controller.font);
		connectITEM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(FiMa.getConfig()[0].length() > 2) {
					Com.connect();
				}else {
					
					if(FiMa.getConfig()[0].length() > 2) {
						
						warWin.DisplayWarning(new String[]{"Faulty Username","You need a longer username","Please change your username","OK!"});
						
					}else {
					
						warWin.DisplayWarning(new String[]{"No Username","You have no Username selected","Please select a username in Settings","OK!"});
						
					}
					
				}
				
				
			}
			
		});
		settingsMenu.add(connectITEM);
		
		JMenuItem disconnectITEM = new JMenuItem("Disconnect");
		disconnectITEM.setFont(Controller.font);
		disconnectITEM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentWindow.removeAll();
				currentWindow.repaint();
				currentWindow.revalidate();
				Com.disconnect();
			}
			
		});
		settingsMenu.add(disconnectITEM);
		
		JMenuItem settingsITEM = new JMenuItem("Settings");
		settingsITEM.setFont(Controller.font);
		settingsITEM.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				SettingsMenu SM = new SettingsMenu(con);
				
				currentWindow.removeAll();
				currentWindow.add(SM.getPanel());
				currentWindow.repaint();
				currentWindow.revalidate();
				
			}
			
		});
		
		settingsMenu.add(settingsITEM);
		
		JMenuItem deleteModManager = new JMenuItem("Delete ModManager");
		deleteModManager.setFont(Controller.font);
		deleteModManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JDialog really = new JDialog();
				really.setTitle("Remove it ?");
				really.setSize((int)(350 * Controller.scale),(int)(150 * Controller.scale));
				really.setLayout(null);
				really.setLocationRelativeTo(null);
				really.setModal(true);
				
				JTextField ReallyText = new JTextField();
				ReallyText.setSize((int)(300 * Controller.scale),(int)(25 * Controller.scale));
				ReallyText.setLocation((int)(25 * Controller.scale),(int)(25 * Controller.scale));
				ReallyText.setEditable(false);
				ReallyText.setBackground(null);
				ReallyText.setBorder(null);
				ReallyText.setText("Do you really wanna remove the ModManager ?");
				
				JButton reallyButton = new JButton();
				reallyButton.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
				reallyButton.setLocation((int)(25 * Controller.scale),(int)(75 * Controller.scale));
				reallyButton.setText("YES");
				reallyButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						remove = true;
						really.dispose();
					}
					
				});
				
				JButton noButton = new JButton();
				noButton.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
				noButton.setLocation((int)(175 * Controller.scale),(int)(75 * Controller.scale));
				noButton.setText("NO");
				noButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						really.dispose();
					}
					
				});
				
				really.add(ReallyText);
				really.add(reallyButton);
				really.add(noButton);
				really.setVisible(true);
				
				if(remove) {
					
					try {
						FiMa.deleteModManagerFolder();
						Runtime.getRuntime().exec("cmd /c ping localhost -n 6 > nul && del ManicMinersModManager.jar");
						Runtime.getRuntime().exec("cmd /c ping localhost -n 6 > nul && del ManicMinersModManager.exe");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				
			}
			 
		 });
		
		settingsMenu.add(deleteModManager);
		
		JMenuItem ABOUT = new JMenuItem("About");
		ABOUT.setFont(Controller.font);
		ABOUT.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				About A = new About(con);
				
				currentWindow.removeAll();
				currentWindow.add(A.getPanel());
				currentWindow.repaint();
				currentWindow.revalidate();
				
			}
			
		});
		
		settingsMenu.add(ABOUT);
		
		JMenuItem ChangeLog = new JMenuItem("Change Log");
		ChangeLog.setFont(Controller.font);
		ChangeLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ChangeLog CL = new ChangeLog(con);
				
				currentWindow.removeAll();
				currentWindow.add(CL.getPanel());
				currentWindow.repaint();
				currentWindow.revalidate();
				
			}
			
		});
		settingsMenu.add(ChangeLog);
		
		return settingsMenu;
	}
	 
	public MapBrowser MB;
	
	private JMenu MapMenu() {
		
		JMenu mapMenu = new JMenu("Maps");
		mapMenu.setFont(Controller.font);
		
	        JMenuItem browseMapITEM = new JMenuItem("Browse");
	        browseMapITEM.setFont(Controller.font);
	        browseMapITEM.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(!Com.isConnected()) {
						warWin.DisplayWarning(new String[]{"No Internet","You are Not connected","Please connect first","OK!"});
					}else {
						
						MB = new MapBrowser(con);
						
						currentWindow.removeAll();
						currentWindow.add(MB.getPanel());
						currentWindow.repaint();
						currentWindow.revalidate();
						
					}
					
				}
	        	
	        });
	        
	        mapMenu.add(browseMapITEM);
	        
	        
	        
	        JMenuItem uploadMapITEM = new JMenuItem("Upload");
	        uploadMapITEM.setFont(Controller.font);
	        uploadMapITEM.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(!Com.isConnected()) {
						warWin.DisplayWarning(new String[]{"No Internet","You are Not connected","Please connect first","OK!"});
					}else {
						
						LevelUploader LeUp = new LevelUploader(con);
						
						currentWindow.removeAll();
						currentWindow.add(LeUp.getPanel());
						currentWindow.repaint();
						currentWindow.revalidate();
						
					}
					
				}
	        	
	        });
	        mapMenu.add(uploadMapITEM);
	        
	        
	        
	        JMenuItem manageITEM = new JMenuItem("Manage");
	        manageITEM.setFont(Controller.font);
	        manageITEM.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					MapManager LM = new MapManager(con);
					
					currentWindow.removeAll();
					currentWindow.add(LM.getPanel());
					currentWindow.repaint();
					currentWindow.revalidate();
					
				}
	        	
	        });
	        mapMenu.add(manageITEM);
	        return mapMenu;
	    }

	public TextureBrowser TB;
	
	 private JMenu TextureMenu() {
	    	
		 JMenu textureMenu = new JMenu("Texture");
	     textureMenu.setFont(Controller.font);   
		 
	     JMenuItem browseTexturesITEM = new JMenuItem("Browse");
	     browseTexturesITEM.setFont(Controller.font);
	     browseTexturesITEM.addActionListener(new ActionListener() {

	    	 @Override
			public void actionPerformed(ActionEvent e) {
					
				if(!Com.isConnected()) {
					warWin.DisplayWarning(new String[]{"No Internet","You are Not connected","Please connect first","OK!"});
				}else {
					
					TB = new TextureBrowser(con);
					
					currentWindow.removeAll();
					currentWindow.add(TB.getPanel());
					currentWindow.repaint();
					currentWindow.revalidate();
					
				}
					
			}
	        	
	       });
	        
	        textureMenu.add(browseTexturesITEM);
	        
	        
	        JMenuItem uploadTexturesITEM = new JMenuItem("Upload");
	        uploadTexturesITEM.setFont(Controller.font);
	        uploadTexturesITEM.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(!Com.isConnected()) {
						warWin.DisplayWarning(new String[]{"No Internet","You are Not connected","Please connect first","OK!"});
					}else {
						
						TextureUploader TU = new TextureUploader(con);
						
						currentWindow.removeAll();
						currentWindow.add(TU.getPanel());
						currentWindow.repaint();
						currentWindow.revalidate();
						
					}
					
				}
	        	
	        });
	        
	        textureMenu.add(uploadTexturesITEM);
	        
	        
	        JMenuItem manageTextureITEM = new JMenuItem("Manage");
	        manageTextureITEM.setFont(Controller.font);
	        manageTextureITEM.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					TextureManager TM = new TextureManager(con);
					
					currentWindow.removeAll();
					currentWindow.add(TM.getPanel());
					currentWindow.repaint();
					currentWindow.revalidate();
					
				}
	        	
	        });
	        
	        
	        textureMenu.add(manageTextureITEM);
	        
	        
	        return textureMenu;
	    }
	    
	    
	    
	    
	    
	    
	 
	 public String getVersion() {
		 return version;
	 }
	    
	 
}
