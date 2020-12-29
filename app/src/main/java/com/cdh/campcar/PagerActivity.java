package com.cdh.campcar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cdh.campcar.Fragment.MyPagerAdapter;

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
        adapterViewPager = new MyPagerAdapter(this, 4);
        vpPager.setAdapter(adapterViewPager);
        home = (ImageButton) findViewById(R.id.vhome);
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
