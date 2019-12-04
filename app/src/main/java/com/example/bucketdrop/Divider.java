package com.example.bucketdrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.example.bucketdrop.AdaptorData.AdaptorDrop;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Divider extends RecyclerView.ItemDecoration {
    public static final String TAG = "Divider";

    private Drawable mDivider;
    private int morientation;

    public Divider (Context context, int orientation){
       mDivider = context.getDrawable(R.drawable.divider);
       if (orientation != LinearLayoutManager.VERTICAL){
           throw new RuntimeException("Invalid Orientation");
       }
       morientation = orientation;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        drawHorizontalDivider(c,parent,state);
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if (morientation == LinearLayoutManager.VERTICAL){
            int left,top,right,buttom;

            left=parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            for (int i = 0; i <childCount ; i++) {

                if (AdaptorDrop.FOOTER_ITEM != parent.getAdapter().getItemViewType(i)) {
                    View child = parent.getChildAt(i);
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    top = child.getBottom() + params.bottomMargin;
                    buttom = top + mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, buttom);
                    mDivider.draw(c);
                    Log.d(TAG, "Divider dimens" + left + " ," + top + " ," + right + " ," + buttom);
                }

            }
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
      outRect.set(0,0,0,mDivider.getIntrinsicHeight());
    }
}
