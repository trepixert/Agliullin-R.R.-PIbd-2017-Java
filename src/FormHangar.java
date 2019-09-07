import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.stream.IntStream;

public class FormHangar extends JPanel {
    private ArmorAirCraft armorAirCraft;
    private Board removedAirCraft = new Board();
    private JButton setAirCraft = new JButton("Добавить самолет");
    private JLabel removeAirCraftLabel = new JLabel("Забрать самолёт: ");
    private JTextField removeAirCraftField = new JTextField();
    private JButton removeAirCraft = new JButton("Забрать самолет");
    private JList<String> listLevels;
    private DefaultListModel<String> model;
    private MultiLevelHangar hangar;
    private final int countLevel = 5;
    private FormAirCraftConfig formAirCraftConfig;
    private JMenuBar menu;
    private JMenuItem save;
    private JMenuItem load;

    public FormHangar(JFrame window) {
        setLayout(null);
        init(window);
        eventsHandler();
    }

    private void init(JFrame window) {
        hangar = new MultiLevelHangar(countLevel, getWidth(), getHeight());
        listLevels = new JList<>();
        model = new DefaultListModel<>();
        menu = new JMenuBar();
        hangar = new MultiLevelHangar(countLevel, getWidth(), getHeight());
        IntStream.range(0, countLevel).forEach(i -> model.addElement("Уровень: " + i));
        listLevels.setModel(model);
        listLevels.setSelectedIndex(0);

        setAirCraft.setBounds(800, 10, 200, 20);
        removeAirCraftField.setBounds(800, 200, 200, 40);
        removeAirCraftLabel.setBounds(800, 170, 200, 20);
        removeAirCraft.setBounds(800, 250, 200, 20);
        removedAirCraft.setBounds(710, 320, 300, 580);
        listLevels.setBounds(800, 70, 200, 100);
        menu.setBounds(5, 0, 1200, 20);

        add(setAirCraft);
        add(removeAirCraftField);
        add(removeAirCraftLabel);
        add(removeAirCraft);
        add(removedAirCraft);
        add(listLevels);

        menu.add(createFileMenu());
        window.setJMenuBar(menu);
        menu.setVisible(true);
    }

    public JMenu createFileMenu() {
        JMenu file = new JMenu("Файл");
        save = new JMenuItem("Сохранить");
        load = new JMenuItem("Загрузить");
        file.add(save);
        file.addSeparator();
        file.add(load);
        return file;
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
        setAirCraft.addActionListener(e -> {
            formAirCraftConfig = new FormAirCraftConfig(new JFrame());
            if (formAirCraftConfig.isSuccess()) {
                armorAirCraft = formAirCraftConfig.getAirCraft();
                int place = hangar.getHangar(listLevels.getSelectedIndex()).addAirCraft(armorAirCraft);
                if (place != -1)
                    repaint();
            }
            repaint();
        });

        listLevels.addListSelectionListener(e -> repaint());

        save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null,
                        hangar.saveData(file.getPath()) ? "Сохранение прошло успешно!" : "Не сохранилось");
                repaint();
            }
        });

        load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null,
                        hangar.loadData(file.getPath()) ? "Загрузка прошла успешно!" : "Не загрузилось");
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int selectedLevel = listLevels.getSelectedIndex();
        if (selectedLevel != -1 && hangar != null) {
            hangar.getHangar(selectedLevel).draw((Graphics2D) g);
        }
    }

    public void setListLevels(JList<String> levels) {
        listLevels = levels;
    }

}

