package com.blues.lupolupo.model.loaders;


import com.blues.lupolupo.controllers.retrofit.LupolupoHTTPManager;
import com.blues.lupolupo.model.Episode;
import com.blues.lupolupo.model.Panel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * @author Ritesh Shakya
 */
public class EpisodeLoader {
    private static EpisodeLoader _instance;
    private Episode episodeData;
    private List<Panel> panelList;

    public static EpisodeLoader getInstance() {
        if (_instance == null)
            _instance = new EpisodeLoader();
        return _instance;
    }

    public Task<Task<Void>> startLoading(Episode episode) {
        this.episodeData = episode;
        this.panelList = new LinkedList<>();
        return LupolupoHTTPManager.getInstance().getPanel(episode.id).onSuccess(new Continuation<List<Panel>, Task<Void>>() {
            @Override
            public Task<Void> then(Task<List<Panel>> results) throws Exception {
                ArrayList<Task<Void>> tasks = new ArrayList<>();
                if (results.getResult() != null && results.getResult().size() != 0) {
                    panelList = results.getResult();
                    for (final Panel panel : results.getResult()) {
                        tasks.add(GlideLoader.getImage("http://lupolupo.com/images/" + episodeData.comic_id + "/" + panel.episode_id + "/" + panel.panel_image));
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
}