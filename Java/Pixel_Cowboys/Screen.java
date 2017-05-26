import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class Screen extends JPanel implements Runnable {

    private boolean isRunning;
    private boolean isWatching;

    private static final short WIDTH = 800;
    private static final short HEIGHT = 600;
    private static final short IPS = 100;

    private int ready;
    private int aim;
    private int fire;
    private long startTime;
    private int i;

    private boolean isReady;
    private boolean isAim;
    private boolean isFire;
    private boolean shot;
    private boolean isWin;
    private boolean isPlayerA;
    private boolean animation1;

    private Key key;
    private Font customFont;
    private BufferedImage imageStartPlayer;
    private BufferedImage imageShotPlayer;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage image4;
    private BufferedImage image5;
    private BufferedImage image6;
    private BufferedImage image7;
    private BufferedImage image8;
    private BufferedImage blood;
    private BufferedImage blank;
    private AffineTransform transform1;
    private AffineTransform transform2;
    private AffineTransform transform3;

    Screen() {

        i = 0;
        key = new Key();
        addKeyListener(key);

        animation1 = false;
        isRunning = false;
        isWatching = true;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        ready = 3000;
        aim = 3000;
        fire = ThreadLocalRandom.current().nextInt(300, 7500);

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources\\slkscre.ttf")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources\\slkscre.ttf")));

            imageStartPlayer = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy.png"));
            imageShotPlayer = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy1.png"));
            image2 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy2.png"));
            image3 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy3.png"));
            image4 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy4.png"));
            image5 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy5.png"));
            image6 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy6.png"));
            image7 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy7.png"));
            image8 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\cowboy8.png"));
            blank = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\blankScreen.png"));
            blood = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\Cowboy\\src\\pics\\bloodScreen.png"));

            transform1 = new AffineTransform();
            transform2 = new AffineTransform();
            transform3 = new AffineTransform();

            transform1.setToTranslation(80,235);
            transform2.setToTranslation(720, 235);
            transform2.scale(-1,1);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        startTime = System.currentTimeMillis();
        start();
    }

    private void start() {
        isRunning = true;
        Thread thread = new Thread(this, "LOOP");
        thread.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics.setFont(customFont);
        if (!shot) {
            graphics2D.drawImage(imageStartPlayer, transform1, null);
            graphics2D.drawImage(imageStartPlayer, transform2, null);
            if (!isFire) {
                if (!isAim) {
                    if (isReady) {
                        graphics.drawString("READY", 325,300);
                    }
                    else {

                    }
                }
                else {
                    graphics.drawString("AIM", 360,300);
                }
            }
            else {
                graphics.drawString("FIRE", 350,300);
            }
        }
        else {
            if ((!isWin) && (isPlayerA)) {
                i++;
                graphics2D.drawImage(blank, 750,200,null);
                if (i <= 50) {
                    graphics2D.drawImage(imageShotPlayer, transform1, null);
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                }
                if ((i >= 50) && (i <= 200)) {
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                    graphics2D.drawImage(image2, transform2, null);
                }
                if ((i >= 200) && (i <= 350)) {
                    graphics2D.drawImage(image4, transform1, null);
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                }
                if ((i >= 350) && (i <= 500)) {
                    graphics2D.drawImage(image5, transform1, null);
                    graphics2D.drawImage(image6, transform2, null);
                }
                if ((i >= 500) && (i <= 650)) {
                    graphics2D.drawImage(blood, 125,280, null);
                    graphics2D.drawImage(image7, transform1, null);
                    graphics2D.drawImage(image3, transform2, null);
                }
                if (i >= 650) {
                    graphics2D.drawImage(blood, 125,280, null);
                    graphics2D.drawImage(image7, transform1, null);
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                    graphics.drawString("PLAYER 2 WINS", 200,300);
                }
            }
            else if ((!isWin) && (!isPlayerA)) {
                i++;
                graphics2D.drawImage(blank, 50,200,null);
                if (i <= 50) {
                    graphics2D.drawImage(imageShotPlayer, transform2, null);
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                }
                if ((i >= 50) && (i <= 200)) {
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                    graphics2D.drawImage(image2, transform1, null);
                }
                if ((i >= 200) && (i <= 350)) {
                    graphics2D.drawImage(image4, transform2, null);
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                }
                if ((i >= 350) && (i <= 500)) {
                    graphics2D.drawImage(image5, transform2, null);
                    graphics2D.drawImage(image6, transform1, null);
                }
                if ((i >= 500) && (i <= 650)) {
                    graphics2D.drawImage(blood, 675,280, null);
                    graphics2D.drawImage(image7, transform2, null);
                    graphics2D.drawImage(image3, transform1, null);
                }
                if (i >= 650) {
                    graphics2D.drawImage(blood, 675,280, null);
                    graphics2D.drawImage(image7, transform2, null);
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                    graphics.drawString("PLAYER 1 WINS", 200,300);
                }
            }
            else if ((isWin) && (isPlayerA)) {
                i++;
                if (i <= 50) {
                    graphics2D.drawImage(imageShotPlayer, transform1, null);
                    graphics2D.drawImage(imageShotPlayer, transform2, null);
                }
                if ((i >= 50) && (i <= 300)) {
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                }
                if ((i >= 300) && (i <= 450)) {
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                    graphics2D.drawImage(image8, transform2, null);
                }
                if ((i >= 450) && (i <= 500)) {
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                    graphics2D.drawImage(image7, transform2, null);
                    graphics2D.drawImage(blood, 675,280, null);
                }
                if (i >= 500) {
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                    graphics2D.drawImage(image7, transform2, null);
                    graphics2D.drawImage(blood, 675,280, null);
                    graphics.drawString("PLAYER 1 WINS", 200,300);
                }
            }
            else if ((isWin) && (!isPlayerA)) {
                i++;
                if (i <= 50) {
                    graphics2D.drawImage(imageShotPlayer, transform2, null);
                    graphics2D.drawImage(imageShotPlayer, transform1, null);
                }
                if ((i >= 50) && (i <= 300)) {
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                    graphics2D.drawImage(imageStartPlayer, transform1, null);
                }
                if ((i >= 300) && (i <= 450)) {
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                    graphics2D.drawImage(image8, transform1, null);
                }
                if ((i >= 450) && (i <= 500)) {
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                    graphics2D.drawImage(image7, transform1, null);
                    graphics2D.drawImage(blood, 125,280, null);
                }
                if (i >= 500) {
                    graphics2D.drawImage(imageStartPlayer, transform2, null);
                    graphics2D.drawImage(image7, transform1, null);
                    graphics2D.drawImage(blood, 125,280, null);
                    graphics.drawString("PLAYER 2 WINS", 200,300);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                Thread.sleep(1000/IPS);
                //$$$$$*****$$$$$
                if (System.currentTimeMillis() - startTime > ready + aim + fire) {
                    isFire = true;
                }
                else if (System.currentTimeMillis() - startTime > ready + aim) {
                    isAim = true;
                }
                else if (System.currentTimeMillis() - startTime > ready) {
                    isReady = true;
                }
                repaint();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Key implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (isWatching) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    isWatching = false;
                    if (!isFire) {
                        isWin = false;
                        isPlayerA = true;
                    }
                    else {
                        isWin = true;
                        isPlayerA = true;
                    }
                    shot = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    isWatching = false;
                    if (!isFire) {
                        isWin = false;
                        isPlayerA = false;
                    }
                    else {
                        isWin = true;
                        isPlayerA = false;
                    }
                    shot = true;
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
