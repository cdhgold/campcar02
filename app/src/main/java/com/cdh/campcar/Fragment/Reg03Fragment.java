package com.cdh.campcar.Fragment;

import android.os.Bundle;
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
public class Reg03Fragment extends Fragment implements View.OnClickListener {
    private EditText carAddr   ;
    private EditText carTel    ;
    private EditText carFuel   ;
    private EditText carAmt    ;
    private static Reg03Fragment reg01Frg = null;
    public static Reg03Fragment getInstance() {
        if(reg01Frg == null) {
            reg01Frg = new Reg03Fragment();
        }
       // Bundle args = new Bundle();
       // args.putInt("someInt", page);
      //  args.putString("someTitle", title);
       // reg01Frg.setArguments(args);
        return reg01Frg;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reg03, container, false);
        carAddr  = view.findViewById(R.id.carAddr );
        carTel   = view.findViewById(R.id.carTel  );
        carFuel  = view.findViewById(R.id.carFuel );
        carAmt   = view.findViewById(R.id.carAmt  );
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");

    }
    @Override
    public void onClick(View v) {

    }
}