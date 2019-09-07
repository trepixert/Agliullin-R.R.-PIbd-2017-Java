import java.awt.*;

public class AirCraftImpl extends BaseArmorAirCraftImpl {
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

    public AirCraftImpl(String info){
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
        return super.toString()+";"+comColor.getRGB()+";"+modernized+";"+guns+";"+gunColor.getRGB()+";"+countLines;
    }
}
