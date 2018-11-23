import java.awt.*;

public class AirCraft extends BaseArmorAirCraft {
    private Color DopColor;
    private Color GunColor;
    public boolean modernizide;
    public boolean guns;

    public AirCraft(int maxSpeed, float weight, Color mainColor, Color dopColor, boolean modernizide, boolean guns, Color gunColor){
        super(maxSpeed,weight,mainColor);
        DopColor = dopColor;
        GunColor = gunColor;
        this.modernizide = modernizide;
        this.guns = guns;
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
        if (modernizide) {
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
}
