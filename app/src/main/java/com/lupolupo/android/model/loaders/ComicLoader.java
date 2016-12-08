package com.lupolupo.android.model.loaders;


import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.Episode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * @author Ritesh Shakya
 */
public class ComicLoader {
    private static ComicLoader _instance;
    private Comic comicData;
    private List<Episode> episodeList;

    public static ComicLoader getInstance() {
        if (_instance == null)
            _instance = new ComicLoader();
        return _instance;
    }

    public Task<Task<Void>> startLoading(final Comic comicData) {
        this.comicData = comicData;
        this.episodeList = new LinkedList<>();
        return LupolupoHTTPManager.getInstance().getEpisodes(comicData.id).onSuccess(new Continuation<List<Episode>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Episode>> results) throws Exception {
                ArrayList<Task<Void>> tasks = new ArrayList<>();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    episodeList = results.getResult();
                    tasks.add(GlideLoader.getImage("http://lupolupo.com/images/" + comicData.id + "/" + comicData.comic_big_image));
                    for (final Episode episode : results.getResult()) {
                        tasks.add(GlideLoader.getImage("http://lupolupo.com/images/" + episode.comic_id + "/" + episode.id + "/" + episode.episode_image));
                    }
                }
                return Task.whenAll(tasks);
            }
        });
    }


    public Comic getComicData() {
        return comicData;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }
}
