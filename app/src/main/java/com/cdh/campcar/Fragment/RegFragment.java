package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;

import me.relex.circleindicator.CircleIndicator3;

/*
캠핑카등록
 */
public class RegFragment extends Fragment implements View.OnClickListener {
    // 이름, 아이디, 이메일, 성별, 나이 표시해주기
    // 정보수정? 시간 남으면 하기 -> 정보수정 글씨를 누르면 팝업화면으로 수정하고 확인하면 refresh

    private EditText carNm;
    private EditText carEmail;
    private EditText carYear 	;
    private EditText carKm     ;
    private EditText carAddr   ;
    private EditText carTel    ;
    private EditText carFuel   ;
    private EditText carAmt    ;
    private EditText carInfo   ;
    private ImageButton carImg01  ;
    private TextView carOk ;
    private ProductDBHelper dbHelper;
    FragmentStateAdapter adapterViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager, container, false);
        ViewPager2 vpPager = (ViewPager2) view.findViewById(R.id.vpPager);
        adapterViewPager = MainActivity.getInstance((MainActivity)getActivity() );
        vpPager.setAdapter(adapterViewPager);

        CircleIndicator3 indicator = (CircleIndicator3 ) view.findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}