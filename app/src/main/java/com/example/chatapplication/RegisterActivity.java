package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.alreadyHaveAccountTitleTextView)
    TextView alreadyHaveAccountTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.alreadyHaveAccountTitleTextView)
    public void onAlreadyHaveAccountTitleTextViewClicked(){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}
