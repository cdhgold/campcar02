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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import java.text.DecimalFormat;
import java.util.ArrayList;
/*
main page 하단 상품정보를 보여준다
차량선택시 상세보기로 이동
 */
public class HomeGridAdapter extends RecyclerView.Adapter<HomeGridAdapter.HomeViewHolder> {
    private Context context;
    private ItemClickListener listener;
    private ArrayList<ProductBean> data;
    private ImageView imageView;
    private RequestManager glideMang;
    private int pos = 0;
    public HomeGridAdapter(Context context, ArrayList<ProductBean> data,ItemClickListener listener,
                           RequestManager mGlideRequestManager){
        this.context = context;
        this.data = data;
        this.listener = listener;
        this.glideMang = mGlideRequestManager;
    }

    @NonNull
    @Override
    public HomeGridAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_card, viewGroup, false);
        return new HomeGridAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeGridAdapter.HomeViewHolder homeViewHolder, int i) {
        ProductBean productBean = data.get(i);
        pos = productBean.getSeq() ;

        String imgStr = productBean.getCarImg01() ;

        //this.glideMang.load("file:///" + ctx.getFilesDir() + "/" + imgStr).into(shopViewHolder.productImage);
        Picasso.get().load("file:///"+context.getFilesDir()+"/"+imgStr).into(homeViewHolder.productImage) ;

        //shopViewHolder.productImage.setImageBitmap(myBitmap);
        homeViewHolder.productName.setText(productBean.getCarNm());
        DecimalFormat formatter = new DecimalFormat("###,###");
        String reg = "^[0-9]+$";
        String amt = productBean.getCarAmt();
        if(amt.matches(reg)) {
            amt = formatter.format(Double.parseDouble(amt));
        }
        homeViewHolder.productPrice.setText(amt);

    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        else
            return data.size();
    }

    public void updateData(ArrayList<ProductBean> data){
        this.data = data;

    }

    // url 이미지를 set drawadble
    private Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable drawa = Drawable.createFromStream(is, "src name");
            return drawa;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }
    }
    public Drawable getImage(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Drawable drawable = new BitmapDrawable(null, bitmap);
        return drawable;
    }
    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        private Context context;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            //notifyItemRangeChanged(0, data.size());
            productImage = itemView.findViewById(R.id.imageView);
            productName = itemView.findViewById(R.id.productNameTv);
            productPrice = itemView.findViewById(R.id.productPriceTv);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition() ;
            if (pos != RecyclerView.NO_POSITION) {
                listener.onItemClick(v, pos,"img" );

            }
        }
    }

}
