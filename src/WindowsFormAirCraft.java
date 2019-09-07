import javax.swing.*;

public class WindowsFormAirCraft extends JFrame {

    public WindowsFormAirCraft() {
        JFrame window = new JFrame("AirCraft");
        setUp(window);
    }

    private void setUp(JFrame window) {
        window.setSize(900, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new AirCraftPanel());
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new WindowsFormAirCraft();
    }
}
