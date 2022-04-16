package com.dev_marinov.hometestaleffinish;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout llFragList, llFragScreen;
    int lastVisibleItem;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llFragList = findViewById(R.id.llFragList);
        llFragScreen = findViewById(R.id.llFragScreen);
        arrayList = new ArrayList<>();

        setWindows(); // установка заднего фона

        // переход во FragmentList
        FragmentList fragmentList = new FragmentList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.llFragList, fragmentList);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        Log.e("444","ДО счетчик=" + getSupportFragmentManager().getBackStackEntryCount());
        // как только будет ноль (последний экран) выполниться else
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Log.e("444","ПОСЛЕ счетчик=" + getSupportFragmentManager().getBackStackEntryCount());
            // часть кода для того чтобы я просто мог только нажать кн назад и удалить view
            if(getSupportFragmentManager().getBackStackEntryCount() == 1)
            {
                llFragScreen.removeAllViews();
                //getSupportFragmentManager().popBackStack(); // удаление фрагментов из транзакции
            }
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack(); // удаление фрагментов из транзакции
            myAlertDialog(); // метод реализации диалога с пользователем закрыть приложение или нет
        }
    }
    // метод реализации диалога с пользователем закрыть приложение или нет
    public void myAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Do you wish to exit ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // finish used for destroyed activity
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // Nothing will be happened when clicked on no button
                // of Dialog
            }
        });
        alertDialog.show();
    }

    public void setWindows() {
        Window window = getWindow();
        // установка градиента анимации на toolbar
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        Drawable background = getResources().getDrawable(R.drawable.gradient_3);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS Флаг, указывающий, что это Окно отвечает за отрисовку фона для системных полос.
        // Если установлено, системные панели отображаются с прозрачным фоном, а соответствующие области в этом окне заполняются цветами,
        // указанными в Window#getStatusBarColor()и Window#getNavigationBarColor().
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.black));
        window.setBackgroundDrawable(background);
    }
}