package com.cdh.campcar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.cdh.campcar.Data.ProductBean;

import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import io.reactivex.disposables.Disposable;

import com.cdh.campcar.util.ApiConfig;
import com.cdh.campcar.util.AppConfig;
import com.cdh.campcar.util.ServerResponse;
import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
이미지 상세보기 및 수정 및 내리기 처리 .
 */
public class ImgEditActivity extends FragmentActivity implements View.OnClickListener {
    FragmentStateAdapter adapterViewPager;
    private List<Uri> selectedUriList;
    private ImageView iv_image;
    private Uri selectedUri;
    private ViewGroup mSelectedImagesContainer;
    ImageButton home;
    ImageButton gallery;
    ImageButton camera;
    ImageView commImgView;
    EditText email;
    public static String imgFlg = ""; // data에서 가져왔는지, 수정된이미지인지 구분
    public static String imgNum = ""; // img 순번
    static SharedPreferences sref = null;
    SharedPreferences.Editor editor = null;

    private RequestManager requestManager;
    public static ArrayList<String> selectedPhotos = new ArrayList<>();
    public final static int REQUEST_CODE = 1;
    final static int CAPTURE_IMAGE = 2;
    ProductBean vo = new ProductBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_main);
        requestManager = Glide.with(this);
        mSelectedImagesContainer = findViewById(R.id.selected_photos_container);
        home = (ImageButton) findViewById(R.id.vhome); // 메인화면으로 가기
        gallery= (ImageButton) findViewById(R.id.addimg1);
        email= (EditText) findViewById(R.id.email);// 비번
        home.setOnClickListener(this);
        gallery.setOnClickListener(this);
        //tedPermission(); // 권한없을시 ,
        getAlbumImg();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vhome:
                this.finish();
                break;
            case R.id.addimg1:
                getAlbumImg();
                break;


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

        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }
    /*
    갤러리에서 이미지가져오기( Activity 추가 )
     */
    public void getAlbumImg( ){

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                TedBottomPicker.with(ImgEditActivity.this)
                        //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                        .setPeekHeight(1600)
                        .showTitle(false)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("No Select")
                        .setSelectedUriList(selectedUriList)
                        .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(List<Uri> uriList) {
                                showUriList(uriList);
                            }
                        });


            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(ImgEditActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        checkPermission(permissionlistener);
    }
    private void showUriList(List<Uri> uriList) {
        // Remove all views before
        // adding the new ones.
        mSelectedImagesContainer.removeAllViews();

        mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int widthPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int heightPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        for (Uri uri : uriList) {
            View imageHolder = LayoutInflater.from(this).inflate(R.layout.edit_img, null);
            ImageView thumbnail = imageHolder.findViewById(R.id.media_image);
  Log.d("cdhgold","uri.toString() : " + uri.toString()); // file path , 서버로 전송 순차적으로
            requestManager
                    .load(uri.toString())
                    .apply(new RequestOptions().fitCenter())
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder); // framelayout 이 add

            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(widthPixel, heightPixel));

        }
        uploadMultipleFiles(uriList);
    }
    // 권한설정
    private void checkPermission(PermissionListener permissionlistener) {
        TedPermission.with(ImgEditActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
    // 수정
    // Uploading Image/Video
    private void uploadMultipleFiles(List<Uri> uriList) {
        List<String> list = new ArrayList<String>();
        for (Uri uri : uriList) {
            list.add(uri.toString()) ; // file path
        }

        String semail = email.getText().toString();

        RequestBody requestBody;
        MultipartBody.Part body;
        LinkedHashMap<String, RequestBody> mapRequestBody = new LinkedHashMap<String, RequestBody>();
        List<MultipartBody.Part> arrBody = new ArrayList<>();
        Map<String, RequestBody> map = new HashMap<>();


        // Parsing any Media type file
        if(list.get(0) !=null ){
Log.d("file1==> ",list.get(0));
            File file = new File(list.get(0));
            RequestBody img01Body =  RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), semail);
            map.put("file\"; filename=\"carImg01.jpg\" ", img01Body);
            map.put("email", email);

        }
        if(list.get(1) !=null ){
            File file = new File(list.get(1));
            RequestBody img02Body =  RequestBody.create(MediaType.parse("image/*"), file);
            map.put("file\"; filename=\"carImg02.jpg\" ", img02Body);

        }



        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call <ResponseBody> call = getResponse.uploadFile(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(  "Err", t.getMessage());
            }
        });
    }

    //사진수정이후 탄다 .
    @Override
    public void onResume(){
        super.onResume();

    }
    @Override
    public void onStart(){
        super.onStart();


    }
}
