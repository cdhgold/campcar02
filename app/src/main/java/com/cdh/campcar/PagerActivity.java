package com.cdh.campcar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.MyPagerAdapter;
import com.cdh.campcar.Fragment.Reg02Fragment;
import com.cdh.campcar.Fragment.Reg03Fragment;

import me.relex.circleindicator.CircleIndicator3;

/*
캠핑카등록 view pager activity
 */
public class PagerActivity extends AppCompatActivity  implements View.OnClickListener {
    FragmentStateAdapter adapterViewPager;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        ViewPager2 vpPager = (ViewPager2) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(this, 4); // fragment 연결
        vpPager.setAdapter(adapterViewPager);
        home = (ImageButton) findViewById(R.id.vhome); // 메인화면으로 가기
        CircleIndicator3 indicator = (CircleIndicator3 ) findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);
        home.setOnClickListener(this);

        vpPager.registerOnPageChangeCallback(callback);
    }
    /*
    페이지 전환시 이벤트
     */
    private ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int pos) {
            super.onPageSelected(pos);
            Log.e("cdhgold", "OnPageChangeCallback : " + pos);
            if (pos == 0 || pos == 2) {     // carNm carEmail carYear carKm
                Reg02Fragment.getReg02();
            } else if (pos == 3) {          //   carAddr carTel carFuel carAmt, 마지막 저장 처리
                Reg03Fragment.getReg03();

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vhome:
                this.finish();
                break;

        }
    }

}
