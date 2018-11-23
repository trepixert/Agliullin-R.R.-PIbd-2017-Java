import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private IArmorAirCraft airCraft;
    public void setAirCraft(IArmorAirCraft airCraft){
        this.airCraft = airCraft;
    }

    public void paint(Graphics g){
        super.paint(g);
        if(airCraft!=null)
            airCraft.DrawAirCraft(g);
    }
}
