import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * This class acts as the bridge between the GameLogicModel and the view.
 * It handles user interactions such as playing cards, drawing cards,
 * ending turns, and calling 'UNO'. The controller ensures that the view stays updated
 * according to the state of the model.
 * @Author Charis Nobossi 101297742
 * @Author Anvita Ala 101301514
 * @Author Pardis Ehsani 101300400
 * @Author Pulcherie Mbaye 101302394
 *
 */

public class UnoController implements ActionListener {
    private UnoViewFrame viewFrame;
    private final GameLogicModel model;
    private JLabel roundLabel;
    private int roundNumber;
    private UNOCommandManager commandManager;

    /**
     * Constructs a new controller with the given model
     *
     * @param model contains the game logic
     */
    public UnoController(GameLogicModel model) {
        this.model = model;
        this.roundNumber = 1;
        commandManager = new UNOCommandManager();
    }

    /**
     * Sets the iew for this controller and connexts event listeners to the appropriate button
     * @param viewFrame represents the GUI
     */

    public void setView(UnoViewFrame viewFrame) {
        this.viewFrame = viewFrame;
        viewFrame.drawPile.addActionListener(this);
        viewFrame.discardPile.addActionListener(this);
        viewFrame.nextPlayerButton.addActionListener(this);
        viewFrame.UNOButton.addActionListener(this);
        //undo/redo buttons that i might have to change just wait
        viewFrame.undoButton.addActionListener(this);
        viewFrame.redoButton.addActionListener(this);
        model.startGame();
        updateView();
        handleAITurnIfCurrent();
    }

    /**
     * Handles what happens when the player clicks on a card they want to play
     * checks if the card can be played on the current top card,
     * updates the view and manages rounds
     * @param heldCard the card clicked by the player
     */
    public void onCardClicked(Card heldCard) {

        Card previousTopCard = model.getTopCard();
        if (model.getCurrentPlayer() instanceof AIPlayer) {
            JOptionPane.showMessageDialog(null, "It's an AI player's turn. Click Next Player to continue.");
            model.setTurnCompleted(false);
            return;
        }

        if (model.isTurnCompleted()) {
            JOptionPane.showMessageDialog(null, "Your turn is complete! Click on the next player button.");
            return;
        }

        int previousPlayerHandSize = model.getCurrentPlayer().getHand().size();

        boolean success = model.tryPlayCard(heldCard);
        if (!success) {
            viewFrame.showMessage(
                    "You cannot play " + heldCard + " on " + model.getTopCard()
            );
            return;
        }

        commandManager.push(new PlayerCardCommand(model, model.getCurrentPlayer(), heldCard, previousTopCard));
        updateView();
        model.setTurnCompleted(true);

        if(model.getCurrentPlayer().getHand().isEmpty()){
            int points = model.awardRoundPointsTo(model.getCurrentPlayer());
            JOptionPane.showMessageDialog(null, "Player: " + model.getCurrentPlayer().getName() + " wins the round with: " + points + " points!!" +
                    "\n + Total Points: " + model.scores.get(model.getCurrentPlayer()) + "\n Round Over!");

            //viewFrame.scoreLabel.setText("Score: " + model.scores.get(model.getCurrentPlayer()));

            Player playerWinner = model.getMatchWinner(500);
            boolean isGameOver = playerWinner != null;

            String[] options = {"New game","New round", "Exit"};
                Object selectedOption = JOptionPane.showInputDialog(
                        null,
                        "Continue?",
                        "Selection", // Title of the dialog
                        JOptionPane.QUESTION_MESSAGE, // Message type (e.g., QUESTION_MESSAGE, INFORMATION_MESSAGE)
                        null, // Icon (null for default)
                        options, // Array of options for the dropdown
                        options[0] // Default selected option
                );
            if(("Exit").equals(selectedOption)){
                JOptionPane.showMessageDialog(null, "Thanks for playing! \nExiting...");
                System.exit(0);
            }
            else if(("New round").equals(selectedOption)){
                model.startNewRound();
                roundNumber++;
                commandManager.clear();
                JOptionPane.showMessageDialog(null, "New round!\n" + "Scores are kept!\n" + "First Player: " + model.getFirstPlayerName());

            }
            else{ // start new game
                model.startNewGame();
                model.setTurnCompleted(false);
                roundNumber++;
                roundNumber = 1;
                commandManager.clear();
                JOptionPane.showMessageDialog(null, "New round started!");

            }
            //updateHand();
            updateView();

            return;
        }
        handleAITurnIfCurrent();
    }

    /**
     * handles when the player clicks on the draw button
     * draws a new card for the current player and updates the view
     */
    public void onDrawClicked(){
        model.drawCardCurrentPlayer();
        updateView();
    }

    /**
     * handles all button events in the GUI
     * and responds accordingly
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (viewFrame == null) {
            System.err.println("ViewFrame is not initialized yet!");
            return;
        }

        Object source = e.getSource();

        if (source == viewFrame.drawPile) {

            if (model.getCurrentPlayer() instanceof AIPlayer) {
                JOptionPane.showMessageDialog(null, "It's an AI player's turn. Click Next Player to continue.");
                return;
            } else if (model.getTopCard().getCardDarkType().equals(Card.DarkType.WILD_DRAW_COLOUR) && !model.lightMode) {
                if (!model.drawPile.getFirst().getCardDarkColour().equals(model.getTopCard().getCardDarkColour())) {
                    onDrawClicked();
                    return;
                }
            }
            if (model.isTurnCompleted()) {
                JOptionPane.showMessageDialog(null, "Your turn is complete! Click on the next player button.");
                return;
            }
            onDrawClicked();
            model.setTurnCompleted(true);

        } else if (source == viewFrame.discardPile) {
            JOptionPane.showMessageDialog(viewFrame, "Top card: " + model.getTopCard());

        } else if (source == viewFrame.nextPlayerButton) {

            if (model.getCurrentPlayer() instanceof AIPlayer) {
                handleAITurnIfCurrent();
                return;
            }
            // making sure player actually plays b4 going to next player
            if (model.getTopCard().getCardDarkType().equals(Card.DarkType.WILD_DRAW_COLOUR)
                    && !model.isTurnCompleted() && !model.lightMode) {
                viewFrame.showMessage("You have to draw cards until you get a "
                        + model.getTopCard().getCardDarkColour() + " card!");
                return;
            } else if (!model.isTurnCompleted()) {
                viewFrame.showMessage("You must play or draw before ending your turn!");
                return;
            }

            if (model.getCurrentPlayer().getHand().size() == 1 && !model.getCurrentPlayer().UNOClicked) {
                JOptionPane.showMessageDialog(null,
                        "You had 'UNO' card and didn't click UNO before ending your turn! draw 2 cards ! :P");
                onDrawClicked();
                onDrawClicked();
            }
            // End current player's turn and move to next player
            model.setTurnCompleted(false);
            model.playerTurn();
            viewFrame.scoreLabel.setText("Score: " + model.scores.get(model.getCurrentPlayer()));
            updateView();

            // If the new current player is AI, let it play right away
            handleAITurnIfCurrent();
        } else if (source == viewFrame.UNOButton) {
            if (!(model.getCurrentPlayer().getHand().size() == 1)) {
                JOptionPane.showMessageDialog(null, "Uh oh! You don't have 'uno' card! draw 2 :P");
                onDrawClicked();
                onDrawClicked();
                model.setTurnCompleted(false);
            } else {
                JOptionPane.showMessageDialog(null, "UNOOOOO!!!");
                model.getCurrentPlayer().UNOClicked = true;
            }
        }
        else if (source == viewFrame.undoButton) {
            // TESTING SOMETHING OUT
            if (commandManager.isUndoable()){
                commandManager.undo();

                //viewFrame.updateHand(model.getPlayerHand());
                //viewFrame.updateTopCard(model.getTopCard());
                //viewFrame.repaint();
                //viewFrame.revalidate();

                updateView();
                model.setTurnCompleted(false); //do not move to the next player
                //viewFrame.showMessage("Undid Last Move");
                JOptionPane.showMessageDialog(null, "Undid your last move!");
            }
            else {
                JOptionPane.showMessageDialog(null, "You nothing to undo!");
            }
        }
        else if (source == viewFrame.redoButton) {
            if (commandManager.isRedoable()){
                commandManager.redo();
                //viewFrame.updateHand(model.getPlayerHand());
                //viewFrame.updateTopCard(model.getTopCard());
                //viewFrame.repaint();
                //viewFrame.revalidate();
                updateView();
                model.setTurnCompleted(true);
                JOptionPane.showMessageDialog(null, "You redoed your last move!");

            }
            else {
                JOptionPane.showMessageDialog(null, "You nothing to redo!");
                //model.redo()
            }
        }
    }


    /**
     * Updates the view to reflect the current game state
     */
    public void updateView() {
        if (viewFrame != null) {
            viewFrame.updateHand(model.getPlayerHand());
            viewFrame.updateTopCard(model.getTopCard());
            viewFrame.roundLabel.setText("Round number: " + roundNumber);

            viewFrame.undoButton.setEnabled(commandManager.isUndoable());
            viewFrame.redoButton.setEnabled(commandManager.isRedoable());
            viewFrame.repaint();
            viewFrame.revalidate();

            viewFrame.currentPlayerLabel.setText(model.getCurrentPlayer().getName());
            if ((!model.lightMode && model.getTopCard().getCardLightType().equals(Card.LightType.FLIP_TO_DARK)) ||
                    (model.lightMode && model.getTopCard().getCardDarkType().equals(Card.DarkType.FLIP_TO_LIGHT))) {
            }
        }

    }

    /**
     * returns the current player's hand
     * @return a list of cards representing the player's hand
     */
    public List<Card> getCurrentPlayerHand() {
        return model.getCurrentPlayer().getHand();
    }


    /**
     * Handles the AI player's turn if the current player is an AI.
     * Adds a short delay, lets the AI choose and play (or draw) a card,
     * shows a message about the action, and updates the game view.
     */
    private void handleAITurnIfCurrent() {

        Player currentPlayer = model.getCurrentPlayer();

        if (currentPlayer instanceof AIPlayer) {
            //added a delay to really make it seem like you're playing against an AI model
               try {
                   Thread.sleep(1500);
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
               }
               // Let the AI take its turn
               AIPlayer aiPlayer = (AIPlayer) currentPlayer;
               //ai model plays the card
               Card played = model.handleAIPlayer(aiPlayer);

               updateView();

               //if the card can be played then
               if (played != null) {
                   viewFrame.showMessage(aiPlayer.getName() + " played " + played);
                   //model.setTurnCompleted(false);
                }
               //otherwise ai picked up a card cuz they couldnt play anything
               else {
                   viewFrame.showMessage(aiPlayer.getName() + " drew a card.");
               }
               model.setTurnCompleted(false);
               updateView();
       }
    }

}
