package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;

    @BindView(R.id.needNewAccountTitleTextView)
    TextView needNewAccountTitleTextView;
    @BindView(R.id.loginButton)
    CardView loginButton;
    @BindView(R.id.loginWithPhoneButton)
    CardView loginWithPhoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @OnClick(R.id.needNewAccountTitleTextView)
    public void onNeedNewAccountTitleTextViewClicked() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked() {
    }

    @OnClick(R.id.loginWithPhoneButton)
    public void onLoginWithPhoneButtonClicked() {

    }
}
