package com.example.bucketdrop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.bucketdrop.beans.Drop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import io.realm.Realm;

public class AddDropDialog extends DialogFragment {

    private ImageButton mbuttonClosed;
    private EditText editText;


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int Id = view.getId();
            switch (Id){
                case R.id.add_it_button:
                    saveDataintoRealm();
            }
            dismiss();
        }
    };

    private void saveDataintoRealm() {

        Realm Realminstance = Realm.getDefaultInstance();
        String what = editText.getText().toString();
        long added = System.currentTimeMillis();
        Drop drop = new Drop(what,added,0,false);

        Realminstance.beginTransaction();
        Realminstance.copyToRealm(drop);
        Realminstance.commitTransaction();


    }


    public AddDropDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_drop_layout,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mbuttonClosed = view.findViewById(R.id.imageButton);
        mbuttonClosed.setOnClickListener(clickListener);

        editText = view.findViewById(R.id.editText);
    }
}
