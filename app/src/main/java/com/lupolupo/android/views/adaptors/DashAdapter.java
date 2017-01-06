package com.lupolupo.android.views.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lupolupo.android.R;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.enums.AppMode;
import com.lupolupo.android.model.loaders.AppLoader;
import com.lupolupo.android.views.activities.ComicActivity;
import com.lupolupo.android.views.activities.SplashActivity;
import com.lupolupo.android.views.holders.DashViewSmallHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class DashAdapter extends RecyclerView.Adapter<DashViewSmallHolder> {
    private final Context context;
    private List<Comic> data;

    public DashAdapter(Context context) {
        this.context = context;
        this.data = new LinkedList<>();
    }

    @Override
    public DashViewSmallHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_small_preview, parent, false);
        return new DashViewSmallHolder(v);
    }

    @Override
    public void onBindViewHolder(final DashViewSmallHolder holder, int position) {
        final Comic currentComic = data.get(position);
        holder.comicTitle.setText(currentComic.comic_name);
        holder.loadImage("images/" + currentComic.id + "/" + currentComic.comic_image);
        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SplashActivity.class);
                intent.putExtra(ComicActivity.INTENT_COMIC, currentComic);
                context.startActivity(intent);
            }
        });
        if (AppLoader.getInstance().getMode() == AppMode.FEATURED && currentComic.getChecked()) {
            holder.checkImage.setVisibility(View.VISIBLE);
        } else {
            holder.checkImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Comic> getData() {
        return data;
    }

    public void setData(List<Comic> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
}
