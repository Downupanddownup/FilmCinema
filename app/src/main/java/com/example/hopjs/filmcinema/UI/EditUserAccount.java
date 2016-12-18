package com.example.hopjs.filmcinema.UI;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.hopjs.filmcinema.Common.TelNumMatch;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
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
    private EditText etPhoneOld,etPhoneNew,etPhoneCode;
    private Button btPhoneSend;
    private EditText etPswOld,etPswNew,etPswNewConfirm;
    private EditText etFName;
    private RadioButton rbFMan,rbFWoman;
    private Button btFChange;
    private ImageView ivFPortrait;
    private View vPhone,vPsw,vF;
    private Context context;
    private UserAccount userAccount;
    private boolean hasFFixed=false;
    private String portraitPath,portraitName;
    private Bitmap portrait;
    private boolean hasPhoneSend = false;
    private String code;
    private PcenterFragment fragment;

    public EditUserAccount(Context context, PcenterFragment fragment) {
        this.fragment = fragment;
        this.context = context;
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

        etFName.setOnClickListener(listener);
        rbFMan.setOnClickListener(listener);
        rbFWoman.setOnClickListener(listener);
        btFChange.setOnClickListener(listener);

        etPhoneCode = (EditText)vPhone.findViewById
                (R.id.et_edit0user0account_phone0number_confirm0code);
        etPhoneOld = (EditText)vPhone.findViewById
                (R.id.et_edit0user0account_phone0number_old);
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

    }

    public void editFinfor(){

        etFName.setText(userAccount.getName());
        if(userAccount.getSex().equals("男")){
            rbFMan.setChecked(true);
            rbFWoman.setChecked(false);
        }else {
            rbFMan.setChecked(false);
            rbFWoman.setChecked(true);
        }
        if(userAccount.isSetportrait()){
            Glide.with(context)
                    .load(userAccount.getPortraitName())
                    .into(ivFPortrait);
        }else {
            Glide.with(context)
                    .load(R.drawable.defaultportrait)
                    .into(ivFPortrait);
        }

        final NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
        niftyDialogBuilder
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
                        if(hasFFixed){
                           // userAccount =
                            userAccount.setName(etFName.getText().toString());
                            if(rbFMan.isChecked()){
                                userAccount.setSex("男");
                            }else {
                                userAccount.setSex("女");
                            }
                            //Connect
                            userAccount.setPortraitName(portraitName);//重名
                            ((MyApplication)context.getApplicationContext()).userAccount=userAccount;
                            Toast.makeText(context.getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
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

        etPhoneOld.setText(userAccount.getBphone());
        etPhoneNew.setText("");
        etPhoneCode.setText("");
        final NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
        niftyDialogBuilder
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
                        if(!etPhoneOld.getText().toString().equals(userAccount.getPwd())){
                            Toast.makeText(context.getApplicationContext(),"旧手机号码不正确",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(etPhoneNew.getText().length()==0){
                            Toast.makeText(context.getApplicationContext(),"请输入新的手机号",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!TelNumMatch.isValidPhoneNumber(etPhoneNew.getText().toString())){
                            Toast.makeText(context.getApplicationContext(),"新的手机号不正确",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(hasPhoneSend||etPhoneCode.getText().length()==0){
                            if(code.equals(etPhoneCode.getText().toString())){
                                //result = Connect.send
                               // if(result == ok){}else{Toast.makeText(context.getApplicationContext(),"该手机号码已绑定其他用户",Toast.LENGTH_SHORT).show();}
                                // userAccount.setPortraitName(portatitName);//重名
                                ((MyApplication)context.getApplicationContext()).userAccount=userAccount;
                                if(true){
                                    Toast.makeText(context.getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context.getApplicationContext(),"修改失败，该手机号已绑定另一个账号",Toast.LENGTH_SHORT).show();
                                }
                                niftyDialogBuilder.dismiss();
                            }else {
                                Toast.makeText(context.getApplicationContext(),"验证码错误，请重试",Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(context.getApplicationContext(),"请填写验证码",Toast.LENGTH_SHORT).show();
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

    public void changPsw(){
        etPswNew.setText("");
        etPswNewConfirm.setText("");
        etPswOld.setText("");
        final NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
        niftyDialogBuilder
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
                                    userAccount.setPwd(etPswNew.getText().toString());
                                    //C
                                    ((MyApplication)context.getApplicationContext()).userAccount=userAccount;
                                    niftyDialogBuilder.dismiss();
                                }else {
                                    Toast.makeText(context.getApplicationContext(),
                                            "两次密码输入不同",Toast.LENGTH_SHORT).show();

                                }
                            }else {
                                Toast.makeText(context.getApplicationContext(),
                                        "旧密码输入错误",Toast.LENGTH_SHORT).show();

                            }

                        }else {
                            Toast.makeText(context.getApplicationContext(),"请完整地填写密码",Toast.LENGTH_SHORT).show();

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
                    hasPhoneSend=true;
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
                    hasFFixed = true;
                    getPortraitPath();
                    break;
                case R.id.et_edit0user0account_finfor_name:
                    hasFFixed = true;
                    break;
            }
        }
    };

    public void sendSMS(){
        String phoneNumber = etPhoneOld.getText().toString();
        String message = getCode();

        if(!phoneNumber.equals(userAccount.getPwd())){
            Toast.makeText(context.getApplicationContext(),"旧手机号码不正确",Toast.LENGTH_SHORT).show();
            return;
        }

        if(etPhoneNew.getText().length()==0){
            Toast.makeText(context.getApplicationContext(),"请输入新的手机号",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!TelNumMatch.isValidPhoneNumber(etPhoneNew.getText().toString())){
            Toast.makeText(context.getApplicationContext(),"新的手机号不正确",Toast.LENGTH_SHORT).show();
            return;
        }

        //拆分短信内容（手机短信长度限制）
        if(TelNumMatch.isValidPhoneNumber(phoneNumber)) {
            //获取短信管理器
            android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }else {
            Toast.makeText(context.getApplicationContext(),"号码错误",Toast.LENGTH_SHORT).show();
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
