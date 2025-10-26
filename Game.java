import java.util.Scanner;
import java.util.*;

public class Game {
    public static void main(String[] args) {
        while (true) {
            Scanner userInput = new Scanner(System.in);

            System.out.println("Welcome to UNO FLIP");
            System.out.println("---------------------------------------");
            System.out.println("Enter the number of Players (2-4): ");
            int numPlayers = userInput.nextInt();
            System.out.println("");

            ArrayList<String> players = new ArrayList<>();

            if (numPlayers > 4 || numPlayers < 2) {
                System.out.println("Invalid number of Players!");
                System.out.println("Please try again!");
                break;

            }
            else {
                //valid number of players
                for (int i = 1; i < numPlayers + 1; i++){
                   System.out.println("Enter the player " + i + " name: ");
                   String playerName = userInput.next();
                   players.add(playerName);
                }
            }
            GameLogic gameLogic = new GameLogic(players);

            gameLogic.startGame();


        }
    }
}