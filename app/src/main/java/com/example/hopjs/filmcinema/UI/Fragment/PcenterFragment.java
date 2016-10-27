package com.example.hopjs.filmcinema.UI.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

/**
 * Created by Hopjs on 2016/10/13.
 */

public class PcenterFragment extends Fragment {

    private ImageView ivReturn,ivSearch;
    private ImageView ivPortrait,ivSex;
    private TextView tvName,tvPorT;
    private CardView cvOrder,cvCritic,cvCollention,cvAccount,cvPwd,cvBphone;
    private Button btLoginout;
    private UserAccount userAccount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pcenter,container,false);
        inite(view);
        setListener();
        loadUserData();
        return  view;
    }

    private void inite(View view){
        ivReturn = (ImageView)view.findViewById(R.id.iv_pcenter_header_return);
        ivSearch = (ImageView)view.findViewById(R.id.iv_pcenter_header_search);
        ivPortrait = (ImageView)view.findViewById(R.id.iv_pcenter_portrait);
        ivSex = (ImageView)view.findViewById(R.id.iv_pcenter_sex);
        tvName = (TextView)view.findViewById(R.id.tv_pcenter_name);
        tvPorT = (TextView)view.findViewById(R.id.tv_pcenter_phoneortips);
        cvOrder = (CardView)view.findViewById(R.id.cv_pcenter_activity_order);
        cvCritic = (CardView)view.findViewById(R.id.cv_pcenter_activity_critic);
        cvCollention = (CardView)view.findViewById(R.id.cv_pcenter_activity_collection);
        cvAccount = (CardView)view.findViewById(R.id.cv_pcenter_information_account);
        cvPwd = (CardView)view.findViewById(R.id.cv_pcenter_information_pwd);
        cvBphone = (CardView)view.findViewById(R.id.cv_pcenter_information_bphone);
        btLoginout = (Button)view.findViewById(R.id.bt_pcenter_loginout);
    }
    private void setListener(){
        ivReturn.setOnClickListener(listener);
        ivSearch.setOnClickListener(listener);
        cvOrder.setOnClickListener(listener);
        cvAccount.setOnClickListener(listener);
        cvBphone.setOnClickListener(listener);
        cvCollention.setOnClickListener(listener);
        cvCritic.setOnClickListener(listener);
        cvPwd.setOnClickListener(listener);
        btLoginout.setOnClickListener(listener);
    }
    private void loadUserData(){
        Bitmap bitmap;
        userAccount = ((MyApplication)getActivity().getApplicationContext()).userAccount;
        if(userAccount.isLogin()){
            tvName.setText(userAccount.getName());
            tvPorT.setText(userAccount.getBphone());
            if(userAccount.getSex() == "男"){
                ivSex.setImageResource(R.drawable.man);
            }else {
                ivSex.setImageResource(R.drawable.woman);
            }
            if(userAccount.isSetportrait()){
                bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                        getBitmap(userAccount.getPortraitId(),getActivity().getApplicationContext(),0.1);
                ivPortrait.setImageBitmap(bitmap);
            }else {
                bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                        getBitmap(R.drawable.defaultportrait,getActivity().getApplicationContext(),0.1);
                ivPortrait.setImageBitmap(bitmap);
            }
            btLoginout.setText("登  出");
        }else {
            tvName.setText("");
            ivSex.setVisibility(View.INVISIBLE);
            tvPorT.setText("请 登 录");
            bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                    getBitmap(R.drawable.smile,getActivity().getApplicationContext(),0.1);
            ivPortrait.setImageBitmap(bitmap);
            btLoginout.setText("登  录");
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_pcenter_header_return:
                    Test.showToast(getContext(),"这是return");
                    break;
                case R.id.iv_pcenter_header_search:
                    Transform.toSearch(getActivity());
                    break;
                case R.id.cv_pcenter_activity_order:
                    Transform.toTicketRecord(getActivity());
                    break;
                case R.id.cv_pcenter_activity_critic:
                    Transform.toMyCritic(getActivity());
                    break;
                case R.id.cv_pcenter_activity_collection:
                    Transform.toCollection(getActivity());
                    break;
                case R.id.cv_pcenter_information_account:
                    Test.showToast(getContext(),"账号编辑");
                    break;
                case R.id.cv_pcenter_information_pwd:
                    Test.showToast(getContext(),"修改密码");
                    break;
                case R.id.cv_pcenter_information_bphone:
                    Test.showToast(getContext(),"修改绑定手机号");
                    break;
                case R.id.bt_pcenter_loginout:
                    Test.showToast(getContext(),"推出登录");
                    break;
            }
        }
    };
}
