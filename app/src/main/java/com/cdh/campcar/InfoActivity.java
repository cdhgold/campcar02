package com.cdh.campcar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.InfoFragment;
import com.github.chrisbanes.photoview.PhotoView;

/*
안내, 광고
 */
public class InfoActivity extends AppCompatActivity implements View.OnClickListener{

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private InfoFragment infoFragment = new InfoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_main); // R.layout.info_fragment
        // 첫 화면 지정 - info aa
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.infoFrameLayout, infoFragment).commitAllowingStateLoss();

    }
    /*
    click시마다 이미지 전환
    fragment로 처리
     */
    @Override
    public void onClick(View v) {

    };
    @Override
    public void onBackPressed() {

        finish(); // 현 activity종료
    }
}
