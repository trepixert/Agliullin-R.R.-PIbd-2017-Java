import java.awt.*;

public class BaseArmorAirCraftImpl extends AbstractArmorAirCraft {
    protected final int airCraftWidth = 100;
    protected final int airCraftHeight = 100;

    public BaseArmorAirCraftImpl(int MaxSpeed, float Weight, Color MainColor) {
        this.maxSpeed = MaxSpeed;
        this.weight = Weight;
        this.mainColor = MainColor;
    }

    public void moveAirCraft(Direction direction) {
        float step = maxSpeed * 100 / weight;
        switch (direction) {
            case RIGHT:
                if (startPosX + step < pictureWidth - airCraftWidth)
                    startPosX += step;
                break;
            case LEFT:
                if (startPosX - step > 0)
                    startPosX -= step;
                break;
            case UP:
                if (startPosY - step > 0)
                    startPosY -= step;
                break;
            case DOWN:
                if (startPosY + step < pictureHeight - airCraftHeight)
                    startPosY += step;
                break;
        }
    }

    @Override
    public void drawAirCraft(Graphics g) {
        g.setColor(mainColor);
        g.drawOval(startPosX + 80, startPosY + 50, 250, 50);
        g.drawOval(startPosX + 220, startPosY - 35, 80, 220);
        g.drawOval(startPosX + 110, startPosY + 10, 30, 120);
        g.fillOval(startPosX + 80, startPosY + 50, 250, 50);
        g.fillOval(startPosX + 220, startPosY - 35, 80, 220);
        g.fillOval(startPosX + 110, startPosY + 10, 30, 120);
    }

    public void setMainColor(Color color) {
        this.mainColor = color;
    }
}
