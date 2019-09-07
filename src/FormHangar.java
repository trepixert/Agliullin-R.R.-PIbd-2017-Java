import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.IntStream;

public class FormHangar extends JPanel {
    private ArmorAirCraft armorAirCraft;
    private Board removedAirCraft = new Board();
    private JButton setAirCraft = new JButton("Добавить самолет");
    private JLabel removeAirCraftLabel = new JLabel("Забрать самолёт: ");
    private JButton sortButton = new JButton("Сортировать");
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
    private Logger logger;

    public FormHangar(JFrame window) {
        setLayout(null);
        init(window);
        eventsHandler();
    }

    private void init(JFrame window) {
        hangar = new MultiLevelHangar(countLevel, getWidth(), getHeight());
        listLevels = new JList<>();
        model = new DefaultListModel<>();
        logger = Logger.getLogger(FormHangar.class.getName());
        try {
            FileHandler fh = new FileHandler("C://Temp//logger.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu = new JMenuBar();
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
        sortButton.setBounds(1010, 30, 190, 20);

        add(setAirCraft);
        add(removeAirCraftField);
        add(removeAirCraftLabel);
        add(removeAirCraft);
        add(removedAirCraft);
        add(listLevels);
        add(sortButton);

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
                    try {
                        ArmorAirCraft airCraft = hangar.getHangar(listLevels.getSelectedIndex()).removeAirCraft(Integer.parseInt(removeAirCraftField.getText()));
                        airCraft.setPosition(40, 40, removedAirCraft.getX(), removedAirCraft.getY());
                        removedAirCraft.setAirCraft(airCraft);
                        removedAirCraft.repaint();
                        logger.info("Изъят самолет " + airCraft.toString() + " с места " + removeAirCraftField.getText());
                    } catch (HangarNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Не найдено!");
                        removedAirCraft.setAirCraft(null);
                        removedAirCraft.repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Неизвестная ошибка");
                    }
                    repaint();
                }
            }
        });
        setAirCraft.addActionListener(e -> {
            formAirCraftConfig = new FormAirCraftConfig(new JFrame());
            if (formAirCraftConfig.isSuccess()) {
                try {
                    armorAirCraft = formAirCraftConfig.getAirCraft();
                    int place = hangar.getHangar(listLevels.getSelectedIndex()).addAirCraft(armorAirCraft);
                    logger.info("Добавлен самолет " + armorAirCraft.toString() + " на место " + place);
                    if (place != -1)
                        repaint();
                } catch (HangarOverflowException | HangarAlreadyHaveException ex) {
                    ex.printStackTrace();
                }
            }
            repaint();
        });

        listLevels.addListSelectionListener(e -> repaint());

        save.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    hangar.saveData(file.getPath());
                    JOptionPane.showMessageDialog(null, "Сохранение прошло успешно!");
                    logger.info("Сохранено в файл " + file.getPath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Не сохранилось");
                    ex.printStackTrace();
                }
            }
        });

        load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    hangar.loadData(file.getPath());
                    JOptionPane.showMessageDialog(null, "Загрузка прошла успешно!");
                    logger.info("Загружено из файла " + file.getPath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Не загрузилось");
                    ex.printStackTrace();
                }
                repaint();
            }
        });

        sortButton.addActionListener(e -> {
            hangar.sort();
            repaint();
        });
        repaint();
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

