package com.web.curation.dao.feed;

import java.util.List;

import com.web.curation.model.feed.Feed;
import com.web.curation.model.feed.FeedRequest;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeedDao {
    // Create
    int addNewFeed(Feed feed);
    
    // Read
    List<Feed> getFeedList(FeedRequest feedRequest);
    Feed getFeedDetail(FeedRequest feedRequest);
    int getFeedCount(int uno);

    // Update
    int modifyFeed(Feed feed);

    // Delete
    int deleteFeed(int fno);
}