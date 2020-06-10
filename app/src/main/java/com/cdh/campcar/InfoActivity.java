package com.cdh.campcar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.AdFragment;
import com.cdh.campcar.Fragment.InfoFragment;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
안내, 광고
 */
public class InfoActivity extends AppCompatActivity  {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private InfoFragment infoFragment = new InfoFragment();
    private AdFragment adFragment = new AdFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_main); // R.layout.info_fragment
        // 첫 화면 지정 - info aa
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.infoFrameLayout, infoFragment).commitAllowingStateLoss();
        // bottom menu 설정
        BottomNavigationView bottomNav = findViewById(R.id.info_bottom_navigation_view);
        bottomNav.setOnNavigationItemSelectedListener(new InfoActivity.ItemSelectListener());
    }
    private class ItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.nav_info: // 첫화면 (메인 )
                    transaction.replace(R.id.infoFrameLayout, infoFragment).commitAllowingStateLoss();

                    break;
                case R.id.nav_ad: // 첫화면 (메인 )
                    transaction.replace(R.id.infoFrameLayout, adFragment).commitAllowingStateLoss();

                    break;
            }
            return true;
        }
    }
    /*
    click시마다 이미지 전환
    fragment로 처리
    nav_info, nav_ad
     */

    @Override
    public void onBackPressed() {

        finish(); // 현 activity종료
    }
}
