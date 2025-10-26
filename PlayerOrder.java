import  java.util.*;

public class PlayerOrder {
    private LinkedList<Player> players;
    private Player player;

    public class playerNode {
        Player pName;
        playerNode prev;
        playerNode next;

        public playerNode(Player p) {
            this.pName = p;
            this.prev = null;
            this.next = null;

        }
    }
    public class playerDoublyLinkedList {
        playerNode firstPlayer;
        playerNode lastPlayer;

        public playerDoublyLinkedList() {
            this.firstPlayer = null;
            this.lastPlayer = null;
        }


    //adding players to the linkedList
    public void addPlayer(Player p) {
        playerNode tempPlayerNode = new playerNode(p);

        if (players == null || players.size() == 0) {
            System.out.println("There are no players to add!");
        }
        else if (firstPlayer == null){
            firstPlayer = tempPlayerNode;
            lastPlayer = tempPlayerNode;
        }
        else {
          lastPlayer = tempPlayerNode;
          tempPlayerNode.prev = lastPlayer;
          lastPlayer.next = tempPlayerNode;
        }
    }

    //traversing forward the doubly linked list
    public void nextPlayerClockwise(){
        playerNode currentPlayer = firstPlayer;
        while (currentPlayer != null) {
            System.out.println("Its "+ currentPlayer.pName + "'s turn!");
            currentPlayer = currentPlayer.next;
        }
    }

    public void nextPlayerCounterClockwise(){
        playerNode currentPlayer = lastPlayer;
        while (currentPlayer != null) {
            System.out.println("Its "+ currentPlayer.pName + "'s turn!");
            currentPlayer = currentPlayer.prev;
        }
    }

}
}