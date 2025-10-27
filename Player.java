import java.util.ArrayList;


public class Player {
    private final String name;
    private final ArrayList<Card> hand; //cards currently in the players hand
    private boolean playerTurn;

    public Player(String name){
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    // get the players hand, it will return a list of cards the player is holding
    public ArrayList<Card> getHand(){
        return hand;
    }
    // show players hand in the console
    public String showHand(){
        for(int i =0; i< hand.size(); i++){
            System.out.println((i + 1) + "." + hand.get(i));
        }
        return " ";
    }


    //public void drawCard(){
        //the pile to be infinite
        //GameLogic gamelogic = new GameLogic();
       // hand.add(GameLogic.getTopCard());
       //System.out.println(name + "drew a card: " + newCard);


    //}

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


    //if the player has one card left say uno if they have no cards left they win
    public boolean hasOneCard(){
        if(hand.size() == 1){
            return true;
        }
        return false;
    }

    public boolean hasZeroCard(){
        if(hand.isEmpty()){
            return true;
        }
        return false;
    }

    public void setPlayerTurnTrue() {
        playerTurn = true;
    }
    public void setPlayerTurnFalse() {
        playerTurn = false;
    }

    public boolean getPlayerTurnStatus(){
        return playerTurn;
    }

}
