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
    private BufferedImage img;

    public User(String username, String name, int id, InputStream image) {
        this.username = username;
        this.name = name;
        this.id = id;
        if (image != null) {
            try {
                this.img = ImageIO.read(image); // Load once!
            } catch (IOException e) {
                System.out.println("Error loading image: " + e.getMessage());
                this.img = null;
            }
        }    }

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
            return new ImageIcon(img);
        }
        return null;
    }
}