package com.example.bucketdrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bucketdrop.AdaptorData.AdaptorDrop;
import com.example.bucketdrop.AdaptorData.Addlisterner;
import com.example.bucketdrop.AdaptorData.Filter;
import com.example.bucketdrop.AdaptorData.MarkCompletedListerner;
import com.example.bucketdrop.beans.Drop;

public class ActivityMain extends AppCompatActivity  {

    public static final String FILTER = "filter";

    public static final String TAG ="MainActivity";

    private Addlisterner mAddlisterner = new Addlisterner() {
        @Override
        public void add() {
            showAddropDialog();


        }
    };

    private ItemClickListerner itemClickListerner = new ItemClickListerner() {
        @Override
        public void markComplete(int position) {
            showMarkDialog(position);
        }
    };

    private MarkCompletedListerner mMarkCompletedListerner = new MarkCompletedListerner() {
        @Override
        public void markCompleted(int position) {
           mAdaptorDrop.markCompleted(position);
        }
    };
    private Realm mRealmInstance;

    private void showMarkDialog(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);

        DiaglogMark diaglogMark = new DiaglogMark();
        diaglogMark.setArguments(bundle);
        diaglogMark.setMarkCompletedListener(mMarkCompletedListerner);
        diaglogMark.show(getSupportFragmentManager(), "MARK");


    }


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


        mRealmInstance = Realm.getDefaultInstance();

        mRecyclerView.hideifEmpty(toolbar);
        mRecyclerView.showifEmty(emptyView);

        int filter = loadData();
        realmResult = getResult(filter);

/*
        realmResult = mRealmInstance.where(Drop.class).findAllAsync();*/



        mAdaptorDrop = new AdaptorDrop(this, mRealmInstance,realmResult,mAddlisterner,itemClickListerner);
        mRecyclerView.setAdapter(mAdaptorDrop);

        SimpleCallbackhelper simpleCallbackhelper = new SimpleCallbackhelper(mAdaptorDrop);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallbackhelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);



    }

    private RealmResults<Drop> getResult(int filter) {
        RealmResults<Drop> realmResults;
        switch (filter){
            case Filter.NEAR:
                realmResults =mRealmInstance.where(Drop.class).sort("when").findAllAsync();
                break;
            case Filter.FAR:
                realmResults =mRealmInstance.where(Drop.class).sort("when",Sort.DESCENDING).findAllAsync();
                break;
            case Filter.COMPLETED:
                realmResults =mRealmInstance.where(Drop.class).equalTo("completed",Boolean.TRUE).findAllAsync();
                break;

            case Filter.NOT_COMPLETED:
                realmResults =mRealmInstance.where(Drop.class).equalTo("when",Boolean.FALSE).findAllAsync();
                break;
                default:
                    realmResults =mRealmInstance.where(Drop.class).findAllAsync();

        }
        return realmResults;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions,menu);
        return true;
    }

    public void saveData(int filter){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(FILTER, filter);
        edit.apply();


    }

    public int loadData(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
       return preferences.getInt(FILTER,Filter.NONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId){
            case R.id.add_item:
                showAddropDialog();
                return true;
            case R.id.near_items:

                realmResult = mRealmInstance.where(Drop.class).sort("when").findAllAsync();
                realmResult.addChangeListener(mDropRealmChangeListener);
                saveData(Filter.NEAR);
                return true;
            case R.id.far_away_activities:
                mRealmInstance = Realm.getDefaultInstance();
                realmResult = mRealmInstance.where(Drop.class).sort("when", Sort.DESCENDING).findAllAsync();
                realmResult.addChangeListener(mDropRealmChangeListener);
                saveData(Filter.FAR);
                return true;
            case R.id.item_completed:
                mRealmInstance = Realm.getDefaultInstance();
                realmResult = mRealmInstance.where(Drop.class).equalTo("completed",Boolean.TRUE).findAllAsync();
                realmResult.addChangeListener(mDropRealmChangeListener);
                saveData(Filter.COMPLETED);
                return true;

            case R.id.not_completed:
                mRealmInstance = Realm.getDefaultInstance();
                realmResult = mRealmInstance.where(Drop.class).equalTo("completed",Boolean.FALSE).findAllAsync();
                realmResult.addChangeListener(mDropRealmChangeListener);
                saveData(Filter.NOT_COMPLETED);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
