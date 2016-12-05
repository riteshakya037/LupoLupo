package com.blues.lupolupo.model.dtos;

import com.blues.lupolupo.model.Comic;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class GetComicDto {
    public String success;
    public final List<Comic> comics = new LinkedList<>();
}
