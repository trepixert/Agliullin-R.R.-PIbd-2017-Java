import java.awt.*;

public class BaseArmorAirCraft extends AbstractArmorAirCraft{
    protected final int AirCraftWidth = 100;
    protected final int AirCraftHeight = 100;

    public BaseArmorAirCraft(int MaxSpeed, float Weight, Color MainColor) {
        this.MaxSpeed = MaxSpeed;
        this.Weight = Weight;
        this.MainColor = MainColor;
    }

    public BaseArmorAirCraft(String info){
        String[] strs = info.split(";");
        if(strs.length==3){
            MaxSpeed = Integer.parseInt(strs[0]);
            Weight = Float.parseFloat(strs[1]);
            MainColor = new Color(Integer.parseInt(strs[2]));
        }
    }

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

    public void DrawAirCraft(Graphics g) {
        g.setColor(MainColor);
        g.drawOval(_startPosX+80,_startPosY+50,250,50);
        g.drawOval(_startPosX+220,_startPosY-35,80,220);
        g.drawOval(_startPosX+110,_startPosY+10,30,120);
        g.fillOval(_startPosX+80,_startPosY+50,250,50);
        g.fillOval(_startPosX+220,_startPosY-35,80,220);
        g.fillOval(_startPosX+110,_startPosY+10,30,120);
    }

    public void setMainColor(Color color){
        this.MainColor = color;
    }
    @Override
    public String toString() {
        return MaxSpeed+";"+Weight+";"+MainColor.getRGB();
    }
}
