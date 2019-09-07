import javax.swing.*;
import java.awt.*;

public class AirCraftPanel extends JPanel {
    private JButton createButton = new JButton("Создать самолет");
    private JButton toUp = new JButton("Вверх");
    private JButton toDown = new JButton("Вниз");
    private JButton toLeft = new JButton("Влево");
    private JButton toRight = new JButton("Вправо");
    private ArmorAirCraft armorAirCraft;

    public AirCraftPanel() {
        setLayout(null);
        createEvent();
        init();
        initDrivingDirections();
    }

    private void createEvent() {
        createButton.addActionListener(e -> {
            armorAirCraft = new ArmorAirCraft((int) (Math.random() * 100) + 1, (int) (Math.random() * 5000) + 1000, Color.GRAY, Color.BLACK);
            armorAirCraft.setPosition((int) (Math.random() * 100) + 1, (int) (Math.random() * 100) + 1, getWidth(), getHeight());
            addAirCraft(armorAirCraft);
            repaint();
        });
    }

    private void init() {
        createButton.setBounds(10, 10, 150, 20);
        toUp.setBounds(700, 400, 90, 20);
        toDown.setBounds(700, 430, 90, 20);
        toLeft.setBounds(600, 410, 90, 20);
        toRight.setBounds(800, 410, 90, 20);

        add(createButton);
        add(toUp);
        add(toDown);
        add(toLeft);
        add(toRight);
    }

    private void initDrivingDirections() {
        toUp.addActionListener(e -> {
            if (armorAirCraft != null) {
                armorAirCraft.moveAirCraft(Direction.Up);
                repaint();
            }
        });
        toDown.addActionListener(e -> {
            if (armorAirCraft != null) {
                armorAirCraft.moveAirCraft(Direction.Down);
                repaint();
            }
        });
        toLeft.addActionListener(e -> {
            if (armorAirCraft != null) {
                armorAirCraft.moveAirCraft(Direction.Left);
                repaint();
            }
        });
        toRight.addActionListener(e -> {
            if (armorAirCraft != null) {
                armorAirCraft.moveAirCraft(Direction.Right);
                repaint();
            }
        });
    }

    public void addAirCraft(ArmorAirCraft armorAirCraft) {
        this.armorAirCraft = armorAirCraft;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (armorAirCraft != null)
            armorAirCraft.drawAirCraft(g);
    }
}
