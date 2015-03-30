import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public JTextField hyperlinkField;
	public JTextField commentField;
	public JTextField metaTagField;
	public DefaultTableModel commentTableModel;
	public DefaultTableModel metaTagTableModel;
		
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
	    
	    hyperlinkField = new JTextField();
	    hyperlinkField.setBounds(100, 42, 200, 20);
	    
	    TextPrompt hyperlinkTP = new TextPrompt("Add new hyperlink...", hyperlinkField);
	    hyperlinkTP.changeAlpha(0.8f);
	    
	    contentPanel.add(hyperlinkLabel);
	    contentPanel.add(hyperlinkField);
	    
	    JLabel commentLabel = new JLabel();
	    commentLabel.setText("Comments");
	    commentLabel.setFont(new Font("Serif", Font.PLAIN, 16));
	    commentLabel.setBounds(10, 68, 100, 20);
	    
	    commentField = new JTextField();
	    commentField.setBounds(100, 69, 200, 20);
	    
	    TextPrompt commentTP = new TextPrompt("Add new comment...", commentField);
	    commentTP.changeAlpha(0.8f);
	    
	    String[] commentColumn = {"Comments"};    
	    JTable commentTable = new JTable();
	    commentTableModel = new  DefaultTableModel(0, 0);
	    commentTableModel.setColumnIdentifiers(commentColumn);
	    commentTable.setModel(commentTableModel);
	    
	    commentTable.setTableHeader(null);
	    commentTable.setBounds(100, 93, 200, 42);
        JScrollPane commentPanel = new JScrollPane(commentTable);
        commentPanel.setBounds(commentTable.getBounds());
        
        contentPanel.add(commentPanel);	    
	    contentPanel.add(commentLabel);
	    contentPanel.add(commentField);	    

		JLabel metaTagLabel = new JLabel();
	    metaTagLabel.setText("Meta-tags");
	    metaTagLabel.setFont(new Font("Serif", Font.PLAIN, 16));
	    metaTagLabel.setBounds(10, 148, 100, 20);
	    
	    metaTagField = new JTextField();
	    metaTagField.setBounds(100, 148, 200, 20);
	    
	    TextPrompt metaTagTP = new TextPrompt("Add new meta-tag...", metaTagField);
	    metaTagTP.changeAlpha(0.8f);
	    
	    String[] metaTagColumn = {"metaTags"};
	    JTable metaTagTable = new JTable();
	    metaTagTableModel = new  DefaultTableModel(0, 0);
	    metaTagTableModel.setColumnIdentifiers(metaTagColumn);
	    metaTagTable.setModel(metaTagTableModel);
	    
	    metaTagTable.setTableHeader(null);
	    metaTagTable.setBounds(100, 172, 200, 42);
        JScrollPane metaTagPanel = new JScrollPane(metaTagTable);
        metaTagPanel.setBounds(metaTagTable.getBounds());
        
        contentPanel.add(metaTagPanel);	    
	    contentPanel.add(metaTagLabel);
	    contentPanel.add(metaTagField);
	    
	    JButton backButton = new JButton();
	    backButton.setName("back");
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
	
	public void clearCommentRows() {
		int commentTableSize = commentTableModel.getRowCount();
		for (int i = 0; i < commentTableSize; i++) {
			commentTableModel.removeRow(i);
		}
		commentField.setText(null);
	}
	
	public void clearMetaTagRows() {
		int metaTagTableSize = metaTagTableModel.getRowCount();
		for (int i = 0; i < metaTagTableSize; i++) {
			metaTagTableModel.removeRow(i);
		}
		metaTagField.setText(null);
	}
	
	public void clearHyperlinkText() {
		hyperlinkField.setText(null);
	}
	
	public void clearAll() {
		clearCommentRows();
		clearMetaTagRows();
		clearHyperlinkText();
	}
	
	public void addCommentRows(Hyperlink hyperlink) {
		for (Comment comment : hyperlink.comments) {
			if (comment != null)
				commentTableModel.addRow(new String[] {comment.value});
		}
	}
	
	public void addMetaTagRows(Hyperlink hyperlink) {
		for (MetaTag metaTag : hyperlink.metaTags) {
			if (metaTag != null)
				metaTagTableModel.addRow(new String[] {metaTag.value});
		}
	}
	
	public void addAll(Hyperlink hyperlink) {
		hyperlinkField.setText(hyperlink.value);
		addCommentRows(hyperlink);
		addMetaTagRows(hyperlink);
	}
	
	public void hideWindow() { setVisible(false); }
	public void showWindow() { setVisible(true);  }
	
	public static void main(String[] args) {
       
    } // end main
	
	
}
