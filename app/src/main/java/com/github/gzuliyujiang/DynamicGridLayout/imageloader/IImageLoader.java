package com.github.gzuliyujiang.DynamicGridLayout.imageloader;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.MainThread;

/**
 * 设计思想是使用接口对各模块解耦规范化，不强依赖某些明确的三方库，使得三方库可自由搭配组装。
 * <p>
 * 集成第三方图片加载框架（如：Glide、Picasso、Universal-Image-Loader、Fresco），
 * <p>
 * Glide：http://github.com/bumptech/glide
 * UIL：https://github.com/nostra13/Android-Universal-Image-Loader
 * Picasso：https://github.com/square/picasso
 * Fresco：https://github.com/facebook/fresco
 * <p>
 * Created by liyujiang on 2015/12/9.
 */
public interface IImageLoader {

    /**
     * 图片加载方法
     * <p>
     * （默认图片可以自己每次单独设置，主要满足软件一些地方可能默认图片不一样的情况）
     *
     * @param imageView   图片视图
     * @param urlOrRes    泛型，图片地址，可以是string、assets、res
     * @param placeholder 默认占位图
     */
    @MainThread
    <T> void display(ImageView imageView, T urlOrRes, int placeholder);

    void clearCache(Context context);

    long getCacheSize(Context context);

}
