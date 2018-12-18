import javax.swing.*;

public class WindowsFormAirCraft extends JFrame{
    private JFrame window;
    public WindowsFormAirCraft(){
        window = new JFrame("AirCraft");
        window.setSize(1200,1024);
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
