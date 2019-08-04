package domain.filter;

import java.util.ArrayList;
import java.util.Collection;

public class Filter<T extends Filterable> {

    private ArrayList<T> filterContent;
    private ArrayList<T> results = new ArrayList<>();
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
        if (results.size() != 0) {
            Collection<String> headingCollection = results.get(0).getFilterMap().keySet();
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
