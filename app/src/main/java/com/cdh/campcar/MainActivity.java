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
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cdh.campcar.Fragment.CartFragment;
import com.cdh.campcar.Fragment.HomeFragment;
import com.cdh.campcar.Fragment.MyFragment;
import com.cdh.campcar.Fragment.ShopFragment;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private final int MY_REQUEST_CODE = 100;// auto update
    private AppUpdateManager mAppUpdateManager; // auto update
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
        /*
        uto update check
         */
        mAppUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        // 업데이트 사용 가능 상태인지 체크
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();
        // 사용가능 체크 리스너를 달아준다
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // 업데이트가 사용 가능한 상태 (업데이트 있음) -> 이곳에서 업데이트를 요청해주자
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                                AppUpdateType.IMMEDIATE,
                                // 현재 Activity
                                MainActivity.this,
                                // 전역변수로 선언해준 Code
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e("AppUpdater", "AppUpdateManager Error", e);
                        e.printStackTrace();
                    }
                } else {

                    // 업데이트가 사용 가능하지 않은 상태(업데이트 없음) -> 다음 액티비티로 넘어가도록
                }
            }
        });// auto update end
    }
    // auto update실패시
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
//Log.d("AppUpdate", "Update flow failed! Result code: " + resultCode); // 로그로 코드 확인
                UtilActivity.showALert("앱업데이트 알림!",getApplicationContext());
                finishAffinity(); // 앱 종료
            }
        }
    }
    // auto update 앱재실행시
    @Override
    protected void onResume() {
        super.onResume();
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(
                new OnSuccessListener<AppUpdateInfo>() {
                    @Override
                    public void onSuccess(AppUpdateInfo appUpdateInfo) {
                        if (appUpdateInfo.updateAvailability()
                                == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            // 인 앱 업데이트가 이미 실행중이었다면 계속해서 진행하도록
                            try {
                                mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, MY_REQUEST_CODE);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
    private class ItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            ProductBean.setDimg(null);// 이미지초기화
            ProductBean vo = new ProductBean();
            vo.setProd(null);
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
    //fragment 전환
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
                ProductBean.setDimg(null);
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
