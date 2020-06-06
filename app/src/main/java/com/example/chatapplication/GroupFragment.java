package com.example.chatapplication;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Interfaces.OnRecyclerViewItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GroupFragment extends Fragment  implements OnRecyclerViewItemClickListener {

    private DatabaseReference databaseReference;
    private GroupNameAdapter groupNameAdapter;

    private List<String> groupNameList = new ArrayList<>();

    @BindView(R.id.groupNamesRecyclerView)
    RecyclerView groupNamesRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, view);
        recyclerViewImplementation();
        return view;
    }

    private void recyclerViewImplementation() {
        groupNameAdapter = new GroupNameAdapter(groupNameList, this);
        groupNamesRecyclerView.setAdapter(groupNameAdapter);
        groupNamesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupNamesRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(32));
        retrieveAndSetData();
    }

    private void retrieveAndSetData() {
        databaseReference= FirebaseDatabase.getInstance().getReference().child(ConstantKey.GROUPS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String>stringSet=new HashSet<>();
                Iterator iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    stringSet.add(((DataSnapshot)iterator.next()).getKey());
                }
                groupNameList.clear();
                groupNameList.addAll(stringSet);
                groupNameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"ERROR: "+databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }

    @Override
    public void onItemClicked(int position) {
        String groupName=groupNameList.get(position);
        startActivity(new Intent(getActivity(),GroupChatActivity.class)
        .putExtra(ConstantKey.ITEM_SELECTED_GROUPS,groupName));
    }

}
