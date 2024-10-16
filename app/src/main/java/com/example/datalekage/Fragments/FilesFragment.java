package com.example.datalekage.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.datalekage.CreatePDFActivity;
import com.example.datalekage.R;
import com.example.datalekage.UploadPDF;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class FilesFragment extends Fragment {

   FloatingActionButton btnadd;
   View v;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    ListView myPDFListView;
    List<UploadPDF> uploadPDFList;
    CardView mycardview;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public FilesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_files, container, false);
        btnadd = v.findViewById(R.id.btnadd);

        mycardview = (CardView)v.findViewById(R.id.cardview);
        myPDFListView = (ListView)v.findViewById(R.id.myListView);
        uploadPDFList = new ArrayList<>();

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        myPDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UploadPDF uploadPDF = uploadPDFList.get(position);

                String link = uploadPDF.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);

            }
        });


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CreatePDFActivity.class));
            }
        });

        uploadPDFList.clear();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("PDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ps : dataSnapshot.getChildren()){

                    UploadPDF uploadPDF = ps.getValue(UploadPDF.class);
                    uploadPDFList.add(uploadPDF);

                }

                String [] uploads = new String[uploadPDFList.size()];

                for (int i=0;i<uploads.length;i++){

                    uploads[i]=uploadPDFList.get(i).getName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,uploads);
                myPDFListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return v;
    }
}