package com.dev_marinov.hometestaleffinish;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

class AdapterSwipe extends RecyclerView.Adapter<AdapterSwipe.SliderViewHolder> {

    ArrayList<String> arrayList;
    ViewPager2 viewPager2;

    public AdapterSwipe(ArrayList<String> arrayList, ViewPager2 viewPager2) {
        this.arrayList = arrayList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.slide_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        //holder.setImageView(arrayList.get(position).toString());

        Picasso.get()
                .load(arrayList.get(position).toString())
                .placeholder(R.drawable.picture_not_available)
//                .error(R.drawable.picture_not_available)
//                .resize(width, height).centerCrop(Gravity.CENTER_HORIZONTAL)
//                .centerCrop()
                //.noPlaceholder()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }


    }

}
