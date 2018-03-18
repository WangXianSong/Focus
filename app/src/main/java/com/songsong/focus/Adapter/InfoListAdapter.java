package com.songsong.focus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.songsong.focus.Bean.Item;
import com.songsong.focus.R;

import java.util.ArrayList;

/**
 * Created by a on 2018/3/18.
 */

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.InfoViewHolder> {

    private ArrayList<Item> mData;
    private Context mContext;


    //构造函数，用于把要展示的数据源传进来
    public InfoListAdapter(ArrayList<Item> data, Context context) {
        mData = data;
        mContext = context;
    }

    //A、创建 ViewHolder，通过使用View来绑定条目样式文件
    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.info_item, parent, false);
        InfoViewHolder holder = new InfoViewHolder(view);
        return holder;
    }

    //B、绑定 ViewHolder,此方法内可以对布局中的控件进行操作
    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(mData.get(position).getImgurl()).into(holder.img);
    }

    //C、数据的长度
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //定义一个 InfoViewHolder
    //view参数，是RecyclerView最外层的子项，可以通过findViewbyId来获取布局中的控件
    public class InfoViewHolder extends RecyclerView.ViewHolder {

        TextView title;//标题
        ImageView img;//显示的图片
        TextView headTitle;//头部标题

        public InfoViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            img = itemView.findViewById(R.id.item_image);
            headTitle = itemView.findViewById(R.id.item_headtitle);
        }
    }
}
