package com.example.bucketdrop.AdaptorData;

import android.view.View;
import android.widget.Button;

import com.example.bucketdrop.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FooterHolder extends RecyclerView.ViewHolder {

    private Button mButton;

    private Addlisterner mAddlisterner;

    public FooterHolder(@NonNull View itemView ,Addlisterner addlisterner) {
        super(itemView);
        mAddlisterner = addlisterner;
        mButton =itemView.findViewById(R.id.btn_footer);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddlisterner.add();

            }
        });
    }



}
