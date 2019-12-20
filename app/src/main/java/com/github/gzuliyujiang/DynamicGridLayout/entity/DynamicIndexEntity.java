package com.github.gzuliyujiang.DynamicGridLayout.entity;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * 首页动态布局条目索引数据实体
 * Created by liyujiang on 2019/12/20
 */
@Keep
public class DynamicIndexEntity implements Serializable {
    private int startX;
    private int startY;
    private int rowSpan;
    private int columnSpan;
    private String image;
    private String click;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

}
