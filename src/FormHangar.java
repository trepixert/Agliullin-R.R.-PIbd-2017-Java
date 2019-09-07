import javax.swing.*;
import java.awt.*;

public class FormHangar extends JPanel {
    private Hangar<ArmorAirCraft> hangar;
    private ArmorAirCraft armorAirCraft;
    private Board removedAirCraft = new Board();
    private JButton setToHangarBaseAirCraft = new JButton("Припарковать обычный");
    private JButton setToHangarCoolAirCraft = new JButton("Припарковать крутой ");
    private JLabel removeAirCraftLabel = new JLabel();
    private JTextField removeAirCraftField = new JTextField();
    private JButton removeAirCraft = new JButton("Забрать самолет");


    public FormHangar() {
        setLayout(null);
        init();
        eventsHandler();
    }

    private void eventsHandler() {
        removeAirCraft.addActionListener(e -> {
            if (!removeAirCraftField.getText().equals("")) {
                ArmorAirCraft airCraft = hangar.removeAirCraft(Integer.parseInt(removeAirCraftField.getText()));
                if (airCraft != null) {
                    airCraft.setPosition(5, 50, removedAirCraft.getWidth(), removedAirCraft.getHeight());
                    removedAirCraft.setAirCraft(airCraft);
                    removedAirCraft.repaint();
                    repaint();
                } else {
                    removedAirCraft.setAirCraft(null);
                    removedAirCraft.repaint();
                }
            }
        });

        setToHangarBaseAirCraft.addActionListener(e -> {
            armorAirCraft = new BaseArmorAirCraftImpl((int) (Math.random() * 100) + 1, (int) (Math.random() * 5000) + 1000, Color.GRAY);
            hangar.addAirCraft(armorAirCraft);
            repaint();
        });

        setToHangarCoolAirCraft.addActionListener(e -> {
            armorAirCraft = new AirCraftImpl((int) (Math.random() * 501) + 100, (int) (Math.random() * 5000) + 1000, Color.GRAY, Color.RED, true, true, Color.BLACK);
            hangar.addAirCraft(armorAirCraft);
            repaint();
        });
    }

    private void init() {
        hangar = new Hangar<>(9, getWidth(), getHeight());

        setToHangarBaseAirCraft.setBounds(800, 15, 200, 20);
        setToHangarCoolAirCraft.setBounds(800, 40, 200, 20);
        removeAirCraftField.setBounds(800, 100, 200, 40);
        removeAirCraftLabel.setBounds(800, 70, 200, 20);
        removeAirCraft.setBounds(800, 150, 200, 20);
        removedAirCraft.setBounds(710, 180, 1100, 580);

        removeAirCraftLabel.setText("Забрать машину: ");

        add(setToHangarBaseAirCraft);
        add(setToHangarCoolAirCraft);
        add(removeAirCraftField);
        add(removeAirCraftLabel);
        add(removeAirCraft);
        add(removedAirCraft);
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (hangar != null) {
            hangar.draw((Graphics2D) g);
        }
    }
}
