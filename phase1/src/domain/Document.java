package domain;

import login.SearchObject;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Document implements SearchObject {
    private  boolean CV_or_not;//if document is CV then this will be true
    private  String content;
    public  Document(){System.out.println("this is document");}

    public void update_document(String a){
        this.content = a;
    }

    public  void add_words(String a){
        this.content+=a;
    }

    public String getContent(){return this.content;}
    public  boolean get_CV_or_not(){return CV_or_not;}

    @Override
    public ArrayList<String> getSearchValues() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
