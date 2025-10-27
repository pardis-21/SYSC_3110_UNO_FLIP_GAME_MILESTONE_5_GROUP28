import java.util.*;
import java.util.Scanner;


public class GameLogic{
    private PlayerOrder playerOrder;
    private ArrayList<Card> cards;
    private LinkedList<Card> discardPile;
    private ArrayList<Card> drawPile;
    private ArrayList<Card> flipPile;
    private boolean direction; //clockwise or counterclockwise
    private int score;
    private static final int SEVEN = 7;


    public GameLogic(ArrayList<String> playerNames) {

        //creating an arrayList of players

        playerOrder = new PlayerOrder();

        //adding players to doublylinkedlist
        for (int i = 0; i < playerNames.size(); i++){
            Player player = new Player(playerNames.get(i));
            playerOrder.addPlayer(player);
        }



        discardPile = new LinkedList<>();

        flipPile = new ArrayList<>();

        //populating the cards with a card to make a deck (108 cards)
        drawPile = new ArrayList<>();
        for (int i = 0; i < 108; i++){
            drawPile.add( new Card());
        }

        //assuming by UNO rules that all players have same age and strating from CW direction
        direction = true; //clockwise direction
        score = 0;
    }

    //at the beginning of the game, each player is dealt 7 cards
    private void dealCardsBeginning(){
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
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

    private void playerNames(){
        for (Player player : playerOrder.getAllPlayersToArrayList()){
            System.out.println(player.getName());
        }
    }

    private void addCardToPlayerHand(){
        for (Player player : playerOrder.getAllPlayersToArrayList()){
            player.drawCard();
        }


    }

    public void playerTurn() {
        //checking the players turn status
        Player currentPlayer = playerOrder.getCurrentPlayer();
        System.out.println("It's " +  currentPlayer.getName() + "'s turn");

        if (direction == true) {
            playerOrder.nextPlayerClockwise();
        }
        else {
            playerOrder.nextPlayerCounterClockwise();
        }
    }

    public void startGame() {
        //starting round
        dealCardsBeginning();
        System.out.println("");
        System.out.println("Top Card: " + showTopCard());
        System.out.println("");
        System.out.println("Player: " + playerOrder.getCurrentPlayer().getName() + "'s turn");

    }

    public void confirmPlayerAtScreen(){
        Scanner userInput= new Scanner(System.in);

        Boolean flag = true;
        while (flag) {
            System.out.println("Is player: " + playerOrder.getCurrentPlayer().getName() + " at the screen?");
            String userChoice = userInput.nextLine();

            if (userChoice.equalsIgnoreCase("yes") || userChoice.equalsIgnoreCase("y")) {
                playerTurn();
                flag = false;
            }
            else if (userChoice.equalsIgnoreCase("no") || userChoice.equalsIgnoreCase("n")) {
                System.out.println("Pass the computer");
                //playerTurn();


            }
            else {
                System.out.println("Enter Yes or No");
            }

        }
    }

    public void playGame() {
        System.out.println("These are your cards: ");
        System.out.println(playerOrder.getCurrentPlayer().getHand());


    }


}