import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayOrderTest {
    private PlayerOrder playerOrder;

    @Before
    public void setUp() {
        playerOrder = new PlayerOrder();
    }

    @Test
    public void testNewPlayerOrderIsEmpty(){
        assertTrue("a new playerOrder should be empty", playerOrder.isEmpty());
        assertNull("the current player should be null since PlayerOrder is newly created",playerOrder.getCurrentPlayer());
    }

    @Test
    public void testAddOnePlayer(){
        Player player1 = new Player("John");
        playerOrder.addPlayer(player1);

        assertEquals("current player should be one going first", player1, playerOrder.getCurrentPlayer());
        assertEquals("there should only be one player", 1, playerOrder.getAllPlayersToArrayList().size());
    }

    @Test
    public void testAddmorePlayeers(){
        Player p1 = new Player("anna");
        Player p2 = new Player("Jim");
        Player p3 = new Player("Kevin");

        playerOrder.addPlayer(p1);
        playerOrder.addPlayer(p2);
        playerOrder.addPlayer(p3);

        ArrayList<Player> AllPlayers = playerOrder.getAllPlayersToArrayList();

        assertTrue(AllPlayers.contains(p1));
        assertTrue(AllPlayers.contains(p2));
        assertTrue(AllPlayers.contains(p3));

        assertEquals("there should be 3 players", 3, AllPlayers.size());
    }

    @Test
    public void testNextPlayerClockwise(){
        Player p1 = new Player("anna");
        Player p2 = new Player("Jim");
        Player p3 = new Player("Kevin");

        playerOrder.addPlayer(p1);
        playerOrder.addPlayer(p2);
        playerOrder.addPlayer(p3);

        Player beginner = playerOrder.getCurrentPlayer();
        playerOrder.nextPlayerClockwise();
        Player nextPlayer = playerOrder.getCurrentPlayer();

        assertNotEquals("clockwise should get the current player", beginner, nextPlayer);

    }

    @Test
    public void testNextPlayerCounterClockwise(){
        Player p1 = new Player("anna");
        Player p2 = new Player("Jim");
        Player p3 = new Player("Kevin");

        playerOrder.addPlayer(p1);
        playerOrder.addPlayer(p2);
        playerOrder.addPlayer(p3);

        playerOrder.nextPlayerClockwise();

        playerOrder.nextPlayerCounterClockwise();
        Player counterClockWise = playerOrder.getCurrentPlayer();

        assertEquals("counter clowise should go to the previous player", p1, counterClockWise);
    }

    @Test
    public void testgetAllPlayersToArrayListNoPlayers(){
        ArrayList<Player> players = playerOrder.getAllPlayersToArrayList();
        assertTrue("this should return an empty list because theres no players", players.isEmpty());
    }

    @Test
    public void testgetAllPlayersToArrayListYesPlayers(){
        Player p1 = new Player("anna");
        Player p2 = new Player("Jim");
        Player p3 = new Player("Kevin");

        playerOrder.addPlayer(p1);
        playerOrder.addPlayer(p2);
        playerOrder.addPlayer(p3);

        int allPlayers = playerOrder.getAllPlayersToArrayList().size();
        assertEquals("total number of added players should match the number of players added", 3, allPlayers);
    }




}
