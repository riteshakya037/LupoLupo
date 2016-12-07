package com.blues.lupolupo.model.dtos;

import com.blues.lupolupo.model.Panel;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class GetPanelDto {
    public String success;
    public final List<Panel> panels = new LinkedList<>();
}
