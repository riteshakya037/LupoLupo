package com.lupolupo.android.model.loaders;


import com.lupolupo.android.common.StringUtils;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.enums.AppMode;
import com.lupolupo.android.model.loaders.bases.LoaderBase;

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
    private final HashMap<String, ProgressCount> fileProgressMap = new HashMap<>();
    private List<Comic> comicList;
    private AppMode mAppMode = AppMode.RECENT;
    private List<Comic> checkedList = new LinkedList<>();
    private List<Comic> uncheckedList = new LinkedList<>();
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
                        tasks.add(ImageLoader.getImage("images/" + comic.id + "/", comic.comic_image, AppLoader.this));
                    }
                    for (Comic comic : comicList) {
                        if (comic.getChecked()) {
                            if (!checkedList.contains(comic))
                                checkedList.add(comic);
                        } else {
                            if (!uncheckedList.contains(comic))
                                uncheckedList.add(comic);
                        }
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
        return uncheckedList;
    }


    public List<Comic> getLargeComics() {
        return checkedList;
    }

    public AppMode getMode() {
        return mAppMode;
    }

    public void setAppMode(AppMode appMode) {
        this.mAppMode = appMode;
    }

    @Override
    public void setProgress(String imgFile, int bytesWritten, int totalSize) {

    }
}
