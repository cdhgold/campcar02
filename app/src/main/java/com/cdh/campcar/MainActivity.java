package com.cdh.campcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.DdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cdh.campcar.Fragment.CartFragment;
import com.cdh.campcar.Fragment.HomeFragment;
import com.cdh.campcar.Fragment.MyFragment;
import com.cdh.campcar.Fragment.ShopFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private ShopFragment shopFragment = new ShopFragment();
    private CartFragment cartFragment = new CartFragment();
    private MyFragment myFragment = new MyFragment();
    private DdFragment ddFragment = new DdFragment();   // data refresh
    private int iexit = 0;      // 종료여부
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);
        bottomNav.setOnNavigationItemSelectedListener(new ItemSelectListener());


    }


    private class ItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.nav_home: // 첫화면 (메인 )
                    transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();
                    break;

                case R.id.nav_shop: // 보기 그리드
                    transaction.replace(R.id.frameLayout, shopFragment).commitAllowingStateLoss();
                    break;

                case R.id.nav_cart: // 보기 목록
                    transaction.replace(R.id.frameLayout, cartFragment).commitAllowingStateLoss();
                    break;

                case R.id.nav_my:   // data 등록
                    transaction.replace(R.id.frameLayout, myFragment).commitAllowingStateLoss();
                    break;
                case R.id.nav_data: // 자료down
                    transaction.replace(R.id.frameLayout, ddFragment).commitAllowingStateLoss();
                    break;
                case R.id.info: // 앱안내
                    transaction.replace(R.id.frameLayout, ddFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
    //fragment 전화
    public void replaceFragment(Fragment fragment ) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
    }
    @Override
    public void onBackPressed() {
         //ActivityCompat.finishAffinity(this); // app 종료

        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setMessage("정말 종료하시겠습니까?");
        d.setPositiveButton("예", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // process전체 종료
                finish();
            }
        });
        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        d.show();
    }
}
