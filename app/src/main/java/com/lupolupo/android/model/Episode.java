package com.lupolupo.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("WeakerAccess")
public class Episode implements Parcelable {
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
    public String id;
    public String comic_id;
    public String episode_name;
    public String episode_image;
    public String likes;
    public String comic_name;
    public String created_date;
    public String e_abbreviation = "";
    public String link;

    private Episode(Parcel in) {
        id = in.readString();
        comic_id = in.readString();
        episode_name = in.readString();
        episode_image = in.readString();
        likes = in.readString();
        comic_name = in.readString();
        created_date = in.readString();
    }

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

    public static class PopularComparator implements Comparator<Episode> {

        @Override
        public int compare(Episode episode, Episode t1) {
            return Integer.valueOf(t1.likes) - Integer.valueOf(episode.likes);
        }
    }


    public static class AbbreviationComparator implements Comparator<Episode> {
        @Override
        public int compare(Episode episode, Episode t1) {
            return episode.e_abbreviation.compareTo(t1.e_abbreviation);
        }
    }


    public static class TimeComparator implements Comparator<Episode> {
        @Override
        public int compare(Episode episode, Episode t1) {
            return t1.created_date.compareTo(episode.created_date);
        }
    }
}
