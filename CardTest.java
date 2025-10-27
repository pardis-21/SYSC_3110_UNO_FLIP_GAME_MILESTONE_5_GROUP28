import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testRandomCard(){
        Card card = new Card();
        assertNotNull("card should have a colour",card.getCardColour());
        assertNotNull("card should have a type",card.getCardType());


    }

    @Test
    public void testWildCard(){
        //find a wild card and makes sure it has a colour and a type
        boolean wild = false;
        for(int i = 0; i< 50; i++){
            Card card = new Card();
            if(card.getCardType() == Card.Type.WILD || card.getCardType() == Card.Type.WILD_DRAW2){
                wild = true;
                assertEquals("a wild card should have a colour",Card.Colour.RAINBOW, card.getCardColour());
            }
        }

    }

    @Test
    public void testNormalCardColourNotRainbow(){
        for(int i = 0; i<50; i++){
            Card card = new Card();
            if(card.getCardType() != Card.Type.WILD && card.getCardType() != Card.Type.WILD_DRAW2){
                assertNotEquals("non wild cards should not have rainbow colour", Card.Colour.RAINBOW, card.getCardColour());
            }
        }
    }

    @Test

    public void testPlayCardOnAnotherWildCardCanAlwaysPlay(){
        Card card = new Card();
        Card topCard = new Card();

        boolean value;
        if(card.getCardType() == Card.Type.WILD || card.getCardType() == Card.Type.WILD_DRAW2){
            value = true;

           // assertTrue("wild card should always be playable", card.playCardOnAnother(topCard));
        }
        else if(card.getCardColour() == topCard.getCardColour() || card.getCardType() == topCard.getCardType() || card.getCardColour() == Card.Colour.RAINBOW){
            value = true;

           // assertTrue("a non wild card should only be able to play if the type or colour matches the topcards", card.playCardOnAnother(topCard));
        }else{
            value = false;
        }

        boolean realvalaue = card.playCardOnAnother(topCard);
        assertEquals("the result depends whether or not the card matches the topcard",value, realvalaue);
    }

    @Test
    public void testToString(){
        Card card = new Card();
        String result = card.toString();
        assertTrue("toString() has a space between colour and type",result.contains(" "));
    }



}
