package com.example.hopjs.filmcinema.UI;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

/**
 * Created by Hopjs on 2016/10/19.
 */

public class FilmDetail extends AppCompatActivity {
    private ImageView ivReturn,ivSearch,ivBackground,ivPoster;
    private RelativeLayout relativeLayout;
    private TextView tvTitle,tvName,tvScord,tvType,tvTime,tvDate;
    private ImageButton ibtCritic,ibtLike,ibtBuy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film0detail);

        tvName = (TextView)findViewById(R.id.tv_film0detail_finfor_name);
        tvScord = (TextView)findViewById(R.id.tv_film0detail_finfor_scord);
        tvType = (TextView)findViewById(R.id.tv_film0detail_finfor_type);
        tvTime = (TextView)findViewById(R.id.tv_film0detail_finfor_time);
        tvDate = (TextView)findViewById(R.id.tv_film0detail_finfor_date);
        ivBackground = (ImageView)findViewById(R.id.iv_film0detail_finfor_background);
        ivPoster = (ImageView)findViewById(R.id.iv_film0detail_finfor_poster);
        relativeLayout = (RelativeLayout)findViewById(R.id.rl_film0detail_rl);
        tvTitle = (TextView)findViewById(R.id.tv_header_title);
        ivReturn = (ImageView)findViewById(R.id.iv_header_return);
        ivSearch = (ImageView)findViewById(R.id.iv_header_search);
        ibtBuy = (ImageButton)findViewById(R.id.ibt_film0detail_buy);
        ibtCritic = (ImageButton)findViewById(R.id.ibt_film0detail_critic);
        ibtLike = (ImageButton)findViewById(R.id.ibt_film0detail_collect);

        ivReturn.setOnClickListener(clickListener);
        ivSearch.setOnClickListener(clickListener);
        ibtLike.setOnClickListener(clickListener);
        ibtBuy.setOnClickListener(clickListener);
        ibtCritic.setOnClickListener(clickListener);

        Bitmap bitmap = ((MyApplication)getApplicationContext()).bitmapCache.
                getBitmap(R.drawable.filmbackground,getApplicationContext(),0.2);
        ivBackground.setImageBitmap(bitmap);
        bitmap = ((MyApplication)getApplicationContext()).bitmapCache.
                getBitmap(R.drawable.v,getApplicationContext(),0.05);
        ivPoster.setImageBitmap(bitmap);
        tvTitle.setText("电 影 详 情");
        relativeLayout.setFocusable(true);
        relativeLayout.setFocusableInTouchMode(true);
        relativeLayout.requestFocus();
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_header_return:
                    Test.showToast(FilmDetail.this,"你点击了返回按钮");
                    break;
                case R.id.iv_header_search:
                    Transform.toSearch(FilmDetail.this);
                    break;
                case R.id.ibt_film0detail_buy:
                    Transform.toCinemaChoose(FilmDetail.this,"3");
                    break;
                case R.id.ibt_film0detail_critic:
                    Transform.toCritic(FilmDetail.this,"3");
                    break;
                case R.id.ibt_film0detail_collect:
                    Test.showToast(FilmDetail.this,"你收藏了这部电影");
                    break;
            }
        }
    };
}
