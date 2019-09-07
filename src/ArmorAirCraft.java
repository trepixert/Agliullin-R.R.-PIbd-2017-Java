import java.awt.*;

public interface ArmorAirCraft {
    void setPosition(int x, int y, int weight, int height);

    void moveAirCraft(Direction direction);

    void drawAirCraft(Graphics g);

    void setMainColor(Color color);
}
