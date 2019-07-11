package domain;

import java.io.*;

class IOStream {
    private String filePath;


    public IOStream(String filePath) {
        this.filePath = filePath;
    }

    @SuppressWarnings("unchecked")
    protected Object readFromFile() throws ClassNotFoundException, IOException {
        InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.filePath));
        ObjectInput input = new ObjectInputStream(bufferedInputStream);

        Object object = input.readObject();
        input.close();
        System.out.println("Objects are read into the system.");
        return object;
    }

    public void writeToFile(Object object) throws IOException {
        OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.filePath));
        ObjectOutput output = new ObjectOutputStream(bufferedOutputStream);

        output.writeObject(object);
        output.close();
        System.out.println("Objects are stored in ser file.");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TheSystem theSystem = TheSystem.getInstance();
        IOStream ioStream = new IOStream("phase1/Data.ser");
//        store in the file
        ioStream.writeToFile(theSystem);
//        read from file
        TheSystem system = (TheSystem) ioStream.readFromFile();
        System.out.println(system.getClass());
        System.out.println(system.accountManager.getClass());
        System.out.println(system.jobPostingManager.getClass());
        System.out.println(system.documentManager.getClass());

    }

}