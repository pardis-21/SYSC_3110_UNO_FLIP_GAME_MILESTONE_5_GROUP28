import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the GUI for the Uno game. It displays the player's hand, discard and draw piles
 * and provides buttons for player interactions such as drawing cards, declaring UNO, and moving
 * to the next player.
 * This class is part of the MVC pattern and interacts with GameLogicModel and UnoController to
 * update and display teh current game state.
 * @Author Charis Nobossi 101297742
 * @Author Anvita Ala 101301514
 * @Author Pardis Ehsani 101300400
 * @Author Pulcherie Mbaye 101302394
 */

public class UnoViewFrame extends JFrame {


    private JPanel cardPanel; // panel for current player's cards
    public JPanel scorePanel; // panel for the player's score
    public JLabel scoreLabel;
    public JButton nextPlayerButton;

    private static final int NUMBER_OF_CARDS_BEGGING_GAME = 7;
    private ArrayList<Player> playerNames;
    private final GameLogicModel model;
    private UnoController controller;

    public JTextArea currentPlayerName;


    //PLAYER CARDS IN HAND AS BUTTONS
    private ArrayList<JButton> playerCardButtons;
    //private JButton playerCard;
    private static final int SEVEN = 7;

    private JPanel decksPanel; // panel for the discard pile, new card pile, and "UNO!" button
    public JButton discardPile;
    public JButton UNOButton;
    public JButton drawPile;


    /**
     * Constructs the UNO game frame, initialising all ui components
     * @param model the model managing the game logic
     */
    public UnoViewFrame(GameLogicModel model){
        super("Uno");
        this.model = model;
        //controller = new UnoController(model);
        this.playerCardButtons = new ArrayList<>();
        playerNames = new ArrayList<>();

        currentPlayerName = new JTextArea("");
        currentPlayerName.setEditable(false);


        //FRAME PROPERTIES
        getContentPane().setBackground(new Color(30, 120,60));
        setTitle("UNO FLIP - MILESTONE 2");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel(); // display the players cards
        decksPanel = new JPanel();
        scorePanel = new JPanel(); // display score
        discardPile = new JButton(); //shows top card on discard pile
        drawPile = new JButton("DRAW"); //pile to take a card
        UNOButton = new JButton("UNO"); // when player has one card, button shows
        nextPlayerButton = new JButton("NEXT PLAYER");


        // SETTING UP NEW CARD BUTTON
        drawPile.setPreferredSize(new Dimension(150, 150));
        drawPile.setFont(new Font("Arial", Font.BOLD, 16));
        drawPile.setBackground(Color.GRAY);
        drawPile.setForeground(Color.BLACK);

        //SETTING UP SCORE PANEL
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.BLACK);
        scorePanel.setBackground(new Color(30, 120,60));
        scorePanel.add(scoreLabel);

        // TESTING RANDOM CARD FOR DISCARD PILE
        Card topCard = new Card();
        String displaNum;
        if(topCard.getCardLightType().ordinal() <= 9) {
            displaNum = String.valueOf(topCard.getCardLightType().ordinal());
        }
        else{
            displaNum = topCard.getCardLightType().toString();
        }
        //SETTING UP DISCARD PILE BUTTON
        discardPile.setFont(new Font("Arial", Font.BOLD, 18));
        discardPile.setText(displaNum);
        discardPile.setBackground(topCard.JavaCardLightColour(topCard.getCardLightColour()));
        discardPile.setForeground(Color.BLACK);
        discardPile.setFont(new Font("Arial", Font.BOLD, 18));
        discardPile.setPreferredSize(new Dimension(150, 150));

       UNOButton.setText("UNO!!");
       UNOButton.setBackground(Color.WHITE);
       UNOButton.setForeground(Color.BLACK);
       UNOButton.setFont(new Font("Arial", Font.BOLD, 18));
       UNOButton.setPreferredSize(new Dimension(150, 150));


        // ADDING LAYOUT
        setLayout(new BorderLayout());
        decksPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 200));
        decksPanel.add(drawPile);
        decksPanel.add(discardPile);
        decksPanel.add(UNOButton);


        add(decksPanel, BorderLayout.CENTER);
        add(cardPanel, BorderLayout.SOUTH);
        add(scorePanel, BorderLayout.NORTH);
        add(currentPlayerName, BorderLayout.EAST);
        add(nextPlayerButton, BorderLayout.WEST);


        // WHEN LAYER PRESSESS NEWCARD BUTTON, ONE RANDOM CARD IS ADDED TO THEIR HAND
        // WHEN PLAYER HAND == 1, UNO BUTTON SHOWS AND THEY HAVE TO CLICK IT, IF NOT THEY GET 2 RANDOM CARDS ADDED
        // IF PLAYER ONE PLAYS, POP UP SAY PLAYER 2? PRESS OK,PLAYER 2 CAN PLAY
        // WHEN INSIDE PLAYER 2, SHOWS PLAYER 2 HAND


        cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setVisible(true);

    }

    /**
     * Updates the display of the current player's hand by creating
     * button components for each card in their hand
     * @param hand the list of cards in the player's hand
     */
    public void updateHand(List<Card> hand) {
        cardPanel.removeAll();  // clearing the old buttons
//        if (model.getTopCard().getCardLightType().equals(Card.LightType.FLIP_TO_DARK) || model.getTopCard().getCardDarkType().equals(Card.DarkType.FLIP_TO_LIGHT)){
//            model.lightMode = !model.lightMode;
//        }

        if (hand != null) {
            for (Card card : hand) {

                String label;
                JButton cardButton = new JButton();

                if (model.lightMode) {
                    label = card.getCardLightColour() + " " + card.getCardLightType();
                    cardButton.setBackground(card.JavaCardLightColour(card.getCardLightColour()));
                } else {
                    label = card.getCardDarkColour() + " " + card.getCardDarkType();
                    cardButton.setBackground(card.JavaCardDarkColour(card.getCardDarkColour()));
                }

                cardButton.setText(label);
                cardButton.setForeground(Color.BLACK);
                cardButton.setOpaque(true);
                cardButton.setContentAreaFilled(true);

                // listener
                cardButton.addActionListener(e -> controller.onCardClicked(card));

                cardPanel.add(cardButton);
            }

            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }

    //public void updateAllPlayerHands(PlayerOrder order){
      //  for (Player player: order.getAllPlayersToArrayList()){
        //    updateHand(player.getHand());
        //}
    //}


    /**
     * updates the top card displayed on the discard pile.
     * If the top card is a wild, prompts the user ti select a color
     * @param card the new top card to display
     */
    public void updateTopCard(Card card) {
        if (card == null) return;
//        if (card.getCardLightType().equals(Card.LightType.FLIP_TO_DARK) || card.getCardDarkType().equals(Card.DarkType.FLIP_TO_LIGHT)){
//            model.lightMode = !model.lightMode;
//        }
        if (model.lightMode) {
            String text = card.getCardLightColour() + " " + card.getCardLightType();  // e.g. RED THREE

            if (card.getCardLightColour() == Card.LightColour.RAINBOW) {
                String[] options = {"RED", "BLUE", "YELLOW", "GREEN"};
                Object selectedOption = JOptionPane.showInputDialog(
                        null,
                        "Please choose a colour to set the rainbow:",
                        "Selection", // Title of the dialog
                        JOptionPane.QUESTION_MESSAGE, // Message type (e.g., QUESTION_MESSAGE, INFORMATION_MESSAGE)
                        null, // Icon (null for default)
                        options, // Array of options for the dropdown
                        options[0] // Default selected option
                );
                card.setCardLightColour((String) selectedOption);
            }
            //model.playGame(card);

            discardPile.setText(text);
            discardPile.setBackground(card.JavaCardLightColour(card.getCardLightColour()));
            discardPile.setForeground(Color.BLACK);
            discardPile.setOpaque(true);
            discardPile.setContentAreaFilled(true);
        }
        else{
            String text = card.getCardDarkColour() + " " + card.getCardDarkType();  // e.g. RED THREE

            if (card.getCardDarkColour() == Card.DarkColour.RAINBOW) {
                String[] options = {"ORANGE", "TEAL", "PINK", "PURPLE"};
                Object selectedOption = JOptionPane.showInputDialog(
                        null,
                        "Please choose a colour to set the rainbow:",
                        "Selection", // Title of the dialog
                        JOptionPane.QUESTION_MESSAGE, // Message type (e.g., QUESTION_MESSAGE, INFORMATION_MESSAGE)
                        null, // Icon (null for default)
                        options, // Array of options for the dropdown
                        options[0] // Default selected option
                );
                card.setCardDarkColour((String) selectedOption);

            }
            //model.playGame(card);

            discardPile.setText(text);
            discardPile.setBackground(card.JavaCardDarkColour(card.getCardDarkColour()));
            discardPile.setForeground(Color.BLACK);
            discardPile.setOpaque(true);
            discardPile.setContentAreaFilled(true);
        }
    }


    /**
     * displays a popup message dialog with teh given message text
     * @param message message to be displayed
     */
    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Initializes and displays the current players hamd at teh start of a game
     * And clears previous cards and loads the current player's hand as buttons
     */
    //STARTING THE GAME FOR THE FIRST 7 CARDS PER PLAYER
    public void startGamePlayerButtons(){

        //JButton playerCardButton = playerCardButtons[NUMBER_OF_CARDS_BEGGING_GAME];
        //CLEARING EVERYTHING AT THE START OF THE GAME
        cardPanel.removeAll();
        cardPanel.revalidate();
        cardPanel.repaint();

        //GET THE CURRENT PLAYER FROM THE PLAYERORDER CLASS
        Player currentPlayer = model.getCurrentPlayer();
        currentPlayerName.setText(currentPlayer.getName());

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

    /**
     * updates the frame when the model notifies observers that
     * a player's turn has changed.
     */
    //called by model.notifyObserver()
    private void update(){
        model.playerTurn();
        startGamePlayerButtons();

    }

    /**
     * Sets the controller used by the view
     * @param controller the controller managing game actions
     */
    public void setController(UnoController controller) {
        this.controller = controller;
    }



}
