package com.blues.lupolupo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class GetComicDto {
    public String success;
    public final List<Comic> comics = new LinkedList<>();
}
