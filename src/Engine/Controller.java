package Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import FrontEnd.AssetLoader;
import FrontEnd.WarningWin;
import communication.communicator;
import mainWindow.MainWindow;

public class Controller extends Thread{

private String version = "UwU"; // Client Version

public AssetLoader asLo;
public FileManager fiMa;
public communicator com;

public MainWindow mainWin;

public boolean jar = false;
public boolean exe = false;
public boolean dev = false;

private boolean running = false;

	public Controller() {
		
		OSCheck();
		
	}
	
	public void setVersion(String ver) {
		version = ver;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void run() {
		
		asLo = new AssetLoader();
		
		asLo.LoadAssets();
		
		fiMa = new FileManager(this,asLo);
		
		ExeJarCheck();
		
//		waWi = new WarningWin(this);
		
		com = new communicator(this,dev,exe,jar,fiMa);
		
		mainWin = new MainWindow(version,asLo,com,fiMa,this); // needs communicator for windows aswell as assets
		mainWin.setVisible();
		
		com.setMainWin(mainWin);
		
		fiMa.loadTextures();
		
		fiMa.Log("Log : " + "Gamevserion - " + version);
		fiMa.Log("Log creation date/Time : " + java.time.LocalDate.now() + " / " + java.time.LocalTime.now());
		fiMa.Log("--");
		
		if(fiMa.getConfig()[2].equals("true")) {
			com.connect();
		}
		
		running = true;
		while(running) {
			
			com.UPDATE();
			
			
			
			
			
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	
	private void ExeJarCheck() {
		
		File F = new File("");
		F = new File(F.getAbsoluteFile() + "\\");
		F.deleteOnExit();
		
		String curDir = F.getAbsolutePath();
		
		System.out.println(curDir);
		
		String[] content = F.list();  
		
		for(String s : content) {
			if(s.equals("ManicMinersModManager.exe")) {
				exe = true;
			}
			if(s.equals("ManicMinersModManager.jar")) {
				jar = true;
			}
			if(s.equals(".classpath") || (fiMa.getConfig()[0] != null && fiMa.getConfig()[0].equals("ICsleep"))) {
				dev = true;
			}
		}
		
	}
	
	
	private void OSCheck() {
		
		String OS = System.getProperty("os.name").toLowerCase();
		
		if(OS.contains("x")) {
			
			System.out.println("Your OS is : " + OS);
			
			JDialog popUp = new JDialog();
			popUp.setModal(true);
			popUp.setSize(225,150);
			popUp.setLocationRelativeTo(null);
			popUp.setTitle("Your not using windows!");
			popUp.setLayout(null);
			popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			JPanel content = new JPanel();
			content.setSize(900, 950);
			content.setLocation(0, 0);
			content.setLayout(null);
			
			JTextField ReallyText = new JTextField();
			ReallyText.setSize(500,25);
			ReallyText.setLocation(50,10);
			ReallyText.setEditable(false);
			ReallyText.setBackground(null);
			ReallyText.setBorder(null);
			ReallyText.setText("Your not using Windows");
			
			JTextField mapNameText = new JTextField();
			mapNameText.setSize(500,25);
			mapNameText.setLocation(50,40);
			mapNameText.setEditable(false);
			mapNameText.setBackground(null);
			mapNameText.setBorder(null);
			mapNameText.setText("Some things may not work !");
			
			JButton yes = new JButton();
			yes.setSize(125,25);
			yes.setLocation(50,75);
			yes.setText(" OK ! ");
			yes.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					popUp.dispose();
					
				}
				
			});
			
			content.add(ReallyText);
			content.add(mapNameText);
			content.add(yes);
			
			popUp.setContentPane(content);
			
			popUp.setVisible(true);
		}
		
	}
	
	
}
