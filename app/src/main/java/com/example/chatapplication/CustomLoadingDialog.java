package com.example.chatapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class CustomLoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    public CustomLoadingDialog(Activity activity) {
        this.activity = activity;
    }

    void startLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater=activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_loading_dialog,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        alertDialog.show();
    }

    void stopLoadingDialog(){
        alertDialog.dismiss();
    }
}
