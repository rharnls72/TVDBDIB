package com.web.curation.dao.vote;

import com.web.curation.model.vote.Vote;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VoteDao {
    int addVote(Vote vote);
    int deleteVote(Vote vote);
}