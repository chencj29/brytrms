package cn.net.metadata.wrapper;

/**
 * 键值对wrapper
 * Created by xiaowu on 3/8/16.
 */
public class KeyValuePairsWapper {
    public String key;
    private String value;

    public KeyValuePairsWapper(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValuePairsWapper() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
