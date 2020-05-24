package com.cdh.campcar.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.cdh.campcar.Data.GetXml;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

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
    private ImageView img01;
    private TextView save ;
    private ProductDBHelper dbHelper;

    final static int TAKE_PICTURE = 1;
    final static int CAPTURE_IMAGE = 2;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reg_img, container, false);


        save  = view.findViewById(R.id.save );
        imgCamera  = view.findViewById(R.id.imgCamera );
        imgAlbum   = view.findViewById(R.id.imgAlbum );
        img01  = view.findViewById(R.id.img01 );        //  get 한 이미지를 보여준다
        save.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgAlbum.setOnClickListener(this);

        dbHelper = ProductDBHelper.getInstance(getContext());
        tedPermission();
        return view;
    }
    /*
    카메라로 이미지가져오기
     */
    public void getCameraImg(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
    }
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
            }
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }
    /*
    갤러리에서 이미지가져오기
     */
    public void getAlbumImg(View view){
        //Intent intent = new Intent(Intent.ACTION_PICK);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK) {
                    Uri fileUri = intent.getData();
                    ContentResolver resolver = getActivity().getContentResolver();
                    try {
                        InputStream inputStream = resolver.openInputStream(fileUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img01.setImageBitmap(bitmap);
                        inputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case CAPTURE_IMAGE:
            if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK && intent.hasExtra("data")) {
                Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                if (bitmap != null) {
                    img01.setImageBitmap(bitmap);
                }


            }
                break;

        }
    }
    /*
    서버에 등록
     */
    public void setSave(View view){



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
                //setSave(v);
                sendServer send = new sendServer();
                send.start();
                break;
        }
    }

    class sendServer extends Thread {
        public void run() {
            File file = null;
            try {
                file = new File(Environment.getExternalStorageDirectory(), "/carImg01.jpeg");
                //bitmap을 생성
                Bitmap bitmap = ((BitmapDrawable)img01.getDrawable()).getBitmap();
                OutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
                out.close();
                ProductBean vo = new ProductBean();
                vo = vo.getProd();              // myFragment 에서 값 담음
                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
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
                        dos.writeBytes("Content-Disposition: form-data; name=\"carNm\""+lineEnd+lineEnd+ URLEncoder.encode(vo.getCarNm(), "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carAddr\""+lineEnd+lineEnd+ URLEncoder.encode(vo.getCarAddr(), "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carAmt\""+lineEnd+lineEnd+ URLEncoder.encode(vo.getCarAmt(), "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carInfo\""+lineEnd+lineEnd+ URLEncoder.encode(vo.getCarInfo(), "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carFuel\""+lineEnd+lineEnd+ URLEncoder.encode(vo.getCarFuel(), "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carKm\""+lineEnd+lineEnd+ URLEncoder.encode(vo.getCarKm(), "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carTek\""+lineEnd+lineEnd+ URLEncoder.encode(vo.getCarTel(), "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carYear\""+lineEnd+lineEnd+ URLEncoder.encode(vo.getCarYear(), "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"carImg01\";filename=\""
                                + "carImg01.jpg" + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
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