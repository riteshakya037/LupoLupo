package com.lupolupo.android.model.loaders;


import com.lupolupo.android.common.StringUtils;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.enums.AppMode;

import java.util.ArrayList;
import java.util.Collections;
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

    public Task<Void> startLoading(final Comic comicData) {
        this.comicData = comicData;
        this.episodeList = new LinkedList<>();
        return LupolupoHTTPManager.getInstance().getEpisodes(comicData.id).onSuccessTask(new Continuation<List<Episode>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Episode>> results) throws Exception {
                ArrayList<Task<Void>> tasks = new ArrayList<>();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    episodeList = results.getResult();
                    tasks.add(GlideLoader.getImage("/images/" + comicData.id + "/", comicData.comic_big_image));
                    for (final Episode episode : results.getResult()) {
                        episode.episode_name = StringUtils.replaceEncoded(episode.episode_name);
                        episode.comic_name = StringUtils.replaceEncoded(episode.comic_name);

                        tasks.add(GlideLoader.getImage("images/" + episode.comic_id + "/" + episode.id + "/", episode.episode_image));
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
        if (AppLoader.getInstance().getMode() == AppMode.POPULAR) {
            List<Episode> tempList = new ArrayList<>(episodeList);
            Collections.sort(tempList, new Episode.PopularComparator());
            return tempList;
        }
        return episodeList;
    }
}
