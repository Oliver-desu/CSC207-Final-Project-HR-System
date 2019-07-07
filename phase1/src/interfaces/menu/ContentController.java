package interfaces.menu;

import interfaces.SearchKey;
import interfaces.Viewable;
import interfaces.ViewableHolder;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ContentController {

    private MenuView theView;
    private ViewableHolder viewableHolder;

    public ContentController(MenuView theView, ViewableHolder viewableHolder) {
        this.theView = theView;
        this.viewableHolder = viewableHolder;
        theView.addContentUpdateListener(new ContentUpdateListener());
    }

    class ContentUpdateListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            SearchKey key = theView.getSelectedKey();
            Viewable viewable = viewableHolder.getViewableMap().get(key);
            theView.updateContentPanel(viewable.getView());
        }
    }
}
