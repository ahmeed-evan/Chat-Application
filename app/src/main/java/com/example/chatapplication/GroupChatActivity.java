package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupChatActivity extends AppCompatActivity {
    @BindView(R.id.sendButton)
    ImageView sendButton;
    @BindView(R.id.newMessageEditText)
    EditText newMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        ButterKnife.bind(this);
        String currentGroupName= getIntent().getExtras().get(ConstantKey.ITEM_SELECTED_GROUPS).toString();
        getSupportActionBar().setTitle(currentGroupName);
    }
}