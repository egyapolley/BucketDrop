package com.example.bucketdrop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.bucketdrop.beans.Drop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import io.realm.Realm;

public class AddDropFragmentDialog extends DialogFragment {

    private ImageButton mImageButton;
    private Button mButton;
    private EditText mEditText;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.add_it:
                    savedrop();
                    break;
            }
            dismiss();
        }
    };

    private void savedrop() {
        String what = mEditText.getText().toString();
        long added = System.currentTimeMillis();
        Drop drop = new Drop(what,added,0,false);
        Realm  realmInstance = Realm.getDefaultInstance();
        realmInstance.beginTransaction();
        realmInstance.copyToRealm(drop);
        realmInstance.commitTransaction();


    }

    public AddDropFragmentDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.adddrop_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageButton = view.findViewById(R.id.imageButton);
        mImageButton.setOnClickListener(mOnClickListener);
        mEditText = view.findViewById(R.id.editText);
        mButton = view.findViewById(R.id.add_it);
        mButton.setOnClickListener(mOnClickListener);


    }
}
