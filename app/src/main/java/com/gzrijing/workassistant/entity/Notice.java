package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Notice implements Parcelable {
    private String title;
    private String content;
    private String promulgator;
    private String date;

    public Notice() {
    }

    public Notice(String title, String content, String promulgator, String date) {
        this.title = title;
        this.content = content;
        this.promulgator = promulgator;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPromulgator() {
        return promulgator;
    }

    public void setPromulgator(String promulgator) {
        this.promulgator = promulgator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.promulgator);
        dest.writeString(this.date);
    }

    protected Notice(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.promulgator = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Notice> CREATOR = new Parcelable.Creator<Notice>() {
        public Notice createFromParcel(Parcel source) {
            return new Notice(source);
        }

        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };
}
