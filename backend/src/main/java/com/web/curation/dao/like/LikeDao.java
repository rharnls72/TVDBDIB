package com.web.curation.dao.like;

import com.web.curation.model.like.Like;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LikeDao {
    // Like 는 Create, Delete 만 필요함

    // Create
    int addProgramLike(Like like);
    int addProgramReplyLike(Like like);

    int addEpisodeLike(Like like);
    int addEpisodeReplyLike(Like like);

    int addFeedLike(Like like);
    int addFeedReplyLike(Like like);

    // Delete
    int deleteProgramLike(Like like);
    int deleteProgramReplyLike(Like like);

    int deleteEpisodeLike(Like like);
    int deleteEpisodeReplyLike(Like like);

    int deleteFeedLike(Like like);
    int deleteFeedReplyLike(Like like);

    // Read(for Program)
    Like getProgramLikeInfo(Like like);
}