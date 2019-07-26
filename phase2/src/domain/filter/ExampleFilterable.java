package domain.filter;

import java.util.ArrayList;

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
}
