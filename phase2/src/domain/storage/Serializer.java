package domain.storage;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Serializer {
    private String filePath;
    private static final Logger logger = Logger.getLogger(Serializer.class.getName());
    private InfoCenter infoCenter = new InfoCenter(); //for test

    public Serializer(String filePath) {
        this.filePath = filePath;

//        Initialize logger
        logger.setLevel(Level.FINER);
        try {
            FileHandler fileHandler = new FileHandler("Serializer.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(filePath);
        if (file.exists()) {
            try {
                readFromFile();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setInfoCenter(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
    }

    public InfoCenter getInfoCenter() {
        return infoCenter;
    }

    @SuppressWarnings("unchecked")
    private void readFromFile() throws ClassNotFoundException {
        try {
            InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.filePath));
            ObjectInput input = new ObjectInputStream(bufferedInputStream);

            Object object = input.readObject();
            input.close();
            this.infoCenter = (InfoCenter) object;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Fail to read InfoCenter object from ser file", e);
        }
    }

    public void writeToFile() {
        try {
            OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.filePath));
            ObjectOutput output = new ObjectOutputStream(bufferedOutputStream);

            output.writeObject(this.infoCenter);
            output.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Fail to write InfoCenter object to ser file", e);
        }
    }
}

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        TheSystem theSystem = TheSystem.getInstance();
//        IOStream ioStream = new IOStream("phase1/Data.ser");
////        store in the file
//        ioStream.writeToFile(theSystem);
////        read from file
//        TheSystem system = (TheSystem) ioStream.readFromFile();
//        System.out.println(system.getClass());
//        System.out.println(system.accountManager.getClass());
//        System.out.println(system.jobPostingManager.getClass());
//        System.out.println(system.documentManager.getClass());
//
//        System.out.println(system.accountManager.getApplicants());
//
//    }
//
