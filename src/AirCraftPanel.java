import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class AirCraftPanel extends JPanel {
    private JButton createButton = new JButton("Создать самолет");
    private JButton toUp = new JButton("Вверх");
    private JButton toDown = new JButton("Вниз");
    private JButton toLeft = new JButton("Влево");
    private JButton toRight = new JButton("Вправо");
    private ArmorAirCraft armorAirCraft;
    public AirCraftPanel(){
        eHandler handler = new eHandler();
        setLayout(null);
        createButton.setBounds(10,10,150,20);
        add(createButton);
        createButton.addMouseListener(handler);
        toUp.setBounds(700,400,90,20);
        add(toUp);
        toUp.addMouseListener(handler);
        toDown.setBounds(700,430,90,20);
        add(toDown);
        toDown.addMouseListener(handler);
        toLeft.setBounds(600,410,90,20);
        add(toLeft);
        toLeft.addMouseListener(handler);
        toRight.setBounds(800,410,90,20);
        add(toRight);
        toRight.addMouseListener(handler);
    }
    public void addAirCraft(ArmorAirCraft armorAirCraft){
        this.armorAirCraft = armorAirCraft;
    }
    public void paint(Graphics g){
        super.paint(g);
        if(armorAirCraft!=null)
            armorAirCraft.DrawAirCraft(g);
    }
    class eHandler implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource()==createButton){
                armorAirCraft = new ArmorAirCraft((int)(Math.random()*100)+1,(int)(Math.random()*5000)+1000, Color.GRAY,Color.BLACK);
                armorAirCraft.SetPosition((int)(Math.random()*100)+1,(int)(Math.random()*100)+1, getWidth(),getHeight());
                addAirCraft(armorAirCraft);
                repaint();
            }
            if(e.getSource()==toUp&&armorAirCraft!=null){
                armorAirCraft.moveAirCraft(Direction.Up);
                repaint();
            }
            if(e.getSource()==toDown&&armorAirCraft!=null){
                armorAirCraft.moveAirCraft(Direction.Down);
                repaint();
            }
            if(e.getSource()==toRight&&armorAirCraft!=null){
                armorAirCraft.moveAirCraft(Direction.Right);
                repaint();
            }
            if(e.getSource()==toLeft&&armorAirCraft!=null){
                armorAirCraft.moveAirCraft(Direction.Left);
                repaint();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
    }
}
