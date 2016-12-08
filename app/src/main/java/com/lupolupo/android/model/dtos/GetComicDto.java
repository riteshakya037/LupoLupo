package com.lupolupo.android.model.dtos;

import com.lupolupo.android.model.Comic;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class GetComicDto {
    public final List<Comic> comics = new LinkedList<>();
    public String success;
}
