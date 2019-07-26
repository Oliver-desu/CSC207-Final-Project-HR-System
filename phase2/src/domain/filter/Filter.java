package domain.filter;

import java.util.ArrayList;

public class Filter<T> {

    private ArrayList<T> filterContent;
    private ArrayList<T> results = new ArrayList<>();

    public void setFilterContent(ArrayList<T> filterContent) {
        this.filterContent = filterContent;
        filter();
    }

    // Todo: implement actual filter in future.
    private void filter() {
        if (filterContent != null) results = filterContent;
    }

    public T getSelectedItem(int index) {
        return getResults().get(index);
    }

    public ArrayList<T> getResults() {
        return results;
    }

    public String[] getHeadings() {
        if (results.size() != 0 && results.get(0) instanceof Filterable) {
            return ((Filterable) results.get(0)).getHeadings();
        } else return null;
    }
}
