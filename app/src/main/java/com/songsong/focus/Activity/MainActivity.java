package com.songsong.focus.Activity;

import android.os.Bundle;

import com.songsong.focus.R;

public class MainActivity extends BaseActivity {
    //记得，所有Activity都继承自BaseActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    //初始化布局
    private void initView() {
        //设置标题内容，该方法继承自父类，所以再写一次
        setTitle("首页", 1);
    }

}
