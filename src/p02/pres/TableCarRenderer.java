package p02.pres;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableCarRenderer
        extends JLabel
        implements TableCellRenderer {

    private JLabel label;

    public TableCarRenderer() {
        this.label = new JLabel();
        this.add(label);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        int cellHeight = table.getHeight();
        int cellWidth = table.getColumnModel().getColumn(column).getWidth();
        this.label.setSize(new Dimension(cellWidth, cellHeight));
        if ((Integer) value == 1) {
            ImageIcon icon = new ImageIcon("src/p02/source/car.png");
            Image image = icon.getImage().getScaledInstance((int)(cellWidth - cellWidth * 0.2), (int)(cellHeight - cellHeight * 0.1), Image.SCALE_SMOOTH);
            this.label.setIcon(new ImageIcon(image));
        } else {
            this.label.setIcon(null);
        }
        this.label.setHorizontalAlignment(JLabel.CENTER);
        this.label.setOpaque(false);
        return this;
    }
}
