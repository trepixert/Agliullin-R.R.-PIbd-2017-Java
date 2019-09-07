import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JList listLevels;
    private DefaultListModel model;
    private JLabel levelLabel;
    MultiLevelHangar hangar;
    private final int countLevel = 5;

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
        eHandler handler = new eHandler();
        hangar = new MultiLevelHangar(countLevel,getWidth(),getHeight());
        listLevels = new JList();
        model = new DefaultListModel();
        for(int i=0;i<countLevel;i++)
            model.addElement("Уровень: "+i);
        listLevels.setModel(model);
        listLevels.setSelectedIndex(0);
        listLevels.setBounds(800,70,200,100);
        add(listLevels);
        listLevels.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                repaint();
            }
        });
        setToHangarBaseAirCraft.setBounds(800,15,200,20);
        add(setToHangarBaseAirCraft);
        setToHangarBaseAirCraft.addActionListener(handler);
        setToHangarCoolAirCraft.setBounds(800,40,200,20);
        add(setToHangarCoolAirCraft);
        setToHangarCoolAirCraft.addActionListener(handler);
        removeAirCraftLabel.setText("Забрать машину: ");
        removeAirCraftLabel.setBounds(800,170,200,20);
        add(removeAirCraftLabel);
        removeAirCraftField.setBounds(800,200,200,40);
        add(removeAirCraftField);
        removeAirCraft.setBounds(800,250,200,20);
        add(removeAirCraft);
        add(removedAirCraft);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        if (hangar != null) {
            hangar.draw((Graphics2D) g);
        int selectedLevel = listLevels.getSelectedIndex();
        if(selectedLevel!=-1)
            if(hangar!=null) hangar.getHangar(selectedLevel).Draw((Graphics2D) g);
    }

    public void setListLevels(JList levels){
        listLevels = levels;
    }

    class eHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==setToHangarBaseAirCraft){
                if(listLevels.getSelectedIndex()>-1){
                    Color mainColor = JColorChooser.showDialog(null,"Choose a color",Color.RED);
                    armorAirCraft = new BaseArmorAirCraft(100,1000,mainColor);
                    int place = hangar.getHangar(listLevels.getSelectedIndex()).addAirCraft(armorAirCraft);
                    if(place != -1)
                        repaint();
                }
            }
            if(e.getSource()==setToHangarCoolAirCraft){
                if(listLevels.getSelectedIndex()>-1){
                    Color mainColor = JColorChooser.showDialog(null,"Choose a color",Color.RED);
                    Color dopColor = JColorChooser.showDialog(null,"Choose a color", Color.RED);
                    armorAirCraft = new AirCraft(100,1000,mainColor,dopColor,true,true,Color.BLACK);
                    int place = hangar.getHangar(listLevels.getSelectedIndex()).addAirCraft(armorAirCraft);
                    if(place != -1 )
                        repaint();
                }
            }
            if(e.getSource()== removeAirCraft){
                if(!removeAirCraftField.getText().equals("")){
                    if(listLevels.getSelectedIndex()>-1){
                        Object airCraft = hangar.getHangar(listLevels.getSelectedIndex()).removeAirCraft(Integer.parseInt(removeAirCraftField.getText()));
                        if(airCraft!=null){
                            ((IArmorAirCraft) airCraft).SetPosition(40,40,removedAirCraft.getX(),removedAirCraft.getY());
                            removedAirCraft.setAirCraft((IArmorAirCraft) airCraft);
                            removedAirCraft.repaint();
                        }else{
                            removedAirCraft.setAirCraft(null);
                            removedAirCraft.repaint();
                        }
                        repaint();
                    }
                }
            }
        }
    }
}
