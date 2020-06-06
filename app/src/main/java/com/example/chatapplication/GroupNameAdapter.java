package com.example.chatapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Interfaces.OnRecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupNameAdapter extends RecyclerView.Adapter<GroupNameAdapter.ViewHolder> {

    private List<String>groupNameList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public GroupNameAdapter(List<String> groupNameList, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.groupNameList = groupNameList;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}
