package pouilleux.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Card {
	
	//Attributes:
	private int value;
	private String family;
	private Color color;
	
	//Constructors:
	/**
	 * Primitive constructor.
	 */
	public Card() {
		this.value = 0;
		this.family = null;
		this.color = null;
	}
	
	/**
	 * Constructor with all the parameters.
	 * @param value
	 * @param family
	 * @param color
	 */
	public Card(int value, String family, Color color) {
		this.value = value;
		this.family = family;
		this.color = color;
	}
	
	//Methods:
	/**
	 * Set the value of the card.
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Set the family of the card.
	 * @param family
	 */
	public void setFamily(String family) {
		this.family = family;
	}
	
	/**
	 * Set the color of the card.
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Return the value of the card.
	 * @return
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Return the family of the card.
	 * @return
	 */
	public String getFamily() {
		return this.family;
	}
	
	/**
	 * Return the color of the card.
	 * @return
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Test if two cards are equals.
	 * @param card
	 * @return
	 */
	public boolean equals(Card card) {
		return this.value == card.getValue() && this.family.equals(card.getFamily()) && this.color.equals(card.getColor());
	}
	
	/**
	 * Convert the card into a string.
	 */
	public String toString() {
		return new String(this.valueToString()+this.family);		
	}
	
	/**
	 * Return the object as "xf" where x the value and f the family first letter.
	 * @return
	 */
	public String toStringCompact() {
		//Initialize the variables:
		char family;
		String value;
		
		//Convert the integer value to the current card value:
		value=valueToString();
		
		//Reducing the family to the first letter:
		family = this.getFamily().charAt(0);
		family = Character.toUpperCase(family);
		
		//Returning the new char:
		return value+family;
	}
	
	/**
	 * Convert the value of the card by it's path to load the image.
	 * @return
	 */
	public String getPath() {
		return this.valueToString()+this.getFamily();
	}
	
	/**
	 * Convert the value of the card into a string corresponding to the classical french card.
	 * @return
	 */
	private String valueToString() {
		//Initialization:
		String temp = new String();
		
		//Convert the integer value to the current card value:
		if (this.value<=10) {
			temp = ""+this.value;
		} else {
			switch(this.value) {
				case 11:
					temp = "J";
					break;
				case 12:
					temp = "Q";
					break;
				case 13:
					temp = "K";
					break;
			}
		}
		return temp;
	}
	
	public static List<Integer> getPairsIndex(ArrayList<Card> deck) {
		//Initialization:
		List<Integer> pairIndices = new ArrayList<>();
		
		//Testing each cards:
        for (int i = 0; i < deck.size(); i++) {
            for (int j = i + 1; j < deck.size(); j++) {
                if (deck.get(i).value == deck.get(j).value && deck.get(i).color.equals(deck.get(j).color)) {
                	//We ignore the Jspades and the Jclubs:
                	if (!(deck.get(i).value==11 && deck.get(i).color.equals(Color.BLUE)) && !(deck.get(j).value==11 && deck.get(j).color.equals(Color.BLUE))) {
                		if (!pairIndices.contains(i)) pairIndices.add(i);
                        if (!pairIndices.contains(j)) pairIndices.add(j);
                	}
                }
            }
        }
        return pairIndices;
	}
}
