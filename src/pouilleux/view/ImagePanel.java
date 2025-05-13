package pouilleux.view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Class to show images in a JPanel.
 */
public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image backgroundImage;

	public ImagePanel(String path) {
		backgroundImage = new ImageIcon(path).getImage();
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
