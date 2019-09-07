import javax.swing.*;
import java.awt.*;

public class DroppedPanel extends JPanel {
    private ArmorAirCraft airCraft;

    public void setAirCraft(ArmorAirCraft airCraft) {
        this.airCraft = airCraft;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (airCraft != null) {
            airCraft.drawAirCraft(g);
        }
    }
}
