import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class Enemy_Running {

    Enemy_Running(int x, int y) {
        optimal_length = 250;
        this.x = x;
        this.y = y;
        transform = new AffineTransform();
        timer = 30;
        factor = 0;
        maxTimer = 20;
    }
    private AffineTransform transform;

    boolean isDead;
    int x;
    int y;
    double angle;
    double length;
    private int optimal_length;
    private byte timer;
    byte health = 12;
    boolean isWall;

    private int lengthForSin;
    private double factor;
    private double angleForWall;
    private double minDistance;
    private double distance;
    private byte wallID;
    private byte timerForWall = 0;
    private byte me;
    double previousAngle;

    byte maxTimer;
    byte timerForStay = 0;
    int previousX;
    int previousY;

    public void movement(int heroX, int heroY, LinkedList<Bullet> bullets, ArrayList<Wall> walls) {



        minDistance = Math.sqrt((walls.get(0).x - x)*(walls.get(0).x - x)+(walls.get(0).y - y)*(walls.get(0).y - y));
        for (byte i = 1; i < walls.size(); i++) {
            distance = Math.sqrt((walls.get(i).x - x)*(walls.get(i).x - x)+(walls.get(i).y - y)*(walls.get(i).y - y));
            if (distance < minDistance) {
                minDistance = distance;
                wallID = i;
            }
        }

        if (minDistance < 70) {
            timerForWall = 1;
        }

        if ((timerForWall > 8) || (timerForWall < 1)) {

            length = Math.sqrt((heroX - x)*(heroX - x)+(heroY - y)*(heroY - y));

            //4 and 2
            if (heroX > x) {
                angle = Math.atan((double) (heroY - y)/(double)(heroX - x));
            }
            //1 and 3
            else if (heroX < x){
                angle = Math.atan((double) (heroY - y)/(double)(heroX - x)) + Math.PI;
            }
            else if (heroX == x) {
                if (heroY > y) {
                    angle = Math.PI / 2;
                }
                if (heroY < y) {
                    angle = -Math.PI / 2;
                }
            }

            if (length > optimal_length + 2) {
                if (length > 500) lengthForSin = 270;
                if (length < 500) lengthForSin = 210;
                maxTimer = 20; //270 -- 180
                x = x + (int)(Math.cos(angle + Math.PI/lengthForSin*Math.sin(factor)*120)*7);
                y = y + (int)(Math.sin(angle + Math.PI/lengthForSin*Math.sin(factor)*120)*7);
                previousAngle = angle;
            }
            else if (length < optimal_length - 2) {
                maxTimer = 5;
                x = x - (int)(Math.cos(angle + Math.PI/360*Math.sin(factor)*100)*7);
                y = y - (int)(Math.sin(angle + Math.PI/360*Math.sin(factor)*100)*7);
                previousAngle = angle;
            }
            //STANDING FIXED
            else {
                maxTimer = 10;
                if ((previousX == heroX) && (previousY == heroY)) {
                    previousAngle = previousAngle + Math.PI / 240;
                    x = heroX - (int)(optimal_length*Math.cos(previousAngle));
                    y = heroY - (int)(optimal_length*Math.sin(previousAngle));
                }
            }

            factor = factor + Math.PI / 30;
            if (factor > 30) factor = 0;
        }
        else {
            //4 and 2
            if (walls.get(wallID).x > x) {
                angleForWall = Math.atan((double) (walls.get(wallID).y - y)/(double)(walls.get(wallID).x - x));
            }
            //1 and 3
            else if (walls.get(wallID).x < x){
                angleForWall = Math.atan((double) (walls.get(wallID).y - y)/(double)(walls.get(wallID).x - x)) + Math.PI;
            }
            else if (walls.get(wallID).x == x) {
                if (walls.get(wallID).y > y) {
                    angleForWall = Math.PI / 2;
                }
                if (walls.get(wallID).y < y) {
                    angleForWall = -Math.PI / 2;
                }
            }
            if (angle > 0) me = 1;
            else if (angle < 0) me = -1;
            x = x + (int)(Math.cos(angleForWall + (me)*Math.PI / 2)*7);
            y = y + (int)(Math.sin(angleForWall + (me)*Math.PI / 2)*7);

            timerForWall++;
        }

        isWall = false;

        if (x < 25) {
            x = x + 6;
        }
        else if (x > 925) {
            x = x - 6;
        }
        else if (y < 25) {
            y = y + 6;
        }
        else if (y > 925) {
            y = y - 6;
        }
        timer++;
        if (timer > maxTimer) {
            for (int i = 0; i < walls.size(); i++) {
                if (walls.get(i).x > x) {
                    angleForWall = Math.atan((double) (walls.get(i).y - y)/(double)(walls.get(i).x - x));
                }
                //1 and 3
                else if (walls.get(wallID).x < x){
                    angleForWall = Math.atan((double) (walls.get(i).y - y)/(double)(walls.get(i).x - x)) + Math.PI;
                }
                else if (walls.get(i).x == x) {
                    if (walls.get(i).y > y) {
                        angleForWall = Math.PI / 2;
                    }
                    if (walls.get(i).y < y) {
                        angleForWall = -Math.PI / 2;
                    }
                }

                if (Math.abs(angleForWall - angle) < Math.PI / 30) {
                    isWall = true;
                    break;
                }
            }
            if (!isWall) {
                timer = 0;
                bullets.add(new Bullet(x,y,heroX,heroY,false));
            }
        }

        //SYNCHRONIZED
        timerForStay++;
        if (timerForStay > 10) {
            timerForStay = 0;
            previousX = heroX;
            previousY = heroY;
        }
        isWall = false;
    }
    public void paint(Graphics2D g2d, BufferedImage image) {
        transform.setToTranslation(x, y);
        transform.scale(2,2);
        if (!isDead) {
            transform.rotate(angle, image.getWidth()/2, image.getHeight()/2);
        }
        else {
            transform.rotate(angle + Math.PI, image.getWidth()/2, image.getHeight()/2);
        }
        g2d.drawImage(image, transform, null);
    }
}
