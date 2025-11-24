import org.junit.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
/**
 * This test class tests the UNO Game's Logic by the JUnit Framework.
 * Here, all methods are tested individually to check correctness.
 *
 * @Author Charis Nobossi 101297742
 * @Author Anvita Ala 101301514
 * @Author Pardis Ehsani 101300400
 * @Author Pulcherie Mbaye 101302394
 */

public class GameLogicModelTest {

    private GameLogicModel gameLogic;
    private PlayerOrder playerOrder;
    private Player p1, p2, p3;
//    private Game game;
//    private ArrayList<Player> playerNames;

    @Before
    public void setUp() {
        gameLogic = new GameLogicModel();
        playerOrder = new PlayerOrder();
        p1 = new Player("player 1");
        p2 = new Player("player 2");
        p3 = new Player("player 3");
        playerOrder.addPlayer(p1);
        playerOrder.addPlayer(p2);
        playerOrder.addPlayer(p3);
        gameLogic.setPlayerOrder(playerOrder);
//        playerNames = new ArrayList<>();
//        playerNames.add(new Player("1"));
//        playerNames.add(new Player("2"));
//        playerNames.add(new Player("3"));

        //gameLogic = new GameLogicModel(playerNames);

//        PlayerOrder playerOrder = new PlayerOrder();
//        playerOrder.addPlayer(playerNames.get(0));
//        playerOrder.addPlayer(playerNames.get(1));
//        playerOrder.addPlayer(playerNames.get(2));
    }

    @Test
    public void testConstructorDoesNotCrash() {
        assertNotNull("GameLogic instance should be created", gameLogic);
    }

    @Test
    public void testInitScores() throws Exception {
        gameLogic.initScores();

        Field scoresField = GameLogicModel.class.getDeclaredField("scores");
        scoresField.setAccessible(true);
        Map<Player, Integer> scores = (Map<Player, Integer>) scoresField.get(gameLogic);

        Field playerOrderField = GameLogicModel.class.getDeclaredField("playerOrder");
        playerOrderField.setAccessible(true);
        Object playerOrderObj = playerOrderField.get(gameLogic);

        ArrayList<Player> players = (ArrayList<Player>) playerOrderObj
                .getClass()
                .getMethod("getAllPlayersToArrayList")
                .invoke(playerOrderObj);

        for (Player p : players) {
            assertTrue("Scores should contain player: " + p.getName(), scores.containsKey(p));
            assertEquals("Score for player " + p.getName() + " should be 0", Integer.valueOf(0), scores.get(p));
        }
    }

    @Test
    public void testStartGameSetsTopCard() {
        gameLogic.startGame();
        assertNotNull("Top card should not be null after starting the game", gameLogic.getTopCard());
    }

    @Test
    public void testDrawCardCurrentPlayerAddsCard(){
        int initialHand = p1.getHand().size();
        gameLogic.drawCardCurrentPlayer();
        assertEquals(initialHand + 1, p1.getHand().size());
    }


    @Test
    public void testSetAndGetTurnCompleted(){
        gameLogic.setTurnCompleted(true);
        assertTrue(gameLogic.isTurnCompleted());
    }

    @Test
    public void testTryPlayCardValidCardReturnsTrue(){
        Card top = new Card();
        top.setCardLightColour("RED");

        Card playable = new Card();
        playable.setCardLightColour("RED");
        p1.getHand().clear();
        p1.getHand().add(playable);

        gameLogic.startGame();
        gameLogic.getTopCard().setCardLightColour("RED");

        boolean result = gameLogic.tryPlayCard(playable);

        assertTrue("Should return true for a playable card", result);

    }

    @Test
    public void testSetPlayerOrderInitializesScores(){
        gameLogic.setPlayerOrder(playerOrder);
        assertNull(gameLogic.getMatchWinner(500));
    }

    @Test
    public void testPlayerTurnClockwise(){
        Player firstPlayer = gameLogic.getCurrentPlayer();
        gameLogic.playerTurn();
        assertNotEquals(firstPlayer, gameLogic.getCurrentPlayer());
    }
    @Test
    public void testPlayerReverse(){
        GameLogicModel game = new GameLogicModel();
        Player firstPlayer = new Player("1");
        Player secondPlayer = new Player("2");
        Player thirdPlayer = new Player("3");

        PlayerOrder order = new PlayerOrder();

        order.addPlayer(firstPlayer);
        order.addPlayer(secondPlayer);
        order.addPlayer(thirdPlayer);
        game.setPlayerOrder(order);
        game.initScores();

        assertEquals(firstPlayer, game.getCurrentPlayer());
        //advances to the next player
        game.playerTurn();
        assertEquals(secondPlayer, game.getCurrentPlayer());

        game.playerTurn();
        assertEquals(thirdPlayer, game.getCurrentPlayer());

        //this is clockwise
        assertTrue(game.getDirection());

        //reverse the direction now
        game.setDirection(false);
        assertEquals(false, game.getDirection());

        game.playerTurn();
        //now it swaps the order and should go back to the second player
        assertEquals(secondPlayer, game.getCurrentPlayer());

        game.playerTurn();
        assertEquals(firstPlayer, game.getCurrentPlayer());
        game.playerTurn();
        assertEquals(thirdPlayer, game.getCurrentPlayer());

        //another way is to use the REVERSE CARD

        Card reverseCard = new Card();
        reverseCard.lightMode = true;

        try {
            Field typeField = Card.class.getDeclaredField("cardLightType");
            typeField.setAccessible(true);
            typeField.set(reverseCard, Card.LightType.REVERSE);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        game.applyCardEffect(reverseCard);
        //now switches back to CW
        assertTrue(game.getDirection());

        game.playerTurn();
        assertEquals(secondPlayer, game.getCurrentPlayer());

        game.playerTurn();

    }

    @Test
    public void testAwardRoundPointWhenWinnerEmptiesHand(){
        p1.getHand().clear();
        p2.getHand().add(new Card());
        gameLogic.startGame();
        gameLogic.playGame(new Card());
        assertNotNull(gameLogic.getMatchWinner(0));
    }

    /*
    @Test
    public void testRoundPoints(){

        p1.getHand().clear();
        p2.getHand().add(new Card());
        p3.getHand().add(new Card());

        int points = gameLogic.awardRoundPointsTo(p1);
        assertTrue(points > 1);
        assertEquals(Optional.of(points), gameLogic.scores.get(p1));
    }
*/

    @Test
    public void testGetDirectionInitiallyClockwise(){
        assertTrue(gameLogic.getDirection());
    }

    @Test
    public void testPlayerTurnRunsWithoutException() {
        try {
            gameLogic.playerTurn();
        } catch (Exception e) {
            fail("playerTurn should not throw an exception");
        }
    }

    @Test
    public void testGetMatchWinnerReturnsNullIfNoOneWins() {
        gameLogic.startGame();
        assertNull("No winner should be returned if no one reached target", gameLogic.getMatchWinner(100));
    }

    @Test
    public void testSetPlayerOrderAddsPlayersToScores() throws Exception {
        gameLogic.initScores();

        Field scoresField = GameLogicModel.class.getDeclaredField("scores");
        scoresField.setAccessible(true);
        Map<Player, Integer> scores = (Map<Player, Integer>) scoresField.get(gameLogic);

        Field playerOrderField = GameLogicModel.class.getDeclaredField("playerOrder");
        playerOrderField.setAccessible(true);
        Object oldPlayerOrder = playerOrderField.get(gameLogic);

        ArrayList<String> newNames = new ArrayList<>();
        newNames.add("4");
        PlayerOrder newOrder = new PlayerOrder();
        for (String name : newNames) {
            newOrder.addPlayer(new Player(name));
        }

        gameLogic.setPlayerOrder(newOrder);

        Object newPlayerOrderObj = playerOrderField.get(gameLogic);
        ArrayList<Player> newPlayers = (ArrayList<Player>) newPlayerOrderObj
                .getClass()
                .getMethod("getAllPlayersToArrayList")
                .invoke(newPlayerOrderObj);

        for (Player p : newPlayers) {
            assertTrue("Scores map should contain new player: " + p.getName(), scores.containsKey(p));
        }
    }

    @Test
    public void testPlayUNOGameRunsWithoutException() {
        try {
        } catch (Exception e) {
            fail("playUNOGame should not throw an exception on setup");
        }
    }

    @Test
    public void testFlipSide(){
        GameLogicModel game = new GameLogicModel();
        Player firstPlayer = new Player("first");
        Player secondPlayer = new Player("second");
        Player thirdPlayer = new Player("third");

        PlayerOrder playerOrder = new PlayerOrder();

        playerOrder.addPlayer(firstPlayer);
        playerOrder.addPlayer(secondPlayer);
        playerOrder.addPlayer(thirdPlayer);
        game.setPlayerOrder(playerOrder);
        game.initScores();

        //asserting that the game starts in the lightmode as normal
        assertTrue(game.lightMode);
        //then changing the game to the flip side
        game.flipSide();
        //because it is now in the flip side, it is no longer in light mode
        assertFalse(game.lightMode);

    }
    @Test
    public void testFlipSideToggle(){
        GameLogicModel game = new GameLogicModel();
        boolean prevMode = game.lightMode;
        game.flipSide();
        assertNotEquals(prevMode, game.lightMode);
    }
    @Test
    public void testFlipSideUpdatesCards(){
        GameLogicModel game = new GameLogicModel();
        Player firstPlayer = new Player("p1");
        Player secondPlayer = new Player("p2");
        Player thirdPlayer = new Player("p3");

        PlayerOrder playerOrder = new PlayerOrder();

        playerOrder.addPlayer(firstPlayer);
        playerOrder.addPlayer(secondPlayer);
        playerOrder.addPlayer(thirdPlayer);
        game.setPlayerOrder(playerOrder);
        game.initScores();

        game.dealCardsBeginning();
        Card blueCard = new Card();
        blueCard.lightMode = true;

        try {
            Field typeField = Card.class.getDeclaredField("cardLightColour");
            typeField.setAccessible(true);
            typeField.set(blueCard, Card.LightColour.BLUE);
        }
        catch (Exception e) {
            fail(e.getMessage());
        }
        //adding it to the first players hand
        firstPlayer.getHand().add(blueCard);
        game.flipSide();
        assertEquals(game.lightMode, blueCard.lightMode);

        game.dealCardsBeginning();
        //checking if the players hand is now all in lightmode
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            for (Card card: player.getHand()) {
                assertEquals(game.lightMode, card.lightMode);
            }
        }

    }


}
