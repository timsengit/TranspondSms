package com.tim.tsms.transpondsms.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tim.tsms.transpondsms.model.RuleModel;
import com.tim.tsms.transpondsms.model.SenderModel;
import com.tim.tsms.transpondsms.model.vo.DingDingSettingVo;
import com.tim.tsms.transpondsms.model.vo.SmsVo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.tim.tsms.transpondsms.model.SenderModel.TYPE_DINGDING;

public class SendUtil {
    private static String TAG = "SendUtil";

    public static void send_msg(String msg){
        if(SettingUtil.using_dingding()){
            try {
                DingdingMsg.sendMsg(msg);
            }catch (Exception e){
                Log.d(TAG,"发送出错："+e.getMessage());
            }

        }
        if(SettingUtil.using_email()){
//            SendMailUtil.send(SettingUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_TOADD_KEY),"转发",msg);
        }

    }
    public static void send_msg(Context context,SmsVo smsVo){
        RuleUtil.init(context);
        List<RuleModel> rulelist = RuleUtil.getRule(null,null);
        if(!rulelist.isEmpty()){
            SenderUtil.init(context);
            for (RuleModel ruleModel:rulelist
            ) {
                List<SenderModel> senderModels = SenderUtil.getSender(ruleModel.getId(),null);
                for (SenderModel senderModel:senderModels
                ) {
                    SendUtil.senderSendMsg(smsVo,senderModel);
                }
            }

        }

        if(SettingUtil.using_dingding()){
            try {
//                DingdingMsg.sendMsg(msg);
            }catch (Exception e){
                Log.d(TAG,"发送出错："+e.getMessage());
            }

        }
        if(SettingUtil.using_email()){
//            SendMailUtil.send(SettingUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_TOADD_KEY),"转发",msg);
        }

    }
    public static void senderSendMsg(SmsVo smsVo,SenderModel senderModel) {

        switch (senderModel.getType()){
            case TYPE_DINGDING:
                //try phrase json setting
                if (senderModel.getJsonSetting() != null) {
                    DingDingSettingVo dingDingSettingVo = JSON.parseObject(senderModel.getJsonSetting(), DingDingSettingVo.class);
                    if(dingDingSettingVo!=null){
                        try {
                            DingdingMsg.sendMsg(false, dingDingSettingVo.getToken(), dingDingSettingVo.getSecret(), smsVo.toString());
                        }catch (Exception e){
                            Log.e(TAG, "senderSendMsg: dingding error "+e.getMessage() );
                        }

                    }
                }

                break;
            default:
                break;
        }
    }

}
