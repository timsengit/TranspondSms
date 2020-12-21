package com.tim.tsms.transpondsms;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tim.tsms.transpondsms.utils.UpdateAppHttpUtil;
import com.tim.tsms.transpondsms.utils.aUtil;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;


public class SettingActivity extends AppCompatActivity {
    private String TAG = "SettingActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Switch check_with_reboot = (Switch)findViewById(R.id.switch_with_reboot);
        checkWithReboot(check_with_reboot);

        TextView version_now = (TextView)findViewById(R.id.version_now);
        Button check_version_now = (Button)findViewById(R.id.check_version_now);
        try {
            version_now.setText(aUtil.getVersionName(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        check_version_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNewVersion();
            }
        });

    }

    //检查重启广播接受器状态并设置
    private void checkWithReboot(Switch withrebootSwitch){
        //获取组件
        final ComponentName cm = new ComponentName(this.getPackageName(), this.getPackageName()+".RebootBroadcastReceiver");
        final PackageManager pm = getPackageManager();
        int state = pm.getComponentEnabledSetting(cm);
        if (state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                && state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER) {

            withrebootSwitch.setChecked(true);
        }else{
            withrebootSwitch.setChecked(false);
        }
        withrebootSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int newState = (Boolean)isChecked ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                pm.setComponentEnabledSetting(cm, newState,PackageManager.DONT_KILL_APP);
                Log.d(TAG,"onCheckedChanged:"+isChecked);
            }
        });
    }

    private void checkNewVersion(){
        String geturl = "http://api.allmything.com/api/version/hasnew?versioncode=";

        try {
            geturl+= aUtil.getVersionCode(SettingActivity.this);

            Log.i("SettingActivity",geturl);
            new UpdateAppManager
                    .Builder()
                    //当前Activity
                    .setActivity(SettingActivity.this)
                    //更新地址
                    .setUpdateUrl(geturl)
                    //全局异常捕获
                    .handleException(new ExceptionHandler() {
                        @Override
                        public void onException(Exception e) {
                            Log.e(TAG, "onException: ",e );
                            Toast.makeText(SettingActivity.this, "更新失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    //实现httpManager接口的对象
                    .setHttpManager(new UpdateAppHttpUtil())
                    .build()
                    .checkNewApp(new UpdateCallback(){
                        /**
                         * 没有新版本
                         */
                        protected void noNewApp(String error) {
                            Toast.makeText(SettingActivity.this, "没有新版本", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    .update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
