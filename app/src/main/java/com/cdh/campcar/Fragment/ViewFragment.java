package com.cdh.campcar.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cdh.campcar.Data.PreferenceManager;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.PhotoActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.HomeGridAdapter;
import com.cdh.campcar.Recycler.ItemClickListener;
import com.cdh.campcar.Recycler.ViewAdapter;
import com.cdh.campcar.UtilActivity;
import com.cdh.campcar.ViewPagerActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/*
캠핑카 상세보기 , 처음화면에서 이동
 */
public class ViewFragment extends Fragment  implements ItemClickListener {
    // 이름, 아이디, 이메일, 성별, 나이 표시해주기
    // 정보수정? 시간 남으면 하기 -> 정보수정 글씨를 누르면 팝업화면으로 수정하고 확인하면 refresh

    private TextView carNm;
    private TextView carYear 	;
    private TextView carKm     ;
    private TextView carAddr   ;
    private TextView carTel    ;
    private TextView carFuel   ;
    private TextView carAmt    ;
    private TextView carInfo   ;
    private TextView regdt   ;
    private ImageView carImg01   ;
    private ImageView carImg02   ;
    private ImageView carImg03   ;
    private ImageView carImg04   ;
    private ImageView carImg05   ;
    private ImageView carImg06   ;
    private ImageView carImg07   ;


    private TextView carOk ;
    private ProductDBHelper dbHelper;
    private GridView gridView;
    private ViewAdapter adapter;

    ProductBean vo = new ProductBean();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_fragment, container, false);

        carNm	 = view.findViewById(R.id.carNm	);
        carYear  = view.findViewById(R.id.carYear );
        carKm    = view.findViewById(R.id.carKm   );
        carAddr  = view.findViewById(R.id.carAddr );
        carTel   = view.findViewById(R.id.carTel  );
        carFuel  = view.findViewById(R.id.carFuel );
        carAmt   = view.findViewById(R.id.carAmt  );
        regdt   = view.findViewById(R.id.regdt  );
        carInfo  = view.findViewById(R.id.carInfo );
        carImg01 = view.findViewById(R.id.carimg01 );
        carImg02 = view.findViewById(R.id.carimg02 );
        carImg03 = view.findViewById(R.id.carimg03 );
        carImg04 = view.findViewById(R.id.carimg04 );
        carImg05 = view.findViewById(R.id.carimg05 );
        carImg06 = view.findViewById(R.id.carimg06 );
        carImg07 = view.findViewById(R.id.carimg07 );


        vo = vo.getProd();
        if(vo == null){
            PreferenceManager prefm = new PreferenceManager();
            String pos = prefm.getString(getContext(),"pos" );// 이미지 번호를 check한다.
            dbHelper = ProductDBHelper.getInstance(getContext());
            ArrayList<ProductBean>  data = dbHelper.getRandomProduct();
            ProductBean pvo = ProductBean.getInstance();
            pvo.setProd(data.get(Integer.parseInt(pos) ));
 Log.d("view  vo is null ","cdh pos "+pos)   ;
            vo = pvo.getProd();
        }
//        int seq = vo.getSeq();  // pk seq
// Log.d("campcar seq2 ",String.valueOf(seq))   ;
        carNm.setText(vo.getCarNm());
        carTel.setText(vo.getCarTel());
        carYear.setText(vo.getCarYear());
        carKm.setText(vo.getCarKm());
        carAddr.setText(vo.getCarAddr());
        DecimalFormat formatter = new DecimalFormat("###,###");
        String reg = "^[0-9]+$";
        String amt = vo.getCarAmt();
        if(amt.matches(reg)) {
            amt = formatter.format(Double.parseDouble(amt));
        }
        carAmt.setText(amt);
        carFuel.setText(vo.getCarFuel());
        carInfo.setText(vo.getCarInfo());
        regdt.setText(vo.getUpdDt());
        String img01 = vo.getCarImg01();
        String img02 = vo.getCarImg02();
        String img03 = vo.getCarImg03();
        String img04 = vo.getCarImg04();
        String img05 = vo.getCarImg05();
        String img06 = vo.getCarImg06();
        String img07 = vo.getCarImg07();


        Glide.with(getContext()).load("file:///"+getContext().getFilesDir()+"/"+img01).into(carImg01);
        if("".equals(img02)){
            carImg02.setImageDrawable(getNoImg());
        }else {
            Glide.with(getContext()).load("file:///" + getContext().getFilesDir() + "/" + img02).into(carImg02);
        }
        if("".equals(img03)){
            carImg03.setImageDrawable(getNoImg());
        }else {
            Glide.with(getContext()).load("file:///"+getContext().getFilesDir()+"/"+img03).into(carImg03);
        }
        if("".equals(img04)){
            carImg04.setImageDrawable(getNoImg());
        }else {
            Glide.with(getContext()).load("file:///"+getContext().getFilesDir()+"/"+img04).into(carImg04);
        }
        if("".equals(img05)){
            carImg05.setImageDrawable(getNoImg());
        }else {
            Glide.with(getContext()).load("file:///"+getContext().getFilesDir()+"/"+img05).into(carImg05);
        }

        if("".equals(img06)){
            carImg06.setImageDrawable(getNoImg());
        }else {
            Glide.with(getContext()).load("file:///"+getContext().getFilesDir()+"/"+img06).into(carImg06);
        }
        if("".equals(img07)){
            carImg07.setImageDrawable(getNoImg());
        }else {
            Glide.with(getContext()).load("file:///"+getContext().getFilesDir()+"/"+img07).into(carImg07);
        }

        String[] img = {img01,img02,img03,img04,img05,
                         img06,img07 };
        vo.setDimg(img);
        carImg01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemClick(v,0,"");
            }
        });
        carImg02.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemClick(v,1,"");
            }
        });
        carImg03.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemClick(v,2,"");
            }
        });
        carImg04.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemClick(v,3,"");
            }
        });
        carImg05.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemClick(v,4,"");
            }
        });
        carImg06.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemClick(v,5,"");
            }
        });
        carImg07.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onItemClick(v,6,"");
            }
        });

        ////////////////////////////////////

        return view;
    }
    // no img 처리
    public Drawable getNoImg(){
        Drawable img = ContextCompat.getDrawable(getContext(), R.drawable.noimg );
        return img;
    }
    // pos 이미지 순번, 이미지클릭시 이미지 상세보기로 이동 - ViewPagerActivity 로 이동
    @Override
    public void onItemClick(View v, int pos,String gbn) {
        //photoActivity를 호출
        //Intent intent = new Intent(getContext(), PhotoActivity.class); // 확대해서 보기
        Intent intent = new Intent(getContext(), ViewPagerActivity.class); // 확대해서 보기
        //액티비티 시작!
        intent.putExtra("pos",String.valueOf(pos));
        startActivity(intent);

    }
    @Override
    public void onResume(){
        super.onResume();
        vo = vo.getProd();


    }
    @Override
    public void onStart(){
        super.onStart();


    }
}