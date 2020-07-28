package com.web.curation.dao.episode;

import java.util.List;

import com.web.curation.model.episode.Episode;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EpisodeDao {
    // Create
    int addNewEpisode(Episode episode);

    // Read
    List<Episode> getAllEpisodeList(int uno, int num);
    List<Episode> getEpisodeList(int pno);
    Episode getEpisodeDetail(int eno);

    // Update
    int modifyEpisode(Episode episode);

    // Delete
    int deleteEpisode(int eno);
}