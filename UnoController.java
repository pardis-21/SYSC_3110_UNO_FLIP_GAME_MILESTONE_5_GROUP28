import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UnoController implements ActionListener {

    private UnoViewFrame viewFrame;
    private final GameLogicModel model;


    public UnoController(GameLogicModel model) {

        this.model = model;
    }

    public void setView(UnoViewFrame viewFrame) {
        this.viewFrame = viewFrame;
        viewFrame.drawPile.addActionListener(this);
        // frame.UNOButton.addActionListener(this);
        //viewFrame.updateTopCard(model.getTopCard());
        viewFrame.discardPile.addActionListener(this);
        viewFrame.nextPlayerButton.addActionListener(this);
        model.startGame();
        updateView();    }




    public void onCardClicked(Card heldCard) {
        Card top = model.getTopCard();
        boolean success = model.tryPlayCard(heldCard);

        if (!success) {
            viewFrame.showMessage(
                    "You cannot play " + heldCard + " on " + model.getTopCard()
            );
            return;
        }

        updateView();
    }

    public void onDrawClicked(){
        model.drawCardCurrentPlayer();
        updateView();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton){
            source = (JButton)source;
        }
        if (source == viewFrame.drawPile) {
            model.drawCardCurrentPlayer();
            updateView();
        }
        else if (source == viewFrame.discardPile){
            JOptionPane.showMessageDialog(viewFrame,"Top card: " + model.getTopCard());
        }
        else if (source == viewFrame.nextPlayerButton) { // making sure player actually plays b4 going to next player
            if (!model.isTurnCompleted()) {
                viewFrame.showMessage("You must play or draw before ending your turn!");
                return;
            }

            model.setTurnCompleted(false);
            model.playerTurn();
            updateView();
        }





    }

    public void updateView() {
        if (viewFrame == null) {
            return;
        }
        else {
            //PlayerOrder playerOrder = new PlayerOrder();
            //List<Card> hand = model.getPlayerHand();
            viewFrame.updateHand(model.getPlayerHand());
            viewFrame.updateTopCard(model.getTopCard());
        }

    }

    public List<Card> getCurrentPlayerHand() {
        return model.getCurrentPlayer().getHand();
    }
}
