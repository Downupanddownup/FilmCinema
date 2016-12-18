package com.example.hopjs.filmcinema.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/12/17.
 */

public class RvCityAdapter extends RecyclerView.Adapter<RvCityAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFirstLettle;
        private LinearLayout llCityCan;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFirstLettle = (TextView)itemView.findViewById(R.id.tv_city_item_first0lettle);
            llCityCan = (LinearLayout)itemView.findViewById(R.id.ll_city_item_can);
        }
    }
    public static class City{
        private String id;
        private String name;
        private String lettle;

        public String getId() {
            return id;
        }

        public String getLettle() {
            return lettle;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLettle(String lettle) {
            this.lettle = lettle;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    private class CityList{
        public String firstLettle;
        public ArrayList<City> cities;
        public int sum;
        public CityList() {
            cities = new ArrayList<>();
            sum=0;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view,String cityId);
    }

    private ArrayList<CityList> cities;
    private Context context;
    private OnItemClickListener listener;

    public RvCityAdapter(Context context,ArrayList<City> cities,OnItemClickListener listener) {
        this.context = context;
        this.cities = getCityList(cities);
        this.listener = listener;
    }

    private ArrayList<CityList> getCityList(ArrayList<City> cities){
        ArrayList<CityList> cityArrayList = new ArrayList<>();
        char t = 'A';
        int a = (int) t;
        for(int i=0;i<26;++i){
            CityList tem = new CityList();
            t = (char)(a+i);
            tem.firstLettle = t+"";
            cityArrayList.add(tem);
        }
        for(int i=0;i<26;++i){
            for(int j=0;j<cities.size();++j){
                if(cityArrayList.get(i).firstLettle.equals(cities.get(j).getLettle())){
                    cityArrayList.get(i).cities.add(cities.get(j));
                    cityArrayList.get(i).sum++;
                }
            }
        }
        for(int i=0;i<cityArrayList.size();++i){
            if(cityArrayList.get(i).sum==0){
                cityArrayList.remove(i);
                i--;
            }
        }
        return  cityArrayList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.llCityCan.removeAllViews();
        holder.tvFirstLettle.setText(cities.get(position).firstLettle);
        LinearLayout linearLayout =null;
        for(int i=0;i<cities.get(position).sum;++i){
            if(i%3==0){
                linearLayout = getLinearLayout();
                holder.llCityCan.addView(linearLayout);
            }
            TextView textView = getTextView();
            textView.setTag(cities.get(position).cities.get(i).getId());
            textView.setText(cities.get(position).cities.get(i).getName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,v.getTag()+"");
                }
            });
            linearLayout.addView(textView);
        }
    }

    private TextView getTextView(){
        TextView textView = new TextView(context);
        WindowManager manager = ((Activity)context).getWindowManager();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                manager.getDefaultDisplay().getWidth()*2/7,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(17);
        return textView;
    }

    private LinearLayout getLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(context);
        WindowManager manager = ((Activity)context).getWindowManager();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                manager.getDefaultDisplay().getWidth()*6/7,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    private int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).
                inflate(R.layout.city_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}
