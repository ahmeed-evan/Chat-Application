package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomLoadingDialog customLoadingDialog;

    @BindView(R.id.optionTab)
    TabLayout optionTab;
    @BindView(R.id.optionViewPager)
    ViewPager optionViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        customLoadingDialog=new CustomLoadingDialog(this);
        optionTabImplementation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            userNameSpecified();
        }
    }

    private void userNameSpecified() {
        customLoadingDialog.startLoadingDialog();
        String UUID=firebaseUser.getUid();
        databaseReference.child(ConstantKey.USER).child(UUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(ConstantKey.USER_NAME).exists()) {
                    startActivity(new Intent(MainActivity.this,EditProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|
                            Intent.FLAG_ACTIVITY_NEW_TASK));
                    customLoadingDialog.stopLoadingDialog();
                }else {
                    customLoadingDialog.stopLoadingDialog();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"ERROR: "+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void optionTabImplementation() {
        optionTab.setupWithViewPager(optionViewPager);
        TabLayoutViewPagerAdapter tabLayoutViewPagerAdapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(), 0);
        tabLayoutViewPagerAdapter.addFragment(new ChatFragment(), "Chat");
        tabLayoutViewPagerAdapter.addFragment(new GroupFragment(), "Group");
        tabLayoutViewPagerAdapter.addFragment(new ContactFragment(), "Contact");
        optionViewPager.setAdapter(tabLayoutViewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.dropdown_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

            case R.id.createGroup:
                new CustomCreateGroupDialog(MainActivity.this).startCreatingGroupDialog();

            case R.id.settings:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));

            default:
                return true;
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
