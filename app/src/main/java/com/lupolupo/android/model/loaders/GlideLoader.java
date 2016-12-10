package com.lupolupo.android.model.loaders;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;

import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * @author Ritesh Shakya
 */
public class GlideLoader {
    static Task<Void> getImage(String coverURL) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        Glide.with(LupolupoAPIApplication.get())
                .load(coverURL)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        tcs.setResult(null);
                    }
                });
        return tcs.getTask();
    }

    public static void load(String url, ImageView coverImage) {
        Glide.with(LupolupoAPIApplication.get())
                .load(url)
                .crossFade()
                .placeholder(R.drawable.background_empty)
                .thumbnail(.5f)
                .into(coverImage);
    }
}
