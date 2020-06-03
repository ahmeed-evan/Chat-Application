package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private CustomLoadingDialog customLoadingDialog;
    private FirebaseAuth firebaseAuth;

    @BindView(R.id.needNewAccountTitleTextView)
    TextView needNewAccountTitleTextView;
    @BindView(R.id.loginButton)
    CardView loginButton;
    @BindView(R.id.loginWithPhoneButton)
    CardView loginWithPhoneButton;
    @BindView(R.id.userEmailEditText)
    EditText userEmailEditText;
    @BindView(R.id.userPasswordEditText)
    EditText userPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        customLoadingDialog = new CustomLoadingDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
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
        userLoginProcess();
    }

    @OnClick(R.id.loginWithPhoneButton)
    public void onLoginWithPhoneButtonClicked() {

    }

    private void userLoginProcess() {
        String userEmail = userEmailEditText.getText().toString();
        String userPassword = userPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(LoginActivity.this, "Provide a Valid Email Address to Login.", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(LoginActivity.this, "Provide a Valid Password to Login.", Toast.LENGTH_SHORT).show();
        } else {
            customLoadingDialog.startLoadingDialog();
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        customLoadingDialog.stopLoadingDialog();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    } else {
                        customLoadingDialog.stopLoadingDialog();
                        Toast.makeText(LoginActivity.this, "Error :" + task.getException().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
