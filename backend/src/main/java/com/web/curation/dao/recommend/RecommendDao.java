package com.web.curation.dao.recommend;

import java.util.List;

import com.web.curation.model.recommend.RecommendData;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RecommendDao {
    public List<RecommendData> getEpisodeLike();
    public List<RecommendData> getProgramLike();

    public List<RecommendData> getEpisodeReply();
    public List<RecommendData> getProgramReply();

    public List<RecommendData> getEpisodeDibs();

    public List<RecommendData> getProgramFollow();

    // public void insertTestUser();
    // public void insertTestFollow();
    // public void insertTestReply();
    // public void insertTestLike();
}