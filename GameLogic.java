import java.util.*;

public class GameLogic{
    private ArrayList<Player> players;
    private ArrayList<Card> cards;
    private ArrayList<Card> hand;
    private ArrayList<Card> discardPile;
    private ArrayList<Card> drawPile;
    private ArrayList<Card> flipPile;
    private boolean direction; //clockwise or counterclockwise
    private int score;


    public GameLogic(ArrayList<String> playerNames) {

        //creating an arrayList of players
        players = new ArrayList<>();


        //adding the elements of the player names to the list of players
        for  (int i = 0; i < playerNames.size(); i++){
            players.add(new Player(playerNames.get(i)));

        }

        hand = new ArrayList<>();
        discardPile = new ArrayList<>();

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
                for (Card card : drawPile){
                    player.getHand().add(card);

            }

            }
        }
    }

    private void addCardToPlayerHand(){


    }




}