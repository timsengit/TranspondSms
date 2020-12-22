package com.tim.tsms.transpondsms;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tim.tsms.transpondsms.adapter.RuleAdapter;
import com.tim.tsms.transpondsms.model.RuleModel;
import com.tim.tsms.transpondsms.model.SenderModel;
import com.tim.tsms.transpondsms.utils.RuleUtil;
import com.tim.tsms.transpondsms.utils.SenderUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class RuleActivity extends AppCompatActivity {

    private String TAG = "RuleActivity";
    // 用于存储数据
    private List<RuleModel> ruleModels = new ArrayList<>();
    private RuleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
        RuleUtil.init(RuleActivity.this);
        SenderUtil.init(RuleActivity.this);

        // 先拿到数据并放在适配器上
        initRules(); //初始化数据
        adapter = new RuleAdapter(RuleActivity.this, R.layout.rule_item, ruleModels);

        // 将适配器上的数据传递给listView
        ListView listView = findViewById(R.id.list_view_sender);
        listView.setAdapter(adapter);

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RuleModel ruleModel = ruleModels.get(position);
                Log.d(TAG, "onItemClick: "+ruleModel);
                setRule(ruleModel);
            }
        });


    }
    // 初始化数据
    private void initRules() {
        ruleModels = RuleUtil.getRule(null, null);
    }

    public void addSender(View view) {
        setRule(null);
    }


    private void setRule(final RuleModel ruleModel) {
        final AlertDialog.Builder alertDialog71 = new AlertDialog.Builder(RuleActivity.this);
        final View view1 = View.inflate(RuleActivity.this, R.layout.activity_alter_dialog_setview_rule, null);

        final RadioGroup radioGroupRuleFiled = (RadioGroup) view1.findViewById(R.id.radioGroupRuleFiled);
        if(ruleModel!=null)radioGroupRuleFiled.check(ruleModel.getRuleFiledCheckId());

        final RadioGroup radioGroupRuleCheck = (RadioGroup) view1.findViewById(R.id.radioGroupRuleCheck);
        if(ruleModel!=null)radioGroupRuleCheck.check(ruleModel.getRuleCheckCheckId());
        
        final TextView radioGroupRuleSenderTv = (TextView) view1.findViewById(R.id.radioGroupRuleSenderTv);
        final RadioGroup radioGroupRuleSender = (RadioGroup) view1.findViewById(R.id.radioGroupRuleSender);

        List<SenderModel> senderModels =SenderUtil.getSender(null,null);
        for (SenderModel senderModel:senderModels
             ) {
            RadioButton tempButton = new RadioButton(this);
            tempButton.setText(senderModel.getName());
            tempButton.setTag(senderModel.getId());
            if(ruleModel!=null && ruleModel.getSenderId().equals(senderModel.getId())){
                Log.d(TAG, "setRule: "+ruleModel.getSenderId());
                tempButton.setChecked(true);
            }
            radioGroupRuleSender.addView(tempButton);
        }
        if(senderModels.isEmpty()){
            radioGroupRuleSenderTv.setText("请先在发送方页面添加发送方");
        }



        final EditText editTextRuleValue = view1.findViewById(R.id.editTextRuleValue);
        if (ruleModel != null)
            editTextRuleValue.setText(ruleModel.getValue());

        Button buttonruleok = view1.findViewById(R.id.buttonruleok);
        Button buttonruledel = view1.findViewById(R.id.buttonruledel);
        alertDialog71
                .setTitle(R.string.setdingdingtitle)
                .setIcon(R.mipmap.dingding)
                .setView(view1)
                .create();
        final AlertDialog show = alertDialog71.show();
        buttonruleok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object senderId = ((RadioButton) view1.findViewById(radioGroupRuleSender.getCheckedRadioButtonId())).getTag();
                if (ruleModel == null) {
                    RuleModel newRuleModel = new RuleModel();
                    newRuleModel.setFiled(RuleModel.getRuleFiledFromCheckId(radioGroupRuleFiled.getCheckedRadioButtonId()));
                    newRuleModel.setCheck(RuleModel.getRuleCheckFromCheckId(radioGroupRuleCheck.getCheckedRadioButtonId()));
                    newRuleModel.setValue(editTextRuleValue.getText().toString());
                    newRuleModel.setSenderId(Long.valueOf(senderId.toString()));
                    RuleUtil.addRule(newRuleModel);
                    initRules();
                    adapter.add(ruleModels);
                } else {
                    ruleModel.setFiled(RuleModel.getRuleFiledFromCheckId(radioGroupRuleFiled.getCheckedRadioButtonId()));
                    ruleModel.setCheck(RuleModel.getRuleCheckFromCheckId(radioGroupRuleCheck.getCheckedRadioButtonId()));
                    ruleModel.setValue(editTextRuleValue.getText().toString());
                    ruleModel.setSenderId(Long.valueOf(senderId.toString()));
                    RuleUtil.updateRule(ruleModel);
                    initRules();
                    adapter.update(ruleModels);
                }

                show.dismiss();


            }
        });
        buttonruledel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ruleModel != null) {
                    RuleUtil.delRule(ruleModel.getId());
                    initRules();
                    adapter.del(ruleModels);
                }
                show.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
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
