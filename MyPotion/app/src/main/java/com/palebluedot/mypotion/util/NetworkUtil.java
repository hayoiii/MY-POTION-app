package com.palebluedot.mypotion.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;

public class NetworkUtil {
    public static boolean check(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Log.e("NetworkCheck: check(): networkInfo.detailedState", networkInfo.getDetailedState().toString());

        return networkInfo.isConnected();
    }
}
