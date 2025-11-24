import javax.swing.*;

/**
 * This class represents a clickable button that corresponds to a specific card
 * each button visually represents a card in the players hand and allows player interaction
 * @Author Charis Nobossi 101297742
 * @Author Anvita Ala 101301514
 * @Author Pardis Ehsani 101300400
 * @Author Pulcherie Mbaye 101302394
 */

public class PlayerCardButton extends JButton {
    private Card.LightType cardType;
    private Card.LightColour cardColour;
    private Card card;

    /**
     * constructs a PlayerCardButton for the given card
     * This button stores the card's type and colour, allowing the controller to determine
     * which card was clicked and when the button is pressed.
     *
     * @param card the card represented by this button
     */
    public PlayerCardButton(Card card){
        this.card = card;
        cardType = card.getCardLightType();
        cardColour = card.getCardLightColour();
    }

    /**
     * returns the type of the card represented by this button
     * @return card type
     */
    public Card.LightType getCardType() {
        return cardType;

    }

    /**
     * returns the colour of the card represented by this button
     * @return card colour
     */
    public Card.LightColour getCardColour() {
        return cardColour;
    }

    /**
     * returns the card associated with this button
     * @return the card linked to this button
     */
    public Card getCard() {
        return card;
    }

    /**
     * to connect the button's click event to the controller
     * @param controller Connect the controller handling button cliks
     */

    public void addActionListner(UnoController controller) {
    }
}
