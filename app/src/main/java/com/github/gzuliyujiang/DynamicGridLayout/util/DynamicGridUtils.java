package com.github.gzuliyujiang.DynamicGridLayout.util;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;
import androidx.gridlayout.widget.GridLayout;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThrowableUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.gzuliyujiang.DynamicGridLayout.R;
import com.github.gzuliyujiang.DynamicGridLayout.entity.DynamicAreaEntity;
import com.github.gzuliyujiang.DynamicGridLayout.entity.DynamicIndexEntity;
import com.github.gzuliyujiang.DynamicGridLayout.entity.DynamicItemEntity;
import com.github.gzuliyujiang.DynamicGridLayout.imageloader.ImageLoader;
import com.github.gzuliyujiang.DynamicGridLayout.logger.Logger;

import java.util.List;

/**
 * 动态网格布局辅助类
 * Created by liyujiang on 2019/12/20
 */
public class DynamicGridUtils {
    private static final boolean DEBUG_GRID_LAYOUT = false;

    public static void renderDynamicArea(ViewGroup containerView, DynamicAreaEntity data) {
        DynamicItemEntity item1 = data.getItem1();
        renderItem(containerView, item1, R.id.item_1, R.id.item_1_bg);
        DynamicItemEntity item2 = data.getItem2();
        renderItem(containerView, item2, R.id.item_2, R.id.item_2_bg);
        DynamicItemEntity item3 = data.getItem3();
        renderItem(containerView, item3, R.id.item_3, R.id.item_3_bg);
    }

    private static void renderItem(ViewGroup containerView, DynamicItemEntity data, @IdRes int gridId, @IdRes int bgId) {
        GridLayout gridLayout = containerView.findViewById(gridId);
        ImageView ivBg = containerView.findViewById(bgId);
        if (data == null) {
            gridLayout.setVisibility(View.GONE);
            ivBg.setVisibility(View.GONE);
            return;
        }
        List<DynamicIndexEntity> indexes = data.getIndexes();
        if (!data.isEnable() || indexes.size() == 0) {
            gridLayout.setVisibility(View.GONE);
            ivBg.setVisibility(View.GONE);
            return;
        }
        if (DEBUG_GRID_LAYOUT) {
            gridLayout.setBackgroundColor(0xEE333333);
        }
        //此处不应该用容器的宽度，应该用屏幕宽减去容器边距来适配
        int rootMargin = SizeUtils.dp2px(12);
        int width = ScreenUtils.getScreenWidth() - 2 * rootMargin;
        int rowCount = data.getRowCount();
        int columnCount = data.getColumnCount();
        LogUtils.d("[动态布局]以屏幕宽为基准，容器宽=" + width + "px，容器边距=" + rootMargin + "px，行=" + rowCount + "格，列=" + columnCount + "格");
        float sizeCell = width / (columnCount * 1F);
        int height = (int) (sizeCell * rowCount);
        LogUtils.d("[动态布局]每格=" + sizeCell + "px，宽度=" + width + "px，高度=" + height + "px");
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) gridLayout.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.leftMargin = rootMargin;
        layoutParams.rightMargin = rootMargin;
        layoutParams.topMargin = 0;
        layoutParams.bottomMargin = 0;
        gridLayout.setLayoutParams(layoutParams);
        gridLayout.setRowCount(rowCount);
        gridLayout.setColumnCount(columnCount);
        gridLayout.setVisibility(View.VISIBLE);
        gridLayout.removeAllViews();
        ivBg.setVisibility(View.VISIBLE);
        ImageLoader.display(ivBg, data.getBackground());
        int imageMargin = SizeUtils.dp2px(4);
        for (final DynamicIndexEntity entity : indexes) {
            try {
                renderIndex(gridLayout, sizeCell, imageMargin, entity);
            } catch (Exception e) {
                Logger.debug("[动态布局]startX=" + entity.getStartX() + ", spanX=" + entity.getColumnSpan() + "\n "
                        + "startY=" + entity.getStartY() + ", spanY=" + entity.getRowSpan() + "\n" + ThrowableUtils.getFullStackTrace(e));
            }
        }
    }

    private static void renderIndex(GridLayout layout, float sizeCell, int margin, DynamicIndexEntity entity) {
        ImageView imageView = new ImageView(layout.getContext());
        int randomColor = ColorUtils.getRandomColor();
        if (DEBUG_GRID_LAYOUT) {
            imageView.setBackgroundColor(Color.argb(125, Color.red(randomColor), Color.green(randomColor), Color.blue(randomColor)));
        }
        int rowCount = layout.getRowCount();
        int startY = entity.getStartY();
        int rowSpan = entity.getRowSpan();
        if (startY + rowSpan > rowCount) {
            rowSpan = rowCount - startY;
            LogUtils.d("[动态布局]行列网格已经越出边界" + (entity.getColumnSpan() - rowSpan) + "格，自动校正");
        }
        GridLayout.Spec rowSpec = GridLayout.spec(startY, rowSpan);
        int columnCount = layout.getColumnCount();
        int startX = entity.getStartX();
        int columnSpan = entity.getColumnSpan();
        if (startX + columnSpan > columnCount) {
            columnSpan = columnCount - startX;
            LogUtils.d("[动态布局]列网格已经越出边界" + (entity.getColumnSpan() - columnSpan) + "格，自动校正");
        }
        GridLayout.Spec columnSpec = GridLayout.spec(startX, columnSpan);
        GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        int width = (int) (columnSpan * sizeCell) - 2 * margin;
        int height = (int) (rowSpan * sizeCell) - 2 * margin;
        LogUtils.d("[动态布局]图片边距" + margin + "px，宽度=" + width + "px，高度=" + height + "px");
        gridParams.width = width;
        gridParams.height = height;
        gridParams.setMargins(margin, margin, margin, margin);
        imageView.setLayoutParams(gridParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(v -> {
            ToastUtils.showShort("点击" + entity.getClick());
        });
        if (!DEBUG_GRID_LAYOUT) {
            ImageLoader.display(imageView, entity.getImage());
        }
        layout.addView(imageView);
    }

}
