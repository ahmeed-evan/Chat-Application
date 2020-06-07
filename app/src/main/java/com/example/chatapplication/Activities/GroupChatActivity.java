package com.example.chatapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapplication.ConstantKey;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupChatActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private DatabaseReference userUidDatabaseReference,groupNameDatabaseReference,groupMessageKeyDatabaseReference;
    private String currentGroupName,currentUserName;

    @BindView(R.id.newMessageEditText)
    EditText newMessageEditText;

    public GroupChatActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        ButterKnife.bind(this);
        currentGroupName= getIntent().getExtras().get(ConstantKey.ITEM_SELECTED_GROUPS).toString();
        getSupportActionBar().setTitle(currentGroupName);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userUidDatabaseReference = FirebaseDatabase.getInstance().getReference().child(ConstantKey.USER).child(firebaseUser.getUid());
        groupNameDatabaseReference=FirebaseDatabase.getInstance().getReference().child(ConstantKey.GROUPS).child(currentGroupName);
        getUserInfo();
    }

    private void getUserInfo() {
        userUidDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    currentUserName=dataSnapshot.child(ConstantKey.USER_NAME).getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GroupChatActivity.this,"ERROR: "+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.sendButton)
    void sendButtonClicked(){
        String newMessage=newMessageEditText.getText().toString();
        newMessageEditText.setText("");
        String newMessageKey=groupNameDatabaseReference.push().getKey();
        groupMessageKeyDatabaseReference=groupNameDatabaseReference.child(newMessageKey);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(newMessage)){
                    Date date=new Date();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd yyyy");
                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a");
                    String currentDate=simpleDateFormat.format(date.getTime());
                    String currentTime=simpleTimeFormat.format(date.getTime());

                    HashMap<String,Object>newMessageInfoMap=new HashMap<>();
                    newMessageInfoMap.put(ConstantKey.USER_NAME,currentUserName);
                    newMessageInfoMap.put(ConstantKey.NEW_MESSAGE,newMessage);
                    newMessageInfoMap.put(ConstantKey.CURRENT_DATE,currentDate);
                    newMessageInfoMap.put(ConstantKey.CURRENT_TIME,currentTime);

                    groupMessageKeyDatabaseReference.updateChildren(newMessageInfoMap);
                }
            }
        });
    }
}