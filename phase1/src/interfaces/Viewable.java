package interfaces;

import javax.swing.*;

// for all instance in the list that is able to view.
// they should either implement Viewable or be able to create a viewable object.
public interface Viewable {
    JPanel getView();
}
