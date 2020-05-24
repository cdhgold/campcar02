package com.cdh.campcar.Recycler;

import android.content.Context;
import android.content.res.Resources;
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

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;

import java.util.ArrayList;

public class ViewAdapter extends BaseAdapter {
    Context context;
    int layout;
    String img[];
    LayoutInflater inf;
    private ImageView imageView;
    public ViewAdapter(Context context,   String[] img) {
        this.context = context;
        this.img = img;

    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return img[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewAdapter.ViewHolder viewHolder;
        final int pos = position;
        if(convertView == null){
            viewHolder = new ViewAdapter.ViewHolder();
            // cardview : 라운드테두리, 그림자를 줄수 있다
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row, null);
            viewHolder.productImage = convertView.findViewById(R.id.carimg);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewAdapter.ViewHolder) convertView.getTag();
        }
        String imgStr = img[position] ;
        Bitmap bm = UtilActivity.StringToBitmap(imgStr);
        Drawable image =  new BitmapDrawable(context.getResources(), bm);
         if("".equals(imgStr.trim()) ){
             Resources res = context.getResources();
             image = ResourcesCompat.getDrawable(res, R.drawable.noimg, null);
         }
        viewHolder.productImage.setImageDrawable(image);
        return convertView;
    }
    public class ViewHolder {
        ImageView productImage;

    }
}
