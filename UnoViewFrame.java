import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UnoViewFrame extends JFrame implements UnoView {


    private JPanel cardPanel; // panel for current player's cards
    private JPanel decksPanel; // panel for the discard pile, new card pile, and "UNO!" button
    JButton drawPile;
    public JPanel scorePanel; // panel for the player's score
    public JButton nextPlayerButton;


    private JButton unoButton;
    private static final int NUMBER_OF_CARDS_BEGGING_GAME = 7;
    private ArrayList<Player> playerNames;
    private final GameLogicModel model;
    private UnoController controller;

    private JTextArea currentPlayerName;


    //PLAYER CARDS IN HAND AS BUTTONS
    private ArrayList<JButton> playerCardButtons;
    //private JButton playerCard;
    private static final int SEVEN = 7;


    public JButton discardPile;
    public JButton UNOButton;

    public int numPlayers = 0;

    public UnoViewFrame(GameLogicModel model){
        super("Uno");
        this.model = model;
        controller = new UnoController(model);
        this.playerCardButtons = new ArrayList<>();
        playerNames = new ArrayList<>();

        // ____________GET NUMBER OF PLAYERS_______________
        boolean validInput = false;

        while (!validInput) {
            String input = JOptionPane.showInputDialog(this, "Enter the number of players (2–4):");
            if (input == null) {
                // user clicked "Cancel"
                System.exit(0);
            }

            try {
                numPlayers = Integer.parseInt(input.trim());
                if (numPlayers < 2 || numPlayers > 4) {
                    JOptionPane.showMessageDialog(this, "Please enter a number between 2 and 4.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter a number between 2 and 4.");
            }
        }


        //____________________________GET PLAYER NAMES_________________
        while (playerNames.size() < numPlayers) {
            String playerName = JOptionPane.showInputDialog(this,
                    "Enter name for player " + (playerNames.size() + 1) + ":");

            if (playerName == null || playerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!");
                continue;
            }

            // Check for duplicate names
            boolean exists = false;
            for (Player player : playerNames) {
                if (player.getName().equalsIgnoreCase(playerName.trim())) {
                    JOptionPane.showMessageDialog(this, "That player already exists!");
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                playerNames.add(new Player(playerName.trim())); // create Player object
            }
        }


        //FRAME PROPERTIES
        getContentPane().setBackground(new Color(30, 120,60));
        setTitle("UNO FLIP - MILESTONE 2");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel(); // display the players cards
        decksPanel = new JPanel();

        scorePanel = new JPanel(); // display score
        discardPile = new JButton(); //shows top card on discard pile
        discardPile.setPreferredSize(new Dimension(200, 150));
        discardPile.setFont(new Font("Arial", Font.BOLD, 18));


        drawPile = new JButton("DRAW"); //pile to take a card
        UNOButton = new JButton("UNO"); // when player has one card, button shows
        nextPlayerButton = new JButton("NEXT PLAYER");

        // SETTING UP NEWCARD BUTTON
        drawPile.setPreferredSize(new Dimension(150, 150));
        drawPile.setFont(new Font("Arial", Font.BOLD, 16));
        drawPile.setBackground(Color.GRAY);
        drawPile.setForeground(Color.BLACK);

        //SETTING UP SCOREPANEL
        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.BLACK);
        scorePanel.setBackground(new Color(30, 120,60));
        scorePanel.add(scoreLabel);

        //DECK PANEL
        decksPanel.add(drawPile);

        // TESTING RANDOM CARD FOR DISCARD PILE
        Card topCard = new Card();
        String displaNum;
        if(topCard.getCardType().ordinal() <= 9) {
            displaNum = String.valueOf(topCard.getCardType().ordinal());
        }
        else{
            displaNum = topCard.getCardType().toString();
        }
        discardPile.setText(displaNum);
        discardPile.setBackground(topCard.JavaCardColour(topCard.getCardColour()));
        discardPile.setForeground(Color.BLACK);
        discardPile.setFont(new Font("Arial", Font.BOLD, 18));
        discardPile.setPreferredSize(new Dimension(150, 150));


        // ADDING LAYOUT
        setLayout(new BorderLayout());
        decksPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 200));
        decksPanel.add(discardPile);

        add(decksPanel, BorderLayout.CENTER);
        add(cardPanel, BorderLayout.SOUTH);
        add(scorePanel, BorderLayout.NORTH);
        add(nextPlayerButton, BorderLayout.WEST);


// WHEN LAYER PRESSESS NEWCARD BUTTON, ONE RANDOM CARD IS ADDED TO THEIR HAND
        // WHEN PLAYER HAND == 1, UNO BUTTON SHOWS AND THEY HAVE TO CLICK IT, IF NOT THEY GET 2 RANDOM CARDS ADDED
        // IF PLAYER ONE PLAYS, POP UP SAY PLAYER 2? PRESS OK,PLAYER 2 CAN PLAY
        // WHEN INSIDE PLAYER 2, SHOWS PLAYER 2 HAND


        cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setVisible(true);

    }
    public void updateHand(List<Card> hand){
        cardPanel.removeAll();  // clearing the old buttons

        if (hand != null) {
            for (Card card : hand) {
                String label = card.getCardColour() + " " + card.getCardType();
                JButton cardButton = new JButton(label);
                cardButton.setBackground(card.JavaCardColour(card.getCardColour()));
                cardButton.setForeground(Color.BLACK);
                cardButton.setOpaque(true);
                cardButton.setContentAreaFilled(true);
                cardPanel.add(cardButton);

                cardButton.addActionListener(e -> {
                    if (controller != null) {
                        controller.onCardClicked(card);
                    }
                });

                cardPanel.add(cardButton);
            }
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }


    public void updateTopCard(Card card){
        if (card == null) return;

        String text = card.getCardColour() + " " + card.getCardType();  // e.g. RED THREE
        discardPile.setText(text);

        discardPile.setBackground(card.JavaCardColour(card.getCardColour()));
        discardPile.setForeground(Color.BLACK);
        discardPile.setOpaque(true);
        discardPile.setContentAreaFilled(true);
    }


    public void showWildColourSelection(){

    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    //STARTING THE GAME FOR THE FIRST 7 CARDS PER PLAYER
    public void startGamePlayerButtons(){

        //JButton playerCardButton = playerCardButtons[NUMBER_OF_CARDS_BEGGING_GAME];
        //CLEARING EVERYTHING AT THE START OF THE GAME
        cardPanel.removeAll();
        cardPanel.revalidate();
        cardPanel.repaint();

        //GET THE CURRENT PLAYER FROM THE PLAYERORDER CLASS
        Player currentPlayer = model.getCurrentPlayer();

        //DEBUGGING PURPOSES CAN DELETE LATER ENSURING THAT THE CORRECT PLAYER HAND IS SHOWN
        System.out.println("SHOWING CARDS FOR: " + currentPlayer.showHand());
        String displayText;


        //CREATING THE BUTTON ARRAY FOR THE BOTTOM PANEL CARDPANEL IN THE FRAME
        List<Card> hand = controller.getCurrentPlayerHand();

        for (Card card: hand){
            PlayerCardButton playerCardButtons = new PlayerCardButton(card);
            playerCardButtons.addActionListner(controller);
            /*
            for (int i = 0; i < SEVEN; i++) {
                PlayerCardButton playerButton = new PlayerCardButton(card);
                playerCardButtons[i] = playerButton;
                playerButton.addActionListener(controller);
                playerButton.setFocusable(true);
                playerCardButtons[i].setFocusable(false);
                cardPanel.add(playerButton);
            }


             */

        }
        cardPanel.revalidate();
        cardPanel.repaint();

    }

    //called by model.notifyObserver()
    private void update(){
        model.playerTurn();
        startGamePlayerButtons();

    }
    public void setController(UnoController controller) {
        this.controller = controller;
    }



}
