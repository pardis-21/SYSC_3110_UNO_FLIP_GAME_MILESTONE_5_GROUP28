import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        // Load unobackground.jpg from the classpath (out directory)
        java.net.URL imgURL = getClass().getResource("unobackground.jpg");

        if (imgURL != null) {
            backgroundImage = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("ERROR: Could not find unobackground.jpg");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
