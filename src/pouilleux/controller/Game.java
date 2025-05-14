package pouilleux.controller;

import pouilleux.model.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
	//Attributes:
    private Round round;
    private int currentPlayerID;
    private int turnCount = 0;

    //Constructors:
    /**
	 * Constructor used to initialize the main frame.
	 */
	public Game() {
		this.round = new Round();
		this.currentPlayerID = 0;
		this.turnCount = 0;
	}	
	
	/**
	 * Constructor used to load the game from a save.
	 * @param path
	 */
	public Game(String path) {
		try {
			this.round = new Round(path);
	        this.currentPlayerID = 0;
		} catch (Exception e) {
			ViewManager.navigateTo("TitleScreen");
			System.out.println(PropretiesReader.getString("failLoad"));
		}
		this.turnCount = 0;
	}
	
	/**
	 * 
	 * @param players
	 */
    public Game(ArrayList<Player> players) {
        this.round = new Round(players);
        this.round.initialize();
        this.currentPlayerID = 0;
    }

    //Methods:
    /**
	* Method to initialize the game from the corresponding view.
	* @param numberOfPlayer
	* @param usernames
	* @return
	*/
	public void initialize(int numberOfPlayer, ArrayList<String> usernames) {
		//Initialization:
		ArrayList<Player> players = new ArrayList<Player>(4);
		String temp = new String();
		boolean isBot = true;
				
		for (int i=0; i<4; i++) {
			//We verify if the current player is a human or a bot:
			if (i <= numberOfPlayer) {
				isBot = false;
			} else {
				isBot = true;
			}

			//We verify that the current player has enter a user name or not:
			if (usernames.get(i).length() != 0) {
				temp=usernames.get(i);
			} else {
				//If the player is human and do not have a name we call it by his number:
				if (!isBot) {
					temp = "Player "+(i+1);
					//But if it is a bot we give him a preselected name:
				} else {
					temp=botName(i);
				}
			}
					
			//Once all the preparations and verifications done we create the player:
			players.add(new Player(i,temp,isBot));
		}
				
		//Once all the player are initialized we create the round:
		this.round = new Round(players);
		this.currentPlayerID = 0;
		this.round.initialize();
	}
	
	/**
	 * Return the turn count.
	 * @return
	 */
	public int getTurnCount() {
	    if (this.turnCount<=4) {
	    	return 1;
	    } else {
	    	return 1+this.turnCount/4;
	    }
	}
    
    /**
	 * Return a player that not the one who is playing by it's identifier.
	 * @param identifier
	 * @return The asked player.
	 */
	public Player getOtherPlayer(int identifier) {
		//We check which player is the current to not send him.
		if (this.currentPlayerID==identifier) {
			return this.round.getPlayer(0);
		} else { //If it's not the case we send the ask player.
			return this.round.getPlayer(identifier);
		}
	}
	
	/**
	 * Return the current player.
	 * @return
	 */
    public Player getCurrentPlayer() {
        return round.getPlayer(currentPlayerID);
    }

    /**
     * Return the discard.
     * @return
     */
    public ArrayList<Card> getDiscard() {
        return round.getDiscard();
    }

    /**
     * Return the current player identifier.
     * @return
     */
    public int getCurrentPlayerID() {
        return currentPlayerID;
    }

    /**
     * Return is the current player can discarding.
     * @return
     */
    public boolean canDiscard() {
        List<Integer> pairIndices = Card.getPairsIndex(getCurrentPlayer().getDeck());
        return !pairIndices.isEmpty();
    }

    /**
     * Main method that run the game.
     */
    public void nextTurn() {
    	//Initialization:
        Player current = getCurrentPlayer();

        //If the current player got pairs he discard them:
        List<Integer> pairIndices = Card.getPairsIndex(current.getDeck());
        if (!pairIndices.isEmpty()) {
            this.round.discarding(currentPlayerID, pairIndices);
        }

        //We verify that the player still have cards.
        checkFinish(current);

        //We verify that the game is not already over before continuing.
        if (checkGameOver()) {
        	//If it so we go to the end game screen that show the loser (so the only one who still have cards, the JSpades).
            ViewManager.navigateTo("EndScreen", getCurrentPlayer());
            return;
        }

        //We change the current player, by verifying who is still in game:
        this.currentPlayerID = round.getNextPlayerID(this.currentPlayerID);
        this.turnCount++; //The game run automatically the bot's turn, so we print the count turn to help the players to see how is going.
        ViewManager.navigateTo("GameScreen", this);

        //We verifying if there is still human player:
        if (onlyBotsRemain()) {
            playBotIfNeeded();
        } else {
            playBotIfNeeded();
        }
        
        //We verifying is the game isn't over:
        if (checkGameOver()) {
        	return;
        }
    }

    /**
     * Pick a card from an other player.
     */
    public void pick() {
    	//Initialization:
        Player current = getCurrentPlayer();
        int sourcePlayerID;

        //We get the identifier of the next player (verifying who still in game):
        sourcePlayerID = getPreviousValidPlayer(this.currentPlayerID);
        if (sourcePlayerID == -1) {
            nextTurn();
            return;
        }

        this.round.pickFrom(currentPlayerID, sourcePlayerID);

        //Verifying that the source player still have cards:
        checkFinish(current);

        //We verifying that the game is not over (every source we pick the last card have the last player expect the current one):
        if (checkGameOver()) {
        	//If it so it means that the current player have the JSpades, so he lose:
            ViewManager.navigateTo("EndScreen", getCurrentPlayer());
            return;
        }

        this.currentPlayerID = round.getNextPlayerID(currentPlayerID);
        this.turnCount++;
        ViewManager.navigateTo("GameScreen", this);

        playBotIfNeeded();
        if (checkGameOver()) return;
    }

    /**
     * Get in who's deck we are going to pick a card. To not pick in a empty one for ever and ever.
     * @param currentPlayerID
     * @return
     */
    private int getPreviousValidPlayer(int currentPlayerID) {
    	//Initialization:
        int countPlayers = this.round.getAllPlayers().size();
        int index = currentPlayerID;

        //We test each player:
        for (int i = 1; i < countPlayers; i++) {
            int candidateID = (index - i + countPlayers) % countPlayers;
            Player candidate = round.getPlayer(candidateID);
            //We verifying of the player still in the game:
            if (candidate.getInGame() && candidate.getDeckLength() > 0) {
                return candidateID;
            }
        }

        //If there is no one valid player it means the game is over:
        return -1;
    }

    /**
     * Find the loser when the game is over, from anywhere of the instances.
     * @return
     */
    private Player findLoser() {
    	//We test each players:
        for (Player player : round.getAllPlayers()) {
        	//We are looking for the Jspades:
            for (Card card : player.getDeck()) {
                if (card.getValue() == 11 && "spades".equalsIgnoreCase(card.getFamily()) && Color.BLUE.equals(card.getColor())) {
                	//If we find the card we have our loser:
                    return player;
                }
            }
        }
        //Return out of the loop (JVM need):
        return null;
    }

    /**
     * 
     */
    private void playBotIfNeeded() {
        Player current = getCurrentPlayer();

        if (current.getIsBot() && current.getInGame()) {
            //We reload the view to show what change for the humans players:
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            //Checking if the bot can discarding without picking:
            if (canDiscard()) {
            	//If it so we call nextTurn();
                nextTurn();
            } else {
            	//Otherwise the bot pick a card:
                pick();
            }
        }
    }

    /**
     * Return if a player still in the game.
     * @param player
     */
    private void checkFinish(Player player) {
        if (player.getDeckLength() == 0 && player.getInGame()) {
            player.setInGame(false);
        }
    }

    /**
     * Verifying if the game is over.
     * @return
     */
    private boolean checkGameOver() {
    	//Initialization:
    	//If all the humans players are discard all there cards, we stop the game and define the loser as the one who have the JSpades, it avoid the wait an eternity the end of the game
        boolean allHumansEmpty = onlyBotsRemain();
        int stillInGame = 4;
        
        //If there is only two players in the game the one who have the JSpades loose:
        for (Player player: this.round.getAllPlayers()) {
        	if (!player.getInGame()) {
        		stillInGame -= 1;
        	}
        }
        
        //If one of the conditions is true the game is over:
        if (allHumansEmpty || stillInGame == 2) {
        	//We search the loser due to findLoser():
            ViewManager.navigateTo("EndGameScreen", findLoser());
            return true;
        }

        //If no one conditions is filled it means that the game is not over:
        return false;
    }

    /**
     * Return if there still a human player in the game.
     * @return
     */
    private boolean onlyBotsRemain() {
        for (Player player : this.round.getAllPlayers()) {
            if (player.getInGame() && !player.getIsBot()) {
            	//There still a human in the game:
                return false;
            }
        }
        //There is only bots in the game:
        return true;
    }
    
    /**
	 * Save the current game from the view.
	 * @throws IOException 
	 */
	public void save() throws IOException {
		this.round.save();
	}
    
    /**
	 * Method that give a name to the bots.
	 * @param identifier
	 * @return
	 */
	private static String botName(int identifier) {
		String temp = new String();
		switch (identifier) {
			case 0:
				temp = "Fake Player"; //Normally the user cannot make a round with 4 bots, but if it the case we schedule it.
				break;
			case 1:
				temp = "J. Doe";
				break;
			case 2:
				temp = "Toto";
				break;
			case 3:
				temp = PropretiesReader.getString("leoLion");
				break;
		}
		return temp;
	}
}
