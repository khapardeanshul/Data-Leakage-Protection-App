package com.example.datalekage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreatePDFActivity extends AppCompatActivity {

    EditText pdfName;
    Button btn_upload,btn_view;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    List<UploadPDF> uploadPDFList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdfactivity);

        pdfName=(EditText)findViewById(R.id.name);
        btn_upload=(Button)findViewById(R.id.uploadPDf);
        btn_view=(Button)findViewById(R.id.viewFiles);

        uploadPDFList = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("PDF");

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDFFile();
            }
        });

       btn_view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(CreatePDFActivity.this,DashboardActivity.class));
           }
       });


    }

    private void selectPDFFile() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data !=null && data.getData() !=null){

            uploadPDFFile(data.getData());
        }

    }

    private void uploadPDFFile(Uri data) {

        databaseReference= FirebaseDatabase.getInstance().getReference();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        StorageReference reference = storageReference.child("pdf/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uri =  taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url = uri.getResult();

                String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                UploadPDF uploadPDF = new UploadPDF();
                uploadPDF.setName(pdfName.getText().toString().trim());
                uploadPDF.setUrl(url.toString());
                uploadPDF.setDate(myCurrentDateTime);
                // databaseReference.child(t1).child(t2).child(t3).child(t4).setValue(uploadPDF);
                // databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                databaseReference.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("PDF").child(myCurrentDateTime).setValue(uploadPDF);
                Toast.makeText(CreatePDFActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded"+(int)progress+"%");

            }
        });

    }



}