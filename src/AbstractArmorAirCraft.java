import java.awt.*;

public abstract class AbstractArmorAirCraft implements ArmorAirCraft {
    protected int startPosX;
    protected int startPosY;
    protected int pictureWidth;
    protected int pictureHeight;
    protected int maxSpeed;
    protected float weight;
    protected Color mainColor;

    public void setPosition(int x, int y, int width, int height) {
        startPosX = x;
        startPosY = y;
        pictureWidth = width;
        pictureHeight = height;
    }

    public abstract void drawAirCraft(Graphics g);

    public abstract void moveAirCraft(Direction direction);
}
