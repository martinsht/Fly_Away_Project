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

    public booking() {
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


            }
        });
    }
}
