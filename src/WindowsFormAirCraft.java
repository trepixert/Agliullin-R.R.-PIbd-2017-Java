import javax.swing.*;

public class WindowsFormAirCraft extends JFrame{
    private JFrame window;
    public WindowsFormAirCraft(){
        window = new JFrame("AirCraft");
        window.setSize(900,500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.add(new AirCraftPanel());
        window.setResizable(false);
        window.setVisible(true);
    }
    public static void main(String[] args) {
        new WindowsFormAirCraft();
    }
}
