import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class FormAirCraftConfig extends JDialog {
    private boolean success;
    private DroppedPanel panel;
    private JLabel addBaseAirCraft = new JLabel("Самолет");
    private JLabel addCoolAirCraft = new JLabel("Истребитель");
    private JLabel addMainColor = new JLabel("Добавить основной цвет");
    private JLabel addDopColor = new JLabel("Добавить дополнительный цвет");
    private JButton addAirCraft = new JButton("Добавить");
    private JButton cancel = new JButton("Отмена");
    private ArmorAirCraft airCraft;

    public FormAirCraftConfig(JFrame frame) {
        super(frame, true);
        setSize(1000, 800);
        setLayout(null);
        panel = new DroppedPanel();
        init();
    }

    private void init() {
        setLayout(null);
        eHandler mouseHandler = new eHandler();

        addAirCraft.setBounds(30, 30, 100, 20);
        cancel.setBounds(30, 60, 100, 20);
        addBaseAirCraft.setBounds(20, 100, 150, 30);
        addCoolAirCraft.setBounds(20, 140, 150, 30);
        panel.setBounds(250, 50, 400, 300);
        addMainColor.setBounds(280, 400, 150, 30);
        addDopColor.setBounds(280, 440, 200, 30);

        setUpComponents();

        setUpDragAndDrop();

        add(addAirCraft);
        add(cancel);
        add(addBaseAirCraft);
        add(addCoolAirCraft);
        add(panel);
        add(addMainColor);
        add(addDopColor);

        eventsHandler(mouseHandler);

        addPanelsForColor();
    }

    private void setUpComponents() {
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        addBaseAirCraft.setHorizontalAlignment(SwingConstants.CENTER);
        addBaseAirCraft.setBorder(border);
        addBaseAirCraft.setTransferHandler(new TransferHandler("text"));

        addCoolAirCraft.setHorizontalAlignment(SwingConstants.CENTER);
        addCoolAirCraft.setBorder(border);
        addCoolAirCraft.setTransferHandler(new TransferHandler("text"));

        addMainColor.setBorder(border);
        addMainColor.setHorizontalAlignment(SwingConstants.CENTER);

        addDopColor.setBorder(border);
        addDopColor.setHorizontalAlignment(SwingConstants.CENTER);

        //panel.setLocation(250, 50);
        panel.setBorder(border);
    }

    private void setUpDragAndDrop() {
        panel.setDropTarget(new DropTarget() {
            public void drop(DropTargetDropEvent e) {
                try {
                    for (DataFlavor df : e.getTransferable().getTransferDataFlavors()) {
                        String str = (String) e.getTransferable().getTransferData(df);
                        if (str.equals("Самолет")) {
                            airCraft = new BaseArmorAirCraftImpl(100, 100, Color.WHITE);
                        } else if (str.equals("Истребитель")) {
                            airCraft = new AirCraftImpl(100, 100, Color.WHITE, Color.BLACK,
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

        addMainColor.setDropTarget(new DropTarget() {
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

        addDopColor.setDropTarget(new DropTarget() {
            public void drop(DropTargetDropEvent e) {
                if (airCraft != null) {
                    try {
                        for (DataFlavor df : e.getTransferable().getTransferDataFlavors()) {
                            ((AirCraftImpl) airCraft).setComColor((selectColor(e.getTransferable().getTransferData(df).toString())));
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
    }

    private void eventsHandler(eHandler mouseHandler) {
        addBaseAirCraft.addMouseListener(mouseHandler);
        addAirCraft.addActionListener(e -> {
            success = true;
            dispose();
        });
        cancel.addActionListener(e -> {
            success = false;
            dispose();
        });
        addCoolAirCraft.addMouseListener(mouseHandler);
    }

    private void addPanelsForColor() {
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
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

    public boolean isSuccess() {
        setVisible(true);
        return success;
    }

    private Color selectColor(String s) {
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

    private void draw(DroppedPanel panel, ArmorAirCraft armorAirCraft) {
        if (armorAirCraft != null) {
            armorAirCraft.setPosition(10, 40, panel.getWidth(), panel.getHeight());
            panel.setAirCraft(armorAirCraft);
            panel.repaint();
        }
    }

    public ArmorAirCraft getAirCraft() {
        return airCraft;
    }

    private static class eHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JComponent jc = (JComponent) e.getSource();
            TransferHandler th = jc.getTransferHandler();
            th.exportAsDrag(jc, e, TransferHandler.COPY);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }
}
