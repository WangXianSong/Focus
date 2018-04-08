package com.songsong.focus.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.songsong.focus.Adapter.InfoListAdapter;
import com.songsong.focus.Bean.Item;
import com.songsong.focus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;


public class MainActivity extends BaseActivity {
    //记得，所有Activity都继承自BaseActivity

    private RecyclerView mRecyclerView;
    private List<Item> mDatas;
    private InfoListAdapter mAdapter;

    private ArrayList<Item> bannerList;//banner控件
    private ArrayList<String> titles, images, ids;//存放banner中的标题、图片、每一项的id


    private int otherdate = 0;//从今日算起，倒数第几天 eg:昨天 就是1 前天就是2
    private RequestQueue mQueue;

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

    //调用数据
    private void initdata() {
        mDatas = new ArrayList<Item>();
//        for (int i = 'A'; i < 'z'; i++) {
//            Item item = new Item();
//            item.setTitle("" + (char) i);
//            item.setImgurl("https://www.baidu.com/img/bd_logo1.png");
//            mDatas.add(item);
//        }
        getInfoFromNet();
    }

    private void getInfoFromNet() {
        // 获取网络数据
        mQueue = Volley.newRequestQueue(this);

        String url = null;
        if (otherdate == 0) {
            //如果是今日就用最后的数据
            url = "http://news-at.zhihu.com/api/4/news/latest";
        } else {
            //否则就是之前的判断流程
            url = "http://news.at.zhihu.com/api/4/news/before/" + getDate();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray list = null;
                    try {
                        //获取返回数据的内容
                        list = response.getJSONArray("stories");  //将stories返回的数据存到list

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //开始解析数据
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);    //将list数据取出来根据i来存放到item
                        JSONArray images = item.getJSONArray("images"); //将每一个item的图片取出来存到数组
                        Item listItem = new Item();
                        //实例一个Item，并设置数据
                        listItem.setTitle(item.getString("title"));//标题
                        listItem.setImgurl(images.getString(0));    //图片
                        listItem.setDate(getDate());                       //日期
                        listItem.setId(item.getString("id"));        //id
                        mDatas.add(listItem);
                    }
                    mAdapter.notifyDataSetChanged();//通知适配器 刷新数据
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //如果遇到异常，在这里通知用户
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("碰到了一点问题");
                //此处的showToast()；是已经在BaseActivity中写好的，可以直接拿来用
            }
        });
        mQueue.add(jsonObjectRequest);//开始任务
    }


    private String getDate() {
        //获取当前需要加载的数据的日期
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -otherdate - 1);//otherdate天前的日子

        String date = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        //将日期转化为 20170520 这样的格式
        return date;
    }


    private void initBanner() {
        //初始化banner
        titles = new ArrayList<>();
        ids = new ArrayList<>();
        images = new ArrayList<>();

        bannerList = new ArrayList<>();
        mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://news-at.zhihu.com/api/4/news/latest", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //解析banner中的数据
                    JSONArray topinfos = response.getJSONArray("top_stories");//将stories返回的数据存到list
                    Log.d("TAG", "onResponse: " + topinfos);
                    for (int i = 0; i < topinfos.length(); i++) {
                        JSONObject item = topinfos.getJSONObject(i); //将list数据取出来根据i来存放到item
                        Item item1 = new Item();    //新建一个Item对象
                        item1.setImgurl(item.getString("image"));
                        item1.setTitle(item.getString("title"));
                        item1.setId(item.getString("id"));
                        bannerList.add(item1);      //添加到数组中
                        titles.add(item1.getTitle());
                        images.add(item1.getImgurl());
                        ids.add(item1.getId());
                    }


                    setHeader(mRecyclerView, images, titles, ids);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("碰到了一点问题");
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    private void setHeader(RecyclerView view, ArrayList<String> images, ArrayList<String> titles, ArrayList<String> ids) {
        View header = LayoutInflater.from(this).inflate(R.layout.headview_banner, view, false);
        //找到banner所在的布局
        BGABanner banner = header.findViewById(R.id.banner);
        //绑定banner
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(MainActivity.this)
                        .load(model)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                //此处可设置banner子项的点击事件

            }
        });
        banner.setData(images, titles);
        mAdapter.setHeadView(header);//向适配器中添加banner
    }


}