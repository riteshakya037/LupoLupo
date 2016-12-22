package com.lupolupo.android.model.loaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * @author Ritesh Shakya
 */
public class GlideLoader {
    static Task<Void> getImage(final String parentFolder, final String fileName) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        File imgFile = new File(LupolupoAPIApplication.get().getCacheDir(), parentFolder + fileName);
        if (imgFile.exists()) {
            tcs.setResult(null);
        } else {
            Glide.with(LupolupoAPIApplication.get())
                    .load("http://lupolupo.com/" + parentFolder + fileName)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            saveImage(resource, parentFolder, fileName);
                            tcs.setResult(null);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            tcs.setError(null);
                        }
                    });
        }
        return tcs.getTask();
    }

    public static void load(String url, ImageView coverImage) {
        File imgFile = new File(LupolupoAPIApplication.get().getCacheDir(), url);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            coverImage.setImageBitmap(myBitmap);
        } else {
            Glide.with(LupolupoAPIApplication.get())
                    .load("http://lupolupo.com/" + url)
                    .crossFade()
                    .placeholder(R.drawable.background_empty)
                    .thumbnail(.5f)
                    .into(coverImage);
        }
    }

    private static void saveImage(Bitmap bitmap, String parentFolder, String fileName) {
        try {
            File cachePath = new File(LupolupoAPIApplication.get().getCacheDir(), parentFolder);
            cachePath.mkdirs();
            FileOutputStream stream = new FileOutputStream(cachePath + "/" + fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
