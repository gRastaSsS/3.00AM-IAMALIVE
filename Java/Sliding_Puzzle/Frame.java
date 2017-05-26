import javax.swing.*;

public class Frame extends JFrame {

    private static final String TITLE = "picture";

    Frame() {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(TITLE);

        init();
    }

    public void init() {
        Recognizer s = new Recognizer();
        add(s);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception  {
        new Frame();
    }
}
