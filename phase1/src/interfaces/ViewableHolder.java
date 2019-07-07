package interfaces;

import java.util.HashMap;

// who stores current page list.
public interface ViewableHolder {
    HashMap<SearchKey, Viewable> getViewableMap();
}
