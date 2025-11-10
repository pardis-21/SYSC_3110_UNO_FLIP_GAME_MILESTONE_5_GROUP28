import javax.swing.*;
import java.util.*;
import java.util.Scanner;

/**
 * The GameLogic class manages the main gameplay flow for an UNO-like card game.
 * It controls player turns, card drawing, discarding, special card effects,
 * and scoring between multiple players.
 * @Author Charis Nobossi 101297742
 * @Author Pardis Ehsani 101300400
 * @Author Anvita Ala 101301514
 * @Author Pulcherie Mbaye 101302394
 */
public class GameLogicModel {
    private PlayerOrder playerOrder;
    private ArrayList<Card> cards;
    private final ArrayList<Card> discardPile;
    private final ArrayList<Card> drawPile;
    private boolean roundEnded = false;
    //private ArrayList<Card> flipPile; for future use
    private boolean direction; //clockwise or counterclockwise
    private final Map<Player, Integer> scores = new HashMap<>();
    private static final int SEVEN = 7;
    private boolean turnCompleted = false;


    //NEW GUI COMPONENTS ADDED
    private List<UnoView> views = new ArrayList<>();



    /**
     * Constructs a new GameLogic instance and initializes players, draw pile,
     * and default direction.
     *
     */
    public GameLogicModel() {

        //creating an arrayList of players



        //instance of that class
        playerOrder = new PlayerOrder();



        discardPile = new ArrayList<>();

        // for future use flipPile = new ArrayList<>();

        //populating the cards with a card to make a deck (108 cards)
        drawPile = new ArrayList<>();
        for (int i = 0; i < 108; i++){
            drawPile.add(new Card());
        }

        //assuming by UNO rules that all players have same age and starting from CW direction
        direction = true; //clockwise direction
        playerOrder = new PlayerOrder();
        direction = true;
    }



    public void addUnoViewFrame(UnoView view){
        views.add(view);
    }
    /**
     * Initializes the score map with each player's score set to 0.
     */
    public void initScores() {
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            scores.put(player, 0);
        }
    }

    /**
     * Deals seven cards to each player at the start of the game and ensures
     * the first discard card is not a special card.
     */
    //at the beginning of the game, each player is dealt 7 cards
    private void dealCardsBeginning(){
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            while (player.getHand().size() < SEVEN) {
                player.getHand().add(drawPile.get(0));
                drawPile.remove(0);
            }
        }
        //this is to make sure the top card isn't a special card
        while (
                drawPile.get(0).getCardType() == Card.Type.DRAW_ONE ||
                        drawPile.get(0).getCardType() == Card.Type.WILD_DRAW2 ||
                        drawPile.get(0).getCardType() == Card.Type.WILD ||
                        drawPile.get(0).getCardType() == Card.Type.REVERSE ||
                        drawPile.get(0).getCardType() == Card.Type.SKIP
        ) {
            Card topCard = drawPile.remove(0);
            drawPile.add(topCard);
        }

        discardPile.add(0, drawPile.remove(0));

    }

    /**
     * Returns the top card from the discard pile.
     *
     * @return the topCard currently on the discard pile
     */

    public Card getTopCard(){
        return discardPile.get(0);
    }

    public boolean isTurnCompleted() {
        return turnCompleted;
    }

    public void setTurnCompleted(boolean completed) {
        this.turnCompleted = completed;
    }
    /**
     * Starts a new round by dealing cards and displaying the initial top card.
     */

    public void startGame() {
        //starting round
        dealCardsBeginning();
        System.out.println();
        discardPile.get(0);
        System.out.println("Top Card: " + discardPile.get(0));
        System.out.println();

    }

    /**
     * Confirms that the current player is ready to take their turn.
     * Prompts the user to verify that the correct player is at the screen.
     */
    public void confirmPlayerAtScreen(){
        Scanner userInput= new Scanner(System.in);

        System.out.println("Player: " + playerOrder.getCurrentPlayer().getName() + "'s turn\n");
        Boolean flag = true;
        while (flag) {

            System.out.println("Is player: " + playerOrder.getCurrentPlayer().getName() + " at the screen?");
            String userChoice = userInput.nextLine();

            if (userChoice.equalsIgnoreCase("yes") || userChoice.equalsIgnoreCase("y")) {
                //playerTurn();
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

    /**
     * Handles the gameplay for a single player's turn, including:
     * - Drawing a card
     * - Playing a valid card
     * - Applying special card effects
     * - Checking for round completion
     */

    public void playGame() {
        Scanner userInput = new Scanner(System.in);

        System.out.println("These are your cards: ");
        boolean flag = true;
        while (flag) {
            System.out.println(playerOrder.getCurrentPlayer().showHand());
            System.out.println(playerOrder.getCurrentPlayer().getName()  + ": What is the index of the card you would like to play? (Or enter 0 to draw a card) ");


            try {
               int choice = userInput.nextInt();

                //if the player wants to draw a card
                if (choice == 0) {
                    playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                    System.out.println(playerOrder.getCurrentPlayer().getName() + " has drawn a card");

                    drawPile.remove(0);
                    playerTurn();
                    flag = false;
                }
                //check if input is valid but not 0
                else if (choice > 0 && choice <= playerOrder.getCurrentPlayer().getHand().size()) {
                    Card card = playerOrder.getCurrentPlayer().getHand().get(choice - 1);

                    if (card.playCardOnAnother(getTopCard())) {
                        if (card.getCardType().equals(Card.Type.REVERSE)) {
                            direction = !direction;
                            playerOrder.getCurrentPlayer().getHand().remove(card);
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has played the following card: " + card.getCardColour() + " " + card.getCardType());
                            System.out.println("Order of game has been reversed\n");

                        }
                        else if (card.getCardType().equals(Card.Type.SKIP)) {

                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has played the following card: " + card.getCardColour() + " " + card.getCardType());
                            playerOrder.getCurrentPlayer().getHand().remove(card);

                            //if direction is CW
                            if (direction) {
                                playerOrder.nextPlayerClockwise();
                                System.out.println( playerOrder.getCurrentPlayer().getName() + "'s turn has been skipped\n");

                            }
                            //if direction is CCW
                            else {
                                playerOrder.nextPlayerCounterClockwise();
                                System.out.println( playerOrder.getCurrentPlayer().getName() + "'s turn has been skipped\n");
                            }

                        }
                        else if (card.getCardType() == Card.Type.WILD) {
                            System.out.println("WILD card played. Enter the colour (RED, GREEN, BLUE, YELLOW):");

                            // If you previously used nextInt()/next() elsewhere, clear the leftover newline once:
                            if (userInput.hasNextLine()) userInput.nextLine();

                            flag = true;
                            while (flag) {
                                String colour = userInput.nextLine().trim().toUpperCase();

                                if (!colour.equals("RED") && !colour.equals("GREEN") &&
                                        !colour.equals("BLUE") && !colour.equals("YELLOW")) {
                                    System.out.println("Invalid colour. Please try again (RED, GREEN, BLUE, YELLOW):");
                                } else {
                                    card.setCardColour(colour); // if this expects String
                                    flag = false;
                                    playerOrder.getCurrentPlayer().getHand().remove(card);

                                }
                            }
                        }
                        else if (card.getCardType().equals(Card.Type.WILD_DRAW2)) {
                            System.out.println("WIlD DRAW2 card has been played. Enter the colour that will be played next: (RED, GREEN, BLUE, YELLOW)");
                            playerOrder.getCurrentPlayer().getHand().remove(card);

                            if (userInput.hasNextLine()) userInput.nextLine();

                            flag = true;
                            while (flag) {
                                String colour = userInput.nextLine().trim().toUpperCase();

                                if (!colour.equals("RED") && !colour.equals("GREEN") &&
                                        !colour.equals("BLUE") && !colour.equals("YELLOW")) {
                                    System.out.println("Invalid colour. Please try again (RED, GREEN, BLUE, YELLOW):");
                                } else {
                                    card.setCardColour(colour); // if setCardColour takes String
                                    flag = false;
                                }
                            }


                            playerTurn();
                            playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                            drawPile.remove(0);
                            playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                            drawPile.remove(0);
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has drawn 2 cards.");



                        }

                        else if (card.getCardType().equals(Card.Type.DRAW_ONE)) {
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has played the following card: " + card.getCardColour() + " " + card.getCardType());
                            playerOrder.getCurrentPlayer().getHand().remove(card);

                            playerTurn();
                            playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has drawn a card.");
                            drawPile.remove(0);


                        }
                        else{
                            System.out.println( playerOrder.getCurrentPlayer().getName() + " has played the following card: " +  card.getCardColour() + " " + card.getCardType());
                            playerOrder.getCurrentPlayer().getHand().remove(card);
                        }

                        discardPile.add(0, card);

                        if(playerOrder.getCurrentPlayer().getHand().isEmpty()){
                            awardRoundPointsTo(playerOrder.getCurrentPlayer());
                            return;
                        }

                        playerTurn();
                        flag = false;

                    }
                    else {
                        System.out.println("!!!Card cannot be played. Choose another or enter 0 to draw a card!!! ");
                        System.out.println();
                    }
                } else {
                    System.out.println("Invalid input. Try again");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Try again");
                userInput.nextLine();
            }
        }

    }

    /**
     * Awards points to the player who wins a round, based on other players' remaining cards.
     *
     * @param winner the player who won the current round
     */

    private void awardRoundPointsTo(Player winner) {
        int pointsgained = 0;
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            if (player != winner) {
                pointsgained += Card.pointsForHand(player.getHand());
            }
        }
        scores.put(winner, scores.getOrDefault(winner, 0) + pointsgained);
        System.out.println(winner.getName() + " earns " + pointsgained + " points! Total = " + scores.get(winner));
        roundEnded = true;
        return;

    }

    /**
     * Returns the player who has reached or exceeded the target score.
     *
     * @param target the score threshold required to win
     * @return the winning player, or null if no one has reached the target yet
     */
    public Player getMatchWinner(int target) {
        for (Player p : playerOrder.getAllPlayersToArrayList()) {
            Integer s = scores.get(p);
            if (s != null && s >= target){
                return p;
            }
        }
        return null;
    }

    /**
     * Advances the game to the next player's turn based on current direction.
     */
    public void playerTurn() {
        //checking the players turn status
        if (direction) {
            playerOrder.nextPlayerClockwise();
        }
        else {
            playerOrder.nextPlayerCounterClockwise();
        }
    }

    public boolean getDirection(){
        return direction;
    }

    /**
     * Sets the player order and ensures all players exist in the score map.
     *
     * @param playerOrder the plyerOrder instance managing turn order
     */
    public void setPlayerOrder(PlayerOrder playerOrder){
        this.playerOrder = playerOrder;
        for (Player p : playerOrder.getAllPlayersToArrayList()) {
            scores.putIfAbsent(p, 0);
        }
    }
    /**
    * Gets the player order
     */
    public Player getCurrentPlayer(){
        return playerOrder.getCurrentPlayer();
    }

    /**
     * Gets the player hand
     */
    public ArrayList<Card> getPlayerHand(){
        return getCurrentPlayer().getHand();
    }
    /**
     * Gets the total number of players
     */
    public int getTotalNumberOfPlayers(){
        return playerOrder.getAllPlayersToArrayList().size();
    }

    public void initializePlayers(){
        String userInput = JOptionPane.showInputDialog(null, "Enter the number of Players (2–4): ");
        int players = Integer.parseInt(userInput);
        int numPlayers = getTotalNumberOfPlayers();
        while (true) {

            try {
                if (numPlayers < 2 || numPlayers > 4) {
                    JOptionPane.showMessageDialog(null, "Invalid number of players! Please enter 2–4.");
                } else
                    break;
            }
            catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Invalid number of players! Please enter 2–4.");
            }
        }

        while (players < numPlayers) {
            String playerName = JOptionPane.showInputDialog(null, "Enter player name");

            //make sure 2 players by same name don't exist
            boolean exists = false;
            for (Player player : playerOrder.getAllPlayersToArrayList()) {
                if (player.getName().equals(playerName)) {
                    JOptionPane.showMessageDialog(null, "That player already exists!");
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                Player player = new Player(playerName);
                playerOrder.addPlayer(player);
            }
        }
    }


    /**
     * Runs a full UNO game session, starting rounds and continuing until
     * a player wins the round.
     */
    public void playUNOGame(){
        roundEnded = false;
        startGame();
        while(!roundEnded){
            confirmPlayerAtScreen();
            System.out.println("Top card: " + getTopCard() + "\n");
            playGame(); // this will set roundEnded = true when someone goes out
        }
    }


    public void drawCardCurrentPlayer() {
        getCurrentPlayer().getHand().add(drawPile.get(0));
        drawPile.remove(0);


    }
    /**
     * Tries to play the given card from the current player's hand.
     * @param card the card the user clicked
     * @return true if the move was valid and applied, false otherwise
     */
    public boolean tryPlayCard(Card card) {
        Card top = getTopCard();

        // try first, is this card playable?
        if (!card.playCardOnAnother(top)) {
            return false;
        }

        getCurrentPlayer().getHand().remove(card);

        discardPile.add(0, card);
        // need to implement special cards

        return true;
    }


}