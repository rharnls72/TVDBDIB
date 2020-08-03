package com.web.curation.dao.dibs;

import com.web.curation.model.dibs.Dibs;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DibsDao {
    public int createFeedDibs(Dibs dibs);
    public int createEpisodeDibs(Dibs dibs);

    public int deleteFeedDibs(Dibs dibs);
    public int deleteEpisodeDibs(Dibs dibs);
}