import java.awt.*;

public interface IArmorAirCraft {
    void SetPosition(int x, int y, int weight, int height);
    void moveAirCraft(Direction direction);
    void DrawAirCraft(Graphics g);
    void setMainColor(Color color);
}
