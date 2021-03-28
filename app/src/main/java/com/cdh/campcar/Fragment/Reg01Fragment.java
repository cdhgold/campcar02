package com.cdh.campcar.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

import static android.app.Activity.RESULT_OK;

/*
캠핑카등록1 -  이미지
 */
public class Reg01Fragment extends Fragment implements View.OnClickListener {
    private ImageButton imgCamera  ;
    private ImageButton imgAlbum  ;

    final static int TAKE_PICTURE = 1;
    final static int CAPTURE_IMAGE = 2;
    private ImageView img01;
    private static Reg01Fragment reg01Frg = null;
    public static Reg01Fragment getInstance() {
        if(reg01Frg == null) {
            reg01Frg = new Reg01Fragment();
        }
       // Bundle args = new Bundle();
       // args.putInt("someInt", page);
      //  args.putString("someTitle", title);
       // reg01Frg.setArguments(args);
        return reg01Frg;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reg01, container, false);
        imgCamera  = view.findViewById(R.id.imgCamera );
        imgAlbum   = view.findViewById(R.id.imgAlbum );
        img01  = view.findViewById(R.id.img01 );        //  get 한 이미지를 보여준다
        imgCamera.setOnClickListener(this);
        imgAlbum.setOnClickListener(this);
        tedPermission(); // 권한없을시 ,
        ProductBean vo = new ProductBean();
        ProductBean.prod = vo;
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
            case R.id.imgCamera:
                getCameraImg(v);
                break;
            case R.id.imgAlbum:
                getAlbumImg(v);
                break;
            case R.id.save:

                break;
        }
    }
    /*
        카메라로 이미지가져오기
         */
    public void getCameraImg(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
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

    // 카메라로 촬영한 영상을 가져오는 부분, 내부저장소로 파일생성
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
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
                        setFilefromImg(bitmap);
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
                        setFilefromImg(bitmap);
                    }


                }
                break;

        }
    }
    //내부저장소에 file 생성
    private void setFilefromImg(Bitmap bitmap) {
        //내부저장소 캐시 경로를 받아옵니다.
        File storage = getActivity().getCacheDir();
        for (File cacheFile : storage.listFiles()) { // 기존파일삭제
            if (cacheFile.exists() && "carImg01.jpeg".equals(cacheFile.getName()) ) {
                cacheFile.delete();
                break;
            }
        }
        //저장할 파일 이름
        String fileName =  "carImg01.jpeg";
        //storage 에 파일 인스턴스를 생성합니다.
        File tempFile = new File(storage, fileName);
        try {
            // 파일을 쓸 수 있는 스트림을 준비합니다.
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out); // out 으로 compress
            // 스트림 사용후 닫아줍니다.
            out.close();
        } catch (FileNotFoundException e) {
            Log.e("cdhgold","FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            Log.e("cdhgold","IOException : " + e.getMessage());
        }
    }
    /*
    권한 요청
     */
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
}