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
		popUp.setSize((int)(225 * Controller.scale),(int)(150 * Controller.scale));
		popUp.setLocationRelativeTo(null);
		popUp.setTitle("");
		popUp.setIconImage(asLo.IconOff.getImage());
		popUp.setLayout(null);
		popUp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		popUp.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            	onClose();
            }
            
        });
		
		content = new JPanel();
		content.setSize((int)(900 * Controller.scale), (int)(950 * Controller.scale));
		content.setLocation(0, 0);
		content.setLayout(null);
		
		ReallyText = new JTextField();
		ReallyText.setFont(Controller.font);
		ReallyText.setSize((int)(500 * Controller.scale),(int)(25 * Controller.scale));
		ReallyText.setLocation((int)(50 * Controller.scale),(int)(10 * Controller.scale));
		ReallyText.setEditable(false);
		ReallyText.setBackground(null);
		ReallyText.setBorder(null);
		ReallyText.setText("");
		
		mapNameText = new JTextField();
		mapNameText.setFont(Controller.font);
		mapNameText.setSize((int)(500 * Controller.scale),(int)(25 * Controller.scale));
		mapNameText.setLocation((int)(50 * Controller.scale),(int)(40 * Controller.scale));
		mapNameText.setEditable(false);
		mapNameText.setBackground(null);
		mapNameText.setBorder(null);
		mapNameText.setText("");
		
		yes = new JButton();
		yes.setFont(Controller.font);
		yes.setSize((int)(125 * Controller.scale),(int)(25 * Controller.scale));
		yes.setLocation((int)(50 * Controller.scale),(int)(75 * Controller.scale));
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
		ReallyText.setLocation((int)(50 * Controller.scale) + warning[0].length(),(int)(10 * Controller.scale));
		
		mapNameText.setText(warning[2]);
		mapNameText.setLocation((int)(50 * Controller.scale) + warning[0].length(),(int)(40 * Controller.scale));
		
		yes.setText(warning[3]);
		yes.setLocation((int)(50 * Controller.scale) + extraLength / 4,(int)(75 * Controller.scale));
		
		content.revalidate();
		content.validate();
		content.repaint();
		
		popUp.setSize((int)(225 * Controller.scale) + extraLength2,(int)(150 * Controller.scale));
		
		popUp.setVisible(true);
	}
	
	
	private int onClose() {
		
		popUp.setVisible(false);
		popUp.dispose();
		
		return 0;
	}
	
	
	
}
