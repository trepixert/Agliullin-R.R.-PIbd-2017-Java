import javax.swing.*;

public class WindowsFormAirCraft extends JFrame {

    public WindowsFormAirCraft() {
        JFrame window = new JFrame("AirCraft");
        setUp(window);
    }

    private void setUp(JFrame window) {
        window.setSize(1200, 1024);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.add(new FormHangar(window));
        window.setResizable(false);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new WindowsFormAirCraft();
    }
}
