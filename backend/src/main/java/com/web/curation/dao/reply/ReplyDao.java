package com.web.curation.dao.reply;

import java.util.List;
import java.util.Map;

import com.web.curation.model.reply.Reply;

public interface ReplyDao {
    // Create
    int createReply(Reply reply);

    // Read
    List<Reply> getReplyList(Map<String, Object> param);
    List<Reply> getReReplyList(Map<String, Object> param);

    // Update
    int modifyReply(Reply reply);

    // Delete
    int deleteReply(int erno);
}