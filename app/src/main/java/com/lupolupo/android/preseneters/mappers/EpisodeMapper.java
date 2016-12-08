package com.lupolupo.android.preseneters.mappers;

import android.support.v7.widget.RecyclerView;

/**
 * @author Ritesh Shakya
 */
public interface EpisodeMapper {
    void registerAdapter(RecyclerView.Adapter adapter);

    void setTitle(String episode_name);
}
