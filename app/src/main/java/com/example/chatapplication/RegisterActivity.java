package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private CustomLoadingDialog customLoadingDialog;

    @BindView(R.id.alreadyHaveAccountTitleTextView)
    TextView alreadyHaveAccountTitleTextView;
    @BindView(R.id.userEmailEditText)
    EditText userEmailEditText;
    @BindView(R.id.userPasswordEditText)
    EditText userPasswordEditText;
    @BindView(R.id.registerButton)
    CardView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        customLoadingDialog=new CustomLoadingDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.alreadyHaveAccountTitleTextView)
    public void onAlreadyHaveAccountTitleTextViewClicked(){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    @OnClick(R.id.registerButton)
    public void onRegisterButtonClicked(){
        registerUser();
    }

    private void registerUser() {
        String userEmail=userEmailEditText.getText().toString();
        String userPassword=userPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Provide a Email Address to Register",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Provide a Password to Register", Toast.LENGTH_LONG).show();
        }
        else {
            customLoadingDialog.startLoadingDialog();
            firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        customLoadingDialog.stopLoadingDialog();

                    }else {
                      Toast.makeText(RegisterActivity.this,"Error:"+ task.getException().toString(),Toast.LENGTH_LONG).show();
                      customLoadingDialog.stopLoadingDialog();
                    }
                }
            });
        }
    }
}
