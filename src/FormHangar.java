import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class FormHangar extends JPanel {
    private ArmorAirCraft armorAirCraft;
    private Board removedAirCraft = new Board();
    private JButton setToHangarBaseAirCraft = new JButton("Припарковать обычный");
    private JButton setToHangarCoolAirCraft = new JButton("Припарковать крутой ");
    private JLabel removeAirCraftLabel = new JLabel();
    private JTextField removeAirCraftField = new JTextField();
    private JButton removeAirCraft = new JButton("Забрать самолет");
    private JList<String> listLevels;
    private DefaultListModel<String> model;
    private MultiLevelHangar hangar;
    private final int countLevel = 5;

    public FormHangar() {
        setLayout(null);
        init();
        eventsHandler();
    }

    private void eventsHandler() {
        removeAirCraft.addActionListener(e -> {
            if (!removeAirCraftField.getText().equals("")) {
                if (listLevels.getSelectedIndex() > -1) {
                    ArmorAirCraft airCraft = hangar.getHangar(listLevels.getSelectedIndex()).removeAirCraft(Integer.parseInt(removeAirCraftField.getText()));
                    if (airCraft != null) {
                        airCraft.setPosition(40, 40, removedAirCraft.getX(), removedAirCraft.getY());
                        removedAirCraft.setAirCraft(airCraft);
                        removedAirCraft.repaint();
                    } else {
                        removedAirCraft.setAirCraft(null);
                        removedAirCraft.repaint();
                    }
                    repaint();
                }
            }
        });

        setToHangarBaseAirCraft.addActionListener(e -> {
            if (listLevels.getSelectedIndex() > -1) {
                Color mainColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                armorAirCraft = new BaseArmorAirCraftImpl(100, 1000, mainColor);
                int place = hangar.getHangar(listLevels.getSelectedIndex()).addAirCraft(armorAirCraft);
                if (place != -1)
                    repaint();
            }
        });

        setToHangarCoolAirCraft.addActionListener(e -> {
            if (listLevels.getSelectedIndex() > -1) {
                Color mainColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                Color dopColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
                armorAirCraft = new AirCraftImpl(100, 1000, mainColor, dopColor, true, true, Color.BLACK);
                int place = hangar.getHangar(listLevels.getSelectedIndex()).addAirCraft(armorAirCraft);
                if (place != -1) {
                    repaint();
                }
            }
        });

        listLevels.addListSelectionListener(e -> repaint());
    }

    private void init() {
        hangar = new MultiLevelHangar(countLevel, getWidth(), getHeight());
        listLevels = new JList<>();
        model = new DefaultListModel<>();
        IntStream.range(0, countLevel).forEach(i -> model.addElement("Уровень: " + i));
        listLevels.setModel(model);
        listLevels.setSelectedIndex(0);

        setToHangarBaseAirCraft.setBounds(800, 15, 200, 20);
        setToHangarCoolAirCraft.setBounds(800, 40, 200, 20);
        removeAirCraftField.setBounds(800, 200, 200, 40);
        removeAirCraftLabel.setBounds(800, 170, 200, 20);
        removeAirCraft.setBounds(800, 250, 200, 20);
        removedAirCraft.setBounds(710, 320, 300, 580);
        listLevels.setBounds(800, 70, 200, 100);

        removeAirCraftLabel.setText("Забрать самолёт: ");

        add(setToHangarBaseAirCraft);
        add(setToHangarCoolAirCraft);
        add(removeAirCraftField);
        add(removeAirCraftLabel);
        add(removeAirCraft);
        add(removedAirCraft);
        add(listLevels);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int selectedLevel = listLevels.getSelectedIndex();
        if (selectedLevel != -1) {
            if (hangar != null) {
                hangar.getHangar(selectedLevel).draw((Graphics2D) g);
            }
        }
    }

    public void setListLevels(JList<String> levels) {
        listLevels = levels;
    }
}
