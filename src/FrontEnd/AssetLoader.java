package FrontEnd;

import java.net.URL;

import javax.swing.ImageIcon;

import mainWindow.MainWindow;

public class AssetLoader {

public ImageIcon Icon;
	
public ImageIcon Background;

	public AssetLoader() {
		
	}
	
	
	public void LoadAssets() {
		
		//Window / Taskbar icon
		URL Iconurl = MainWindow.class.getResource("/Icon.png");
		Icon = new ImageIcon(Iconurl);
		
		//Background image
		URL BGurl = MainWindow.class.getResource("/Background.png");
		Background = new ImageIcon(BGurl);
		
	}
	
	
	
}
