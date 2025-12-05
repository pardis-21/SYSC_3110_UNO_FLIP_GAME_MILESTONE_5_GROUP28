import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.util.*;

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
    public final ArrayList<Card> discardPile;
    public final ArrayList<Card> drawPile;
    private boolean roundEnded = false;
    private boolean direction; //clockwise or counterclockwise
    public final Map<Player, Integer> scores = new HashMap<>();
    private static final int SEVEN = 7;
    private boolean turnCompleted = false;
    private int numPlayers = 0;
    public boolean lightMode = true;

    //for the SAVING SNAP SHOT (working on this)
    private Deque<UNOGameStateSnapShot> undoStateSnapShot;
    private Deque<UNOGameStateSnapShot> redoStateSnapShot;


    /**
     * Constructs a new GameLogic instance and initializes players, draw pile,
     * and default direction.
     *
     */
    public GameLogicModel() {
        //saving snap shot
        this.redoStateSnapShot = new ArrayDeque<>();
        this.undoStateSnapShot = new ArrayDeque<>();

        //instance of that class
        playerOrder = new PlayerOrder();
        discardPile = new ArrayList<>();

        // for future use flipPile = new ArrayList<>();
        drawPile = new ArrayList<>();

        createDeckofCards();
        //shuffling cards for randomness
        Collections.shuffle(drawPile);

        //assuming by UNO rules that all players have same age and starting from CW direction
        direction = true; //clockwise direction
    }

    public ArrayList<Card> createDeckofCards(){
        drawPile.clear();
        //populating the cards with a card to make a deck (108 cards)
        for (int i = 0; i < 108; i++){
            drawPile.add(new Card());
        }
        return drawPile;
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
    public void dealCardsBeginning(){
        //if (drawPile.isEmpty()) return;

        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            while (player.getHand().size() < SEVEN) {
                if (drawPile.isEmpty()) break;
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

    /**
     * Determines if the players turn is complete to allow for the next player to move on
     * @return true or false depending on whether players turn is complete
     */
    public boolean isTurnCompleted() {
        return turnCompleted;
    }

    /**
     * Sets the turn complete if the player puts down the card to ensure that the next player can play
     * @param completed a boolean value, if true, the current players turn is complete, if false, the current player is still playing
     */
    public void setTurnCompleted(boolean completed) {
        this.turnCompleted = completed;
    }
    /**
     * Starts a new round by dealing cards and displaying the initial top card.
     */

    /**
     * Starting the game by dealing cards and showing the top card in the discard pile
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

    /**
     *
     * Playing the game in both light mode and dark mode for UNO FLIP
     * @param card to determine how the game can be played within UNO and if its a lightmode or darkmode game
     */
    public void playGame(Card card) {

        if (lightMode) {
            if (card.getCardLightType().equals(Card.LightType.REVERSE)) {
                direction = !direction;
                playerOrder.getCurrentPlayer().getHand().remove(card);
                if (getTotalNumberOfPlayers() == 2) {
                    playerTurn();
                }

            }
            else if (card.getCardLightType().equals(Card.LightType.SKIP)) {
                playerOrder.getCurrentPlayer().getHand().remove(card);
                playerTurn(); // skip this player

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
                if (getTotalNumberOfPlayers() == 2) {
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

            }
            //SKIP EVERY PLAYER CARD
            else if (card.getCardDarkType().equals(Card.DarkType.SKIP_ALL)){

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
        } else {
            playerOrder.nextPlayerCounterClockwise();
        }
        turnCompleted = false;
    }

    public boolean getDirection(){
        return direction;
    }

    public boolean setDirection(boolean direction){
        this.direction = direction;
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
        return numPlayers;
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

            if(!exists){
                int choice = JOptionPane.showConfirmDialog(null, " is " + playerName + " an AI? ", "Player Type", JOptionPane.YES_NO_OPTION);

                boolean ai = (choice == JOptionPane.YES_OPTION);

                Player player;
                if(ai){
                    player = new AIPlayer(playerName + "(AI)");
                } else{
                    player = new Player(playerName);
                }
                playerOrder.addPlayer(player);


            }

        }

        initScores();
    }

    /**
     * handles an AI players turn
     * @return the card the AI player plays
     */
    public Card handleAIPlayer(AIPlayer ai) {

        if (getCurrentPlayer() != ai) {
            return null;
        } else {
            Card top = getTopCard();
            Card chosenCard = ai.chooseCardToPlay(top);

            // No playable card so draw
            if (chosenCard == null) {
                //draw a card
                Card drawn = drawOneorNullCard();
                //if there are more cards to draw from draw pile, then ai player will draw a card
                if (drawn != null) {
                    //adds the drawed card into its hand
                    ai.getHand().add(drawn);
                }
                setTurnCompleted(true);
                playerTurn();
                //ending ai turn and going to next player
                setTurnCompleted(false);
                //playerTurn();
                return chosenCard;
            }

            // If it's a rainbow card, let AI set its colour first
            if (chosenCard.lightMode && chosenCard.getCardLightColour() == Card.LightColour.RAINBOW) {
                Card.LightColour chosenColour = ai.chooseBestLightColour();
                chosenCard.setCardLightColour(chosenColour.name());

            } else if (!chosenCard.lightMode && chosenCard.getCardDarkColour() == Card.DarkColour.RAINBOW) {
                Card.DarkColour chosenColour = ai.chooseBestDarkColour();
                chosenCard.setCardDarkColour(chosenColour.name());
            }

            boolean success = tryPlayCard(chosenCard);
            if (!success) {
                // optional: draw a card instead of silently skipping
                Card drawn = drawOneorNullCard();
                if (drawn != null) {
                    ai.getHand().add(drawn);
                    JOptionPane.showMessageDialog(null, ai.getName() + " had no valid moves and drew a card!");
                }
                setTurnCompleted(true);
                playerTurn();
                setTurnCompleted(false);
                return null;
            }

            JOptionPane.showMessageDialog(null, ai.getName() + " played " + chosenCard.toString());

// Now decide if we need to move the turn further or not:
            if (lightMode) {
                switch (chosenCard.getCardLightType()) {
                    case REVERSE, SKIP, DRAW_ONE, WILD_DRAW2, FLIP_TO_DARK -> {
                        // turn already advanced appropriately in applyCardEffect
                    }
                    default -> {
                        // Simple card: just go to next player
                        playerTurn();
                    }
                }
            } else {
                switch (chosenCard.getCardDarkType()) {
                    case REVERSE, DRAW_FIVE, WILD_DRAW_COLOUR, FLIP_TO_LIGHT -> {
                        // already handled by applyCardEffect
                    }
                    case SKIP_ALL -> {
                        // AI gets to play again: do nothing, keep current player as AI
                    }
                    default -> {
                        playerTurn();
                    }
                }
            }

            setTurnCompleted(false);
            return chosenCard;
        }
    }


    /**
     * Draws one card from the draw pile and adds it to the current player's hand.
     * Marks the player's turn as completed after drawing.
     */
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

        // Remember who is playing this card
        Player originalPlayer = getCurrentPlayer();

        // 2. Remove from hand + place on discard
        originalPlayer.getHand().remove(card);
        discardPile.add(0, card);

        // 3. Apply special effects (may advance to another player)
        applyCardEffect(card);

        // 4. If the original player emptied their hand, end the round
        if (originalPlayer.getHand().isEmpty()) {
            awardRoundPointsTo(originalPlayer);
        }

        // 5. Set turnCompleted based on whose turn it is now
        if (getCurrentPlayer() == originalPlayer) {
            // They played a normal card and it's still their turn (i.e., waiting for "Next Player")
            setTurnCompleted(true);
        } else {
            // A special card (SKIP, DRAW, REVERSE, FLIP, etc.) advanced the turn.
            // The new current player is just starting their turn.
            setTurnCompleted(false);
        }

        return true;
    }

    public void applyCardEffect(Card card) {

        if (lightMode) {
            switch (card.getCardLightType()) {

                case REVERSE:
                    direction = !direction;

                    playerTurn();
                    if (numPlayers == 2) {
                        playerTurn();
                    }
                    JOptionPane.showMessageDialog(null, "DIRECTION IS SWITCHED !");
                    break;

                case SKIP: {
                    // Move once to the player who is going to be skipped
                    playerTurn();
                    Player skippedPlayer = getCurrentPlayer();

                    // Move again so that we completely skip their turn
                    playerTurn();

                    JOptionPane.showMessageDialog(
                            null,
                            skippedPlayer.getName() + " has been skipped!"
                    );

                    // New current player is about to start a fresh turn
                    setTurnCompleted(false);
                    break;
                }


                case DRAW_ONE:
                    playerTurn(); //skip to the next player
                    if(!drawPile.isEmpty()) {
                        //make the player pick up a card
                        playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                        drawPile.remove(0);

                    }
                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 1 card and been skipped!");
                    playerTurn(); //advance to the next next player
                    break;

                case WILD_DRAW2:
                    playerTurn();
                    for(int i = 0;i < 2 && !drawPile.isEmpty(); i++) {
                        playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                        drawPile.remove(0);
                    }

                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 2 cards and been skipped!");
                    playerTurn();
                    break;

                case FLIP_TO_DARK:
                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has flipped the game!");
                    flipSide();
                    playerTurn();
                    break;

                default:
                    break;

            }

        }
        else {
            // dark mode
            switch (card.getCardDarkType()) {

                case REVERSE:
                    direction = !direction;
                    playerTurn();
                    if (numPlayers == 2) {
                        playerTurn();
                    }
                    JOptionPane.showMessageDialog(null, "DIRECTION IS SWITCHED !");
                    break;

                case DRAW_FIVE:
                    playerTurn(); // skip this player
                    for (int i = 0; i < 5; i++) {//draw 5 cards
                        Card drawCard = drawOneorNullCard();
                        //there are no more cards to pick up
                        if (drawCard == null){
                            break;
                        }
                        playerOrder.getCurrentPlayer().getHand().add(drawCard);
                    }
                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 5 cards and  been skipped! !");
                    //after the cards have been dealt to that one player who had to DRAW FIVE
                    //skip to the next player automatically

                    playerTurn();
                    break;

                case SKIP_ALL:
                    //DO NOTHING AS ITS AGAIN THE CURRENTS PLAYER TURN

                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has skipped all other players!");
                    break;

                case WILD_DRAW_COLOUR:
                    Player attackedPlayer = playerOrder.getCurrentPlayer();
                    Card.DarkColour chosenCard = card.getCardDarkColour();
                    if (chosenCard == null){

                        playerTurn();
                        break;
                    }
                    playerTurn();
                    break;

                case FLIP_TO_LIGHT:
                    JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has flipped the game!");
                    flipSide();
                    playerTurn();
                    break;

                default:
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

    /**
     * Forcing the lightmode to be the current games mode
     * @param mode is true then its light mode, if false, then its in dark mode
     */
    public void forceLightMode(boolean mode){
        this.lightMode = mode;
    }

    /**
     * Draws a single card or if the deck is empty, it returns null
     * @return the card on the top of the deck if none, then null
     */
    private Card drawOneorNullCard() {
        if (drawPile == null || drawPile.isEmpty()) {
            return null;
        }
        else {
            Card drawedCard = drawPile.get(0);
            drawPile.remove(0);
            return drawedCard;

        }
    }

    /**
     * Checking if the top card without returning it is null or not meaning
     * is there a card in the pile or not
     * @return the top card if there is any in the top of the drawpile
     */
    private Card peekAtDrawPile(){
        if (drawPile == null || drawPile.isEmpty()){
            return null;
        }
        else {
            return drawPile.get(0);
        }
    }

    /**
     * Reseting the cards (such as discard and draw piles) for a new round
     * but also perserving the scores that are currently totalled
     */
    public void resetAllCardsForNewRound(){

    }

    /**
     * Reseting the cards (such as discard and draw piles) for a new Game
     * but also resetting all the scores to 0
     */
    public void resetAllCardsForNewGame(){
        //reset the scores and also call reset a new round
    }


    /**
     * used to return the order of the players
     * @return the order of the players
     */
    public PlayerOrder getPlayerOrder(){
        return playerOrder;
    }

    //SAVING SNAP SHOTS BUT ALSO ITS IMPLEMENTED IN A SEPERATE CLASS BECAUSE THIS MODEL IS GETTING WAY TOO LONG
    private UNOGameStateSnapShot createUNOGameStateSnapShot(){
        return new UNOGameStateSnapShot(playerOrder.getAllPlayersToArrayList(), new ArrayList<Card>(drawPile), new ArrayList<Card>(discardPile), lightMode, playerOrder.getCurrentPlayer(), getDirection());
    }
    //SAVING THE SNAPSHOT
    public void saveSnateSnapShot(){
        undoStateSnapShot.push(createUNOGameStateSnapShot());
        redoStateSnapShot.clear();
    }

    public void restoreStateSnapShot(UNOGameStateSnapShot restoredSnapShot){
        //setPlayerOrder(restoredSnapShot);
        //drawPile = restoredSnapShot.drawpile
        //discardPile = restoredSnapShot
        lightMode = restoredSnapShot.lightMode;
        //direction = restoredSnapShot.directino
        //playerOrder.setCurrentPlayer(restoredSnapShot.player)
    }


    //RESETTING THE GAME AND STUFF

    public void resetRound(){
        drawPile.clear();
        discardPile.clear();
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            player.clearHand();
        }
        dealCardsBeginning();
        initializePlayers();
        startGame();
        lightMode = true;
        //forceLightMode(true);
    }

    public void resetGame(){
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            player.clearHand();
            //resetting the scores for all the players in the game
            initScores();
        }
        resetAllCardsForNewRound();
        resetAllCardsForNewGame();
        resetRound();
    }

    public ArrayList<Card> getDiscardPile() {
        return discardPile;

    }

    public void setTopCard(Card prevoiusTopCard) {
        discardPile.add(prevoiusTopCard);
    }

    public void setCurrentPlayer(Player previousCurrentPlayer) {
        playerOrder.setCurrentPlayer(previousCurrentPlayer);
    }

    public void startNewRound() {
        //starting the new round and clearing eveyrthing
        discardPile.clear();
        drawPile.clear();

        //now resetting all players in the groups hand
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            player.clearHand();
        }
        createDeckofCards();
        Collections.shuffle(drawPile);

        dealCardsBeginning();

        forceLightMode(true);
        setDirection(true);
        setTurnCompleted(false);
        setFirstPlayer();

        //JOptionPane.showMessageDialog(null, playerOrder.getCurrentPlayer().getName() + " has drawn 2 cards and been skipped!");
        //draw pile rebuilt
        drawPile.addAll(discardPile);

        dealCardsBeginning();
    }

    public Player getFirstPlayerName(){
        return playerOrder.getCurrentPlayer();

    }

    public void setFirstPlayer(){
        playerOrder.setToFirstPlayer();
    }

    public void startNewGame(){
        scores.clear();
        initializePlayers();
        initScores();
        startGame();
        //forceLightMode(true);
    }


}