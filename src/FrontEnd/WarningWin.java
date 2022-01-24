package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Engine.Controller;

public class WarningWin{


public boolean chooise = false;

private JDialog popUp;

private JPanel content;
private JTextField ReallyText;
private JTextField mapNameText;
private JButton yes;

	public WarningWin(AssetLoader asLo) {
		
		
		popUp = new JDialog();
		popUp.setModal(true);
		popUp.setSize(225,150);
		popUp.setLocationRelativeTo(null);
		popUp.setTitle("");
		popUp.setIconImage(asLo.Icon.getImage());
		popUp.setLayout(null);
		popUp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		popUp.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            	onClose();
            }
            
        });
		
		content = new JPanel();
		content.setSize(900, 950);
		content.setLocation(0, 0);
		content.setLayout(null);
		
		ReallyText = new JTextField();
		ReallyText.setSize(500,25);
		ReallyText.setLocation(50,10);
		ReallyText.setEditable(false);
		ReallyText.setBackground(null);
		ReallyText.setBorder(null);
		ReallyText.setText("");
		
		mapNameText = new JTextField();
		mapNameText.setSize(500,25);
		mapNameText.setLocation(50,40);
		mapNameText.setEditable(false);
		mapNameText.setBackground(null);
		mapNameText.setBorder(null);
		mapNameText.setText("");
		
		yes = new JButton();
		yes.setSize(125,25);
		yes.setLocation(50,75);
		yes.setText("");
		yes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				chooise = true;
				popUp.dispose();
				
			}
			
		});
		
		content.add(ReallyText);
		content.add(mapNameText);
		content.add(yes);
		
		popUp.setContentPane(content);
		
		
	}
	
	
	
	public void DisplayWarning(String[] warning) {
		
		chooise = false;
		
		int extraLength1 = warning[1].length() * 4;
		int extraLength2 = warning[2].length() * 4;
		
		int extraLength = 0;
		
		if(extraLength1 > extraLength2) {
			extraLength = extraLength1;
		}else {
			extraLength = extraLength2;
		}
		
		popUp.setTitle(warning[0]);
		
		ReallyText.setText(warning[1]);
		ReallyText.setLocation(50 + warning[0].length(),10);
		
		mapNameText.setText(warning[2]);
		mapNameText.setLocation(50 + warning[0].length(),40);
		
		yes.setText(warning[3]);
		yes.setLocation(50 + extraLength / 4,75);
		
		content.revalidate();
		content.validate();
		content.repaint();
		
		popUp.setSize(225 + extraLength2,150);
		
		popUp.setVisible(true);
	}
	
	
	private int onClose() {
		
		popUp.setVisible(false);
		popUp.dispose();
		
		return 0;
	}
	
	
	
}
