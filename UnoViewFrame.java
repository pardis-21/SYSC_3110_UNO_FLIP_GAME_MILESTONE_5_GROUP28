import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

public class UnoViewFrame extends JFrame implements Serializable {

    private JPanel cardPanel; // panel for current player's cards
    public JPanel scorePanel; // panel for the player's score
    public JPanel currentPlayerPanel;
    public JPanel roundPanel;
    public JLabel scoreLabel;
    public JLabel currentPlayerLabel;
    //making a top panel with both the score and the round and the current player name cuz i want it to be cleaner
    public JPanel topPanel;
    //makeing a redo/undo panel for clearness and for these buttons to not overlap antyhign
    public JPanel undoredoPanel;

    public JButton nextPlayerButton;
    //private ArrayList<Player> playerNames;
    private final GameLogicModel model;
    private UnoController controller;
    public JTextArea currentPlayerName;
    //PLAYER CARDS IN HAND AS BUTTONS
    private ArrayList<JButton> playerCardButtons;
    //private JButton playerCard;
    private static final int SEVEN = 7;
    private JPanel decksPanel; // panel for the discard pile, new card pile, and "UNO!" button


    //EVERY BUTTON NEEDS TO BE PRIVATE, CAN NOT HAVE PUBLIC FIELDS
    //NEED TO CHANGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!111
    public JButton discardPile;
    public JButton UNOButton;
    public JButton drawPile;
    //undo button
    public JButton undoButton;
    //redo button
    public JButton redoButton;
    //label for the round at the top of the screen
    public JLabel roundLabel;
    public int roundNumber;


    public JMenuItem saveItem;
    public JMenuItem loadItem;

    /**
     * Constructs the UNO game frame, initialising all ui components
     * @param model the model managing the game logic
     */
    public UnoViewFrame(GameLogicModel model){
        super("Uno");
        this.model = model;
        controller = new UnoController(model);
        this.playerCardButtons = new ArrayList<>();
        //playerNames = new ArrayList<>();


        //FRAME PROPERTIES
        getContentPane().setBackground(new Color(30, 120,60));
        setTitle("UNO FLIP - MILESTONE 4");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel(); // display the players cards
        decksPanel = new JPanel();
        scorePanel = new JPanel(); // display score
        currentPlayerPanel = new JPanel(); //display the current players name
        topPanel = new JPanel(); //top panel
        discardPile = new JButton(); //shows top card on discard pile
        roundPanel = new JPanel(); //shows the round panel and should be above the score
        drawPile = new JButton("DRAW"); //pile to take a card
        UNOButton = new JButton("UNO"); // when player has one card, button shows
        nextPlayerButton = new JButton("NEXT PLAYER");


        // SETTING UP NEW CARD BUTTON
        drawPile.setPreferredSize(new Dimension(150, 150));
        drawPile.setFont(new Font("Arial", Font.BOLD, 16));
        drawPile.setBackground(Color.GRAY);
        drawPile.setForeground(Color.BLACK);

        //ADDING A ROUND LABEL AT THE TOP TO INDICATE WHICH ROUND WERE AT
        roundLabel = new JLabel("Round: " + roundNumber);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roundLabel.setForeground(Color.BLACK);
        roundLabel.setBackground(Color.WHITE);
        roundNumber = 1;

        //SETTING UP THE ROUND PANEL
        roundPanel.setLayout(new FlowLayout());
        roundPanel.add(roundLabel);

        //SETTING UP SCORE PANEL

        scoreLabel = new JLabel("Score: 0");  //need to add the score here
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(Color.BLACK);
        scorePanel.setBackground(new Color(153, 255,255));
        scorePanel.add(scoreLabel);

        //SETTING UP THE CURRENT PLAYER NAME PANEL AND CURRENT PLAYER NAME LABEL
        currentPlayerLabel = new JLabel("Current Player: " + currentPlayerName);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        currentPlayerLabel.setForeground(Color.BLACK);
        currentPlayerPanel.setBackground(Color.WHITE);
        currentPlayerPanel.add(currentPlayerLabel);


        //SHOWCASING THE UNDO AND THE REDO BUTTONS
        undoButton = new JButton("UNDO");
        redoButton = new JButton("REDO");
        undoButton.setPreferredSize(new Dimension(100, 100));
        redoButton.setPreferredSize(new Dimension(100, 100));
        undoButton.setFont(new Font("Arial", Font.BOLD, 18));
        redoButton.setFont(new Font("Arial", Font.BOLD, 18));
        undoButton.setVisible(true);
        redoButton.setVisible(true);
        undoButton.setBackground(Color.WHITE);
        redoButton.setBackground(Color.WHITE);

        //ADDING UNDO AND REDO BUTTONS TO THE FRAME
        undoredoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        //undoredoPanel.setOpaque(false);

        undoredoPanel.add(undoButton);
        undoredoPanel.add(redoButton);

        //SETTING THE TOP PANEL WITH BOTH SCOREPANEL AND ROUNDPANEL AND PLAYERNAMEPANEL
        topPanel.setBackground(new Color(30, 120,60));
        topPanel.setPreferredSize(new Dimension(200, 200));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(roundPanel, BorderLayout.NORTH);
        topPanel.add(scorePanel, BorderLayout.CENTER);
        topPanel.add(currentPlayerPanel, BorderLayout.SOUTH);

        //topPanel.add(currentPlayerName, BorderLayout.SOUTH);
        topPanel.add(undoredoPanel, BorderLayout.EAST);


        //topPanel.add(undoredoPanel, BorderLayout.EAST);

        //this.add(currentPlayerName, BorderLayout.EAST);

        //this.add(topPanel, BorderLayout.NORTH);
        //this.add(undoredoPanel, BorderLayout.EAST);


        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        saveItem = new JMenuItem("Save Game");
        saveItem.setActionCommand("SaveGame");

        loadItem = new JMenuItem("Load Game");
        loadItem.setActionCommand("LoadGame");

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);



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

        // ADDING THE LAYOUT TO THE JFRAME
        this.add(decksPanel, BorderLayout.CENTER);
        this.add(cardPanel, BorderLayout.SOUTH);
        this.add(nextPlayerButton, BorderLayout.WEST);
        this.add(topPanel, BorderLayout.NORTH);



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

    /**
     * updates the top card displayed on the discard pile.
     * If the top card is a wild, prompts the user ti select a color
     * @param card the new top card to display
     */
    public void updateTopCard(Card card) {
        if (card == null) return;
        //light mode card
        if (model.lightMode) {
            String text = card.getCardLightColour() + " " + card.getCardLightType();

            if (card.getCardLightColour() == Card.LightColour.RAINBOW) {
                String[] options = {"RED", "BLUE", "YELLOW", "GREEN"};
                Object selectedOption = JOptionPane.showInputDialog(
                        null,
                        "Please choose a colour to set the rainbow:",
                        "Selection", // Title of the dialog
                        JOptionPane.QUESTION_MESSAGE,
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
            String text = card.getCardDarkColour() + " " + card.getCardDarkType();

            if (card.getCardDarkColour() == Card.DarkColour.RAINBOW) {
                String[] options = {"ORANGE", "TEAL", "PINK", "PURPLE"};
                Object selectedOption = JOptionPane.showInputDialog(
                        null,
                        "Please choose a colour to set the rainbow:",
                        "Selection", // Title of the dialog
                        JOptionPane.QUESTION_MESSAGE,
                        null, // Icon (null for default)
                        options, // Array of options for the dropdown
                        options[0] // Default selected option
                );
                card.setCardDarkColour((String) selectedOption);

            }
            //showing what the discard pile looks like
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
        }
        cardPanel.revalidate();
        cardPanel.repaint();

    }

    /**
     * updates the frame when the model notifies observers that
     * a player's turn has changed.
     */
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


    public void saveGame(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            out.writeObject(this);
            out.flush();
            System.out.println("Game saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
