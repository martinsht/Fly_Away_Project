import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class booking extends JFrame {
    private JPanel panel;
    private JButton bookButton;
    private JSpinner spinner1;
    private JTextField textField1;
    private JComboBox comboBox1;

    public booking(User user) {
        setSize(500,500);
        setContentPane(panel);
        setVisible(true);

        ArrayList<String> listofFlights = connect.destinations();
        for(String str: listofFlights){
           comboBox1.addItem(str);
        }

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textField1.getText().isEmpty()){
                    if (connect.book((String) comboBox1.getSelectedItem(),textField1.getText(),(int) spinner1.getValue(),user.getId(), user.getName())) // check if there is a success message on the console.
                    {
                        new Ticket_info(user);
                        setVisible(false); // hide booking page
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Fill all the fields!");
                }


            }
        });
    }
}
