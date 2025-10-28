import java.util.Scanner;
import java.util.*;
/**
 * The Game class runs the main UNO game.
 * It handles player setup, game rounds, and determines the winner
 * once a player reaches 500 points.
 * This class uses PlayerOrder and Gamelogic to manage turns and rules.
 * @Author Charis Nobossi 101297742
 * @Author Anvita Ala 101301514
 * @Author Pardis Ehsani 101300400
 */
public class Game {
    static ArrayList<String> players;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        int numPlayers = 0;
        players = new ArrayList<>();

        while (true) {
            System.out.println("Welcome to UNO");
            System.out.println("---------------------------------------");

            while (true) {
                System.out.print("Enter the number of Players (2–4): ");

                try {
                    numPlayers = userInput.nextInt();
                    userInput.nextLine(); // clear the buffer

                    if (numPlayers < 2 || numPlayers > 4) {
                        System.out.println("Invalid number of players! Please enter a number between 2 and 4.\n");
                    } else {
                        break; // valid number, exit inner loop
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number between 2 and 4.\n");
                    userInput.nextLine(); // clear the invalid input
                }
            }


        PlayerOrder playerOrder = new PlayerOrder();

            while (players.size() < numPlayers) {
                System.out.print("Enter the player name: ");
                String playerName = userInput.nextLine();

                if (players.contains(playerName)) {
                    System.out.println("That player already exists.\n");
                    continue;
                }

                players.add(playerName);
                Player player = new Player(playerName);
                playerOrder.addPlayer(player);
            }

            //start game

            GameLogic gameLogic;
            final int TARGET = 500;

            while (true) {
                // create a new game for each round
                gameLogic = new GameLogic(players);
                gameLogic.setPlayerOrder(playerOrder);

                // play one round
                gameLogic.playUNOGame();

                // check if someone reached 500
                Player matchWinner = gameLogic.getMatchWinner(TARGET);

                if (matchWinner != null) {
                    System.out.println("🏆 " + matchWinner.getName() + " reached " + TARGET + " points!");
                    System.out.println("GAME OVER!");
                    break; // stop entire match
                }

                // otherwise, let user start new round
                Scanner in = new Scanner(System.in);
                System.out.println("No winner yet. Type 'NEW' to start a new round");
                while (!in.nextLine().trim().equalsIgnoreCase("NEW")) {
                    System.out.println("Type 'NEW' to start a new round");
                }
            }







        }
    }
}