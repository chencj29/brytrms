package cn.net.metadata.wrapper;

/**
 * Created by xiaowu on 3/31/16.
 */
public class CarouselAxisWrapper extends GanttAxisWrapper {
    String nature;

    public CarouselAxisWrapper(String value, String text, String available, String nature) {
        super(value, text, available);
        this.nature = nature;
    }

    public CarouselAxisWrapper(String nature) {
        this.nature = nature;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}
