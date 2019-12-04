package com.example.bucketdrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bucketdrop.AdaptorData.AdaptorDrop;
import com.example.bucketdrop.AdaptorData.Addlisterner;
import com.example.bucketdrop.beans.Drop;

public class ActivityMain extends AppCompatActivity  {

    public static final String TAG ="MainActivity";

    private Addlisterner mAddlisterner = new Addlisterner() {
        @Override
        public void add() {
            showAddropDialog();


        }
    };



    View emptyView;

    private Toolbar toolbar;

    private Button buttonAdd;
    private RealmResults<Drop> realmResult;
    private AdaptorDrop mAdaptorDrop;

    private BucketRecycleView mRecyclerView;

    private RealmChangeListener<RealmResults<Drop>> mDropRealmChangeListener = new RealmChangeListener<RealmResults<Drop>>() {
        @Override
        public void onChange(RealmResults<Drop> drops) {
            mAdaptorDrop.update(drops);



        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initbackgoundImage();


        emptyView = findViewById(R.id.emptyView);


        buttonAdd = findViewById(R.id.button);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddropDialog();
            }
        });
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new Divider(this,LinearLayoutManager.VERTICAL));


        mRecyclerView.hideifEmpty(toolbar);
        mRecyclerView.showifEmty(emptyView);

        Realm realmInstance = Realm.getDefaultInstance();
        realmResult = realmInstance.where(Drop.class).findAllAsync();



        mAdaptorDrop = new AdaptorDrop(this,realmResult,mAddlisterner);
        mRecyclerView.setAdapter(mAdaptorDrop);

    }

    private void showAddropDialog() {
        AddDropFragmentDialog dropFragmentDialog = new AddDropFragmentDialog();
        dropFragmentDialog.show(getSupportFragmentManager(),"ADD");
    }

    private void initbackgoundImage(){
        ImageView imageView = findViewById(R.id.imageViewbg);
      Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(imageView);


    }

    @Override
    protected void onStart() {
        super.onStart();
        realmResult.addChangeListener(mDropRealmChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        realmResult.removeChangeListener(mDropRealmChangeListener);
    }
}
