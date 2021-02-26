package com.example.qaunewsalerts;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class Row implements Parcelable {
    private int mimage;
    private String mtext1;
    private String mtext2;
    public Row(int image,String text1, String text2) {
        mimage = image;
        mtext1 = text1;
        mtext2 = text2;


    }

    protected Row(Parcel in) {
        mimage = in.readInt();
        mtext1 = in.readString();
        mtext2 = in.readString();
    }

    public static final Creator<Row> CREATOR = new Creator<Row>() {
        @Override
        public Row createFromParcel(Parcel in) {
            return new Row(in);
        }

        @Override
        public Row[] newArray(int size) {
            return new Row[size];
        }
    };

    public int getImageResource(){
        return mimage;
}
    public String getText1() {
        return mtext1;
    }
    public String getText2() {
        return mtext2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mimage);
        dest.writeString(mtext1);
        dest.writeString(mtext2);
    }
    public void onCustomToggleClick(View view){


    }
}
