package com.example.hopjs.filmcinema.UI;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hopjs.filmcinema.Common.CheckInput;
import com.example.hopjs.filmcinema.Common.TelNumMatch;
import com.example.hopjs.filmcinema.Data.Result;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.UI.Fragment.PcenterFragment;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hopjs on 2016/12/18.
 */

public class EditUserAccount {
    private final int MSG_PHONE=1;
    private final int MSG_FINFOR=2;
    private final int MSG_PWD=3;
    private EditText etPhonePwd,etPhoneNew,etPhoneCode;
    private Button btPhoneSend;
    private EditText etPswOld,etPswNew,etPswNewConfirm;
    private EditText etFName;
    private RadioButton rbFMan,rbFWoman;
    private Button btFChange;
    private ImageView ivFPortrait;
    private View vPhone,vPsw,vF;
    private Context context;
    private UserAccount userAccount;
    private boolean hasFFixed;
    private boolean portraitHasFixed;
    private String portraitPath,portraitName;
    private Bitmap portrait;
    private boolean hasPhoneSend;
    private String code;
    private PcenterFragment fragment;
    private Handler handler;
    private int handlerMessage;
    private NiftyDialogBuilder niftyDialogBuilder;
    private boolean sendMSisSleep;
    private Handler sleepOneMinute=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btPhoneSend.setText("发送验证码 "+msg.arg2+"s");
        }
    };

    public EditUserAccount(Context context, PcenterFragment fragment,Handler handler,int message) {
        this.fragment = fragment;
        this.context = context;
        this.handler = handler;
        handlerMessage = message;
        userAccount = ((MyApplication)context.getApplicationContext()).userAccount;
        vF = LayoutInflater.from(context).
                inflate(R.layout.edit0user0account_finfor,null,false);
        vPhone = LayoutInflater.from(context).
                inflate(R.layout.edit0user0account_phone0number,null,false);
        vPsw = LayoutInflater.from(context).
                inflate(R.layout.edit0user0account_password,null,false);
        etFName = (EditText)vF.findViewById(R.id.et_edit0user0account_finfor_name);
        rbFMan = (RadioButton) vF.findViewById(R.id.rb_edit0user0account_finfor_man);
        rbFWoman = (RadioButton)vF.findViewById(R.id.rb_edit0user0account_finfor_woman);
        btFChange = (Button) vF.findViewById(R.id.bt_edit0user0account_finfor_change);
        ivFPortrait = (ImageView) vF.findViewById(R.id.iv_edit0user0account_finfor_portrait);


        rbFMan.setOnClickListener(listener);
        rbFWoman.setOnClickListener(listener);
        btFChange.setOnClickListener(listener);

        etPhoneCode = (EditText)vPhone.findViewById
                (R.id.et_edit0user0account_phone0number_confirm0code);
        etPhonePwd = (EditText)vPhone.findViewById
                (R.id.et_edit0user0account_pwd);
        etPhoneNew = (EditText)vPhone.findViewById
                (R.id.et_edit0user0account_phone0number_new);
        btPhoneSend = (Button) vPhone.findViewById
                (R.id.bt_edit0user0account_phone0number_send);

        btPhoneSend.setOnClickListener(listener);


        etPswOld = (EditText)vPsw.findViewById
                (R.id.et_edit0user0account_password_old);
        etPswNew = (EditText)vPsw.findViewById
                (R.id.et_edit0user0account_password_new);
        etPswNewConfirm = (EditText)vPsw.findViewById
                (R.id.et_edit0user0account_password_new0confirm);
        sendMSisSleep = false;

    }

    public void editFinfor(){
        portraitHasFixed=false;
        hasFFixed=false;
        etFName.setText(userAccount.getName());
        if(userAccount.getSex().equals("男")){
            rbFMan.setChecked(true);
            rbFWoman.setChecked(false);
        }else {
            rbFMan.setChecked(false);
            rbFWoman.setChecked(true);
        }
        Glide.with(context)
                .load(userAccount.getPortraitName())
                .into(ivFPortrait);

        niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
        niftyDialogBuilder
                .withDialogColor(context.getResources().getColor(R.color.ButtomGuidBar))
                .isCancelable(false)
                .withMessage("                请调整您的信息")
                .withTitle("基 本 信 息")
                .setCustomView(vF,context)
                .withEffect(Effectstype.Fall)
                .withButton1Text("确认")
                .withButton2Text("取消")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userAccount=((MyApplication)context.getApplicationContext()).userAccount;
                        if(!etFName.getText().toString().equals(userAccount.getName()))hasFFixed=true;
                        if(hasFFixed){
                           // userAccount =
                            if(etFName.getText().length()==0){
                                Toast.makeText(context.getApplicationContext(),
                                        "请输入用户名",Toast.LENGTH_SHORT).show();
                                return;

                            }
                            if(CheckInput.isRightName(etFName.getText().toString())!=
                                    CheckInput.NAME_OK){
                                Toast.makeText(context.getApplicationContext(),
                                        "用户名长度应在1-10之间",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(!portraitHasFixed)portrait=null;

                            String sex="";
                            if(rbFMan.isChecked()){
                                sex="男";
                            }else {
                                sex="女";
                            }
                            Result r=Connect.postEdit(userAccount.getUserId(),etFName.getText().toString(),
                                    sex,portrait);
                            if(r.getCode()!=Result.RESULT_NOT_NETWORK){
                                ((MyApplication)context.getApplicationContext()).userAccount=userAccount;
                                Toast.makeText(context.getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                                Message message = new Message();
                                message.arg1=handlerMessage;
                                handler.sendMessage(message);
                            }else {
                                Toast.makeText(context.getApplicationContext(),
                                        "修改失败，请检查您的网络",Toast.LENGTH_SHORT).show();
                            }
                            niftyDialogBuilder.dismiss();
                        }else {
                            niftyDialogBuilder.dismiss();
                        }
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        niftyDialogBuilder.dismiss();
                    }
                })
                .show();
    }



    public void changePhone(){
        hasPhoneSend = false;
        etPhonePwd.setText("");
        etPhoneNew.setText("");
        etPhoneCode.setText("");
        niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
        niftyDialogBuilder
                .withDialogColor(context.getResources().getColor(R.color.ButtomGuidBar))
                .isCancelable(false)
                .withMessage("            请填写新的手机号码")
                .withTitle("绑 定 手 机 号")
                .setCustomView(vPhone,context)
                .withEffect(Effectstype.Fall)
                .withButton1Text("确认")
                .withButton2Text("取消")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etPhonePwd.getText().length()==0
                                ||etPhoneNew.getText().length()==0
                                ||etPhoneCode.length()==0){
                            Toast.makeText(context.getApplicationContext(),"请完整的输入内容",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!etPhonePwd.getText().toString().equals(userAccount.getPwd())){
                            Toast.makeText(context.getApplicationContext(),"密码不正确",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!hasPhoneSend){
                            Toast.makeText(context.getApplicationContext(),"请发送验证码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!etPhoneCode.getText().toString().equals(code)){
                            Toast.makeText(context.getApplicationContext(),"验证码不正确",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        userAccount=((MyApplication)context.getApplicationContext()).userAccount;
                        switch (Connect.postPhone(userAccount.getUserId(),etPhoneNew.getText().toString())
                                .getCode()){
                            case Result.RESULT_OK:
                                Toast.makeText(context.getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                                Message message = new Message();
                                message.arg1 = handlerMessage;
                                handler.sendMessage(message);
                                break;
                            case Result.RESULT_NOT_NETWORK:
                                Toast.makeText(context.getApplicationContext(), "修改失败，请检查您的网络", Toast.LENGTH_SHORT).show();
                                break;
                            case Result.RESULT_EXIT_PHONE:
                                Toast.makeText(context.getApplicationContext(), "修改失败，该手机号已绑定另一个账号", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        niftyDialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        niftyDialogBuilder.dismiss();
                    }
                })
                .show();
    }

    public void changPsw(){
        etPswNew.setText("");
        etPswNewConfirm.setText("");
        etPswOld.setText("");
        niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
        niftyDialogBuilder
                .withDialogColor(context.getResources().getColor(R.color.ButtomGuidBar))
                .isCancelable(false)
                .withMessage("            请输入您的密码")
                .withTitle("密 码 修 改")
                .setCustomView(vPsw,context)
                .withEffect(Effectstype.Fall)
                .withButton1Text("确认")
                .withButton2Text("取消")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etPswOld.getText().length()!=0&&
                                etPswNew.getText().length()!=0&&
                                etPswNewConfirm.getText().length()!=0){

                            if(etPswOld.getText().toString().equals(userAccount.getPwd())){
                                if(etPswNewConfirm.getText().toString().equals(
                                        etPswNew.getText().toString())){
                                    switch(CheckInput.isRightPassword(etPswNew.getText().toString())) {
                                        case CheckInput.PASSWORD_OK:
                                            if(Connect.postPsd(userAccount.getUserId(),etPswNew.getText().toString())
                                                    .getCode()==Result.RESULT_OK) {
                                                Toast.makeText(context.getApplicationContext(),
                                                        "修改成功",Toast.LENGTH_SHORT).show();
                                                userAccount.setPwd(etPswNew.getText().toString());
                                                //
                                                ((MyApplication) context.getApplicationContext()).userAccount = userAccount;
                                            }else {
                                                Toast.makeText(context.getApplicationContext(),
                                                        "修改失败，请检查您的网络",Toast.LENGTH_SHORT).show();
                                            }
                                            niftyDialogBuilder.dismiss();
                                            break;
                                        case CheckInput.PASSWORD_EXISTILLEGALCHAR:
                                            Toast.makeText(context.getApplicationContext(),
                                                    "新密码不能包含除数字、字母以外的其他字符",Toast.LENGTH_SHORT).show();
                                            break;
                                        case CheckInput.PASSWORD_NOTFITLENGTH:
                                            Toast.makeText(context.getApplicationContext(),
                                                    "新密码应该在6-12位之间",Toast.LENGTH_SHORT).show();
                                            break;
                                    }

                                }else {
                                    Toast.makeText(context.getApplicationContext(),
                                            "两次密码输入不同",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(context.getApplicationContext(),
                                        "旧密码输入错误",Toast.LENGTH_SHORT).show();

                            }

                        }else {
                            Toast.makeText(context.getApplicationContext(),
                                    "请完整地填写密码",Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        niftyDialogBuilder.dismiss();
                    }
                })
                .show();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_edit0user0account_phone0number_send:
                    sendSMS();
                    break;
                case R.id.rb_edit0user0account_finfor_man:
                    hasFFixed = true;
                    rbFWoman.setChecked(false);
                    break;
                case R.id.rb_edit0user0account_finfor_woman:
                    hasFFixed = true;
                    rbFMan.setChecked(false);
                    break;
                case R.id.bt_edit0user0account_finfor_change:
                    getPortraitPath();
                    break;
            }
        }
    };

    public void sendSMS(){
        if(!sendMSisSleep) {
            String phoneNumber = etPhoneNew.getText().toString();
            code = getCode();


            if (phoneNumber.length() == 0) {
                Toast.makeText(context.getApplicationContext(), "请输入新的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TelNumMatch.isValidPhoneNumber(phoneNumber)) {
                Toast.makeText(context.getApplicationContext(), "新的手机号不正确", Toast.LENGTH_SHORT).show();
                return;
            }

            //拆分短信内容（手机短信长度限制）
            //获取短信管理器
            android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, code, null, null);
            Toast.makeText(context.getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
            sendMSisSleep=true;
            hasPhoneSend=true;
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        Message message;
                        for(int i=0;i<60;++i){
                            sleep(1000);
                            message=Message.obtain();
                            message.arg2 = 60-i;
                            sleepOneMinute.sendMessage(message);
                        }
                        sendMSisSleep=false;
                    }catch (Exception e) {
                    }
                }
            }.start();
        }else {
            Toast.makeText(context.getApplicationContext(), "请过段时间再发", Toast.LENGTH_SHORT).show();
        }
    }
    private String getCode(){
        Time time = new Time("GMT+8");
        time.setToNow();
        Random random = new Random(time.second);
        String code="";
        String tem = random.nextInt(10)*time.year%10+"";
        code+=tem;
        tem = (char)(random.nextInt(10)*time.year%26+'A')+"";
        code+=tem;
        tem = random.nextInt(10)*time.minute%10+"";
        code+=tem;
        tem = (char)(random.nextInt(10)*time.minute%26+'A')+"";
        code+=tem;
        tem = random.nextInt(10)*time.monthDay%10+"";
        code+=tem;
        return code;
    }
    private void getPortraitPath(){
        fragment.openPicture();
    }

    public void setPortrait(Bitmap portrait) {
        this.portrait = portrait;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        portrait.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(context)
                .load(bytes)
                .into(ivFPortrait);
        portraitHasFixed = true;
    }
    public void setPortraitPath(String portrait) {
        this.portraitPath = portrait;
        int index=0;
        for(int i=0;i<portraitPath.length();++i){
            if(portraitPath.charAt(i)=='/'){
                index = i;
            }
        }
        int dian=0;
        for(int i=0;i<portraitPath.length();++i){
            if(portraitPath.charAt(i)=='.'){
                dian = i;
            }
        }
        portraitName="";
        for(int i=index+1;i<dian;++i){
            portraitName+=portraitPath.charAt(i);
        }
    }
}
