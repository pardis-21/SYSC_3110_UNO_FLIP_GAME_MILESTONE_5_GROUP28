import java.util.Random;

public class Card {
    private static final Random random = new Random();

    //INITIALIZE INFO FOR CARD TYPE
    public enum Type {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, DRAW_ONE, REVERSE, SKIP, WILD, WILD_DRAW2; // make each of the types

        private static final Type[] TYPE_VALUES = values(); // makes an array of all the Type values (list may be better)
        private static final int TYPE_SIZE = values().length; // length of ^

       //use random, return a type value
        public static Type randomType()  {
            return TYPE_VALUES[(random.nextInt(TYPE_SIZE))];
        }
    }
    //INITIALIZE INFO FOR CARD COLOUR
    public enum Colour{
        RED, GREEN, BLUE, YELLOW, RAINBOW; // all colour options

        private static final Colour[] COLOUR_VALUES = values(); // makes an array of all the Colour values (list may be better)
        private static final int COLOUR_SIZE = values().length; // length of ^

        public static Colour randomColour() {
            return COLOUR_VALUES[(random.nextInt(COLOUR_SIZE-1))]; // -1 because we don't want rainbow as an option for the random
        }
    }

    private Type cardType;
    private Colour cardColour;

    public Card(){
        cardType = Type.randomType();
        if (cardType == Type.WILD || cardType == Type.WILD_DRAW2){ // if the card type is a wild, the colour must be rainbow
            cardColour = Colour.RAINBOW;
        }
        else{
            cardColour = Colour.randomColour();
        }
    }

    public Type getCardType(){
        return cardType;
    }

    public Colour getCardColour() {
        return cardColour;
    }


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

    @Override
    public String toString() {
        return cardColour.toString() + " " + cardType.toString();
    }
}
