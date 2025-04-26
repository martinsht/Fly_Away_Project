import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Ticket_info extends JFrame{
    private JTable schedule;
    private JButton bookButton;
    private JPanel panel;
    private JLabel welcome;
    private JButton logOutButton;
    private JTextField searchtext;
    private JLabel imagelabel;
    private JLabel logo;
    public static DefaultTableModel model;
    private ArrayList<String[]> tickets;

    public Ticket_info(User user) {
        setSize(500,500);
        setContentPane(panel);
        setVisible(true);
        model = new DefaultTableModel();
        schedule.setModel(model); // Attach model to table
        welcome.setText("Welcome " + user.getName());
        ImageIcon icon = user.getImageIcon();
        if(icon != null) {
        imagelabel.setText("");
        imagelabel.setIcon(icon);
        }

        if (user.getType().equals("customer")){
        updateTable("SELECT * FROM management.ticket where customer_id = "+user.getId());
        }
        if (user.getType().equals("pilot")){
            updateTable("SELECT * FROM management.flight where pilot_id = "+user.getId());
        }
        bookButton.setEnabled(user.getType().equals("customer"));
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new booking(user);
                setVisible(false);
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Successfully logged out ", "Log Out Success", JOptionPane.INFORMATION_MESSAGE);
                new Welcome();
                setVisible(false);
            }
        });
        searchtext.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (user.getType().equals("customer")) {
                    updateTable("Select * from management.ticket where destination like '%"+searchtext.getText()+"%' and customer_id = "+user.getId()+";");
                }else if(user.getType().equals("pilot")){
                    updateTable("Select * from management.flight where arrival like '%"+searchtext.getText()+"%'and pilot_id = "+user.getId()+";");
                }
            }
        });
    }
    private void updateTable (String query) {
        model.setRowCount(0);
        model.setColumnCount(0);
        tickets = connect.executeQuery(query);
        for (String[] flight : tickets) {
            model.addRow(flight); // Add each client's data as a new row
        }
    }
}
