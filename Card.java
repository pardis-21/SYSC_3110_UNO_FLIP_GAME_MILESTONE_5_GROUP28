import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Represents a card in UNO, with a type and colour.
 * Each card can be played based on matching colour or type rules.
 * @Author Anvita Ala 101301514
 * @Author Charis Nobossi 101297742
 * @Author Pulcherie Mbaye 101302394
 */
public class Card {
    private static final Random random = new Random();

    /**
     * Represents the different types of cards that can exist in the game.
     */
    //INITIALIZE INFO FOR CARD TYPE
    public enum Type {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DRAW_ONE, REVERSE, SKIP, WILD, WILD_DRAW2; // make each of the types

        private static final Type[] TYPE_VALUES = values(); // makes an array of all the Type values (list may be better)
        private static final int TYPE_SIZE = values().length; // length of ^

        /**
         * Returns a random type from the available card types.
         *
         * @return a randomly selected card type
         */
       //use random, return a type value
        public static Type randomType()  {
            return TYPE_VALUES[(random.nextInt(TYPE_SIZE))];
        }
    }

    /**
     * Represents the different colours a card can have.
     */
    //INITIALIZE INFO FOR CARD COLOUR
    public enum Colour{
        RED, GREEN, BLUE, YELLOW, RAINBOW; // all colour options

        private static final Colour[] COLOUR_VALUES = values(); // makes an array of all the Colour values (list may be better)
        private static final int COLOUR_SIZE = values().length; // length of ^


        /**
         * Returns a random colour but not RAINBOW.
         *
         * @return a randomly selected colour (not rainbow)
         */

        public static Colour randomColour() {
            return COLOUR_VALUES[(random.nextInt(COLOUR_SIZE-1))]; // -1 because we don't want rainbow as an option for the random
        }

    }

    private Type cardType;
    private Colour cardColour;

    /**
     * Constructs a new Card with a random type and colour.
     * Wild cards automatically receive the RAINBOW colour.
     */

    public Card(){
        cardType = Type.randomType();
        if (cardType == Type.WILD || cardType == Type.WILD_DRAW2){ // if the card type is a wild, the colour must be rainbow
            cardColour = Colour.RAINBOW;
        }
        else{
            cardColour = Colour.randomColour();
        }
    }

    /**
     * Returns the type of this card.
     *
     * @return the card type
     */
    public Type getCardType(){
        return cardType;
    }

    /**
     * Returns the colour of this card.
     *
     * @return the card colour
     */

    public Colour getCardColour() {
        return cardColour;
    }

    /**
     * Used to set background colours
     * @param colour
     * @return the Java.awt.Color equivalent of Colour.
     */
    public Color JavaCardColour(Colour colour){
        if(colour == Colour.RED){
            return Color.RED;
        }
        else if(colour == Colour.BLUE){
            return Color.BLUE;
        }
        else if(colour == Colour.GREEN){
            return Color.GREEN;
        }
        else if(colour == Colour.YELLOW){
            return Color.YELLOW;
        }
        else{
            return Color.WHITE;
        }
    }

    /**
     * Sets the colour of this card using a string name.
     *
     * @param colour the name of the colour to set
     * @throws IllegalArgumentException if the colour name is invalid
     */

    public void setCardColour(String colour) {
        this.cardColour = Card.Colour.valueOf(colour);
    }


    /**
     * Checks whether this card can be played on top of another card.
     *
     * @param topCard the card currently on top of the discard pile
     * @return true if this card can be played; false otherwise
     */
    //checks if this card can be played on top of another
    public boolean playCardOnAnother(Card topCard){
        // wild cards can be on top of anything
        if(this.cardColour == Colour.RAINBOW){
                return true;
        }
        // if it's not a wild card then the card being played must match the card in the discard pile by colour or type
        else if(this.cardColour == topCard.cardColour || this.cardType == topCard.cardType){
            return true;
        }
        return false;
    }


    /**
     * Returns the point value of a given card based on its type.
     *
     * @param c the card to evaluate
     * @return the point value of the card
     */
    public static int pointsForCard(Card c) {
        switch (c.getCardType()) {
            case ZERO:
                return 0;
            case ONE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;

            case REVERSE, SKIP, DRAW_ONE:
                return 20;

            case WILD, WILD_DRAW2:
                return 50;
        }
        return 0;
    }


    /**
     * Calculates the total point value of all cards in a hand.
     *
     * @param hand the list of cards in the player's hand
     * @return the total point value
     */
    public static int pointsForHand(List<Card> hand) {
        int sum = 0;
        for (Card c : hand) sum += pointsForCard(c);
        return sum;
    }


    /**
     * Returns a string representation of the card in the format:
     * "COLOUR TYPE"
     *
     * @return a string showing the card's colour and type
     */
    @Override
    public String toString() {
        return cardColour.toString() + " " + cardType.toString();
    }
}
