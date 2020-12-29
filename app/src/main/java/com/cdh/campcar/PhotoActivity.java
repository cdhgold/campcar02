package com.cdh.campcar;

import com.cdh.campcar.Fragment.PhotoEdFragment;
import com.cdh.campcar.Fragment.PhotoFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/*
상세보기화면에서 이동 ( viewFragment ) 이미지 확대용
 */
public class PhotoActivity extends AppCompatActivity  {
    private PhotoFragment photoFragment = new PhotoFragment();      // 이미지 확대보기
    private PhotoEdFragment photoEdFragment = new PhotoEdFragment(); // 이미지수정
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_main);
        Intent vintent = getIntent(); // gets the previously created intent
        String imgpos = vintent.getStringExtra("pos"); // 이미지위치
        Bundle param = new Bundle();
        param.putString("imgpos", imgpos );
        photoFragment.setArguments(param);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.phoFrame, photoFragment).commitAllowingStateLoss();

    }
    //fragment 전환
    public void replaceFragment(Fragment fragment ) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.phoFrame, fragment).commitAllowingStateLoss();
    }
    @Override
    public void onBackPressed() {
        //ProductBean.setDimg(null);
        finish(); // 현 activity종료
    }
}
