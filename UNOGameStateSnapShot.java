import java.io.Serializable;
import java.util.*;

public class UNOGameStateSnapShot implements Serializable {

    public final List<Player> playersInOrder;
    public final int currentPlayerIndex;

    public final List<Card> drawPile;
    public final List<Card> discardPile;

    public final Map<Player, Integer> scores;

    public final boolean lightMode;
    public final boolean direction;
    public final boolean roundEnded;

    public UNOGameStateSnapShot(GameLogicModel game) {
        // Store references directly
        this.playersInOrder = new ArrayList<>(game.playerOrder.getAllPlayersToArrayList());
        this.currentPlayerIndex = game.playerOrder.getAllPlayersToArrayList().indexOf(game.playerOrder.getCurrentPlayer());

        this.drawPile = new ArrayList<>(game.drawPile);
        this.discardPile = new ArrayList<>(game.discardPile);

        this.scores = new HashMap<>(game.scores);

        this.lightMode = game.lightMode;
        this.direction = game.direction;
        this.roundEnded = game.roundEnded;
    }

    public void restore(GameLogicModel game) {

        // 1) Restore PlayerOrder
        PlayerOrder newOrder = new PlayerOrder();
        for (Player p : playersInOrder) {
            newOrder.addPlayer(p); // just add the existing object
        }

        // 2) Set current player
        if (!playersInOrder.isEmpty() && currentPlayerIndex >= 0 && currentPlayerIndex < playersInOrder.size()) {
            newOrder.setCurrentPlayer(playersInOrder.get(currentPlayerIndex));
        }

        game.playerOrder = newOrder;

        // 3) Restore piles
        game.drawPile.clear();
        game.drawPile.addAll(drawPile);

        game.discardPile.clear();
        game.discardPile.addAll(discardPile);

        // 4) Restore scores
        game.scores.clear();
        game.scores.putAll(scores);

        // 5) Restore mode
        game.lightMode = lightMode;
        game.direction = direction;
        game.roundEnded = roundEnded;
    }
}
