package bku.iot.demoiot;

public class DiaryEntry {
    private String time;
    private String action;

    public DiaryEntry(String time, String action) {
        this.time = time;
        this.action = action;
    }

    public String getTime() {
        return time;
    }

    public String getAction() {
        return action;
    }
}
