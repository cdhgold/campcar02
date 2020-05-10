package com.cdh.campcar.Recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
/*
main page 하단 상품정보를 보여준다
 */
public class HomeGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProductBean> data;

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

        if(convertView == null){
            viewHolder = new HomeViewHolder();
            // cardview : 라운드테두리, 그림자를 줄수 있다
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_item_card, null);
            viewHolder.productImage = convertView.findViewById(R.id.imageView);
            viewHolder.productName = convertView.findViewById(R.id.productNameTv);
            viewHolder.productPrice = convertView.findViewById(R.id.productPriceTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HomeViewHolder) convertView.getTag();
        }
        Drawable drawable = LoadImageFromWebOperations(data.get(position).getCarImg01() );
        viewHolder.productImage.setImageDrawable(drawable);
        viewHolder.productName.setText(data.get(position).getCarNm());
        viewHolder.productPrice.setText(String.valueOf(data.get(position).getCarAmt()));

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
