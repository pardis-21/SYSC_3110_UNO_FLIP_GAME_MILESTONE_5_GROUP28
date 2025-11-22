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




}
