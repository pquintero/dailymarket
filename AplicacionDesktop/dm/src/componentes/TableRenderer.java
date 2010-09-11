package componentes;
import java.awt.Component;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

public class TableRenderer extends JFrame {
    public TableRenderer() {
    }
}

class MultiRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    JCheckBox checkBox = new JCheckBox();

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Boolean) { // Boolean
            checkBox.setSelected(((Boolean) value).booleanValue());
            checkBox.setHorizontalAlignment(JLabel.CENTER);
            return checkBox;
        }
        String str = (value == null) ? "" : value.toString();
        return super.getTableCellRendererComponent(table, str, isSelected,
                hasFocus, row, column);
    }
}

class MultiEditor implements TableCellEditor {

    private final static int BOOLEAN = 1;

    private final static int STRING = 2;

    private final static int NUM_EDITOR = 3;

    DefaultCellEditor[] cellEditors;

    int flg;

    public MultiEditor() {
        cellEditors = new DefaultCellEditor[NUM_EDITOR];
        // ----------------------------------------------------
        JCheckBox checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(JLabel.CENTER);
        cellEditors[BOOLEAN] = new DefaultCellEditor(checkBox);
        // ----------------------------------------------------
        JTextField textField = new JTextField();
        cellEditors[STRING] = new DefaultCellEditor(textField);
        // ----------------------------------------------------
        flg = BOOLEAN;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
//        System.err.println("getTableCellEditorComponent called:");
//        System.err.println("   isSelected: " + isSelected);
//        System.err.println("   row: " + row);
//        System.err.println("   col: " + column);
//        
        if (value instanceof Boolean) { // Boolean
            //System.err.println("   Boolean");
            flg = BOOLEAN;
            return cellEditors[BOOLEAN].getTableCellEditorComponent(table,
                    value, isSelected, row, column);
        } else if (value instanceof String) { // String
           // System.err.println("   String");
            flg = STRING;
            return cellEditors[STRING].getTableCellEditorComponent(table,
                    value, isSelected, row, column);
        } else {
            //System.err.println("   Trouble!");
        }
        return null;
    }

    public Object getCellEditorValue() {
        switch (flg) {
        case BOOLEAN:
        case STRING:
            return cellEditors[flg].getCellEditorValue();
        default:
            return null;
        }
    }

    public Component getComponent() {
        return cellEditors[flg].getComponent();
    }

    public boolean stopCellEditing() {
        return cellEditors[flg].stopCellEditing();
    }

    public void cancelCellEditing() {
        cellEditors[flg].cancelCellEditing();
    }

    public boolean isCellEditable(EventObject anEvent) {
        return cellEditors[flg].isCellEditable(anEvent);
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return cellEditors[flg].shouldSelectCell(anEvent);
    }

    public void addCellEditorListener(CellEditorListener l) {
        cellEditors[flg].addCellEditorListener(l);
    }

    public void removeCellEditorListener(CellEditorListener l) {
        cellEditors[flg].removeCellEditorListener(l);
    }

    public void setClickCountToStart(int n) {
        cellEditors[flg].setClickCountToStart(n);
    }

    public int getClickCountToStart() {
        return cellEditors[flg].getClickCountToStart();
    }
}  