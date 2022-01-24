package textures;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Engine.Controller;

public class TextureUploader {

	private JPanel Panel;

	private Controller con;

	private JScrollPane scrollPane;

	private LinkedList<String> uploadThis;

		public TextureUploader(Controller Con) {
			
			con = Con;
		
			Panel = new JPanel();
			Panel.setSize(720,480);
			Panel.setLocation(0, 0);
			Panel.setLayout(null);
			Panel.setOpaque(false);
			
			
			//Sorting and Removing
			
			JPanel options = new JPanel();
			options.setSize(175,370);
			options.setLocation(500,25);
			options.setLayout(null);
			options.setBackground(Color.LIGHT_GRAY);
			
			JTextField FilterText = new JTextField();
			FilterText.setSize(125,25);
			FilterText.setLocation(65,10);
			FilterText.setEditable(false);
			FilterText.setBackground(null);
			FilterText.setBorder(null);
			FilterText.setText("FILTER");
			
			JTextField textureTypeText = new JTextField();
			textureTypeText.setSize(125,25);
			textureTypeText.setLocation(25,50);
			textureTypeText.setEditable(false);
			textureTypeText.setBackground(null);
			textureTypeText.setBorder(null);
			textureTypeText.setText("Texture Type :");
			
			JComboBox<String> textureTypeBox = new JComboBox<String>();
			textureTypeBox.setSize(125,25);
			textureTypeBox.setLocation(25,75);
			textureTypeBox.setEditable(false);
			textureTypeBox.addItem("Arm");
			textureTypeBox.addItem("Belt");
			textureTypeBox.addItem("Face");
			textureTypeBox.addItem("LegLeft");
			textureTypeBox.addItem("LegRight");
			textureTypeBox.addItem("Torso");
			
			UpdateTexturePanel(textureTypeBox);
			
			JButton sort = new JButton();
			sort.setSize(125,25);
			sort.setLocation(25,250);
			sort.setText("Update List");
			sort.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					UpdateTexturePanel(textureTypeBox);
					uploadThis.clear();
				}	
				
			});
			
			JButton uploadTextures = new JButton();
			uploadTextures.setSize(149,25);
			uploadTextures.setLocation(12,300);
			uploadTextures.setText("Upload Textures");
			uploadTextures.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					File[] textures = new File[uploadThis.size()];
					
					int count = 0;
					for(String s : uploadThis) {
						textures[count] = new File(s);
						count++;
					}
					
					con.com.addPacket("RequestTextureUpload");
					con.com.writeFile(textures,textureTypeBox.getSelectedItem().toString());
					
				}
				
			});
			
			
			
			
			
			options.add(FilterText);
			options.add(textureTypeText);
			options.add(textureTypeBox);
			options.add(sort);
			options.add(uploadTextures);
			
			Panel.add(scrollPane);
			Panel.add(options);
			
		}
		
		
		
		
		
		
		
		
		
		
		
		private void UpdateTexturePanel(JComboBox<String> textureType) {
			
			Component[] panelComps = Panel.getComponents();
			
			for(Component comp : panelComps) {
				if(comp.equals(scrollPane)) {
					Panel.remove(scrollPane);
				}
			}
			
			
			
			//Textures List
			HashMap<String,ImageIcon> data  = con.fiMa.getTextures();
					
			//List of Texture Types
			LinkedList<String> textureTypes = new LinkedList<String>();
					
			//List of textures to delet
			uploadThis = new LinkedList<String>();
					
			//Panel that displays the textures sortet...kinda 
			JPanel ImageDisplay = new JPanel();
			ImageDisplay.setPreferredSize(new Dimension(430,720));
			ImageDisplay.setBackground(Color.LIGHT_GRAY);
			ImageDisplay.setLocation(0, 0);
			ImageDisplay.setLayout(null);
			
			con.fiMa.Log("");
			con.fiMa.Log("--------");
			con.fiMa.Log("");
			con.fiMa.Log(" TM ");
			
			con.fiMa.Log("");
			con.fiMa.Log(" Adding Process");
			con.fiMa.Log("");
			
			int PosX = 25;
			int PosY = 25;
			
			Border EmptyBorder = BorderFactory.createEmptyBorder();
			Border RedBorder = BorderFactory.createLineBorder(Color.RED);
			Border BlackBorder = BorderFactory.createLineBorder(Color.BLACK);
			
			TreeMap<String,ImageIcon> sortedData = new TreeMap<>();
			
			sortedData.putAll(data);
			
			for(Map.Entry<String,ImageIcon> entry : sortedData.entrySet()) {
				
				if(textureType.getSelectedItem().toString().equals(entry.getKey().replaceAll("[0-9]",""))) {
					
					if(!textureTypes.contains(entry.getKey().replaceAll("[0-9]",""))) { 
						textureTypes.add(entry.getKey().replaceAll("[0-9]",""));
						
						con.fiMa.Log("Added : -" + entry.getKey().replaceAll("[0-9]","") + "- to textureTypes");
						
						if(PosX!=25) {
							PosY += 133;
						}
						
						PosX = 25;
						
						
						JTextField TextureTypeText = new JTextField();
						TextureTypeText.setSize(125,25);
						TextureTypeText.setLocation(PosX,PosY);
						TextureTypeText.setEditable(false);
						TextureTypeText.setBackground(null);
						TextureTypeText.setBorder(null);
						TextureTypeText.setText(entry.getKey().replaceAll("[0-9]","") + " : ");
						
						ImageDisplay.add(TextureTypeText);
						
						PosY+=25;
					}
					
					JLabel Texture = new JLabel();
					Texture.setSize(100,100);
					Texture.setLocation(PosX, PosY);
					Texture.setBackground(Color.WHITE);
					Texture.setBorder(EmptyBorder);
					Texture.setText(entry.getValue().toString());
					Texture.addMouseListener(new MouseListener() {

						@Override
						public void mouseClicked(MouseEvent e) {
							
							Border currentBorder = Texture.getBorder();
							
							if(currentBorder.equals(RedBorder)) {
								Texture.setBorder(BlackBorder);
								uploadThis.remove(Texture.getText());
							}else {
								Texture.setBorder(RedBorder);
								uploadThis.add(Texture.getText());
							}
							
						}

						@Override
						public void mousePressed(MouseEvent e) {
							
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							
							Border currentBorder = Texture.getBorder();
							
							if(currentBorder.equals(EmptyBorder)) {
								Texture.setBorder(BlackBorder);
							}
							
						}

						@Override
						public void mouseExited(MouseEvent e) {
							
							Border currentBorder = Texture.getBorder();
							
							if(currentBorder.equals(BlackBorder)) {
								Texture.setBorder(EmptyBorder);
							}
							
						}
						
					});
					
					Image textureImage = entry.getValue().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
					
					
					Texture.setIcon(new ImageIcon(textureImage));
					
					con.fiMa.Log("");
					con.fiMa.Log("Key : " + entry.getKey());
					con.fiMa.Log("Image : " + entry.getValue());
					con.fiMa.Log(" Added ! ");
					con.fiMa.Log("");
					
					ImageDisplay.add(Texture);
					
					PosX+=133;
					
					if(PosX > 400) {
						PosX = 25;
						PosY+=133;
					}
				}
				
				
				
				}
			
			
			ImageDisplay.setPreferredSize(new Dimension(430,PosY+133));
			
			scrollPane = new JScrollPane(ImageDisplay);
			scrollPane.setSize(450,370);
			scrollPane.setLocation(25,25);
			scrollPane.getVerticalScrollBar().setUnitIncrement(16);
			
			
			Panel.add(scrollPane);
			
			ImageDisplay.revalidate();
			ImageDisplay.validate();
			ImageDisplay.repaint();
			
			scrollPane.revalidate();
			scrollPane.validate();
			scrollPane.repaint();
			
			Panel.revalidate();
			Panel.validate();
			Panel.repaint();
			
		}
		
		
		
		
		
		public JPanel getPanel() {
			return Panel;
		}
	
}
