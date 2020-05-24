package com.cdh.campcar.Recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.MyInsFragment;
import com.cdh.campcar.Fragment.ViewFragment;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;

import java.io.ByteArrayInputStream;
import java.io.File;
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

    public HomeGridAdapter(Context context, ArrayList<ProductBean> data){
        this.context = context;
        this.data = data;
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
        final int pos = position;
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
        File storage = new File(Environment.DIRECTORY_PICTURES , imgStr);

        //Drawable image =  new BitmapDrawable(context.getResources(), bm);

        //Drawable image = Drawable.createFromPath(Environment.DIRECTORY_PICTURES+imgStr );
        //imageView.setImageBitmap(BitmapFactory.decodeFile(pathToPicture));
        Bitmap myBitmap = BitmapFactory.decodeFile(storage.getAbsolutePath());

        viewHolder.productImage.setImageBitmap(myBitmap);
       // viewHolder.productImage.setImageDrawable(image);
        viewHolder.productName.setText(data.get(position).getCarNm());
        viewHolder.productPrice.setText(String.valueOf(data.get(position).getCarAmt()));
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                // 상세보기로 가기
                //data.get(pos); // procutBean 을 넘긴다
                ProductBean procutBean = new ProductBean();
                procutBean.setProd(data.get(pos));
                ViewFragment frg = new ViewFragment();
                ((MainActivity)context).replaceFragment(frg);    // 새로 불러올 Fragment의 Instance를 Main으로 전달

            }
        });
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
