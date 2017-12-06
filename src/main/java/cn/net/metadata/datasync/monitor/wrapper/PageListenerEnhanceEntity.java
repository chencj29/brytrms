package cn.net.metadata.datasync.monitor.wrapper;

/**
 * 用于SocketIONotiCenter通知Browser时页面定位
 * Created by xiaowu on 16/1/13.
 */
public class PageListenerEnhanceEntity {
    private String random;
    private String tableId;
    private boolean isSlave;
    private String mainTableId;
    private String mainDataId;

    public PageListenerEnhanceEntity(String random, String tableId, boolean isSlave, String mainTableId, String mainDataId) {
        this.random = random;
        this.tableId = tableId;
        this.isSlave = isSlave;
        this.mainTableId = mainTableId;
        this.mainDataId = mainDataId;
    }

    public PageListenerEnhanceEntity() {
    }

    @Override
    public String toString() {
        return "PageListenerEnhanceEntity{" +
                "random='" + random + '\'' +
                ", tableId='" + tableId + '\'' +
                ", isSlave=" + isSlave +
                ", mainTableId='" + mainTableId + '\'' +
                ", mainDataId='" + mainDataId + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageListenerEnhanceEntity)) return false;

        PageListenerEnhanceEntity that = (PageListenerEnhanceEntity) o;

        if (isSlave() != that.isSlave()) return false;
        if (getRandom() != null ? !getRandom().equals(that.getRandom()) : that.getRandom() != null) return false;
        if (getTableId() != null ? !getTableId().equals(that.getTableId()) : that.getTableId() != null) return false;
        if (getMainTableId() != null ? !getMainTableId().equals(that.getMainTableId()) : that.getMainTableId() != null)
            return false;
        return getMainDataId() != null ? getMainDataId().equals(that.getMainDataId()) : that.getMainDataId() == null;

    }

    @Override
    public int hashCode() {
        int result = getRandom() != null ? getRandom().hashCode() : 0;
        result = 31 * result + (getTableId() != null ? getTableId().hashCode() : 0);
        result = 31 * result + (isSlave() ? 1 : 0);
        result = 31 * result + (getMainTableId() != null ? getMainTableId().hashCode() : 0);
        result = 31 * result + (getMainDataId() != null ? getMainDataId().hashCode() : 0);
        return result;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public boolean isSlave() {
        return isSlave;
    }

    public void setSlave(boolean slave) {
        isSlave = slave;
    }

    public String getMainTableId() {
        return mainTableId;
    }

    public void setMainTableId(String mainTableId) {
        this.mainTableId = mainTableId;
    }

    public String getMainDataId() {
        return mainDataId;
    }

    public void setMainDataId(String mainDataId) {
        this.mainDataId = mainDataId;
    }
}

