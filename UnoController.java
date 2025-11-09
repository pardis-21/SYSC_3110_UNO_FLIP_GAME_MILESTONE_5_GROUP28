import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UnoController implements ActionListener {

    UnoViewFrame frame;

    public UnoController(){

        frame = new UnoViewFrame();
        frame.newCard.addActionListener(this);
       // frame.UNOButton.addActionListener(this);
        frame.discardPile.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton){
            source = (JButton)source;
        }
        if(source == frame.newCard){
            JOptionPane.showMessageDialog(frame, "new card drawn");
            // IMPLEMENT ACTIONS

        }
        else if (source == frame.discardPile){
            JOptionPane.showMessageDialog(frame,"top card");
        }


    }
}
