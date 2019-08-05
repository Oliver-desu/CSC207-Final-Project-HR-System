package domain.filter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class {@code Filter} filter through a list of instances(filterable) and calculate the result
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.panels.FilterPanel
 * @see Filterable
 * @since 2019-08-05
 */
public class Filter<T extends Filterable> {

    /**
     * The list of instances(filterable) to filter through
     *
     * @see #getHeadings()
     * @see #setFilterContent(ArrayList)
     * @see #filter()
     */
    private ArrayList<T> filterContent;

    /**
     * The result list of instances(filterable) after calculation
     *
     * @see #getResults()
     * @see #getSelectedItem(int)
     * @see #filter()
     */
    private ArrayList<T> results = new ArrayList<>();

    /**
     * The string provides keywords for filtering
     *
     * @see #getFilterValues()
     * @see #setFilterString(String)
     */
    private String filterString = "";

    public void setFilterContent(ArrayList<T> filterContent) {
        this.filterContent = filterContent;
    }

    private String[] getFilterValues() {
        return filterString.split("[;]");
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    private boolean isValueMatched(String value, String filterValue) {
        return value.toLowerCase().startsWith(filterValue.toLowerCase());
    }

    private boolean isMatched(T filterable) {
        for (String value : filterable.getFilterMap().values()) {
            for (String filterValue : getFilterValues()) {
                if (isValueMatched(value, filterValue)) return true;
            }
        }
        return false;
    }

    public void filter() {
        results.clear();
        if (filterContent == null) return;
        for (T filterable : filterContent) {
            if (isMatched(filterable)) results.add(filterable);
        }
    }

    public T getSelectedItem(int index) {
        return getResults().get(index);
    }

    public ArrayList<T> getResults() {
        return results;
    }

    public String[] getHeadings() {
        if (filterContent.size() != 0) {
            Collection<String> headingCollection = filterContent.get(0).getFilterMap().keySet();
            return new ArrayList<>(headingCollection).toArray(new String[0]);
        } else return null;
    }

    public String[] getSearchValues(T filterable, String[] headings) {
        String[] searchValues = new String[headings.length];
        for (int i = 0; i < headings.length; i++) {
            searchValues[i] = filterable.getFilterMap().get(headings[i]);
        }
        return searchValues;
    }
}
