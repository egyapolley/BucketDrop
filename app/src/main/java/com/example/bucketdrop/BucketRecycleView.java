package com.example.bucketdrop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class BucketRecycleView extends RecyclerView {

    private List<View> emptyViews = Collections.emptyList();
    private List<View> nonEmptyViews = Collections.emptyList();

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleView();

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {

        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {

        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {

        }
    };

    private void toggleView() {
        if (getAdapter() != null && !nonEmptyViews.isEmpty() && !emptyViews.isEmpty()){
            if (getAdapter().getItemCount() ==1){
                setVisibility(View.GONE);
                Utils.hideViews(nonEmptyViews);
                Utils.showViews(emptyViews);
            }else {
                setVisibility(View.VISIBLE);
                Utils.showViews(nonEmptyViews);
                Utils.hideViews(emptyViews);
            }
        }
    }


    public BucketRecycleView(@NonNull Context context) {
        super(context);
    }

    public BucketRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null){
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }
        mAdapterDataObserver.onChanged();
    }

    public void hideifEmpty(View ...views) {
     nonEmptyViews = Arrays.asList(views);
    }

    public void showifEmty(View ...views) {
        emptyViews = Arrays.asList(views);
    }
}
