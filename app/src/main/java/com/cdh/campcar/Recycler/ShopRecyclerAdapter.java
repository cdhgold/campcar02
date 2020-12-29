package com.cdh.campcar.Recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.ViewFragment;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
/*
구분조건으로 3열보기
 */
public class ShopRecyclerAdapter extends RecyclerView.Adapter<ShopRecyclerAdapter.ShopViewHolder> {
    private ArrayList<ProductBean> data;
    private ItemClickListener listener;
    private Context ctx;
    private int pos = 0;
    private RequestManager glideMang;
    public ShopRecyclerAdapter(Context context, ArrayList<ProductBean> data, ItemClickListener listener
                                , RequestManager mGlideRequestManager){
        this.data = data;
        this.listener = listener;
        this.ctx = context;
        this.glideMang = mGlideRequestManager;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_card, viewGroup, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, int i) {
        ProductBean productBean = data.get(i);
        pos = productBean.getSeq() ;

        String imgStr = productBean.getCarImg01() ;
        //File storage = new File(ctx.getFilesDir(), imgStr);
        //Bitmap myBitmap = BitmapFactory.decodeFile(storage.getAbsolutePath());
        //Bitmap bm = UtilActivity.StringToBitmap(imgStr);
        //Drawable image =  new BitmapDrawable(ctx.getResources(), bm);

        //shopViewHolder.productImage.setImageDrawable( image  );

        //this.glideMang.load("file:///" + ctx.getFilesDir() + "/" + imgStr).into(shopViewHolder.productImage);
        Picasso.get().load("file:///"+ctx.getFilesDir()+"/"+imgStr).into(shopViewHolder.productImage);

        //shopViewHolder.productImage.setImageBitmap(myBitmap);
        shopViewHolder.productName.setText(productBean.getCarNm());
        shopViewHolder.productPrice.setText(String.valueOf(productBean.getCarAmt()));

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

    public Drawable getImage(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Drawable drawable = new BitmapDrawable(null, bitmap);
        return drawable;
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        private Context context;

        public ShopViewHolder(@NonNull View itemView) {
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
