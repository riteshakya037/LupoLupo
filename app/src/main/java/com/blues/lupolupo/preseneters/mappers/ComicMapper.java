package com.blues.lupolupo.preseneters.mappers;

import com.blues.lupolupo.model.Comic;
import com.blues.lupolupo.preseneters.bases.BaseRecycleAdapterMapper;

/**
 * @author Ritesh Shakya
 */
public interface ComicMapper extends BaseRecycleAdapterMapper {
    void setHeader(Comic comicData);
}
