import java.util.*;
import java.util.Scanner;


public class GameLogic{
    private PlayerOrder playerOrder;
    private ArrayList<Card> cards;
    private final ArrayList<Card> discardPile;
    private final ArrayList<Card> drawPile;
    //private ArrayList<Card> flipPile; for future use
    private boolean direction; //clockwise or counterclockwise
    private final Map<Player, Integer> scores = new HashMap<>();
    private static final int SEVEN = 7;


    public GameLogic(ArrayList<String> playerNames) {

        //creating an arrayList of players

        //instance of that class
        playerOrder = new PlayerOrder();

        //adding players to doublylinkedlist
        for (int i = 0; i < playerNames.size(); i++){
            Player player = new Player(playerNames.get(i));
            playerOrder.addPlayer(player);
        }

        initScores();

        discardPile = new ArrayList<>();

        // for future use flipPile = new ArrayList<>();

        //populating the cards with a card to make a deck (108 cards)
        drawPile = new ArrayList<>();
        for (int i = 0; i < 108; i++){
            drawPile.add(new Card());
        }

        //assuming by UNO rules that all players have same age and starting from CW direction
        direction = true; //clockwise direction
    }

    private void initScores() {
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            scores.put(player, 0);
        }
    }

    //at the beginning of the game, each player is dealt 7 cards
    private void dealCardsBeginning(){
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            if (player.getHand().size() == 7){
                break;
            }
            else {
                for (int i = 0; i < SEVEN; i++){
                        player.getHand().add(drawPile.get(0));
                        drawPile.remove(drawPile.get(0));
                    }

            }
        }
        //this is to make sure the top card isn't a special card
        while (
                drawPile.get(0).getCardType() == Card.Type.DRAW_ONE ||
                        drawPile.get(0).getCardType() == Card.Type.WILD_DRAW2 ||
                        drawPile.get(0).getCardType() == Card.Type.WILD ||
                        drawPile.get(0).getCardType() == Card.Type.REVERSE ||
                        drawPile.get(0).getCardType() == Card.Type.SKIP
        ) {
            Card topCard = drawPile.remove(0);
            drawPile.add(topCard);
        }

        discardPile.add(0, drawPile.remove(0));

    }

    public Card getTopCard(){
        return discardPile.get(0);
    }

    public void startGame() {
        //starting round
        dealCardsBeginning();
        System.out.println();
        discardPile.get(0);
        System.out.println("Top Card: " + discardPile.get(0));
        System.out.println();

    }

    public void confirmPlayerAtScreen(){
        Scanner userInput= new Scanner(System.in);
        System.out.println("Player: " + playerOrder.getCurrentPlayer().getName() + "'s turn\n");
        Boolean flag = true;
        while (flag) {

            System.out.println("Is player: " + playerOrder.getCurrentPlayer().getName() + " at the screen?");
            String userChoice = userInput.nextLine();

            if (userChoice.equalsIgnoreCase("yes") || userChoice.equalsIgnoreCase("y")) {
                //playerTurn();
                flag = false;
            }
            else if (userChoice.equalsIgnoreCase("no") || userChoice.equalsIgnoreCase("n")) {
                System.out.println("Pass the computer");
                //playerTurn();


            }
            else {
                System.out.println("Enter Yes or No");
            }


        }
    }

    public void playGame() {
        Scanner userInput = new Scanner(System.in);

        System.out.println("These are your cards: ");
        boolean flag = true;
        while (flag) {
            System.out.println(playerOrder.getCurrentPlayer().showHand());
            System.out.println(playerOrder.getCurrentPlayer().getName()  + ": What is the index of the card you would like to play? (Or enter 0 to draw a card) ");


            try {
               int choice = userInput.nextInt();

                //if the player wants to draw a card
                if (choice == 0) {
                    playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                    System.out.println(playerOrder.getCurrentPlayer().getName() + " has drawn the following card: " + drawPile.get(0).getCardColour() + " " + drawPile.get(0).getCardType());
                    drawPile.remove(0);
                    playerTurn();
                    flag = false;
                }
                //check if input is valid but not 0
                else if (choice > 0 && choice <= playerOrder.getCurrentPlayer().getHand().size()) {
                    Card card = playerOrder.getCurrentPlayer().getHand().get(choice - 1);

                    if (card.playCardOnAnother(getTopCard())) {
                        if (card.getCardType().equals(Card.Type.REVERSE)) {
                            direction = !direction;
                            playerOrder.getCurrentPlayer().getHand().remove(card);
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has played the following card: " + card.getCardColour() + " " + card.getCardType());
                            System.out.println("Order of game has been reversed\n");

                        }
                        else if (card.getCardType().equals(Card.Type.SKIP)) {

                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has played the following card: " + card.getCardColour() + " " + card.getCardType());
                            playerOrder.getCurrentPlayer().getHand().remove(card);

                            //if direction is CW
                            if (direction) {
                                playerOrder.nextPlayerClockwise();
                                System.out.println( playerOrder.getCurrentPlayer().getName() + "'s turn has been skipped\n");

                            }
                            //if direction is CCW
                            else {
                                playerOrder.nextPlayerCounterClockwise();
                                System.out.println( playerOrder.getCurrentPlayer().getName() + "'s turn has been skipped\n");
                            }

                        }
                        else if (card.getCardType() == Card.Type.WILD) {
                            System.out.println("WILD card played. Enter the colour (RED, GREEN, BLUE, YELLOW):");

                            // If you previously used nextInt()/next() elsewhere, clear the leftover newline once:
                            if (userInput.hasNextLine()) userInput.nextLine();

                            flag = true;
                            while (flag) {
                                String colour = userInput.nextLine().trim().toUpperCase();

                                if (!colour.equals("RED") && !colour.equals("GREEN") &&
                                        !colour.equals("BLUE") && !colour.equals("YELLOW")) {
                                    System.out.println("Invalid colour. Please try again (RED, GREEN, BLUE, YELLOW):");
                                } else {
                                    card.setCardColour(colour); // if this expects String
                                    flag = false;
                                    playerOrder.getCurrentPlayer().getHand().remove(card);

                                }
                            }
                        }
                        else if (card.getCardType().equals(Card.Type.WILD_DRAW2)) {
                            System.out.println("WIlD DRAW2 card has been played. Enter the colour that will be played next: (RED, GREEN, BLUE, YELLOW)");
                            playerOrder.getCurrentPlayer().getHand().remove(card);

                            if (userInput.hasNextLine()) userInput.nextLine();

                            flag = true;
                            while (flag) {
                                String colour = userInput.nextLine().trim().toUpperCase();

                                if (!colour.equals("RED") && !colour.equals("GREEN") &&
                                        !colour.equals("BLUE") && !colour.equals("YELLOW")) {
                                    System.out.println("Invalid colour. Please try again (RED, GREEN, BLUE, YELLOW):");
                                } else {
                                    card.setCardColour(colour); // if setCardColour takes String
                                    flag = false;
                                }
                            }


                            playerTurn();
                            playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has drawn the following card: " + drawPile.get(0).getCardColour() + " " + drawPile.get(0).getCardType());
                            drawPile.remove(0);

                            playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has drawn the following card: " + drawPile.get(0).getCardColour() + " " + drawPile.get(0).getCardType());
                            drawPile.remove(0);


                        }

                        else if (card.getCardType().equals(Card.Type.DRAW_ONE)) {
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has played the following card: " + card.getCardColour() + " " + card.getCardType());
                            playerOrder.getCurrentPlayer().getHand().remove(card);

                            playerTurn();
                            playerOrder.getCurrentPlayer().getHand().add(drawPile.get(0));
                            System.out.println(playerOrder.getCurrentPlayer().getName() + " has drawn the following card: " + drawPile.get(0).getCardColour() + " " + drawPile.get(0).getCardType());
                            drawPile.remove(0);


                        }
                        else{
                            System.out.println( playerOrder.getCurrentPlayer().getName() + " has played the following card: " +  card.getCardColour() + " " + card.getCardType());
                            playerOrder.getCurrentPlayer().getHand().remove(card);
                        }

                        discardPile.add(0, card);

                        if(playerOrder.getCurrentPlayer().getHand().isEmpty()){
                            awardRoundPointsTo(playerOrder.getCurrentPlayer());
                        }

                        playerTurn();
                        flag = false;

                    }
                    else {
                        System.out.println("!!!Card cannot be played. Choose another or enter 0 to draw a card!!! ");
                        System.out.println();
                    }
                } else {
                    System.out.println("Invalid input. Try again");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Try again");
                userInput.nextLine();
            }
        }

    }

    private void awardRoundPointsTo(Player winner) {
        int pointsgained = 0;
        for (Player player : playerOrder.getAllPlayersToArrayList()) {
            if (player != winner) {
                pointsgained += Card.pointsForHand(player.getHand());
            }
        }

        // Updating the winner's score
        scores.put(winner, scores.get(winner) + pointsgained);

        System.out.println(winner.getName() + " earns " + pointsgained + " points! Total = " + scores.get(winner));
    }

    public void playerTurn() {
        //checking the players turn status
        if (direction) {
            playerOrder.nextPlayerClockwise();
        }
        else {
            playerOrder.nextPlayerCounterClockwise();
        }
    }
    public void setPlayerOrder(PlayerOrder playerOrder){
        this.playerOrder = playerOrder;
    }
    public void playUNOGame(){


        boolean flag = true;

        startGame();
        while(flag){
            confirmPlayerAtScreen();
            System.out.println("Top card: " + getTopCard() + "\n");
            playGame();
    }
        }


}