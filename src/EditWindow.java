import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class EditWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public EditWindow(ButtonListener buttonListener) {
		super("Edit Window");
		
		 try {	            
			 com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default", "INSERT YOUR LICENSE KEY HERE", "Hyperlink DB");
	            
			 // select the Look and Feel
	        UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
	            
	     }	
		 catch (Exception ex) {
	    	 ex.printStackTrace();
	     }
		
		JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));		
		
		JLabel name = new JLabel();
	    name.setText("Boot SNT");
	    name.setFont(new Font("Serif", Font.PLAIN, 20));
	    name.setBounds(10, 8, 100, 20);
	    
	    JLabel hyperlinkLabel = new JLabel();
	    hyperlinkLabel.setText("Hyperlink");
	    hyperlinkLabel.setFont(new Font("Serif", Font.PLAIN, 16));
	    hyperlinkLabel.setBounds(10, 42, 100, 20);
	    
	    JTextField hyperlinkField = new JTextField();
	    hyperlinkField.setBounds(100, 42, 200, 20);
	    
	    TextPrompt hyperlinkTP = new TextPrompt("Add new hyperlink...", hyperlinkField);
	    hyperlinkTP.changeAlpha(0.8f);
	    
	    contentPanel.add(hyperlinkLabel);
	    contentPanel.add(hyperlinkField);
	    
	    JLabel commentLabel = new JLabel();
	    commentLabel.setText("Comments");
	    commentLabel.setFont(new Font("Serif", Font.PLAIN, 16));
	    commentLabel.setBounds(10, 68, 100, 20);
	    
	    JTextField commentField = new JTextField();
	    commentField.setBounds(100, 68, 200, 20);
	    
	    TextPrompt commentTP = new TextPrompt("Add new comment...", commentField);
	    commentTP.changeAlpha(0.8f);
	    
	    String[] columnNames = {"Names"};
	    Object[][] data = new Object[20][1];
	    JTable commentTable = new JTable(data, columnNames);
	    commentTable.setTableHeader(null);
	    commentTable.setBounds(100, 92, 200, 40);
        JScrollPane commentPanel = new JScrollPane(commentTable);
        commentPanel.setBounds(commentTable.getBounds());
        
        contentPanel.add(commentPanel);	    
	    contentPanel.add(commentLabel);
	    contentPanel.add(commentField);
	    
	    JLabel metatagLabel = new JLabel();
	    metatagLabel.setText("Meta-tags");
	    metatagLabel.setFont(new Font("Serif", Font.PLAIN, 16));
	    metatagLabel.setBounds(10, 148, 100, 20);
	    
	    JTextField metatagField = new JTextField();
	    metatagField.setBounds(100, 148, 200, 20);
	    
	    TextPrompt metatagTP = new TextPrompt("Add new meta-tag...", metatagField);
	    metatagTP.changeAlpha(0.8f);
	    
	    Object[][] data2 = new Object[5][1];
	    JTable metatagTable = new JTable(data2, columnNames);	    
	    metatagTable.setTableHeader(null);
	    metatagTable.setBounds(100, 172, 200, 40);
        JScrollPane metatagPanel = new JScrollPane(metatagTable);
        metatagPanel.setBounds(metatagTable.getBounds());
        
        contentPanel.add(metatagPanel);	    
	    contentPanel.add(metatagLabel);
	    contentPanel.add(metatagField);
	    
	    JButton backButton = new JButton();
	    backButton.setText("Back");        
        backButton.setBounds(230, 230, 100, 30);
        backButton.setFocusPainted(false);
        backButton.addActionListener(buttonListener);
		
	    contentPanel.add(name);
	    contentPanel.add(backButton);   
	    setContentPane(contentPanel);

	    addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
	    
		setLocation(350, 140);
        setSize(350, 300);
        setResizable(false);
	}
		
	public void hideWindow() { setVisible(false); }
	public void showWindow() { setVisible(true);  }
	
	public static void main(String[] args) {
       
    } // end main
	
	
}
