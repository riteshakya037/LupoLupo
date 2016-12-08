package com.lupolupo.android.views.adaptors;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.views.activities.EpisodeActivity;
import com.lupolupo.android.views.activities.SplashActivity;
import com.lupolupo.android.views.bases.BaseEmptyRelativeLayoutView;
import com.lupolupo.android.views.holders.EpisodeHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class ComicEpisodeAdapter extends RecyclerView.Adapter<EpisodeHolder> {
    private final Activity activity;
    private List<Episode> data;
    private final BaseEmptyRelativeLayoutView listener;


    public ComicEpisodeAdapter(Activity activity, BaseEmptyRelativeLayoutView listener) {
        this.activity = activity;
        this.listener = listener;
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
    public void onBindViewHolder(EpisodeHolder holder, final int position) {
        final Episode currentEpisode = data.get(position);
        holder.episodeTitle.setText(currentEpisode.episode_name);
        holder.txtEpisodeId.setText(LupolupoAPIApplication.get().getString(R.string.episode_no, position + 1));
        holder.txtEpisodeLikeCount.setText(currentEpisode.likes);
        holder.episodeDate.setText(currentEpisode.created_date);
        holder.setEpisodeId(currentEpisode.id);
        holder.setEpisodeImage("http://lupolupo.com/images/" + currentEpisode.comic_id + "/" + currentEpisode.id + "/" + currentEpisode.episode_image);
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SplashActivity.class);
                intent.putExtra(EpisodeActivity.INTENT_EPISODE, currentEpisode);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Episode> data) {
        this.data = data;
        if (data.isEmpty()) {
            listener.showEmptyRelativeLayout();
        } else {
            listener.hideEmptyRelativeLayout();
        }
        this.notifyDataSetChanged();
    }
}
