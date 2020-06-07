package com.example.chatapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomCreateGroupDialog {
    private Activity activity;
    private AlertDialog alertDialog;
    private DatabaseReference databaseReference;

    @BindView(R.id.createGroupEditText)
    EditText createGroupEditText;
    @BindView(R.id.createGroupButton)
    CardView createGroupButton;

    public CustomCreateGroupDialog(Activity activity) {
        this.activity = activity;
    }

    public void startCreatingGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_create_group_dialog, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @OnClick(R.id.createGroupButton)
    public void setCreateGroupButtonClicked() {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        String groupName = createGroupEditText.getText().toString();
        if (!TextUtils.isEmpty(groupName)) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    databaseReference.child(ConstantKey.GROUPS).child(groupName).setValue("");
                }
            });
        }
        alertDialog.dismiss();
    }

}
