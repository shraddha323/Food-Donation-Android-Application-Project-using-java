package com.example.food_donation_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    public static final String EXTRA_URL="imageUrl";
    public static final String EXTRA_CREATOR="creatorName";
    public static final String EXTRA_MOBILE="creatorName2";

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseReference;
    private ValueEventListener mDBListener;
    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads=new ArrayList<>();

        mAdapter=new ImageAdapter(HomeActivity.this,mUploads);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(HomeActivity.this);

        mStorage=FirebaseStorage.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("uploads");

        mDBListener=mDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Upload upload=postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
//whenever item is clicked,new activity opened with same image item,image name and mobile no.
    @Override
    public void onItemClick(int position) {
        Intent it=new Intent(HomeActivity.this,Name.class);
        Upload clickedItem=mUploads.get(position);
        it.putExtra(EXTRA_URL,clickedItem.getImageUrl());
        it.putExtra(EXTRA_CREATOR,clickedItem.getName());
        it.putExtra(EXTRA_MOBILE,clickedItem.getMob());
        startActivity(it);
        //Toast.makeText(HomeActivity.this,"Normal Click at position: "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(HomeActivity.this,"Whatever Click at position: "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem=mUploads.get(position);
        final String selectedKey=selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseReference.child(selectedKey).removeValue();
                Toast.makeText(HomeActivity.this,"Item deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mDBListener);
    }

    public void onClick2(View view) {

       Intent i=new Intent(HomeActivity.this,Upload_Page.class);
        startActivity(i);
    }
}
