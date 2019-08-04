package domain.show;

public interface ShowAble {
    String toString();

    default String getInfoString(String header, String description) {
        return header + ": " + description + "\n";
    }
}
