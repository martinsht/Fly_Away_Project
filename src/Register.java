import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {
    private JPasswordField passwordField1;
    private JTextField usernametext;
    private JTextField nametext;
    private JButton signUpButton;
    private JPanel panel;
    private JComboBox comboBox1;

    public Register() {
        setSize(500, 500);
        setContentPane(panel);
        setVisible(true);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nametext.getText();
                String username = usernametext.getText();
                String password = passwordField1.getText();

                // Hash the password using BCrypt -we have to import the BCrypt library with Maven. (See the video tutorial in the assignment)
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                boolean allFieldsFilled = !usernametext.getText().isEmpty() &&
                        !nametext.getText().isEmpty() &&
                        !passwordField1.getText().isEmpty();
                if (allFieldsFilled) {
                    if (comboBox1.getSelectedIndex() == 0) {
                        String query = "INSERT INTO management.customer (name, username, password) VALUES (?, ?, ?)";

                        if (connect.add(name, username, hashedPassword, query)) // check if there is a success message on the console.
                        {
                            new login(); // show login page
                            setVisible(false); // hide register page
                        }

                    }
                    if (comboBox1.getSelectedIndex()==1){
                        String query = "INSERT INTO management.pilot (name, username, password) VALUES (?, ?, ?)";
                        if (connect.add(name, username, hashedPassword,query)) // check if there is a success message on the console.
                        {
                            new login(); // show login page
                            setVisible(false); // hide register page
                        }
                    }
                } else {
                    // Show a message prompting the user to fill in all the fields
                    JOptionPane.showMessageDialog(null, "Fill all the fields!");
                }
            }
        });
    }
}
