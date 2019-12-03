package com.example.bucketdrop.AdaptorData;

import android.view.View;
import android.widget.TextView;

import com.example.bucketdrop.R;
import com.example.bucketdrop.beans.Drop;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DropViewHolder extends RecyclerView.ViewHolder {


    private TextView mTextView;
    public DropViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.tv_what);


    }


    public void bind(Drop drop){
        mTextView.setText(drop.getWhat());

    }
}
