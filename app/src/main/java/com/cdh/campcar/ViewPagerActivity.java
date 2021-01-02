package com.cdh.campcar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.cdh.campcar.Data.ProductBean;
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
import com.cdh.campcar.Fragment.MyPagerAdapter;
import com.cdh.campcar.Fragment.Reg02Fragment;
import com.cdh.campcar.Fragment.Reg03Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.relex.circleindicator.CircleIndicator3;

/*
이미지 상세보기 및 수정 및 내리기 처리 .
 */
public class ViewPagerActivity extends AppCompatActivity  implements View.OnClickListener {
    FragmentStateAdapter adapterViewPager;
    ImageButton home;
    ImageButton gallery;
    ImageButton camera;
    ImageView commImgView;
    final static int CAPTURE_IMAGE = 2;
    ProductBean vo = new ProductBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_img_pager);

        ViewPager2 vpPager = (ViewPager2) findViewById(R.id.imgvpager);
        adapterViewPager = new ImgPagerAdapter(this, 10);// fragment 연결
        vpPager.setAdapter(adapterViewPager);
        home = (ImageButton) findViewById(R.id.vhome); // 메인화면으로 가기
        gallery= (ImageButton) findViewById(R.id.addimg1);
        camera= (ImageButton) findViewById(R.id.addimg2);

        CircleIndicator3 indicator = (CircleIndicator3 ) findViewById(R.id.vindicator);
        indicator.setViewPager(vpPager);
        home.setOnClickListener(this);
        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);
        vo = vo.getProd();
Log.d("ViewPagerActivity","getProd===>"+vo);
        vpPager.registerOnPageChangeCallback(callback);


    }
    //fragment에서 호출해서 이미지를 보인다.
    public void setAimg1(ImageView img, int pos){
        commImgView = img;
 Log.d("commImgView"," setAimg1 "+commImgView);
        if(pos == 0) {// 대표이미지
            String img01 = vo.getCarImg01();
            Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img01).into(img);
        }
        else if(pos == 1){
            String img02 = vo.getCarImg02();
            if("".equals(img02)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///" + getApplicationContext().getFilesDir() + "/" + img02).into(img);
            }

        }
        else if(pos == 2){
            String img03 = vo.getCarImg03();
            if("".equals(img03)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///"+getApplicationContext().getFilesDir()+"/"+img03).into(img);
            }

        }
        else if(pos == 3){
            String img04 = vo.getCarImg04();
            if("".equals(img04)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///"+getApplicationContext().getFilesDir()+"/"+img04).into(img);
            }
        }
        else if(pos == 4){
            String img05 = vo.getCarImg05();
            if("".equals(img05)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///"+getApplicationContext().getFilesDir()+"/"+img05).into(img);
            }
        }
        else if(pos == 5){
            String img06 = vo.getCarImg06();
            if("".equals(img06)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///"+getApplicationContext().getFilesDir()+"/"+img06).into(img);
            }
        }
        else if(pos == 6){
            String img07 = vo.getCarImg07();
            if("".equals(img07)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///"+getApplicationContext().getFilesDir()+"/"+img07).into(img);
            }
        }
        else if(pos == 7){
            String img08 = vo.getCarImg08();
            if("".equals(img08)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///"+getApplicationContext().getFilesDir()+"/"+img08).into(img);
            }
        }
        else if(pos == 8){
            String img09 = vo.getCarImg09();
            if("".equals(img09)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///"+getApplicationContext().getFilesDir()+"/"+img09).into(img);
            }
        }
        else if(pos == 9){
            String img10 = vo.getCarImg10();
            if("".equals(img10)){
                img.setImageDrawable(getNoImg());
            }else {
                Glide.with(getApplicationContext()).load("file:///"+getApplicationContext().getFilesDir()+"/"+img10).into(img);
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
            case R.id.addimg2:
                getCameraImg(v);
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
                    ContentResolver resolver = this.getContentResolver();
                    try {
                        InputStream inputStream = resolver.openInputStream(fileUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        commImgView.setImageBitmap(bitmap);
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
Log.d("commImgView"," commImgView "+commImgView);
                        commImgView.setImageBitmap(bitmap);
                        setFilefromImg(bitmap);
                    }


                }
                break;

        }
    }
    //내부저장소에 file 생성
    private void setFilefromImg(Bitmap bitmap) {
        //내부저장소 캐시 경로를 받아옵니다.
        File storage = this.getCacheDir();
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
    // no img 처리
    public Drawable getNoImg(){
        Drawable img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.noimg );
        return img;
    }
}
