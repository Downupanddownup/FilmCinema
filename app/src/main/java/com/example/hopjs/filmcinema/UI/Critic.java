package com.example.hopjs.filmcinema.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopjs.filmcinema.Adapter.CriticAdapter;
import com.example.hopjs.filmcinema.Data.Result;
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
    CriticAdapter.Critic critic;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg2==Result.RESULT_OK){
                Intent intent = new Intent();
                intent.putExtra("criticId",critic.getId());
                intent.putExtra("name",critic.getName());
                intent.putExtra("portraitName",critic.getPortraitName());
                intent.putExtra("filmId",critic.getFilmId());
                intent.putExtra("scord",critic.getScord());
                intent.putExtra("content",critic.getContent());
                intent.putExtra("date",critic.getDate());
                intent.putExtra("praiseNum","0");
                setResult(RESULT_OK,intent);
//                Looper.prepare();
                Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_SHORT).show();
 //               Looper.loop();
            }else {
                setResult(RESULT_CANCELED,null);
//                Looper.prepare();
                Toast.makeText(getApplicationContext(),"发送失败，请检查您的网络",Toast.LENGTH_SHORT).show();
  //              Looper.loop();
            }
            finish();
        }
    };

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
                critic = new CriticAdapter.Critic();
                MyApplication myApplication = (MyApplication)getApplicationContext();
                critic.setId(myApplication.userAccount.getUserId());
                critic.setName(myApplication.userAccount.getName());
                critic.setPortraitName(myApplication.userAccount.getPortraitName());
                critic.setFilmId(filmId);
                critic.setScord(rbScord.getRating()*2);
                critic.setContent(etCritic.getText().toString());
                Time time =new Time("GMT");
                time.setToNow();
                critic.setDate(""+time.year+time.month+time.monthDay+(time.hour+8)+time.minute+"");

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Result result = Connect.postCritic(critic);
                        Message message = Message.obtain();
                        message.arg2=result.getCode();
                        handler.sendMessage(message);
                    }
                }.start();


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
