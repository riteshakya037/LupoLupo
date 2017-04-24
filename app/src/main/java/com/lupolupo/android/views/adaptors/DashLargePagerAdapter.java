package com.lupolupo.android.views.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lupolupo.android.R;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.loaders.ImageLoader;
import com.lupolupo.android.views.custom.infinite_view_pager.InfinitePagerAdapter;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ritesh Shakya
 */
public class DashLargePagerAdapter extends InfinitePagerAdapter {


    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<Comic> mData;

    public DashLargePagerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mData = new LinkedList<>();
    }

    @Override
    public int getItemCount() {
        return mData.size() > 5 ? 5 : mData.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.fragment_large_image, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.loadImage(mData.get(position));
        return view;
    }

    public void setData(List<Comic> result) {
        mData = result;
        this.notifyDataSetChanged();
    }

    @SuppressWarnings("WeakerAccess")
    public static class ViewHolder {
        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.imageView)
        ImageView imageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void loadImage(Comic comicData) {
            ImageLoader.load("images/" + comicData.id + "/" + comicData.comic_big_image, imageView);
        }
    }

}
