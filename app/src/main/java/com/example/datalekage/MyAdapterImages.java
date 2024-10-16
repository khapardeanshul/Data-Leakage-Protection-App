package com.example.datalekage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapterImages extends RecyclerView.Adapter<ImagesViewHolder>{


   private Context mContext;
   private List<ImageData> myImageList;


    public MyAdapterImages(Context mContext, List<ImageData>  myImageList) {
        this.mContext = mContext;
        this.myImageList = myImageList;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item_images,parent,false);
        return new ImagesViewHolder(mview);

    }

    @Override
    public void onBindViewHolder(@NonNull final ImagesViewHolder holder, int i) {

        Glide.with(mContext)
                .load(myImageList.get(i).getImglink())
                .into(holder.b_image);

        holder.b_name.setText(myImageList.get(i).getName());
        holder.b_desc.setText(myImageList.get(i).getDesc());
        holder.b_date.setText("Date : "+myImageList.get(i).getDate());



        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(mContext, DetailImage.class);
                intent.putExtra("BImage",myImageList.get(holder.getAdapterPosition()).getImglink());
                intent.putExtra("BName",myImageList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("BDesc",myImageList.get(holder.getAdapterPosition()).getDesc());
                intent.putExtra("BDate",myImageList.get(holder.getAdapterPosition()).getDate());

                mContext.startActivity(intent);


            }
        });





    }

    @Override
    public int getItemCount() {
        return myImageList.size();
    }


}

class ImagesViewHolder extends RecyclerView.ViewHolder{

    TextView b_name,b_date,b_desc;
    CardView mCardView;
    ImageView b_image;


    public ImagesViewHolder( View itemView) {
        super(itemView);

        b_image = itemView.findViewById(R.id.b_image);
        b_name = itemView.findViewById(R.id.b_title);
        b_desc = itemView.findViewById(R.id.b_desc);
        b_date = itemView.findViewById(R.id.b_date);
        mCardView = itemView.findViewById(R.id.myCardView);

    }
}
