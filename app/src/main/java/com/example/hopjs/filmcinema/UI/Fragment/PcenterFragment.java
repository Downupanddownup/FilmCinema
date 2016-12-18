package com.example.hopjs.filmcinema.UI.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.EditUserAccount;
import com.example.hopjs.filmcinema.UI.Login;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hopjs on 2016/10/13.
 */

public class PcenterFragment extends Fragment {

    private final String IMAGE_TYPE = "image/*";
    private final int IMAGE_CODE = 1;   //这里的IMAGE_CODE是自己任意定义的
    private Bitmap picture;
    private String path;

    private ImageView ivReturn,ivSearch;
    private ImageView ivPortrait,ivSex;
    private TextView tvName,tvPorT;
    private CardView cvOrder,cvCritic,cvCollention,cvAccount,cvPwd,cvBphone;
    private Button btLoginout;
    private UserAccount userAccount;
    private EditUserAccount editUserAccount;
    private Login login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pcenter,container,false);
        inite(view);
        setListener();
        loadUserData();
        ivReturn.setVisibility(View.INVISIBLE);
        editUserAccount = new EditUserAccount(getActivity(),this);
        login = new Login(getActivity(),getActivity());
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
               /* bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                        getBitmap(userAccount.getPortraitId(),getActivity().getApplicationContext(),0.1);
                ivPortrait.setImageBitmap(bitmap);*/
                Connect.TemUrl temUrl = new Connect.TemUrl();
                temUrl.setConnectionType(Connect.NETWORK_FILM_PICTURE);
                temUrl.addHeader("portraitName",userAccount.getPortraitName());
                Glide.with(this)
                        .load(temUrl.getSurl())
                        .placeholder(R.drawable.x)
                        .error(R.drawable.w)
                        .into(ivPortrait);
            }else {
                /*bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                        getBitmap(R.drawable.defaultportrait,getActivity().getApplicationContext(),0.1);
                ivPortrait.setImageBitmap(bitmap);*/
                Glide.with(this)
                        .load(R.drawable.defaultportrait)
                        .into(ivPortrait);
            }
            btLoginout.setText("登  出");
        }else {
            tvName.setText("");
            ivSex.setVisibility(View.INVISIBLE);
            tvPorT.setText("请 登 录");
            /*bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                    getBitmap(R.drawable.smile,getActivity().getApplicationContext(),0.1);
            ivPortrait.setImageBitmap(bitmap);*/
            Glide.with(this)
                    .load(R.drawable.smile)
                    .into(ivPortrait);
            btLoginout.setText("登  录");
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
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
                    editUserAccount.editFinfor();
                    break;
                case R.id.cv_pcenter_information_pwd:
                    Test.showToast(getContext(),"修改密码");
                    editUserAccount.changPsw();
                    break;
                case R.id.cv_pcenter_information_bphone:
                    Test.showToast(getContext(),"修改绑定手机号");
                    editUserAccount.changePhone();
                    break;
                case R.id.bt_pcenter_loginout:
                    Test.showToast(getContext(),"推出登录");
                    if(userAccount.isLogin()){
                        btLoginout.setText("登 录");
                        userAccount.setLogin(false);
                        ((MyApplication)getActivity().getApplicationContext()).userAccount.setLogin(false);
                    }else {
                        login.show();
                    }
                    break;
            }
        }
    };

    public void openPicture() {
        // TODO Auto-generated method stub
        //使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片

        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);

        getAlbum.setType(IMAGE_TYPE);

        startActivityForResult(getAlbum, IMAGE_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            Log.e("TAG->onresult","ActivityResult resultCode error");
            return;
        }

        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getActivity().getContentResolver();

        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                picture = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                //    这里开始的第二部分，获取图片的路径：
                String[] proj = {MediaStore.Images.Media.DATA};

                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = getActivity().managedQuery(originalUri, proj, null, null, null);

                //按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                //将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();

                //最后根据索引值获取图片路径
                path = cursor.getString(column_index);

                editUserAccount.setPortrait(picture);
                editUserAccount.setPortraitPath(path);
            }catch (IOException e) {
                Log.e("TAG-->Error",e.toString());
            }
        }
    }
}
