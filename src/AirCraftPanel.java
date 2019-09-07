import javax.swing.*;
import java.awt.*;

public class AirCraftPanel extends JPanel {
    private JButton createButton = new JButton("Создать самолет");
    private JButton createCoolButton = new JButton("Создать крутой самолет");
    private JButton toUp = new JButton("Вверх");
    private JButton toDown = new JButton("Вниз");
    private JButton toLeft = new JButton("Влево");
    private JButton toRight = new JButton("Вправо");
    private BaseArmorAirCraftImpl baseArmorAirCraftImpl;

    public AirCraftPanel() {
        setLayout(null);
        init();
        createEvent();
        initDrivingDirections();
    }

    private void createEvent() {
        createButton.addActionListener(e -> {
            baseArmorAirCraftImpl = new BaseArmorAirCraftImpl((int) (Math.random() * 100) + 1, (int) (Math.random() * 5000) + 1000, Color.GRAY);
            baseArmorAirCraftImpl.setPosition((int) (Math.random() * 100) + 1, (int) (Math.random() * 100) + 1, getWidth(), getHeight());
            addAirCraft(baseArmorAirCraftImpl);
            repaint();
        });
        createCoolButton.addActionListener(e -> {
            baseArmorAirCraftImpl = new AirCraftImpl((int) (Math.random() * 501) + 100, (int) (Math.random() * 5000) + 1000, Color.GRAY, Color.RED, true, true, Color.BLACK);
            baseArmorAirCraftImpl.setPosition((int) (Math.random() * 501) + 100, (int) (Math.random() * 201) + 100, getWidth(), getHeight());
            addAirCraft(baseArmorAirCraftImpl);
            repaint();
        });
    }

    private void init() {
        createButton.setBounds(10, 10, 150, 20);
        createCoolButton.setBounds(160, 10, 250, 20);
        toUp.setBounds(700, 400, 90, 20);
        toDown.setBounds(700, 430, 90, 20);
        toLeft.setBounds(600, 410, 90, 20);
        toRight.setBounds(800, 410, 90, 20);

        add(createButton);
        add(createCoolButton);
        add(toUp);
        add(toDown);
        add(toLeft);
        add(toRight);
    }

    private void initDrivingDirections() {
        toUp.addActionListener(e -> {
            if (baseArmorAirCraftImpl != null) {
                baseArmorAirCraftImpl.moveAirCraft(Direction.UP);
                repaint();
            }
        });
        toDown.addActionListener(e -> {
            if (baseArmorAirCraftImpl != null) {
                baseArmorAirCraftImpl.moveAirCraft(Direction.DOWN);
                repaint();
            }
        });
        toLeft.addActionListener(e -> {
            if (baseArmorAirCraftImpl != null) {
                baseArmorAirCraftImpl.moveAirCraft(Direction.LEFT);
                repaint();
            }
        });
        toRight.addActionListener(e -> {
            if (baseArmorAirCraftImpl != null) {
                baseArmorAirCraftImpl.moveAirCraft(Direction.RIGHT);
                repaint();
            }
        });
    }

    public void addAirCraft(BaseArmorAirCraftImpl baseArmorAirCraftImpl) {
        this.baseArmorAirCraftImpl = baseArmorAirCraftImpl;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (baseArmorAirCraftImpl != null)
            baseArmorAirCraftImpl.drawAirCraft(g);
    }
}
