package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdh.campcar.R;

/*
 이미지 02
 */
public class Img02Fragment extends Fragment implements View.OnClickListener {

    private ImageView img01;
    private static Img02Fragment img01Frg = null;
    public static Img02Fragment getInstance() {
        if(img01Frg == null) {
            img01Frg = new Img02Fragment();
        }
        return img01Frg;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_img02, container, false);
        img01  = view.findViewById(R.id.carImg02 );        //  get 한 이미지를 보여준다

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

}