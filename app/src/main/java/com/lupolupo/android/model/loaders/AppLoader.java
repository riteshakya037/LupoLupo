package com.lupolupo.android.model.loaders;


import com.lupolupo.android.common.StringUtils;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.enums.AppMode;
import com.lupolupo.android.model.loaders.bases.LoaderBase;
import com.lupolupo.android.preseneters.events.DownloadProgressEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * @author Ritesh Shakya
 */
public class AppLoader implements LoaderBase {
    private static AppLoader _instance;
    private List<Comic> comicList;
    private AppMode mAppMode = AppMode.RECENT;
    private List<Comic> tempList = new ArrayList<>();
    private final HashMap<String, ProgressCount> fileProgressMap = new HashMap<>();
    private int taskSize = 0;
    private boolean startLoading = false;

    public static AppLoader getInstance() {
        if (_instance == null)
            _instance = new AppLoader();
        return _instance;
    }

    public Task<Void> startLoading() {
        this.comicList = new LinkedList<>();
        return LupolupoHTTPManager.getInstance().getComics().onSuccessTask(new Continuation<List<Comic>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Comic>> results) throws Exception {
                ArrayList<Task<Void>> tasks = new ArrayList<>();
                clear();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    comicList = results.getResult();
                    for (final Comic comic : results.getResult()) {
                        taskSize++;
                        comic.comic_name = StringUtils.replaceEncoded(comic.comic_name);
                        tasks.add(ImageLoader.getImage("images/" + comic.id + "/", comic.comic_big_image, AppLoader.this));
                        tasks.add(ImageLoader.getImage("images/" + comic.id + "/", comic.comic_image, AppLoader.this, false));
                    }
                    for (Comic comic : comicList) {
                        if (comic.getChecked())
                            tempList.add(comic);
                    }
                }
                startLoading = true;
                return Task.whenAll(tasks);
            }
        });
    }

    private void clear() {
        fileProgressMap.clear();
        taskSize = 0;
        startLoading = false;
    }


    public List<Comic> getComics() {
        return comicList;
    }


    public List<Comic> getLargeComics() {
        return tempList;
    }

    public AppMode getMode() {
        return mAppMode;
    }

    public void setAppMode(AppMode appMode) {
        this.mAppMode = appMode;
    }

    @Override
    public void setProgress(String imgFile, int bytesWritten, int totalSize) {
        synchronized (fileProgressMap) {
            fileProgressMap.put(imgFile, new ProgressCount(bytesWritten, totalSize));
            if (startLoading) {
                int totalBytesWritten = 0;
                int totalFileSize = 0;
                for (ProgressCount progressCount : fileProgressMap.values()) {
                    totalBytesWritten += progressCount.bytesWritten;
                    totalFileSize += progressCount.totalSize;
                }
                EventBus.getDefault().post(new DownloadProgressEvent(totalBytesWritten, totalFileSize, taskSize * 2 / fileProgressMap.size()));
            }
        }
    }
}
