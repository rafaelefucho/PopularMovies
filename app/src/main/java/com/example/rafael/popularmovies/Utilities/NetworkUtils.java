package com.example.rafael.popularmovies.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.rafael.popularmovies.MainActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rafael on 17/02/2018.
 */

public class NetworkUtils {


    public static boolean isInternetConnectionAvailable(MainActivity mainActivity) {
        final ConnectivityManager cm = (ConnectivityManager)
                mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return (networkInfo != null && networkInfo.isConnected());
    }


}
