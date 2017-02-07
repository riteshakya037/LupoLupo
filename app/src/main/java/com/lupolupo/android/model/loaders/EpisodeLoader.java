package com.lupolupo.android.model.loaders;


import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.Panel;
import com.lupolupo.android.model.loaders.bases.LoaderBase;
import com.lupolupo.android.preseneters.events.DownloadProgressEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

/**
 * @author Ritesh Shakya
 */
public class EpisodeLoader implements LoaderBase {
    private static EpisodeLoader _instance;
    private Episode episodeData;
    private final HashMap<String, ProgressCount> fileProgressMap = new HashMap<>();
    private List<Panel> panelList;
    private int taskSize = 0;

    public static EpisodeLoader getInstance() {
        if (_instance == null)
            _instance = new EpisodeLoader();
        return _instance;
    }

    public Task<Void> startLoading(Episode episode) {
        this.episodeData = episode;
        this.panelList = new LinkedList<>();
        return LupolupoHTTPManager.getInstance().getPanel(episode.id).onSuccessTask(new Continuation<List<Panel>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Panel>> results) throws Exception {
                ArrayList<Task<Void>> tasks = new ArrayList<>();
                fileProgressMap.clear();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    panelList = results.getResult();
                    for (final Panel panel : results.getResult().subList(0, results.getResult().size() < 5 ? results.getResult().size() : 5)) {
                        taskSize++;
                        tasks.add(ImageLoader.getImage("images/" + episodeData.comic_id + "/" + panel.episode_id + "/", panel.panel_image, EpisodeLoader.this));
                    }
                }
                return Task.whenAll(tasks);
            }
        });
    }

    public List<Panel> getPanelList() {
        return panelList;
    }

    public Episode getEpisode() {
        return episodeData;
    }

    public Episode getEpisodeData() {
        return episodeData;
    }

    public void setEpisodeData(Episode episodeData) {
        this.episodeData = episodeData;
    }

    public void getRemaining() {
        Task.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (Panel panel : panelList) {
                    ImageLoader.getImage("images/" + episodeData.comic_id + "/" + panel.episode_id + "/", panel.panel_image, EpisodeLoader.this);
                }
                return null;
            }
        });
    }

    @Override
    public void setProgress(String imgFile, int bytesWritten, int totalSize) {
        synchronized (fileProgressMap) {
            fileProgressMap.put(imgFile, new ProgressCount(bytesWritten, totalSize));
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
