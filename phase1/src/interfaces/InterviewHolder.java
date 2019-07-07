package interfaces;

import domain.Interview;

public interface InterviewHolder {
    Interview getInterview(SearchKey key);
}
