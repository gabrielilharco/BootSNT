import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.TableColumn;

public class MainWindow extends JFrame {
    
	private static final long serialVersionUID = 1L;
    	
	public MainWindow(ButtonListener buttonListener) {
        super("Main Window");
        
        // select the Look and Feel
        try {
            com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default", "INSERT YOUR LICENSE KEY HERE", "Hyperlink DB");
        	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        // setup the widgets
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setLayout(null);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        
        String[] columnNames = {"Hyperlink", "Created", "Last edited"};
        
        ArrayList<String[]> datum = new ArrayList<String[]>();
        
        String[] entry;                
        for (Hyperlink hyperlink : DBHyperlink.select().values()) {
        	entry = new String[columnNames.length];
        	entry[0] = hyperlink.value;
        	entry[1] = hyperlink.created.toString();
        	entry[2] = hyperlink.lastEdited.toString();
        	datum.add(entry);
        }

        String[][] data = datum.toArray(new String[datum.size()][3]);
        
        /*String[][] data = new String[datum.size()][columnNames.length];
        
        int j = 0;
        for (String[] hyperlinkData : datum) {
        	data[j][0] = hyperlinkData[0];
        	data[j][1] = hyperlinkData[1];
        	data[j][2] = hyperlinkData[2];
        	j++;
        }*/
        
        
        JTable table = new JTable(data, columnNames);
        table.setBounds(10, 40, 420, 220);
        JScrollPane tablePanel = new JScrollPane(table);
        tablePanel.setBounds(table.getBounds());
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else {
                column.setPreferredWidth(40);
            }
        }        
        
        ImageIcon add = new ImageIcon("res/add.png");
        JButton addButton = new JButton(add);
        addButton.setBorder(null);
        addButton.setBounds(435, 62, 20, 20);
        addButton.setFocusPainted(false);
        contentPanel.add(addButton);
        
        ImageIcon edit = new ImageIcon("res/edit.png");
        JButton editButton = new JButton(edit);
        editButton.setBorder(null);
        editButton.setBounds(460, 62, 20, 20);
        editButton.setFocusPainted(false);
        contentPanel.add(editButton);
        
        ImageIcon del = new ImageIcon("res/del.png");
        JButton delButton = new JButton(del);
        delButton.setBorder(null);
        delButton.setBounds(488, 62, 15, 21);
        delButton.setFocusPainted(false);
        contentPanel.add(delButton);   
                
        JLabel name = new JLabel();
        name.setText("Boot SNT");
        name.setFont(new Font("Serif", Font.PLAIN, 20));
        name.setBounds(10, 8, 100, 20);
        
        JButton addNewButton = new JButton();
        addNewButton.setText("Add new");        
        addNewButton.setBounds(390, 270, 100, 30);
        addNewButton.setFocusPainted(false);
        addNewButton.addActionListener(buttonListener);
        
        contentPanel.add(addNewButton);
        contentPanel.add(name);
        contentPanel.add(tablePanel);
        
        setContentPane(contentPanel);
        
        // add the listeners
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // edit the window
        setLocation(32, 32);
        setSize(520, 340);
        setResizable(false);
    }
    	
	public void hideWindow() { setVisible(false); }
	public void showWindow() { setVisible(true);  }
	
    public static void main(String[] args) {
    	
    } // end main
    
}