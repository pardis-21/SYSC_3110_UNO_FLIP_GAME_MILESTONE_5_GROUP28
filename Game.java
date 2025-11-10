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
    static public ArrayList<Player> players = new ArrayList<>();
    public int numPlayers;
    PlayerOrder playerOrder = new PlayerOrder();

    public Game() {
        Scanner userInput = new Scanner(System.in);
        numPlayers = 0;

        System.out.println("Welcome to UNO");
        System.out.println("---------------------------------------");

//       ____________________CODE MOVED TO UNOFRAME________________
//        // get number of players
//        while (true) {
//            System.out.print("Enter the number of Players (2–4): ");
//            try {
//                numPlayers = userInput.nextInt();
//                userInput.nextLine();
//                if (numPlayers < 2 || numPlayers > 4) {
//                    System.out.println("Invalid number of players! Please enter 2–4.\n");
//                } else break;
//            } catch (InputMismatchException e) {
//                System.out.println("Invalid input! Please enter a number between 2 and 4.\n");
//                userInput.nextLine();
//            }
//        }
//
//        // get player names
//        while (players.size() < numPlayers) {
//            System.out.print("Enter player name: ");
//            String playerName = userInput.nextLine().trim();
//
//            //make sure 2 players by same name don't exist
//            boolean exists = false;
//            for (Player player : players) {
//                if (player.getName().equals(playerName)) {
//                    System.out.println("That player already exists.\n");
//                    exists = true;
//                    break;
//                }
//            }
//            if (!exists) {
//                addNewPlayer(playerName);
//            }
//        }


        //I HAD TO COMMENT OUT FOR GUI TO SHOW

//        // initialize GameLogic ONCE, so scores persist
//        GameLogic gameLogic = new GameLogic(players);
//        gameLogic.setPlayerOrder(playerOrder);
//        final int TARGET = 500;
//
//        // play multiple rounds until someone wins the match
//        while (true) {
//            System.out.println("\n===== STARTING NEW ROUND =====\n");
//            gameLogic.playUNOGame();
//
//            Player matchWinner = gameLogic.getMatchWinner(TARGET);
//            if (matchWinner != null) {
//                System.out.println(matchWinner.getName() + " reached " + TARGET + " points!");
//                System.out.println("GAME OVER!");
//                break;
//            }
//
//            System.out.println("No one has reached " + TARGET + " points yet. Press ENTER to start a new round.");
//            userInput.nextLine();
//        }

    }

    public static void main(String[] args) {
            GameLogicModel model = new GameLogicModel();

            PlayerOrder order = new PlayerOrder();
            order.addPlayer(new Player("Player 1"));
            order.addPlayer(new Player("Player 2"));
            model.setPlayerOrder(order);

            UnoViewFrame view = new UnoViewFrame(model);

            UnoController controller = new UnoController(model);

            view.setController(controller);
            controller.setView(view);
        }






}
