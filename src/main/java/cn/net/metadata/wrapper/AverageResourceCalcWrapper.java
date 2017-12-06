package cn.net.metadata.wrapper;

/**
 * 值机柜台、安检口、候机厅平均使用率查询结果包装类
 * Created by xiaowu on 2017/3/22.
 */
public class AverageResourceCalcWrapper {
    public String code;
    public Integer counter;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public AverageResourceCalcWrapper(String code, Integer counter) {
        this.code = code;
        this.counter = counter;
    }

    public AverageResourceCalcWrapper() {
    }
}
