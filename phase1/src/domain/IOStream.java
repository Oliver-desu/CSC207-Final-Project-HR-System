package domain;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class IOStream {
    private String filePath;


    public IOStream(String filePath) {
        this.filePath = filePath;
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> readFromFile() throws ClassNotFoundException, IOException {
        InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.filePath));
        ObjectInput input = new ObjectInputStream(bufferedInputStream);

        Map<String, Object> map = (HashMap<String, Object>) input.readObject();
        input.close();
        System.out.println("Objects are read into the system.");
        return map;
    }

    public void writeToFile(Map<String, Object> map) throws IOException {
        OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.filePath));
        ObjectOutput output = new ObjectOutputStream(bufferedOutputStream);

        output.writeObject(map);
        output.close();
        System.out.println("Objects are stored in ser file.");
    }

}