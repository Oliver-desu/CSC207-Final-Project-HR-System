package domain.filter;

import java.util.ArrayList;

public class Filter<T> {

    private ArrayList<T> results;

    public Filter() {

    }

    public void setFilterContent(ArrayList<T> filterContent) {
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
