package com.liyahong.customview;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liyahong.customview.uitls.DrawableUtils;
import com.liyahong.customview.widget.CustomSketchpad;

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
