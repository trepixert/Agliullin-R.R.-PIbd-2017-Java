import java.awt.*;
import java.io.Serializable;

public class BaseArmorAirCraftImpl extends AbstractArmorAirCraft implements Serializable, Comparable<BaseArmorAirCraftImpl> {
    protected final int airCraftWidth = 100;
    protected final int airCraftHeight = 100;

    public BaseArmorAirCraftImpl(int MaxSpeed, float Weight, Color MainColor) {
        this.maxSpeed = MaxSpeed;
        this.weight = Weight;
        this.mainColor = MainColor;
    }

    public BaseArmorAirCraftImpl(String info) {
        String[] strings = info.split(";");
        if (strings.length == 3) {
            this.maxSpeed = Integer.parseInt(strings[0]);
            this.weight = Float.parseFloat(strings[1]);
            this.mainColor = new Color(Integer.parseInt(strings[2]));
        }
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

    @Override
    public String toString() {
        return maxSpeed + ";" + weight + ";" + mainColor.getRGB();
    }

    @Override
    public int compareTo(BaseArmorAirCraftImpl another) {
        if (another == null) {
            return 1;
        }
        if (maxSpeed != another.maxSpeed) {
            return Integer.compare(maxSpeed, another.maxSpeed);
        }
        if (weight != another.weight) {
            return Float.compare(weight, another.weight);
        }
        if (mainColor != another.mainColor) {
            return Integer.compare(mainColor.getRGB(), another.mainColor.getRGB());
        }
        return 0;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null) return false;
        if (!(another instanceof BaseArmorAirCraftImpl)) return false;
        BaseArmorAirCraftImpl airCraftObj = (BaseArmorAirCraftImpl) another;
        return equals(airCraftObj);
    }

    public boolean equals(BaseArmorAirCraftImpl another) {
        if (another == null) return false;
        if (maxSpeed != another.maxSpeed) return false;
        if (weight != another.weight) return false;
        return mainColor == another.mainColor;
    }
}
