import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;


public class AudioPlayer {
    private static Clip backgroundSound;

    public void playAudio(String soundName) {

        try {
            File audioFile = new File(soundName);

            if (!audioFile.exists()){
                JOptionPane.showMessageDialog(null, "Audio file does not exist");
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
            audioClip.start();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Playing: " + e.getMessage());
        }

    }

    public void startBackGroundMusic(String soundName) {
        try {
            File audioFile = new File(soundName);

            if (!audioFile.exists()){
                JOptionPane.showMessageDialog(null, "Audio file does not exist");
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            backgroundSound = AudioSystem.getClip();
            backgroundSound.open(audioInputStream);
            backgroundSound.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundSound.start();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Playing: " + e.getMessage());
        }
    }

    public void stopBackGroundMusic(String soundName) {
        if (backgroundSound.isOpen() || backgroundSound.isRunning() || backgroundSound != null) {
            backgroundSound.stop();
        }
    }
}
