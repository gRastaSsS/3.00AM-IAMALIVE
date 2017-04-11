import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    private static final String TITLE = "Game";

    public Frame() {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(TITLE);

        init();

    }
    public void init() {
        setLayout(new GridLayout(1,1,0,0));
        Screen s = new Screen();
        addMouseMotionListener(s);
        add(s);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public static void main(String[] args) {
        new Frame();
    }
}
