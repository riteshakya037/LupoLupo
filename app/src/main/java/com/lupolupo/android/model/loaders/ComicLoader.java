package com.lupolupo.android.model.loaders;


import com.lupolupo.android.common.StringUtils;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Comic;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.enums.AppMode;
import com.lupolupo.android.model.loaders.bases.LoaderBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * @author Ritesh Shakya
 */
public class ComicLoader implements LoaderBase {
    private static ComicLoader _instance;
    private Comic comicData;
    private List<Episode> episodeList;
    private final HashMap<String, ProgressCount> fileProgressMap = new HashMap<>();
    private int taskSize = 0;
    private boolean startLoading = false;

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
                clear();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    episodeList = results.getResult();
//                    Collections.sort(episodeList, new Episode.AbbreviationComparator());
                    tasks.add(ImageLoader.getImage("/images/" + comicData.id + "/", comicData.comic_big_image, ComicLoader.this));
                    for (final Episode episode : results.getResult()) {
                        episode.episode_name = StringUtils.replaceEncoded(episode.episode_name);
                        episode.comic_name = StringUtils.replaceEncoded(episode.comic_name);
                        taskSize++;
                        tasks.add(ImageLoader.getImage("images/" + episode.comic_id + "/" + episode.id + "/", episode.episode_image, ComicLoader.this));
                    }
                }
                startLoading = true;
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

    public Task<Void> startLoadingAll() {
        this.episodeList = new LinkedList<>();
        return LupolupoHTTPManager.getInstance().getAllEpisodes().onSuccessTask(new Continuation<List<Episode>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Episode>> results) throws Exception {
                ArrayList<Task<Void>> tasks = new ArrayList<>();
                clear();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    episodeList = results.getResult();
                    for (final Episode episode : results.getResult()) {
                        episode.episode_name = StringUtils.replaceEncoded(episode.episode_name);
                        episode.comic_name = StringUtils.replaceEncoded(episode.comic_name);
                        taskSize++;
                        tasks.add(ImageLoader.getImage("images/" + episode.comic_id + "/" + episode.id + "/", episode.episode_image, ComicLoader.this));
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

    @Override
    public void setProgress(String imgFile, int bytesWritten, int totalSize) {

    }

    public Task<Void> startLoading(final String comic_id) {
        return LupolupoHTTPManager.getInstance().getComics().onSuccessTask(new Continuation<List<Comic>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Comic>> results) throws Exception {
                clear();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    for (final Comic comic : results.getResult()) {
                        if (comic.id.equals(comic_id)) {
                            comicData = comic;
                        }
                    }
                }
                return startLoading(comicData);
            }
        });
    }
}
