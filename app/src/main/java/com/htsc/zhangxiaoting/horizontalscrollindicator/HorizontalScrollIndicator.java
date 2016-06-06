package com.htsc.zhangxiaoting.horizontalscrollindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个水平可滚动的indicator
 * Created by zhangxiaoting on 16/6/6.
 */
public class HorizontalScrollIndicator extends HorizontalScrollView {
    private List<String> mTitles = new ArrayList<>();
    private static int VISIBLE_TAB_COUNT = 4;
    private LinearLayout mWrapper;
    private int tabWidth = 0;

    public HorizontalScrollIndicator(Context context) {
        this(context, null);
    }

    public HorizontalScrollIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HorizontalScrollIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Screen width
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 产生单个TextView
     *
     * @param tile TextView的标题
     * @return
     */
    private View generateView(String tile) {
        TextView tabItem = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置当前Button的布局参数
        if (mTitles.size() < (VISIBLE_TAB_COUNT + 1)) {
            // tab数量在4个以内,那么平分屏幕宽度
            lp.width = getScreenWidth() / mTitles.size();
        } else {
            lp.width = getScreenWidth() / VISIBLE_TAB_COUNT;
        }
        tabWidth = lp.width;
        tabItem.setText(tile);
        tabItem.setGravity(Gravity.CENTER);
        tabItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tabItem.setTextColor(0x8CFFFFFF);
        tabItem.setLayoutParams(lp);
        return tabItem;
    }

    /**
     * 根据传入的字串来生成每个button,然后为整个indicator布局设置内容
     *
     * @param tiles 传入的字串
     */
    public void setTabItemTiles(List<String> tiles) {
        mTitles = tiles;
        mWrapper = new LinearLayout(getContext());

        if (tiles != null && tiles.size() > 0) {
            this.removeAllViews();
            for (String tile : tiles) {
                mWrapper.addView(generateView(tile));
            }
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mWrapper.setLayoutParams(lp);
        addView(mWrapper);
        highlightTextView(0);
        setItemClickEvent();
    }

    /**
     * 高亮当前位置的文本和背景色
     *
     * @param position
     */
    private void highlightTextView(int position) {
        TextView mCurrentItem = (TextView) mWrapper.getChildAt(position);
        int[] location = new int[2];
        mCurrentItem.getLocationOnScreen(location);
        if (location[0] < 0) {
            this.smoothScrollBy(location[0], 0);
        }
        if (location[0] + tabWidth > getWidth()) {
            this.smoothScrollBy(location[0] + tabWidth - getWidth(), 0);
        }
        for (int i = 0; i < mWrapper.getChildCount(); i++) {
            View view = mWrapper.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(0x8CFFFFFF);
                view.setBackgroundColor(0xFF16161E);
                if (i == position) {
                    ((TextView) view).setTextColor(0xCFC7240B);
                    view.setBackgroundColor(0xFF1F1F26);
                }
            }
        }
    }

    /**
     * 设置tab的点击事件，点击时viewpager处理相应操作
     */
    private void setItemClickEvent() {
        for (int i = 0; i < mWrapper.getChildCount(); i++) {
            View view = mWrapper.getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    highlightTextView(finalI);
                    Toast.makeText(getContext(), "clicked, position = " + finalI, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
