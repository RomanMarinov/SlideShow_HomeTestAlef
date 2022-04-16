package com.dev_marinov.hometestaleffinish;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


public class FragmentScreen extends Fragment {

    ViewPager2 viewPager2;
    AdapterSwipe adapterSwipe;
    ImageView imageViewFragScreen;

    ViewGroup viewGroupFragScreen;
    LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.viewGroupFragScreen = container;
        this.layoutInflater = inflater;

        return initInterface();
    }

    // новый интерфес для установки макета при переворачивании экрана
    public View initInterface() {
        View view;
        if (viewGroupFragScreen != null) { // если view есть
            viewGroupFragScreen.removeAllViewsInLayout(); // то удаляем views после поворота экрана
            Log.e("444", "viewGroup.removeAllViewsInLayout() во FragmentScreen");
        }

        // получить экран ориентации
        int orientation = getActivity().getResources().getConfiguration().orientation;
        // раздуть соответствующий макет в зависимости от ориентации экрана
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = layoutInflater.inflate(R.layout.fragment_screen, viewGroupFragScreen, false);

        } else {
            view = layoutInflater.inflate(R.layout.fragment_screen, viewGroupFragScreen, false);
        }

        imageViewFragScreen = view.findViewById(R.id.imageViewFragScreen);
        viewPager2 = view.findViewById(R.id.viewPager2ImageSlider);
        // адаптер для просмотра картинок фулскрин
        adapterSwipe = new AdapterSwipe(((MainActivity)getActivity()).arrayList, viewPager2);
        viewPager2.setAdapter(adapterSwipe);// установка адаптера


        // класс трансофрмации картинок в фулскрин
        DepthTransformation depthTransformation = new DepthTransformation();
        viewPager2.setPageTransformer(depthTransformation); // помещает трансформер во viewPager2
        viewPager2.setOffscreenPageLimit(10);
        // получение данных для фул скрин
        Bundle bundle = this.getArguments();
        int currentPosition = bundle.getInt("key_position", 0);

        new Handler().postDelayed(new Runnable() { // задержка 0.5 сек
            @Override
            public void run() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager2.setCurrentItem(currentPosition);
                        }
                    });
                }
                catch (Exception e) {
                    Log.e("444", "-try catch FragmentHome 1-" + e);
                }
            }
        }, 50);

        return view; // в onCreateView() возвращаем объект View, который является корневым элементом разметки фрагмента.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e("444", "-зашел FragmentScreen onConfigurationChanged-");
        // ДО СОЗДАНИЯ НОВОГО МАКЕТА ПИШЕМ ПЕРЕМЕННЫЕ В КОТОРЫЕ СОХРАНЯЕМ ЛЮБЫЕ ДАННЫЕ ИЗ ТЕКУЩИХ VIEW

        // создать новый макет------------------------------
        View view = initInterface();
        // ПОСЛЕ СОЗДАНИЯ НОВОГО МАКЕТА ПЕРЕДАЕМ СОХРАНЕННЫЕ ДАННЫЕ В СТАРЫЕ(ТЕ КОТОРЫЕ ТЕКУЩИЕ) VIEW

        // отображать новую раскладку на экране
        viewGroupFragScreen.addView(view);
        super.onConfigurationChanged(newConfig);
    }

}