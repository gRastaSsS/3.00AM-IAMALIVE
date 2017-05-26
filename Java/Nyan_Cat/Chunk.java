import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class Chunk {

    //  LINES                       12
    //  MINIMUM_ITEMS_IN_LINE       0
    //  MAXIMUM_ITEMS_IN_LINE       20
    //  MAX ITEMS FOR CHUNK         192
    //  RANDOM CAKE LEVELS          ___
    //  WIDTH                       1000
    //  CAT LEFT BORDER             70
    //  CAT RIGHT BORDER            134

    private static final byte LINES = 12;
    private static final byte MAX_ITEMS = 20;
    private short acceleration = 4;
    private int dynamicX;
    private int id;
    private int line;
    public int score;
    private ChunkLine[] chunkLines;
    private BufferedImage[] images;

    public void paint(Graphics2D graphics) {
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < MAX_ITEMS; j++) {
                if (chunkLines[i].items[j].isExist) {
                    if (chunkLines[i].items[j].isRotten) {
                        graphics.drawImage(images[chunkLines[i].items[j].skin], dynamicX + j * 50, 100 + i * 50, null);
                    } else {
                        graphics.drawImage(images[chunkLines[i].items[j].skin], dynamicX + j * 50, 100 + i * 50, null);
                    }
                }

            }
        }
        dynamicX = dynamicX - acceleration;
    }

    public void update(int catY) {
        score = 0;
        id = (134 - dynamicX) / 50;
        line = (catY - 100) / 50;
        if ((id >= 0) && (id <= 19)) {
            if (chunkLines[line].items[id].isExist) {
                if (chunkLines[line].items[id].isRotten) score = score - 50;
                else score = score + 75;
            }
            chunkLines[line].items[id].isExist = false;

            if (catY % 50 != 0) {
                if (chunkLines[line + 1].items[id].isExist) {
                    if (chunkLines[line + 1].items[id].isRotten) score = score - 25;
                    else score = score + 25;
                }
                chunkLines[line + 1].items[id].isExist = false;
            }
        }
    }

    Chunk(int x) {
        score = 0;
        dynamicX = x;
        chunkLines = new ChunkLine[LINES];
        for (int i = 0; i < LINES; i++) chunkLines[i] = new ChunkLine();
        try {
            images = new BufferedImage[14];
            String path = "C:\\AInOne\\progs\\Java\\Spaceship\\src\\resources";
            images[0] = ImageIO.read(new File(path, "junk1.png"));
            images[1] = ImageIO.read(new File(path, "junk2.png"));
            images[2] = ImageIO.read(new File(path, "junk3.png"));
            images[3] = ImageIO.read(new File(path, "junk4.png"));
            images[4] = ImageIO.read(new File(path, "junk5.png"));
            images[5] = ImageIO.read(new File(path, "junk6.png"));
            images[6] = ImageIO.read(new File(path, "junk7.png"));
            images[7] = ImageIO.read(new File(path, "junk8.png"));
            images[8] = ImageIO.read(new File(path, "rotten1.png"));
            images[9] = ImageIO.read(new File(path, "rotten2.png"));
            images[10] = ImageIO.read(new File(path, "rotten3.png"));
            images[11] = ImageIO.read(new File(path, "rotten4.png"));
            images[12] = ImageIO.read(new File(path, "rotten5.png"));
            images[13] = ImageIO.read(new File(path, "rotten6.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ChunkLine {

        class Item {
            boolean isExist;
            boolean isRotten;
            int skin;

            Item() {
                isExist = false;
                isRotten = false;
                skin = ThreadLocalRandom.current().nextInt(0, 8);
            }
        }

        Item[] items = new Item[MAX_ITEMS];
        int number;

        ChunkLine() {
            for (int i = 0; i < MAX_ITEMS; i++) {
                items[i] = new Item();
                number = ThreadLocalRandom.current().nextInt(1, 5);
                if (number == 4) {
                    items[i].isExist = true;
                    number = ThreadLocalRandom.current().nextInt(1, 4);
                    if (number == 3) {
                        items[i].isRotten = true;
                        items[i].skin = ThreadLocalRandom.current().nextInt(8, 14);
                    }
                }
            }
        }
    }

    public int getDynamicX() {
        return dynamicX;
    }

    public void setAcceleration(short acceleration) {
        this.acceleration = acceleration;
    }
}
