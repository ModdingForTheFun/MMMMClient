package map;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Engine.Controller;

public class MapManager {

	
private JPanel Panel;

private Controller con;

	public MapManager(Controller Con) {
		
		con = Con;
	
		Panel = new JPanel();
		Panel.setSize(720,480);
		Panel.setLocation(0, 0);
		Panel.setLayout(null);
		Panel.setOpaque(false);
		
		
		//Level List
		String[][] data  = con.fiMa.getLevelData();
		
		String[] columNames = {"Level Name" , "Author" , "Level Type" , "Length"};
		
		JTable levelTable = new JTable(data,columNames);
		levelTable.setDefaultEditor(Object.class, null);
		levelTable.setAutoCreateRowSorter(true);
		levelTable.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(levelTable);
		scrollPane.setSize(450,370);
		scrollPane.setLocation(25,25);
		
		levelTable.setFillsViewportHeight(true);
		
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
		
		JTextField SortText = new JTextField();
		SortText.setSize(125,25);
		SortText.setLocation(25,50);
		SortText.setEditable(false);
		SortText.setBackground(null);
		SortText.setBorder(null);
		SortText.setText("Author :");
		
		JComboBox<String> authorBox = new JComboBox<String>();
		authorBox.setSize(125,25);
		authorBox.setLocation(25,75);
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
		
		
		
		
		
		
		JTextField LevelTypeText = new JTextField();
		LevelTypeText.setSize(125,25);
		LevelTypeText.setLocation(25,100);
		LevelTypeText.setEditable(false);
		LevelTypeText.setBackground(null);
		LevelTypeText.setBorder(null);
		LevelTypeText.setText("LevelType :");
		
		JComboBox<String> LevelTypeBox = new JComboBox<String>();
		LevelTypeBox.setSize(125,25);
		LevelTypeBox.setLocation(25,125);
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
		LengthText.setLocation(25,150);
		LengthText.setEditable(false);
		LengthText.setBackground(null);
		LengthText.setBorder(null);
		LengthText.setText("Length :");
		
		JComboBox<String> LengthBox = new JComboBox<String>();
		LengthBox.setSize(125,25);
		LengthBox.setLocation(25,175);
		LengthBox.setEditable(false);
		LengthBox.addItem("All");
		LengthBox.addItem("Short");
		LengthBox.addItem("Medium");
		LengthBox.addItem("Long");
		LengthBox.addItem("Infinite");
		
		
		
		
		
		
		
		JButton sort = new JButton();
		sort.setSize(125,25);
		sort.setLocation(25,250);
		sort.setText("Update List");
		sort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateLevelList(authorBox,LevelTypeBox,LengthBox,levelTable);
			}	
			
		});
		
		JButton storeMap = new JButton();
		storeMap.setSize(125,25);
		storeMap.setLocation(25,300);
		storeMap.setText("Store Map");
		
		JButton deleteMap = new JButton();
		deleteMap.setSize(125,25);
		deleteMap.setLocation(25,300);
		deleteMap.setText("Delete Map");
		deleteMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String levelName = levelTable.getValueAt(levelTable.getSelectedRow(),0).toString();
				
				con.fiMa.deleteMap(levelName);
				
			}
			
		});
		
		options.add(FilterText);
		options.add(SortText);
		options.add(authorBox);
		options.add(LevelTypeText);
		options.add(LevelTypeBox);
		options.add(LengthText);
		options.add(LengthBox);
		options.add(sort);
		options.add(deleteMap);
		
		Panel.add(scrollPane);
		Panel.add(options);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	private void UpdateLevelList(JComboBox<String> authorBox,JComboBox<String> levelTypeBox,JComboBox<String> lengthBox,JTable levelTable) {
	
		String[][] data  = con.fiMa.getLevelData();
		
		
		
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
			
		String[][] finalData = new String[dataList.size()][4];
			
		int pos = 0;
		
		for(String[] levelData : dataList) {
			finalData[pos][0] = levelData[0];
			finalData[pos][1] = levelData[1];
			finalData[pos][2] = levelData[2];
			finalData[pos][3] = levelData[3];
			pos++;
		}
		
		
		String[] columNames = {"Level Name" , "Author" , "Level Type" , "Length"};
		
		TableModel updatedTable = new DefaultTableModel(finalData,columNames);
		
		levelTable.setModel(updatedTable);
		
	}
	
	
	
	public JPanel getPanel() {
		return Panel;
	}
	
	
}
