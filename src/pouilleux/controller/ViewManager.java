package pouilleux.controller;

import pouilleux.view.MainFrame;
import pouilleux.model.*;

/**
 * COntroller to change the current page.
 */
public class ViewManager {
    private static MainFrame mainFrame;

    /**
     * Method to initialize the main frame that is used to show the pages.
     * @param frame
     */
    public static void init(MainFrame frame) {
        mainFrame = frame;
    }

    /**
     * Method to change page with no much parameters.
     * @param pageName
     */
    public static void navigateTo(String pageName) {
        mainFrame.showPage(pageName);
    }
    
    /**
     * Method to change page with passing a game controller.
     * @param pageName
     * @param game
     */
    public static void navigateTo(String pageName, Game game) {
        mainFrame.showPage(pageName, game);
    }

    /**
     * Method to change page with passing a player.
     * @param pageName
     * @param player
     */
    public static void navigateTo(String pageName, Player player) {
        mainFrame.showPage(pageName, player);
    }
}
