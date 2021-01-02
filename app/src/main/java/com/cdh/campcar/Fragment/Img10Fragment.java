package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdh.campcar.R;
import com.cdh.campcar.ViewPagerActivity;

/*
 이미지 02
 */
public class Img10Fragment extends Fragment implements View.OnClickListener {

    public static ImageView img01;
    private static Img10Fragment img01Frg = null;
    public static Img10Fragment getInstance() {
        if(img01Frg == null) {
            img01Frg = new Img10Fragment();
        }
        return img01Frg;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_img10, container, false);
        img01  = view.findViewById(R.id.carImg10 );        //  get 한 이미지를 보여준다

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");

    }
    // 카메라, 앨범 에서 이미지 가져오기
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }
    @Override
    public void onStart(){
        super.onStart();
        getImg();
    }
    public void getImg(){
        Log.e("img01 ", "getImg : " + img01);
        ((ViewPagerActivity)getActivity()).setAimg1(img01,9);

    }
}