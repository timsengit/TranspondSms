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
import com.tim.tsms.transpondsms.adapter.LogAdapter;
import com.tim.tsms.transpondsms.adapter.RuleAdapter;
import com.tim.tsms.transpondsms.model.LogModel;
import com.tim.tsms.transpondsms.model.vo.LogVo;
import com.tim.tsms.transpondsms.utils.LogUtil;
import com.tim.tsms.transpondsms.utils.RuleUtil;
import com.tim.tsms.transpondsms.utils.SendHistory;
import com.tim.tsms.transpondsms.utils.SendUtil;
import com.tim.tsms.transpondsms.utils.UpdateAppHttpUtil;
import com.tim.tsms.transpondsms.utils.aUtil;
import com.umeng.analytics.MobclickAgent;
import com.vector.update_app.UpdateAppManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private TSMSBroadcastReceiver smsBroadcastReceiver;
    private String TAG = "MainActivity";
    // logVoList用于存储数据
    private List<LogVo> logVos =new ArrayList<>();
    private LogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.init(this);
        // 先拿到数据并放在适配器上
        initTLogs(); //初始化数据
        adapter=new LogAdapter(MainActivity.this,R.layout.tlog_item, logVos);

        // 将适配器上的数据传递给listView
        ListView listView=findViewById(R.id.list_view_log);
        listView.setAdapter(adapter);

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogVo logVo= logVos.get(position);
//                Toast.makeText(MainActivity.this,logVo.getName(),Toast.LENGTH_SHORT).show();
            }
        });

//        textv_msg.setMovementMethod(ScrollingMovementMethod.getInstance());
//        textv_msg.setText(SendHistory.getHistory());

        checkPermission();

//        intentFilter=new IntentFilter();
//        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
//        intentFilter.addAction(MessageBroadcastReceiver.ACTION_DINGDING);
//        smsBroadcastReceiver=new SMSBroadcastReceiver();
//        //动态注册广播
//        registerReceiver(smsBroadcastReceiver, intentFilter);
    }
    // 初始化数据
    private void initTLogs(){
        logVos= LogUtil.getLog(null,null);
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
        //取消注册广播
        unregisterReceiver(smsBroadcastReceiver);
    }



    public void toSetting(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void toRuleSetting(View view){
        Intent intent = new Intent(this, RuleActivity.class);
        startActivity(intent);
    }

    public void toSendSetting(View view){
        Intent intent = new Intent(this, SenderActivity.class);
        startActivity(intent);
    }

    public void refreshLog(View view){
        Log.d(TAG,"refreshLog");
        LogModel newModel=new LogModel("199999","content", 1l);
        LogUtil.addLog(newModel);
        initTLogs();
        adapter.add(logVos);
    }
    
    //按返回键不退出回到桌面
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    private void  checkPermission()
    {
        // 检查权限是否获取（android6.0及以上系统可能默认关闭权限，且没提示）
        PackageManager pm = getPackageManager();
        boolean permission_receive_boot = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.RECEIVE_BOOT_COMPLETED", this.getPackageName()));
        boolean permission_readsms = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.READ_SMS", this.getPackageName()));

        if (!(
                permission_receive_boot
                && permission_readsms
        )) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECEIVE_BOOT_COMPLETED,
                    Manifest.permission.READ_SMS,
            }, 0x01);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.to_setting:
                toSetting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

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
