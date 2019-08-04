package domain.show;

public interface ShowAble {
    String toString();

    default String getInfoString(String header, String description, boolean singleLine) {
        return header + ": " + (singleLine ? "" : "\n") + description + "\n";
    }
}
