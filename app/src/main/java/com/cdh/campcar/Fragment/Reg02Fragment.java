package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdh.campcar.R;

/*
캠핑카등록1 -  이미지 
 */
public class Reg02Fragment extends Fragment implements View.OnClickListener {
    private EditText carNm;
    private EditText carEmail;
    private EditText carYear 	;
    private EditText carKm     ;

    private static Reg02Fragment reg01Frg = null;
    public static Reg02Fragment getInstance() {
        if(reg01Frg == null) {
            reg01Frg = new Reg02Fragment();
        }
       // Bundle args = new Bundle();
       // args.putInt("someInt", page);
      //  args.putString("someTitle", title);
       // reg01Frg.setArguments(args);
        return reg01Frg;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reg02, container, false);

        carNm	 = view.findViewById(R.id.carNm	);
        carEmail	 = view.findViewById(R.id.email	);
        carYear  = view.findViewById(R.id.carYear );
        carKm    = view.findViewById(R.id.carKm   );
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");


    }
    @Override
    public void onStop() {// 안타네
        super.onStop();
        String scarNm =  carNm.getText().toString();
        String scarEmail =  carEmail.getText().toString();
        String scarYear = carYear.getText().toString();
        String scarKm   = carKm.getText().toString();
        Log.e("cdhgold","scarNm : " + scarNm);
    }

    @Override
    public void onClick(View v) {

    }
}