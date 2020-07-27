package com.web.curation.dao.reply;

import java.util.List;
import java.util.Map;

import com.web.curation.model.reply.Reply;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProgramReplyDao extends ReplyDao {
    // Create
    int createReply(Reply reply);

    // Read
    List<Reply> getReplyList(Map<String, Object> param);

    // Update
    int modifyReply(Reply reply);

    // Delete
    int deleteReply(int erno);
}