package com.lupolupo.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("WeakerAccess")
public class Comic implements Parcelable {
    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };
    public final String id;
    public final String comic_name;
    public final String comic_abbreviation;
    public final String comic_image;
    public final String comic_big_image;
    public final String created;

    private Comic(Parcel in) {
        id = in.readString();
        comic_name = in.readString();
        comic_abbreviation = in.readString();
        comic_image = in.readString();
        comic_big_image = in.readString();
        created = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(comic_name);
        parcel.writeString(comic_abbreviation);
        parcel.writeString(comic_image);
        parcel.writeString(comic_big_image);
        parcel.writeString(created);
    }
}
