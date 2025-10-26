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

        for  (int i = 0; i < playerNames.size(); i++){
            players.add(new Player(playerNames.get(i)));

        }

        cards = new ArrayList<>();
        hand = new ArrayList<>();
        discardPile = new ArrayList<>();
        flipPile = new ArrayList<>();
        drawPile = new ArrayList<>();
        direction = true; //clockwise direction
        score = 0;
    }



}