package com.github.gzuliyujiang.DynamicGridLayout.entity;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * 首页动态布局区域数据实体
 * Created by liyujiang on 2019/12/20
 */
@Keep
public class DynamicAreaEntity implements Serializable {
    private DynamicItemEntity item1;
    private DynamicItemEntity item2;
    private DynamicItemEntity item3;

    public DynamicItemEntity getItem1() {
        return item1;
    }

    public void setItem1(DynamicItemEntity item1) {
        this.item1 = item1;
    }

    public DynamicItemEntity getItem2() {
        return item2;
    }

    public void setItem2(DynamicItemEntity item2) {
        this.item2 = item2;
    }

    public DynamicItemEntity getItem3() {
        return item3;
    }

    public void setItem3(DynamicItemEntity item3) {
        this.item3 = item3;
    }

}
