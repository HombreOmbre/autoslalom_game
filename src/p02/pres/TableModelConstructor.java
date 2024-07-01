package p02.pres;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModelConstructor extends AbstractTableModel {

    private List<Integer> cellList;

    public TableModelConstructor(int[] binaryPos) {
        this.cellList = new ArrayList<>();
        for (int num : binaryPos)
            cellList.add(num);
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return JLabel.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.cellList.get(columnIndex);
    }

    public void updateData(int[] newData) {
        for (int i = 0; i < newData.length; i++) {
            if (this.cellList.get(i) != newData[i]) {
                this.cellList.set(i, newData[i]);
                fireTableCellUpdated(0, i);
            }
        }
    }
}
