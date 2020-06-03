package com.example.chatapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Model.SettingOption;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingOptionAdapter extends RecyclerView.Adapter<SettingOptionAdapter.ViewHolder>{

    private List<SettingOption>settingOptionList;

    public SettingOptionAdapter(List<SettingOption> settingOptionList) {
        this.settingOptionList = settingOptionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_option_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.settingOptionIcon.setImageResource(settingOptionList.get(position).getSettingOptionIcon());
        holder.settingOptionTextView.setText(settingOptionList.get(position).getSettingOptionTextView());
    }

    @Override
    public int getItemCount() {
        return settingOptionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.settingOptionIcon)
        ImageView settingOptionIcon;
        @BindView(R.id.settingOptionTextView)
        TextView settingOptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
