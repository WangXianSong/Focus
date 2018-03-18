package com.songsong.focus;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

/**
 * Created by a on 2018/3/18.
 */

public class BaseActivity extends AppCompatActivity{
    public Toolbar toolbar;
    /**
     *
     * @param title 标题栏标题
     * @param type  标题类型，1为带菜单栏的标题栏，2为带back键的标题栏
     */
    public void setTitle(String title,int type){

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);//标题字体颜色
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        switch (type){
            case 1:
                toolbar.setNavigationIcon(R.drawable.menu);
                break;
            case 2:
                toolbar.setNavigationIcon(R.drawable.back);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        }


    }
    public void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
