import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
/**
 * This test class tests the different methods in the Card Class.
 *
 * @Author Anvita Ala 101301514
 * @Author Charis Nobossi 101297742
 * @Author Pulcherie Mbaye 101302394
 * @Author Pardis Ehsani 101300400
 */
public class CardTest {

    @Test
    public void testRandomCard(){
        Card card = new Card();
        assertNotNull("card should have a colour",card.getCardLightColour());
        assertNotNull("card should have a type",card.getCardLightType());


    }

    @Test
    public void testWildCard(){
        //find a wild card and makes sure it has a colour and a type
        boolean wild = false;
        for(int i = 0; i< 50; i++){
            Card card = new Card();
            if(card.getCardLightType() == Card.LightType.WILD || card.getCardLightType() == Card.LightType.WILD_DRAW2){
                wild = true;
                assertEquals("a wild card should have a colour", Card.LightColour.RAINBOW, card.getCardLightColour());
            }
        }

    }

    @Test
    public void testNormalCardColourNotRainbow(){
        for(int i = 0; i<50; i++){
            Card card = new Card();
            if(card.getCardLightType() != Card.LightType.WILD && card.getCardLightType() != Card.LightType.WILD_DRAW2){
                assertNotEquals("non wild cards should not have rainbow colour", Card.LightColour.RAINBOW, card.getCardLightColour());
            }
        }
    }

    @Test

    public void testPlayCardOnAnotherWildCardCanAlwaysPlay(){
        Card card = new Card();
        Card topCard = new Card();

        boolean value;
        if(card.getCardLightType() == Card.LightType.WILD || card.getCardLightType() == Card.LightType.WILD_DRAW2){
            value = true;

           // assertTrue("wild card should always be playable", card.playCardOnAnother(topCard));
        }
        else if(card.getCardLightColour() == topCard.getCardLightColour() || card.getCardLightType() == topCard.getCardLightType() || card.getCardLightColour() == Card.LightColour.RAINBOW){
            value = true;

           // assertTrue("a non wild card should only be able to play if the type or colour matches the topcards", card.playCardOnAnother(topCard));
        }else{
            value = false;
        }

        boolean realvalaue = card.playCardOnAnother(topCard);
        assertEquals("the result depends whether or not the card matches the topcard",value, realvalaue);
    }

    @Test
    public void testPointsForCard(){
        Card c1 = new Card();
        assertTrue(Card.pointsForCard(c1) >= 0);
    }

    @Test
    public void testPointsForHand(){

        ArrayList<Card> hand= new ArrayList<>();
        Card c1 = new Card();
        Card c2 = new Card();
        hand.add(c1);
        hand.add(c2);

        int value = Card.pointsForCard(c1) + Card.pointsForCard(c2);
        int valueHand = Card.pointsForHand(hand);

        assertEquals(value, valueHand);
    }



    @Test
    public void testToString(){
        Card card = new Card();
        String result = card.toString();
        assertTrue("toString() has a space between colour and type",result.contains(" "));
    }



}
