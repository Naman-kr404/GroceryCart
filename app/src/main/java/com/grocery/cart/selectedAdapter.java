package com.grocery.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class selectedAdapter extends RecyclerView.Adapter<selectedAdapter.ViewHolder> {
    Context context;
    ArrayList<seletedContactModel> arrContacts;
    selectedAdapter(Context context, ArrayList<seletedContactModel> arrContacts){
        this.context=context;
        this.arrContacts=arrContacts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.selectedproduct,parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.txtImg.getContext())
                .load(arrContacts.get(position).getImg()) // Now using the instance's img field
                .into(holder.txtImg);
        holder.txtInfo.setText(arrContacts.get(position).product_info);
        holder.txtQuantity.setText(arrContacts.get(position).quantity);
        holder.txtPrice.setText(arrContacts.get(position).price);
        holder.edtQuantity.setText(arrContacts.get(position).nQuantity);
    }

    @Override
    public int getItemCount() {
        return arrContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView txtImg;
        TextView txtInfo, txtQuantity, txtPrice, edtQuantity;
        Button buybtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtImg=itemView.findViewById(R.id.txtImg);
            txtInfo=itemView.findViewById(R.id.txtInfo);
            txtQuantity=itemView.findViewById(R.id.txtQuantity);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            edtQuantity=itemView.findViewById(R.id.edtQuantity);


        }
    }
}
