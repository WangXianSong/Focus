package com.songsong.focus.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.songsong.focus.Adapter.InfoListAdapter;
import com.songsong.focus.Bean.Item;
import com.songsong.focus.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    //记得，所有Activity都继承自BaseActivity

    private RecyclerView mRecyclerView;
    private List<Item> mDatas;
    private InfoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initdata();
        initView();
    }

    //初始化布局
    private void initView() {
        //设置标题内容，该方法继承自父类，所以再写一次
        setTitle("首页", 1);
        //配置RecyclerView
        mRecyclerView = findViewById(R.id.infolist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new InfoListAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    //模拟数据
    private void initdata() {
        mDatas = new ArrayList<Item>();
        for (int i = 'A'; i < 'z'; i++) {
            Item item = new Item();
            item.setTitle("" + (char) i);
            item.setImgurl("https://www.baidu.com/img/bd_logo1.png");
            mDatas.add(item);
        }
    }
}
