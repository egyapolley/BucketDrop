package com.example.bucketdrop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bucketdrop.AdaptorData.MarkCompletedListerner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DiaglogMark extends DialogFragment {

    private MarkCompletedListerner mMarkCompletedListerner;

    private ImageButton imageButtonClose;
    private Button markCompleted;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.btn_markComplete:
                    Bundle arguments = getArguments();
                    if (arguments != null){
                        int position = arguments.getInt("POSITION");
                        mMarkCompletedListerner.markCompleted(position);

                    }


            }
            dismiss();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diaglog_mark_completxml, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       imageButtonClose = view.findViewById(R.id.buttonClose);
       markCompleted = view.findViewById(R.id.btn_markComplete);
       markCompleted.setOnClickListener(clickListener);
       imageButtonClose.setOnClickListener(clickListener);


    }

    public void setMarkCompletedListener(MarkCompletedListerner markCompletedListerner) {
        this.mMarkCompletedListerner = markCompletedListerner;
    }
}
