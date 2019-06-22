package login;

import javax.swing.*;

public class ExampleAccount implements WindowObject{
    public static void main(String[] args) {
        ExampleAccount account = new ExampleAccount();
        account.getUI().setVisible(true);
    }
    @Override
    public JFrame getUI() {
        JFrame jFrame = InterfaceTool.createInterface("My account", 440, 600);
        jFrame.add(InterfaceTool.createButton("Change password",160, 40, null));
        jFrame.add(InterfaceTool.createButton("Back",160, 40, null));
        jFrame.add(InterfaceTool.createTextLabel("<html>name:<br>id:<br>etc..</html>",400,400));
        jFrame.add(InterfaceTool.createButton("Upload Document:", 150, 40, null));
        jFrame.add(InterfaceTool.createTextField(230,30));
        jFrame.add(InterfaceTool.createButton("Past Applications",160, 40, null));
        jFrame.add(InterfaceTool.createButton("Current Applications",160, 40, null));
        return jFrame;
    }
}
