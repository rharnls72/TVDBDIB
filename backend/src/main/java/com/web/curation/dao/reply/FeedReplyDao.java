package com.web.curation.dao.reply;

import java.util.List;

import com.web.curation.model.reply.Reply;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeedReplyDao {
    // Create
    int createReply(Reply reply);

    // Read
    List<Reply> getReplyList(int eno);

    // Update
    int modifyReply(Reply reply);

    // Delete
    int deleteReply(int erno);
}