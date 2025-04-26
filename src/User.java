import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class User {
    private int id;
    private String username;
    private String name;
    private String type;
    private InputStream img;

    public User(String username, String name, int id, InputStream img) {
        this.username = username;
        this.name = name;
        this.id = id;
        this.img = img;
    }

    public String getName() {
        return name;
    }
    public String getUsername(){
        return username;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public ImageIcon getImageIcon() {
        if(img != null) {
            try {
                // Convert InputStream to BufferedImage
                BufferedImage image = ImageIO.read(img);
                // Return ImageIcon from the BufferedImage
                return new ImageIcon(image);
            } catch (IOException e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }
        return null;
    }
}