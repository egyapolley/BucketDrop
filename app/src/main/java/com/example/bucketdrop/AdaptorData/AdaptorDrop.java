package com.example.bucketdrop.AdaptorData;


import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bucketdrop.R;
import com.example.bucketdrop.beans.Drop;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;


public class AdaptorDrop extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Addlisterner mAddlisterner;

    public static final int NORMAL_ITEM =0;
    public static final int FOOTER_ITEM=1;


    private LayoutInflater mLayoutInflater;

    private RealmResults<Drop> mDrops;


    public AdaptorDrop(Context context, RealmResults<Drop> drops, Addlisterner addlisterner) {
        mLayoutInflater = LayoutInflater.from(context);
        mAddlisterner = addlisterner;
        update(drops);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM){
        View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
        return new DropViewHolder(view);
        }
        else {
            View view = mLayoutInflater.inflate(R.layout.footer_layout_item,parent,false);
            return new FooterHolder(view, mAddlisterner);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      if (holder instanceof DropViewHolder){
          DropViewHolder dropViewHolder = (DropViewHolder)holder;
          dropViewHolder.bind(mDrops.get(position));
      }


    }

    @Override
    public int getItemCount() {
        return mDrops.size()+1;
    }

    public void update(RealmResults<Drop> realmResults){
        mDrops =realmResults;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDrops == null || position <mDrops.size()) return NORMAL_ITEM;
        else return FOOTER_ITEM;
    }
}
