package cn.net.metadata.wrapper;

/**
 * Created by xiaowu on 16/5/11.
 */
public class ComboboxWrapper {
    private String id;
    private String value;
    private String text;
    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ComboboxWrapper(String id, String value, String text, boolean selected) {
        this.id = id;
        this.value = value;
        this.text = text;
        this.selected = selected;
    }

    public ComboboxWrapper() {
    }
}
