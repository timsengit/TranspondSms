package com.tim.tsms.transpondsms.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tim.tsms.transpondsms.model.LogModel;
import com.tim.tsms.transpondsms.model.RuleModel;
import com.tim.tsms.transpondsms.model.SenderModel;
import com.tim.tsms.transpondsms.model.vo.DingDingSettingVo;
import com.tim.tsms.transpondsms.model.vo.EmailSettingVo;
import com.tim.tsms.transpondsms.model.vo.SmsVo;
import com.tim.tsms.transpondsms.model.vo.WebNotifySettingVo;

import java.util.List;

import static com.tim.tsms.transpondsms.model.RuleModel.CHECK_CONTAIN;
import static com.tim.tsms.transpondsms.model.RuleModel.CHECK_END_WITH;
import static com.tim.tsms.transpondsms.model.RuleModel.CHECK_IS;
import static com.tim.tsms.transpondsms.model.RuleModel.CHECK_NOT_IS;
import static com.tim.tsms.transpondsms.model.RuleModel.CHECK_START_WITH;
import static com.tim.tsms.transpondsms.model.RuleModel.FILED_MSG_CONTENT;
import static com.tim.tsms.transpondsms.model.RuleModel.FILED_PHONE_NUM;
import static com.tim.tsms.transpondsms.model.RuleModel.FILED_TRANSPOND_ALL;
import static com.tim.tsms.transpondsms.model.SenderModel.TYPE_DINGDING;
import static com.tim.tsms.transpondsms.model.SenderModel.TYPE_EMAIL;
import static com.tim.tsms.transpondsms.model.SenderModel.TYPE_WEB_NOTIFY;

public class SendUtil {
    private static String TAG = "SendUtil";

    public static void send_msg(String msg){
        if(SettingUtil.using_dingding()){
            try {
                SenderDingdingMsg.sendMsg(msg);
            }catch (Exception e){
                Log.d(TAG,"发送出错："+e.getMessage());
            }

        }
        if(SettingUtil.using_email()){
//            SenderMailMsg.send(SettingUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_TOADD_KEY),"转发",msg);
        }

    }
    public static void send_msg_list(Context context,List<SmsVo> smsVoList){
        Log.i(TAG, "send_msg_list size: "+smsVoList.size());
        for (SmsVo smsVo:smsVoList){
            SendUtil.send_msg(context,smsVo);
        }
    }
    public static void send_msg(Context context,SmsVo smsVo){
        Log.i(TAG, "send_msg smsVo:"+smsVo);
        RuleUtil.init(context);
        LogUtil.init(context);

        List<RuleModel> rulelist = RuleUtil.getRule(null,null);
        if(!rulelist.isEmpty()){
            SenderUtil.init(context);
            for (RuleModel ruleModel:rulelist
            ) {
                boolean canSend=false;
                //使用转发规则制定的字段 匹配
                switch (ruleModel.getFiled()){
                    case FILED_TRANSPOND_ALL:
                        canSend=true;
                        break;
                    case FILED_PHONE_NUM:
                        switch (ruleModel.getCheck()){
                            case CHECK_IS:
                                if(smsVo.getMobile()!=null && smsVo.getMobile().equals(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                            case CHECK_NOT_IS:
                                if(smsVo.getMobile()!=null && !smsVo.getMobile().equals(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                            case CHECK_START_WITH:
                                if(smsVo.getMobile()!=null && smsVo.getMobile().startsWith(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                            case CHECK_END_WITH:
                                if(smsVo.getMobile()!=null && smsVo.getMobile().endsWith(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                            case CHECK_CONTAIN:
                                if(smsVo.getMobile()!=null && smsVo.getMobile().contains(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                        }
                        break;
                    case FILED_MSG_CONTENT:
                        switch (ruleModel.getCheck()){
                            case CHECK_IS:
                                if(smsVo.getContent()!=null && smsVo.getContent().equals(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                            case CHECK_NOT_IS:
                                if(smsVo.getContent()!=null && !smsVo.getContent().equals(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                            case CHECK_START_WITH:
                                if(smsVo.getContent()!=null && smsVo.getContent().startsWith(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                            case CHECK_END_WITH:
                                if(smsVo.getContent()!=null && smsVo.getContent().endsWith(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                            case CHECK_CONTAIN:
                                if(smsVo.getContent()!=null && smsVo.getContent().contains(ruleModel.getValue())){
                                    canSend=true;
                                }
                                break;
                        }
                        break;

                }
                //规则匹配发现需要发送
                if(canSend){
                    List<SenderModel> senderModels = SenderUtil.getSender(ruleModel.getSenderId(),null);
                    for (SenderModel senderModel:senderModels
                    ) {
                        LogUtil.addLog(new LogModel(smsVo.getMobile(),smsVo.getContent(),senderModel.getId()));
                        SendUtil.senderSendMsg(smsVo,senderModel);
                    }
                }

            }

        }
    }
    public static void senderSendMsg(SmsVo smsVo,SenderModel senderModel) {

        Log.i(TAG, "senderSendMsg smsVo:"+smsVo+"senderModel:"+senderModel);
        switch (senderModel.getType()){
            case TYPE_DINGDING:
                //try phrase json setting
                if (senderModel.getJsonSetting() != null) {
                    DingDingSettingVo dingDingSettingVo = JSON.parseObject(senderModel.getJsonSetting(), DingDingSettingVo.class);
                    if(dingDingSettingVo!=null){
                        try {
                            SenderDingdingMsg.sendMsg(false, dingDingSettingVo.getToken(), dingDingSettingVo.getSecret(), smsVo.getSmsVoForSend());
                        }catch (Exception e){
                            Log.e(TAG, "senderSendMsg: dingding error "+e.getMessage() );
                        }

                    }
                }

                break;
            case TYPE_EMAIL:
                //try phrase json setting
                if (senderModel.getJsonSetting() != null) {
                    EmailSettingVo emailSettingVo = JSON.parseObject(senderModel.getJsonSetting(), EmailSettingVo.class);
                    if(emailSettingVo!=null){
                        try {
                            SenderMailMsg.sendEmail(false, emailSettingVo.getHost(),emailSettingVo.getPort(),emailSettingVo.getFromEmail(),
                                    emailSettingVo.getPwd(),emailSettingVo.getToEmail(),smsVo.getMobile(),smsVo.getSmsVoForSend());
                        }catch (Exception e){
                            Log.e(TAG, "senderSendMsg: SenderMailMsg error "+e.getMessage() );
                        }

                    }
                }

                break;
            case TYPE_WEB_NOTIFY:
                //try phrase json setting
                if (senderModel.getJsonSetting() != null) {
                    WebNotifySettingVo webNotifySettingVo = JSON.parseObject(senderModel.getJsonSetting(), WebNotifySettingVo.class);
                    if(webNotifySettingVo!=null){
                        try {
                            SenderWebNotifyMsg.sendMsg(false,webNotifySettingVo.getToken(),smsVo.getMobile(),smsVo.getSmsVoForSend());
                        }catch (Exception e){
                            Log.e(TAG, "senderSendMsg: SenderWebNotifyMsg error "+e.getMessage() );
                        }

                    }
                }

                break;
            default:
                break;
        }
    }

}
