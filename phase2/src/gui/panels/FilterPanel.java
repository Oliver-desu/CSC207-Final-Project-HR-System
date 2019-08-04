package gui.panels;

import domain.filter.Filter;
import domain.filter.Filterable;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FilterPanel<T extends Filterable> extends JPanel {

    private static final int TITLE_HEIGHT = 30;
    private static final int SEARCH_BUTTON_WIDTH = 80;
    private static final int SEARCH_BUTTON_HEIGHT = 30;

    private Filter<T> filter = new Filter<>();
    private JTable filterTable = new JTable();
    private DefaultTableModel tableModel = new NotEditableTableModel();

    public FilterPanel(Dimension dimension) {
        setup(dimension, "Unnamed");
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
        Filter<T> filter = getFilter();
        filter.filter();
        String[] headings = filter.getHeadings();
        if (headings == null) return;
        getTableModel().setColumnIdentifiers(headings);
        for (T result : getFilter().getResults()) {
            getTableModel().addRow(filter.getSearchValues(result, headings));
        }
        getFilterTable().updateUI();
        updateUI();
    }

    public void setup(Dimension dimension, String title) {
        setPreferredSize(dimension);
        setLayout(new FlowLayout());
        int width = dimension.width - 10;
        titleSectionSetup(new Dimension(width, TITLE_HEIGHT), title);
        searchSectionSetup(width);
        filterTableSetup(new Dimension(width, dimension.height - TITLE_HEIGHT - SEARCH_BUTTON_HEIGHT - 30));
    }

    private void titleSectionSetup(Dimension dimension, String title) {
        JLabel label = new JLabel(title);
        label.setPreferredSize(dimension);
        add(label);
    }

    private void searchSectionSetup(int width) {
        JTextField textField = new JTextField("XXX; XX; XXX");
        textField.setPreferredSize(new Dimension(width - SEARCH_BUTTON_WIDTH - 10, SEARCH_BUTTON_HEIGHT));

        JButton button = new JButton("Search");
        button.setPreferredSize(new Dimension(SEARCH_BUTTON_WIDTH, SEARCH_BUTTON_HEIGHT));
        button.addActionListener(new SearchListener(textField));

        add(button);
        add(textField);
    }

    private void filterTableSetup(Dimension dimension) {
        JTable filterTable = getFilterTable();
        filterTable.setModel(getTableModel());
        JScrollPane scrollPane = new JScrollPane(filterTable);
        scrollPane.setPreferredSize(dimension);
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

    private class NotEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    private class SearchListener implements ActionListener {

        private JTextField textField;

        private SearchListener(JTextField textField) {
            this.textField = textField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            getFilter().setFilterString(textField.getText());
            update();
        }
    }
}