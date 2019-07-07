package interfaces.menu;

import interfaces.Page;
import interfaces.PagesHolder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PagesController {

    private MenuView theView;
    private PagesHolder pagesHolder;

    public PagesController(MenuView theView, PagesHolder pagesHolder) {
        this.theView = theView;
        this.pagesHolder = pagesHolder;
        for (String title : pagesHolder.getPagesMap().keySet()) {
            theView.addPageUpdateListener(title, new PageUpdateListener(title));
        }
    }

    class PageUpdateListener implements ActionListener {
        private String title;

        PageUpdateListener(String title) {
            this.title = title;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Page page = pagesHolder.getPagesMap().get(title);
            theView.updatePage(page);
        }
    }

}
