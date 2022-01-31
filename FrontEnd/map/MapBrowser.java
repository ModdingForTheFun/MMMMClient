package map;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Engine.Controller;

public class MapBrowser {


	
private JPanel Panel;

private Controller con;



private String LevelName = "";

private String[][] data;


private JComboBox<String> authorBox;

	public MapBrowser(Controller Con) {
		
		con = Con;
	
		Panel = new JPanel();
		Panel.setSize(720,480);
		Panel.setLocation(0, 0);
		Panel.setLayout(null);
		Panel.setOpaque(false);
		
		
		//Level List , fetch data from server
		data  = con.com.getLevelData(); 
		
		String[][] ownData = con.fiMa.getLevelData();
		
		String[][] updatedData = new String[data.length][6];
		
		int counter = 0;
		for(String[] Level : data) {
			
			updatedData[counter][0] = Level[0];
			updatedData[counter][1] = Level[1];
			updatedData[counter][2] = Level[2];
			updatedData[counter][3] = Level[3];
			updatedData[counter][4] = "No";
			updatedData[counter][5] = Level[4];
			
			for(String[] owndLevel : ownData) {
				
				if(Level[0].equals(owndLevel[0])) {
					
					updatedData[counter][4] = "Yes";
					
				}
				
			}
			
			counter ++;
			
		}
		
		data = updatedData;
		
		String[] columNames = {"Level Name" , "Author" , "Level Type" , "Length" , "Downloaded" , "Voting"};
		
		JTable levelTable = new JTable(data,columNames);
		levelTable.setDefaultEditor(Object.class, null);
		levelTable.setAutoCreateRowSorter(true);
		levelTable.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(levelTable);
		scrollPane.setSize(450,370);
		scrollPane.setLocation(25,25);
		
		levelTable.setFillsViewportHeight(true);
		
		
		
		
		//AUTHOT BOX
		
		authorBox = new JComboBox<String>();
		authorBox.setSize(125,25);
		authorBox.setLocation(25,80);
		authorBox.setEditable(false);
		authorBox.addItem("All");
		
		LinkedList<String> AuthorNames = new LinkedList<String>();
			
		int rowCount = levelTable.getRowCount();
			
		String AuthorName = "";
			
		while(rowCount > 0) {
				
			rowCount--;
				
			AuthorName = levelTable.getValueAt(rowCount,1).toString();
			
			if(!AuthorNames.contains(AuthorName)) {
				AuthorNames.add(AuthorName);
			}
				
		}
		
		for(String author : AuthorNames) {
			authorBox.addItem(author);
		}
		
		
		
		//Sorting , Details , Download
		
		JPanel options = new JPanel();
		options.setSize(175,370);
		options.setLocation(500,25);
		options.setLayout(null);
		options.setBackground(Color.LIGHT_GRAY);
		
		JTextField FilterText = new JTextField();
		FilterText.setSize(125,25);
		FilterText.setLocation(65,5);
		FilterText.setEditable(false);
		FilterText.setBackground(null);
		FilterText.setBorder(null);
		FilterText.setText("FILTER");
		
		JTextField SortText = new JTextField();
		SortText.setSize(125,25);
		SortText.setLocation(25,55);
		SortText.setEditable(false);
		SortText.setBackground(null);
		SortText.setBorder(null);
		SortText.setText("Author :");
		
		JTextField LevelTypeText = new JTextField();
		LevelTypeText.setSize(125,25);
		LevelTypeText.setLocation(25,105);
		LevelTypeText.setEditable(false);
		LevelTypeText.setBackground(null);
		LevelTypeText.setBorder(null);
		LevelTypeText.setText("LevelType :");
		
		JComboBox<String> LevelTypeBox = new JComboBox<String>();
		LevelTypeBox.setSize(125,25);
		LevelTypeBox.setLocation(25,130);
		LevelTypeBox.setEditable(false);
		LevelTypeBox.addItem("All");
		LevelTypeBox.addItem("Gimmick");
		LevelTypeBox.addItem("Puzzle");
		LevelTypeBox.addItem("Casual");
		LevelTypeBox.addItem("No Level");
		LevelTypeBox.addItem("Grind");
		LevelTypeBox.addItem("Panic");
		
		JTextField LengthText = new JTextField();
		LengthText.setSize(125,25);
		LengthText.setLocation(25,155);
		LengthText.setEditable(false);
		LengthText.setBackground(null);
		LengthText.setBorder(null);
		LengthText.setText("Length :");
		
		JComboBox<String> LengthBox = new JComboBox<String>();
		LengthBox.setSize(125,25);
		LengthBox.setLocation(25,180);
		LengthBox.setEditable(false);
		LengthBox.addItem("All");
		LengthBox.addItem("Short");
		LengthBox.addItem("Medium");
		LengthBox.addItem("Long");
		LengthBox.addItem("Infinite");
		
		
		JButton sort = new JButton();
		sort.setSize(125,25);
		sort.setLocation(25,285);
		sort.setText("Update List");
		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateLevelList(authorBox,LevelTypeBox,LengthBox,levelTable);
			}	
			
		});
		
		
		JButton downloadMap = new JButton();
		downloadMap.setSize(125,25);
		downloadMap.setLocation(25,315);
		downloadMap.setText("Download Map");
		downloadMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				LevelName = levelTable.getValueAt(levelTable.getSelectedRow(),0).toString();
				
				con.com.downloadLevel(LevelName);
				
			}
			
		});
		
		// buttons for up and down voting
		
		JButton UpVoteMap = new JButton();
		UpVoteMap.setSize(125,25);
		UpVoteMap.setLocation(25,225);
		UpVoteMap.setText("Up Vote");
		UpVoteMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				con.com.LevelName = levelTable.getValueAt(levelTable.getSelectedRow(),0).toString();
				
				con.com.addPacket("UpVoteMap");
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				UpdateLevelList(authorBox,LevelTypeBox,LengthBox,levelTable);
				
			}
			
		});
		
		JButton DownVoteMap = new JButton();
		DownVoteMap.setSize(125,25);
		DownVoteMap.setLocation(25,255);
		DownVoteMap.setText("Down Vote");
		DownVoteMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				con.com.LevelName = levelTable.getValueAt(levelTable.getSelectedRow(),0).toString();
				con.com.addPacket("DownVoteMap");
			
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				UpdateLevelList(authorBox,LevelTypeBox,LengthBox,levelTable);
			}
			
		});
		
		 
		// map info  350,150
		
		mapInfoFrame = new JFrame();
		mapInfoFrame.setSize(375,200);
		mapInfoFrame.setTitle("Map Info");
		mapInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mapInfoFrame.setLayout(null);
		mapInfoFrame.setLocationRelativeTo(null);
		if(con.com.isConnected()) {
			mapInfoFrame.setIconImage(con.asLo.IconOn.getImage());
		}else {
			mapInfoFrame.setIconImage(con.asLo.IconOff.getImage());
		}
		
		
		
		JPanel MIFpanel = new JPanel();
		MIFpanel.setSize(360,160);
		MIFpanel.setLayout(null);
		MIFpanel.setBackground(Color.BLACK);
		
		infoText = new JTextArea();
		infoText.setSize(350, 150);
		infoText.setLocation(5,5);
		infoText.setEditable(false);
		infoText.setLineWrap(true);
		infoText.setWrapStyleWord(true);
		
		MIFpanel.add(infoText);
		
		mapInfoFrame.setContentPane(MIFpanel);
		
		JButton mapInfo = new JButton();
		mapInfo.setSize(125,25);
		mapInfo.setLocation(25,30);
		mapInfo.setText("Map Info");
		mapInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				con.com.LevelName = levelTable.getValueAt(levelTable.getSelectedRow(),0).toString();
				
				mapInfoFrame.setTitle("Map Info : " + con.com.LevelName);
				
				con.com.addPacket("MapInfo");
				
			}
			
		});
		
		options.add(FilterText);
		options.add(mapInfo);
		options.add(SortText);
		options.add(authorBox);
		options.add(LevelTypeText);
		options.add(LevelTypeBox);
		options.add(LengthText);
		options.add(LengthBox);
		options.add(sort);
		options.add(downloadMap);
		options.add(UpVoteMap);
		options.add(DownVoteMap);
		
		Panel.add(scrollPane);
		Panel.add(options);
		
	}
	
	
	private JFrame mapInfoFrame;
	
	private JTextArea infoText;
	
	public void setInfoText(String info) {
		
		infoText.setText(info);
		mapInfoFrame.setVisible(true);
		
	}
	
	
	
	
	
	
	
	private void UpdateLevelList(JComboBox<String> authorBox,JComboBox<String> levelTypeBox,JComboBox<String> lengthBox,JTable levelTable) {
		
		String[][] ownData = con.fiMa.getLevelData();
		
		String[][] updatedData = new String[data.length][6];
		
		data = con.com.getLevelData();
		
		int counter = 0;
		for(String[] Level : data) {
			
			updatedData[counter][0] = Level[0];
			updatedData[counter][1] = Level[1];
			updatedData[counter][2] = Level[2];
			updatedData[counter][3] = Level[3];
			updatedData[counter][4] = "No";
			updatedData[counter][5] = Level[4];
			
			for(String[] owndLevel : ownData) {
				
				if(Level[0].equals(owndLevel[0])) {
					
					updatedData[counter][4] = "Yes";
					
				}
				
			}
			
			counter ++;
			
		}
		
		data = updatedData;
		
		LinkedList<String[]> dataList = new LinkedList<String[]>();
			
		for(String[] levelData : data) {
				
			if((levelData[1].equals(authorBox.getSelectedItem().toString())||authorBox.getSelectedItem().toString().equals("All"))
					&&
				(levelData[2].contains(levelTypeBox.getSelectedItem().toString())||levelTypeBox.getSelectedItem().toString().equals("All"))
					&&
				(levelData[3].contains(lengthBox.getSelectedItem().toString())||lengthBox.getSelectedItem().toString().equals("All"))) {
				
				
				dataList.add(levelData);
			}
				
		}
			
		String[][] finalData = new String[dataList.size()][6];
			
		int pos = 0;
		
		for(String[] levelData : dataList) {
			finalData[pos][0] = levelData[0];
			finalData[pos][1] = levelData[1];
			finalData[pos][2] = levelData[2];
			finalData[pos][3] = levelData[3];
			finalData[pos][4] = levelData[4];
			finalData[pos][5] = levelData[5];
			pos++;
		}
		
		
		String[] columNames = {"Level Name" , "Author" , "Level Type" , "Length" , "Downloaded" , "Voting"};
		
		TableModel updatedTable = new DefaultTableModel(finalData,columNames);
		
		levelTable.setModel(updatedTable);
		
	}
	
	
	
	public JPanel getPanel() {
		return Panel;
	}
	
	
	public String getLevelName() {
		return LevelName;
	}
	
	
	 
	
}
