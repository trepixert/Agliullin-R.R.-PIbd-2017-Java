import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FormHangar extends JPanel {
    private IArmorAirCraft armorAirCraft;
    private Board removedAirCraft = new Board();
    private JButton setAirCraft = new JButton("Добавить самолет");
    private JLabel removeAirCraftLabel = new JLabel();
    private JTextField removeAirCraftField = new JTextField();
    private JButton removeAirCraft = new JButton("Забрать самолет");
    private JList listLevels;
    private DefaultListModel model;
    private JLabel levelLabel;
    MultiLevelHangar hangar;
    private final int countLevel = 5;
    private FormAirCraftConfig formAirCraftConfig;
    private JMenuBar menu;
    private JMenuItem save;
    private JMenuItem load;

    public FormHangar(JFrame window){
        setLayout(null);
        eHandler handler = new eHandler();
        menu = new JMenuBar();
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
        setAirCraft.setBounds(800,30,200,20);
        add(setAirCraft);
        setAirCraft.addActionListener(handler);
        removeAirCraftLabel.setText("Забрать машину: ");
        removeAirCraftLabel.setBounds(800,170,200,20);
        add(removeAirCraftLabel);
        removeAirCraftField.setBounds(800,200,200,40);
        add(removeAirCraftField);
        removeAirCraft.setBounds(800,250,200,20);
        add(removeAirCraft);
        removeAirCraft.addActionListener(handler);
        removedAirCraft.setLocation(710,280);
        removedAirCraft.setSize(400,400);
        add(removedAirCraft);
        menu.add(createFileMenu());
        window.setJMenuBar(menu);
        menu.setBounds(5,0,1200,20);
        menu.setVisible(true);
    }

    public JMenu createFileMenu(){
        eHandler handler = new eHandler();
        JMenu file = new JMenu("Файл");
        save = new JMenuItem("Сохранить");
        load = new JMenuItem("Загрузить");
        file.add(save);
        file.addSeparator();
        file.add(load);
        save.addActionListener(handler);
        load.addActionListener(handler);
        return file;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        int selectedLevel = listLevels.getSelectedIndex();
        if(selectedLevel!=-1)
            if(hangar!=null) hangar.getHangar(selectedLevel).Draw((Graphics2D) g);
    }

    public void setListLevels(JList levels){
        listLevels = levels;
    }

    private class eHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==setAirCraft){
                formAirCraftConfig = new FormAirCraftConfig(new JFrame());
                if(formAirCraftConfig.isSucces()){
                    armorAirCraft = formAirCraftConfig.getAirCraft();
                    int place = hangar.getHangar(listLevels.getSelectedIndex()).addAirCraft(armorAirCraft);
                    if(place!=-1)
                        repaint();
                }
                repaint();
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
            if(e.getSource()==save){
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if(hangar.saveData(file.getPath()))
                        JOptionPane.showMessageDialog(null,"Сохранение прошло успешно!");
                    else
                        JOptionPane.showMessageDialog(null,"Не сохранилось");
                }
            }
            if(e.getSource()==load){
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if(hangar.loadData(file.getPath()))
                        JOptionPane.showMessageDialog(null,"Загрузка прошла успешно!");
                    else
                        JOptionPane.showMessageDialog(null,"Не загрузилось");
                }
            }
            repaint();
        }
    }
}
