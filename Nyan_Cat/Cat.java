import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Cat {

    private static final short coordinateX = 70;
    private int coordinateY;

    private AffineTransform transform;
    private BufferedImage cat;

    public void paint(Graphics2D graphics2D) {

        transform.setToTranslation(coordinateX, coordinateY);
        transform.scale(2,2);

        graphics2D.drawImage(cat, transform, null);
    }
    public void increaseY() {
        if (!(coordinateY > 645)) coordinateY = coordinateY + 5;
    }
    public void decreaseY() {
        if (!(coordinateY < 105)) coordinateY = coordinateY - 5;
    }

    Cat() {
        try {
            String path = "C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources";
            cat = ImageIO.read(new File(path, "cat.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        transform = new AffineTransform();
        coordinateY = 400;
    }
    public int getCoordinateY() {
        return coordinateY;
    }
}
