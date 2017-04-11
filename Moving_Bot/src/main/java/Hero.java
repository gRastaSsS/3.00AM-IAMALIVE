public class Hero {
    int x_left;
    int x_right;
    int y_up;
    int y_down;
    int width;
    int height;

    byte health = 12;
    boolean isDead = false;

    Hero(int x, int y) {
        x_left = x;
        y_up = y;
    }

    public void update(int posX, int posY) {
            x_left = posX;
            y_up = posY;
            x_right = x_left + width;
            y_down = y_up + height;
    }

}
