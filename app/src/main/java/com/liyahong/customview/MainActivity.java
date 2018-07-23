package com.liyahong.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liyahong.customview.widget.CustomTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomTextView tv_custom;
    private Button btn_intent_drawable, btn_intent_scrath_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        bindEvent();
        initData();
    }

    private void initView() {
        tv_custom = (CustomTextView) findViewById(R.id.tv_custom);
        btn_intent_drawable = (Button) findViewById(R.id.btn_intent_drawable);
        btn_intent_scrath_view = (Button) findViewById(R.id.btn_intent_scrath_view);
    }

    private void bindEvent() {
        tv_custom.setOnTextChangedListener(onTextChangedListener);
        btn_intent_drawable.setOnClickListener(this);
        btn_intent_scrath_view.setOnClickListener(this);
    }

    private void initData() {

    }

    private CustomTextView.OnTextChangedListener onTextChangedListener = new CustomTextView.OnTextChangedListener() {
        @Override
        public void onChanged(String text) {
            showMsg(text, false);
        }
    };

    private void showMsg(String msg, boolean isLong){
        Toast.makeText(this, msg, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_intent_drawable:
                startActivity(new Intent(this, LightingActivity.class));
                break;
            case R.id.btn_intent_scrath_view:
                startActivity(new Intent(this, ScrathViewActivity.class));
                break;
        }
    }
}
