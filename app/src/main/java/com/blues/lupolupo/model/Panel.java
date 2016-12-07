package com.blues.lupolupo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Ritesh Shakya
 */
@SuppressWarnings("WeakerAccess")
public class Panel implements Parcelable {
    public static final Creator<Panel> CREATOR = new Creator<Panel>() {
        @Override
        public Panel createFromParcel(Parcel in) {
            return new Panel(in);
        }

        @Override
        public Panel[] newArray(int size) {
            return new Panel[size];
        }
    };
    public final String id;
    public final String panel_image;
    public final String episode_id;
    public final String created;

    private Panel(Parcel in) {
        id = in.readString();
        panel_image = in.readString();
        episode_id = in.readString();
        created = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(panel_image);
        parcel.writeString(episode_id);
        parcel.writeString(created);
    }
}
