package com.lupolupo.android.model.loaders;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.lupolupo.android.common.LupolupoAPIApplication;

import java.util.HashMap;

import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * @author Ritesh Shakya
 */
public class GlideLoader {
    private static HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();

    static Task<Void> getImage(final String url) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        if (bitmapHashMap.containsKey(url)) {
            tcs.setResult(null);
        } else {
            Glide.with(LupolupoAPIApplication.get())
                    .load("http://lupolupo.com/" + url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            saveImage(resource, url);
                            tcs.setResult(null);
                        }
                    });
        }
        return tcs.getTask();
    }

    public static void load(String url, ImageView coverImage) {
        if (bitmapHashMap.containsKey(url)) {
            coverImage.setImageBitmap(bitmapHashMap.get(url));
        }
    }

    private static void saveImage(Bitmap bitmap, String url) {
        bitmapHashMap.put(url, bitmap);
    }

    public static Bitmap getBitmap(String url) {
        return bitmapHashMap.get(url);
    }
}
