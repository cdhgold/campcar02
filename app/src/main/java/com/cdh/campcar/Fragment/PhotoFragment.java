package com.cdh.campcar.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cdh.campcar.Data.GetXml;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.InfoActivity;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.PhotoActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.Recycler.HomeGridAdapter;
import com.cdh.campcar.UtilActivity;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.internal.Util;

/*
이미지확대, photoActivity  에서 사용
본인 이미지수정 및 판매완료(삭제)  처리
 */
public class PhotoFragment extends Fragment implements View.OnClickListener{
    private String[] img = ProductBean.getDimg();
    private ProductBean vo = null;
    private int seq  = 0;
    private Button btn;
    private Button btnDel;

    private ProductDBHelper dbHelper;
    private int i = 0;
    private View view;
    private String email = "";
    public static TextView txtEmail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = ProductDBHelper.getInstance(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.photo, container, false);
        PhotoView photoView = view.findViewById(R.id.photoView);
        Glide.with(getContext()).load("file:///"+getContext().getFilesDir()+"/"+img[0]).into(photoView);
        btn = view.findViewById(R.id.btnImgEdit);
        btnDel = view.findViewById(R.id.btnImgDel);// 삭제
        txtEmail = view.findViewById(R.id.email);
        photoView.setOnClickListener(this);
        vo = new ProductBean();
        vo = vo.getProd();
        email = vo.getCarEmail(); // 본인여부확인
        seq = vo.getSeq();
        btn.setOnClickListener(this);
        btnDel.setOnClickListener(this);

 Log.d("seq등록 ", String.valueOf(seq) );


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
                String chkemail = (String)txtEmail.getText();
                if("".equals(chkemail) ){
                    goNext("");
                }else{
                    if(email.equals(chkemail)) {
                        goNext("E");
                    }else{
                        UtilActivity.showAlim("이메일확인하세요!",getContext() );
                        txtEmail.setText("");
                    }
                }

                break;
            case R.id.btnImgDel:   // 이미지수정, 이미지선택 서버전송,
                chkemail = (String)txtEmail.getText();
                if("".equals(chkemail) ){
                    goNext("");
                }else{
                    if(email.equals(chkemail)) {
                        goNext("D");
                    }else{
                        UtilActivity.showAlim("이메일확인하세요!",getContext() );
                        txtEmail.setText("");
                    }
                }
                break;
        }

    };
    // 이메일 체크
    public  void goNext (String gbn) {

        try {
            if("".equals(gbn)){
                UtilActivity.inputDial(getContext() );
            }else if("E".equals(gbn)){
                Bundle args = new Bundle();
                args.putString("img", String.valueOf(i));
                PhotoEdFragment frg = new PhotoEdFragment();
                frg.setArguments(args); // param pass
                ((PhotoActivity) getContext()).replaceFragment(frg);    // 새로 불러올 Fragment의 Instance를 Main으로 전달
            }else if("D".equals(gbn)){ // 삭제
                // 서버 data삭제, local data삭제
                sendServer ss = new sendServer();
                ss.start();
            }

        } catch (Exception e) {

        }
    }// end

    class sendServer extends Thread {
        public void run() {

            try {

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";   // w3c에서 규정
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;

                try {
                    String upLoadServerUri = "http://49.50.167.90/car/carIns/delCar";

                    URL url = new URL(upLoadServerUri);

                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE",
                            "multipart/form-data");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    // conn.setRequestProperty("carImg01", "carImg01");

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    //한글깨짐
                    dos.writeBytes("Content-Disposition: form-data; name=\"seq\""+lineEnd+lineEnd+ URLEncoder.encode(String.valueOf(seq) , "UTF-8")+lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes(lineEnd);

                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);

                    // Responses from the server (code and message)
                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    if (serverResponseCode == 200) {
                        dbHelper.deleteProduct(String.valueOf(seq) );
                        //저장후 이동
                        Log.d("campCar","success");
                        Log.d("campCar",serverResponseMessage);
                        //성공시 xml다시받고 , list화면으로 이동
                        GetXml getxml = new GetXml(getContext());
                        getxml.start();
                        //이전 page로
                        Intent intent = new Intent(getContext(), MainActivity.class); // main화면으로
                        //액티비티 시작!
                        startActivity(intent);
                    }else{
                        Log.d("campCar","server err");
                        Log.d("campCar",serverResponseMessage);
                        // 실패시 alert 메세지 (인터넷확인 잠시후 재시도 )
                        UtilActivity.showAlim("다시 시도하세요!",getContext() );
                    }
                    // close the streams //
                    dos.flush();
                    dos.close();

                } catch (Exception e) {
                    // dialog.dismiss();
                    e.printStackTrace();
                }finally {
                    dos.flush();
                    dos.close();

                }
                // dialog.dismiss();



            } catch (Exception ex) {
                // dialog.dismiss();

                ex.printStackTrace();
            }




        }//run
    }//class thread end
}