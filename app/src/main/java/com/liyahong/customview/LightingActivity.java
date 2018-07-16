package com.liyahong.customview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

public class LightingActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private ImageView iv_image;
    private SeekBar sb_hue, sb_saturation, sb_scale;
    private Bitmap bitmap;

    private float hue;          //色调
    private float saturation;  //饱和度
    private float scale;        //亮度
    private static final float MID_VALUE = 100.0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighting);

        initView();
        bindEvent();
        initData();
    }

    private void initView() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        sb_hue = (SeekBar) findViewById(R.id.sb_hue);
        sb_saturation = (SeekBar) findViewById(R.id.sb_saturation);
        sb_scale = (SeekBar) findViewById(R.id.sb_scale);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.linger);
        iv_image.setImageBitmap(bitmap);
    }

    private void bindEvent() {
        sb_hue.setOnSeekBarChangeListener(this);
        sb_saturation.setOnSeekBarChangeListener(this);
        sb_scale.setOnSeekBarChangeListener(this);
    }

    private void initData() {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_hue:
                hue = (progress - MID_VALUE) * 1.0f / MID_VALUE * 100;
                break;
            case R.id.sb_saturation:
                saturation = progress * 1.0f / MID_VALUE;
                break;
            case R.id.sb_scale:
                scale = progress * 1.0f / MID_VALUE;
        }

        iv_image.setImageBitmap(handleImageEfface(bitmap, hue, saturation, scale));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private Bitmap handleImageEfface(Bitmap bitmap, float hue, float saturation, float scale){
        Bitmap btp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(btp);
        Paint paint = new Paint();

        //色调
        ColorMatrix rotateMatrix = new ColorMatrix();
        rotateMatrix.setRotate(0, hue);  //红
        rotateMatrix.setRotate(1, hue);  //绿
        rotateMatrix.setRotate(2, hue);  //蓝

        //饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        //亮度
        ColorMatrix scaleMatrix = new ColorMatrix();
        scaleMatrix.setScale(scale, scale, scale, 1);  //0：代表全黑  1：代表原图

        ColorMatrix imgMatrix = new ColorMatrix();
        imgMatrix.postConcat(rotateMatrix);
        imgMatrix.postConcat(saturationMatrix);
        imgMatrix.postConcat(scaleMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imgMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return btp;
    }
}
