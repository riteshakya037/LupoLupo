package com.lupolupo.android.model.loaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.model.loaders.bases.LoaderBase;

import java.io.File;

import bolts.Task;
import bolts.TaskCompletionSource;
import cz.msebera.android.httpclient.Header;

/**
 * @author Ritesh Shakya
 */
public class ImageLoader {
    private static final String TAG = ImageLoader.class.getSimpleName();

    static Task<Void> getImage(final String parentFolder, final String fileName, final LoaderBase loaderBase) {
        return getImage(parentFolder, fileName, loaderBase, true);
    }

    static Task<Void> getImage(final String parentFolder, final String fileName, final LoaderBase loaderBase, boolean skip) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        final File imgFile = new File(LupolupoAPIApplication.get().getCacheDir(), parentFolder + fileName);
        if (imgFile.exists() && skip) {
            tcs.setResult(null);
            loaderBase.setProgress(imgFile.getAbsolutePath(), 0, 0);
        } else {
            HttpUtils.get("http://lupolupo.com/" + parentFolder + fileName, null, true, new FileAsyncHttpResponseHandler(imgFile) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    Log.d(TAG, "urlDownloadImage - onSuccess: " + Integer.toString(statusCode));
                    tcs.setResult(null);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable e, File file) {
                    Log.d(TAG, "urlDownloadImage - onFailure: " + Integer.toString(statusCode) + " " + e.getMessage());
                    file.delete();
                    tcs.setError(new Exception(e));
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    loaderBase.setProgress(imgFile.getAbsolutePath(), (int) bytesWritten, (int) totalSize);
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


    private static class HttpUtils {

        private static AsyncHttpClient clientAsync = new AsyncHttpClient();
        private static SyncHttpClient clientSync = new SyncHttpClient();

        public static void get(String url, RequestParams params, boolean beAsync, AsyncHttpResponseHandler responseHandler) {
            if (beAsync)
                clientAsync.get(url, params, responseHandler);
            else
                clientSync.get(url, params, responseHandler);
        }

        public static void post(String url, RequestParams params, boolean beAsync, AsyncHttpResponseHandler responseHandler) {
            if (beAsync)
                clientAsync.post(url, params, responseHandler);
            else
                clientSync.post(url, params, responseHandler);
        }
    }
}
