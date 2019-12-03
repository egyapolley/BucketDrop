package com.example.bucketdrop.AdaptorData;

import android.view.View;
import android.widget.Button;

import com.example.bucketdrop.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FooterHolder extends RecyclerView.ViewHolder {

    private Button mButton;

    public FooterHolder(@NonNull View itemView) {
        super(itemView);
        mButton =itemView.findViewById(R.id.btn_footer);
    }



}
