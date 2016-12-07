package com.blues.lupolupo.views.adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blues.lupolupo.R;
import com.blues.lupolupo.model.Panel;
import com.blues.lupolupo.views.holders.EpisodePanelHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class EpisodeAdapter extends RecyclerView.Adapter<EpisodePanelHolder> {
    private List<Panel> mData;
    private String comic_id;

    public EpisodeAdapter() {
        mData = new LinkedList<>();
    }

    @Override
    public EpisodePanelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_episode_panel, parent, false);
        return new EpisodePanelHolder(v);
    }

    @Override
    public void onBindViewHolder(EpisodePanelHolder holder, int position) {
        Panel panelData = mData.get(position);
        holder.loadPanelImage("http://lupolupo.com/images/" + comic_id + "/" + panelData.episode_id + "/" + panelData.panel_image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Panel> result) {
        mData = result;
        this.notifyDataSetChanged();
    }

    public void setComicId(String comic_id) {
        this.comic_id = comic_id;
    }
}
