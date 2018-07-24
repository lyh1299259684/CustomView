package com.liyahong.customview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liyahong.customview.adapter.PaintColorAndSketchpadColorAdapter;
import com.liyahong.customview.adapter.PaintStrongAdapter;
import com.liyahong.customview.bean.ColorBean;
import com.liyahong.customview.bean.StrongBean;
import com.liyahong.customview.interfaces.OnItemClickListener;
import com.liyahong.customview.uitls.SDCardPathUtils;
import com.liyahong.customview.widget.CustomSketchpad;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SketchpadActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomSketchpad sketchpad;
    private TextView tv_eraser, tv_reset, tv_save;
    private RecyclerView rv_sketchpad_color, rv_paint_color, rv_paint_strong;

    private List<ColorBean> colorList;
    private List<ColorBean> sketchpadColorList;
    private List<StrongBean> strongList;
    private PaintColorAndSketchpadColorAdapter paintColorAdapter, sketchpadColorAdapter;
    private PaintStrongAdapter strongAdapter;

    private static final int SDK_PERMISSION_REQUEST = 0x110;

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
        tv_save = (TextView) findViewById(R.id.tv_save);
        rv_sketchpad_color = (RecyclerView) findViewById(R.id.rv_sketchpad_color);
        rv_paint_color = (RecyclerView) findViewById(R.id.rv_paint_color);
        rv_paint_strong = (RecyclerView) findViewById(R.id.rv_paint_strong);
    }

    private void bindEvent() {
        tv_eraser.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    private void initData() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("自定义画板");
        }
        rv_paint_color.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_paint_strong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_sketchpad_color.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        initPaintColor();
        initSketchpadColor();
        initStrong();

        //反转集合
        Collections.reverse(sketchpadColorList);

        //初始化数据
        sketchpad.setBackgroundColor(Color.parseColor(sketchpadColorList.get(0).getColor()));
        sketchpad.setPaintColor(colorList.get(0).getColor());
        sketchpad.setPaintStrong(strongList.get(0).getStrong());

        refreshPaintColorAdapter(colorList);
        refreshSketchpadColorAdapter(sketchpadColorList);
        refreshPaintStrongAdapter(strongList);
    }

    /**
     * 画笔颜色适配器
     * @param datas
     */
    private void refreshPaintColorAdapter(List<ColorBean> datas){
        if (paintColorAdapter == null) {
            paintColorAdapter = new PaintColorAndSketchpadColorAdapter(datas, this, 1);
            paintColorAdapter.setOnItemClickListener(mOnItemClick);
            rv_paint_color.setAdapter(paintColorAdapter);
        } else {
            paintColorAdapter.refreshAdapter(datas);
        }
    }

    /**
     * 画板颜色适配器
     * @param datas
     */
    private void refreshSketchpadColorAdapter(List<ColorBean> datas){
        if (sketchpadColorAdapter == null) {
            sketchpadColorAdapter = new PaintColorAndSketchpadColorAdapter(datas, this, 2);
            sketchpadColorAdapter.setOnItemClickListener(mOnItemClick);
            rv_sketchpad_color.setAdapter(sketchpadColorAdapter);
        } else {
            sketchpadColorAdapter.refreshAdapter(datas);
        }
    }

    /**
     * 字体粗细适配器
     * @param datas
     */
    private void refreshPaintStrongAdapter(List<StrongBean> datas){
        if (strongAdapter == null) {
            strongAdapter = new PaintStrongAdapter(datas, this);
            strongAdapter.setOnItemClickListener(mOnItemClick);
            rv_paint_strong.setAdapter(strongAdapter);
        } else {
            strongAdapter.refreshAdapter(datas);
        }
    }

    /**
     * 子条目点击
     */
    private OnItemClickListener mOnItemClick = new OnItemClickListener() {
        @Override
        public void OnItemClick(int position, int action) {
            switch (action) {
                case 1:     //画笔颜色
                    ColorBean colorBean = colorList.get(position);
                    for (int i = 0; i < colorList.size(); i++) {
                        if (position == i) {
                            colorList.get(i).setSelected(true);
                        } else {
                            colorList.get(i).setSelected(false);
                        }
                    }
                    refreshPaintColorAdapter(colorList);
                    sketchpad.setPaintColor(colorBean.getColor());
                    break;
                case 2:     //画板颜色
                    ColorBean sketchpadColor = sketchpadColorList.get(position);
                    for (int i = 0; i < sketchpadColorList.size(); i++) {
                        if (position == i) {
                            sketchpadColorList.get(i).setSelected(true);
                        } else {
                            sketchpadColorList.get(i).setSelected(false);
                        }
                    }
                    refreshSketchpadColorAdapter(sketchpadColorList);
                    sketchpad.setBackgroundColor(Color.parseColor(sketchpadColor.getColor()));
                    break;
                case 3:     //画笔粗细
                    StrongBean strongBean = strongList.get(position);
                    for (int i = 0; i < strongList.size(); i++) {
                        if (position == i) {
                            strongList.get(i).setSelected(true);
                        } else {
                            strongList.get(i).setSelected(false);
                        }
                    }
                    refreshPaintStrongAdapter(strongList);
                    sketchpad.setPaintStrong(strongBean.getStrong());
                    break;
            }
        }
    };

    /**
     * 初始化画笔颜色
     */
    private void initPaintColor(){
        colorList = new ArrayList<>();
        colorList.add(new ColorBean("#FFB6C1").setSelected(true));
        colorList.add(new ColorBean("#FFC0CB"));
        colorList.add(new ColorBean("#DC143C"));
        colorList.add(new ColorBean("#FFF0F5"));
        colorList.add(new ColorBean("#DB7093"));
        colorList.add(new ColorBean("#FF69B4"));
        colorList.add(new ColorBean("#FF1493"));
        colorList.add(new ColorBean("#C71585"));
        colorList.add(new ColorBean("#DA70D6"));
        colorList.add(new ColorBean("#D8BFD8"));
        colorList.add(new ColorBean("#DDA0DD"));
        colorList.add(new ColorBean("#EE82EE"));
        colorList.add(new ColorBean("#FF00FF"));
        colorList.add(new ColorBean("#8B008B"));
        colorList.add(new ColorBean("#800080"));
        colorList.add(new ColorBean("#BA55D3"));
        colorList.add(new ColorBean("#9400D3"));
        colorList.add(new ColorBean("#9932CC"));
        colorList.add(new ColorBean("#4B0082"));
        colorList.add(new ColorBean("#8A2BE2"));
        colorList.add(new ColorBean("#9370DB"));
        colorList.add(new ColorBean("#7B68EE"));
        colorList.add(new ColorBean("#6A5ACD"));
        colorList.add(new ColorBean("#483D8B"));
        colorList.add(new ColorBean("#E6E6FA"));
        colorList.add(new ColorBean("#F8F8FF"));
        colorList.add(new ColorBean("#0000FF"));
        colorList.add(new ColorBean("#0000CD"));
        colorList.add(new ColorBean("#191970"));
        colorList.add(new ColorBean("#00008B"));
        colorList.add(new ColorBean("#000080"));
        colorList.add(new ColorBean("#4169E1"));
        colorList.add(new ColorBean("#6495ED"));
        colorList.add(new ColorBean("#B0C4DE"));
        colorList.add(new ColorBean("#778899"));
        colorList.add(new ColorBean("#708090"));
        colorList.add(new ColorBean("#1E90FF"));
        colorList.add(new ColorBean("#F0F8FF"));
        colorList.add(new ColorBean("#4682B4"));
        colorList.add(new ColorBean("#87CEFA"));
        colorList.add(new ColorBean("#87CEEB"));
        colorList.add(new ColorBean("#00BFFF"));
        colorList.add(new ColorBean("#ADD8E6"));
        colorList.add(new ColorBean("#B0E0E6"));
        colorList.add(new ColorBean("#5F9EA0"));
        colorList.add(new ColorBean("#F0FFFF"));
        colorList.add(new ColorBean("#40E0D0"));
        colorList.add(new ColorBean("#AFEEEE"));
        colorList.add(new ColorBean("#2F4F4F"));
        colorList.add(new ColorBean("#00FFFF"));
        colorList.add(new ColorBean("#008B8B"));
        colorList.add(new ColorBean("#008080"));
        colorList.add(new ColorBean("#48D1CC"));
        colorList.add(new ColorBean("#20B2AA"));
        colorList.add(new ColorBean("#7FFFD4"));
        colorList.add(new ColorBean("#66CDAA"));
        colorList.add(new ColorBean("#00FA9A"));
        colorList.add(new ColorBean("#F5FFFA"));
        colorList.add(new ColorBean("#00FF7F"));
        colorList.add(new ColorBean("#3CB371"));
        colorList.add(new ColorBean("#2E8B57"));
        colorList.add(new ColorBean("#90EE90"));
        colorList.add(new ColorBean("#98FB98"));
        colorList.add(new ColorBean("#F0FFF0"));
        colorList.add(new ColorBean("#8FBC8F"));
        colorList.add(new ColorBean("#32CD32"));
        colorList.add(new ColorBean("#00FF00"));
        colorList.add(new ColorBean("#228B22"));
        colorList.add(new ColorBean("#008000"));
        colorList.add(new ColorBean("#006400"));
        colorList.add(new ColorBean("#7FFF00"));
        colorList.add(new ColorBean("#7CFC00"));
        colorList.add(new ColorBean("#ADFF2F"));
        colorList.add(new ColorBean("#556B2F"));
        colorList.add(new ColorBean("#9ACD32"));
        colorList.add(new ColorBean("#6B8E23"));
        colorList.add(new ColorBean("#F5F5DC"));
        colorList.add(new ColorBean("#FAFAD2"));
        colorList.add(new ColorBean("#FFFFF0"));
        colorList.add(new ColorBean("#FFFFE0"));
        colorList.add(new ColorBean("#FFFF00"));
        colorList.add(new ColorBean("#808000"));
        colorList.add(new ColorBean("#BDB76B"));
        colorList.add(new ColorBean("#FFFACD"));
        colorList.add(new ColorBean("#EEE8AA"));
        colorList.add(new ColorBean("#F0E68C"));
        colorList.add(new ColorBean("#FFD700"));
        colorList.add(new ColorBean("#FFF8DC"));
        colorList.add(new ColorBean("#DAA520"));
        colorList.add(new ColorBean("#B8860B"));
        colorList.add(new ColorBean("#FFFAF0"));
        colorList.add(new ColorBean("#FDF5E6"));
        colorList.add(new ColorBean("#F5DEB3"));
        colorList.add(new ColorBean("#FFE4B5"));
        colorList.add(new ColorBean("#FFA500"));
        colorList.add(new ColorBean("#FFEFD5"));
        colorList.add(new ColorBean("#FFEBCD"));
        colorList.add(new ColorBean("#FFDEAD"));
        colorList.add(new ColorBean("#FAEBD7"));
        colorList.add(new ColorBean("#D2B48C"));
        colorList.add(new ColorBean("#DEB887"));
        colorList.add(new ColorBean("#FFE4C4"));
        colorList.add(new ColorBean("#FF8C00"));
        colorList.add(new ColorBean("#FAF0E6"));
        colorList.add(new ColorBean("#CD853F"));
        colorList.add(new ColorBean("#FFDAB9"));
        colorList.add(new ColorBean("#F4A460"));
        colorList.add(new ColorBean("#D2691E"));
        colorList.add(new ColorBean("#8B4513"));
        colorList.add(new ColorBean("#FFF5EE"));
        colorList.add(new ColorBean("#A0522D"));
        colorList.add(new ColorBean("#FFA07A"));
        colorList.add(new ColorBean("#FF7F50"));
        colorList.add(new ColorBean("#FF4500"));
        colorList.add(new ColorBean("#E9967A"));
        colorList.add(new ColorBean("#FF6347"));
        colorList.add(new ColorBean("#FFE4E1"));
        colorList.add(new ColorBean("#FA8072"));
        colorList.add(new ColorBean("#FFFAFA"));
        colorList.add(new ColorBean("#F08080"));
        colorList.add(new ColorBean("#BC8F8F"));
        colorList.add(new ColorBean("#CD5C5C"));
        colorList.add(new ColorBean("#FF0000"));
        colorList.add(new ColorBean("#A52A2A"));
        colorList.add(new ColorBean("#B22222"));
        colorList.add(new ColorBean("#8B0000"));
        colorList.add(new ColorBean("#FFFFFF"));
        colorList.add(new ColorBean("#F5F5F5"));
        colorList.add(new ColorBean("#DCDCDC"));
        colorList.add(new ColorBean("#D3D3D3"));
        colorList.add(new ColorBean("#C0C0C0"));
        colorList.add(new ColorBean("#A9A9A9"));
        colorList.add(new ColorBean("#808080"));
        colorList.add(new ColorBean("#696969"));
        colorList.add(new ColorBean("#000000"));
    }

    /**
     * 初始化画板颜色
     */
    private void initSketchpadColor(){
        sketchpadColorList = new ArrayList<>();

        sketchpadColorList.add(new ColorBean("#FFB6C1"));
        sketchpadColorList.add(new ColorBean("#FFC0CB"));
        sketchpadColorList.add(new ColorBean("#DC143C"));
        sketchpadColorList.add(new ColorBean("#FFF0F5"));
        sketchpadColorList.add(new ColorBean("#DB7093"));
        sketchpadColorList.add(new ColorBean("#FF69B4"));
        sketchpadColorList.add(new ColorBean("#FF1493"));
        sketchpadColorList.add(new ColorBean("#C71585"));
        sketchpadColorList.add(new ColorBean("#DA70D6"));
        sketchpadColorList.add(new ColorBean("#D8BFD8"));
        sketchpadColorList.add(new ColorBean("#DDA0DD"));
        sketchpadColorList.add(new ColorBean("#EE82EE"));
        sketchpadColorList.add(new ColorBean("#FF00FF"));
        sketchpadColorList.add(new ColorBean("#8B008B"));
        sketchpadColorList.add(new ColorBean("#800080"));
        sketchpadColorList.add(new ColorBean("#BA55D3"));
        sketchpadColorList.add(new ColorBean("#9400D3"));
        sketchpadColorList.add(new ColorBean("#9932CC"));
        sketchpadColorList.add(new ColorBean("#4B0082"));
        sketchpadColorList.add(new ColorBean("#8A2BE2"));
        sketchpadColorList.add(new ColorBean("#9370DB"));
        sketchpadColorList.add(new ColorBean("#7B68EE"));
        sketchpadColorList.add(new ColorBean("#6A5ACD"));
        sketchpadColorList.add(new ColorBean("#483D8B"));
        sketchpadColorList.add(new ColorBean("#E6E6FA"));
        sketchpadColorList.add(new ColorBean("#F8F8FF"));
        sketchpadColorList.add(new ColorBean("#0000FF"));
        sketchpadColorList.add(new ColorBean("#0000CD"));
        sketchpadColorList.add(new ColorBean("#191970"));
        sketchpadColorList.add(new ColorBean("#00008B"));
        sketchpadColorList.add(new ColorBean("#000080"));
        sketchpadColorList.add(new ColorBean("#4169E1"));
        sketchpadColorList.add(new ColorBean("#6495ED"));
        sketchpadColorList.add(new ColorBean("#B0C4DE"));
        sketchpadColorList.add(new ColorBean("#778899"));
        sketchpadColorList.add(new ColorBean("#708090"));
        sketchpadColorList.add(new ColorBean("#1E90FF"));
        sketchpadColorList.add(new ColorBean("#F0F8FF"));
        sketchpadColorList.add(new ColorBean("#4682B4"));
        sketchpadColorList.add(new ColorBean("#87CEFA"));
        sketchpadColorList.add(new ColorBean("#87CEEB"));
        sketchpadColorList.add(new ColorBean("#00BFFF"));
        sketchpadColorList.add(new ColorBean("#ADD8E6"));
        sketchpadColorList.add(new ColorBean("#B0E0E6"));
        sketchpadColorList.add(new ColorBean("#5F9EA0"));
        sketchpadColorList.add(new ColorBean("#F0FFFF"));
        sketchpadColorList.add(new ColorBean("#40E0D0"));
        sketchpadColorList.add(new ColorBean("#AFEEEE"));
        sketchpadColorList.add(new ColorBean("#2F4F4F"));
        sketchpadColorList.add(new ColorBean("#00FFFF"));
        sketchpadColorList.add(new ColorBean("#008B8B"));
        sketchpadColorList.add(new ColorBean("#008080"));
        sketchpadColorList.add(new ColorBean("#48D1CC"));
        sketchpadColorList.add(new ColorBean("#20B2AA"));
        sketchpadColorList.add(new ColorBean("#7FFFD4"));
        sketchpadColorList.add(new ColorBean("#66CDAA"));
        sketchpadColorList.add(new ColorBean("#00FA9A"));
        sketchpadColorList.add(new ColorBean("#F5FFFA"));
        sketchpadColorList.add(new ColorBean("#00FF7F"));
        sketchpadColorList.add(new ColorBean("#3CB371"));
        sketchpadColorList.add(new ColorBean("#2E8B57"));
        sketchpadColorList.add(new ColorBean("#90EE90"));
        sketchpadColorList.add(new ColorBean("#98FB98"));
        sketchpadColorList.add(new ColorBean("#F0FFF0"));
        sketchpadColorList.add(new ColorBean("#8FBC8F"));
        sketchpadColorList.add(new ColorBean("#32CD32"));
        sketchpadColorList.add(new ColorBean("#00FF00"));
        sketchpadColorList.add(new ColorBean("#228B22"));
        sketchpadColorList.add(new ColorBean("#008000"));
        sketchpadColorList.add(new ColorBean("#006400"));
        sketchpadColorList.add(new ColorBean("#7FFF00"));
        sketchpadColorList.add(new ColorBean("#7CFC00"));
        sketchpadColorList.add(new ColorBean("#ADFF2F"));
        sketchpadColorList.add(new ColorBean("#556B2F"));
        sketchpadColorList.add(new ColorBean("#9ACD32"));
        sketchpadColorList.add(new ColorBean("#6B8E23"));
        sketchpadColorList.add(new ColorBean("#F5F5DC"));
        sketchpadColorList.add(new ColorBean("#FAFAD2"));
        sketchpadColorList.add(new ColorBean("#FFFFF0"));
        sketchpadColorList.add(new ColorBean("#FFFFE0"));
        sketchpadColorList.add(new ColorBean("#FFFF00"));
        sketchpadColorList.add(new ColorBean("#808000"));
        sketchpadColorList.add(new ColorBean("#BDB76B"));
        sketchpadColorList.add(new ColorBean("#FFFACD"));
        sketchpadColorList.add(new ColorBean("#EEE8AA"));
        sketchpadColorList.add(new ColorBean("#F0E68C"));
        sketchpadColorList.add(new ColorBean("#FFD700"));
        sketchpadColorList.add(new ColorBean("#FFF8DC"));
        sketchpadColorList.add(new ColorBean("#DAA520"));
        sketchpadColorList.add(new ColorBean("#B8860B"));
        sketchpadColorList.add(new ColorBean("#FFFAF0"));
        sketchpadColorList.add(new ColorBean("#FDF5E6"));
        sketchpadColorList.add(new ColorBean("#F5DEB3"));
        sketchpadColorList.add(new ColorBean("#FFE4B5"));
        sketchpadColorList.add(new ColorBean("#FFA500"));
        sketchpadColorList.add(new ColorBean("#FFEFD5"));
        sketchpadColorList.add(new ColorBean("#FFEBCD"));
        sketchpadColorList.add(new ColorBean("#FFDEAD"));
        sketchpadColorList.add(new ColorBean("#FAEBD7"));
        sketchpadColorList.add(new ColorBean("#D2B48C"));
        sketchpadColorList.add(new ColorBean("#DEB887"));
        sketchpadColorList.add(new ColorBean("#FFE4C4"));
        sketchpadColorList.add(new ColorBean("#FF8C00"));
        sketchpadColorList.add(new ColorBean("#FAF0E6"));
        sketchpadColorList.add(new ColorBean("#CD853F"));
        sketchpadColorList.add(new ColorBean("#FFDAB9"));
        sketchpadColorList.add(new ColorBean("#F4A460"));
        sketchpadColorList.add(new ColorBean("#D2691E"));
        sketchpadColorList.add(new ColorBean("#8B4513"));
        sketchpadColorList.add(new ColorBean("#FFF5EE"));
        sketchpadColorList.add(new ColorBean("#A0522D"));
        sketchpadColorList.add(new ColorBean("#FFA07A"));
        sketchpadColorList.add(new ColorBean("#FF7F50"));
        sketchpadColorList.add(new ColorBean("#FF4500"));
        sketchpadColorList.add(new ColorBean("#E9967A"));
        sketchpadColorList.add(new ColorBean("#FF6347"));
        sketchpadColorList.add(new ColorBean("#FFE4E1"));
        sketchpadColorList.add(new ColorBean("#FA8072"));
        sketchpadColorList.add(new ColorBean("#FFFAFA"));
        sketchpadColorList.add(new ColorBean("#F08080"));
        sketchpadColorList.add(new ColorBean("#BC8F8F"));
        sketchpadColorList.add(new ColorBean("#CD5C5C"));
        sketchpadColorList.add(new ColorBean("#FF0000"));
        sketchpadColorList.add(new ColorBean("#A52A2A"));
        sketchpadColorList.add(new ColorBean("#B22222"));
        sketchpadColorList.add(new ColorBean("#8B0000"));
        sketchpadColorList.add(new ColorBean("#FFFFFF"));
        sketchpadColorList.add(new ColorBean("#F5F5F5"));
        sketchpadColorList.add(new ColorBean("#DCDCDC"));
        sketchpadColorList.add(new ColorBean("#D3D3D3"));
        sketchpadColorList.add(new ColorBean("#C0C0C0"));
        sketchpadColorList.add(new ColorBean("#A9A9A9"));
        sketchpadColorList.add(new ColorBean("#808080"));
        sketchpadColorList.add(new ColorBean("#696969"));
        sketchpadColorList.add(new ColorBean("#000000").setSelected(true));
    }

    /**
     * 初始化字体粗细
     */
    private void initStrong(){
        strongList = new ArrayList<>();

        strongList.add(new StrongBean(3, R.mipmap.paint_strong_12).setSelected(true));
        strongList.add(new StrongBean(6, R.mipmap.paint_strong_20));
        strongList.add(new StrongBean(9, R.mipmap.paint_strong_28));
        strongList.add(new StrongBean(12, R.mipmap.paint_strong_36));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_eraser:  //撤销
                showMsg("正在努力开发中...");
                break;
            case R.id.tv_reset:   //重置
                sketchpad.clearCanvas();
                break;
            case R.id.tv_save:    //保存到本地
                getPermission();
                break;
        }
    }

    private void getPermission(){
        boolean isAllGranted = checkPermissionAllGranted(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        });

        if (isAllGranted) {
            saveImageToAlbum();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            //SD卡写入权限
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            //SD卡读取权限
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                saveImageToAlbum();
            }
        } else {
            saveImageToAlbum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case SDK_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImageToAlbum();
                } else {
                    showMsg("保存失败，请允许所有权限。");
                }
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    protected boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    private void saveImageToAlbum(){
        Bitmap bitmap = sketchpad.getBitmap();
        File file = null;
        try {
            file = saveImage(bitmap, System.currentTimeMillis() + ".png");
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);  //这个广播的目的就是更新图库
            showMsg("保存成功，路径为：" + file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            showMsg("保存失败，未知错误！");
        }
    }

    /**
     * @param bm
     * @param fileName
     * @return
     * @throws IOException
     */
    protected File saveImage(Bitmap bm, String fileName) throws IOException {
        String subForder = SDCardPathUtils.getSDPath(this);
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (colorList != null) {
            colorList.clear();
            colorList = null;
        }
        if (sketchpadColorList != null) {
            sketchpadColorList.clear();
            sketchpadColorList = null;
        }
        if (strongList != null) {
            strongList.clear();
            strongList = null;
        }
    }
}
