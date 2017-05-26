import java.awt.*;
import java.util.ArrayList;

public class Bullet {
    private int x;
    private int y;
    private double angle;
    public boolean isDead;
    private boolean isHero;

    Bullet(int getX, int getY, int mouseX, int mouseY, boolean isHero) {
        x = getX; y = getY;
        isDead = false;
        this.isHero = isHero;

        //4 and 2
        if (mouseX > getX) {
            angle = Math.atan((double) (mouseY - y)/(double)(mouseX - x));
        }
        //1 and 3
        else if (mouseX < getX){
            angle = Math.atan((double) (mouseY - y)/(double)(mouseX - x)) + Math.PI;
        }
        else if (mouseX == getX) {
            if (mouseY > getY) {
                angle = Math.PI / 2;
            }
            if (mouseY < getY) {
                angle = -Math.PI / 2;
            }
        }
    }
    /*

    1 | 2
    --|--
    3 | 4

     */
    public void update(ArrayList<Wall> walls, Hero hero, Enemy_Running enemy) {
        x = x + 2*(int)(Math.cos(angle)*10);
        y = y + 2*(int)(Math.sin(angle)*10);

        for (Wall wall : walls) {
            if ((x >= wall.x) && (x <= wall.x + 40) && (y >= wall.y) && (y <= wall.y + 40)) {
                isDead = true;
            }
        }
        if ((x > 1000) || (x < 0) || (y > 1000) || (y < 0)) {
            isDead = true;
        }

        if (isHero) {
            if ((x >= enemy.x) && (x <= enemy.x + 48) && (y >= enemy.y) && (y <= enemy.y + 48)) {
                enemy.health--;
                isDead = true;
            }
        }
        else {
            if ((x >= hero.x_left) && (x <= hero.x_left + 48) && (y >= hero.y_up) && (y <= hero.y_up + 48)) {
                hero.health--;
                isDead = true;
            }
        }
    }
    public void paintBullet(Graphics graphics) {
        graphics.fillRect(x, y, 4, 4);
    }
}
