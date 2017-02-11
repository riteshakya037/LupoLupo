package com.lupolupo.android.model.loaders;

import com.lupolupo.android.common.StringUtils;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.Panel;
import com.lupolupo.android.model.loaders.bases.LoaderBase;
import com.lupolupo.android.preseneters.events.DownloadProgressEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * @author Ritesh Shakya
 */
public class FlipLoader implements LoaderBase {
    private static FlipLoader _instance;
    private final HashMap<Episode, List<Panel>> episodeList = new LinkedHashMap<>();
    private final HashMap<String, ProgressCount> fileProgressMap = new HashMap<>();
    private int taskSize = 0;
    private boolean startLoading = false;

    public static FlipLoader getInstance() {
        if (_instance == null)
            _instance = new FlipLoader();
        return _instance;
    }

    public Task<Void> startLoading(final boolean skip) {
        return LupolupoHTTPManager.getInstance().getAllEpisodes().onSuccessTask(new Continuation<List<Episode>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Episode>> results) throws Exception {
                ArrayList<Task<Void>> tasks = new ArrayList<>();
                clear();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    for (final Episode episode : results.getResult()) {
                        episode.episode_name = StringUtils.replaceEncoded(episode.episode_name);
                        episode.comic_name = StringUtils.replaceEncoded(episode.comic_name);
                        taskSize++;
                        episodeList.put(episode, null);
                        tasks.add(ImageLoader.getImage("images/" + episode.comic_id + "/" + episode.id + "/", episode.episode_image, FlipLoader.this, skip));
                        tasks.add(LupolupoHTTPManager.getInstance().getPanel(episode.id).onSuccessTask(new Continuation<List<Panel>, Task<Void>>() {
                            @Override
                            public Task<Void> then(Task<List<Panel>> results) throws Exception {
                                ArrayList<Task<Void>> taskSub = new ArrayList<>();
                                if (results.getResult() != null && results.getResult().size() != 0) {
                                    synchronized (episodeList) {
                                        episodeList.put(episode, results.getResult());
                                        for (final Panel panel : results.getResult().subList(0, results.getResult().size() < 5 ? results.getResult().size() : 5)) {
                                            taskSize++;
                                            taskSub.add(ImageLoader.getImage("images/" + episode.comic_id + "/" + panel.episode_id + "/", panel.panel_image, FlipLoader.this));
                                        }
                                    }
                                }
                                startLoading = true;
                                return Task.whenAll(taskSub);
                            }
                        }));
                    }
                }
                startLoading = true;
                return Task.whenAll(tasks);
            }
        });
    }

    private void clear() {
        episodeList.clear();
        fileProgressMap.clear();
        taskSize = 0;
        startLoading = false;
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

    public HashMap<Episode, List<Panel>> getFlipMap() {
        return episodeList;
    }

    public List<Panel> getPanelList(Episode episodeData) {
        return episodeList.get(episodeData);
    }
}