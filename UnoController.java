import javax.swing.*;
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


    /**
     * Constructs a new controller with the given model
     *
     * @param model contains the game logic
     */
    public UnoController(GameLogicModel model) {

        this.model = model;
    }

    /**
     * sets the iew for this controller and connexts event listeners to the appropriate button
     *
     * @param viewFrame represents the GUI
     */

    public void setView(UnoViewFrame viewFrame) {
        this.viewFrame = viewFrame;
        viewFrame.drawPile.addActionListener(this);
        // frame.UNOButton.addActionListener(this);
        //viewFrame.updateTopCard(model.getTopCard());
        viewFrame.discardPile.addActionListener(this);
        viewFrame.nextPlayerButton.addActionListener(this);
        viewFrame.UNOButton.addActionListener(this);
        model.startGame();
        updateView();    }


    /**
     * Handles what happens when the player clicks on a card they want to play
     * checks if the card can be played on the current top card,
     * updates the view and manages rounds
     * @param heldCard the card clicked by the player
     */
    public void onCardClicked(Card heldCard) {
        if (model.isTurnCompleted()) {
            JOptionPane.showMessageDialog(null, "Your turn is complete! Click on the next player button.");
            return;
        }

        Card top = model.getTopCard();

        boolean success = model.tryPlayCard(heldCard);
        if (!success) {
            viewFrame.showMessage(
                    "You cannot play " + heldCard + " on " + model.getTopCard()
            );
            return;
        }

        updateView();
        model.setTurnCompleted(true);

        Card updated = model.getTopCard();
        Card.LightType lightType = updated.getCardLightType();
        Card.DarkType darkType = updated.getCardDarkType();

        if (model.lightMode) {
            if (lightType == Card.LightType.REVERSE || lightType == Card.LightType.SKIP || lightType == Card.LightType.DRAW_ONE || lightType == Card.LightType.WILD_DRAW2) {
                model.setTurnCompleted(false);
            }
        }
        else{
            if(darkType == Card.DarkType.REVERSE || darkType == Card.DarkType.SKIP_ALL || darkType == Card.DarkType.DRAW_FIVE){
                model.setTurnCompleted(false);
            }
        }

        if(model.getCurrentPlayer().getHand().isEmpty()){
            int points = model.awardRoundPointsTo(model.getCurrentPlayer());
            JOptionPane.showMessageDialog(null, "Player " + model.getCurrentPlayer().getName() + " wins the round with " + points + " points!!" );
            viewFrame.scoreLabel.setText("Score: " + model.scores.get(model.getCurrentPlayer()));

            String[] options = {"New game", "Exit"};
                Object selectedOption = JOptionPane.showInputDialog(
                        null,
                        "Continue?",
                        "Selection", // Title of the dialog
                        JOptionPane.QUESTION_MESSAGE, // Message type (e.g., QUESTION_MESSAGE, INFORMATION_MESSAGE)
                        null, // Icon (null for default)
                        options, // Array of options for the dropdown
                        options[0] // Default selected option
                );
            if(((String)selectedOption).equals(options[1])){
                JOptionPane.showMessageDialog(null, "Thanks for playing!");
                System.exit(0);
            }
            else{
                model.startGame();
                model.setTurnCompleted(false);
                updateView();
                JOptionPane.showMessageDialog(null, "New round started!");
            }
        }
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
            if(model.getTopCard().getCardDarkType().equals(Card.DarkType.WILD_DRAW_COLOUR) && !model.lightMode){
                if(!model.drawPile.getFirst().getCardDarkColour().equals(model.getTopCard().getCardDarkColour())){
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

        }
        else if (source == viewFrame.discardPile){
            JOptionPane.showMessageDialog(viewFrame,"Top card: " + model.getTopCard());

        }
        else if (source == viewFrame.nextPlayerButton) { // making sure player actually plays b4 going to next player
            if(model.getTopCard().getCardDarkType().equals(Card.DarkType.WILD_DRAW_COLOUR) && !model.isTurnCompleted() && !model.lightMode){
                viewFrame.showMessage("You have to draw cards until you get a " + model.getTopCard().getCardDarkColour() + " card!");
                return;
            }
            else if (!model.isTurnCompleted()) {
                viewFrame.showMessage("You must play or draw before ending your turn!");
                return;
            }
            if (model.getCurrentPlayer().getHand().size() == 1 && !model.getCurrentPlayer().UNOClicked) {
                JOptionPane.showMessageDialog(null, "You had 'uno' card and didn't click UNO before ending your turn! draw 2 :P");
                onDrawClicked();
                onDrawClicked();
            }

            model.setTurnCompleted(false);
            model.playerTurn();
            viewFrame.scoreLabel.setText("Score: " + model.scores.get(model.getCurrentPlayer()));

            updateView();
        }
        else if (source == viewFrame.UNOButton) {
            if (!(model.getCurrentPlayer().getHand().size() == 1)) {
                JOptionPane.showMessageDialog(null, "Uh oh! You don't have 'uno' card! draw 2 :P");
                onDrawClicked();
                onDrawClicked();
                model.setTurnCompleted(false);
            }
            else{
                JOptionPane.showMessageDialog(null, "UNOOOOO!!!");
                model.getCurrentPlayer().UNOClicked = true;
            }

        }





    }

    /**
     * Updates the view to reflect the current game state
     */
    public void updateView() {
        if (viewFrame != null) {
            //PlayerOrder playerOrder = new PlayerOrder();
            //List<Card> hand = model.getPlayerHand();
            viewFrame.updateHand(model.getPlayerHand());
            viewFrame.updateTopCard(model.getTopCard());
            viewFrame.currentPlayerName.setText(model.getCurrentPlayer().getName());
        }


    }


    /**
     * returns the current player's hand
     * @return a list of cards representing the player's hand
     */
    public List<Card> getCurrentPlayerHand() {
        return model.getCurrentPlayer().getHand();
    }
}
