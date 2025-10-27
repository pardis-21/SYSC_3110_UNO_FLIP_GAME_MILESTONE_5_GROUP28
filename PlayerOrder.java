import java.util.ArrayList;

public class PlayerOrder {

    private playerNode firstPlayer; //head
    private playerNode lastPlayer; //tail
    private int numPlayers; //num of nodes
    private playerNode currentPlayer;

    //creating a node
    private class playerNode {
        private Player pName; //data
        private playerNode prev;
        private playerNode next;

        //constructor
        public playerNode(Player p) {
            this.pName = p;
            this.prev = null;
            this.next = null;

        }
    }

    //constructor
    public PlayerOrder() {
        this.firstPlayer = null;
        this.lastPlayer = null;
        this.numPlayers = 0;
        this.currentPlayer = null;
    }
    public boolean isEmpty() {
        return numPlayers == 0;
    }

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

    //traversing forward the doubly linked list
    public void nextPlayerClockwise(){
        if (currentPlayer != null) {
            currentPlayer = currentPlayer.next;

        }
    }
    //traversing backwards the doubly linked list
    public void nextPlayerCounterClockwise(){
        if (currentPlayer != null) {
            currentPlayer = currentPlayer.prev;
        }

    }

    public ArrayList<Player> getAllPlayersToArrayList() {
        ArrayList<Player> allPlayers = new ArrayList<>();

        if (currentPlayer == null) {
            return allPlayers;

        }
        for (int i = 0; i < numPlayers; i++){
            allPlayers.add(currentPlayer.pName);
            currentPlayer = currentPlayer.next;
        }
        return allPlayers;
    }

    public int getTotalNumPlayers(){
        return numPlayers;
    }

    public Player getCurrentPlayer() {
       if (currentPlayer == null){
           return null;
       }
       else {
           return currentPlayer.pName;
       }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}