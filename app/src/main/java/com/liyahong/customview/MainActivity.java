package com.liyahong.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.liyahong.customview.widget.CustomTextView;

public class MainActivity extends AppCompatActivity {

    private CustomTextView tv_custom;

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
    }

    private void bindEvent() {
        tv_custom.setOnTextChangedListener(onTextChangedListener);
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
}
