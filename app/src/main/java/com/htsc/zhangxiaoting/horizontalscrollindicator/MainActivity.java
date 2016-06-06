package com.htsc.zhangxiaoting.horizontalscrollindicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mTitles = Arrays.asList("沪深A股", "自选股", "上证A股", "深证A股", "中小板", "创业板");
    private HorizontalScrollIndicator scrollIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        scrollIndicator = (HorizontalScrollIndicator) findViewById(R.id.horizontal_scroll_indicator);
        scrollIndicator.setTabItemTiles(mTitles);
    }
}
