package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.ItemClickListener;

/*
 app안내
 */
public class AdFragment extends Fragment  implements ItemClickListener {

    private View view;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad, container, false);


        return view;
    }

    @Override
    public void onItemClick(View v, int position, String gbn) {

    }
}