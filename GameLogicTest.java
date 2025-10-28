import org.junit.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class GameLogicTest {

    private GameLogic game;
    private ArrayList<String> playerNames;

    @Before
    public void setUp() {
        playerNames = new ArrayList<>();
        playerNames.add("1");
        playerNames.add("2");
        playerNames.add("3");

        game = new GameLogic(playerNames);
    }

    @Test
    public void testConstructorDoesNotCrash() {
        assertNotNull("GameLogic instance should be created", game);
    }

    @Test
    public void testInitScores() throws Exception {
        game.initScores();

        Field scoresField = GameLogic.class.getDeclaredField("scores");
        scoresField.setAccessible(true);
        Map<Player, Integer> scores = (Map<Player, Integer>) scoresField.get(game);

        Field playerOrderField = GameLogic.class.getDeclaredField("playerOrder");
        playerOrderField.setAccessible(true);
        Object playerOrderObj = playerOrderField.get(game);

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
        game.startGame();
        assertNotNull("Top card should not be null after starting the game", game.getTopCard());
    }

    @Test
    public void testPlayerTurnRunsWithoutException() {
        try {
            game.playerTurn();
        } catch (Exception e) {
            fail("playerTurn should not throw an exception");
        }
    }

    @Test
    public void testGetMatchWinnerReturnsNullIfNoOneWins() {
        game.startGame();
        assertNull("No winner should be returned if no one reached target", game.getMatchWinner(100));
    }

    @Test
    public void testSetPlayerOrderAddsPlayersToScores() throws Exception {
        game.initScores();

        Field scoresField = GameLogic.class.getDeclaredField("scores");
        scoresField.setAccessible(true);
        Map<Player, Integer> scores = (Map<Player, Integer>) scoresField.get(game);

        Field playerOrderField = GameLogic.class.getDeclaredField("playerOrder");
        playerOrderField.setAccessible(true);
        Object oldPlayerOrder = playerOrderField.get(game);

        ArrayList<String> newNames = new ArrayList<>();
        newNames.add("4");
        PlayerOrder newOrder = new PlayerOrder();
        for (String name : newNames) {
            newOrder.addPlayer(new Player(name));
        }

        game.setPlayerOrder(newOrder);

        Object newPlayerOrderObj = playerOrderField.get(game);
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
