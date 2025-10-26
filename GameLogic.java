import java.util.*;

public class GameLogic{
    private LinkedList<Player> players;
    private ArrayList<Card> cards;
    private LinkedList<Card> discardPile;
    private ArrayList<Card> drawPile;
    private ArrayList<Card> flipPile;
    private boolean direction; //clockwise or counterclockwise
    private int score;
    private static final int SEVEN = 7;


    public GameLogic(ArrayList<String> playerNames) {

        //creating an arrayList of players
        players = new LinkedList<>();


        //adding players to doublylinkedlist




        discardPile = new LinkedList<>();

        flipPile = new ArrayList<>();

        //populating the cards with a card to make a deck (108 cards)
        drawPile = new ArrayList<>();
        for (int i = 0; i <= 108; i++){
            drawPile.add( new Card());
        }

        //assuming by UNO rules that all players have same age and strating from CW direction
        direction = true; //clockwise direction
        score = 0;
    }

    //at the beginning of the game, each player is dealt 7 cards
    private void dealCardsBeginning(){
        for (Player player : players) {
            if (player.getHand().size() == 7){
                break;
            }
            else {
                for (int i = 0; i < SEVEN; i++){
                    if (player.getHand().size() == 7){
                        break;
                    }
                    else {
                        player.getHand().add(drawPile.get(0));
                        drawPile.remove(drawPile.get(0));
                    }
            }

            }
        }
        discardPile.addFirst(drawPile.get(0));
        drawPile.remove(0);

    }

    private Card showTopCard(){
        return discardPile.get(0);
    }

    private void addCardToPlayerHand(){
        for (Player player : players){
            player.drawCard();
        }


    }

    public void playerTurn(){
        //checking the players turn status
        for (Player player : players){
            if (player.getPlayerTurnStatus() == true){

            }
            else {


            }

        }
    }

    public void startGame(){
        //starting round
        dealCardsBeginning();
        for (int i = 0; i < players.size(); i++){
            players.get(0).setPlayerTurnTrue();
        }

        }



    }


}