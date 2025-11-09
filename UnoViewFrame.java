import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class UnoViewFrame extends JFrame {

    private GameLogicModel model;

    private JPanel cardPanel; // panel for current player's cards
    private JPanel decksPanel; // panel for the discard pile, new card pile, and "UNO!" button
    public JPanel scorePanel; // panel for the player's score
    private JButton playerCardButtons[];
    private JButton unoButton;
    private static final int NUMBER_OF_CARDS_BEGGING_GAME = 7;
    private ArrayList<Player> playerNames;

    //JButton nextPlayerButton;

    public JButton discardPile;
    public JButton newCard;
    public JButton UNOButton;

    public UnoViewFrame(GameLogicModel model){
        this.model = model;
        this.model = new GameLogicModel(Game.players);

        while (true) {
            String userInput = JOptionPane.showInputDialog(null, "Enter the number of Players (2–4): ");
            model.numPlayers = Integer.parseInt(userInput);

            try {
                if (model.numPlayers < 2 || model.numPlayers > 4) {
                    JOptionPane.showMessageDialog(null, "Invalid number of players! Please enter 2–4.");
                } else
                    break;
            }
            catch (InputMismatchException e) {
                JOptionPane.showMessageDialog(null, "Invalid number of players! Please enter 2–4.");
            }
        }

        while (model.players.size() < model.numPlayers) {
            String playerName = JOptionPane.showInputDialog(null, "Enter player name");

            //make sure 2 players by same name don't exist
            boolean exists = false;
            for (Player player : model.players) {
                if (player.getName().equals(playerName)) {
                    JOptionPane.showMessageDialog(null, "That player already exists!");
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                model.addNewPlayer(playerName);
            }
        }

        cardPanel = new JPanel(); // display the players cards
        decksPanel = new JPanel();
        scorePanel = new JPanel(); // display score
        discardPile = new JButton(); //shows top card on discard pile
        newCard = new JButton(); //pile to take a card
        UNOButton = new JButton(); // when player has one card, button shows

        // SETTING UP NEWCARD BUTTON
        newCard.setPreferredSize(new Dimension(150, 150));
        newCard.setFont(new Font("Arial", Font.BOLD, 16));
        newCard.setBackground(Color.GRAY);
        newCard.setForeground(Color.BLACK);
        newCard.setText("pick a card");

        //SETTING UP SCOREPANEL
        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.BLACK);
        scorePanel.setBackground(new Color(30, 120,60));
        scorePanel.add(scoreLabel);


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
        discardPile.setFont(new Font("Arial", Font.BOLD, 40));
        discardPile.setPreferredSize(new Dimension(150, 150));



        // ADDING LAYOUT
        setLayout(new BorderLayout());
        decksPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 200));
        decksPanel.add(discardPile);
        decksPanel.add(newCard);

        add(decksPanel, BorderLayout.CENTER);
        add(cardPanel, BorderLayout.SOUTH);
        add(scorePanel, BorderLayout.NORTH);


        //FRAME PROPERTIES
        getContentPane().setBackground(new Color(30, 120,60));
        setTitle("UNO FLIP - MILESTONE 2");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


// WHEN LAYER PRESSESS NEWCARD BUTTON, ONE RANDOM CARD IS ADDED TO THEIR HAND
        // WHEN PLAYER HAND == 1, UNO BUTTON SHOWS AND THEY HAVE TO CLICK IT, IF NOT THEY GET 2 RANDOM CARDS ADDED
        // IF PLAYER ONE PLAYS, POP UP SAY PLAYER 2? PRESS OK,PLAYER 2 CAN PLAY
        // WHEN INSIDE PLAYER 2, SHOWS PLAYER 2 HAND

        //FIGURE OUT HOW TO DO FOR ALL PLAYERS
        cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));


    //START THE GAME
        model.startGame();
        startGamePlayerButtons();


    }
    public void updateHand(List<Card> Hand){
        discardPile.removeAll();
        discardPile.repaint();
    }

    public void updateTopCard(Card card){
        discardPile.removeAll();
        discardPile.repaint();
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
        Player currentPlayer = game.playerOrder.getCurrentPlayer();

        //DEBUGGING PURPOSES CAN DELETE LATER ENSURING THAT THE CORRECT PLAYER HAND IS SHOWN
        System.out.println("SHOWING CARDS FOR: " + currentPlayer.showHand());
        String displayText;
        //CREATING THE BUTTON ARRAY FOR THE BOTTOM PANEL CARDPANEL IN THE FRAME
        for (Card card: currentPlayer.getHand()){

            if (card.getCardType().ordinal() <= 9) {
                displayText = String.valueOf(card.getCardType().ordinal());

            }
            else {
                displayText = card.getCardType().toString();
            }

            JButton cardButton = new JButton(displayText);
            cardButton.setBackground(card.JavaCardColour(card.getCardColour()));
            cardButton.setForeground(Color.BLACK);
            cardButton.setPreferredSize(new Dimension(80, 100));

            //ACTION LISTNER HERE
            cardButton.addActionListener(e -> handlePlayerCard(card));
            cardPanel.add(cardButton);
        }
        cardPanel.revalidate();
        cardPanel.repaint();



    }

    private void handlePlayerCard(Card card){
        GameLogicModel model = new GameLogicModel(playerNames);

            if (model.getDirection()) {
                game.playerOrder.nextPlayerClockwise();
            }
            else {
                game.playerOrder.nextPlayerCounterClockwise();
            }
            startGamePlayerButtons();
    }


}
