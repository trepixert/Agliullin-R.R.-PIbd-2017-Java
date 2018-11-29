import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.io.IOException;

public class FormAirCraftConfig extends JDialog {
    private boolean succes;
    private DroppedPanel panel;
    private JLabel addBaseAirCraft = new JLabel("Самолет");
    private JLabel addCoolAirCraft = new JLabel("Истребитель");
    private JLabel addMainColor = new JLabel("Добавить основной цвет");
    private JLabel addDopColor = new JLabel("Добавить дополнительный цвет");
    private JButton addAirCraft = new JButton("Добавить");
    private JButton cancel = new JButton("Отмена");
    private IArmorAirCraft airCraft;

    public FormAirCraftConfig(JFrame frame){
        super(frame,true);
        setSize(1000,800);
        setLayout(null);
        panel = new DroppedPanel();
        addComponents();
    }

    public void addComponents(){
        setLayout(null);
        Border border = BorderFactory.createLineBorder(Color.BLACK,1);
        eHandler mouseHandler = new eHandler();
        Handler handler = new Handler();

        //Добавить кнопкку "Добавить"
        addAirCraft.setBounds(30,30,100,20);
        addAirCraft.addActionListener(handler);
        add(addAirCraft);
        //Добавить кнопку "Отмена"
        cancel.setBounds(30,60,100,20);
        cancel.addActionListener(handler);
        add(cancel);
        //Добавить обычный самолет
        addBaseAirCraft.setBounds(20,100,150,30);
        addBaseAirCraft.setHorizontalAlignment(SwingConstants.CENTER);
        addBaseAirCraft.setBorder(border);
        addBaseAirCraft.addMouseListener(mouseHandler);
        addBaseAirCraft.setTransferHandler(new TransferHandler("text"));
        add(addBaseAirCraft);
        //Добавить модернизированный самолет
        addCoolAirCraft.setBounds(20,140,150,30);
        addCoolAirCraft.setHorizontalAlignment(SwingConstants.CENTER);
        addCoolAirCraft.setBorder(border);
        addCoolAirCraft.addMouseListener(mouseHandler);
        addCoolAirCraft.setTransferHandler(new TransferHandler("text"));
        add(addCoolAirCraft);
        //Создаем панель для оформления самолета
        panel.setSize(400,300);
        panel.setLocation(250,50);
        panel.setBorder(border);
        add(panel);
        panel.setDropTarget(new DropTarget(){
            public void drop(DropTargetDropEvent e) {
                try {
                    for (DataFlavor df : e.getTransferable().getTransferDataFlavors()) {
                        String str = (String)e.getTransferable().getTransferData(df);
                        if (str.equals("Самолет")) {
                            airCraft = new BaseArmorAirCraft(100, 100, Color.WHITE);
                        } else if (str.equals("Истребитель")) {
                            airCraft = new AirCraft(100, 100, Color.WHITE, Color.BLACK,
                                    true, true, Color.BLACK);
                        }
                        draw(panel, airCraft);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            public void dragEnter(DropTargetDragEvent e) {
                for (DataFlavor df : e.getTransferable().getTransferDataFlavors()) {
                    try {
                        if (e.getTransferable().getTransferData(df) instanceof String)
                            e.acceptDrag(DnDConstants.ACTION_COPY);
                        else
                            e.acceptDrag(DnDConstants.ACTION_NONE);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        //Создаем Labels для цветов
        //основной цвет
        addMainColor.setBounds(280,400,150,30);
        addMainColor.setBorder(border);
        addMainColor.setHorizontalAlignment(SwingConstants.CENTER);
        addMainColor.setDropTarget(new DropTarget(){
            public void drop(DropTargetDropEvent e) {
                if (airCraft != null) {
                    try {
                        for (DataFlavor df : e.getTransferable().getTransferDataFlavors()) {
                            airCraft.setMainColor((selectColor(e.getTransferable().getTransferData(df).toString())));
                            draw(panel, airCraft);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex + "FF");
                    }
                }
            }
            public void dragEnter(DropTargetDragEvent e) {
                for (DataFlavor df : e.getTransferable().getTransferDataFlavors()) {
                    try {
                        if (e.getTransferable().getTransferData(df) instanceof String)
                            e.acceptDrag(DnDConstants.ACTION_COPY);
                        else
                            e.acceptDrag(DnDConstants.ACTION_NONE);
                    } catch (UnsupportedFlavorException | IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        add(addMainColor);
        //дополнительный цвет
        addDopColor.setBounds(280,440,200,30);
        addDopColor.setBorder(border);
        addDopColor.setHorizontalAlignment(SwingConstants.CENTER);
        addDopColor.setDropTarget(new DropTarget(){
            public void drop(DropTargetDropEvent e) {
                if (airCraft != null) {
                    try {
                        for (DataFlavor df : e.getTransferable().getTransferDataFlavors()) {
                            ((AirCraft) airCraft).setDopColor((selectColor(e.getTransferable().getTransferData(df).toString())));
                            draw(panel, airCraft);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
            public void dragEnter(DropTargetDragEvent e) {
                for (DataFlavor df : e.getTransferable().getTransferDataFlavors()) {
                    try {
                        if (e.getTransferable().getTransferData(df) instanceof String)
                            e.acceptDrag(DnDConstants.ACTION_COPY);
                        else
                            e.acceptDrag(DnDConstants.ACTION_NONE);
                    } catch (UnsupportedFlavorException | IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        add(addDopColor);
        //Создаем панельки для цветов
        addPanelsForColor();
    }

    private void addPanelsForColor(){
        Border border = BorderFactory.createLineBorder(Color.BLACK,1);
        eHandler handler = new eHandler();
        JPanel panelWhite = new JPanel();
        panelWhite.setBackground(Color.WHITE);
        panelWhite.setBounds(680, 50, 40, 40);
        panelWhite.setBorder(border);
        panelWhite.setName("white");
        panelWhite.addMouseListener(handler);
        panelWhite.setTransferHandler(new TransferHandler("name"));
        add(panelWhite);

        JPanel panelBlack = new JPanel();
        panelBlack.setBackground(Color.BLACK);
        panelBlack.setBounds(730, 50, 40, 40);
        panelBlack.setName("black");
        panelBlack.addMouseListener(handler);
        panelBlack.setTransferHandler(new TransferHandler("name"));
        add(panelBlack);

        JPanel panelRed = new JPanel();
        panelRed.setBackground(Color.RED);
        panelRed.setBorder(border);
        panelRed.setBounds(680, 100, 40, 40);
        panelRed.setName("red");
        panelRed.addMouseListener(handler);
        panelRed.setTransferHandler(new TransferHandler("name"));
        add(panelRed);

        JPanel panelOrange = new JPanel();
        panelOrange.setBackground(Color.ORANGE);
        panelOrange.setBorder(border);
        panelOrange.setBounds(730, 100, 40, 40);
        panelOrange.setName("orange");
        panelOrange.addMouseListener(handler);
        panelOrange.setTransferHandler(new TransferHandler("name"));
        add(panelOrange);

        JPanel panelPink = new JPanel();
        panelPink.setBackground(Color.PINK);
        panelPink.setBorder(border);
        panelPink.setBounds(680, 150, 40, 40);
        panelPink.setName("pink");
        panelPink.addMouseListener(handler);
        panelPink.setTransferHandler(new TransferHandler("name"));
        add(panelPink);

        JPanel panelBlue = new JPanel();
        panelBlue.setBackground(Color.BLUE);
        panelBlue.setBorder(border);
        panelBlue.setBounds(730, 150, 40, 40);
        panelBlue.setName("blue");
        panelBlue.addMouseListener(handler);
        panelBlue.setTransferHandler(new TransferHandler("name"));
        add(panelBlue);

        JPanel panelYellow = new JPanel();
        panelYellow.setBackground(Color.YELLOW);
        panelYellow.setBorder(border);
        panelYellow.setBounds(680, 200, 40, 40);
        panelYellow.setName("yellow");
        panelYellow.addMouseListener(handler);
        panelYellow.setTransferHandler(new TransferHandler("name"));
        add(panelYellow);

        JPanel panelGreen = new JPanel();
        panelGreen.setBackground(Color.GREEN);
        panelGreen.setBorder(border);
        panelGreen.setBounds(730, 200, 40, 40);
        panelGreen.setName("green");
        panelGreen.addMouseListener(handler);
        panelGreen.setTransferHandler(new TransferHandler("name"));
        add(panelGreen);
    }

    public boolean isSucces(){
        setVisible(true);
        return succes;
    }

    public Color selectColor(String s) {
        switch (s) {
            case "white":
                return Color.WHITE;
            case "black":
                return Color.BLACK;
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "orange":
                return Color.ORANGE;
            case "pink":
                return Color.PINK;
            case "blue":
                return Color.BLUE;
            case "yellow":
                return Color.YELLOW;
        }
        return null;
    }

    private void draw(DroppedPanel panel,IArmorAirCraft armorAirCraft){
        if(armorAirCraft!=null){
            armorAirCraft.SetPosition(10,40,panel.getWidth(),panel.getHeight());
            panel.setAirCraft(armorAirCraft);
            panel.repaint();
        }
    }

    public IArmorAirCraft getAirCraft(){
        return airCraft;
    }

    class Handler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==addAirCraft){
                succes=true;
                dispose();
            }
            if(e.getSource()==cancel){
                succes=false;
                dispose();
            }
        }
    }

    private class eHandler implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            JComponent jc = (JComponent) e.getSource();
            TransferHandler th = jc.getTransferHandler();
            th.exportAsDrag(jc,e,TransferHandler.COPY);
        }

        @Override
        public void mouseReleased(MouseEvent e) {}
    }
}
