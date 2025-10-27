import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
	private Player player;
	private ArrayList<Card> discardPile;

	// runs before each test
	@Before
	public void setUp(){
		player = new Player("John");
		discardPile = new ArrayList<>();
	}

	//testing
	@Test
	public void testgetName(){
		assertEquals("John", player.getName());
  }

  @Test
  public void testgetHand(){
		// the hand is initially empty
	  assertTrue("players hand is empty",player.getHand().isEmpty());
  }

//  @Test
//  public void testhasOneCard(){
//		Card card = new Card();
//		player.getHand().add(card);
//		assertTrue("player has one card",player.hasOneCard());
//  }

  @Test
  public void testhasZeroCard(){
		assertTrue("player has zero cards", player.hasZeroCard());
  }

//  @Test
//  public void testsetPlayerTurnTrue(){
//		player.setPlayerTurnTrue();
//		assertTrue("players turn is set to true", player.getPlayerTurnStatus());
//  }
//
//  @Test
//  public void testsetPlayerTurnFalse(){
//		player.setPlayerTurnFalse();
//		assertFalse("players turn us set to false",player.getPlayerTurnStatus());
//	}

	@Test
  public void testplayCard() {
	  player.getHand().add(new Card());
	  player.getHand().add(new Card());
	  player.getHand().add(new Card());
	  Card topcard = new Card();

	  // play the first card
	  boolean playing = player.playCard(0, topcard, discardPile);

	  assertNotNull(playing);
	  if (playing) {
		  assertEquals("players card is valid therefore hand size decreased by 1",2, player.getHand().size());
		  assertEquals("players card is valid therefore discard pile increase by 1",1, discardPile);
	  } else {
		  assertEquals("the players card is invalid there the hand size stays the same",3, player.getHand().size());
		  assertTrue("the pile is empty",discardPile.isEmpty());
	  }
  }

  @Test
  public void testplayCardInvalidIndex(){
		Card topCard = new Card();
		Card card = new Card();
		player.getHand().add(card);
		boolean result = player.playCard(3, topCard, discardPile);
		assertFalse("Invalid index should return False", result);
  }

  @Test
  public void testShowHand(){
		player.getHand().add(new Card());
		String printed = player.showHand();
		assertEquals(" ", printed);
  }





//        Card.Colour colour = topcard.getCardColour();
//        colour = Card.Colour.RED; // set the top card colour to red
//        Card.Type type = topcard.getCardType();
//        type = Card.Type.FIVE; // set the top card type to 5 // enum is immutable
//
//      Card playerCard = new Card();
//      Card.Colour playerColour = topcard.getCardColour();
//      playerColour = Card.Colour.RED; // set the top card colour to red
//      Card.Type playerType = topcard.getCardType();
//      playerType = Card.Type.THREE; // set the top card type to 5
//
//      player.getHand().add(playerCard);
//      boolean result = player.playCard(0, topcard, discardPile);
//
//      assertTrue(result);
//      assertTrue(discardPile.contains(playerCard));
//      assertTrue(player.getHand().isEmpty());






}
