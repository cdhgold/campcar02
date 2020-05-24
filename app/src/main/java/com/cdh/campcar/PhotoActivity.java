package com.cdh.campcar;

import com.cdh.campcar.Data.ProductBean;
import com.github.chrisbanes.photoview.PhotoView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
/*
이미지 확대용
 */
public class PhotoActivity extends AppCompatActivity implements View.OnClickListener{
    private ProductBean vo = new ProductBean();
    private Drawable[] img = vo.getDimg();
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);

        PhotoView photoView = findViewById(R.id.photoView);
        photoView.setImageDrawable(img[0]);
        photoView.setOnClickListener(this);
    }
    /*
    click시마다 이미지 전환
     */
    @Override
    public void onClick(View v) {
//UtilActivity.showALert("test",this);
        PhotoView photoView = findViewById(R.id.photoView);
        if(i==10){
            i = 0;
        }
        photoView.setImageDrawable(img[i++]);
    };
    @Override
    public void onBackPressed() {
        vo = null;
        finish(); // 현 activity종료
    }
}
