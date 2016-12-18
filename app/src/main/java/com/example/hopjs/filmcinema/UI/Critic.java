package com.example.hopjs.filmcinema.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopjs.filmcinema.Adapter.CriticAdapter;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;

/**
 * Created by Hopjs on 2016/10/19.
 */

public class Critic extends AppCompatActivity {

    private ImageView ivReturn;
    private TextView tvTitle,tvSend;
    private  RatingBar rbScord;
    private EditText etCritic;
    private String filmId;
    private boolean first;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.critic);

        ivReturn = (ImageView)findViewById(R.id.iv_critic_return);
        tvTitle = (TextView) findViewById(R.id.tv_critic_title);
        tvSend = (TextView) findViewById(R.id.tv_critic_send);
        etCritic = (EditText) findViewById(R.id.et_critic_content);
        rbScord = (RatingBar)findViewById(R.id.rb_critic_scord);

        first = true;
        filmId = getIntent().getStringExtra("filmId");
        tvTitle.setText("评 论");
        ivReturn.setOnClickListener(listener);
        tvSend.setOnClickListener(listener);
        etCritic.setOnClickListener(listener);
    }
    private void Send(){
        if(rbScord.getRating()!=0){
            if(etCritic.getText().length()!=0&&!first){
                CriticAdapter.Critic critic = new CriticAdapter.Critic();
                MyApplication myApplication = (MyApplication)getApplicationContext();
                critic.setId(myApplication.userAccount.getUserId());
                critic.setName(myApplication.userAccount.getName());
                critic.setPortraitName(myApplication.userAccount.getPortraitName());
                critic.setFilmId(filmId);
                critic.setScord(rbScord.getRating()*2);
                critic.setContent(etCritic.getText().toString());
               // critic.setDate();
                //Connect
            }else {
                Toast.makeText(getApplicationContext(),"请输入评论",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"请给出评分",Toast.LENGTH_SHORT).show();
        }
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_critic_return:
                    finish();
                    break;
                case R.id.tv_critic_send:
                    Send();
                    break;
                case R.id.et_critic_content:
                    if(first){
                        etCritic.setText("");
                        etCritic.setTextColor(getResources().getColor(R.color.colorDark));
                        first = false;
                    }
                    break;
            }
        }
    };

}
