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
import com.tim.tsms.transpondsms.adapter.RuleAdapter;
import com.tim.tsms.transpondsms.model.RuleModel;
import com.tim.tsms.transpondsms.utils.SendHistory;
import com.tim.tsms.transpondsms.utils.SendUtil;
import com.tim.tsms.transpondsms.utils.UpdateAppHttpUtil;
import com.tim.tsms.transpondsms.utils.aUtil;
import com.umeng.analytics.MobclickAgent;
import com.vector.update_app.UpdateAppManager;

import java.util.ArrayList;
import java.util.List;

public class RuleActivity extends AppCompatActivity {

    private String TAG = "RuleActivity";
    // tlogList用于存储数据
    private List<RuleModel> ruleModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        // 先拿到数据并放在适配器上
        initRules(); //初始化数据
        RuleAdapter adapter=new RuleAdapter(RuleActivity.this,R.layout.rule_item,ruleModels);

        // 将适配器上的数据传递给listView
        ListView listView=findViewById(R.id.list_view_rule);
        listView.setAdapter(adapter);

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RuleModel ruleModel=ruleModels.get(position);
                Toast.makeText(RuleActivity.this,ruleModel.getMatchId()+ruleModel.getSenderId()+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
    // 初始化数据
    private void initRules(){
        for(long i=0;i<10;i++){
            RuleModel a=new RuleModel(i,i+100);
            ruleModels.add(a);
            RuleModel b=new RuleModel(i,i+200);
            ruleModels.add(b);
            RuleModel c=new RuleModel(i,i+200);
            ruleModels.add(c);
            RuleModel d=new RuleModel(i,i+200);
            ruleModels.add(d);
        }
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
