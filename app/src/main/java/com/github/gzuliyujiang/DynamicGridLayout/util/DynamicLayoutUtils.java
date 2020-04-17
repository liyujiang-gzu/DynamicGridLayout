package com.github.gzuliyujiang.DynamicGridLayout.util;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;

import com.blankj.utilcode.util.ColorUtils;
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
public class DynamicLayoutUtils {
    private static final boolean DEBUG_GRID_LAYOUT = false;

    public static void renderDynamicArea(ViewGroup containerView, DynamicAreaEntity data) {
        DynamicItemEntity item1 = data.getItem1();
        renderItem(containerView, item1, R.id.item_1, R.id.item_1_bg);
        DynamicItemEntity item2 = data.getItem2();
        renderItem(containerView, item2, R.id.item_2, R.id.item_2_bg);
        DynamicItemEntity item3 = data.getItem3();
        renderItem(containerView, item3, R.id.item_3, R.id.item_3_bg);
    }

    static void renderItem(ViewGroup containerView,DynamicItemEntity data, @IdRes int containerId, @IdRes int bgId) {
        FrameLayout frameLayout = containerView.findViewById(containerId);
        frameLayout.removeAllViews();
        ImageView ivBg = containerView.findViewById(bgId);
        if (data == null) {
            frameLayout.setVisibility(View.GONE);
            ivBg.setVisibility(View.GONE);
            return;
        }
        List<DynamicIndexEntity> indexes = data.getIndexes();
        if (!data.isEnable() || indexes.size() == 0) {
            frameLayout.setVisibility(View.GONE);
            ivBg.setVisibility(View.GONE);
            return;
        }
        if (DEBUG_GRID_LAYOUT) {
            frameLayout.setBackgroundColor(0xEE333333);
        }
        //此处不应该用容器的宽度，应该用屏幕宽减去容器边距来适配
        int rootPadding = SizeUtils.dp2px(8);
        int width = ScreenUtils.getScreenWidth() - rootPadding;
        int rowCount = data.getRowCount();
        int columnCount = data.getColumnCount();
        Logger.debug("[动态布局]以屏幕宽为基准，容器宽=" + width + "px，容器边距=" + rootPadding + "px，行=" + rowCount + "格，列=" + columnCount + "格");
        float sizeCell = width / (columnCount * 1F);
        int height = (int) (sizeCell * rowCount);
        Logger.debug("[动态布局]每格=" + sizeCell + "px，宽度=" + width + "px，高度=" + height + "px");
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        frameLayout.setPadding(rootPadding, 0, rootPadding, 0);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setVisibility(View.VISIBLE);
        ivBg.setVisibility(View.VISIBLE);
        ImageLoader.display(ivBg, data.getBackground());
        int imageMargin = SizeUtils.dp2px(4);
        for (final DynamicIndexEntity entity : indexes) {
            try {
                renderIndex(frameLayout, rowCount, columnCount, sizeCell, imageMargin, entity);
            } catch (Exception e) {
                Logger.debug("[动态布局]startX=" + entity.getStartX() + ", spanX=" + entity.getColumnSpan() + "\n "
                        + "startY=" + entity.getStartY() + ", spanY=" + entity.getRowSpan() + "\n" + ThrowableUtils.getFullStackTrace(e));
            }
        }
    }

    private static void renderIndex(FrameLayout layout, int rowCount, int columnCount, float sizeCell, int margin, DynamicIndexEntity entity) {
        ImageView imageView = new ImageView(layout.getContext());
        int randomColor = ColorUtils.getRandomColor();
        if (DEBUG_GRID_LAYOUT) {
            imageView.setBackgroundColor(Color.argb(125, Color.red(randomColor), Color.green(randomColor), Color.blue(randomColor)));
        }
        int startY = entity.getStartY();
        int rowSpan = entity.getRowSpan();
        if (startY + rowSpan > rowCount) {
            rowSpan = rowCount - startY;
            Logger.debug("[动态布局]行列网格已经越出边界" + (entity.getColumnSpan() - rowSpan) + "格，自动校正");
        }
        int startX = entity.getStartX();
        int columnSpan = entity.getColumnSpan();
        if (startX + columnSpan > columnCount) {
            columnSpan = columnCount - startX;
            Logger.debug("[动态布局]列网格已经越出边界" + (entity.getRowSpan() - columnSpan) + "格，自动校正");
        }
        int width = (int) ((columnSpan * sizeCell) - 2 * margin);
        int height = (int) ((rowSpan * sizeCell) - 2 * margin);
        Logger.debug("[动态布局]图片边距" + margin + "px，宽度=" + width + "px，高度=" + height + "px");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        int left = (int) (startX * sizeCell);
        int top = (int) (startY * sizeCell);
        layoutParams.setMargins(margin + left, margin + top, margin, margin);
        imageView.setLayoutParams(layoutParams);
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
