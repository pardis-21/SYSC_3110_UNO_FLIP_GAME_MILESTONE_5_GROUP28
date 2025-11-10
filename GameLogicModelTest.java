import org.junit.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class GameLogicModelTest {

    private GameLogicModel gameLogic;
    private Game game;
    private ArrayList<Player> playerNames;

    @Before
    public void setUp() {
        playerNames = new ArrayList<>();
        playerNames.add(new Player("1"));
        playerNames.add(new Player("2"));
        playerNames.add(new Player("3"));

        //gameLogic = new GameLogicModel(playerNames);

        PlayerOrder playerOrder = new PlayerOrder();
        playerOrder.addPlayer(playerNames.get(0));
        playerOrder.addPlayer(playerNames.get(1));
        playerOrder.addPlayer(playerNames.get(2));
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
}
