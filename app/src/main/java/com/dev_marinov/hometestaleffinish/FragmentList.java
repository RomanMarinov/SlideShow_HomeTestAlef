package com.dev_marinov.hometestaleffinish;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class FragmentList extends Fragment {


    SwipeRefreshLayout swipe_container;


    AdapterList adapterList;
    RecyclerView rvList;
    GridLayoutManager gridLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ViewGroup viewGroup;
    LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("444", "зашел во FragmentList");
        Log.e("444","FragmentList getSupportFragmentManager().getBackStackEntryCount()="
                + getActivity().getSupportFragmentManager().getBackStackEntryCount());

        this.viewGroup = container;
        this.layoutInflater = inflater;

        return initInterface();
    }

    public View initInterface() {
        View view;
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            Log.e("444", "viewGroup.removeAllViewsInLayout() во FragmentList");
        }

        // получить экран ориентации
        int orientation = getActivity().getResources().getConfiguration().orientation;
        // раздуть соответствующий макет в зависимости от ориентации экрана
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = layoutInflater.inflate(R.layout.fragment_list, viewGroup, false);

            //mySwipeOnRefreshListener(view); // метод Swipe чтобы обновлять данные вручную
            mySwipeOnRefreshListener(view);
            // метод для установки recyclerview, GridLayoutManager и AdapterListHome
            myRecyclerLayoutManagerAdapter(view, 2, ((MainActivity) getActivity()).lastVisibleItem);

            Log.e("444", "во FragmentList ПОРТРЕТ");
        } else {
            view = layoutInflater.inflate(R.layout.fragment_list, viewGroup, false);

          //  mySwipeOnRefreshListener(view); // метод Swipe чтобы обновлять данные вручную
            mySwipeOnRefreshListener(view);
            // метод для установки recyclerview, GridLayoutManager и AdapterListHome

            myRecyclerLayoutManagerAdapter(view, 3, ((MainActivity) getActivity()).lastVisibleItem);

            Log.e("444", "во FragmentList ЛАНШАФТ");
        }

        if (((MainActivity) getActivity()).arrayList.size() == 0) {
            GetData getData = new GetData();
            getData.getDateMethod(getActivity());
        } else {
            Log.e("444", "FragmentList arrayList.size()  НЕ ПУСТОЙ=");
        }

        return view; // в onCreateView() возвращаем объект View, который является корневым элементом разметки фрагмента.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e("444", "-зашел FragmentList onConfigurationChanged-");
        // ДО СОЗДАНИЯ НОВОГО МАКЕТА ПИШЕМ ПЕРЕМЕННЫЕ В КОТОРЫЕ СОХРАНЯЕМ ЛЮБЫЕ ДАННЫЕ ИЗ ТЕКУЩИХ VIEW

//        // создать новый макет------------------------------
        View view = initInterface();
        // ПОСЛЕ СОЗДАНИЯ НОВОГО МАКЕТА ПЕРЕДАЕМ СОХРАНЕННЫЕ ДАННЫЕ В СТАРЫЕ(ТЕ КОТОРЫЕ ТЕКУЩИЕ) VIEW

        // отображать новую раскладку на экране
        viewGroup.addView(view);
        super.onConfigurationChanged(newConfig);
    }


    // передаем в параметт view initInterface() чтобы определить swipe_container в макете
    public void mySwipeOnRefreshListener(View view) {
        // обновление данных при свайпе
        swipe_container = view.findViewById(R.id.swipe_container);
        swipe_container.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // момент оттягивания свайпа с задержкой
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("444", "-зашел mySwipeOnRefreshListener-");
                        swipe_container.setRefreshing(false);    // Отменяем анимацию обновления false true
                        ((MainActivity) getActivity()).arrayList.clear(); // очищаем массив чтобы в список не копировалась сверху те же данные
                        GetData getData = new GetData();
                        getData.getDateMethod(getActivity());
                    }
                }, 500); // задержка кпол секунды
            }
        });
    }

    // метод для установки recyclerview, GridLayoutManager и AdapterListHome
    public void myRecyclerLayoutManagerAdapter(View view, int column, int lastVisableItem) {

        rvList = view.findViewById(R.id.rvList);
        rvList.setHasFixedSize(false);

        ////////////////
        // staggeredGridLayoutManager - шахматный порядок
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);

        rvList.setLayoutManager(staggeredGridLayoutManager);

//        gridLayoutManager = new GridLayoutManager(getContext(), column);
//        rvList.setLayoutManager(gridLayoutManager);

        adapterList = new AdapterList(getContext(), ((MainActivity) getActivity()).arrayList, rvList);
        rvList.setAdapter(adapterList);

        //adapterListTest.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            staggeredGridLayoutManager.scrollToPositionWithOffset(lastVisableItem, 0);
                        }
                    });
                } catch (Exception e) {
                    Log.e("444", "-try catch FragmentList 1 -" + e);
                }


            }
        }, 500);

    }



}