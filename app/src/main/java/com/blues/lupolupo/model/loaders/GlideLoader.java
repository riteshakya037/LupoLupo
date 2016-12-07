package com.blues.lupolupo.model.loaders;

import com.blues.lupolupo.common.LupolupoAPIApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * @author Ritesh Shakya
 */
class GlideLoader {
    static Task<Void> getImage(String coverURL) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        Glide.with(LupolupoAPIApplication.get())
                .load(coverURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        tcs.setError(e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        tcs.setResult(null);
                        return false;
                    }
                });
        return tcs.getTask();
    }
}
