package com.example.zhouwei.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mButton1,mButton2,mButton3,mButton4,mButton5,mButton6;
    private CustomPopWindow mCustomPopWindow;
    private CustomPopWindow mListPopWindow;
    private AppCompatSeekBar mAppCompatSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1 = (TextView) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        mButton2 = (TextView) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
        mButton3 = (TextView) findViewById(R.id.button3);
        mButton3.setOnClickListener(this);
        mButton4 = (TextView) findViewById(R.id.button4);
        mButton4.setOnClickListener(this);
        mButton5 = (TextView) findViewById(R.id.button5);
        mButton5.setOnClickListener(this);
        mButton6 = (TextView) findViewById(R.id.button6);
        mButton6.setOnClickListener(this);


        mAppCompatSeekBar = (AppCompatSeekBar) findViewById(R.id.seek_bar);
        mAppCompatSeekBar.setMax(100);
        mAppCompatSeekBar.setProgress(100);
        mAppCompatSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               float alpha = seekBar.getProgress() * 1.0f / 100 ;
                if(alpha < 0.2){
                    alpha = 0.2f;
                }
               Window mWindow = getWindow();
                WindowManager.LayoutParams params = mWindow.getAttributes();
                params.alpha = alpha;
                mWindow.setAttributes(params);
                Log.e("zhouwei","progress:"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                showPopBottom();
                break;
            case R.id.button2:
                showPopTop();
                break;
            case R.id.button3:
                showPopMenu();
                break;
            case R.id.button4:
                showPopListView();
                break;
            case R.id.button5:
                showPopTopWithDarkBg();
                break;
            case R.id.button6:
                useInAndOutAnim();
                break;
        }
    }

    private void showPopBottom(){
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_layout1)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .create()
                .showAsDropDown(mButton1,0,10);
    }

    private void showPopTop(){
      CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
              .setView(R.layout.pop_layout2)
              .create();
      popWindow .showAsDropDown(mButton2,0,  - (mButton2.getHeight() + popWindow.getHeight()));
      //popWindow.showAtLocation(mButton1, Gravity.NO_GRAVITY,0,0);
    }

    /**
     * 显示PopupWindow 同时背景变暗
     */
    private void showPopTopWithDarkBg(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .create()
                .showAsDropDown(mButton5,0,20);
    }

    private void useInAndOutAnim(){
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_layout1)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .setAnimationStyle(R.style.CustomPopWindowStyle)
                .create()
                .showAsDropDown(mButton6,0,10);
    }

    private void showPopMenu(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .create()
                .showAsDropDown(mButton3,0,20);


    }

    private void showPopListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list,null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                .create()
                .showAsDropDown(mButton4,0,20);
    }

    private void handleListView(View contentView){
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        adapter.setData(mockData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private List<String> mockData(){
        List<String> data = new ArrayList<>();
        for (int i=0;i<100;i++){
            data.add("Item:"+i);
        }

        return data;
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = "点击 Item菜单1";
                        break;
                    case R.id.menu2:
                        showContent = "点击 Item菜单2";
                        break;
                    case R.id.menu3:
                        showContent = "点击 Item菜单3";
                        break;
                    case R.id.menu4:
                        showContent = "点击 Item菜单4";
                        break;
                    case R.id.menu5:
                        showContent = "点击 Item菜单5" ;
                        break;
                }
                Toast.makeText(MainActivity.this,showContent,Toast.LENGTH_SHORT).show();
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
    }
}
