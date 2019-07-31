package domain.storage;

import domain.applying.Application;
import domain.applying.Interview;
import domain.job.JobPosting;

import java.util.Arrays;
import java.util.HashMap;

public class Info {

    private static final String[] INTERVIEW_INFO_LIST = new String[]{
            "Time:", "Duration(min):", "Location:", "Recommendation:", "Dead line:"
    };

    private static final String[] APPLICATION_INFO_LIST = new String[]{
            // Todo
    };

    private static final String[] JOB_INFO_LIST = new String[]{
            "Job id:", "Company id:", "Position name:", "Num of positions:", "Post date:", "Close date:",
            "CV:", "Cover letter:", "Reference:", "Extra document:"
    };

    private static final String ERROR_MESSAGE = "Error message at class Info: ";

    private String[] infoList = new String[0];
    private HashMap<String, String> infoMap = new HashMap<>();

    public Info(InfoHolder infoHolder, HashMap<String, String> infoMap) {
        infoHolder.setInfo(this);
        initInfoList(infoHolder);
        setChanges(infoMap);
    }

    private void initInfoList(InfoHolder infoHolder) {
        if (infoHolder instanceof Interview) setInfoList(INTERVIEW_INFO_LIST);
        else if (infoHolder instanceof Application) setInfoList(APPLICATION_INFO_LIST);
        else if (infoHolder instanceof JobPosting) setInfoList(JOB_INFO_LIST);
    }

    public void setInfoList(String[] infoList) {
        if (this.infoList.length == 0) this.infoList = infoList;
        else throwErrorMessage("Cannot set InfoList twice!");
    }

    private boolean isValidKey(String key) {
        return Arrays.asList(infoList).contains(key);
    }

    public void setChanges(HashMap<String, String> infoMap) {
        for (String key : infoMap.keySet()) {
            if (isValidKey(key)) this.infoMap.put(key, infoMap.get(key));
        }
    }

    public String getSpecificInfo(String key) {
        if (isValidKey(key)) return infoMap.getOrDefault(key, "");
        else {
            throwErrorMessage("Invalid key for get specific info!");
            return "";
        }
    }

    public String[] getAllInfoHeadings(InfoHolder infoHolder) {
        if (infoHolder instanceof Interview) return INTERVIEW_INFO_LIST;
        else if (infoHolder instanceof Application) return APPLICATION_INFO_LIST;
        else if (infoHolder instanceof JobPosting) return JOB_INFO_LIST;
        return null;
    }

    private void throwErrorMessage(String message) {
        System.out.println(ERROR_MESSAGE + message);
    }
}