import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormHangar extends JPanel {
    Hangar<IArmorAirCraft> hangar;
    IArmorAirCraft armorAirCraft;
    private Board removedAirCraft = new Board();
    private JButton setToHangarBaseAirCraft = new JButton("Припарковать обычный");
    private JButton setToHangarCoolAirCraft = new JButton("Припарковать крутой ");
    private JLabel removeAirCraftLabel = new JLabel();
    private JTextField removeAirCraftField = new JTextField();
    private JButton removeAirCraft = new JButton("Забрать самолет");


    public FormHangar(){
        setLayout(null);
        eHandler handler = new eHandler();
        hangar = new Hangar<>(20,getWidth(),getHeight());
        setToHangarBaseAirCraft.setBounds(800,15,200,20);
        add(setToHangarBaseAirCraft);
        setToHangarBaseAirCraft.addActionListener(handler);
        setToHangarCoolAirCraft.setBounds(800,40,200,20);
        add(setToHangarCoolAirCraft);
        setToHangarCoolAirCraft.addActionListener(handler);
        removeAirCraftLabel.setText("Забрать машину: ");
        removeAirCraftLabel.setBounds(800,70,200,20);
        add(removeAirCraftLabel);
        removeAirCraftField.setBounds(800,100,200,40);
        add(removeAirCraftField);
        removeAirCraft.setBounds(800,150,200,20);
        add(removeAirCraft);
        removeAirCraft.addActionListener(handler);
        removedAirCraft.setLocation(710,180);
        removedAirCraft.setSize(400,400);
        add(removedAirCraft);
    }

    public void paint(Graphics g){
        super.paint(g);
        if(hangar!=null)
            hangar.Draw((Graphics2D) g);
    }

    class eHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==setToHangarBaseAirCraft){
                armorAirCraft = new BaseArmorAirCraft((int)(Math.random()*100)+1,(int)(Math.random()*5000)+1000, Color.GRAY);
                int place = hangar.addAirCraft(armorAirCraft);
                repaint();
            }
            if(e.getSource()==setToHangarCoolAirCraft){
                armorAirCraft = new AirCraft((int)(Math.random()*501)+100,(int)(Math.random()*5000)+1000, Color.GRAY,Color.RED,true,true,Color.BLACK);
                int place = hangar.addAirCraft(armorAirCraft);
                repaint();
            }
            if(e.getSource()== removeAirCraft){
                if(!removeAirCraftField.getText().equals("")){
                    Object airCraft = hangar.removeAirCraft(Integer.parseInt(removeAirCraftField.getText()));
                    if(airCraft!=null){
                        ((IArmorAirCraft) airCraft).SetPosition(5,50,removedAirCraft.getWidth(),removedAirCraft.getHeight());
                        removedAirCraft.setAirCraft((IArmorAirCraft) airCraft);
                        removedAirCraft.repaint();
                        repaint();
                    }else{
                        removedAirCraft.setAirCraft(null);
                        removedAirCraft.repaint();
                    }
                }

            }
        }
    }
}
