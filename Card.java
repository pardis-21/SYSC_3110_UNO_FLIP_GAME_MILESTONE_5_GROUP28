import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.List;


/**
 * Represents a card in UNO, with a type and colour.
 * Each card can be played based on matching colour or type rules.
 * @Author Anvita Ala 101301514
 * @Author Charis Nobossi 101297742
 * @Author Pulcherie Mbaye 101302394
 * @Author Pardis Ehsani 101300400
 */
public class Card implements Serializable {
    private static final Random random = new Random();



    //_____________________LIGHT SIDE CARD_________________________
    /**
     * Represents the different types of cards that can exist in the game.
     */
    //INITIALIZE INFO FOR CARD TYPE
    public enum LightType {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DRAW_ONE, REVERSE, SKIP, WILD, WILD_DRAW2, FLIP_TO_DARK; // make each of the types

        private static final LightType[] TYPE_VALUES = values(); // makes an array of all the Type values (list may be better)
        private static final int TYPE_SIZE = values().length; // length of ^

        /**
         * Returns a random type from the available card types.
         *
         * @return a randomly selected card type
         */
       //use random, return a type value
        public static LightType randomType()  {
            return TYPE_VALUES[(random.nextInt(TYPE_SIZE))];
        }
    }

    /**
     * Represents the different colours a card can have.
     */
    //INITIALIZE INFO FOR CARD COLOUR
    public enum LightColour {
        RED, GREEN, BLUE, YELLOW, RAINBOW; // all colour options

        public static final LightColour[] COLOUR_VALUES = values(); // makes an array of all the Colour values (list may be better)
        private static final int COLOUR_SIZE = values().length; // length of ^


        /**
         * Returns a random colour but not RAINBOW.
         *
         * @return a randomly selected colour (not rainbow)
         */
        public static LightColour randomColour() {
            return COLOUR_VALUES[(random.nextInt(COLOUR_SIZE-1))]; // -1 because we don't want rainbow as an option for the random
        }

    }

//_____________________DARK SIDE CARD_________________________
    public enum DarkType {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DRAW_FIVE, REVERSE, SKIP_ALL, WILD, WILD_DRAW_COLOUR, FLIP_TO_LIGHT; // make each of the types

        private static final DarkType[] TYPE_VALUES = values(); // makes an array of all the Type values (list may be better)
        private static final int TYPE_SIZE = values().length; // length of ^

        /**
         * Returns a random type from the available card types.
         *
         * @return a randomly selected card type
         */
        //use random, return a type value
        public static DarkType randomType()  {
            return TYPE_VALUES[(random.nextInt(TYPE_SIZE))];
        }
    }

    /**
     * Represents the different colours a card can have.
     */
    //INITIALIZE INFO FOR CARD COLOUR
    public enum DarkColour{
        ORANGE, TEAL, PINK, PURPLE, RAINBOW; // all colour options

        public static final DarkColour[] COLOUR_VALUES = values(); // makes an array of all the Colour values (list may be better)
        private static final int COLOUR_SIZE = values().length; // length of ^


        /**
         * Returns a random colour but not RAINBOW.
         *
         * @return a randomly selected colour (not rainbow)
         */
        public static DarkColour randomColour() {
            return COLOUR_VALUES[(random.nextInt(COLOUR_SIZE-1))]; // -1 because we don't want rainbow as an option for the random
        }

    }



    private LightType cardLightType;
    private DarkType cardDarkType;
    private LightColour cardLightColour;
    private DarkColour cardDarkColour;
    public boolean lightMode; //true if the current side is light

    /**
     * Constructs a new Card with a random type and colour.
     * Wild cards automatically receive the RAINBOW colour.
     */
    public Card(){
        lightMode = true;
        cardLightType = LightType.randomType();
        cardDarkType = DarkType.randomType();

        if (cardLightType == LightType.WILD || cardLightType == LightType.WILD_DRAW2){ // if the card type is a wild, the colour must be rainbow
            cardLightColour = LightColour.RAINBOW;
        }
        else{
            cardLightColour = LightColour.randomColour();
        }

        if (cardDarkType == DarkType.WILD || cardDarkType == DarkType.WILD_DRAW_COLOUR){ // if the card type is a wild, the colour must be rainbow
            cardDarkColour = DarkColour.RAINBOW;
        }
        else{
            cardDarkColour = DarkColour.randomColour();
        }
    }

    /**
     * Returns the light type of this card.
     *
     * @return the card type
     */
    public LightType getCardLightType(){
        return cardLightType;
    }

    /**
     * Returns the dark type of this card.
     *
     * @return the card type
     */
    public DarkType getCardDarkType(){
        return cardDarkType;
    }

    /**
     * Returns the light colour of this card.
     *
     * @return the card colour
     */
    public LightColour getCardLightColour() {
        return cardLightColour;
    }

    /**
     * Returns the dark colour of this card.
     *
     * @return the card colour
     */
    public DarkColour getCardDarkColour() {
        return cardDarkColour;
    }

    /**
     * Used to set background colours
     * @param colour
     * @return the Java.awt.Color equivalent of colour.
     */
    public Color JavaCardLightColour(LightColour colour){
        if(colour == LightColour.RED){
            return Color.RED;
        }
        else if(colour == LightColour.BLUE){
            return Color.BLUE;
        }
        else if(colour == LightColour.GREEN){
            return Color.GREEN;
        }
        else if(colour == LightColour.YELLOW){
            return Color.YELLOW;
        }
        else{
            return Color.WHITE;
        }

    }

    /**
     * Used to set background colours
     * @param colour
     * @return the Java.awt.Color equivalent of colour.
     */
    public Color JavaCardDarkColour(DarkColour colour){
        if(colour == DarkColour.ORANGE){
            return Color.ORANGE;
        }
        else if(colour == DarkColour.PINK){
            return Color.PINK;
        }
        else if(colour == DarkColour.TEAL){
            return Color.CYAN;
        }
        else if(colour == DarkColour.PURPLE){
            return Color.MAGENTA;
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
    public void setCardLightColour(String colour) {
        this.cardLightColour = LightColour.valueOf(colour);
    }

    public void setCardLightType(LightType lightType) {
        this.cardLightType = lightType;
    }



    /**
     * Sets the colour of this card using a string name.
     *
     * @param colour the name of the colour to set
     * @throws IllegalArgumentException if the colour name is invalid
     */
    public void setCardDarkColour(String colour) {
        this.cardDarkColour = DarkColour.valueOf(colour);
    }

    public void setCardDarkType(DarkType darkType) {
        this.cardDarkType = darkType;
    }


    /**
     * Checks whether this card can be played on top of another card.
     *
     * @param topCard the card currently on top of the discard pile
     * @return true if this card can be played; false otherwise
     */
    //checks if this card can be played on top of another
    public boolean playCardOnAnother(Card topCard){
        if (lightMode) {
            // wild cards can be on top of anything
            if (this.cardLightColour == LightColour.RAINBOW) {
                return true;
            }
            // if it's not a wild card then the card being played must match the card in the discard pile by colour or type
            else if (this.cardLightColour == topCard.cardLightColour || this.cardLightType == topCard.cardLightType) {
                return true;
            }
        }
        else{
            // wild cards can be on top of anything
            if (this.cardDarkColour == DarkColour.RAINBOW) {
                return true;
            }
            // if it's not a wild card then the card being played must match the card in the discard pile by colour or type
            else if (this.cardDarkColour == topCard.cardDarkColour || this.cardDarkType == topCard.cardDarkType) {
                return true;
            }
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
        if(c.lightMode) {
            switch (c.getCardLightType()) {
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

                case DRAW_ONE:
                    return 10;

                case REVERSE, SKIP:
                    return 20;

                case WILD:
                    return 40;

                case WILD_DRAW2:
                    return 50;
            }
        }
        else{
            switch (c.getCardDarkType()) {
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

                case REVERSE, DRAW_FIVE:
                    return 20;

                case SKIP_ALL:
                    return 30;

                case WILD:
                    return 50;

                case WILD_DRAW_COLOUR:
                    return 60;

            }
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
     * @return a string showing the card's colour and type depending on if it is light/dark side
     */
    @Override
    public String toString() {
        if (lightMode) {
            return cardLightColour.toString() + " " + cardLightType.toString();
        }
        else{
            return cardDarkColour.toString() + " " + cardDarkType.toString();
        }
    }
}
