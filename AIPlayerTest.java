import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.api.*;

/**
 * This class is testing the methods of the AIPlayer test.
 *
 * @Author Anvita Ala 101301514
 * @Author Charis Nobossi 101297742
 * @Author Pulcherie Mbaye 101302394
 * @Author Pardis Ehsani 101300400
 */
public class AIPlayerTest {

    private GameLogicModel gameLogicModel;
    private PlayerOrder playerOrder;
    private AIPlayer aiPlayer;
    private Player human;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        gameLogicModel = new GameLogicModel();
        playerOrder = new PlayerOrder();
        aiPlayer = new AIPlayer("CHATGPT");
        human = new Player("BOB");

        playerOrder.addPlayer(aiPlayer);
        playerOrder.addPlayer(human);
        gameLogicModel.initializePlayers();
        gameLogicModel.setPlayerOrder(playerOrder);
        gameLogicModel.initScores();

        Card topCard = new Card();

        topCard.setCardLightColour("RED");
        topCard.setCardLightType(Card.LightType.FIVE);

        gameLogicModel.discardPile.add(0,topCard);

    }

    @Test
    public void testAIPlayerChoosesValidCard(){
        Card card = new Card();
        card.setCardLightColour("BLUE");
        card.setCardLightType(Card.LightType.ONE);
        aiPlayer.getHand().add(card);
        Card chosen = aiPlayer.chooseCardToPlay(gameLogicModel.getTopCard());
        assertEquals(card,chosen);
    }
    @org.junit.jupiter.api.Test
    void chooseCardToPlay() {
    }

    @org.junit.jupiter.api.Test
    void chooseBestLightColour() {
    }

    @org.junit.jupiter.api.Test
    void chooseBestDarkColour() {
    }
}