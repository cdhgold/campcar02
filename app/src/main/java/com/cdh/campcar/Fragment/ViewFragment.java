package com.cdh.campcar.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.PhotoActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.HomeGridAdapter;
import com.cdh.campcar.Recycler.ItemClickListener;
import com.cdh.campcar.Recycler.ViewAdapter;
import com.cdh.campcar.UtilActivity;

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
    private ImageView carImg01   ;
    private ImageView carImg02   ;
    private ImageView carImg03   ;
    private ImageView carImg04   ;
    private ImageView carImg05   ;
    private ImageView carImg06   ;
    private ImageView carImg07   ;
    private ImageView carImg08   ;
    private ImageView carImg09   ;
    private ImageView carImg10   ;

    private TextView carOk ;
    private ProductDBHelper dbHelper;
    private GridView gridView;
    private ViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_fragment, container, false);

        carNm	 = view.findViewById(R.id.carNm	);
        carYear  = view.findViewById(R.id.carYear );
        carKm    = view.findViewById(R.id.carKm   );
        carAddr  = view.findViewById(R.id.carAddr );
        carTel   = view.findViewById(R.id.carTel  );
        carFuel  = view.findViewById(R.id.carFuel );
        carAmt   = view.findViewById(R.id.carAmt  );
        carInfo  = view.findViewById(R.id.carInfo );
        carImg01 = view.findViewById(R.id.carimg01 );
        carImg02 = view.findViewById(R.id.carimg02 );
        carImg03 = view.findViewById(R.id.carimg03 );
        carImg04 = view.findViewById(R.id.carimg04 );
        carImg05 = view.findViewById(R.id.carimg05 );
        carImg06 = view.findViewById(R.id.carimg06 );
        carImg07 = view.findViewById(R.id.carimg07 );
        carImg08 = view.findViewById(R.id.carimg08 );
        carImg09 = view.findViewById(R.id.carimg09 );
        carImg10 = view.findViewById(R.id.carimg10 );

        //carImg01  = view.findViewById(R.id.imgBtn01 );
        //carOk= view.findViewById(R.id.carOk );

        //carImg01.setOnClickListener(this);
        //carOk.setOnClickListener(this);
        ProductBean vo = new ProductBean();
        vo = vo.getProd();
        int seq = vo.getSeq();  // pk seq
        carNm.setText(vo.getCarNm());
        carTel.setText(vo.getCarTel());
        carYear.setText(vo.getCarYear());
        carKm.setText(vo.getCarKm());
        carAddr.setText(vo.getCarAddr());
        carAmt.setText(vo.getCarAmt());
        carFuel.setText(vo.getCarFuel());
        carInfo.setText(vo.getCarInfo());

        String img01 = vo.getCarImg01();
        String img02 = vo.getCarImg02();
        String img03 = vo.getCarImg03();
        String img04 = vo.getCarImg04();
        String img05 = vo.getCarImg05();
        String img06 = vo.getCarImg06();
        String img07 = vo.getCarImg07();
        String img08 = vo.getCarImg08();
        String img09 = vo.getCarImg09();
        String img10 = vo.getCarImg10();

        Bitmap bm1 = UtilActivity.StringToBitmap(img01);
        Bitmap bm2 = UtilActivity.StringToBitmap(img02);
        Bitmap bm3 = UtilActivity.StringToBitmap(img03);
        Bitmap bm4 = UtilActivity.StringToBitmap(img04);
        Bitmap bm5 = UtilActivity.StringToBitmap(img05);
        Bitmap bm6 = UtilActivity.StringToBitmap(img06);
        Bitmap bm7 = UtilActivity.StringToBitmap(img07);
        Bitmap bm8 = UtilActivity.StringToBitmap(img08);
        Bitmap bm9 = UtilActivity.StringToBitmap(img09);
        Bitmap bm10 = UtilActivity.StringToBitmap(img10);
        Drawable cimg01 =  new BitmapDrawable( getResources(), bm1);
        Drawable cimg02 =  new BitmapDrawable( getResources(), bm2);
        Drawable cimg03 =  new BitmapDrawable( getResources(), bm3);
        Drawable cimg04 =  new BitmapDrawable( getResources(), bm4);
        Drawable cimg05 =  new BitmapDrawable( getResources(), bm5);
        Drawable cimg06 =  new BitmapDrawable( getResources(), bm6);
        Drawable cimg07 =  new BitmapDrawable( getResources(), bm7);
        Drawable cimg08 =  new BitmapDrawable( getResources(), bm8);
        Drawable cimg09 =  new BitmapDrawable( getResources(), bm9);
        Drawable cimg10 =  new BitmapDrawable( getResources(), bm10);
        cimg01 = "".equals(img01.trim()) ? getNoImg(img01): cimg01 ;
        cimg02 = "".equals(img02.trim()) ? getNoImg(img02): cimg02 ;
        cimg03 = "".equals(img03.trim()) ? getNoImg(img03): cimg03 ;
        cimg04 = "".equals(img04.trim()) ? getNoImg(img04): cimg04 ;
        cimg05 = "".equals(img05.trim()) ? getNoImg(img05): cimg05 ;
        cimg06 = "".equals(img06.trim()) ? getNoImg(img06): cimg06 ;
        cimg07 = "".equals(img07.trim()) ? getNoImg(img07): cimg07 ;
        cimg08 = "".equals(img08.trim()) ? getNoImg(img08): cimg08 ;
        cimg09 = "".equals(img09.trim()) ? getNoImg(img09): cimg09 ;
        cimg10 = "".equals(img10.trim()) ? getNoImg(img10): cimg10 ;


        carImg01.setImageDrawable(cimg01);
        carImg02.setImageDrawable(cimg02);
        carImg03.setImageDrawable(cimg03);
        carImg04.setImageDrawable(cimg04);
        carImg05.setImageDrawable(cimg05);
        carImg06.setImageDrawable(cimg06);
        carImg07.setImageDrawable(cimg07);
        carImg08.setImageDrawable(cimg08);
        carImg09.setImageDrawable(cimg09);
        carImg10.setImageDrawable(cimg10);
        Drawable[] img = {cimg01,cimg02,cimg03,cimg04,cimg05,cimg06,cimg07
                            ,cimg08,cimg09,cimg10};
        vo.setDimg(img);
        carImg01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here

                onItemClick(v,0,"");
            }
        });
        ////////////////////////////////////

        return view;
    }
    // no img 처리
    public Drawable getNoImg(String img01){
        Drawable img = ContextCompat.getDrawable(getContext(), R.drawable.noimg );
        return img;
    }

    @Override
    public void onItemClick(View v, int position,String gbn) {
        //photoActivity를 호출
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        //액티비티 시작!
        startActivity(intent);

    }
}