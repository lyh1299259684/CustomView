package com.liyahong.customview;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liyahong.customview.uitls.DrawableUtils;
import com.liyahong.customview.widget.CustomSketchpad;

import java.util.ArrayList;
import java.util.List;

public class SketchpadActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomSketchpad sketchpad;
    private TextView tv_eraser, tv_reset;
    private RecyclerView rv_sketchpad_color, rv_paint_color, rv_paint_strong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketchpad);

        initView();
        bindEvent();
        initData();
    }

    private void initView() {
        sketchpad = (CustomSketchpad) findViewById(R.id.sketchpad);
        tv_eraser = (TextView) findViewById(R.id.tv_eraser);
        tv_reset = (TextView) findViewById(R.id.tv_reset);
        rv_sketchpad_color = (RecyclerView) findViewById(R.id.rv_sketchpad_color);
        rv_paint_color = (RecyclerView) findViewById(R.id.rv_paint_color);
        rv_paint_strong = (RecyclerView) findViewById(R.id.rv_paint_strong);
    }

    private void bindEvent() {
        tv_eraser.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
    }

    private void initData() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("自定义画板");
        }
        rv_paint_color.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_paint_strong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_sketchpad_color.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private List<String> initColor(){
        List<String> colors = new ArrayList<>();

        colors.add("#FFB6C1");
        colors.add("#FFC0CB");
        colors.add("#DC143C");
        colors.add("#FFF0F5");
        colors.add("#DB7093");
        colors.add("#FF69B4");
        colors.add("#FF1493");
        colors.add("#C71585");
        colors.add("#DA70D6");
        colors.add("#D8BFD8");
        colors.add("#DDA0DD");
        colors.add("#EE82EE");
        colors.add("#FF00FF");
        colors.add("#8B008B");
        colors.add("#800080");
        colors.add("#BA55D3");
        colors.add("#9400D3");
        colors.add("#9932CC");
        colors.add("#4B0082");
        colors.add("#8A2BE2");
        colors.add("#9370DB");
        colors.add("#7B68EE");
        colors.add("#6A5ACD");
        colors.add("#483D8B");
        colors.add("#E6E6FA");
        colors.add("#F8F8FF");
        colors.add("#0000FF");
        colors.add("#0000CD");
        colors.add("#191970");
        colors.add("#00008B");
        colors.add("#000080");
        colors.add("#4169E1");
        colors.add("#6495ED");
        colors.add("#B0C4DE");
        colors.add("#778899");
        colors.add("#708090");
        colors.add("#1E90FF");
        colors.add("#F0F8FF");
        colors.add("#4682B4");
        colors.add("#87CEFA");
        colors.add("#87CEEB");
        colors.add("#00BFFF");
        colors.add("#ADD8E6");
        colors.add("#B0E0E6");
        colors.add("#5F9EA0");
        colors.add("#F0FFFF");
        colors.add("#40E0D0");
        colors.add("#AFEEEE");
        colors.add("#2F4F4F");
        colors.add("#00FFFF");
        colors.add("#008B8B");
        colors.add("#008080");
        colors.add("#48D1CC");
        colors.add("#20B2AA");
        colors.add("#7FFFD4");
        colors.add("#66CDAA");
        colors.add("#00FA9A");
        colors.add("#F5FFFA");
        colors.add("#00FF7F");
        colors.add("#3CB371");
        colors.add("#2E8B57");
        colors.add("#90EE90");
        colors.add("#98FB98");
        colors.add("#F0FFF0");
        colors.add("#8FBC8F");
        colors.add("#32CD32");
        colors.add("#00FF00");
        colors.add("#228B22");
        colors.add("#008000");
        colors.add("#006400");
        colors.add("#7FFF00");
        colors.add("#7CFC00");
        colors.add("#ADFF2F");
        colors.add("#556B2F");
        colors.add("#9ACD32");
        colors.add("#6B8E23");
        colors.add("#F5F5DC");
        colors.add("#FAFAD2");
        colors.add("#FFFFF0");
        colors.add("#FFFFE0");
        colors.add("#FFFF00");
        colors.add("#808000");
        colors.add("#BDB76B");
        colors.add("#FFFACD");
        colors.add("#EEE8AA");
        colors.add("#F0E68C");
        colors.add("#FFD700");
        colors.add("#FFF8DC");
        colors.add("#DAA520");
        colors.add("#B8860B");
        colors.add("#FFFAF0");
        colors.add("#FDF5E6");
        colors.add("#F5DEB3");
        colors.add("#FFE4B5");
        colors.add("#FFA500");
        colors.add("#FFEFD5");
        colors.add("#FFEBCD");
        colors.add("#FFDEAD");
        colors.add("#FAEBD7");
        colors.add("#D2B48C");
        colors.add("#DEB887");
        colors.add("#FFE4C4");
        colors.add("#FF8C00");
        colors.add("#FAF0E6");
        colors.add("#CD853F");
        colors.add("#FFDAB9");
        colors.add("#F4A460");
        colors.add("#D2691E");
        colors.add("#8B4513");
        colors.add("#FFF5EE");
        colors.add("#A0522D");
        colors.add("#FFA07A");
        colors.add("#FF7F50");
        colors.add("#FF4500");
        colors.add("#E9967A");
        colors.add("#FF6347");
        colors.add("#FFE4E1");
        colors.add("#FA8072");
        colors.add("#FFFAFA");
        colors.add("#F08080");
        colors.add("#BC8F8F");
        colors.add("#CD5C5C");
        colors.add("#FF0000");
        colors.add("#A52A2A");
        colors.add("#B22222");
        colors.add("#8B0000");
        colors.add("#FFFFFF");
        colors.add("#F5F5F5");
        colors.add("#DCDCDC");
        colors.add("#D3D3D3");
        colors.add("#C0C0C0");
        colors.add("#A9A9A9");
        colors.add("#808080");
        colors.add("#696969");
        colors.add("#000000");

        return colors;
    }

    private List<Integer> initStrong(){
        List<Integer> strongs = new ArrayList<>();

        strongs.add(3);
        strongs.add(6);
        strongs.add(9);
        strongs.add(12);

        return strongs;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_eraser:  //橡皮擦 or 画板切换
                showMsg("正在开发中...");
                break;
            case R.id.tv_reset:
                sketchpad.clearCanvas();
                break;
        }
    }

    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
