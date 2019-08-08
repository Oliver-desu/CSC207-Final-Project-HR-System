package main;

import gui.general.LoginFrame;
import model.exceptions.CannotSaveSystemException;
import model.exceptions.InvalidInputException;
import model.storage.EmploymentCenter;

import java.io.*;
import java.time.LocalDate;

public class Main {

    private static final String DATA_LOCATION = "\\phase2\\data.ser";
    private static LocalDate currentDate;
    private EmploymentCenter employmentCenter = new EmploymentCenter();
    private LoginFrame login;
    private boolean successfullyLoaded = true;

    public Main() {
        loadSystem();
        login = new LoginFrame(this);
    }

    public static void main(String[] args) {
        new Main();
    }

    public static LocalDate getCurrentDate() {
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        return currentDate;
    }

    public void returnToLogin() {
        login.setVisible(true);
    }

    public static void setDaysElapse(String daysElapse) throws InvalidInputException {
        try {
            int days = Integer.parseInt(daysElapse);
            currentDate = getCurrentDate().plusDays(days);
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
    }

    public EmploymentCenter getEmploymentCenter() {
        return employmentCenter;
    }

    private String getPath() {
        return System.getProperty("user.dir") + DATA_LOCATION;
    }

    public boolean isSuccessfullyLoaded() {
        return successfullyLoaded;
    }

    private void loadSystem() {
        try {
            InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(getPath()));
            ObjectInput input = new ObjectInputStream(bufferedInputStream);
            employmentCenter = (EmploymentCenter) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            successfullyLoaded = false;
        }
    }

    public void saveSystem() throws CannotSaveSystemException {
        try {
            OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(getPath()));
            ObjectOutput output = new ObjectOutputStream(bufferedOutputStream);
            output.writeObject(employmentCenter);
            output.close();
        } catch (IOException e) {
            throw new CannotSaveSystemException();
        }
    }
}
