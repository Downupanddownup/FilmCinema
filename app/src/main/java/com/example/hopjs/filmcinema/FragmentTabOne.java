package com.example.hopjs.filmcinema;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Hopjs on 2016/10/11.
 */

public class FragmentTabOne extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);
        TextView textView = (TextView)view.findViewById(R.id.textView);
        textView.setText("ONE");
        return view;
    }
}
