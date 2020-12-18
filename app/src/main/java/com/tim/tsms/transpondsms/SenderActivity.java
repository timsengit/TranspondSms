package com.tim.tsms.transpondsms;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tim.tsms.transpondsms.BroadCastReceiver.TSMSBroadcastReceiver;
import com.tim.tsms.transpondsms.adapter.SenderAdapter;
import com.tim.tsms.transpondsms.model.SenderModel;
import com.tim.tsms.transpondsms.utils.SendHistory;
import com.tim.tsms.transpondsms.utils.SendUtil;
import com.tim.tsms.transpondsms.utils.UpdateAppHttpUtil;
import com.tim.tsms.transpondsms.utils.aUtil;
import com.umeng.analytics.MobclickAgent;
import com.vector.update_app.UpdateAppManager;

import java.util.ArrayList;
import java.util.List;

public class SenderActivity extends AppCompatActivity {

    private String TAG = "SenderActivity";
    // 用于存储数据
    private List<SenderModel> senderModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
        // 先拿到数据并放在适配器上
        initSenders(); //初始化数据
        SenderAdapter adapter=new SenderAdapter(SenderActivity.this,R.layout.sender_item,senderModels);

        // 将适配器上的数据传递给listView
        ListView listView=findViewById(R.id.list_view_sender);
        listView.setAdapter(adapter);

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SenderModel senderModel=senderModels.get(position);
                Toast.makeText(SenderActivity.this,senderModel.getName(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    // 初始化数据
    private void initSenders(){
        for(int i=0;i<10;i++){
            SenderModel a=new SenderModel("a",R.drawable.ic_launcher_background);
            senderModels.add(a);
            SenderModel b=new SenderModel("B",R.drawable.ic_launcher_background);
            senderModels.add(b);
            SenderModel c=new SenderModel("C",R.drawable.ic_launcher_background);
            senderModels.add(c);
            SenderModel d=new SenderModel("D",R.drawable.ic_launcher_background);
            senderModels.add(d);
        }
    }

    public void addSender(View view){

    }
    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
