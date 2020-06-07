package com.example.chatapplication.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.chatapplication.ConstantKey;
import com.example.chatapplication.CustomLoadingDialog;
import com.example.chatapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends AppCompatActivity {
    private boolean isEditClicked = false;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private CustomLoadingDialog customLoadingDialog;

    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.aboutEditText)
    EditText aboutEditText;
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.editLayout)
    TextView editLayout;
    @BindView(R.id.saveButton)
    CardView saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Profile");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        customLoadingDialog=new CustomLoadingDialog(this);
        displayRetrieveData();
        editFieldEnable(isEditClicked);
        buttonClickable(isEditClicked);
    }

    @OnClick(R.id.editLayout)
    public void editLayoutClicked() {
        isEditClicked = true;
        editFieldEnable(isEditClicked);
        nameEditText.requestFocus();
        buttonClickable(isEditClicked);
    }

    @OnClick(R.id.saveButton)
    public void saveButtonClicked() {
        updateUI();
    }

    private void displayRetrieveData() {
        customLoadingDialog.startLoadingDialog();
        databaseReference.child(ConstantKey.USER).child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()&&dataSnapshot.hasChild(ConstantKey.USER_NAME)){
                    String userName=dataSnapshot.child(ConstantKey.USER_NAME).getValue().toString();
                    String userStatus=dataSnapshot.child(ConstantKey.USER_STATUS).getValue().toString();
                    String userPhoneNumber = dataSnapshot.child(ConstantKey.USER_PHONE_NUMBER).getValue().toString();
                    nameEditText.setText(userName);
                    aboutEditText.setText(userStatus);
                    phoneEditText.setText(userPhoneNumber);
                    customLoadingDialog.stopLoadingDialog();
                }else {
                    customLoadingDialog.stopLoadingDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this,"ERROR: "+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void buttonClickable(boolean isEditClicked) {
        if (isEditClicked) {
            saveButton.setVisibility(View.VISIBLE);
        } else {
            saveButton.setVisibility(View.GONE);
        }
    }

    private void editFieldEnable(boolean isEditClicked) {
        nameEditText.setEnabled(isEditClicked);
        aboutEditText.setEnabled(isEditClicked);
        phoneEditText.setEnabled(isEditClicked);
    }

    private void updateUI() {
        customLoadingDialog.startLoadingDialog();
        String inputName = nameEditText.getText().toString();
        String inputAbout = aboutEditText.getText().toString();
        String inputPhone = phoneEditText.getText().toString();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> userInfoMap = new HashMap<>();
                userInfoMap.put(ConstantKey.USER_UUID,firebaseUser.getUid());

                if (!TextUtils.isEmpty(inputName)) {
                    nameEditText.setText(inputName);
                    userInfoMap.put(ConstantKey.USER_NAME,inputName);
                }else {
                    userInfoMap.put(ConstantKey.USER_NAME,"");
                }
                if (!TextUtils.isEmpty(inputAbout)) {
                    aboutEditText.setText(inputAbout);
                    userInfoMap.put(ConstantKey.USER_STATUS,inputAbout);
                }else {
                    userInfoMap.put(ConstantKey.USER_STATUS,"");
                }
                if (!TextUtils.isEmpty(inputPhone)) {
                    phoneEditText.setText(inputPhone);
                    userInfoMap.put(ConstantKey.USER_PHONE_NUMBER,inputPhone);
                }else {
                    userInfoMap.put(ConstantKey.USER_PHONE_NUMBER,"");
                }

                databaseReference.child(ConstantKey.USER).child(firebaseUser.getUid()).setValue(userInfoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        customLoadingDialog.stopLoadingDialog();
                    }
                });
                isEditClicked = false;
                buttonClickable(isEditClicked);
                editFieldEnable(isEditClicked);
            }
        });
    }
}
