package gui.panels;

import domain.filter.Filter;
import domain.filter.Filterable;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class FilterPanel<T> extends JPanel {

    private Filter<T> filter = new Filter<>();
    private JTable filterTable = new JTable();
    private DefaultTableModel tableModel = new NotEditableTableModel();

    public FilterPanel(Dimension dimension) {
        setup(dimension);
    }

    private JTable getFilterTable() {
        return filterTable;
    }

    private DefaultTableModel getTableModel() {
        return tableModel;
    }

    private Filter<T> getFilter() {
        return filter;
    }

    private void update() {
        getTableModel().setRowCount(0);
        String[] headings = getFilter().getHeadings();
        if (headings != null) {
            getTableModel().setColumnIdentifiers(headings);
            for (T result : getFilter().getResults()) {
                getTableModel().addRow(((Filterable) result).getSearchValues());
            }
        }
        getFilterTable().updateUI();
        updateUI();
    }

    public void setup(Dimension dimension) {
        setPreferredSize(dimension);
        setLayout(new FlowLayout());
        getFilterTable().setModel(getTableModel());

        Dimension scrollPaneSize = dimension;
        JScrollPane scrollPane = new JScrollPane(getFilterTable());
        scrollPane.setPreferredSize(scrollPaneSize);
        add(scrollPane);
    }

    public T getSelectObject() {
        int index = getFilterTable().getSelectedRow();
        if (index == -1) return null;
        return getFilter().getSelectedItem(index);
    }

    public void addSelectionListener(ListSelectionListener listener) {
        getFilterTable().getSelectionModel().addListSelectionListener(listener);
    }

    public void setFilterContent(ArrayList<T> filterContent) {
        getFilter().setFilterContent(filterContent);
        update();
    }

    class NotEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}