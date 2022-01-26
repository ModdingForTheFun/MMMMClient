package FrontEnd;

import java.net.URL;

import javax.swing.ImageIcon;

import mainWindow.MainWindow;

public class AssetLoader {

public ImageIcon IconOff;

public ImageIcon IconOn;

public ImageIcon Background;



	public AssetLoader() {
		
	}
	
	
	public void LoadAssets() {
		
		//Window / Taskbar icon
		
		URL IconOnUrl = MainWindow.class.getResource("/ICON_On.png");
		IconOn = new ImageIcon(IconOnUrl);
		
		URL IconOffUrl = MainWindow.class.getResource("/ICON_Off.png");
		IconOff = new ImageIcon(IconOffUrl);
		
		//Background image
		URL BGurl = MainWindow.class.getResource("/Background.png");
		Background = new ImageIcon(BGurl);
		
	}
	
	
	
}
