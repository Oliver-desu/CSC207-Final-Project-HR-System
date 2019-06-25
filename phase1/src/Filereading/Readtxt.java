package Filereading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Readtxt {
    public static void readContext(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String len = null;

            while ((len=br.readLine())!=null){
                System.out.println(len);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub


        readTest2();
    }

    public static void readTest2() {
        File file = new File(Thread.currentThread().getContextClassLoader().getResource("Filereading/CV.txt").getFile());
        Readtxt.readContext(file);



    }}
