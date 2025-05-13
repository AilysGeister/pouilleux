package pouilleux;

import java.io.IOException;

import pouilleux.view.*;
import pouilleux.controller.*;;

/**
 * Class that run the game.
 */
public class Main {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame frame;
			try {
				frame = new MainFrame(Settings.getDisplayMod());
			} catch (IOException e) {
				frame = new MainFrame(0);
			}
            ViewManager.init(frame);
        });
	}
}
