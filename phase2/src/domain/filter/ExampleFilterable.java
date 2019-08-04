package domain.filter;

import java.util.ArrayList;
import java.util.HashMap;

public class ExampleFilterable implements Filterable {

    private ArrayList<String> searchValues = new ArrayList<>();

    public ExampleFilterable(String example) {
        searchValues.add(example);
        searchValues.add(example);
        searchValues.add(example);
    }

    public ExampleFilterable(ArrayList<String> searchValues) {
        this.searchValues = searchValues;
    }

    @Override
    public String[] getHeadings() {
        return new String[]{"???", "!!!", "***"};
    }

    @Override
    public String[] getSearchValues() {
        String[] strings = new String[searchValues.size()];
        return searchValues.toArray(strings);
    }

    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("heading 1", "1111");
        map.put("heading 2", "2222");
        return map;
    }

}
