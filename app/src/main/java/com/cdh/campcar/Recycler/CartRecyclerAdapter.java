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
import androidx.recyclerview.widget.RecyclerView;

import com.cdh.campcar.Data.ProductBean;
import com.cdh.campcar.Fragment.ViewFragment;
import com.cdh.campcar.MainActivity;
import com.cdh.campcar.R;
import com.cdh.campcar.UtilActivity;

import java.util.ArrayList;
/*
최신목록으로 보기
 */
public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder>{
    private ArrayList<ProductBean> data;
    private ItemClickListener listener;
    private Context ctx;
    private int pos = 0;
    public CartRecyclerAdapter(Context context, ArrayList<ProductBean> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
        this.ctx = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_cartitem_card, viewGroup, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        pos = i;
        ProductBean productBean = data.get(i);
        String imgStr = productBean.getCarImg01() ;
        Bitmap bm = UtilActivity.StringToBitmap(imgStr);
        Drawable image =  new BitmapDrawable(ctx.getResources(), bm);

        cartViewHolder.productImage.setImageDrawable(image);
        cartViewHolder.productName.setText(productBean.getCarNm());
        cartViewHolder.productPrice.setText(String.valueOf(productBean.getCarAmt()));
    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        else
            return data.size();
    }

    public Drawable getImage(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Drawable drawable = new BitmapDrawable(null, bitmap);
        return drawable;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.imageView);
            productName = itemView.findViewById(R.id.productNameTv);
            productPrice = itemView.findViewById(R.id.productPriceTv);
            productImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    listener.onItemClick(v, pos,"" );

                }
            });
        }
    }
}
