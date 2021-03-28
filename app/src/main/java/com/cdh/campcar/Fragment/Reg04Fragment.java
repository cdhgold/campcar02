package com.cdh.campcar.Fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cdh.campcar.Data.GetXml;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/*
캠핑카등록1 -  이미지 
 */
public class Reg04Fragment extends Fragment implements View.OnClickListener {
    private EditText carInfo   ;
    private Button carOk1   ;

    private static Reg04Fragment reg04Frg = null;
    public static Reg04Fragment getInstance() {
        if(reg04Frg == null) {
            reg04Frg = new Reg04Fragment();
        }
       // Bundle args = new Bundle();
       // args.putInt("someInt", page);
      //  args.putString("someTitle", title);
       // reg01Frg.setArguments(args);
        return reg04Frg;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reg04, container, false);
        carInfo  = view.findViewById(R.id.carInfo );
        carOk1     = view.findViewById(R.id.carOk1 );
        carOk1.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");

    }
    // 카메라, 앨범 에서 이미지 가져오기
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.carOk1:
                ProductBean vo = new ProductBean();
                vo = vo.getProd();              // myFragment 에서 값 담음
                String carNm 	= vo.getCarNm();
                String carEmail = vo.getCarEmail();
                String carYear  = vo.getCarYear();
                String carKm    = vo.getCarKm();
                String carAddr  = vo.getCarAddr();
                String carTel   = vo.getCarTel();
                String carFuel  = vo.getCarFuel();
                String carAmt   = vo.getCarAmt();
                String carInfo  = vo.getCarInfo();
                File storage = getActivity().getCacheDir();
                File file = null;
                for (File cacheFile : storage.listFiles()) { // 기존파일삭제
                    if (cacheFile.exists() && "carImg01.jpeg".equals(cacheFile.getName()) ) {
                        file = cacheFile;
                        break;
                    }
                }
                if(file == null){
                    UtilActivity.showALert("대표이지(필수)",getContext());
                    return ;
                }
                if("".equals(carEmail)){
                    UtilActivity.showALert("이메일(필수)",getContext());
                    return ;
                }
                if("".equals(carNm)){
                    UtilActivity.showALert("캠핑카명",getContext());
                    return ;
                }
                if("".equals(carYear)){
                    UtilActivity.showALert("년식",getContext());
                    return ;
                }
                if("".equals(carKm)){
                    UtilActivity.showALert("키로수",getContext());
                    return ;
                }
                if("".equals(carAddr)){
                    UtilActivity.showALert("차주 주소",getContext());
                    return ;
                }
                if("".equals(carTel)){
                    UtilActivity.showALert("연락처",getContext());
                    return ;
                }
                if("".equals(carAmt)){
                    UtilActivity.showALert("금액",getContext());
                    return ;
                }
                sendServer send = new sendServer();
                send.start();
                getActivity().finish(); // pagerActivity
                break;
        }
    }

    class sendServer extends Thread {
        public void run() {
            File file = null;
            try {
                File storage = getActivity().getCacheDir();
                for (File cacheFile : storage.listFiles()) { // 기존파일삭제
                    if (cacheFile.exists() && "carImg01.jpeg".equals(cacheFile.getName()) ) {
                        file = cacheFile;
                        break;
                    }
                }

                ProductBean vo = new ProductBean();
                vo = vo.getProd();              // myFragment 에서 값 담음
                String carNm 	= vo.getCarNm();
                String carEmail = vo.getCarEmail();
                String carYear  = vo.getCarYear();
                String carKm    = vo.getCarKm();
                String carAddr  = vo.getCarAddr();
                String carTel   = vo.getCarTel();
                String carFuel  = vo.getCarFuel();
                String carAmt   = vo.getCarAmt();
                String carInfo  = vo.getCarInfo();

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1000 * 100; // 10k
                FileInputStream fileInputStream = null;

                if (file.isFile()) {

                    try {
                        String upLoadServerUri = "http://49.50.167.90/car/carIns/saveApp";

                        // open a URL connection to the Servlet
                        fileInputStream = new FileInputStream( file);
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
                        dos.writeBytes("Content-Disposition: form-data; name=\"email\""+lineEnd+lineEnd+ URLEncoder.encode(carEmail, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carNm\""+lineEnd+lineEnd+ URLEncoder.encode(carNm, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carAddr\""+lineEnd+lineEnd+ URLEncoder.encode(carAddr, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carAmt\""+lineEnd+lineEnd+ URLEncoder.encode(carAmt, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carInfo\""+lineEnd+lineEnd+ URLEncoder.encode(carInfo, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carFuel\""+lineEnd+lineEnd+ URLEncoder.encode(carFuel, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carKm\""+lineEnd+lineEnd+ URLEncoder.encode(carKm, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carTel\""+lineEnd+lineEnd+ URLEncoder.encode(carTel, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carYear\""+lineEnd+lineEnd+ URLEncoder.encode(carYear, "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carImg01\";filename=\""
                                + "carImg01.jpg" + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();// 바이트수를 반환

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {
                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();// read하고 나머지를 읽는다.
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                        int serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn.getResponseMessage();

                        if (serverResponseCode == 200) {
                            Log.d("campCar","success");
                            Log.d("campCar",serverResponseMessage);
                            //성공시 xml다시받고 , list화면으로 이동
                            GetXml getxml = new GetXml(getContext());
                            getxml.start();
                            HomeFragment frg = new HomeFragment();
                            ((MainActivity)getActivity()).replaceFragment(frg);
                        }else{
                            Log.d("campCar","server err");
                            Log.d("campCar",serverResponseMessage);
                            // 실패시 alert 메세지 (인터넷확인 잠시후 재시도 )

                        }
                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();
                        vo = null;
                    } catch (Exception e) {
                        // dialog.dismiss();
                        e.printStackTrace();
                    }finally {
                        fileInputStream.close();
                        dos.flush();
                        dos.close();
                        vo = null;

                    }
                    // dialog.dismiss();

                } // End else block


            } catch (Exception ex) {
                // dialog.dismiss();

                ex.printStackTrace();
            }




        }//run
    }//class

}