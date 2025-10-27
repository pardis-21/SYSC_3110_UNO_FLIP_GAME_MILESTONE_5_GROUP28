import java.util.Scanner;
import java.util.*;

public class Game {
    static ArrayList<String> players;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        int numPlayers = 0;
        players = new ArrayList<>();

        while (true) {
            System.out.println("Welcome to UNO");
            System.out.println("---------------------------------------");
            System.out.println("Enter the number of Players (2-4): ");

                try {
                    numPlayers = userInput.nextInt();
                    userInput.nextLine(); // clear the buffer

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number between 2 and 4.\n");
                    userInput.nextLine(); // clear the invalid input
                    continue;
                }


        PlayerOrder playerOrder = new PlayerOrder();

            if (numPlayers > 4 || numPlayers < 2) {
                System.out.println("Invalid number of Players!");
                System.out.println("Please try again!");

            }
            else {
                //valid number of players
                while (players.size() < numPlayers){
                   System.out.println("Enter the player name: ");
                   String playerName = userInput.next();
                   if (players.contains(playerName)){
                       System.out.println("That player already exists.");
                       continue;
                   }
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