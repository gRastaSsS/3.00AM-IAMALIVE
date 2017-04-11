import javax.swing.*;

public class Frame extends JFrame {

    private static final String TITLE = "cowboy";

    Frame() {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(TITLE);

        init();
    }

    public void init() {
        Screen s = new Screen();
        add(s);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception  {
        new Frame();
    }

}
