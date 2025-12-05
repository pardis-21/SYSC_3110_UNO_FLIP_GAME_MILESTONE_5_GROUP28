public class PlayerCardCommand implements UNOCommand{

    private GameLogicModel gameLogicModel;
    private Player currentPlayer;
    private Card currentTopCard;
    private Card prevoiusTopCard;
    private Player previousCurrentPlayer;


    public PlayerCardCommand(GameLogicModel model, Player player, Card cardPlayed, Card previousCard) {
        this.gameLogicModel = model;
        this.currentPlayer = player;
        this.currentTopCard = cardPlayed;
        this.prevoiusTopCard = previousCard;
        //this.previousCurrentPlayer = player;
    }


    @Override
    public void undoAction(){
        if (!gameLogicModel.getDiscardPile().isEmpty() && gameLogicModel.getDiscardPile().get(0) == currentTopCard) {
            gameLogicModel.getDiscardPile().remove(currentTopCard); //or 0 because we check if its the same
        }
        //remove the card from the card thats in the discard pile and then place
        //it back into the players hand (or current players hand cards)
        //gameLogicModel.getDiscardPile().removeLast();
        currentPlayer.getHand().add(currentTopCard);

        if (prevoiusTopCard != null) {
            //gameLogicModel.setTopCard(prevoiusTopCard);
            gameLogicModel.getDiscardPile().add(0, prevoiusTopCard); //at the top of the discard pile

            gameLogicModel.getCurrentPlayer();
        }
        //gameLogicModel.setCurrentPlayer(previousCurrentPlayer);
       // gameLogicModel.notify();
    }
    public void redoAction(){

        previousCurrentPlayer.getHand().remove(currentTopCard);

        if (prevoiusTopCard != null && gameLogicModel.getDiscardPile().isEmpty() && gameLogicModel.getDiscardPile().get(0) == currentTopCard) {

            gameLogicModel.getDiscardPile().remove(0); //remove the top card
        }
        //gameLogicModel.setTopCard(currentTopCard);
        //gameLogicModel.setCurrentPlayer(previousCurrentPlayer);
        gameLogicModel.getDiscardPile().add(0, currentTopCard); //put the card back on the discard pile at the top
        //advance to the next player
        //gameLogicModel.playerTurn();
        //gameLogicModel.notify();
    }
}
