package com.example.bucketdrop.AdaptorData;

import android.view.View;
import android.widget.TextView;

import com.example.bucketdrop.ItemClickListerner;
import com.example.bucketdrop.R;
import com.example.bucketdrop.beans.Drop;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DropViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListerner itemClickListerner;


    private TextView mTextView;
    public DropViewHolder(@NonNull View itemView, ItemClickListerner itemClickListerner) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.itemClickListerner = itemClickListerner;
        mTextView = itemView.findViewById(R.id.tv_what);


    }


    public void bind(Drop drop){
        mTextView.setText(drop.getWhat());

    }

    @Override
    public void onClick(View view) {
        itemClickListerner.markComplete(getAdapterPosition());

    }
}
