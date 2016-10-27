package com.example.hopjs.filmcinema;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.Fragment.CinemaFragment;
import com.example.hopjs.filmcinema.UI.Fragment.FilmFragment;
import com.example.hopjs.filmcinema.UI.Fragment.HomePageFragment;
import com.example.hopjs.filmcinema.UI.Fragment.PcenterFragment;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

/*
* 命名规范：
* 资源文件
*   1，全部小写
*   2，名称能够体现意义
*   3，_用来区分从属，前者表示母体
*   4，用0来分割名词
* xml文件变量
*   1,控件缩写作为开头
*   2，用_连接各部分
*   3，第二部分为xml文件名
*   4，第三部分表示控件含义
*   5，第三部分采用首字母小写，余单词首字母大写形式
* java文件变量
*   1，类名，采用全单词首字母大写形式
*   2，普通变量，采用首字母小写，余单词首字母大写形式
*   3，控件变量，可与控件同名
*   4，常量，全部大写
*   5，方法，采用首字母小写，余单词首字母大写形式，以动词开头
* */

public class MainActivity extends AppCompatActivity {

    public static final int HOMEPAGE = 0;
    public static final int FILM = 1;
    public static final int CINEMA = 2;
    public static final int PCENTER = 3;
    private ViewPager viewPager;
    private PagerBottomTabLayout pagerBottomTabLayout;
    private FragAdapter fragAdapter;
    private Controller controller;
    private boolean vpLock = false;
    private boolean cLock = false;
    private boolean firstRun = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.vp_view);
        final List<Fragment> fragments = new ArrayList<>();

        HomePageFragment homePageFragment = new HomePageFragment();
        FilmFragment filmFragment = new FilmFragment();
        CinemaFragment cinemaFragment = new CinemaFragment();
        PcenterFragment pcenterFragment = new PcenterFragment();
        fragments.add(homePageFragment);
        fragments.add(filmFragment);
        fragments.add(cinemaFragment);
        fragments.add(pcenterFragment);
/*
        String[] strings = {"这是主页","这是电影","这是影院","这是个人中心"};
        fragments.add(new Fragment1());
        fragments.add(new Fragment1());
        fragments.add(new Fragment1());
        fragments.add(new Fragment1());
        int i = 0;
        for(String s:strings){
            Bundle bundle = new Bundle();
            bundle.putCharSequence("text",s);
            fragments.get(i).setArguments(bundle);
            ++i;
        }*/
        fragAdapter = new FragAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(fragAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    Log.e("Mainaaaaa","/r/nthis is viewPager"+viewPager.getCurrentItem()+" cLock:"+cLock);
                    if (!cLock) {
                        vpLock = true;
                        controller.setSelect(viewPager.getCurrentItem());
                    }else {
                            cLock = false;
                    }
                    Log.e("Mainaaaaa","this is viewPager"+viewPager.getCurrentItem()+" cLock:"+cLock);
                }
            }
        });
        viewPager.setCurrentItem(HOMEPAGE);
        pagerBottomTabLayout = (PagerBottomTabLayout)findViewById(R.id.pbtl_guid);
        controller = pagerBottomTabLayout.builder()
                .addTabItem(R.drawable.home_page, "首页",getResources().getColor(R.color.colorRed))
                .addTabItem(R.drawable.film, "电影",getResources().getColor(R.color.colorAccent))
                .addTabItem(R.drawable.cinema, "影院",getResources().getColor(R.color.colorYellow))
                .addTabItem(R.drawable.person_center, "个人中心",getResources().getColor(R.color.colorGreen))
                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR | TabLayoutMode.HIDE_TEXT)
                .build();
       // controller.setSelect(0);
        controller.addTabItemClickListener(new OnTabItemSelectListener(){
            @Override
            public void onSelected(int index, Object tag) {
                if(firstRun){
                    firstRun = false;
                    return;
                }
                Log.e("Mainaaaaa","this is guid:"+index+" vpLock:"+vpLock);
                if(!vpLock){
                    cLock = true;
                    viewPager.setCurrentItem(index);
                    Log.e("Mainaaaaa","this is guid:"+index+" cLock:"+cLock);
                }else {
                    vpLock = false;
                }
                Log.e("Mainaaaaa","this is guid:"+index+" vpLock:"+vpLock);
                /*Log.e("Main","this is guid"+index);
                viewPager.setCurrentItem(index);*/
            }

            @Override
            public void onRepeatClick(int index, Object tag) {

            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        int location = HOMEPAGE;
        if(getIntent()!= null) {
            location = getIntent().getIntExtra("location",HOMEPAGE);
        }
        viewPager.setCurrentItem(location);
        controller.setSelect(location);
    }
}
