package com.web.curation.dao.reply;

import java.util.List;

import com.web.curation.model.reply.Reply;
import com.web.curation.model.reply.ReplyRequest;

public interface ReplyDao {
    // Create
    int createReply(Reply reply);

    // Read
    List<Reply> getReplyList(ReplyRequest req);
    List<Reply> getReReplyList(ReplyRequest req);

    // Update
    int modifyReply(Reply reply);

    // Delete
    int deleteReply(int erno);
}