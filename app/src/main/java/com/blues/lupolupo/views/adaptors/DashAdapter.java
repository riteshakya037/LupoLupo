package com.blues.lupolupo.views.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blues.lupolupo.R;
import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.views.activities.ComicActivity;
import com.blues.lupolupo.views.holders.DashViewSmallHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class DashAdapter extends RecyclerView.Adapter<DashViewSmallHolder> {
    private List<Comic> data;
    private final Context context;

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
        holder.loadImage("http://lupolupo.com/images/" + currentComic.id + "/" + currentComic.comic_big_image);
        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ComicActivity.class);
                intent.putExtra(ComicActivity.INTENT_COMIC, currentComic);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Comic> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
}
