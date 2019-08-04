package domain.filter;

import java.util.HashMap;

public interface Filterable {
    String[] getSearchValues();

    String[] getHeadings();

    HashMap<String, String> getFilterMap();

}
