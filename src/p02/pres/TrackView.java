package p02.pres;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrackView
    extends JFrame {

    private JTable carTable;
    private List<TableModelConstructor> obstacleTableModels;
    private SevenSegmentContainer counter;

    public TrackView() {
        setTitle("Autoslalom Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(340, 340));

        ImageIcon img = new ImageIcon("src/p02/source/background.jpg");
        JLabel bgLabel = new JLabel(img);
        bgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        layeredPane.add(bgLabel, JLayeredPane.DEFAULT_LAYER);

        int[] cellWidth = {25,30,35,40,50,60,70};
        int[] cellHeight = {30,35,40,45,50,55,60};
        int[] xPos = {250,230,205,185,150,110,70};
        int yPos = 0;

        obstacleTableModels = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            JTable table = new JTable();
            TableModelConstructor tmc = new TableModelConstructor(new int[]{0,0,0});
            obstacleTableModels.add(tmc);
            table.setModel(tmc);
            table.setRowHeight(cellHeight[i]);
            table.getColumnModel().getColumn(0).setPreferredWidth(cellWidth[i]);
            table.getColumnModel().getColumn(1).setPreferredWidth(cellWidth[i]);
            table.getColumnModel().getColumn(2).setPreferredWidth(cellWidth[i]);
            table.setDefaultRenderer(Object.class, new TableObstacleRenderer());
            table.setBounds(xPos[i], yPos, cellWidth[i] * 3, cellHeight[i]);
            table.setBackground(new Color(199,211,189,255));
            table.setSelectionBackground(new Color(199,211,189,255));
            table.setShowGrid(false);
            layeredPane.add(table, JLayeredPane.PALETTE_LAYER);
            yPos += cellHeight[i];
        }

        carTable = new JTable();
        carTable.setModel(new TableModelConstructor(new int[]{0,1,0}));
        carTable.setRowHeight(cellHeight[6]);
        carTable.getColumnModel().getColumn(0).setPreferredWidth(cellWidth[6]);
        carTable.getColumnModel().getColumn(1).setPreferredWidth(cellWidth[6]);
        carTable.getColumnModel().getColumn(2).setPreferredWidth(cellWidth[6]);
        carTable.setDefaultRenderer(Object.class, new TableCarRenderer());
        carTable.setBounds(xPos[6], yPos, cellWidth[6] * 3, cellHeight[6]);
        carTable.setBackground(new Color(199,211,189,255));
        carTable.setSelectionBackground(new Color(199,211,189,255));
        carTable.setShowGrid(false);
        layeredPane.add(carTable, JLayeredPane.PALETTE_LAYER);

        counter = new SevenSegmentContainer();
        counter.setBounds(5, 25, 110, 80);
        layeredPane.add(counter, JLayeredPane.PALETTE_LAYER);

        add(layeredPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTable getCarTable() {
        return carTable;
    }

    public List<TableModelConstructor> getObstacleTableModels() {
        return obstacleTableModels;
    }

    public SevenSegmentContainer getCounter() {
        return counter;
    }
}
