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

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;

/*
캠핑카등록
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    // 이름, 아이디, 이메일, 성별, 나이 표시해주기
    // 정보수정? 시간 남으면 하기 -> 정보수정 글씨를 누르면 팝업화면으로 수정하고 확인하면 refresh

    private EditText carNm;
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

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, container, false);

        carNm	 = view.findViewById(R.id.carNm	);
        carYear  = view.findViewById(R.id.carYear );
        carKm    = view.findViewById(R.id.carKm   );
        carAddr  = view.findViewById(R.id.carAddr );
        carTel   = view.findViewById(R.id.carTel  );
        carFuel  = view.findViewById(R.id.carFuel );
        carAmt   = view.findViewById(R.id.carAmt  );
        carInfo  = view.findViewById(R.id.carInfo );
        carImg01  = view.findViewById(R.id.imgBtn01 );
        carOk= view.findViewById(R.id.carOk );

        carImg01.setOnClickListener(this);
        carOk.setOnClickListener(this);

        dbHelper = ProductDBHelper.getInstance(getContext());
        // ArrayList<UserBean> userArr = dbHelper.getProductbySeq(pManager.getString(getContext(), "user_id"));
/*
        userName.setText(userArr.get(0).getName());
        userName2.setText(userArr.get(0).getName());
        userId.setText(userArr.get(0).getId());
        userEmail.setText(userArr.get(0).getEmail());
        userGender.setText(userArr.get(0).getGender());
        userYears.setText(userArr.get(0).getYears());
*/

        return view;
    }
    /*
    등록버튼
     */
    public void carInsert(View view){
        String scarNm =  carNm.getText().toString();
        String scarYear = carYear.getText().toString();
        String scarKm   = carKm.getText().toString();
        String scarAddr = carAddr.getText().toString();
        String scarTel  = carTel.getText().toString();
        String scarFuel = carFuel.getText().toString();
        String scarAmt  = carAmt.getText().toString();
        String scarInfo = carInfo.getText().toString();
        if("".equals(scarNm)){
            UtilActivity.showALert("캠핑카명",getContext());
            return ;
        }
        if("".equals(scarYear)){
            UtilActivity.showALert("년식",getContext());
            return ;
        }
        if("".equals(scarKm)){
            UtilActivity.showALert("키로수",getContext());
            return ;
        }
        if("".equals(scarAddr)){
            UtilActivity.showALert("차주주소",getContext());
            return ;
        }
        if("".equals(scarTel)){
            UtilActivity.showALert("연락처",getContext());
            return ;
        }
        if("".equals(scarAmt)){
            UtilActivity.showALert("금액",getContext());
            return ;
        }
        ProductBean vo = new ProductBean();
        vo.setCarNm(scarNm);
        vo.setCarYear(scarYear);
        vo.setCarKm(scarKm);
        vo.setCarAddr(scarAddr);
        vo.setCarTel(scarTel);
        vo.setCarFuel(scarFuel);
        vo.setCarAmt(scarAmt);
        vo.setCarInfo(scarInfo);
        vo.setProd(vo);

        MyInsFragment frg = new MyInsFragment();
        ((MainActivity)getActivity()).replaceFragment(frg);    // 새로 불러올 Fragment의 Instance를 Main으로 전달


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.carOk:
                carInsert(v);
                break;
            case R.id.imgBtn01:
                carInsert(v);
                break;

        }
    }
}