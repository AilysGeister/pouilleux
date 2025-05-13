package pouilleux.model;

import java.util.ArrayList;

/**
 * Player class used to represent the players of the pouilleux game.
 */
public class Player {
	
	//Attributes:
	private int identifier;
	private String username;
	private ArrayList<Card> deck;
	private boolean isBot = true;
	private boolean inGame = true;
	
	//Constructors:
	/**
	 * This is the default constructor of a player in case there is no parameters in a call.
	 */
	public Player() {
		this.identifier = 0;
		this.username = "";
		this.deck = new ArrayList<Card>(52);
	}
	
	public Player(int identifier, String username, boolean isBot) {
		this.identifier = identifier;
		this.username = username;
		this.deck = new ArrayList<Card>(13);
		this.isBot = isBot;
	}
	
	//Methods:
	/**
	 * Return the identifier of the user.
	 * @return
	 */
	public int getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Return the username of the player.
	 * @return
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Return if the player is a bot.
	 * @return
	 */
	public boolean getIsBot() {
		return this.isBot;
	}
	
	/**
	 * Return if the player finish playing.
	 * @return
	 */
	public boolean getInGame() {
		return this.inGame;
	}
	
	/**
	 * Return the player's deck.
	 * @return
	 */
	public ArrayList<Card> getDeck() {
		return this.deck;
	}
	
	/**
	 * Return the Card object at the given index from the player's deck.
	 * @param index
	 * @return
	 */
	public Card getCard(int index) {
		return this.deck.get(index);
	}
	
	public int getDeckLength() {
		return this.getDeck().size();
	}
	
	/**
	 * Add the given Card object in the player's deck.
	 * @param card
	 */
	public void addCard(Card card) {
		this.deck.add(card);
	}
	
	/**
	 * Remove the asked card from the players' deck.
	 * @param index
	 */
	public void removeCard(int index) {
		this.deck.remove(index);
	}
	
	/**
	 * Discard the asked card.
	 * @param index
	 * @return
	 */
	public Card discard(int index) {
		Card temp = this.getCard(index);
		this.removeCard(index);
		return temp;
	}
	
	/**
	 * Set if the player still in the game.
	 * @param value
	 */
	public void setInGame(boolean value) {
		this.inGame = value;
	}
	
	/**
	 * Set the deck of the player.
	 * @param deck
	 */
	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}
	
	public boolean equals(Player player) {
		return this.identifier == player.getIdentifier() && this.username.equals(player.getUsername()) && this.deck.equals(player.getDeck());
	}
	
	/**
	 * Convert the player data to string with CSV format.
	 * @return
	 */
	public String toCSV() {
		String temp = new String();
		temp = this.getIsBot()+","+this.getUsername()+","+this.getInGame();
		ArrayList<Card> deck = this.getDeck();
		for (Card card: deck) {
			temp+=","+card.toStringCompact();
		}
		return temp;
	}
}
