import javax.swing.*;

public class PlayerCardButton extends JButton {
    private Card.Type cardType;
    private Card.Colour cardColour;
    private Card card;


    public PlayerCardButton(Card card){
        this.card = card;
        cardType = card.getCardType();
        cardColour = card.getCardColour();
    }

    public Card.Type getCardType() {
        return cardType;

    }

    public Card.Colour getCardColour() {
        return cardColour;
    }

    public Card getCard() {
        return card;
    }

    public void addActionListner(UnoController controller) {
    }
}
