import com.sun.source.doctree.SerialDataTree;

import java.awt.*;
import java.io.Serializable;

public class AirCraft extends BaseArmorAirCraft implements Serializable {
    private Color DopColor;
    private Color GunColor;
    public boolean modernized;
    public boolean guns;
    private int _countLines;

    public AirCraft(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean modernized, boolean guns, Color gunColor){
        super(maxSpeed,weight,mainColor);
        DopColor = dopColor;
        GunColor = gunColor;
        this.modernized = modernized;
        this.guns = guns;
    }

    public AirCraft(String info){
        super(info);
        String[] strs = info.split(";");
        MaxSpeed = Integer.parseInt(strs[0]);
        Weight = Float.parseFloat(strs[1]);
        MainColor = new Color(Integer.parseInt(strs[2]));
        DopColor = new Color(Integer.parseInt(strs[3]));
        modernized = Boolean.parseBoolean(strs[4]);
        guns = Boolean.parseBoolean(strs[5]);
        GunColor = new Color(Integer.parseInt(strs[6]));
        _countLines = Integer.parseInt(strs[7]);
    }

    @Override
    public void DrawAirCraft(Graphics g){
        if (guns) {
            g.setColor(DopColor);
            g.drawOval( _startPosX + 220+30, _startPosY + 20, 80, 25);
            g.drawOval( _startPosX + 220 + 30, _startPosY - 10, 70, 25);
            g.drawOval( _startPosX + 220 + 30, _startPosY + 100, 80, 25);
            g.drawOval( _startPosX + 220 + 30, _startPosY + 130, 70, 25);
            g.setColor(GunColor);
            g.fillOval( _startPosX + 220 + 30, _startPosY + 20, 80, 25);
            g.fillOval( _startPosX + 220 + 30, _startPosY - 10, 70, 25);
            g.fillOval( _startPosX + 220 + 30, _startPosY + 100, 80, 25);
            g.fillOval( _startPosX + 220 + 30, _startPosY + 130, 70, 25);

        }
        if (modernized) {
            g.setColor(DopColor);
            g.drawOval( _startPosX + 75, _startPosY + 45, 260, 60);
            g.drawOval( _startPosX + 215, _startPosY - 35, 90, 220);
            g.drawOval( _startPosX + 105, _startPosY + 10, 40, 120);
            g.fillOval( _startPosX + 75, _startPosY + 45, 260, 60);
            g.fillOval( _startPosX + 215, _startPosY - 35, 90, 220);
            g.fillOval( _startPosX + 105, _startPosY + 10, 40, 120);
        }
        super.DrawAirCraft(g);
    }

    @Override
    public void moveAirCraft(Direction direction) {
        float step = MaxSpeed * 100 / Weight;
        switch (direction)
        {
            case Right:
                if (_startPosX + step < _pictureWidth - AirCraftWidth)
                    _startPosX += step;
                break;
            case Left:
                if (_startPosX - step > 0)
                    _startPosX -= step;
                break;
            case Up:
                if (_startPosY - step > 0)
                    _startPosY -= step;
                break;
            case Down:
                if (_startPosY + step < _pictureHeight - AirCraftHeight)
                    _startPosY += step;
                break;
        }
    }

    public void setDopColor(Color color){
        this.DopColor = color;
    }

    @Override
    public String toString() {
        return super.toString()+";"+DopColor.getRGB()+";"+modernized+";"+guns+";"+GunColor.getRGB()+";"+_countLines;
    }

    public int compareTo(AirCraft another) {
        if (another == null) return 1;
        if (MaxSpeed != another.MaxSpeed) return Integer.compare(MaxSpeed,another.MaxSpeed);
        if (Weight != another.Weight) return Float.compare(Weight,another.Weight);
        if (MainColor != another.MainColor) return Integer.compare(MainColor.getRGB(),another.MainColor.getRGB());
        if (DopColor != another.DopColor) return Integer.compare(DopColor.getRGB(),another.DopColor.getRGB());
        if (modernized!= another.modernized) return Boolean.compare(modernized,another.modernized);
        if (guns != another.guns) return Boolean.compare(guns,another.guns);
        if (GunColor != another.GunColor) return Integer.compare(GunColor.getRGB(),another.GunColor.getRGB());
        return 0;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        }
        if (!(another instanceof AirCraft)) {
            return false;
        }
        AirCraft airCraftObf = (AirCraft) another;
        return equals(airCraftObf);
    }

    public boolean equals(AirCraft another) {
        if (another == null) return false;
        if (MaxSpeed != another.MaxSpeed) return false;
        if (Weight != another.Weight) return false;
        if (MainColor != another.MainColor) return false;
        if (DopColor != another.DopColor) return false;
        if (modernized!= another.modernized) return false;
        if (guns != another.guns) return false;
        if (GunColor != another.GunColor) return false;
        return true;
    }
}
