package com.example.datalekage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateImageNoteActivity extends AppCompatActivity {

    ImageView image;
    EditText Bname,Bdesc;
    String imageUrl;
    Button btn_upload,btn_publish;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    List<ImageData> uploadImgList;
    Uri uri;
    CardView mycardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_image_note);

        Bname=(EditText)findViewById(R.id.bname);
        Bdesc=(EditText)findViewById(R.id.bdesc);
        image= findViewById(R.id.iv_Image);

        btn_upload=(Button)findViewById(R.id.uploadImg);
        btn_publish=(Button)findViewById(R.id.saveImg);

        uploadImgList = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Images");

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImgFile();
            }
        });

    }

    private void selectImgFile() {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){

            uri=data.getData();
            image.setImageURI(uri);

        }
        else Toast.makeText(this, "You have not picked a image", Toast.LENGTH_SHORT).show();

    }

    public void uploadImage(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StorageReference storageRefrence = FirebaseStorage.getInstance().getReference().child("Images").child(uri.getLastPathSegment());
        storageRefrence.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uriImage = uriTask.getResult();
                imageUrl= uriImage.toString();
                uploadImageData();
                progressDialog.dismiss();


            }
        });
    }


    public void btnPublish(View view) {

        uploadImage();

    }

    public void uploadImageData(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        ImageData upload = new ImageData();
        upload.setImglink(imageUrl);
        upload.setName(Bname.getText().toString().trim());
        upload.setDesc(Bdesc.getText().toString().trim());
        upload.setDate(myCurrentDateTime);

        FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Images").child(Bname.getText().toString()).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()){
                    Toast.makeText(CreateImageNoteActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    startActivity(new Intent(CreateImageNoteActivity.this,DashboardActivity.class));

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateImageNoteActivity.this,"Failed To Upload..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });



    }

}