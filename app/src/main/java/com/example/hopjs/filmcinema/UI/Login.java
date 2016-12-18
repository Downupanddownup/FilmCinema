package com.example.hopjs.filmcinema.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * Created by Hopjs on 2016/10/15.
 */

public class Login {
    private View vLogin;
    private EditText etName,etPwd;
    private Button btLogin;
    private CheckBox cbRemeber;
    private TextView tvTips;
    private ImageView ivTips;
    private Context context;
    private Activity activity;
    private NiftyDialogBuilder dialogBuilder;
    public Login(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        vLogin = LayoutInflater.from(context).inflate(R.layout.login,null,false);
        etName = (EditText)vLogin.findViewById(R.id.et_login_name);
        etPwd = (EditText)vLogin.findViewById(R.id.et_login_pwd);
        cbRemeber = (CheckBox) vLogin.findViewById(R.id.cb_login_remeber);
        tvTips = (TextView)vLogin.findViewById(R.id.tv_login_tips);
        ivTips = (ImageView) vLogin.findViewById(R.id.iv_login_tips);
        btLogin = (Button)vLogin.findViewById(R.id.bt_login_l);
        tvTips.setVisibility(View.INVISIBLE);
        ivTips.setVisibility(View.INVISIBLE);

        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etName.getText().toString().equals("用户名/手机号")){
                    etName.setText("");
                    etName.setTextColor(Color.BLACK);
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
        btLogin.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(false) {
                if(cbRemeber.isChecked()){
                    //打开文件夹并写入
                }
                UserAccount userAccount = ((MyApplication)context.
                        getApplicationContext()).userAccount;
                userAccount.setName(etName.getText().toString());
                userAccount.setPwd(etPwd.getText().toString());
                Test.showToast(context, "你触发了登录事件");
            }else {
                ivTips.setVisibility(View.VISIBLE);
                tvTips.setVisibility(View.VISIBLE);
                tvTips.setText("用户名或密码错误");
            }
        }
    };
    private void setReturnEvent(){
        dialogBuilder.dismiss();
    }
    private void setRegisterEvent(){
        Transform.toRegister(activity);
    }
    public void show(){
        clear();
        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder
                .withTitle("登 录")
                .withMessage("请输入用户名/手机号和密码")
                .withDuration(500)
                .withEffect(Effectstype.Fliph)
                .isCancelableOnTouchOutside(false)
                .setCustomView(vLogin,context)
                .withButton1Text("返回")
                .withButton2Text("注册")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setReturnEvent();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRegisterEvent();
                    }
                })
                .show();
    }
    public void clear(){
        tvTips.setVisibility(View.INVISIBLE);
        ivTips.setVisibility(View.INVISIBLE);
        cbRemeber.setChecked(false);
        etName.setText("");
        etPwd.setText("");
    }
}
