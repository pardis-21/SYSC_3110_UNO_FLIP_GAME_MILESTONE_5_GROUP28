import java.util.ArrayList;

/**
 * The Player class represents a player in the UNO game.
 * Each player has a name and a hand of Card objects.
 * This class provides methods to view, play, and manage cards in the hand.
 * @Author Pulcherie Mbaye 101302394
 *
 */
public class Player {
    private final String name;
    private final ArrayList<Card> hand; //cards currently in the players hand

    /**
     * Constructs a new Player with the specified name and an empty hand.
     *
     * @param name the name of the player
     */

    public Player(String name){
        this.name = name;
        this.hand = new ArrayList<>();
    }

    /**
     * Gets the player's name.
     *
     * @return the name of the player
     */

    public String getName(){
        return name;
    }

    /**
     * Gets the list of cards currently in the player's hand.
     *
     * @return an ArrayList containing the player's cards
     */

    // get the players hand, it will return a list of cards the player is holding
    public ArrayList<Card> getHand(){
        return hand;
    }

    /**
     * Displays the player's hand in the console.
     * Each card is printed with its index for easier selection.
     *
     * @return a placeholder string (not used)
     */
    // show players hand in the console
    public String showHand(){
        for(int i =0; i< hand.size(); i++){
            System.out.println((i + 1) + "." + hand.get(i));
        }
        return " ";
    }


    //choosing the card to play
    public boolean playCard(int index, Card topCard, ArrayList<Card> discardPile){

        /**
         * checks that the player chose a valid card from their hand.
         * checks wheter that card can be legally played making sure colour/type matches
         * removes the played card from their hand
         * returns true/false depending on if they were able to play
         * @param index which card from the players hand example card 2
         * @param topCard the current top card on the discard pile
         * @param discardPile the list of all cards that have been played
         * @return true if the card was played successfully, false otherwise
         */
        if(index < 0 || index >= hand.size()){
            System.out.println(("invalid card number"));
            return false;
        }
        Card cardChosen = hand.get(index);

        if(cardChosen.playCardOnAnother(topCard)){
            hand.remove(index);
            discardPile.add(cardChosen);
            System.out.println(name + " played " + cardChosen);
            return true;
        }
        else{
            System.out.println("you can't play this card");
            return  false;
        }

    }

    /**
     * Checks if the player has no cards left.
     * Used to determine if the player has won the game.
     *
     * @return true if the player's hand is empty, {@code false} otherwise
     */
    public boolean hasZeroCard(){
        if(hand.isEmpty()){
            return true;
        }
        return false;
    }



}
