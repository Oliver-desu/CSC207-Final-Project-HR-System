package views.interfaces;

import javax.swing.*;
import java.util.HashMap;

public interface ComboBoxHolder {
    HashMap<String, JComboBox<String>> getBoxes();
}
