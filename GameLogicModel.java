import javax.swing.*;
import java.awt.*;
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
    public final ArrayList<Card> drawPile;
    private boolean roundEnded = false;
    //private ArrayList<Card> flipPile; for future use
    private boolean direction; //clockwise or counterclockwise
    public final Map<Player, Integer> scores = new HashMap<>();
    private static final int SEVEN = 7;
    private boolean turnCompleted = false;
    int numPlayers = 0;
    boolean lightMode = true;


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
                drawPile.get(0).getCardLightType() == Card.LightType.DRAW_ONE ||
                        drawPile.get(0).getCardLightType() == Card.LightType.WILD_DRAW2 ||
                        drawPile.get(0).getCardLightType() == Card.LightType.WILD ||
                        drawPile.get(0).getCardLightType() == Card.LightType.REVERSE ||
                        drawPile.get(0).getCardLightType() == Card.LightType.SKIP ||
                        drawPile.get(0).getCardLightType() == Card.LightType.FLIP_TO_DARK
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
        if(discardPile.isEmpty()) {
            return null;
        }
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
     * Handles the gameplay for a single player's turn, including:
     * - Drawing a card
     * - Playing a valid card
     * - Applying special card effects2
     *
     * - Checking for round completion
     */

    public void playGame(Card card) {

        if (lightMode) {
            if (card.getCardLightType().equals(Card.LightType.REVERSE)) {
                direction = !direction;
                playerOrder.getCurrentPlayer().getHand().remove(card);
                playerTurn();
                if (playerOrder.numPlayers == 2) {
                    playerTurn();
                }

            } else if (card.getCardLightType().equals(Card.LightType.SKIP)) {
                playerOrder.getCurrentPlayer().getHand().remove(card);
                playerTurn(); // skip this player
                JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has been skipped!");

                playerTurn();

            } else if (card.getCardLightType().equals(Card.LightType.WILD_DRAW2)) {
                playerOrder.getCurrentPlayer().getHand().remove(card);
                playerTurn();
                playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                drawPile.remove(0);
                playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                drawPile.remove(0);

                JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 2 cards and been skipped!");

                playerTurn();

            } else if (card.getCardLightType().equals(Card.LightType.DRAW_ONE)) {
                playerOrder.getCurrentPlayer().getHand().remove(card);

                playerTurn();
                playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                drawPile.remove(0);
                JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 1 card and been skipped!");

                playerTurn();

            } else if(card.getCardLightType().equals(Card.LightType.FLIP_TO_DARK)) {
                playerOrder.getCurrentPlayer().getHand().remove(card);
                flipSide();
                playerTurn();

            } else {
                playerOrder.getCurrentPlayer().getHand().remove(card);
            }

            discardPile.add(0, card);

            if (playerOrder.getCurrentPlayer().getHand().isEmpty()) {
                awardRoundPointsTo(playerOrder.getCurrentPlayer());
                return;
            }
        }
        else{
            if (card.getCardDarkType().equals(Card.DarkType.REVERSE)) {
                direction = !direction;
                playerOrder.getCurrentPlayer().getHand().remove(card);
                playerTurn();
                if (playerOrder.numPlayers == 2) {
                    playerTurn();
                }

            } else if (card.getCardDarkType().equals(Card.DarkType.DRAW_FIVE)) {
                playerOrder.getCurrentPlayer().getHand().remove(card);
                playerTurn(); // skip this player
                for(int i = 0; i<5; i++) {//draw 5 cards
                    playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                }
                JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 5 cards and been skipped!");
                playerTurn();

            } else if (card.getCardDarkType().equals(Card.DarkType.SKIP_ALL)){
                for (int i = 0; i<playerOrder.getAllPlayersToArrayList().size(); i++){
                    playerTurn();
                }
                JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has skipped all other players!");

            } else if(card.getCardDarkType().equals(Card.DarkType.WILD_DRAW_COLOUR)){
                playerOrder.getCurrentPlayer().getHand().remove(card);
                playerTurn();

            } else if(card.getCardDarkType().equals(Card.DarkType.FLIP_TO_LIGHT)){
                playerOrder.getCurrentPlayer().getHand().remove(card);
                flipSide();
                playerTurn();
            }

        }
    }






    /**
     * Awards points to the player who wins a round, based on other players' remaining cards.
     *
     * @param winner the player who won the current round
     */

    public int awardRoundPointsTo(Player winner) {
        int pointsgained = 0;
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            if (player != winner) {
                pointsgained += Card.pointsForHand(player.getHand());
            }
        }
        scores.put(winner, (scores.getOrDefault(winner, 0) + pointsgained));
        roundEnded = true;
        return pointsgained;
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


    /**
     * now asks "Is X an AI? (yes/no)".
     * If yes, it creates an AIPlayer, otherwise Player
     */
    public void initializePlayers(){
      //  int players = Integer.parseInt(userInput);
        while (true) {

            try {
                String userInput = JOptionPane.showInputDialog(null, "Enter the number of Players (2–4): ");
                if(userInput == null) return;
                numPlayers = Integer.parseInt(userInput);
                if (numPlayers < 2 || numPlayers > 4) {
                    JOptionPane.showMessageDialog(null, "Invalid number of players! Please enter 2–4.");
                } else
                    break;
            }
            catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Invalid number of players! Please enter 2–4.");
            }
        }

        while (playerOrder.getAllPlayersToArrayList().size() < numPlayers) {
            String playerName = JOptionPane.showInputDialog(null, "Enter player name");
            if(playerName == null) return;

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
                String isAI = JOptionPane.showInputDialog(null, "is " + playerName + " an AI? (yes/no)");
                boolean ai = isAI !=null && isAI.equalsIgnoreCase("yes");
                Player player;
                if (ai) {
                    player = new AIPlayer(playerName + " (AI)");
                } else {
                    player = new Player(playerName);
                }
                playerOrder.addPlayer(player);

            }
        }

        initScores();
    }

    /**
     * handles an ai players turn
     */

    public Card handleAIPlayer(AIPlayer ai) {

        if (getCurrentPlayer() != ai) {
            return null;
        }

        Card top = getTopCard();
        Card chosenCard = ai.chooseCardToPlay(top);

        // No playable card → draw
        if (chosenCard == null) {
            drawCardCurrentPlayer();
            return null;
        }

        // If it's a rainbow card, let AI set its colour first
        if (chosenCard.lightMode && chosenCard.getCardLightColour() == Card.LightColour.RAINBOW) {
            Card.LightColour chosenColour = ai.chooseBestLightColour();
            chosenCard.setCardLightColour(chosenColour.name());
        } else if (!chosenCard.lightMode && chosenCard.getCardDarkColour() == Card.DarkColour.RAINBOW) {
            Card.DarkColour chosenColour = ai.chooseBestDarkColour();
            chosenCard.setCardDarkColour(chosenColour.name());
        }

        tryPlayCard(chosenCard);
        return chosenCard;
    }





    public void drawCardCurrentPlayer() {
       if(!drawPile.isEmpty()){
           getCurrentPlayer().getHand().add(drawPile.get(0));
           drawPile.remove(0);
       }
        setTurnCompleted(true);


    }
    /**
     * Tries to play the given card from the current player's hand.
     * @param card the card the user clicked
     * @return true if the move was valid and applied, false otherwise
     */
    public boolean tryPlayCard(Card card) {
        Card top = getTopCard();

        // 1. Check play validity
        if (!card.playCardOnAnother(top)) return false;

        // 2. Remove from hand + place on discard
        getCurrentPlayer().getHand().remove(card);
        discardPile.add(0, card);

        // 3. Apply special effects IMMEDIATELY (only once!)
        applyCardEffect(card);

        // 4. If they emptied their hand, end the round
        if (getCurrentPlayer().getHand().isEmpty()) {
            awardRoundPointsTo(getCurrentPlayer());
        }

        setTurnCompleted(true);
        return true;
    }

    private void applyCardEffect(Card card) {

        if (lightMode) {
            switch (card.getCardLightType()) {

                case REVERSE:
                    direction = !direction;
                    playerOrder.getCurrentPlayer().getHand().remove(card);
                    playerTurn();
                    if (playerOrder.numPlayers == 2) {
                        playerTurn();
                    }
                    break;

                case SKIP:
                    playerOrder.getCurrentPlayer().getHand().remove(card);
                    playerTurn(); // skip this player
                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has been skipped!");

                    playerTurn();
                    break;

                case DRAW_ONE:
                    playerOrder.getCurrentPlayer().getHand().remove(card);

                    playerTurn();
                    playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                    drawPile.remove(0);
                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 1 card and been skipped!");

                    playerTurn();
                    break;

                case WILD_DRAW2:
                    playerOrder.getCurrentPlayer().getHand().remove(card);
                    playerTurn();
                    playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                    drawPile.remove(0);
                    playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                    drawPile.remove(0);

                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 2 cards and been skipped!");

                    playerTurn();
                    break;

                case FLIP_TO_DARK:
                    playerOrder.getCurrentPlayer().getHand().remove(card);
                    flipSide();
                    playerTurn();
                    break;
            }

        } else { // dark mode

            switch (card.getCardDarkType()) {

                case REVERSE:
                    direction = !direction;
                    playerTurn();
                    if (playerOrder.numPlayers == 2) {
                        playerTurn();
                    }
                    break;

                case DRAW_FIVE:
                    playerOrder.getCurrentPlayer().getHand().remove(card);
                    playerTurn(); // skip this player
                    for(int i = 0; i<5; i++) {//draw 5 cards
                        playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                    }
                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 5 cards and been skipped!");
                    playerTurn();
                    break;

                case SKIP_ALL:
                    for (int i = 0; i<playerOrder.getAllPlayersToArrayList().size(); i++){
                        playerTurn();
                    }
                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has skipped all other players!");
                    break;

                case WILD_DRAW_COLOUR:
                    playerOrder.getCurrentPlayer().getHand().remove(card);
                    playerTurn();
                    break;

                case FLIP_TO_LIGHT:
                    playerOrder.getCurrentPlayer().getHand().remove(card);
                    flipSide();
                    playerTurn();
                    break;
            }
        }

    }


    /**
     * handle ai player
     */


    public void flipSide() {
        // 1) Flip the global mode
        lightMode = !lightMode;

        // 2) Force every card to match the global mode
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            for (Card card : player.getHand()) {
                card.lightMode = lightMode;
            }
        }

        for (Card card : drawPile) {
            card.lightMode = lightMode;
        }

        for (Card card : discardPile) {
            card.lightMode = lightMode;
        }
    }






    public PlayerOrder getPlayerOrder(){
        return playerOrder;
    }

}