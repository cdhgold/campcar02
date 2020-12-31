package com.cdh.campcar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import androidx.cardview.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.InfoActivity;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.PhotoActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.HomeGridAdapter;
import com.cdh.campcar.Recycler.ItemClickListener;

import java.util.ArrayList;
/*
최초 main 화면
 */
public class HomeFragment extends Fragment  implements ItemClickListener {
    private static final int INTERVAL_TIME = 3800; // 스크롤 간격
    public RequestManager mGlideRequestManager;

    private View view;
    private ViewFlipper viewFlipper;
    private RecyclerView gridView;
    private HomeGridAdapter adapter;
    private ArrayList<ProductBean> data;
    private ProductDBHelper dbHelper;

    int images[] = {
            R.drawable.slid01,
            R.drawable.slid02,
            R.drawable.slid03,
            R.drawable.slid04,
            R.drawable.slid05,
            R.drawable.slid06,
            R.drawable.slid07,
            R.drawable.slid08,
            R.drawable.slid09,
            R.drawable.slid10

    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlideRequestManager = Glide.with(this);
        ProductBean.setDimg(null);// 이미지초기화
    }

    // 메인. 슬라이드 형식 화면 절반치 광고, 아래에 상품 6개 정도 보여주기
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        viewFlipper = view.findViewById(R.id.imageSlide);

        for(int image : images)
            flipperImages(image);

        showProduct();

        return view;
    }

    private void flipperImages(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(INTERVAL_TIME);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getContext(), R.anim.slide_in_anim);
        viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_anim);
        // 이미지 click 인포화면으로
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
//photoActivity를 호출
                Intent intent = new Intent(getContext(), InfoActivity.class);
                //액티비티 시작!
                startActivity(intent);
            }
        });
    }

    private  void showProduct() {
        dbHelper = ProductDBHelper.getInstance(getContext());

        data = dbHelper.getRandomProduct();
        ProductBean.setPlist(data);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);

        gridView = view.findViewById(R.id.gridView);
        gridView.setLayoutManager(layoutManager);

        adapter = new HomeGridAdapter(getContext(), data, this,mGlideRequestManager);
        gridView.setAdapter(adapter);
    }
    // 상세보기
    @Override
    public void onItemClick(View v, int position,String gbn) {
        ProductBean procutBean = new ProductBean();
        procutBean.setProd(data.get(position));
        ViewFragment frg = new ViewFragment();
        ((MainActivity)getContext()).replaceFragment(frg);    // 새로 불러올 Fragment의 Instance를 Main으로 전달

    }
}