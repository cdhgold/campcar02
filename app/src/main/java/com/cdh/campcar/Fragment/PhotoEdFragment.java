package com.cdh.campcar.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

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
 각 이미지수정
 */
public class PhotoEdFragment extends Fragment  implements View.OnClickListener {

    private View view;

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
    private TextView cancel ;
    private ProductDBHelper dbHelper;
    private ProductBean vo;
    private String imgSeq = ""; // img 수정 순번
    final static int TAKE_PICTURE = 1;
    final static int CAPTURE_IMAGE = 2;
    private int seq = 0;
    private int chkimg = 0; // image 수정여부확인 , image get 100
    Object lock = new Object();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chkimg = 0;
        if (getArguments() != null) {
            imgSeq = getArguments().getString("img"); // img 순번
//UtilActivity.showALert(imgSeq,getContext());
        }
    }

    // 메인. 슬라이드 형식 화면 절반치 광고, 아래에 상품 6개 정도 보여주기
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_reg_img, container, false);
        vo = new ProductBean();
        vo = vo.getProd();
        seq = vo.getSeq(); // 수정data  seq
//UtilActivity.showALert(String.valueOf(seq), getContext());

        save  = view.findViewById(R.id.save );
        cancel  = view.findViewById(R.id.cancel );
        imgCamera  = view.findViewById(R.id.imgCamera );
        imgAlbum   = view.findViewById(R.id.imgAlbum );
        img01  = view.findViewById(R.id.img01 );        //  get 한 이미지를 보여준다
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        imgCamera.setOnClickListener(this);// 카메라
        imgAlbum.setOnClickListener(this);  // 갤러리

        dbHelper = ProductDBHelper.getInstance(getContext());
        tedPermission();// 권한요청

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

    // 갤러리, 카메라로 촬영한 영상을 가져오는 부분
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        chkimg= 100;
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
                // img 있는지 확인
                if( chkimg == 0){
                    UtilActivity.showAlim("이미지를 선택하세요!",getContext());
                    break;
                }
                // inner class thread
                PhotoEdFragment.sendServer send = new PhotoEdFragment.sendServer();
                send.start();
                break;
            case R.id.cancel:
                Bundle bundle = new Bundle();
                bundle.putString("imgpos", "0");
                PhotoFragment frg = new PhotoFragment();
                frg.setArguments(bundle); // param pass
                ((PhotoActivity)getContext()).replaceFragment(frg);
                break;
        }
    }

    class sendServer extends Thread {
        public void run() {
            File file = null;
            try {
                // 임시파일 생성
                file = new File(Environment.getExternalStorageDirectory(), "/carImg01.jpeg");
                //bitmap을 생성
                Bitmap bitmap = ((BitmapDrawable)img01.getDrawable()).getBitmap(); // 가져온 이미지
                OutputStream out = new FileOutputStream(file);              // outstream 정의
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out); // 압축
                out.close();

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";   // w3c에서 규정
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                FileInputStream fileInputStream = null;

                if (file.isFile()) {

                    try {
                        String upLoadServerUri = "http://49.50.167.90/car/carIns/imgUp";

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
                        dos.writeBytes("Content-Disposition: form-data; name=\"seq\""+lineEnd+lineEnd+ URLEncoder.encode(String.valueOf(seq) , "UTF-8")+lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"imgSeq\""+lineEnd+lineEnd+ URLEncoder.encode(imgSeq , "UTF-8")+lineEnd);
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

                            dos.write(buffer, 0, bufferSize); // file write
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
                            //저장후 이동
                            Log.d("campCar","success");
                            Log.d("campCar",serverResponseMessage);
                            //성공시 xml다시받고 , list화면으로 이동
                            GetXml getxml = new GetXml(getContext());
                            getxml.start();
                            try {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showAlert();
                                    }
                                });
                                synchronized (lock) {
                                    lock.wait();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            //main 으로 이동
                            startActivity(intent);

                        }else{
                            Log.d("campCar","server err");
                            Log.d("campCar",serverResponseMessage);
                            // 실패시 alert 메세지 (인터넷확인 잠시후 재시도 )

                        }
                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {
                        // dialog.dismiss();
                        e.printStackTrace();
                    }finally {
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    }
                    // dialog.dismiss();

                } // End else block


            } catch (Exception ex) {
                // dialog.dismiss();

                ex.printStackTrace();
            }




        }//run
    }//class thread end
    // thread 안 alert
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog = builder.setTitle("장영실제작소")
                .setMessage("광고문의는 konginfo.co.kr로! ")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        releaseLock();
                    }
                })


// 백버튼 불가, 바탕화면 클릭 불가
                .setCancelable(false)
                .create();
        dialog.show();
    }
    // thread lock 해제
    void releaseLock() {
        synchronized (lock) {
            lock.notify();
            Log.e("TAG", "notify");
        }
    }

}
