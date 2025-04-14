import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ticket_info extends JFrame{
    private JTable schedule;
    private JButton bookButton;
    private JPanel panel;
    private JLabel welcome;

    public Ticket_info(User user) {
        setSize(500,500);
        setContentPane(panel);
        setVisible(true);
        welcome.setText("Welcome " + user.getName());
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new booking();
            }
        });
    }
}
