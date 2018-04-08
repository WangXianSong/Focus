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

import java.util.List;


public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.InfoViewHolder> {

    private List<Item> mData;
    private Context mContext;

    //构造函数，用于把要展示的数据源传进来
    public InfoListAdapter(Context mContext, List<Item> data) {
        mData = data;
        this.mContext = mContext;
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


    //A、创建 ViewHolder，通过使用View来绑定条目样式文件
    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //修改后
        if (headView != null && viewType == TYPE_HEADER) return new InfoViewHolder(headView);
        InfoViewHolder holder = new InfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.info_item, parent, false));
        return holder;
    }

    //B、绑定 ViewHolder,此方法内可以对布局中的控件进行操作
    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        //修改后
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);

        holder.title.setText(mData.get(pos).getTitle());
        Glide.with(mContext).load(mData.get(position).getImgurl()).into(holder.img);
    }

    //C、数据的长度
    @Override
    public int getItemCount() {
        //修改后
        return headView == null ? mData.size() : mData.size() + 1;
    }


    /*以下是banner的代码*/

    public static final int TYPE_HEADER = 0;//显示headvuiew
    public static final int TYPE_NORMAL = 1;//显示普通的item
    private View headView;//这家伙就是Banner


    public void setHeadView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);
    }

    public View getHeadView() {
        return headView;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView == null)
            return TYPE_NORMAL;
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headView == null ? position : position - 1;
    }

}
