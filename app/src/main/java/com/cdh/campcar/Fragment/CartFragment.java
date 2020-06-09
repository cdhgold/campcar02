package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.CartRecyclerAdapter;
import com.cdh.campcar.Recycler.ItemClickListener;

import java.util.ArrayList;
/*
최신목록으로 보기
 */
public class CartFragment extends Fragment implements ItemClickListener {
    private View view;
    private RecyclerView recyclerView;
    private CartRecyclerAdapter adapter;
    private ArrayList<ProductBean> data;
    private ProductDBHelper dbHelper;
    public RequestManager mGlideRequestManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlideRequestManager = Glide.with(this);

    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cart_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showProduct();
    }

    private void showProduct() {
        dbHelper = ProductDBHelper.getInstance(getContext());
        data = dbHelper.getAllProduct() ;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartRecyclerAdapter(getContext(), data, this,mGlideRequestManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View v, int pos,String gbn) {
// 상세보기로 가기

        ProductBean procutBean = new ProductBean();
        procutBean.setProd(data.get(pos));
        ViewFragment frg = new ViewFragment();
        ((MainActivity)getContext()).replaceFragment(frg);    // 새로 불러올 Fragment의 Instance를 Main으로 전달

    }
}
