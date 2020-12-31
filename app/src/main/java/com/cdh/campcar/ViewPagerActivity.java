package com.cdh.campcar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.ImgPagerAdapter;
import com.cdh.campcar.Fragment.MyPagerAdapter;
import com.cdh.campcar.Fragment.Reg02Fragment;
import com.cdh.campcar.Fragment.Reg03Fragment;

import me.relex.circleindicator.CircleIndicator3;

/*
이미지 상세보기 및 수정 및 내리기 처리 .
 */
public class ViewPagerActivity extends AppCompatActivity  implements View.OnClickListener {
    FragmentStateAdapter adapterViewPager;
    ImageButton home;
    ImageButton gallery;
    ImageButton camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_img_pager);

        ViewPager2 vpPager = (ViewPager2) findViewById(R.id.imgvpager);
        adapterViewPager = new ImgPagerAdapter(this, 10);// fragment 연결
        vpPager.setAdapter(adapterViewPager);
        home = (ImageButton) findViewById(R.id.vhome); // 메인화면으로 가기
        gallery= (ImageButton) findViewById(R.id.addimg1);
        camera= (ImageButton) findViewById(R.id.addimg2);

        CircleIndicator3 indicator = (CircleIndicator3 ) findViewById(R.id.vindicator);
        indicator.setViewPager(vpPager);
        home.setOnClickListener(this);
        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);

        ProductBean vo = new ProductBean();
        vo.setProd(vo);
        vpPager.registerOnPageChangeCallback(callback);
    }
    /*
    페이지 전환시 이벤트, 현재페이지 get
     */
    private ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int pos) {
            super.onPageSelected(pos);
            Log.e("cdhgold", "OnPageChangeCallback : " + pos);
            if (pos == 0 ) {

            } else if (pos == 1) {

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
