import java.util.ArrayList;

/**
 * This class manages the player order implementation, because there are both SKIP and REVERSE cards
 * a circular doubly linkedlist implementation was used. It keeps track of the current player, the previous
 * player and the next player in the order by either moving clockwise or counterclockwise, depending on the game.
 * This class also adds players in order and retrieves them in order.
 *
 * @Author Pardis Ehsani 101300400
 * @Author Charis Nobossi 101297742
 * @Author Anvita Ala 101301514
 * @Author Pulcherie Mbaye 101302394
 */

public class PlayerOrder {

    private playerNode firstPlayer; //head
    private playerNode lastPlayer; //tail
    public int numPlayers; //num of nodes
    private playerNode currentPlayer;
    //creating a node

    //creating a playerNode for CDLL
    private class playerNode {
        private Player pName; //data
        private playerNode prev;
        private playerNode next;

        /**
         * Creates a new Player node with the given player from player class
         *
         * @param p the player that will be stored as data in the node
         */
        //constructor
        public playerNode(Player p) {
            this.pName = p;
            this.prev = null;
            this.next = null;

        }
    }

    /**
     * Inititializes an empty player order
     */
    //constructor
    public PlayerOrder() {
        this.firstPlayer = null;
        this.lastPlayer = null;
        this.numPlayers = 0;
        this.currentPlayer = null;
    }

    /**
     * Checks if there are any players in the nodes/order
     *
     * @return true if there are no players that exist, false otherwise
     */
    public boolean isEmpty() {
        return numPlayers == 0;
    }

    /**
     * Adds a player to a player node (circularly doubly linked-list style) when called upon to link to other player nodes
     *
     * @param p a player from the Player class to be addedd to the order
     */
    //adding players to the linkedList
    public void addPlayer(Player p) {
        playerNode tempPlayerNode = new playerNode(p);

        if (firstPlayer == null) {

            firstPlayer = tempPlayerNode;
            firstPlayer.next = firstPlayer;
            firstPlayer.prev = firstPlayer;

            lastPlayer = firstPlayer;
            lastPlayer.next = firstPlayer;
            lastPlayer.prev = lastPlayer;

            currentPlayer = tempPlayerNode;
        }
        //already a node in CDLL
        else {
            lastPlayer.next = tempPlayerNode;
            tempPlayerNode.prev = lastPlayer;
            tempPlayerNode.next = firstPlayer;
            firstPlayer.prev = tempPlayerNode;
            lastPlayer = tempPlayerNode;

        }
        numPlayers++;
    }

    /**
     * Moves the next players turn with direction being clockwise
     */
    //traversing forward the doubly linked list
    public void nextPlayerClockwise(){
        if (currentPlayer != null) {
            currentPlayer = currentPlayer.next;

        }
    }
    /**
     * Moves the next players turn with direction being counterclockwise
     */
    //traversing backwards the doubly linked list
    public void nextPlayerCounterClockwise(){
        if (currentPlayer != null) {
            currentPlayer = currentPlayer.prev;
        }

    }

    /**
     * Returns all the players from the CDLL as an ArrayList
     *
     * @return list of players in order of added into CDLL
     */
    public ArrayList<Player> getAllPlayersToArrayList() {
        ArrayList<Player> allPlayers = new ArrayList<>();

        if (currentPlayer == null) {
            return allPlayers;

        }

        playerNode temp = currentPlayer;

        for (int i = 0; i < numPlayers; i++){
            //allPlayers.add(currentPlayer.pName);
            allPlayers.add(temp.pName);
            temp = temp.next;

            //currentPlayer = currentPlayer.next;
        }
        return allPlayers;
    }

    /**
     * Returns the currents player turn in the game
     *
     * @return the current player in the game, if there are no players, then is null
     */
    public Player getCurrentPlayer() {
       if (currentPlayer == null){
           return null;
       }
       else {
           return currentPlayer.pName;
       }
    }

    public boolean setCurrentPlayer(Player p){
        if (currentPlayer == null){
            return false;
        }
        playerNode tempPlayer = currentPlayer;
        for (int i = 0; i < numPlayers; i++){
            if (tempPlayer.pName.equals(p)){
                currentPlayer = tempPlayer;
                return true;
            }
            tempPlayer = tempPlayer.next;
        }
        return false;

    }


    public void setToFirstPlayer(){
        currentPlayer = firstPlayer;
    }

    /**
     * Returns a string representation of the order of the PlayerOrder class
     */
    @Override
    public String toString() {
        if (isEmpty()){
            return " No Players";
        }
        return super.toString();
    }
}