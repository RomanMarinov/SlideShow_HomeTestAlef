package com.dev_marinov.hometestaleffinish;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {

    int[] lastVisibleItemPositions; // массив последних значений позиций элементов
    Context context;
    ArrayList<String> arrayList; // массив ссылок на фотки
    int width;
    int height;

    public AdapterList(Context context, ArrayList<String> arrayList, RecyclerView recyclerView) {
        this.context = context;
        this.arrayList = arrayList;

        // на всякий случай для установки размеров
        Display display = ((MainActivity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        // менеджер макета
        final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        // слушатель списка
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastVisibleItemPositions = staggeredGridLayoutManager.findFirstVisibleItemPositions(null);
                // запись последнего значения
                ((MainActivity)context).lastVisibleItem = getMaxPosition(lastVisibleItemPositions);
            }
            // получение последнего значения
            private int getMaxPosition(int[] positions) {
                int max = positions[0];
                return max;
            }
        });

    }

    @NonNull
    @Override
    public AdapterList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterList.ViewHolder holder, int position) {

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up_1));
        Picasso.get()
                .load(arrayList.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.picture_not_available)
                //.error(R.drawable.picture_not_available)
                //.resize(width, height).centerCrop(Gravity.CENTER_HORIZONTAL)
                //.centerCrop()
                .into(holder.image);

        // клик по элементу чтобы перейти во FragmentScreen
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentScreen fragmentScreen = new FragmentScreen();
                Bundle bundle = new Bundle(); // отправка бандл позиции
                bundle.putInt("key_position", position);
                fragmentScreen.setArguments(bundle);

                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.llFragScreen, fragmentScreen, "llFragScreen");
                fragmentTransaction.addToBackStack("llFragScreen");
                fragmentTransaction.commit();
                //((MainActivity)context).llFragList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        CardView cardView;

        public ViewHolder(View view)
        {
            super(view);
            image = view.findViewById(R.id.imageView);
            cardView = view.findViewById(R.id.cardView);
        }
    }
}
