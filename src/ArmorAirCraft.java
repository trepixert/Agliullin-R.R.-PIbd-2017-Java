import java.awt.*;

public class ArmorAirCraft {
    private int startPosX;
    private int startPosY;
    private int pictureWidth;
    private int pictureHeight;
    private final int airCraftWidth = 100;
    private final int airCraftHeight = 100;
    private int maxSpeed;
    private float weight;
    private Color mainColor;
    private Color comColor;

    public ArmorAirCraft(int MaxSpeed, float Weight, Color MainColor, Color comColor) {
        this.maxSpeed = MaxSpeed;
        this.weight = Weight;
        this.mainColor = MainColor;
        this.comColor = comColor;
    }

    public void setPosition(int x, int y, int width, int height) {
        startPosX = x;
        startPosY = y;
        pictureWidth = width;
        pictureHeight = height;
    }

    public void moveAirCraft(Direction direction) {
        float step = maxSpeed * 100 / weight;
        switch (direction) {
            case Right:
                if (startPosX + step < pictureWidth - airCraftWidth)
                    startPosX += step;
                break;
            case Left:
                if (startPosX - step > 0)
                    startPosX -= step;
                break;
            case Up:
                if (startPosY - step > 0)
                    startPosY -= step;
                break;
            case Down:
                if (startPosY + step < pictureHeight - airCraftHeight)
                    startPosY += step;
                break;
        }
    }

    public void drawAirCraft(Graphics g) {
        g.setColor(mainColor);
        g.drawOval(startPosX + 80, startPosY + 50, 250, 50);
        g.drawOval(startPosX + 220, startPosY - 35, 80, 220);
        g.drawOval(startPosX + 110, startPosY + 10, 30, 120);
        g.fillOval(startPosX + 80, startPosY + 50, 250, 50);
        g.fillOval(startPosX + 220, startPosY - 35, 80, 220);
        g.fillOval(startPosX + 110, startPosY + 10, 30, 120);
    }
}
