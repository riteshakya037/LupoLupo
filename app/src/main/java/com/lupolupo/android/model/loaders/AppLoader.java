package com.lupolupo.android.model.loaders;


import com.lupolupo.android.BuildConfig;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.UserInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * @author Ritesh Shakya
 */
public class AppLoader {
    private static AppLoader _instance;
    private List<Comic> comicList;

    public static AppLoader getInstance() {
        if (_instance == null)
            _instance = new AppLoader();
        return _instance;
    }

    public Task<Task<Void>> startLoading() {
        this.comicList = new LinkedList<>();
        return LupolupoHTTPManager.getInstance().getComics().onSuccess(new Continuation<List<Comic>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Comic>> results) throws Exception {
                ArrayList<Task<Void>> tasks = new ArrayList<>();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    comicList = results.getResult();
                    tasks.add(saveInfo());
                    for (final Comic comic : results.getResult()) {
                        tasks.add(GlideLoader.getImage("http://lupolupo.com/images/" + comic.id + "/" + comic.comic_big_image));
                        tasks.add(GlideLoader.getImage("http://lupolupo.com/images/" + comic.id + "/" + comic.comic_image));
                    }
                }
                return Task.whenAll(tasks);
            }
        });
    }


    public List<Comic> getComics() {
        return comicList;
    }

    private Task<Void> saveInfo() {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        UserInfo info = new UserInfo();
        info.getInfo();
        if (BuildConfig.FLAVOR.equals("staging")) {
            tcs.setResult(null);
        } else {
            LupolupoHTTPManager.getInstance().saveInfo(info).continueWith(new Continuation<String, Void>() {
                @Override
                public Void then(Task<String> task) throws Exception {
                    tcs.setResult(null);
                    return null;
                }
            });
        }
        return tcs.getTask();
    }
}
