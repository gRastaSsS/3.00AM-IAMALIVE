import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Screen extends JPanel implements Runnable, MouseMotionListener{
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final int START_POINT_X = 40;
    private static final int START_POINT_Y = 40;
    private static final int MOVEMENT_START_SPEED = 6;
    private static final int FPS = 60;

    private boolean threadIsRunning = false;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean shot = false;
    private boolean spacePressed = false;
    private boolean startAction = false;
    private boolean bigBoy1 = false;
    private boolean bigBoy2 = false;
    private boolean bigBoy3 = false;
    private boolean bigBoy4 = false;

    private int mouseX;
    private int mouseY;
    private int centerX;
    private int centerY;
    private double angle;

    private ArrayList<Wall> final_walls;
    private ArrayList<Wall> walls;
    private LinkedList<Bullet> bullets;
    private AffineTransform transform;
    private Enemy_Running enemy;
    private BufferedImage image;
    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage image4;
    private Key key;
    private Hero hero;
    private Thread thread;

    public Screen() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        try {
            image = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\nigga\\src\\main\\resources\\Char1.png"));
            image1 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\nigga\\src\\main\\resources\\Char2.png"));
            image2 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\nigga\\src\\main\\resources\\zone.png"));
            image3 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\nigga\\src\\main\\resources\\Body.png"));
            image4 = ImageIO.read(new File("C:\\AInOne\\progs\\Java\\nigga\\src\\main\\resources\\Body1.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        transform = AffineTransform.getTranslateInstance(START_POINT_X, START_POINT_Y);
        hero = new Hero(START_POINT_X, START_POINT_Y);
        hero.width = image.getWidth();
        hero.height = image.getHeight();

        final_walls = new ArrayList<Wall>(100);
        walls = new ArrayList<Wall>(1);
        addWalls();

        key = new Key();
        bullets = new LinkedList<Bullet>();
        enemy = new Enemy_Running(920,920);

        addKeyListener(key);

        start();
    }

    private void start() {
        threadIsRunning = true;
        thread = new Thread(this, "LOOP");
        thread.start();
    }

    private void stop() {
        threadIsRunning = false;
    }

    public void run() {
        try {

            while (threadIsRunning) {
                Thread.sleep(1000/FPS);
                bulletBuffer();
                if (enemy.health > 0) {
                    if (!hero.isDead)
                        enemyUpdate();
                }
                else enemy.isDead = true;
                if (hero.health > 0) {
                    heroUpdate();
                }
                else hero.isDead = true;
                repaint();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void enemyUpdate() {
        if ((hero.y_up > 190) || (hero.x_left > 190)) startAction = true;
        if (startAction) {
            enemy.movement(hero.x_left + hero.width, hero.y_up + hero.height, bullets, walls);
        }

    }
    private void bulletBuffer() {
        if (startAction) {
            if (shot) {
                bullets.add(new Bullet(hero.x_left + hero.width, hero.y_up + hero.height, mouseX, mouseY, true));
                shot = false;
            }
            for (Bullet bullet : bullets) {
                if (!bullet.isDead)
                    bullet.update(walls, hero, enemy);
                else bullet = null;
            }
        }
    }

    private void heroUpdate() {
        if ((left) && (hero.x_left - MOVEMENT_START_SPEED > 20)) {
            for (Wall wall : walls) {
                if ((Math.abs(wall.y - hero.y_up) < 40) && (hero.x_left <= wall.x + 45) && (hero.x_left >= wall.x)) {
                    bigBoy1 = true;
                    break;
                }
            }
            if (!bigBoy1) hero.update(hero.x_left - MOVEMENT_START_SPEED, hero.y_up);
        }
        if ((right) && (hero.x_left + MOVEMENT_START_SPEED < 930)) {
            for (Wall wall : walls) {
                if ((Math.abs(wall.y - hero.y_up) < 40) && (hero.x_left >= wall.x - 52) && (hero.x_left < wall.x)) {
                    bigBoy2 = true;
                    break;
                }
            }
            if (!bigBoy2) hero.update(hero.x_left + MOVEMENT_START_SPEED, hero.y_up);
        }
        if ((up) && (hero.y_up - MOVEMENT_START_SPEED > 20)) {
            for (Wall wall : walls) {
                if ((hero.y_up < wall.y + 53) && (hero.y_up > wall.y) && (Math.abs(wall.x - hero.x_left) < 40)) {
                    bigBoy3 = true;
                    break;
                }
            }
            if (!bigBoy3) hero.update(hero.x_left, hero.y_up - MOVEMENT_START_SPEED);
        }
        if ((down) && (hero.y_up - MOVEMENT_START_SPEED < 920)) {
            for (Wall wall : walls) {
                if ((hero.y_up > wall.y - 53) && (hero.y_up < wall.y + 35) && (Math.abs(wall.x - hero.x_left) < 40)) {
                    bigBoy4 = true;
                    break;
                }
            }
            if (!bigBoy4) hero.update(hero.x_left, hero.y_up + MOVEMENT_START_SPEED);
        }
        bigBoy1 = false;
        bigBoy2 = false;
        bigBoy3 = false;
        bigBoy4 = false;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image2, 0, 0, null);
        graphics.drawImage(image2, 810, 810, null);
        Graphics2D g2d = (Graphics2D) graphics;
        transform.setToTranslation(hero.x_left, hero.y_up);
        transform.scale(2,2);
        if (!hero.isDead) {
            transform.rotate(angle, hero.width/2, hero.height/2);
            g2d.drawImage(image, transform,null);
        }
        else {
            transform.rotate(Math.PI / 3, hero.width/2, hero.height/2);
            g2d.drawImage(image4, transform, null);
        }

        graphics.setColor(Color.BLACK);
        for (Bullet bullet : bullets) if (!bullet.isDead) bullet.paintBullet(graphics);
        for (Wall wall : final_walls) graphics.fillRect(wall.x, wall.y, 20, 20);
        for (Wall wall : walls) graphics.fillRect(wall.x, wall.y, 40, 40);

        if (!enemy.isDead) {
            graphics.drawRect(enemy.x, enemy.y - 16, 48, 5);
            graphics.setColor(Color.RED);
            graphics.fillRect(enemy.x + 1, enemy.y - 15, enemy.health * 4 - 1, 4);
        }
        if (!hero.isDead) {
            graphics.setColor(Color.BLACK);
            graphics.drawRect(hero.x_left, hero.y_up - 16, 48, 5);
            graphics.setColor(Color.RED);
            graphics.fillRect(hero.x_left + 1, hero.y_up - 15, hero.health * 4 - 1, 4);
        }
        if (!enemy.isDead) enemy.paint(g2d, image1);
        else enemy.paint(g2d, image3);

    }
    ///////////////////////////////////////////////////////
    public void mouseDragged(MouseEvent e) {}
    /*

    1 | 2
    --|--
    3 | 4

     */
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY() - 20;

        centerX = hero.x_left + hero.width / 2;
        centerY = hero.y_up + hero.height / 2;

        //4 and 2
        if (mouseX > centerX) {
            angle = Math.atan((double) (mouseY - centerY)/(double)(mouseX - centerX));
        }
        //1 and 3
        else if (mouseX < centerX){
            angle = Math.atan((double) (mouseY - centerY)/(double)(mouseX - centerX)) + Math.PI;
        }
    }

    ///////////////////////////////////////////////////////
    private class Key implements KeyListener {
        public void keyTyped(KeyEvent e) {}
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                right = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                left = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                up = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                down = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (!spacePressed) {
                    shot = true;
                    spacePressed = true;
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                right = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                left = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                up = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                down = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                spacePressed = false;
                shot = false;
            }
        }
    }
    ///////////////////////////////////////////////////////
    private void addWalls() {
        for (int i = 0; i < 50; i++) {
            final_walls.add(new Wall(0,i*20));
            final_walls.add(new Wall(980, i*20));
        }
        for (int i = 1; i < 49; i++) {
            final_walls.add(new Wall(i*20, 0));
            final_walls.add(new Wall(i*20, 980));
        }

        walls.add(new Wall(200,800));
        walls.add(new Wall(540, 500));
    }
}