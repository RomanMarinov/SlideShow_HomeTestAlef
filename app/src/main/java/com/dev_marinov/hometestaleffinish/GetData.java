package com.dev_marinov.hometestaleffinish;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;

import cz.msebera.android.httpclient.Header;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

public class GetData {

    public void getDateMethod(FragmentActivity activity) {
        Log.e("444", "-зашел FragmentList getDate-");

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get("https://dev-marinov.ru/server/hometest_alef/list.php", null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Log.e("FRAGSHOW ","-responseString-" + responseString);
                    //String jsonArray = responseString;
                    JSONObject jsonObject = new JSONObject(responseString);
                    Iterator<String> key = jsonObject.keys();

                    ArrayList<String> strings = new ArrayList<>();
                    while (key.hasNext())
                    {
                        String value = key.next();
                        strings.add(value);
                    }

                    for (int i = 0; i < strings.size(); i++) {
                        ((MainActivity)activity).arrayList.add(strings.get(i).toString());
                    }

                    FragmentList fragmentList = (FragmentList) ((MainActivity)activity).getSupportFragmentManager()
                            .findFragmentById(R.id.llFragList);
                    if(fragmentList != null)
                    {
                        fragmentList.adapterList.notifyDataSetChanged();
                    }


//                    adapterList.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    Log.e("333","-try-catch-" + e);
                }
            }
        });
    }
}
