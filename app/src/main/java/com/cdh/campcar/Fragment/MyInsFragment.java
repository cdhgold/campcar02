package com.cdh.campcar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdh.campcar.Data.PreferenceManager;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
캠핑카등록 2
 */
public class MyInsFragment extends Fragment implements View.OnClickListener {
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
    private ImageButton imgCamera  ;
    private ImageButton imgAlbum  ;

    private TextView save ;
    private ProductDBHelper dbHelper;
    private PreferenceManager pManager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reg_img, container, false);

        pManager = new PreferenceManager();


        save  = view.findViewById(R.id.save );
        imgCamera  = view.findViewById(R.id.imgCamera );
        imgAlbum   = view.findViewById(R.id.imgAlbum );

        save.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgAlbum.setOnClickListener(this);

        dbHelper = ProductDBHelper.getInstance(getContext());

        // 시간남으면 회원탈퇴 구현
        return view;
    }
    /*
    카메라로 이미지가져오기
     */
    public void getCameraImg(View view){



    }
    /*
    갤러리에서 이미지가져오기
     */
    public void getAlbumImg(View view){



    }
    /*
    서버에 등록
     */
    public void setSave(View view){
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL url = new URL("http://korea-com.org/foxmann/lesson01.php");       // URL 설정
            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);                         // 서버에서 읽기 모드 지정
            http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
            http.setRequestMethod("POST");         // 전송 방식은 POST

            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //--------------------------
            //   서버로 값 전송
            //--------------------------
            StringBuffer buffer = new StringBuffer();
            buffer.append("id").append("=").append(myId).append("&");                 // php 변수에 값 대입
            buffer.append("pword").append("=").append(myPWord).append("&");   // php 변수 앞에 '$' 붙이지 않는다
            buffer.append("title").append("=").append(myTitle).append("&");           // 변수 구분은 '&' 사용
            buffer.append("subject").append("=").append(mySubject);

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "EUC-KR");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();
            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
            ((TextView)(findViewById(R.id.text_result))).setText(myResult);
            Toast.makeText(MainActivity.this, "전송 후 결과 받음", 0).show();
        } catch (MalformedURLException e) {

        } catch(IOException e){

        }



        }
    // 카메라, 앨범 에서 이미지 가져오기
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgCamera:
                getCameraImg(v);
                break;
            case R.id.imgAlbum:
                getAlbumImg(v);
                break;
            case R.id.save:
                setSave(v);
                break;
        }
    }
}