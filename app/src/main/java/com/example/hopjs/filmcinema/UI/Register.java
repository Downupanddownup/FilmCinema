package com.example.hopjs.filmcinema.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

/**
 * Created by Hopjs on 2016/10/15.
 */

public class Register extends AppCompatActivity {
    private ImageView ivReturn,ivSearch;
    private TextView tvTitle,tvTips;
    private EditText etName,etPwd,etRepwd,etPhone,etCcode;
    private ImageView ivJname,ivJpwd,ivJrepwd,ivJphone,ivTips;
    private Button btSendCode,btRegister,btCancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        inite();
        ivReturn.setOnClickListener(listener);
        btSendCode.setOnClickListener(listener);
        btRegister.setOnClickListener(listener);
        btCancel.setOnClickListener(listener);
        etName.addTextChangedListener(nameWatcher);
        etPhone.addTextChangedListener(phoneWatcher);
        etPwd.addTextChangedListener(pwdWatcher);
        etRepwd.addTextChangedListener(repwdWatcher);
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etName.getText().toString().equals("用户名")){
                    etName.setText("");
                    etName.setTextColor(Color.BLACK);
                }
            }
        });
        etRepwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etRepwd.getText().toString().equals("确认密码")){
                    etRepwd.setText("");
                    etRepwd.setTextColor(Color.BLACK);
                }
            }
        });
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etPhone.getText().toString().equals("手机号")){
                    etPhone.setText("");
                    etPhone.setTextColor(Color.BLACK);
                }
            }
        });
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etPwd.getText().toString().equals("密码")){
                    etPwd.setText("");
                    etPwd.setTextColor(Color.BLACK);
                }
            }
        });
        etCcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etCcode.getText().toString().equals("验证码")){
                    etCcode.setText("");
                    etCcode.setTextColor(Color.BLACK);
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
        btSendCode.setClickable(false);
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
    private TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String tem = etName.getText().toString();
            if(!isLetterOrDigitorChiese(tem)||tem.length()==0){
                ivJname.setVisibility(View.VISIBLE);
                ivJname.setImageResource(R.drawable.wrong);
                ivTips.setVisibility(View.VISIBLE);
                if(tem.length()!=0) {
                    tvTips.setText("存在特殊字符！");
                }else {
                    tvTips.setText("用户名不能为空！");
                }
            }else {
                ivJname.setVisibility(View.VISIBLE);
                ivJname.setImageResource(R.drawable.right);
                ivTips.setVisibility(View.INVISIBLE);
                tvTips.setText("");
            }
        }
    };
    private TextWatcher pwdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String tem = etPwd.getText().toString();
            if(tem.length() < 8){
                ivJpwd.setVisibility(View.VISIBLE);
                ivJpwd.setImageResource(R.drawable.wrong);
                ivTips.setVisibility(View.VISIBLE);
                tvTips.setText("密码不能少于8位！");
                return;
            }
            if(!isLetterOrDigit(tem)){
                ivJpwd.setVisibility(View.VISIBLE);
                ivJpwd.setImageResource(R.drawable.wrong);
                ivTips.setVisibility(View.VISIBLE);
                tvTips.setText("密码只能包含英文与数字！");
            }else {
                ivJpwd.setVisibility(View.VISIBLE);
                ivJpwd.setImageResource(R.drawable.right);
                ivTips.setVisibility(View.INVISIBLE);
                tvTips.setText("");
            }
        }
    };
    private TextWatcher repwdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String tem = etRepwd.getText().toString();
            String pwd = etPwd.getText().toString();
            if(tem.length()!=0) {
                if (!pwd.equals(tem)) {
                    ivJrepwd.setVisibility(View.VISIBLE);
                    ivJrepwd.setImageResource(R.drawable.wrong);
                    ivTips.setVisibility(View.VISIBLE);
                    tvTips.setText("两次密码不相同！");
                } else {
                    ivJrepwd.setVisibility(View.VISIBLE);
                    ivJrepwd.setImageResource(R.drawable.right);
                    ivTips.setVisibility(View.INVISIBLE);
                    tvTips.setText("");
                }
            }
        }
    };
    private TextWatcher phoneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String tem = etPhone.getText().toString();
            if(tem.length()!=11 || !isDigit(tem)||tem.length()==0){
                ivJphone.setVisibility(View.VISIBLE);
                ivJphone.setImageResource(R.drawable.wrong);
                ivTips.setVisibility(View.VISIBLE);
                if(tem.length()!=0){
                    tvTips.setText("号码格式不正确！");
                }else {
                    tvTips.setText("号码不能为空！");
                }

                btSendCode.setClickable(false);
            }else {
                ivJphone.setVisibility(View.VISIBLE);
                ivJphone.setImageResource(R.drawable.right);
                ivTips.setVisibility(View.INVISIBLE);
                tvTips.setText("");
                btSendCode.setClickable(true);
            }
        }
    };
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
                    Test.showToast(Register.this,"你点击了发送按钮");
                    break;
                case R.id.bt_register_r:
                    Test.showToast(Register.this,"你点击了注册按钮");
                    break;
                case R.id.bt_register_c:
                    Test.showToast(Register.this,"你点击了取消按钮");
                    break;
                case R.id.iv_header_return:
                    finish();
                    break;
            }
        }
    };
}
