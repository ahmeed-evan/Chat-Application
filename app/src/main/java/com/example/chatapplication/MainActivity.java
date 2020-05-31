package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @BindView(R.id.optionTab)
    TabLayout optionTab;
    @BindView(R.id.optionViewPager)
    ViewPager optionViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        optionTabImplementation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser==null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    private void optionTabImplementation() {
        optionTab.setupWithViewPager(optionViewPager);
        TabLayoutViewPagerAdapter tabLayoutViewPagerAdapter=new TabLayoutViewPagerAdapter(getSupportFragmentManager(),0);
        tabLayoutViewPagerAdapter.addFragment(new ChatFragment(),"Chat");
        tabLayoutViewPagerAdapter.addFragment(new GroupFragment(),"Group");
        tabLayoutViewPagerAdapter.addFragment(new ContactFragment(),"Contact");
        optionViewPager.setAdapter(tabLayoutViewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.dropdown_option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.logout:
               firebaseAuth.signOut();
               startActivity(new Intent(MainActivity.this,LoginActivity.class));
            case R.id.findFriend:

            case R.id.settings:

            default:return true;
        }
    }
}
