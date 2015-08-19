package com.ccbsoftware.waitfortheinternet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WaitForTheInternet {

    private final static String TAG = WaitForTheInternet.class.getName();

    protected Context context;
    protected OnInternetAction action;

    protected WaitForTheInternet(Context context) {
        this.context = context;
        this.action = null;
    }

    public static WaitForTheInternet with(Context context) {
        return new WaitForTheInternet(context);
    }

    public WaitForTheInternet action(OnInternetAction action) {
        this.action = action;
        return this;
    }

    public void execute() {
        if (action == null) {
            // TODO: Create custom exception
            throw new RuntimeException("remember call action method");
        }
        if (isConnectedToInternet()) {
            action.onInternet();
        }
        else {
            Log.d(TAG, "Waiting for internet");
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.d(TAG, "BroadcastReceiver::onReceive");
                    if (!isInitialStickyBroadcast()) {
                        if (action.checkRetryPolicy()) {
                            execute();
                        }
                        context.unregisterReceiver(this);
                    }
                }
            }, filter);
        }
    }

    protected boolean isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static abstract class OnInternetAction {
        public abstract void onInternet();
        public boolean checkRetryPolicy() {
            return true;
        }
    }
}
