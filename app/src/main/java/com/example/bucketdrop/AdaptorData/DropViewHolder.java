package com.example.bucketdrop.AdaptorData;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.example.bucketdrop.ItemClickListerner;
import com.example.bucketdrop.R;
import com.example.bucketdrop.beans.Drop;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DropViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickListerner itemClickListerner;

    private View mView;


    private TextView mTextView;
    private TextView mWhenTextView;
    public DropViewHolder(@NonNull View itemView, ItemClickListerner itemClickListerner) {
        super(itemView);
        itemView.setOnClickListener(this);
        mView = itemView;
        this.itemClickListerner = itemClickListerner;
        mTextView = itemView.findViewById(R.id.tv_what);
        mWhenTextView =itemView.findViewById(R.id.tv_when);


    }


    public void bind(Drop drop){
        boolean completed = drop.isCompleted();
        if (completed){
            mView.setBackground(mView.getContext().getDrawable(R.drawable.completebg));
        }else {
            mView.setBackground(mView.getContext().getDrawable(R.drawable.background_pressed));
        }
        long when = drop.getWhen();
        CharSequence stringDate = DateUtils.getRelativeTimeSpanString(when, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS, 0);
        mWhenTextView.setText(stringDate.toString());
        mTextView.setText(drop.getWhat());

    }

    @Override
    public void onClick(View view) {
        itemClickListerner.markComplete(getAdapterPosition());

    }
}
