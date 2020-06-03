package com.example.chatapplication.Model;

public class SettingOption {
    private int settingOptionIcon;
    private String settingOptionTextView;

    public int getSettingOptionIcon() {
        return settingOptionIcon;
    }

    public void setSettingOptionIcon(int settingOptionIcon) {
        this.settingOptionIcon = settingOptionIcon;
    }

    public String getSettingOptionTextView() {
        return settingOptionTextView;
    }

    public void setSettingOptionTextView(String settingOptionTextView) {
        this.settingOptionTextView = settingOptionTextView;
    }

    public SettingOption(int settingOptionIcon, String settingOptionTextView) {
        this.settingOptionIcon = settingOptionIcon;
        this.settingOptionTextView = settingOptionTextView;
    }
}
