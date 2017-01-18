package com.example.hopjs.filmcinema.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopjs.filmcinema.Common.CheckInput;
import com.example.hopjs.filmcinema.Common.TelNumMatch;
import com.example.hopjs.filmcinema.Data.Result;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.Random;

/**
 * Created by Hopjs on 2016/10/15.
 */

public class Register extends AppCompatActivity {

    private static final int SLEEP=1;
    private static final int REGISTER=2;
    private ImageView ivReturn,ivSearch;
    private TextView tvTitle,tvTips;
    private EditText etName,etPwd,etRepwd,etPhone,etCcode;
    private ImageView ivJname,ivJpwd,ivJrepwd,ivJphone,ivTips;
    private Button btSendCode,btRegister,btCancel;
    private boolean[] first={true,true,true,true,true};
    private boolean sendMSisSleep=false;
    private String code;
    private boolean hasPhoneSend=false;
    private Handler sleepOneMinute=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.arg1){
                case SLEEP:
                    btSendCode.setText("发送验证码 ("+msg.arg2+"s)");
                    break;
                case REGISTER:
                    switch (msg.arg2){
                        case Result.RESULT_EXIT_PHONE:
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(),
                                    "该手机号码已绑定另一个账号",Toast.LENGTH_SHORT).show();
                            Looper.loop();

                            break;
                        case Result.RESULT_NOT_NETWORK:
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(),"注册失败，请检查您的网络",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            break;
                        case Result.RESULT_OK:
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            Register.this.finish();
                            break;
                    }
                    break;
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        inite();
        ivReturn.setOnClickListener(listener);
        btSendCode.setOnClickListener(listener);
        btRegister.setOnClickListener(listener);
        btCancel.setOnClickListener(listener);
        /*etName.addTextChangedListener(nameWatcher);
        etPhone.addTextChangedListener(phoneWatcher);
        etPwd.addTextChangedListener(pwdWatcher);
        etRepwd.addTextChangedListener(repwdWatcher);*/
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(first[0]){
                    first[0]=false;
                    etName.setText("");
                    etName.setTextColor(Color.BLACK);
                    return;
                }
                String tem = etName.getText().toString();
                if(tem.length()==0){
                    ivJname.setVisibility(View.VISIBLE);
                    ivJname.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("用户名不能为空！");
                    return;
                }
                if(CheckInput.isRightName(tem)==CheckInput.NAME_OVERLENGTH){
                    ivJname.setVisibility(View.VISIBLE);
                    ivJname.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("用户名长度应该在1-10之间");
                    return;
                }
                    ivJname.setVisibility(View.VISIBLE);
                    ivJname.setImageResource(R.drawable.right);
                    ivTips.setVisibility(View.INVISIBLE);
                    tvTips.setText("");
            }
        });
        etRepwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(first[2]){
                    first[2]=false;
                    etRepwd.setText("");
                    etRepwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etRepwd.setTextColor(Color.BLACK);
                    return;
                }
                String tem = etRepwd.getText().toString();
                String pwd = etPwd.getText().toString();
                if(tem.length()==0) {
                    ivJrepwd.setVisibility(View.VISIBLE);
                    ivJrepwd.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("请输入确认密码");
                    return;
                }
                if(!tem.equals(pwd)) {
                    ivJrepwd.setVisibility(View.VISIBLE);
                    ivJrepwd.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("两次密码不相同");
                    return;
                }
                ivJrepwd.setVisibility(View.VISIBLE);
                ivJrepwd.setImageResource(R.drawable.right);
                ivTips.setVisibility(View.INVISIBLE);
                tvTips.setText("");
            }
        });
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(first[3]){
                    first[3]=false;
                    etPhone.setText("");
                    etPhone.setTextColor(Color.BLACK);
                    return;
                }
                String tem = etPhone.getText().toString();
                if(tem.length()==0){
                    ivJphone.setVisibility(View.VISIBLE);
                    ivJphone.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("请输入手机号码");
                    return;
                }
                if(!TelNumMatch.isValidPhoneNumber(tem)){
                    ivJphone.setVisibility(View.VISIBLE);
                    ivJphone.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("手机号码格式不正确");
                    return;
                }
                ivJphone.setVisibility(View.VISIBLE);
                ivJphone.setImageResource(R.drawable.right);
                ivTips.setVisibility(View.INVISIBLE);
                tvTips.setText("");
            }
        });
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(first[1]){
                    first[1]=false;
                    etPwd.setText("");
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPwd.setTextColor(Color.BLACK);
                    return;
                }
                String tem = etPwd.getText().toString();
                if(tem.length() ==0){
                    ivJpwd.setVisibility(View.VISIBLE);
                    ivJpwd.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("请输入密码");
                    return;
                }
                if(CheckInput.isRightPassword(tem)==CheckInput.PASSWORD_NOTFITLENGTH){
                    ivJpwd.setVisibility(View.VISIBLE);
                    ivJpwd.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("密码长度应该在6-12位之间");
                    return;
                }
                if(CheckInput.isRightPassword(tem)==CheckInput.PASSWORD_EXISTILLEGALCHAR){
                    ivJpwd.setVisibility(View.VISIBLE);
                    ivJpwd.setImageResource(R.drawable.warning);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("密码不能包含除数字与字母之外的字符");
                    return;
                }
                ivJpwd.setVisibility(View.VISIBLE);
                ivJpwd.setImageResource(R.drawable.right);
                ivTips.setVisibility(View.INVISIBLE);
                tvTips.setText("");
            }
        });
        etCcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(first[4]){
                    first[4]=false;
                    etCcode.setText("");
                    etCcode.setTextColor(Color.BLACK);
                    return;
                }

            }
        });

        tvTitle.setText("注 册");
        ivSearch.setVisibility(View.INVISIBLE);
        ivJname.setVisibility(View.INVISIBLE);
        ivJrepwd.setVisibility(View.INVISIBLE);
        ivJphone.setVisibility(View.INVISIBLE);
        ivJpwd.setVisibility(View.INVISIBLE);
        ivTips.setVisibility(View.INVISIBLE);
        tvTips.setText("");
      //  btSendCode.setClickable(false);
    }
    private void inite(){
        tvTips = (TextView)findViewById(R.id.tv_register_tips);
        tvTitle = (TextView)findViewById(R.id.tv_header_title);
        btSendCode = (Button)findViewById(R.id.bt_register_ccode);
        btRegister = (Button)findViewById(R.id.bt_register_r);
        btCancel = (Button)findViewById(R.id.bt_register_c);
        ivJname = (ImageView)findViewById(R.id.iv_register_name_judge);
        ivJphone = (ImageView)findViewById(R.id.iv_register_phone_judge);
        ivJpwd = (ImageView)findViewById(R.id.iv_register_pwd_judge);
        ivJrepwd = (ImageView)findViewById(R.id.iv_register_repwd_judge);
        ivReturn = (ImageView)findViewById(R.id.iv_header_return);
        ivSearch = (ImageView)findViewById(R.id.iv_header_search);
        ivTips = (ImageView)findViewById(R.id.iv_register_tips);
        etRepwd = (EditText)findViewById(R.id.et_register_repwd);
        etPwd = (EditText)findViewById(R.id.et_register_pwd);
        etPhone = (EditText)findViewById(R.id.et_register_phone);
        etName = (EditText)findViewById(R.id.et_register_name);
        etCcode = (EditText)findViewById(R.id.et_register_ccode);
    }

    private boolean isLetterOrDigitorChiese(String tem){
        for(int i=0;i<tem.length();++i){
            if(!(Character.isLetterOrDigit(tem.charAt(i))||isChinese(tem.charAt(i)))){
                return false;
            }
        }
        return true;
    }
    private boolean isLetterOrDigit(String tem) {
        for (int i = 0; i < tem.length(); ++i) {
            if (!Character.isLetterOrDigit(tem.charAt(i))) {
                return false;
            } else if (isChinese(tem.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isDigit(String tem){
        for(int i=0;i<tem.length();++i){
            if(!Character.isDigit(tem.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public  boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_register_ccode:
                    sendSMS();
                    break;
                case R.id.bt_register_r:
                    regisiter();
                    break;
                case R.id.bt_register_c:
                    finish();
                    break;
                case R.id.iv_header_return:
                    finish();
                    break;
            }
        }
    };

    public void sendSMS(){
        if(!sendMSisSleep) {
            String phoneNumber = etPhone.getText().toString();
            code = getCode();


            if (phoneNumber.length() == 0) {
                Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TelNumMatch.isValidPhoneNumber(phoneNumber)) {
                Toast.makeText(getApplicationContext(), "手机号不正确", Toast.LENGTH_SHORT).show();
                return;
            }

            //拆分短信内容（手机短信长度限制）
            //获取短信管理器
            android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, code, null, null);
            Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
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
                            message.arg1=SLEEP;
                            message.arg2 = 60-i;
                            sleepOneMinute.sendMessage(message);
                        }
                        sendMSisSleep=false;
                    }catch (Exception e) {
                    }
                }
            }.start();
        }else {
            Toast.makeText(getApplicationContext(), "请过段时间再发", Toast.LENGTH_SHORT).show();
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
    private void regisiter(){
        if(etName.getText().length()==0
                ||first[0]==true
                ||first[1]==true
                ||first[2]==true
                ||first[3]==true
                ||first[4]==true
                ||etPwd.getText().length()==0
                ||etRepwd.getText().length()==0
                ||etPhone.getText().length()==0
                ||etCcode.getText().length()==0
                ){
            Toast.makeText(this,"请输入完整内容",Toast.LENGTH_SHORT).show();
            return;
        }
        if(CheckInput.isRightName(etName.getText().toString())
                ==CheckInput.NAME_OVERLENGTH){
            Toast.makeText(this,"用户名长度应该在1-10之间",Toast.LENGTH_SHORT).show();
            return;
        }
        if(CheckInput.isRightPassword(etPwd.getText().toString())
                ==CheckInput.PASSWORD_NOTFITLENGTH){
            Toast.makeText(this,"密码长度应该在6-12之间",Toast.LENGTH_SHORT).show();
            return;
        }
        if(CheckInput.isRightPassword(etPwd.getText().toString())
                ==CheckInput.PASSWORD_NOTFITLENGTH){
            Toast.makeText(this,"密码不能包含除数字与字母以外的字符",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("1111111111111",etRepwd.getText().toString()+" "+etPwd.getText().toString());
        if(!etRepwd.getText().toString().equals(etPwd.getText().toString())){
            Toast.makeText(this,"两次密码不相同",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!hasPhoneSend){
            Toast.makeText(this,"请发送验证码",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!code.equals(etCcode.getText().toString())){
            Toast.makeText(this,"验证码错误",Toast.LENGTH_SHORT).show();
            return;
        }
        final UserAccount userAccount = new UserAccount();
        userAccount.setName(etName.getText().toString());
        userAccount.setPwd(etPwd.getText().toString());
        userAccount.setBphone(etPhone.getText().toString());
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message message=Message.obtain();
                Result r = Connect.postRegister(userAccount);
                message.arg1=REGISTER;
                message.arg2=r.getCode();
                sleepOneMinute.handleMessage(message);
            }
        }.start();



    }
}
