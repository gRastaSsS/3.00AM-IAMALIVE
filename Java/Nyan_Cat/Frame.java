import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Frame extends JFrame {

    static final String TITLE = "n00btube";

    Frame() {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(TITLE);

        init();
    }

    public void init() {
        setLayout(new GridLayout(1,1,0,0));
        Screen s = new Screen();
        add(s);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) throws Exception  {
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources\\nyanCat.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(inputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        new Frame();
    }

}
