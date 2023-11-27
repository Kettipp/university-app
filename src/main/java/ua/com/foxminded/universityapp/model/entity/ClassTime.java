package ua.com.foxminded.universityapp.model.entity;

public enum ClassTime {
    FIRST ("8.00 - 10.00"),
    SECOND ("10.30 - 12.30"),
    THIRD ("13.00 - 15.00"),
    FOURTH ("15.30 - 17.30");
    private final String time;

    ClassTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
