package com.web.curation.dao.episode;

import java.util.List;

import com.web.curation.model.episode.EpisodeDB;
import com.web.curation.model.episode.EpisodeResponse;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EpisodeDao {
    // Create
    int addNewEpisode(EpisodeResponse episode);

    // Read
    int checkDataExist(EpisodeResponse episode);
    List<EpisodeDB> getLikeReplyInfo(EpisodeResponse episode);

    // Update
    int increaseShare(int eno);

    // Delete
    int deleteEpisode(int eno);
}