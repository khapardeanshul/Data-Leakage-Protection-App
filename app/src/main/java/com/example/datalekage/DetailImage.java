package com.example.datalekage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailImage extends AppCompatActivity {

    ImageView ivImages;
    TextView title,desc,date;
    String uURL;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        title=(TextView)findViewById(R.id.btitle);
        desc=(TextView)findViewById(R.id.bdesc);
        date=(TextView)findViewById(R.id.bdate);
        ivImages = findViewById(R.id.ivbk);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null){
            title.setText(mBundle.getString("BName"));
            desc.setText(mBundle.getString("BDesc"));
            date.setText("Date : "+mBundle.getString("BDate"));

            Glide.with(this)
                    .load(mBundle.getString("BImage"))
                    .into(ivImages);

        }


    }

    public void delete(View view) {

        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Images").child(title.getText().toString());
        //Toast.makeText(NotesDetailActivity.this, "Date:", Toast.LENGTH_SHORT).show();
        reference.removeValue();
        startActivity(new Intent(DetailImage.this,DashboardActivity.class));

    }
}