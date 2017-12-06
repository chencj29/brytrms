package cn.net.metadata.wrapper;

/**
 * Gantt图Y轴包装器
 * Created by xiaowu on 3/21/16.
 */
public class GanttAxisWrapper {
    String value;
    String text;
    String available;


    public GanttAxisWrapper(String value, String text, String available) {
        this.value = value;
        this.text = text;
        this.available = available;
    }

    public GanttAxisWrapper() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

}
