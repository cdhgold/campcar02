package com.cdh.campcar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import id.zelory.compressor.Compressor;
import io.reactivex.disposables.Disposable;

import com.cdh.campcar.util.ApiClient;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
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
    String semail = "";
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
        semail = email.getText().toString();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vhome:
                this.finish();
                break;
            case R.id.addimg1:
                semail = email.getText().toString();
                if("".equals(semail)){
                    UtilActivity.showAlim("email 필수입력!",this);
                    break;
                }
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
                        .setPeekHeight(1100)
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
//Log.d("cdhgold","uri.toString() : " + uri.toString()); // file path , 서버로 전송 순차적으로
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
        List<Uri> list = new ArrayList<Uri>();
        for (Uri uri : uriList) {
            list.add(uri ) ; // file path
        }


        RequestBody requestBody;
        MultipartBody.Part body;
        LinkedHashMap<String, RequestBody> mapRequestBody = new LinkedHashMap<String, RequestBody>();
        List<MultipartBody.Part> arrBody = new ArrayList<>();
        Map<String, MultipartBody.Part> map = new HashMap<String, MultipartBody.Part>();
        MultipartBody.Part body1 = null;
        MultipartBody.Part body2 = null;
        MultipartBody.Part body3 = null;
        MultipartBody.Part body4 = null;
        MultipartBody.Part body5 = null;
        MultipartBody.Part body6 = null;
        MultipartBody.Part body7 = null;
        MultipartBody.Part body8 = null;
        MultipartBody.Part body9 = null;
        MultipartBody.Part body10 = null;

        // Parsing any Media type file
        if(list.size() >= 1 && list.get(0) !=null ){
            Uri furi = list.get(0);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs,"img01" ) ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body1 = MultipartBody.Part.createFormData("carImg01", "carImg01.png", requestFile);
        }
        if(list.size() >= 2 && list.get(1) !=null ){
            Uri furi = list.get(1);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs,"img02" ) ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body2 = MultipartBody.Part.createFormData("carImg02", "carImg02.png", requestFile);
        }
        if(list.size() >= 3 && list.get(2) !=null ){
            Uri furi = list.get(2);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs,"img03" ) ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body3 = MultipartBody.Part.createFormData("carImg03", "carImg03.png", requestFile);
        }
        if(list.size() >= 4 && list.get(3) !=null ){
            Uri furi = list.get(3);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs ,"img04") ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body4 = MultipartBody.Part.createFormData("carImg04", "carImg04.png", requestFile);
        }
        if(list.size() >= 5 && list.get(4) !=null ){
            Uri furi = list.get(4);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs ,"img05") ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body5 = MultipartBody.Part.createFormData("carImg05", "carImg05.png", requestFile);
        }
        if(list.size() >= 6 && list.get(5) !=null ){
            Uri furi = list.get(5);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs ,"img06") ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body6 = MultipartBody.Part.createFormData("carImg06", "carImg06.png", requestFile);
        }
        if(list.size() >= 7 && list.get(6) !=null ){
            Uri furi = list.get(6);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs ,"img07") ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body7 = MultipartBody.Part.createFormData("carImg07", "carImg07.png", requestFile);
        }
        if(list.size() >= 8 && list.get(7) !=null ){
            Uri furi = list.get(7);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs ,"img08") ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body8 = MultipartBody.Part.createFormData("carImg08", "carImg08.png", requestFile);
        }
        if(list.size() >= 9 && list.get(8) !=null ){
            Uri furi = list.get(8);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs ,"img09") ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body9 = MultipartBody.Part.createFormData("carImg09", "carImg09.png", requestFile);
        }
        if(list.size() >= 10 && list.get(9) !=null ){
            Uri furi = list.get(9);
            ContentResolver resolver = this.getContentResolver();
            File file = null;
            try{
                InputStream inputs = resolver.openInputStream(furi);
                file = getFile( inputs ,"img10") ;
            }catch(Exception e){
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body10 = MultipartBody.Part.createFormData("carImg10", "carImg10.png", requestFile);
        }

        Map<String, RequestBody> params = new HashMap<>();
        params.put("email", ApiClient.createRequestBody(semail));

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call <ResponseBody> call = getResponse.uploadProduct(params,body1,body2,body3,body4,body5,body6,body7,body8,body9,body10);
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

    private File getFile( InputStream input , String fileName)   {
        File storage = this.getCacheDir();
        File file = new File(storage, fileName);
        try (OutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024 * 1024]; // 1m
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }

            output.flush();
        } catch (Exception e) {
        }finally {
            try{
                input.close();
            }catch(Exception e){
            }
        }
        return file;
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
