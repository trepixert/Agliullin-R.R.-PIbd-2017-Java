import java.awt.*;
import java.io.Serializable;

public class AirCraftImpl extends BaseArmorAirCraftImpl implements Serializable {
    private Color comColor;
    private Color gunColor;
    private boolean modernized;
    private boolean guns;
    private int countLines;

    public AirCraftImpl(int maxSpeed, float weight, Color mainColor, Color comColor, boolean modernized, boolean guns, Color gunColor) {
        super(maxSpeed, weight, mainColor);
        this.comColor = comColor;
        this.gunColor = gunColor;
        this.modernized = modernized;
        this.guns = guns;
    }

    public AirCraftImpl(String info) {
        super(info);
        String[] strings = info.split(";");
        this.maxSpeed = Integer.parseInt(strings[0]);
        this.weight = Float.parseFloat(strings[1]);
        this.mainColor = new Color(Integer.parseInt(strings[2]));
        this.comColor = new Color(Integer.parseInt(strings[3]));
        modernized = Boolean.parseBoolean(strings[4]);
        guns = Boolean.parseBoolean(strings[5]);
        this.gunColor = new Color(Integer.parseInt(strings[6]));
        this.countLines = Integer.parseInt(strings[7]);
    }

    @Override
    public void drawAirCraft(Graphics g) {
        if (guns) {
            g.setColor(comColor);
            g.drawOval(startPosX + 220 + 30, startPosY + 20, 80, 25);
            g.drawOval(startPosX + 220 + 30, startPosY - 10, 70, 25);
            g.drawOval(startPosX + 220 + 30, startPosY + 100, 80, 25);
            g.drawOval(startPosX + 220 + 30, startPosY + 130, 70, 25);

            g.setColor(gunColor);
            g.fillOval(startPosX + 220 + 30, startPosY + 20, 80, 25);
            g.fillOval(startPosX + 220 + 30, startPosY - 10, 70, 25);
            g.fillOval(startPosX + 220 + 30, startPosY + 100, 80, 25);
            g.fillOval(startPosX + 220 + 30, startPosY + 130, 70, 25);

        }
        if (modernized) {
            g.setColor(comColor);
            g.drawOval(startPosX + 75, startPosY + 45, 260, 60);
            g.drawOval(startPosX + 215, startPosY - 35, 90, 220);
            g.drawOval(startPosX + 105, startPosY + 10, 40, 120);
            g.fillOval(startPosX + 75, startPosY + 45, 260, 60);
            g.fillOval(startPosX + 215, startPosY - 35, 90, 220);
            g.fillOval(startPosX + 105, startPosY + 10, 40, 120);
        }
        super.drawAirCraft(g);
    }

    @Override
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

    public void setComColor(Color comColor) {
        this.comColor = comColor;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + comColor.getRGB() + ";" + modernized + ";" + guns + ";" + gunColor.getRGB() + ";" + countLines;
    }

    public int compareTo(AirCraftImpl another) {
        if (another == null) return 1;
        if (maxSpeed != another.maxSpeed) return Integer.compare(maxSpeed,another.maxSpeed);
        if (weight != another.weight) return Float.compare(weight,another.weight);
        if (mainColor != another.mainColor) return Integer.compare(mainColor.getRGB(),another.mainColor.getRGB());
        if (comColor != another.comColor) return Integer.compare(comColor.getRGB(),another.comColor.getRGB());
        if (modernized!= another.modernized) return Boolean.compare(modernized,another.modernized);
        if (guns != another.guns) return Boolean.compare(guns,another.guns);
        if (gunColor != another.gunColor) return Integer.compare(gunColor.getRGB(),another.gunColor.getRGB());
        return 0;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        }
        if (!(another instanceof AirCraftImpl)) {
            return false;
        }
        AirCraftImpl airCraftObf = (AirCraftImpl) another;
        return equals(airCraftObf);
    }

    public boolean equals(AirCraftImpl another) {
        if (another == null) return false;
        if (maxSpeed != another.maxSpeed) return false;
        if (weight != another.weight) return false;
        if (mainColor != another.mainColor) return false;
        if (comColor != another.comColor) return false;
        if (modernized!= another.modernized) return false;
        if (guns != another.guns) return false;
        if (gunColor != another.gunColor) return false;
        return true;
    }
}
