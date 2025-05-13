package pouilleux.view;

import pouilleux.controller.*;
import pouilleux.model.*;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Game game;

    public MainFrame(int displayMod) {
    	this.game = new Game();
    	
    	setBackground(new Color(0, 128, 0));
        setTitle("Pouilleux");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(0, 128, 0));

        //All the "sub-page" as JPanel:
        cardPanel.add(new TitleMenu(), "TitleScreen");
        cardPanel.add(new SettingMenu(), "SettingMenu");
        cardPanel.add(new LoadMenu(game), "LoadMenu");
        cardPanel.add(new NewGameMenu(game), "NewGameMenu");
        cardPanel.add(new GameScreen(game), "GameScreen");
        cardPanel.add(new EndGame(new Player()), "EndGameScreen");

        //Display mode:
        switch (displayMod) {
        	default:
        		break;
        	case 0:
        		break;
        	case 1:
        		setUndecorated(true);
                setResizable(false);
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                gd.setFullScreenWindow(this);
        		break;
        }
        
        //Load the page:
        setIconImage(new ImageIcon("resources/images/icon.png").getImage());
        setContentPane(cardPanel);
        setVisible(true);
    }
    
    /**
     * Classic show page without extra parameters.
     * @param name
     */
    public void showPage(String name) {
        cardLayout.show(cardPanel, name);
    }
    
    /**
     * Show page use for page which are needed a Game object.
     * @param name
     * @param game
     */
    public void showPage(String name, Game game) {
        this.game = game;
        cardPanel.add(new GameScreen(game), "GameScreen");
        cardLayout.show(cardPanel, name);
    }
    
    /**
     * Show page for page that need a Player object.
     * @param name
     * @param player
     */
    public void showPage(String name, Player player) {
        cardPanel.add(new EndGame(player), "EndGameScreen");
        cardLayout.show(cardPanel, name);
    }
}
