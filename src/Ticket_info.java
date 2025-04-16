import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Ticket_info extends JFrame{
    private JTable schedule;
    private JButton bookButton;
    private JPanel panel;
    private JLabel welcome;
    public static DefaultTableModel model;
    private ArrayList<String[]> tickets;

    public Ticket_info(User user) {
        setSize(500,500);
        setContentPane(panel);
        setVisible(true);
        model = new DefaultTableModel();
        schedule.setModel(model); // Attach model to table
        updateTable(user);
        welcome.setText("Welcome " + user.getName());
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new booking(user);
                setVisible(false);
            }
        });
    }
    private void updateTable (User user) {
        model.setRowCount(0);
        model.setColumnCount(0);
        tickets = connect.executeQuery("SELECT * FROM management.ticket where customer_id = "+user.getId());
        for (String[] flight : tickets) {
            model.addRow(flight); // Add each client's data as a new row
        }
    }
}
