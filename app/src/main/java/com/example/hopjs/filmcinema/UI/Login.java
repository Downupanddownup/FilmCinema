package com.example.hopjs.filmcinema.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopjs.filmcinema.Common.CheckInput;
import com.example.hopjs.filmcinema.Common.TelNumMatch;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.Result;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.Fragment.PcenterFragment;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * Created by Hopjs on 2016/10/15.
 */

public class Login {
    public static final int SUCCESS=1;
    public static final int UNSUCCESS=2;
    public static final int NETWORK_ERRO=3;
    private View vLogin;
    private EditText etName,etPwd;
    private Button btLogin;
    private CheckBox cbRemeber;
    private TextView tvTips;
    private ImageView ivTips;
    private Context context;
    private Activity activity;
    private NiftyDialogBuilder dialogBuilder;
    private Handler handler=null;
    private int handlerMessage=0;

    public Login(Activity activity,Handler handler,int message) {
        this.context = activity;
        this.activity = activity;
        this.handler = handler;
        this.handlerMessage=message;

        vLogin = LayoutInflater.from(context).inflate(R.layout.login,null,false);
        etName = (EditText)vLogin.findViewById(R.id.et_login_name);
        etPwd = (EditText)vLogin.findViewById(R.id.et_login_pwd);
        cbRemeber = (CheckBox) vLogin.findViewById(R.id.cb_login_remeber);
        tvTips = (TextView)vLogin.findViewById(R.id.tv_login_tips);
        ivTips = (ImageView) vLogin.findViewById(R.id.iv_login_tips);
        btLogin = (Button)vLogin.findViewById(R.id.bt_login_l);
        tvTips.setVisibility(View.INVISIBLE);
        ivTips.setVisibility(View.INVISIBLE);

        etName.setTextColor(context.getResources().getColor(R.color.colorDark));
        etPwd.setTextColor(context.getResources().getColor(R.color.colorDark));

        btLogin.setOnClickListener(listener);


    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(etName.getText().length()==0){
                tvTips.setVisibility(View.VISIBLE);
                tvTips.setText("请输入手机号码");
                return;
            }
            if(etPwd.getText().length()==0) {
                tvTips.setVisibility(View.VISIBLE);
                tvTips.setText("请输入密码");
                return;
            }
            if(cbRemeber.isChecked())((MyApplication)context.getApplicationContext()).isRemebered=true;
            else ((MyApplication)context.getApplicationContext()).isRemebered=false;
            UserAccount a=((MyApplication) context.getApplicationContext()).
                    userAccount;
            a.setBphone(etName.getText().toString());
            a.setPwd(etPwd.getText().toString());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    UserAccount userAccount = Connect.postLogin(etName.getText().toString(),
                            etPwd.getText().toString());
                    Message message = Message.obtain();
                    message.arg1 = handlerMessage;
                    if (userAccount != null) {
                        if (userAccount.isSetportrait()) {
                            message.arg2=NETWORK_ERRO;
                        }else {
                            message.arg2=SUCCESS;
                            UserAccount a=((MyApplication) context.getApplicationContext()).
                                    userAccount;
                            a.setUserId(userAccount.getUserId());

                            a.setName(userAccount.getName());
                            a.setSex(userAccount.getSex());
                            a.setPortraitName(userAccount.getPortraitName());
                            a.setLogin(true);
                        }
                    }else {
                        message.arg2=UNSUCCESS;
                    }


                    if (handler != null) {
                        handler.sendMessage(message);
                    }

                }
            }.start();
                dialogBuilder.dismiss();
            };

    };
    private void setReturnEvent(){
        dialogBuilder.dismiss();
    }
    private void setRegisterEvent(){
        Transform.toRegister(activity);
    }
    public void show(){
        boolean isRemebered=((MyApplication)context.getApplicationContext()).isRemebered;
        if(isRemebered){
            UserAccount t=((MyApplication)context.getApplicationContext()).userAccount;
            etName.setText(t.getBphone());
            etPwd.setText(t.getPwd());
        }else {
            clear();
        }
        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder
                .withDialogColor(context.getResources().getColor(R.color.ButtomGuidBar))
                //.withDividerColor(context.getResources().getColor(R.color.colorGreen))
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
