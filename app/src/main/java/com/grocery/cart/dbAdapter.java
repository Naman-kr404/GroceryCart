package com.grocery.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class dbAdapter extends FirebaseRecyclerAdapter<ContactModel, dbAdapter.myViewHolder> {
//    String imageId;
    public dbAdapter(@NonNull FirebaseRecyclerOptions<ContactModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ContactModel model) {
        Glide.with(holder.txtImg.getContext()).load(model.getImg()).into(holder.txtImg);
        holder.txtImg.setTag(model.getImg());
        holder.txtInfo.setText(model.getProduct_info());
        holder.txtQuantity.setText(model.getQuantity());
        holder.txtPrice.setText(model.getPrice());
//        imageId = (String) holder.txtImg.getTag();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView txtImg;
        TextView txtInfo, txtQuantity, txtPrice;
        Button addBtn, rmvBtn;
        EditText edtQuantity;
        int REQUEST_CALL_CODE=1;
//        ArrayList<seletedContactModel>arrContacts=new ArrayList<>();

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txtImg=itemView.findViewById(R.id.txtImg);
            txtInfo=itemView.findViewById(R.id.txtInfo);
            txtQuantity=itemView.findViewById(R.id.txtQuantity);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            edtQuantity=itemView.findViewById(R.id.edtQuantity);
            addBtn=itemView.findViewById(R.id.addBtn);
            rmvBtn=itemView.findViewById(R.id.rmvBtn);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String info=txtInfo.getText().toString();
                    String price=txtPrice.getText().toString();
                    String quantity=txtQuantity.getText().toString();
                    String nquantity=edtQuantity.getText().toString();
                    String imageId = (String) txtImg.getTag();
//                    ((MainActivity) v.getContext()).requestSMSPermission(info, price, quantity); // Call the requestSMSPermission method
                   addBtn.setText("Added");
                   rmvBtn.setVisibility(View.VISIBLE);
                   SelectedItems.arrContacts.add(new seletedContactModel(imageId, info, quantity, price, nquantity));

                }
            });
            rmvBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String imageId = (String) txtImg.getTag();  // Get the imageId tag from the ImageView
                    // Find the item in SelectedItems.arrContacts that matches the imageId
                    for (int i = 0; i < SelectedItems.arrContacts.size(); i++) {
                        if (SelectedItems.arrContacts.get(i).getImg().equals(imageId)) {
                            SelectedItems.arrContacts.remove(i);  // Remove the matching item
                            break;
                        }
                    }
                    rmvBtn.setVisibility(View.GONE);
                    edtQuantity.setText("");
                    addBtn.setText("Add");
                }
            });

        }
    }
}

