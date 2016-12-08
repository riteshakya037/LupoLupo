package com.lupolupo.android.model.dtos;

import com.lupolupo.android.model.Panel;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class GetPanelDto {
    public final List<Panel> panels = new LinkedList<>();
    public String success;
}
