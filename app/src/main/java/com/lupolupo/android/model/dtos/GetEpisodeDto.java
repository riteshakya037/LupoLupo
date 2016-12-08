package com.lupolupo.android.model.dtos;

import com.lupolupo.android.model.Episode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class GetEpisodeDto {
    public final List<Episode> episodes = new LinkedList<>();
    public String success;
}
