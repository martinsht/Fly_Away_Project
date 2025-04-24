import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class Register extends JFrame {
    private JPasswordField passwordField1;
    private JTextField usernametext;
    private JTextField nametext;
    private JButton signUpButton;
    private JPanel panel;
    private JComboBox comboBox1;
    private JButton logInButton;
    private JButton selectButton;
    private JLabel imagelabel;

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
                // Convert the icon from JLabel to an InputStream (for image storage in the DB)
                ImageIcon icon = (ImageIcon) imagelabel.getIcon();
                InputStream imageInputStream = null;

                if (icon != null) {
                    // Create a BufferedImage to hold the icon
                    BufferedImage bufferedImage = new BufferedImage(
                            icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

                    // Create a Graphics object to draw the icon onto the BufferedImage
                    Graphics g = bufferedImage.createGraphics();
                    icon.paintIcon(null, g, 0, 0);
                    g.dispose();  // Dispose of the Graphics object once done

                    // Convert the BufferedImage to an InputStream
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                        imageInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                    } catch (IOException ex) {
                        // Show an error message if there's an issue converting the image to a binary stream
                        JOptionPane.showMessageDialog(null, "Error converting image to binary stream.");
                        return;  // Exit the method if there's an error
                    }
                }
                // Hash the password using BCrypt -we have to import the BCrypt library with Maven. (See the video tutorial in the assignment)
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                boolean allFieldsFilled = !usernametext.getText().isEmpty() &&
                        !nametext.getText().isEmpty() &&
                        !passwordField1.getText().isEmpty() && imagelabel.getIcon()!=null;
                if (allFieldsFilled) {
                    if (comboBox1.getSelectedIndex() == 0) {
                        String query = "INSERT INTO management.customer (name, username, password, image) VALUES (?, ?, ?, ?)";

                        if (connect.add(name, username, hashedPassword, imageInputStream, query)) // check if there is a success message on the console.
                        {
                            new login(); // show login page
                            setVisible(false); // hide register page
                        }

                    }
                    if (comboBox1.getSelectedIndex()==1){
                        String query = "INSERT INTO management.pilot (name, username, password) VALUES (?, ?, ?, ?)";
                        if (connect.add(name, username, hashedPassword, imageInputStream, query)) // check if there is a success message on the console.
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
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new login();
                setVisible(false);
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // Show the open dialog to the user and store the result (APPROVE_OPTION if a file is selected)
                int result = fileChooser.showOpenDialog(null);

                // If the user selects a file, proceed with loading and displaying the image
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // Read the image from the selected file
                        Image image = ImageIO.read(selectedFile);

                        // Scale the image to 100x100 pixels and create an ImageIcon
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));

                        // Set the ImageIcon to the label and clear any existing text
                        imagelabel.setIcon(icon);
                        imagelabel.setText("");
                    } catch (IOException ex) {
                        // Show an error message if there is an issue loading the image
                        JOptionPane.showMessageDialog(null, "Error loading image.");
                    }
                }
            }
        });
    }
}
