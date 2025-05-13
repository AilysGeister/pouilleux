package pouilleux.view;

import pouilleux.controller.*;
import pouilleux.model.*;

import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.JLayeredPane;
import javax.swing.UIManager;

public class GameScreen extends JPanel {
	private Game currentGame;
	private static final long serialVersionUID = 1L;
	private JLayeredPane currentPlayerDeck = new JLayeredPane();
	private JLayeredPane discardPane = new JLayeredPane();
	
	/**
	 * Create the panel.
	 */
	public GameScreen(Game game) {
		this.currentGame=game;
		setLayout(new BorderLayout(0, 0));
		
		JPanel board = new JPanel();
		add(board, BorderLayout.CENTER);
		GridBagLayout gbl_board = new GridBagLayout();
		gbl_board.columnWidths = new int[]{1000, 0};
		gbl_board.rowHeights = new int[]{26, 564, 47, 0};
		gbl_board.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_board.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		board.setLayout(gbl_board);
		
		JPanel otherPlayersPanel = new JPanel();
		otherPlayersPanel.setBackground(UIManager.getColor("CheckBox.darkShadow"));
		GridBagConstraints gbc_otherPlayersPanel = new GridBagConstraints();
		gbc_otherPlayersPanel.anchor = GridBagConstraints.NORTH;
		gbc_otherPlayersPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_otherPlayersPanel.gridx = 0;
		gbc_otherPlayersPanel.gridy = 0;
		board.add(otherPlayersPanel, gbc_otherPlayersPanel);
		otherPlayersPanel.setLayout(new BoxLayout(otherPlayersPanel, BoxLayout.X_AXIS));
		
		//Other player 1:
		JPanel otherPlayer1 = this.otherPlayerPanel(1, game);
		otherPlayersPanel.add(otherPlayer1);
		
		//Other player 2:
		JPanel otherPlayer2 = this.otherPlayerPanel(2, game);				
		otherPlayersPanel.add(otherPlayer2);
		
		//Other player 3:
		JPanel otherPlayer3 = this.otherPlayerPanel(3, game);
		otherPlayersPanel.add(otherPlayer3);
		
		JPanel discardPanelParent = new JPanel();
		discardPanelParent.setBackground(new Color(51, 102, 0));
		GridBagConstraints gbc_discardPanelParent = new GridBagConstraints();
		gbc_discardPanelParent.fill = GridBagConstraints.BOTH;
		gbc_discardPanelParent.insets = new Insets(0, 0, 5, 0);
		gbc_discardPanelParent.gridx = 0;
		gbc_discardPanelParent.gridy = 1;
		board.add(discardPanelParent, gbc_discardPanelParent);
		discardPanelParent.setLayout(new BorderLayout(0, 0));
		
		//Discard:
		JLabel discardTitle = new JLabel(PropretiesReader.getString("discard")+":");
		discardTitle.setFont(new Font("Tahoma", Font.BOLD, 36));
		discardPanelParent.add(discardTitle, BorderLayout.NORTH);
		
		JPanel discardPanel = new ImagePanel("resources/images/board.png");
		discardPanel.setBackground(new Color(51, 102, 0));
		discardPanelParent.add(discardPanel, BorderLayout.CENTER);
		discardPanel.setLayout(null);
		
		this.discardPane = this.initializeDisplayCards(1);
		discardPanel.add(discardPane);
		
		
		//Current Player:
		JPanel currentPlayerPanel = new JPanel();
		GridBagConstraints gbc_currentPlayerPanel = new GridBagConstraints();
		gbc_currentPlayerPanel.fill = GridBagConstraints.BOTH;
		gbc_currentPlayerPanel.gridx = 0;
		gbc_currentPlayerPanel.gridy = 2;
		board.add(currentPlayerPanel, gbc_currentPlayerPanel);
		GridBagLayout gbl_currentPlayerPanel = new GridBagLayout();
		gbl_currentPlayerPanel.columnWidths = new int[] {145, 0, 1200};
		gbl_currentPlayerPanel.rowHeights = new int[] {145, 1};
		gbl_currentPlayerPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0};
		gbl_currentPlayerPanel.rowWeights = new double[]{1.0, 0.0};
		currentPlayerPanel.setLayout(gbl_currentPlayerPanel);
		
		ImagePanel currentPlayerImage = new ImagePanel("resources/images/player"+(game.getCurrentPlayerID()+1)+".png");
		GridBagConstraints gbc_currentPlayerImage = new GridBagConstraints();
		gbc_currentPlayerImage.insets = new Insets(0, 0, 5, 5);
		gbc_currentPlayerImage.fill = GridBagConstraints.BOTH;
		gbc_currentPlayerImage.gridx = 0;
		gbc_currentPlayerImage.gridy = 0;
		currentPlayerImage.setSize(32,32);
		currentPlayerPanel.add(currentPlayerImage, gbc_currentPlayerImage);
		
		JLabel currentPlayerUsername = new JLabel(this.currentGame.getCurrentPlayer().getUsername()); //current player name
		GridBagConstraints gbc_currentPlayerUsername = new GridBagConstraints();
		gbc_currentPlayerUsername.fill = GridBagConstraints.BOTH;
		gbc_currentPlayerUsername.insets = new Insets(0, 0, 5, 5);
		gbc_currentPlayerUsername.gridx = 1;
		gbc_currentPlayerUsername.gridy = 0;
		currentPlayerPanel.add(currentPlayerUsername, gbc_currentPlayerUsername);
		
		this.currentPlayerDeck = this.initializeDisplayCards(0);
		GridBagConstraints gbc_currentPlayerDeck = new GridBagConstraints();
		gbc_currentPlayerDeck.insets = new Insets(0, 0, 5, 5);
		gbc_currentPlayerDeck.fill = GridBagConstraints.BOTH;
		gbc_currentPlayerDeck.gridx = 2;
		gbc_currentPlayerDeck.gridy = 0;
		currentPlayerPanel.add(currentPlayerDeck, gbc_currentPlayerDeck);
		
		JButton actionButton = new JButton();
		Player current = this.currentGame.getCurrentPlayer();

		if (current.getDeckLength() == 0) {
		    // Le joueur ne peut que passer son tour
		    actionButton.setText(PropretiesReader.getString("next"));
		    actionButton.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            game.nextTurn();
		        }
		    });
		} else if (this.currentGame.canDiscard()) {
		    actionButton.setText(PropretiesReader.getString("discarding"));
		    actionButton.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            game.nextTurn();
		        }
		    });
		} else {
		    actionButton.setText(PropretiesReader.getString("pick"));
		    actionButton.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            game.pick();
		        }
		    });
		}
		GridBagConstraints gbc_actionButton = new GridBagConstraints();
		gbc_actionButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_actionButton.insets = new Insets(0, 0, 5, 5);
		gbc_actionButton.gridx = 4;
		gbc_actionButton.gridy = 0;
		currentPlayerPanel.add(actionButton, gbc_actionButton);
		
		
		//Tool bar:
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(new Color(218, 165, 32));
		add(toolBar, BorderLayout.NORTH);
		
		JButton saveButton = new JButton(PropretiesReader.getString("save"));
		saveButton.setBackground(new Color(184, 140, 27));
		saveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					game.save();
				} catch (IOException e1) {
					System.out.println(PropretiesReader.getString("saveFail"));
				}
			}
		});
		toolBar.add(saveButton);
		
		JButton titleScreenButton = new JButton(PropretiesReader.getString("titleScreen"));
		titleScreenButton.setBackground(new Color(184, 140, 27));
		titleScreenButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		titleScreenButton.setToolTipText(PropretiesReader.getString("warningUnsave"));
		titleScreenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewManager.navigateTo("TitleScreen");
			}
		});
		toolBar.add(titleScreenButton);
		
		JButton quitButton = new JButton(PropretiesReader.getString("quit"));
		quitButton.setToolTipText(PropretiesReader.getString("warningUnsave"));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quitButton.setBackground(new Color(184, 140, 27));
		quitButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		toolBar.add(quitButton);
		
		JLabel turnNumberJLabel = new JLabel(PropretiesReader.getString("turn")+ game.getTurnCount()); //$NON-NLS-1$
		toolBar.add(turnNumberJLabel);

	}
	
	
	/**
	 * Create a JPanel for the asked player which is not currently playing.
	 * @param identifier
	 * @return
	 */
	private JPanel otherPlayerPanel(int identifier, Game game) {
		//Initialization:
		JPanel otherPlayer = new JPanel();
		otherPlayer.setAlignmentX(0.0f);
		GridBagLayout gbl_otherPlayer = new GridBagLayout();
		gbl_otherPlayer.columnWidths = new int[] {64, 110, 30};
		gbl_otherPlayer.rowHeights = new int[] {64};
		gbl_otherPlayer.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_otherPlayer.rowWeights = new double[]{0.0};
		otherPlayer.setLayout(gbl_otherPlayer);
		
		//Get the image of the player:
		ImagePanel otherPlayerImage = new ImagePanel("resources/images/player"+(game.getOtherPlayer(identifier).getIdentifier()+1)+".png");
		GridBagConstraints gbc_otherPlayerImage = new GridBagConstraints();
		gbc_otherPlayerImage.insets = new Insets(0, 0, 5, 5);
		gbc_otherPlayerImage.fill = GridBagConstraints.BOTH;
		gbc_otherPlayerImage.gridx = 0;
		gbc_otherPlayerImage.gridy = 0;
		otherPlayer.add(otherPlayerImage, gbc_otherPlayerImage);
		
		//Get the name of the player:
		JLabel otherPlayerName = new JLabel(game.getOtherPlayer(identifier).getUsername());
		GridBagConstraints gbc_otherPlayerName = new GridBagConstraints();
		gbc_otherPlayerName.fill = GridBagConstraints.BOTH;
		gbc_otherPlayerName.insets = new Insets(0, 0, 5, 5);
		gbc_otherPlayerName.gridx = 1;
		gbc_otherPlayerName.gridy = 0;
		otherPlayer.add(otherPlayerName, gbc_otherPlayerName);
		
		//Show how many cards the player have:
		JLabel otherPlayerCardRemain = new JLabel(PropretiesReader.getString("cardRemain")+game.getOtherPlayer(identifier).getDeckLength());
		GridBagConstraints gbc_otherPlayerCardRemain = new GridBagConstraints();
		gbc_otherPlayerCardRemain.insets = new Insets(0, 0, 5, 0);
		gbc_otherPlayerCardRemain.gridx = 2;
		gbc_otherPlayerCardRemain.gridy = 0;
		otherPlayer.add(otherPlayerCardRemain, gbc_otherPlayerCardRemain);
		
		//Returning the new JPanel:
		return otherPlayer;
	}
	
	/**
	 * Create a JLayeredPane thats show the asked cards;
	 * @return
	 */
	private JLayeredPane initializeDisplayCards(int type) {
		//Initialization:
        JLayeredPane cardsPanel = new JLayeredPane();
        ArrayList<Card> deck = new ArrayList<Card>(52);
        int number=0;
        
        //Add ImagePanel objects in the JLayeredPane:
        int x = 0, y = 0, height=140, width=1000;
        int layer = 0;

       //We verify which type of deck we have to show:
        if (type == 0) { //0 is the current player's deck:
        	deck = this.currentGame.getCurrentPlayer().getDeck();
        } else {//Other case is the discard:
        	deck = this.currentGame.getDiscard();
        	height = 140*4;
        	width= 1920;
        }
        
        //Setting the bounds in function of the type:
        cardsPanel.setBounds(0, 0, width, height);
        
        //We generate a ImagePanel for each card in the deck:
        for (Card card : deck) {
        	ImagePanel imagePanel = new ImagePanel("resources/images/"+card.toString()+".png");
            imagePanel.setBounds(x, y, 87, 140);
            
			//Add the image in the JLayeredPane in a different layer:
            cardsPanel.add(imagePanel, Integer.valueOf(layer));
            
            //Adjust the position for the next image:
            if (type==0) { //If it is the player deck, we want to print all in one line.
            	x += 90; //width+offset
            } else { //If it is the discard we just want to print all the card, and we have enough space to show them all.
            	//If there is more than 16 cards we mad a new line:
                if (number>=16) {
                    y += 143;
                    x = 0; //width+offset
                    number = -1;
                } else {
                	x += 90; //width+offset
                }
            }
            
            layer++;
            number++;           
        }

        //Return the cards:
        return cardsPanel;
    }
}
