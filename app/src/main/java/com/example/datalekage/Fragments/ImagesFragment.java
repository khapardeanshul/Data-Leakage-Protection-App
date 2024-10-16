package com.example.datalekage.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datalekage.CreateImageNoteActivity;
import com.example.datalekage.ImageData;
import com.example.datalekage.MyAdapterImages;
import com.example.datalekage.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ImagesFragment extends Fragment {

    RecyclerView mRecyclerView;
    List<ImageData> myImageList;

    ProgressDialog progressDialog;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FloatingActionButton btnadd;

    View v;

    public ImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_images, container, false);
        btnadd = v.findViewById(R.id.btnadd);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateImageNoteActivity.class));
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("LOADING ..");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        myImageList=new ArrayList<>();

        final MyAdapterImages myAdapterImages = new MyAdapterImages(getContext(),myImageList);
        mRecyclerView.setAdapter(myAdapterImages);

        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Images");

        progressDialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myImageList.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {

                   ImageData imgdata = itemSnapshot.getValue(ImageData.class);
                   myImageList.add(imgdata);

                }

                myAdapterImages.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });


        return v;

    }



}