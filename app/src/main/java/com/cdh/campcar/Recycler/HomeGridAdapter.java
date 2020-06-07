package com.cdh.campcar.Recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.MyInsFragment;
import com.cdh.campcar.Fragment.ViewFragment;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
/*
main page 하단 상품정보를 보여준다
차량선택시 상세보기로 이동
 */
public class HomeGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProductBean> data;
    private ImageView imageView;
    private RequestManager glideMang;
    private int pos = 0;
    public HomeGridAdapter(Context context, ArrayList<ProductBean> data, RequestManager mGlideRequestManager){
        this.context = context;
        this.data = data;
        this.glideMang = mGlideRequestManager;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeViewHolder viewHolder;
        //pos = data.get(position).getSeq() ;
        if(convertView == null){
            viewHolder = new HomeViewHolder();
            // cardview : 라운드테두리, 그림자를 줄수 있다
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_item_card, null);
            viewHolder.productImage = convertView.findViewById(R.id.imageView);
            imageView = convertView.findViewById(R.id.imageView);
            viewHolder.productName = convertView.findViewById(R.id.productNameTv);
            viewHolder.productPrice = convertView.findViewById(R.id.productPriceTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HomeViewHolder) convertView.getTag();
        }

        String imgStr = data.get(position).getCarImg01() ; // 파일명
        try {
            this.glideMang.load("file:///" + context.getFilesDir() + "/" + imgStr).into(viewHolder.productImage);
            //Picasso.get().load("file:///"+context.getFilesDir()+"/"+imgStr).into(imageView);
            //viewHolder.productImage = imageView;
            viewHolder.productName.setText(data.get(position).getCarNm()+data.get(position).getSeq());
            viewHolder.productPrice.setText(String.valueOf(data.get(position).getCarAmt()));

        }catch (Exception e){

        }
        return convertView;
    }
    // url 이미지를 set drawadble
    private Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }
    }
    public class HomeViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
    }

    public Drawable getImage(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Drawable drawable = new BitmapDrawable(null, bitmap);
        return drawable;
    }
}
