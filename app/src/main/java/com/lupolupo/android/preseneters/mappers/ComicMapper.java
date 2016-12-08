package com.lupolupo.android.preseneters.mappers;

import com.lupolupo.android.model.Comic;
import com.lupolupo.android.preseneters.bases.BaseRecycleAdapterMapper;

/**
 * @author Ritesh Shakya
 */
public interface ComicMapper extends BaseRecycleAdapterMapper {
    void setHeader(Comic comicData);
}
