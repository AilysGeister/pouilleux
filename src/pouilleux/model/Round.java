package pouilleux.model;

import java.util.*;
import java.io.*;
import java.awt.Color;

/**
 * Class that manage the model of a round of pouilleux game.
 */
public class Round {
	
	//Attributes:
	private ArrayList<Player> players = new ArrayList<Player>(4);
	private ArrayList<Card> discard = new ArrayList<Card>(52);
	
	//Constructors:
	/**
	 * Default constructor used the manipulate objects that need Round without initialize it.
	 */
	public Round() {
		//Initialization:
		ArrayList<Player> temp = new ArrayList<Player>(4);
		
		//Declares players:
		for (int i=0; i<4; i++) {
			temp.add(new Player());
		}
		
		//Affection:
		this.players = temp;
		this.discard = new ArrayList<Card>(52);
	}
	
	/**
	 * Classic constructor to create a new game from a player list created by the user.
	 * @param players The list of the player in the round.
	 */
	public Round(ArrayList<Player> players) {
		this.players.addAll(players);
	}
	
	/**
	 * Constructor used to create a round from an existing saved game.
	 * @param savePath
	 */
	public Round(String savePath) throws Exception {
		//Initialization:
	    this.players = new ArrayList<Player>();
	    this.discard = new ArrayList<Card>();
	    ArrayList<Card> deck;
	    Scanner save = new Scanner(new FileInputStream(savePath));

	    //Each line represent a player:
	    while (save.hasNextLine()) {
	        String line = save.nextLine();
	        String[] parts = line.split(",");
	        String valuePart, family;
            char familyCode;
            int value;
            Color color = null;

	        //The first part of the CSV line is the boolean isBot of Player class:
	        boolean isBot = Boolean.parseBoolean(parts[0]);
	        //The second part of the CSV line is the player's username:
	        String username = parts[1];
	        //The first part of the CSV line is the boolean isInGame of Player class:
	        boolean inGame = Boolean.parseBoolean(parts[2]);

	        //We initialize the player with the data that we get:
	        Player player = new Player(players.size(), username, isBot);
	        player.setInGame(inGame);
	        
	        //We initialize the deck:
	        deck = new ArrayList<Card>();

	        //The rest of the line represent the different cards of the player's deck: 
	        for (int i = 3; i < parts.length; i++) {
	            String cardStr = parts[i];
	            if (cardStr.length() < 2) continue;

	            //Preparing variables:
	            valuePart = cardStr.substring(0, cardStr.length() - 1);
	            familyCode = cardStr.charAt(cardStr.length() - 1);
	            family = null;
	            color = null;

	            //We indentifying the card's value:
	            switch (valuePart) {
	                case "J": 
	                	value = 11; 
	                	break;
	                case "Q": 
	                	value = 12; 
	                	break;
	                case "K": 
	                	value = 13; 
	                	break;
	                default: 
	                	value = Integer.parseInt(valuePart); 
	                	break;
	            }
	            
	            //We identifying the card's family:
	            switch (familyCode) {
	                case 'H':
	                    family = "hearts"; color = Color.RED; break;
	                case 'D':
	                    family = "diamonds"; color = Color.RED; break;
	                case 'S':
	                    family = "spades"; color = Color.BLUE; break;
	                case 'C':
	                    family = "clubs"; color = Color.BLUE; break;
	            }

	            //If the constructor doesn't recognize the card we created it:
	            if (family != null) {
	                deck.add(new Card(value, family, color));
	            }
	        }

	        //Create the player's deck:
	        player.setDeck(deck);
	        this.players.add(player);
	    }

	    //Close the file:
	    save.close();
	}

	
	//Methods:
	
	/**
	 * Initialize a constructed game by shuffle the cards and distributing them to the players.
	 */
	public void initialize() {
	    //Initialize and shuffle the cards:
	    ArrayList<Card> cards = newDeck();
	    Collections.shuffle(cards);
	    
	    // Distribute the cards to players
	    int cardsPerPlayer = 52 / players.size();
	    for (Player player : this.players) {
	        for (int i = 0; i < cardsPerPlayer; i++) {
	            player.addCard(cards.remove(0));
	        }
	    }
	}

	/**
	 * Return the player with the given index.
	 * @param identifier
	 * @return
	 */
	public Player getPlayer(int identifier) {
		return this.players.get(identifier);
	}
	
	/**
	 * Return the identifier of the next player.
	 * @param currentPlayerID
	 * @return
	 */
	public int getNextPlayerID(int currentPlayerID) {
		//Initialization:
		int nextPlayerID;
		boolean isOk = false;
		
		//if the current player is a the end of the list we go back to the beginning:
		if (currentPlayerID == 3) {
			nextPlayerID = 0;
		} else {
			nextPlayerID = currentPlayerID + 1;
		}
		
		//Verify if that player is still in the game:
		do {
			if (this.players.get(nextPlayerID).getInGame()) {
				//If it so we quit the loop and return the ID:
				isOk = true;
				//Otherwise we try the following player:
			} else {
				if (nextPlayerID == 3) {
					nextPlayerID = 0;
				} else {
					nextPlayerID += 1;
				}
			}
		} while (!isOk);
		
		//Once we get the good ID we return it:
		return nextPlayerID;
	}
	
	/**
	 * Return all the players in a ArrayList of Player object.
	 * @return
	 */
	public ArrayList<Player> getAllPlayers() {
		return this.players;
	}
	
	/**
	 * Return the discard of this round.
	 * @return
	 */
	public ArrayList<Card> getDiscard() {
		return this.discard;
	}
	
	/**
	 * Add a Card object in the discard.
	 * @param card
	 */
	public void addDiscard(Card card) {
		this.discard.add(card);
	}
	
	/**
	 * Remove the cards from the player's deck to put them in the discard.
	 * @param playerIdentifier
	 * @param cardsIndexes
	 */
	public void discarding(int playerIdentifier, List<Integer> cardsIndexes) {
		//Initialization:
        ArrayList<Card> source = this.getPlayer(playerIdentifier).getDeck();
        ArrayList<Card> temp = new ArrayList<>(52);

        //For each card of the deck we test if it is in a pair:
        for (int i = 0; i < source.size(); i++) {
            if (cardsIndexes.contains(i)) { //If it so we put it in the discard.
                this.discard.add(source.get(i));
            } else {
                temp.add(source.get(i)); //Otherwise we let it in the player's deck.
            }
        }

        //Once everything is done we modify the player's deck:
        this.players.get(playerIdentifier).setDeck(temp);
    }
	
	/**
	 * Pick a card from an other player's deck.
	 */
	public void pick(int currentPlayerID) {
		//Initialization:
		int sourcePlayerID, length, cardIndex;
		ArrayList<Card> source;
		ArrayList<Card> destination;
		Random random = new Random();
			
		//Checking the players ID:
		if (currentPlayerID==0) {
			sourcePlayerID = 3;
		} else {
			sourcePlayerID = currentPlayerID-1;
		}
				
		//Once we know which players are who we take the decks:
		source = this.getPlayer(sourcePlayerID).getDeck();
		destination = this.getPlayer(currentPlayerID).getDeck();
		length = source.size();
			
		//We take a card from the source to put it on destination:
		cardIndex = random.nextInt(length);
		destination.add(source.get(cardIndex));
		source.remove(cardIndex);
			
		//Once it is done we modify the new decks:
		this.getPlayer(currentPlayerID).setDeck(destination);;
		this.getPlayer(sourcePlayerID).setDeck(source);
	}
	
	public void pickFrom(int currentPlayerID, int sourcePlayerID) {
	    ArrayList<Card> source = this.getPlayer(sourcePlayerID).getDeck();
	    ArrayList<Card> destination = this.getPlayer(currentPlayerID).getDeck();

	    if (source.isEmpty()) return; // sécurité

	    int cardIndex = new Random().nextInt(source.size());
	    Card drawn = source.remove(cardIndex);
	    destination.add(drawn);

	    this.getPlayer(currentPlayerID).setDeck(destination);
	    this.getPlayer(sourcePlayerID).setDeck(source);
	}

	
	/**
	 * Save the game by converting it into a CSV file.
	 * @throws IOException
	 */
	public void save() throws IOException {
	    System.out.println("A");
	    StringBuilder fileName = new StringBuilder();
	    Date time = new Date();
	    String temp = time.toString(); // e.g. "Tue May 14 15:30:45 CEST 2024"

	    // Nettoyage du nom de fichier
	    for (int i = 0; i < temp.length(); i++) {
	        char c = temp.charAt(i);
	        if (c == ':' || c == ' ') {
	            fileName.append('-');
	        } else {
	            fileName.append(c);
	        }
	    }

	    File dir = new File("saves");
	    if (!dir.exists()) dir.mkdir(); // Crée le dossier s'il n'existe pas

	    FileWriter save = new FileWriter("saves/" + fileName + ".csv");
	    System.out.println("B");

	    for (Player player : this.players) {
	        save.write(player.toCSV() + "\n");
	    }

	    save.close();
	    System.out.println("Sauvegarde réussie dans " + fileName + ".csv");
	}

	
	/**
	 * Create a classic 52 cards game.
	 * @return
	 */
	private static ArrayList<Card> newDeck() {
	    // Initialize the variables:
	    ArrayList<Card> temp = new ArrayList<Card>(52);
	    String families[] = {"diamonds","hearts","clubs","spades"};
	    Color color = null;

	    // Set up the cards for a classic 52 cards deck.
	    for (String family : families) {
	        for (int i = 1; i <= 13; i++) { // Correction: 1 to 13
	            if (family.equals("diamonds") || family.equals("hearts")) {
	                color = Color.RED;
	            } else if (family.equals("clubs") || family.equals("spades")) {
	                color = Color.BLUE;
	            }
	            temp.add(new Card(i, family, color));
	        }
	    }
	    return temp;
	}
}
