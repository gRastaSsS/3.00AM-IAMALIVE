import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Screen extends JPanel implements Runnable{

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static final int IPS = 60;

    private long time;
    private boolean up;
    private boolean down;
    private boolean isRunning;
    private boolean isEnd;
    private int health;
    private int score;
    private int previousScore;
    private int disparity;
    private BufferedImage background;
    private BufferedImage healthPoint;
    private Cat cat;
    private Chunk chunk1;
    private Chunk chunk2;
    private Key key;
    private Font customFont;

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(background, 0,0,null);
        graphics.setFont(customFont);
        if (!isEnd) {
            chunk1.paint(graphics2D);
            chunk2.paint(graphics2D);
            cat.paint(graphics2D);
            graphics.drawString(String.valueOf(score), 250, 60);
            for (int i = 0; i < health; i++) {
                graphics2D.drawImage(healthPoint, 50 + i*50, 25, null);
            }
        }
        else {
            graphics.drawString("YOUR SCORE IS " + String.valueOf(score), 135,400);
            isRunning = false;
        }

    }

    Screen() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources\\slkscre.ttf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources\\slkscre.ttf")));
            String path = "C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources";
            background = ImageIO.read(new File(path, "background.jpg"));
            healthPoint = ImageIO.read(new File(path, "healthPoint.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);

        isEnd = false;
        isRunning = false;
        up = false;
        down = false;

        previousScore = 0;
        disparity = -60;
        score = 0;
        health = 3;
        cat = new Cat();
        chunk1 = new Chunk(1500);
        chunk2 = new Chunk(2500);
        key = new Key();

        addKeyListener(key);

        start();
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                Thread.sleep(1000/IPS);
                hotUpdate();

                if (chunk1.getDynamicX() == -1000) {
                    chunk1 = new Chunk(1000);
                }
                else if (chunk2.getDynamicX() == -1000)
                    chunk2 = new Chunk(1000);
                chunk1.update(cat.getCoordinateY());
                chunk2.update(cat.getCoordinateY());
                score = score + chunk1.score;
                score = score + chunk2.score;

                if (System.currentTimeMillis() - time > 2500) {
                    if (score - previousScore < disparity) {
                        health = health - 1;
                        disparity = disparity - 150;
                    }
                    if (disparity >= 180) disparity = 180;
                    else disparity = disparity + 20;
                    previousScore = score;
                    time = System.currentTimeMillis();
                    if (health == 0) {
                        isEnd = true;
                    }
                }
                //&&&&&//
                repaint();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        isRunning = true;
        Thread thread = new Thread(this, "LOOP");
        thread.start();
    }

    private void stop() {
        isRunning = false;
    }

    private void hotUpdate() {
        if (up)
            cat.decreaseY();

        if (down)
            cat.increaseY();
    }

    class Key implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) up = true;
            if (e.getKeyCode() == KeyEvent.VK_S) down = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) up = false;
            if (e.getKeyCode() == KeyEvent.VK_S) down = false;
        }

        @Override
        public void keyTyped(KeyEvent e) {}
    }
}
