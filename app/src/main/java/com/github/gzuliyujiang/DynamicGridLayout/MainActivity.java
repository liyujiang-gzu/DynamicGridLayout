package com.github.gzuliyujiang.DynamicGridLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.gzuliyujiang.DynamicGridLayout.entity.DynamicAreaEntity;
import com.github.gzuliyujiang.DynamicGridLayout.util.AssetUtils;
import com.github.gzuliyujiang.DynamicGridLayout.util.DynamicLayoutUtils;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private ViewGroup containerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerView = findViewById(R.id.dynamic_area);
        fetchDataAndThenRender();
    }

    private void fetchDataAndThenRender() {
        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<String>() {
            @Override
            public String doInBackground() throws Throwable {
                //示例数据为 120x80 的网格
                return AssetUtils.readText(getApplication(), "data.json");
            }

            @Override
            public void onSuccess(String result) {
                DynamicAreaEntity data = new Gson().fromJson(result, DynamicAreaEntity.class);
                DynamicLayoutUtils.renderDynamicArea(containerView, data);
            }

            @Override
            public void onFail(Throwable t) {
                super.onFail(t);
                ToastUtils.showShort(t.getMessage());
            }

            @Override
            protected void onDone() {
                super.onDone();
            }
        });
    }

}
