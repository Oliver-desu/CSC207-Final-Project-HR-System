package views.components;

import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;
import views.interfaces.TextFieldHolder;
import views.interfaces.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// 3(4)
public abstract class ComboBoxPanel extends JPanel implements ViewComponent, ButtonHolder, TextFieldHolder, ComboBoxHolder {

    protected Dimension dimension;
    protected int height;

    protected ComboBoxPanel(Dimension dimension) {
        this.dimension = dimension;
        this.height = dimension.height * 3 / 4;
        setup();
    }

    protected abstract void setup();

    @Override
    abstract public void update();

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        return new HashMap<>();
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        return new HashMap<>();
    }

    @Override
    public HashMap<String, JTextField> getTextFields() {
        return new HashMap<>();
    }
}
