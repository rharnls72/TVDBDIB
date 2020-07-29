package com.web.curation.dao.episode;

import com.web.curation.model.episode.EpisodeDB;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EpisodeDao {
    // Create
    int addNewEpisode(EpisodeDB episode);

    // Read
    EpisodeDB getLikeReplyInfo(EpisodeDB episode);

    // Update
    int increaseShare(int eno);

    // Delete
    int deleteEpisode(int eno);
}