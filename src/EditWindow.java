import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class EditWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public Hyperlink currHyperlink;
	public JTextField hyperlinkField, commentField, metaTagField;
	public Map<Integer, Comment> commentMap;
	public Map<Integer, MetaTag> metaTagMap;
	public DefaultTableModel commentTableModel, metaTagTableModel;
	private ImageIcon /*saveIcon,*/ delIcon;
	public JTable commentTable, metaTagTable;    
    private String[] commentColumn = {"Comments", "Del"};   
    private String[] metaTagColumn = {"metaTags", "Del"};	 
	public TableColumn column;
	public JScrollPane commentPane, metaTagPane;
	public JPanel contentPanel;
	public ButtonListener buttonListener;
		
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
		
		this.buttonListener = buttonListener; 
		//saveIcon = new ImageIcon("res/save.png");
		delIcon = new ImageIcon("res/del.png");
		commentMap = DBComment.select();
		metaTagMap = DBMetaTag.select();
		
		currHyperlink = null;
		
		contentPanel = new JPanel(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));		
		
		JLabel name = new JLabel("Boot SNT");
	    name.setFont(new Font("Serif", Font.PLAIN, 20));
	    name.setBounds(10, 8, 100, 20);
	    
	    JLabel hyperlinkLabel = new JLabel("Hyperlink");
	    hyperlinkLabel.setFont(new Font("Serif", Font.PLAIN, 16));
	    hyperlinkLabel.setBounds(10, 42, 100, 20);
	    
	    hyperlinkField = new JTextField();
	    hyperlinkField.setBounds(100, 42, 200, 20);
	    
	    TextPrompt hyperlinkTP = new TextPrompt("Add new hyperlink...", hyperlinkField);
	    hyperlinkTP.changeAlpha(0.8f);
	    
	    contentPanel.add(hyperlinkLabel);
	    contentPanel.add(hyperlinkField);
	    
	    JLabel commentLabel = new JLabel("Comments");
	    commentLabel.setFont(new Font("Serif", Font.PLAIN, 16));
	    commentLabel.setBounds(10, 68, 100, 20);
	    
	    commentField = new JTextField(null);
	    commentField.setBounds(100, 69, 200, 20);
	    commentField.getDocument().addDocumentListener(new DocumentListener() {
	    	  public void changedUpdate(DocumentEvent e) { warn(); }
	    	  public void removeUpdate(DocumentEvent e) { warn(); }
	    	  public void insertUpdate(DocumentEvent e) { warn(); }
	    	  public void warn() {
	    	  }
	    });
	    
	    JButton addCommentButton = new JButton("Add");
	    addCommentButton.setName("addComment");
	    addCommentButton.setFocusable(false);
	    addCommentButton.setBounds(302, 69, 40, 20);
	    addCommentButton.addActionListener(buttonListener);
	    
	    TextPrompt commentTP = new TextPrompt("Add new comment...", commentField);
	    commentTP.changeAlpha(0.8f);
	    
	    contentPanel.add(commentLabel);
	    contentPanel.add(commentField);	    
	    contentPanel.add(addCommentButton);
	    
		JLabel metaTagLabel = new JLabel("Meta-tags");
	    metaTagLabel.setFont(new Font("Serif", Font.PLAIN, 16));
	    metaTagLabel.setBounds(10, 148, 100, 20);
	    
	    metaTagField = new JTextField(null);
	    metaTagField.setBounds(100, 148, 200, 20);
	    metaTagField.getDocument().addDocumentListener(new DocumentListener() {
	    	  public void changedUpdate(DocumentEvent e) { warn(); }
	    	  public void removeUpdate(DocumentEvent e) { warn(); }
	    	  public void insertUpdate(DocumentEvent e) { warn(); }
	    	  public void warn() {
	    	  }
	    });
	    
	    JButton addMetaTagButton = new JButton("Add");
	    addMetaTagButton.setName("addMetaTag");
	    addMetaTagButton.setFocusable(false);
	    addMetaTagButton.setBounds(302, 148, 40, 20);
	    addMetaTagButton.addActionListener(buttonListener);
	    
	    TextPrompt metaTagTP = new TextPrompt("Add new meta-tag...", metaTagField);
	    metaTagTP.changeAlpha(0.8f);   
        
	    contentPanel.add(metaTagLabel);
	    contentPanel.add(metaTagField);
	    contentPanel.add(addMetaTagButton);
	    
	    JButton backButton = new JButton("Back");
	    backButton.setName("back");
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
	    
		setLocation(500, 200);
        setSize(350, 300);
        setResizable(false);
	}
	
	public void removeAllTables() {
		hyperlinkField.setText(null);
		removeCommentTable();
		removeMetaTagTable();
	}
	
	public void createAllTables() {
		if (currHyperlink != null)
			hyperlinkField.setText(currHyperlink.value);
		createCommentTable();
		createMetaTagTable();
	}
	
	public void updateCommentTable() {
		removeCommentTable();
		createCommentTable();
	}
	
	public void createCommentTable() {
		commentTable = new JTable();
	    commentTable.setName("commentTable");
	    commentTableModel = new  DefaultTableModel(0, 0);
	    commentTableModel.setColumnIdentifiers(commentColumn);
	    commentTable.setModel(commentTableModel);
	    
	    //commentTable.getColumn("b1").setCellRenderer(new ButtonRenderer());
        //commentTable.getColumn("b1").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));

        commentTable.getColumn("Del").setCellRenderer(new ButtonRenderer());
        commentTable.getColumn("Del").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
                
	    commentTable.setTableHeader(null);
	    commentTable.setBounds(100, 93, 200, 42);
        commentPane = new JScrollPane(commentTable);
        commentPane.setBounds(commentTable.getBounds());
        column = null;
        for (int i = 0; i < commentColumn.length; i++) {
            column = commentTable.getColumnModel().getColumn(i);
            column.setResizable(false);
            if (i == 0) {
                column.setMaxWidth(180);
            }
            else {
                column.setMaxWidth(20);
            }
        }
        
        if (currHyperlink != null) {
        	for (Comment comment : currHyperlink.comments) {
        		if (comment != null) {
        			commentTableModel.addRow(new Object[] {comment.value, /*saveIcon,*/ delIcon});
        		}
        	}
        }
                
        contentPanel.add(commentPane);
	}
	
	public void removeCommentTable() {
        contentPanel.remove(commentPane);
	}
	
	public void updateMetaTagTable() {
		removeMetaTagTable();
		createMetaTagTable();
	}
	
	public void createMetaTagTable() {
		metaTagTable = new JTable();
	    metaTagTable.setName("metaTagTable");
	    metaTagTableModel = new  DefaultTableModel(0, 0);
	    metaTagTableModel.setColumnIdentifiers(metaTagColumn);
	    metaTagTable.setModel(metaTagTableModel);
	    
	    //metaTagTable.getColumn("b1").setCellRenderer(new ButtonRenderer());
        //metaTagTable.getColumn("b1").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
	    
        metaTagTable.getColumn("Del").setCellRenderer(new ButtonRenderer());
        metaTagTable.getColumn("Del").setCellEditor(new ButtonEditor(new JCheckBox(), this, buttonListener));
                
	    
	    metaTagTable.setTableHeader(null);
	    metaTagTable.setBounds(100, 172, 200, 42);
        metaTagPane = new JScrollPane(metaTagTable);
        metaTagPane.setBounds(metaTagTable.getBounds());
        column = null;
        for (int i = 0; i < metaTagColumn.length; i++) {
            column = metaTagTable.getColumnModel().getColumn(i);
            column.setResizable(false);
            if (i == 0) {
                column.setMaxWidth(180);
            }
            else {
                column.setMaxWidth(20);
            }
        }
        
        if (currHyperlink != null) {
        	for (MetaTag metaTag : currHyperlink.metaTags) {
        		if (metaTag != null)
        			metaTagTableModel.addRow(new Object[] {metaTag.value, /*saveIcon,*/ delIcon});
        	}
        }
        
        contentPanel.add(metaTagPane);
	}
	
	public void removeMetaTagTable() {
		contentPanel.remove(metaTagPane);
	}
	
	public void hideWindow() { setVisible(false); }
	public void showWindow() { setVisible(true);  }
	
	public static void main(String[] args) {
       
    } // end main
	
	
}
