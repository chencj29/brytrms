package cn.net.metadata.wrapper;

/**
 * Created by xiaowu on 3/29/16.
 */
public class AircraftYAxisWrapper extends GanttAxisWrapper {
    String left;
    String right;
    boolean single;

    public AircraftYAxisWrapper(String value, String text, String available, String left, String right, boolean single) {
        super(value, text, available);
        this.left = left;
        this.right = right;
    }

    public AircraftYAxisWrapper() {
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }
}
