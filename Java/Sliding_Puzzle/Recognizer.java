import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Recognizer extends JPanel implements Runnable {

    private static final short WIDTH = 200;
    private static final short HEIGHT = 200;
    private static final short IPS = 60;
    private static final byte SIZE = 16;
    private boolean isRunning;
    private int indX;
    private int indY;
    private Font customFont;
    private Mouse mouse;
    private int[][] elements;
    private java.util.List<Integer> random;

    Recognizer() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources\\slkscre.ttf")).deriveFont(25f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources\\slkscre.ttf")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        elements = new int[4][4];
        random = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            random.add(i+1);
        }

        int index;
        int a = 0;
        while (!random.isEmpty()) {
            index = ThreadLocalRandom.current().nextInt(0, random.size());
            elements[a][0] = random.get(index);
            random.remove(index);
            index = ThreadLocalRandom.current().nextInt(0, random.size());
            elements[a][1] = random.get(index);
            random.remove(index);
            index = ThreadLocalRandom.current().nextInt(0, random.size());
            elements[a][2] = random.get(index);
            random.remove(index);
            index = ThreadLocalRandom.current().nextInt(0, random.size());
            elements[a][3] = random.get(index);
            random.remove(index);
            a++;
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        mouse = new Mouse();
        addMouseListener(mouse);

        start();
    }
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setFont(customFont);
        Graphics2D graphics2D = (Graphics2D)graphics;
        for (int i = 1; i <= 4; i++) {
            graphics.drawLine(WIDTH/4*i, 0, WIDTH/4*i, HEIGHT);
            graphics.drawLine(0, HEIGHT/4*i, WIDTH, HEIGHT/4*i);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (elements[i][j] != 16)
                graphics.drawString(String.valueOf(elements[i][j]), (i+1)*50 - 50, 50*j+35);
            }
        }
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                Thread.sleep(1000/IPS);
                repaint();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void start() {
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public class Mouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            indX = e.getX() / 50;
            indY = e.getY() / 50;

            if (indX < 3)
                if (elements[indX + 1][indY] == 16) {
                    elements[indX + 1][indY] = elements[indX][indY];
                    elements[indX][indY] = 16;
                }
            if (indX > 0)
                if (elements[indX - 1][indY] == 16) {
                    elements[indX - 1][indY] = elements[indX][indY];
                    elements[indX][indY] = 16;
            }
            if (indY < 3)
                if (elements[indX][indY + 1] == 16) {
                    elements[indX][indY + 1] = elements[indX][indY];
                    elements[indX][indY] = 16;
            }
            if (indY > 0)
                if (elements[indX][indY - 1] == 16) {
                    elements[indX][indY - 1] = elements[indX][indY];
                    elements[indX][indY] = 16;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
