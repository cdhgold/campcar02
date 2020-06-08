package com.cdh.campcar.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.InfoActivity;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.PhotoActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.HomeGridAdapter;
import com.cdh.campcar.UtilActivity;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

/*
이미지확대, photoActivity  에서 사용
 */
public class PhotoFragment extends Fragment implements View.OnClickListener{
    private String[] img = ProductBean.getDimg();
    private ProductBean vo = null;
    private int seq  = 0;
    private Button btn;
    private int i = 0;
    private View view;
    private String email = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.photo, container, false);
        PhotoView photoView = view.findViewById(R.id.photoView);
        Glide.with(getContext()).load("file:///"+getContext().getFilesDir()+"/"+img[0]).into(photoView);
        btn = view.findViewById(R.id.btnImgEdit);
        photoView.setOnClickListener(this);
        vo = new ProductBean();
        vo = vo.getProd();
        email = vo.getCarEmail(); // 본인여부확인
        btn.setOnClickListener(this);



        return view;
    }
    /*
        click시마다 이미지 전환
         */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photoView: // 이미지 확대보기
// UtilActivity.showALert(vo.getSeq() + "", this);
                PhotoView photoView = v.findViewById(R.id.photoView);
                if (i == 9) {
                    i = -1;
                }
                String tmp = img[++i];
                Drawable img = ContextCompat.getDrawable(getContext(), R.drawable.noimg);
                if ("".equals(tmp)) {
                    photoView.setImageDrawable(img);
                } else {
                    Glide.with(getContext()).load("file:///" + getContext().getFilesDir() + "/" + tmp).into(photoView);

                }
                break;
            case R.id.btnImgEdit:   // 이미지수정, 이미지선택 서버전송,
                //email
                dialTrd();
                break;
        }

    };
    // public synchronized
    public  void dialTrd () {

        try {
            boolean btmp = UtilActivity.inputDial(getContext(), email);
            if (btmp) {
                Bundle args = new Bundle();
                args.putString("img", String.valueOf(i));
                PhotoEdFragment frg = new PhotoEdFragment();
                frg.setArguments(args); // param pass
                ((PhotoActivity) getContext()).replaceFragment(frg);    // 새로 불러올 Fragment의 Instance를 Main으로 전달
            } else {
                //UtilActivity.showAlim("이메일을 확인하세요!", getContext());
            }

        } catch (Exception e) {

        }
    }// end

}