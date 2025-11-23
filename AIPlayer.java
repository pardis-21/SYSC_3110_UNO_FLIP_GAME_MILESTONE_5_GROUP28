import java.util.ArrayList;


public class AIPlayer extends Player {

    public AIPlayer(String name){
        super(name);
    }
    // choosing the first valid card

    /**
     *
     * @param topCard top of discard pile
     * @return the chosen card to put down
     */

    public Card chooseCardToPlay(Card topCard){
        for (Card c: getHand()) {
            if (c.playCardOnAnother(topCard)) {
                return c;
            }
        }
        return null;
    }

    // these two functions below will help our ai choose which colour to set to when it get's a wild card
    public Card.LightColour chooseBestLightColour() {
        // Count colours in hand (ignore RAINBOW)
        int red = 0, green = 0, blue = 0, yellow = 0;

        for (Card c : getHand()) {
            if (!c.lightMode) continue;
            Card.LightColour col = c.getCardLightColour();
            switch (col) {
                case RED -> red++;
                case GREEN -> green++;
                case BLUE -> blue++;
                case YELLOW -> yellow++;
                default -> {} // RAINBOW: ignore
            }
        }

        // Pick the max; default to RED if all are 0
        Card.LightColour best = Card.LightColour.RED;
        int max = red;

        if (green > max) { max = green; best = Card.LightColour.GREEN; }
        if (blue  > max) { max = blue;  best = Card.LightColour.BLUE; }
        if (yellow > max) { max = yellow; best = Card.LightColour.YELLOW; }

        return best;
    }

    public Card.DarkColour chooseBestDarkColour() {
        int orange = 0, teal = 0, pink = 0, purple = 0;

        for (Card c : getHand()) {
            if (c.lightMode) continue;
            Card.DarkColour col = c.getCardDarkColour();
            switch (col) {
                case ORANGE -> orange++;
                case TEAL   -> teal++;
                case PINK   -> pink++;
                case PURPLE -> purple++;
                default -> {} // RAINBOW: ignore
            }
        }

        Card.DarkColour best = Card.DarkColour.ORANGE;
        int max = orange;

        if (teal   > max) { max = teal;   best = Card.DarkColour.TEAL; }
        if (pink   > max) { max = pink;   best = Card.DarkColour.PINK; }
        if (purple > max) { max = purple; best = Card.DarkColour.PURPLE; }

        return best;
    }




}
