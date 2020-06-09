package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.ItemClickListener;
import com.cdh.campcar.Recycler.SelectRecyclerAdapter;
import com.cdh.campcar.Recycler.ShopRecyclerAdapter;

import java.util.ArrayList;
/*
중고캠핑카매물보기 3열
 */
public class ShopFragment extends Fragment implements ItemClickListener {
    private static final String TYPE_TOP = "가격";
    private static final String TYPE_BOTTOM = "년식";
    private static final String TYPE_ACC = "키로수";

    private View view;
    private RecyclerView recyclerView;
    private SelectRecyclerAdapter tAdapter;
    private String[] tData; // //가격,년식,키로수
    private ShopRecyclerAdapter pAdapter;
    private ArrayList<ProductBean> pData;
    private ProductDBHelper dbHelper;
    public RequestManager mGlideRequestManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlideRequestManager = Glide.with(this);
        ProductBean.setDimg(null);// 이미지초기화
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_shop_fragment, container, false);

        showTypeSelecter();
        showProduct();

        return view;
    }

    private void showTypeSelecter() {
        //가격,년식,키로수 조건
        tData = getContext().getResources().getStringArray(R.array.type);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView = view.findViewById(R.id.typeSelectRecycler);
        recyclerView.setLayoutManager(layoutManager);
        tAdapter = new SelectRecyclerAdapter(tData, this);
        recyclerView.setAdapter(tAdapter);


    }
    /*
    매물 그리드로 보기 (3열 )
     */
    private void showProduct() {
        dbHelper = ProductDBHelper.getInstance(getContext());
        pData = dbHelper.getAllProduct();
        // 열 3개 gridview
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView = view.findViewById(R.id.productRecycler);
        recyclerView.setLayoutManager(layoutManager);
        pAdapter = new ShopRecyclerAdapter(getContext(), pData, this,mGlideRequestManager);
        recyclerView.setAdapter(pAdapter);
    }
    //필터적용
    private void showProduct(String gbn) {
        pData.clear();

        pData = dbHelper.getProductbySeq(gbn);
        pAdapter.updateData(pData);
        if(pData.size()>0){
            pAdapter.notifyItemRangeChanged(0, pData.size());
        }
    }

    @Override
    public void onItemClick(View v, int position,String gbn) {
        if("".equals(gbn) ) {
            String type = tData[position];

            if (type.equals(TYPE_TOP)) { // 가격
                showProduct("amt");
            } else if (type.equals(TYPE_BOTTOM)) { //년식
                showProduct("year");
            } else if (type.equals(TYPE_ACC)) { // 키로수
                showProduct("km");
            }
        }else {
            //이미지 click시 상세보기

            ProductBean procutBean = new ProductBean();
            procutBean.setProd(pData.get(position));
            ViewFragment frg = new ViewFragment();
            ((MainActivity)getContext()).replaceFragment(frg);    // 새로 불러올 Fragment의 Instance를 Main으로 전달

        }

    }
}