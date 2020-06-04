package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Model.SettingOption;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    private List<SettingOption> settingOptionList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextView profileNameTextView;
    private TextView aboutTextView;

    @BindView(R.id.settingsOptionRecyclerView)
    RecyclerView settingsOptionRecyclerView;
    @BindView(R.id.profileInfoLayout)
    ConstraintLayout profileInfoLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        recyclerViewImplementation();
        getSupportActionBar().setTitle("Setting");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        profileNameTextView = findViewById(R.id.profileNameTextView);
        aboutTextView = findViewById(R.id.aboutTextView);
        displayRetrieveData();
    }

    private void displayRetrieveData() {
        databaseReference.child(ConstantKey.USER).child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChild(ConstantKey.USER_NAME)) {
                    String userName = dataSnapshot.child(ConstantKey.USER_NAME).getValue().toString();
                    String userStatus = dataSnapshot.child(ConstantKey.USER_STATUS).getValue().toString();
                    profileNameTextView.setText(userName);
                    aboutTextView.setText(userStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SettingActivity.this, "ERROR: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recyclerViewImplementation() {
        settingsOptionRecyclerView.setAdapter(new SettingOptionAdapter(settingOptionList));
        settingsOptionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addOption();
    }

    private void addOption() {
        settingOptionList.add(new SettingOption(R.drawable.ic_key, "Account"));
        settingOptionList.add(new SettingOption(R.drawable.ic_chat, "Chat"));
        settingOptionList.add(new SettingOption(R.drawable.ic_notification, "Notification"));
        settingOptionList.add(new SettingOption(R.drawable.ic_data_usage, "Data usage"));
        settingOptionList.add(new SettingOption(R.drawable.ic_about_help, "About and help"));
        settingOptionList.add(new SettingOption(R.drawable.ic_contact, "Contact"));
    }

    @OnClick(R.id.profileInfoLayout)
    public void onProfileInfoLayoutClicked() {
        startActivity(new Intent(SettingActivity.this, EditProfileActivity.class));
    }
}
