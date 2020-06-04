package com.example.chatapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupNameAdapter extends RecyclerView.Adapter<GroupNameAdapter.ViewHolder> {

    private List<String>groupNameList;

    public GroupNameAdapter(List<String> groupNameList) {
        this.groupNameList = groupNameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.group_name_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.groupNameTextView.setText(groupNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return groupNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.groupImage)
        ImageView groupImage;
        @BindView(R.id.groupNameTextView)
        TextView groupNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
