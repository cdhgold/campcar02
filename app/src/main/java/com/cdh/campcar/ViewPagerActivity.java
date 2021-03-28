package com.cdh.campcar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.cdh.campcar.Data.PreferenceManager;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Data.ProductDBHelper;
import com.cdh.campcar.Fragment.Img01Fragment;
import com.cdh.campcar.Fragment.Img02Fragment;
import com.cdh.campcar.Fragment.Img03Fragment;
import com.cdh.campcar.Fragment.Img04Fragment;
import com.cdh.campcar.Fragment.Img05Fragment;
import com.cdh.campcar.Fragment.Img06Fragment;
import com.cdh.campcar.Fragment.Img07Fragment;
import com.cdh.campcar.Fragment.Img08Fragment;
import com.cdh.campcar.Fragment.Img09Fragment;
import com.cdh.campcar.Fragment.Img10Fragment;
import com.cdh.campcar.Fragment.ImgPagerAdapter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

/*
이미지 상세보기 및 수정 및 내리기 처리 .
 */
public class ViewPagerActivity extends AppCompatActivity  implements View.OnClickListener {
    FragmentStateAdapter adapterViewPager;
    private List<Uri> selectedUriList;
    private ViewGroup mSelectedImagesContainer;
    ImageButton home;
    ImageButton gallery;
    ImageButton camera;
    ImageView commImgView;
    public static String imgFlg = ""; // data에서 가져왔는지, 수정된이미지인지 구분
    public static String imgNum = ""; // img 순번
    static SharedPreferences sref = null;
    SharedPreferences.Editor editor = null;
    private ProductDBHelper dbHelper;
    private RequestManager requestManager;
    public static ArrayList<String> selectedPhotos = new ArrayList<>();
    public final static int REQUEST_CODE = 1;
    final static int CAPTURE_IMAGE = 2;
    ProductBean vo = new ProductBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_img_pager);
        imgFlg = "";
        ViewPager2 vpPager = (ViewPager2) findViewById(R.id.imgvpager);
        adapterViewPager = new ImgPagerAdapter(this, 10);// fragment 연결
        vpPager.setAdapter(adapterViewPager);
        home = (ImageButton) findViewById(R.id.vhome); // 메인화면으로 가기
        gallery= (ImageButton) findViewById(R.id.addimg1);

        CircleIndicator3 indicator = (CircleIndicator3 ) findViewById(R.id.vindicator);
        indicator.setViewPager(vpPager);
        home.setOnClickListener(this);
        gallery.setOnClickListener(this);

        vo = vo.getProd();
Log.d("ViewPagerActivity","getProd===>"+vo);
        vpPager.registerOnPageChangeCallback(callback);
        sref = getSharedPreferences("imgflg",MODE_PRIVATE);
        editor = sref.edit();

    }
    // fragment에서 호출해서 이미지를 보인다.
    public void setAimg1( int pos){
        ImageView img= null ;
        if(pos ==0 ){
            img = Img01Fragment.img01 ;
            imgNum = "0";
        }
        else if(pos ==1){
            img = Img02Fragment.img01 ;
            imgNum = "1";
        }
        else if(pos ==2){
            img = Img03Fragment.img01 ;
            imgNum = "2";
        }
        else if(pos ==3){
            img = Img04Fragment.img01 ;
            imgNum = "3";
        }
        else if(pos ==4){
            img = Img05Fragment.img01 ;
            imgNum = "4";
        }
        else if(pos ==5){
            img = Img06Fragment.img01 ;
            imgNum = "5";
        }
        else if(pos ==6){
            img = Img07Fragment.img01 ;
            imgNum = "6";
        }
        else if(pos ==7){
            img = Img08Fragment.img01 ;
            imgNum = "7";
        }
        else if(pos ==8){
            img = Img09Fragment.img01 ;
            imgNum = "8";
        }
        else if(pos ==9){
            img = Img10Fragment.img01 ;
            imgNum = "9";
        }

        if(vo == null){
            PreferenceManager prefm = new PreferenceManager();

            dbHelper = ProductDBHelper.getInstance(getApplicationContext());
            ArrayList<ProductBean>  data = dbHelper.getRandomProduct();
            ProductBean pvo = ProductBean.getInstance();
            pvo.setProd(data.get(pos ));
            Log.d("view  vo is null ","cdh pos "+pos)   ;
            vo = pvo.getProd();
        }
        img.setImageDrawable(getNoImg());
        if(pos == 0    ) {// 대표이미지
            String img01 = vo.getCarImg01();
            Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img01).into(img);
        }
        else if(pos == 1    ){
            String img02 = vo.getCarImg02();
            if("".equals(img02)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img02).into(img);
            }
        }
        else if(pos == 2   ){
            String img03 = vo.getCarImg03();
            if("".equals(img03)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img03).into(img);
            }
        }
        else if(pos == 3   ){
            String img04 = vo.getCarImg04();
            if("".equals(img04)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img04).into(img);
            }
        }
        else if(pos == 4    ){
            String img05 = vo.getCarImg05();
            if("".equals(img05)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img05).into(img);
            }
        }
        else if(pos == 5    ){
            String img06 = vo.getCarImg06();
            if("".equals(img06)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img06).into(img);
            }
        }
        else if(pos == 6     ){
            String img07 = vo.getCarImg07();
            if("".equals(img07)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img07).into(img);
            }
        }
        else if(pos == 7   ){
            String img08 = vo.getCarImg08();
            if("".equals(img08)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img08).into(img);
            }
        }
        else if(pos == 8   ){
            String img09 = vo.getCarImg09();
            if("".equals(img09)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img09).into(img);
            }
        }
        else if(pos == 9     ){
            String img10 = vo.getCarImg10();
            if("".equals(img10)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img10).into(img);
            }
        }

    }
    /*
    페이지 전환시 이벤트, 현재페이지 get
     */
    private ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int pos) {
            super.onPageSelected(pos);
            Log.e("cdhgold", "OnPageChangeCallback : " + pos);
            setAimg1(pos);
            //setImg(vo, pos );

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vhome:
                this.finish();
                break;
            case R.id.addimg1:
                getAlbumImg(v);
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
    갤러리에서 이미지가져오기( Activity 추가 )
     */
    public void getAlbumImg(View view){
        // 이미지 수정 activity
        Intent intent = new Intent(ViewPagerActivity.this, ImgEditActivity.class); // 확대해서 보기
        //액티비티 시작!
       //intent.putExtra("pos",String.valueOf(pos));
        startActivity(intent);

    }

    // no img 처리
    public Drawable getNoImg(){
        Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.noimg );
        return img;
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
