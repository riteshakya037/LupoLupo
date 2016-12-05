package com.blues.lupolupo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("WeakerAccess")
public class Episode implements Parcelable {
    public String id;
    public String comic_id;
    public String episode_name;
    public String episode_image;
    public String likes;
    public String comic_name;
    public String created_date;

    private Episode(Parcel in) {
        id = in.readString();
        comic_id = in.readString();
        episode_name = in.readString();
        episode_image = in.readString();
        likes = in.readString();
        comic_name = in.readString();
        created_date = in.readString();
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(comic_id);
        parcel.writeString(episode_name);
        parcel.writeString(episode_image);
        parcel.writeString(likes);
        parcel.writeString(comic_name);
        parcel.writeString(created_date);
    }
}
