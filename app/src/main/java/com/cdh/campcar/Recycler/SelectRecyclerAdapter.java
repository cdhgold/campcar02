package com.cdh.campcar.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdh.campcar.R;
/*
 가격,년식,키로수 adapter
 */
public class SelectRecyclerAdapter extends RecyclerView.Adapter<SelectRecyclerAdapter.SelectViewHolder> {
    private String[] data;
    private ItemClickListener listener;

    public SelectRecyclerAdapter(String[] data, ItemClickListener listener){
        this.data = data; //가격,년식,키로수
        this.listener = listener;
    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_select_type_card, viewGroup, false);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int i) {
        selectViewHolder.typeSelect.setText(data[i]);

        final int index = i;

    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;
        else
            return data.length;
    }

    public class SelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView typeSelect;

        public SelectViewHolder(@NonNull View itemView) {
            super(itemView);

            typeSelect = itemView.findViewById(R.id.typeSelectTv);
            itemView.setOnClickListener(this)  ;
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition() ;
            if (pos != RecyclerView.NO_POSITION) {
                // 데이터 리스트로부터 아이템 데이터 참조.
                //String gbn = data[pos] ;

                listener.onItemClick(v,pos,"");

            }
        }
    }
}
