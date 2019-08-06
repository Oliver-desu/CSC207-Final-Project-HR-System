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

/**
 * Class {@code FilterPanel} setup gui panel for showing a list of instances(filterable)
 * and be able to search and filter through instances
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.major.Scenario
 * @since 2019-08-05
 */
public class FilterPanel<T extends Filterable> extends JPanel {

    // Settings related to components sizes
    private static final int TITLE_HEIGHT = 30;
    private static final int SEARCH_BUTTON_WIDTH = 80;
    private static final int SEARCH_BUTTON_HEIGHT = 30;

    /**
     * The filter that calculate remaining instance
     *
     * @see Filter
     * @see SearchListener
     * @see #getFilter()
     * @see #update()
     * @see #setFilterContent(ArrayList)
     */
    private Filter<T> filter = new Filter<>();

    /**
     * The filter table that shows results to users
     *
     * @see #getFilterTable()
     * @see #filterTableSetup(Dimension)
     * @see #getSelectObject()
     * @see #addSelectionListener(ListSelectionListener)
     */
    private JTable filterTable = new JTable();

    /**
     * The table model that updates result in filter table
     *
     * @see NotEditableTableModel
     * @see #update()
     * @see #filterTableSetup(Dimension)
     */
    private DefaultTableModel tableModel = new NotEditableTableModel();

    /**
     * Create a filter with given dimension and title.
     * @param dimension
     * @param title
     */
    public FilterPanel(Dimension dimension, String title) {
        setup(dimension, title);
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

    /**
     *TODO
     */
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
        updateUI();
    }

    /**
     * Set up a new filter with given dimension and title of what the filter is for.
     * @param dimension
     * @param title
     */
    public void setup(Dimension dimension, String title) {
        setPreferredSize(dimension);
        setLayout(new FlowLayout());
        int width = dimension.width - 10;
        titleSectionSetup(new Dimension(width, TITLE_HEIGHT), title);
        searchSectionSetup(width);
        int tableHeight = dimension.height - TITLE_HEIGHT - SEARCH_BUTTON_HEIGHT - 30;
        filterTableSetup(new Dimension(width, tableHeight));
    }


    /**
     * Set up the title for the filter.
     * @param dimension
     * @param title
     */
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