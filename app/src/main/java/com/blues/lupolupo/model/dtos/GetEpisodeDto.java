package com.blues.lupolupo.model.dtos;

import com.blues.lupolupo.model.Episode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class GetEpisodeDto {
    public final List<Episode> episodes = new LinkedList<>();
    public String success;
}
