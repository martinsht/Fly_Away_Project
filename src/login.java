import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame{
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton logInButton;
    private JPanel panel;
    private JButton clickHereButton;
    private JComboBox comboBox1;

    public login() {
        setSize(500, 500);
        setContentPane(panel);
        setVisible(true);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = null;

                if (comboBox1.getSelectedIndex() == 0) {
                    String query = "SELECT * FROM management.customer WHERE username = ?";
                    user = connect.login(textField1.getText(), passwordField1.getText(), query, "customer_id");
                    if (!(user ==null)){
                        user.setType("customer");}
                }
                if (comboBox1.getSelectedIndex() == 1) {
                    String query = "SELECT * FROM management.pilot WHERE username = ?";
                    user = connect.login(textField1.getText(), passwordField1.getText(), query, "pilot_id");
                    if (!(user ==null)){
                    user.setType("pilot");}
                }
                    if (user != null) {
                        JOptionPane.showMessageDialog(null, "Successfully logged in as " + user.getName(), "Login Success", JOptionPane.INFORMATION_MESSAGE);
                        new Ticket_info(user);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed. Invalid credentials.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }


        }});
        clickHereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
                setVisible(false);
            }
        });
    }
}
