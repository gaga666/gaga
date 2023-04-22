package com.example.graduates.listview;

import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;


public class Home extends BaseObservable {
    private String text;

    private String date;

    private String title;

    private String email;

    public void setText(String text) {
        this.text = text;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
