package com.blues.lupolupo.views.adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blues.lupolupo.R;
import com.blues.lupolupo.common.LupolupoAPIApplication;
import com.blues.lupolupo.model.Episode;
import com.blues.lupolupo.views.holders.EpisodeHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class ComicEpisodeAdapter extends RecyclerView.Adapter<EpisodeHolder> {
    private List<Episode> data;


    public ComicEpisodeAdapter() {
        data = new LinkedList<>();
    }

    @Override
    public EpisodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_episode, parent, false);
        return new EpisodeHolder(v);
    }

    @Override
    public void onBindViewHolder(EpisodeHolder holder, int position) {
        Episode currentEpisode = data.get(position);
        holder.episodeTitle.setText(currentEpisode.episode_name);
        holder.episodeId.setText(LupolupoAPIApplication.get().getString(R.string.episode_no, position + 1));
        holder.episodeLikeCount.setText(currentEpisode.likes);
        holder.setEpisodeImage("http://lupolupo.com/images/" + currentEpisode.comic_id + "/" + currentEpisode.id + "/" + currentEpisode.episode_image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Episode> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
}
