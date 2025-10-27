import java.util.Scanner;
import java.util.*;

public class Game {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        int numPlayers = 0;

        while (true) {
            System.out.println("Welcome to UNO");
            System.out.println("---------------------------------------");
            System.out.println("Enter the number of Players (2-4): ");
            numPlayers = userInput.nextInt();
            System.out.println("");

            ArrayList<String> players = new ArrayList<>();
            PlayerOrder playerOrder = new PlayerOrder();

            if (numPlayers > 4 || numPlayers < 2) {
                System.out.println("Invalid number of Players!");
                System.out.println("Please try again!");

            }
            else {
                //valid number of players
                for (int i = 1; i < numPlayers + 1; i++){
                   System.out.println("Enter the player " + i + " name: ");
                   String playerName = userInput.next();
                   players.add(playerName);
                   Player player = new Player(playerName);
                   playerOrder.addPlayer(player);

                }
            }

            //start game
            GameLogic gameLogic = new GameLogic(players);
            gameLogic.setPlayerOrder(playerOrder);
            gameLogic.playUNOGame();







        }
    }
}