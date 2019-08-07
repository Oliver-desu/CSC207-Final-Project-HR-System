package model.storage;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Class {@code Serializer} helps to save {@code EmploymentCenter} object to a file and then read from that file.
 */
public class Serializer {

    /**
     * The ser file path of the file where the object is stored.
     */
    private String filePath;

    /**
     * Logger instance for logging use.
     */
    private static final Logger logger = Logger.getLogger(Serializer.class.getName());

    /**
     * The object which is read from the file.
     */
    private EmploymentCenter EmploymentCenter = new EmploymentCenter();


    /**
     * Create a new Serializer.
     *
     * @param filePath the ser file path
     */
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
        if (file.length() > 0) {
            try {
                readFromFile();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public EmploymentCenter getEmploymentCenter() {
        return EmploymentCenter;
    }

    /**
     * Read {@code EmploymentCenter} object from ser file.
     *
     * @throws ClassNotFoundException if class can not find when reading object
     */
    private void readFromFile() throws ClassNotFoundException {
        try {
            InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.filePath));
            ObjectInput input = new ObjectInputStream(bufferedInputStream);

            Object object = input.readObject();
            input.close();
            this.EmploymentCenter = (EmploymentCenter) object;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Fail to read EmploymentCenter object from ser file", e);
        }
    }

    /**
     * Write the storage object to the file.
     */
    public void writeToFile() {
        try {
            OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.filePath));
            ObjectOutput output = new ObjectOutputStream(bufferedOutputStream);

            output.writeObject(this.EmploymentCenter);
            output.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Fail to write EmploymentCenter object to ser file", e);
        }
    }
}

