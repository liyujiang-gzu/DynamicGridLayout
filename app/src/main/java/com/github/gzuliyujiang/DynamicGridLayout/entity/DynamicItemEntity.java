package com.github.gzuliyujiang.DynamicGridLayout.entity;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

/**
 * 首页动态布局条目数据实体
 * Created by liyujiang on 2019/12/20
 */
@Keep
public class DynamicItemEntity implements Serializable {
    private boolean enable;
    private String background;
    private int rowCount;
    private int columnCount;
    private List<DynamicIndexEntity> indexes;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public List<DynamicIndexEntity> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<DynamicIndexEntity> indexes) {
        this.indexes = indexes;
    }

}
