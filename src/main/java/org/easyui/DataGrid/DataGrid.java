package org.easyui.DataGrid;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by xiaowu on 3/8/16.
 */
public class DataGrid<T> {


    private Integer total;
    private List<T> rows;


    public DataGrid(List<T> datas) {
        if (null == datas) this.rows = Lists.newArrayList();
        else this.rows = datas;
        this.total = datas.size();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
